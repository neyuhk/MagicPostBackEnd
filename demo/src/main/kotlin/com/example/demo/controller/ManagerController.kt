package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/manager")
class ManagerController(
        val upcomingServiceImpl: UpcomingServiceImpl,
        val orderHereServiceImpl: OrderHereServiceImpl,
        val movingServiceImpl: MovingServiceImpl,
        val orderStatusServiceImpl: OrderStatusServiceImpl
) {
    @GetMapping("coming", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun listComing(@ModelAttribute idReq: IdReq) : MutableList<OrderStatusResp> {
        val listComing = upcomingServiceImpl.listComing(idReq.serviceAddressId)
        var listOrder : MutableList<OrderStatusResp> = mutableListOf()
        for(order in listComing)
            listOrder.add(orderStatusServiceImpl.getOrderStatusById(order.orderId))
        return listOrder
    }

    @GetMapping("here", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun listHere(@ModelAttribute idReq: IdReq) : MutableList<OrderStatusResp> {
        val listOrderHere = orderHereServiceImpl.listHere(idReq.serviceAddressId)
        var listOrder : MutableList<OrderStatusResp> = mutableListOf()
        for(order in listOrderHere)
            listOrder.add(orderStatusServiceImpl.getOrderStatusById(order.orderId))
        return listOrder
    }

    @GetMapping("moving", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun listMoving(@ModelAttribute idReq: IdReq) : MutableList<OrderStatusResp> {
        val listMoving = movingServiceImpl.listMove(idReq.serviceAddressId)
        var listOrder : MutableList<OrderStatusResp> = mutableListOf()
        for(order in listMoving)
            listOrder.add(orderStatusServiceImpl.getOrderStatusById(order.orderId))
        return listOrder
    }
}