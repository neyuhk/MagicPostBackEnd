package com.example.demo.service

import com.example.demo.model.Order
import com.example.demo.model.OrderHere
import com.example.demo.model.OrderHereResp
import com.example.demo.model.UpcomingResp
import com.example.demo.repo.OrderHereRepo
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
//@RequestMapping("api/v1/manager")
class OrderHereServiceImpl(
        val orderHereRepo: OrderHereRepo
) : OrderHereService {
    override fun addHere(serviceAddressId: String, order: Order) : OrderHereResp {
        val newHere = orderHereRepo.createOrderHere(OrderHere(serviceAddressId, order))
        return OrderHereResp(newHere)
    }

    override fun deleteHere(orderId: String) {
        orderHereRepo.deleteHere(orderId)
    }

    override fun listHere(serviceAddressId: String): List<OrderHereResp> {
        val here = orderHereRepo.ListHere(serviceAddressId).map {here -> OrderHereResp(here) }
        return here
    }
}