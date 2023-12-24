package com.example.demo.repo

import com.example.demo.model.OrderStatusReq
import com.example.demo.model.Order
import com.example.demo.model.OrderStatusResp
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException

@Component
class OrderRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
//        @Value("\${data.mongodb.table.order}") val orderCol : String
):OrderRepo {
    override fun addOrder(order: Order): Order {
        return mongoTemplate.save(order)
    }

    override fun getOrder(): List<Order> {
        return mongoTemplate.findAll(Order::class.java)
    }

    override fun orderById(id: String): Order {
        return mongoTemplate.findById(id, Order::class.java) ?:throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot find any order by id"
        )
    }

//    override fun updateStatus(orderStatusResp: OrderStatusResp): Order {
//        return transactionTemplate.execute { _ ->
//            val oder = orderById(orderStatusResp.oderId)
//            val updatedOrderStatus = oder.updateStatus(orderStatusResp)
//            return@execute mongoTemplate.save(updatedOrderStatus)
//        } ?: throw ResponseStatusException(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Cannot update location now. Please try again"
//        )
//    }
    override fun updateStatus(orderStatusResp: OrderStatusResp): Order {
        return transactionTemplate.execute { _ ->
            val oder : Order = orderById(orderStatusResp.oderId)
            val updatedOrderStatus = oder.updateStatus(orderStatusResp)
            return@execute mongoTemplate.save(updatedOrderStatus)
        } ?: throw ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Cannot update order status now"
        )
    }

//    override fun updateStatus(orderStatusReq: OrderStatusReq): Order {
//        return transactionTemplate.execute { _ ->
//            val oder = orderById(orderStatusReq.oderId)
//            val updatedOrderStatus = oder.updateStatus(orderStatusReq)
//            return@execute mongoTemplate.save(updatedOrderStatus)
//        } ?: throw ResponseStatusException(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                "Cannot update location now. Please try again"
//        )
//    }
}