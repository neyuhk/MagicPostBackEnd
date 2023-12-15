package com.example.demo.repo

import com.example.demo.model.Order
import com.example.demo.model.ServiceAddress
import com.example.demo.model.ServiceAddressReq
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class ServiceAddressRepoImpl(
    val mongoTemplate: MongoTemplate
) : ServiceAddressRepo {
    override fun addServiceAddress(serviceAddress: ServiceAddress) : ServiceAddress {
        return mongoTemplate.save(serviceAddress)
    }

    override fun getServiceAddress(): List<ServiceAddress> {
        return mongoTemplate.findAll(ServiceAddress::class.java)
    }
    override fun getServiceAddressById(id: String) : ServiceAddress {
        return mongoTemplate.findById(id, ServiceAddress::class.java) ?:throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cannot find any event with id $id"
        )
    }
}