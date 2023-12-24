package com.example.demo.repo

import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.transaction.support.TransactionTemplate

class OrderHereRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
) : OrderHereRepo {
    override fun createOrderHere(orderHere: OrderHere): OrderHere {
        return mongoTemplate.save(orderHere)
    }

    override fun deleteUpcoming(orderId: String) {
        val query = Query()
        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
        mongoTemplate.findAndRemove(query, Upcoming::class.java)
    }

}