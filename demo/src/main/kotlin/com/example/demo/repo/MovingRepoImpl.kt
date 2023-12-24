package com.example.demo.repo

import com.example.demo.model.Moving
import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate

@Component
class MovingRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
) : MovingRepo {
    override fun createMoving(moving: Moving): Moving {
        return mongoTemplate.save(moving)
    }

    override fun deleteMove(orderId: String) {
        val query = Query()
        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
        mongoTemplate.findAndRemove(query, Moving::class.java)
    }

    override fun ListMove(addressId: String): List<Moving> {
        val query = Query()
        query.addCriteria(Criteria.where("serviceAddressId").isEqualTo(addressId))
        val listMove = mongoTemplate.findAll(Moving::class.java)
        val listMoveById : MutableList<Moving> = mutableListOf()
        for(serviceAddress in listMove){
            if(serviceAddress.serviceAddressId == addressId) {
                listMoveById.add(serviceAddress)
            }
        }
        return listMoveById
    }
}