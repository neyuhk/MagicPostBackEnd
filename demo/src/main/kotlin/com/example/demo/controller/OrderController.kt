package com.example.demo.controller

import com.example.demo.model.OrderReq
import com.example.demo.model.OrderResp
import com.example.demo.service.OrderServiceImpl
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/order")
class OrderController(val orderServiceImpl: OrderServiceImpl) {
    @PostMapping( MediaType.MULTIPART_FORM_DATA_VALUE)
    fun createOrder(@ModelAttribute orderReq : OrderReq):OrderResp{
        return orderServiceImpl.addOrder(orderReq)
    }

    @GetMapping()
    fun listOrder() : List<OrderResp> {
        return orderServiceImpl.listOrder()
    }
}