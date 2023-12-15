package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.OrderServiceImpl
import com.example.demo.service.OrderStatusServiceImpl
import org.springframework.data.mongodb.repository.Update
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
//@RequestMapping("api/v1/orderStatus")
class OrderStatusController(
        val orderStatusServiceImpl: OrderStatusServiceImpl,
        val orderServiceImpl: OrderServiceImpl
) {
//    @PostMapping("/orderStatus", MediaType.MULTIPART_FORM_DATA_VALUE)
//    fun createOrderStatus(@ModelAttribute orderStatusReq: OrderStatusReq, serviceAddressReq: ServiceAddressReq, orderReq: OrderReq) : OrderStatusResp {
//        return orderStatusServiceImpl.createStatus(orderStatusReq, serviceAddressReq, orderReq)
//    }

    @GetMapping("/{id}")
    fun getOrderStatusById(@PathVariable("id") id: String) : List<OrderStatusResp> {
        return orderStatusServiceImpl.listStatusById(id)
    }
    @GetMapping("/orderStatus")
    fun listOrderStatus() : List<OrderStatusResp> {
        return orderStatusServiceImpl.listStatus()
    }

    @PutMapping("/{id}", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun updateOrderStatus(
        @ModelAttribute serviceAddressReq: ServiceAddressReq,
        @PathVariable id: String
    ) : OrderStatusResp {
        return orderStatusServiceImpl.updateStatus(id, serviceAddressReq)
    }
}