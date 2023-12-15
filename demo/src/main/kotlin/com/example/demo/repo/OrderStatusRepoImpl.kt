package com.example.demo.repo

import com.example.demo.model.Order
import com.example.demo.model.OrderStatus
import com.example.demo.model.ServiceAddressReq
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException

@Component
class OrderStatusRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
) : OrderStatusRepo {
    override fun addOrderStatus(orderStatus: OrderStatus): OrderStatus {
        return mongoTemplate.save(orderStatus)
    }

    override fun updateStatus(serviceAddressReq: ServiceAddressReq): OrderStatus {
        return transactionTemplate.execute{_ ->
            val orderStatus = getStatus(serviceAddressReq.id)
            val updatedStatus = orderStatus.update(serviceAddressReq)
            return@execute mongoTemplate.save(updatedStatus)
        } ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Cannot update order status now"
        )
        }

    override fun getStatus(orderId: String): OrderStatus {
        return mongoTemplate.findById(orderId, OrderStatus::class.java)?:throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot find any event with id $orderId"
        )
    }

    override fun getListStatus(): List<OrderStatus> {
        return mongoTemplate.findAll(OrderStatus::class.java)
    }

    override fun getListStatusById(orderId: String): List<OrderStatus> {
        val listStatus : List<OrderStatus> = mongoTemplate.findAll(OrderStatus::class.java)
        val listOrderStatus : List<OrderStatus> = listOf()
        for (status in listStatus){
            if(status.orderId == orderId) {
                listOrderStatus.plus(status)
            }
        }
        return listOrderStatus
    }
}
