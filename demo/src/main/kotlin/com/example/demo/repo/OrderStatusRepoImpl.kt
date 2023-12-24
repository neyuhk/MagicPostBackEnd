package com.example.demo.repo

import com.example.demo.model.*
import jakarta.servlet.ServletException
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException


@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Component
class OrderStatusRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate,
        val orderRepo: OrderRepo,
        val upcomingRepoImpl: UpcomingRepoImpl,
        val orderHereRepoImpl: OrderHereRepoImpl,
        val movingRepoImpl: MovingRepoImpl,
        val serviceAddressRepoImpl : ServiceAddressRepoImpl
) : OrderStatusRepo {
    override fun addOrderStatus(orderStatus: OrderStatus): OrderStatus {
        return mongoTemplate.save(orderStatus)
    }

    override fun updateStatus(orderId:String, serviceAddressId: String): OrderStatus {
        val listStatus = getListStatusById(orderId)
        val orderStatus : OrderStatus = listStatus[listStatus.size-1]
        return transactionTemplate.execute{_ ->

            println("orderrrr iddd " + orderId)
            println(orderStatus)
//            val orderStatus = getStatusById(orderId)
            val serviceAddress = serviceAddressRepoImpl.getServiceAddressById(serviceAddressId)
            val updatedStatus = orderStatus.update(serviceAddress)
            println(updatedStatus)
            val newOrder = orderRepo.orderById(orderId)

            if(updatedStatus.statusNum == 2) {
                upcomingRepoImpl.deleteUpcoming(orderId)
                movingRepoImpl.deleteMove(orderId)
                orderHereRepoImpl.createOrderHere(OrderHere(serviceAddressId, newOrder))
            }
            if(updatedStatus.statusNum == 1) {
                val serviceAddressIdAfter = orderHereRepoImpl.findIdHere(orderId)
                println("ok " + serviceAddressIdAfter)
                upcomingRepoImpl.createUpcoming(Upcoming(serviceAddressId, newOrder))
                movingRepoImpl.createMoving(Moving(serviceAddressIdAfter, newOrder))
                orderHereRepoImpl.deleteHere(orderId)
            }

            return@execute mongoTemplate.save(updatedStatus)
        } ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Cannot update order status now"
        )
        }

    override fun getStatusById(orderId: String): OrderStatus {
//        val query = Query()
//        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
//        val listStatus = mongoTemplate.find(query, OrderStatus::class.java)
        val listStatus = mongoTemplate.findAll(OrderStatus::class.java)
//        var index = listStatus.size - 1
//        while (index >= 0){
//            val orderStatus = listStatus[index]
//            if(orderStatus.orderId == orderId){
//                println("id " + orderId + " true")
//                return orderStatus
//            }
//            index = index - 1
//        }
        for(orderStatus in listStatus){
            println("order id " + orderStatus.orderId)
            println(orderId)
            if(orderStatus.orderId == orderId){
                println("id " + orderId + " true")
                return orderStatus
            }
        }
                throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot find any status with order id"
        )


//        return mongoTemplate.findById(orderId, OrderStatus::class.java)?:throw ResponseStatusException(
//                HttpStatus.NOT_FOUND,
//                "Cannot find any event with id $orderId"
//        )
    }

    override fun getListStatus(): List<OrderStatus> {
        return mongoTemplate.findAll(OrderStatus::class.java)
    }

    override fun getListStatusById(orderId: String): List<OrderStatus> {
        val listStatus : List<OrderStatus> = mongoTemplate.findAll(OrderStatus::class.java)
//        val listOrderStatus = List<OrderStatus>
        var listOrderStatus : MutableList<OrderStatus> = mutableListOf()
        for (status in listStatus){
            if(status.orderId == orderId) {
                listOrderStatus.add(status)
            }
            println(listOrderStatus.size)
            println(status.orderId)
            println(orderId)
        }
        return listOrderStatus
    }
}
