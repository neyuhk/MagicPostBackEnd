package com.example.demo.repo

import com.example.demo.model.*
import org.springframework.data.mongodb.core.MongoTemplate
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
        val serviceAddressRepo : ServiceAddressRepo
) : OrderStatusRepo {
    override fun addOrderStatus(orderStatus: OrderStatus): OrderStatus {
        return mongoTemplate.save(orderStatus)
    }

    override fun updateStatus(orderId:String, serviceAddressId: String): OrderStatus {
        return transactionTemplate.execute{_ ->

            val orderStatus = getStatusById(orderId)
            val serviceAddress = serviceAddressRepo.getServiceAddressById(serviceAddressId)
            val updatedStatus = orderStatus.update(serviceAddress)
            val newOrder = orderRepo.orderById(orderId)

            if(updatedStatus.statusNum == 2) {
                upcomingRepoImpl.deleteUpcoming(orderId)
                movingRepoImpl.deleteMove(orderId)
                orderHereRepoImpl.createOrderHere(OrderHere(serviceAddressId, newOrder))
            }
            else {
                val serviceAddressIdAfter = orderHereRepoImpl.findIdHere(orderId)
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
        val listStatus : List<OrderStatus> = mongoTemplate.findAll(OrderStatus::class.java)
        for(orderStatus : OrderStatus in listStatus)
            if(orderStatus.orderId == orderId)
                return orderStatus
        throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot find any event with id $orderId"
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
