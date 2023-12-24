package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.OrderServiceImpl
import com.example.demo.service.OrderStatusServiceImpl
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/orderStatus")
class OrderStatusController(
        val orderStatusServiceImpl: OrderStatusServiceImpl
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
//        val listOrderStatusById = orderStatusServiceImpl.listStatusById(id)
//        val index : Int = listOrderStatusById.size - 1
//        val orderStatusResp : OrderStatusResp = listOrderStatusById[index]
        val orderStatusResp : OrderStatusResp = orderStatusServiceImpl.getOrderStatusById(id)
//        return orderStatusServiceImpl.getOrderStatusById(id)
        return orderStatusResp
    }

    @GetMapping("list/{id}")
    fun getListOrderStatusById(@PathVariable("id") id: String) : List<OrderStatusResp>  {
        return orderStatusServiceImpl.listStatusById(id)
    }

    @GetMapping()
    fun listOrderStatus() : List<OrderStatusResp> {
        return orderStatusServiceImpl.listStatus()
    }

    @PutMapping("update",MediaType.MULTIPART_FORM_DATA_VALUE)
    fun updateOrderStatus(@ModelAttribute updateReq: UpdateReq) : OrderStatusResp {
        return orderStatusServiceImpl.updateStatus(updateReq.orderId, updateReq.serviceAddressId)
    }
}