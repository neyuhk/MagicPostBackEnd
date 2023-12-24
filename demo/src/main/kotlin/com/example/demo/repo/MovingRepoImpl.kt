package com.example.demo.repo

import com.example.demo.model.Moving
import com.example.demo.model.Upcoming
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.transaction.support.TransactionTemplate

class MovingRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
) : MovingRepo {
    override fun createMoving(moving: Moving): Moving {
        return mongoTemplate.save(moving)
    }

    override fun deleteUpcoming(orderId: String) {
        val query = Query()
        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
        mongoTemplate.findAndRemove(query, Upcoming::class.java)
    }
}