package com.example.demo.repo

import com.example.demo.model.Order
import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException

@Component
class UpcomingRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
) : UpcomingRepo {
    override fun createUpcoming(upcoming: Upcoming): Upcoming {
        return mongoTemplate.save(upcoming)
    }

    override fun deleteUpcoming(orderId: String) {
        val query = Query()
        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
        mongoTemplate.findAndRemove(query, Upcoming::class.java)
    }

    override fun ListComing(addressId: String): MutableList<Upcoming> {
//        val query = Query()
//        query.addCriteria(Criteria.where("serviceAddressId").isEqualTo(addressId))
        val listComing = mongoTemplate.findAll(Upcoming::class.java)
        var listComingById : MutableList<Upcoming> = mutableListOf()
        for(serviceAddress in listComing){
            if(serviceAddress.serviceAddressId == addressId) {
                listComingById.add(serviceAddress)
            }
        }
        println(addressId)
        return listComingById
    }
}