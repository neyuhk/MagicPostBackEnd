package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.OrderServiceImpl
import com.example.demo.service.OrderStatusServiceImpl
import org.springframework.data.mongodb.repository.Update
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
    @RequestMapping("api/v1/orderStatus")
class OrderStatusController(
        val orderStatusServiceImpl: OrderStatusServiceImpl,
        val orderServiceImpl: OrderServiceImpl
) {
//    @PostMapping("/orderStatus", MediaType.MULTIPART_FORM_DATA_VALUE)
//    fun createOrderStatus(@ModelAttribute orderStatusReq: OrderStatusReq, serviceAddressReq: ServiceAddressReq, orderReq: OrderReq) : OrderStatusResp {
//        return orderStatusServiceImpl.createStatus(orderStatusReq, serviceAddressReq, orderReq)
//    }

//    @GetMapping("/{id}")
//    fun getListOrderStatusById(@PathVariable("id") id: String) : List<OrderStatusResp> {
//        return orderStatusServiceImpl.listStatusById(id)
//    }

    @GetMapping("/{id}")
    fun getOrderStatusById(@PathVariable("id") id: String) :  OrderStatusResp {
        val listOrderStatusById = orderStatusServiceImpl.listStatusById(id)
        val index : Int = listOrderStatusById.size - 1
        val orderStatusResp : OrderStatusResp = listOrderStatusById[index]
//        return orderStatusServiceImpl.getOrderStatusById(id)
        return orderStatusResp
    }

    @GetMapping("listStatus/{id}")
    fun getListOrderStatusById(@PathVariable("id") id: String) : List<OrderStatusResp>  {
        return orderStatusServiceImpl.listStatusById(id)
    }

    @GetMapping()
    fun listOrderStatus() : List<OrderStatusResp> {
        return orderStatusServiceImpl.listStatus()
    }

    @PutMapping(MediaType.MULTIPART_FORM_DATA_VALUE)
    fun updateOrderStatus(
        @ModelAttribute id : String ,serviceAddressId: String,
    ) : OrderStatusResp {
        return orderStatusServiceImpl.updateStatus(id, serviceAddressId)
    }
}