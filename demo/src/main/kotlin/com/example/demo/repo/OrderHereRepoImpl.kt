package com.example.demo.repo

import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException

@Component
class OrderHereRepoImpl(
        val mongoTemplate: MongoTemplate,
        val transactionTemplate: TransactionTemplate
) : OrderHereRepo {
    override fun createOrderHere(orderHere: OrderHere): OrderHere {
        return mongoTemplate.save(orderHere)
    }

    override fun deleteHere(orderId: String) {
        val query = Query()
        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
        mongoTemplate.findAndRemove(query, OrderHere::class.java)
    }

    override fun findIdHere(orderId: String): String {
//        val query = Query()
//        query.addCriteria(Criteria.where("orderId").isEqualTo(orderId))
//        return mongoTemplate.findById(query, OrderHere::class.java)?.serviceAddressId
        val listHere = mongoTemplate.findAll(OrderHere::class.java)
                for(orderHere in listHere){
                    if(orderHere.orderId == orderId)
                        return orderHere.serviceAddressId
                }
        throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot find any orderHere"
        )
    }

    override fun findbyAddressId(addressId: String): OrderHere {
        TODO("Not yet implemented")
    }

    override fun ListHere(addressId: String): MutableList<OrderHere> {
        val listHere = mongoTemplate.findAll(OrderHere::class.java)
        var listHereById : MutableList<OrderHere> = mutableListOf()
        for(serviceAddress in listHere){
            if(serviceAddress.serviceAddressId == addressId) {
                listHereById.add(serviceAddress)
            }
        }
        println(listHere)
        println(listHereById)
        return listHereById
    }

}