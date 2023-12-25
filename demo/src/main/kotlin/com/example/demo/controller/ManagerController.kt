package com.example.demo.controller

import com.example.demo.model.*
import com.example.demo.service.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/manager")
class ManagerController(
        val upcomingServiceImpl: UpcomingServiceImpl,
        val orderHereServiceImpl: OrderHereServiceImpl,
        val movingServiceImpl: MovingServiceImpl,
        val orderStatusServiceImpl: OrderStatusServiceImpl,
        val orderServiceImpl: OrderServiceImpl
) {
    @GetMapping("coming", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun listComing(@ModelAttribute idReq: IdReq) : MutableList<OrderResp> {
        val listComing = upcomingServiceImpl.listComing(idReq.serviceAddressId)
        var listOrder : MutableList<OrderResp> = mutableListOf()
        for(order in listComing)
            listOrder.add(orderServiceImpl.getOrder(order.orderId))
        return listOrder
    }

    @GetMapping("here", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun listHere(@ModelAttribute idReq: IdReq) : MutableList<OrderResp> {
        val listOrderHere = orderHereServiceImpl.listHere(idReq.serviceAddressId)
        var listOrder : MutableList<OrderResp> = mutableListOf()
        for(order in listOrderHere)
            listOrder.add(orderServiceImpl.getOrder(order.orderId))
        return listOrder
    }

    @GetMapping("moving", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun listMoving(@ModelAttribute idReq: IdReq) : MutableList<OrderResp> {
        val listMoving = movingServiceImpl.listMove(idReq.serviceAddressId)
        var listOrder : MutableList<OrderResp> = mutableListOf()
        for(order in listMoving)
            listOrder.add(orderServiceImpl.getOrder(order.orderId))
        return listOrder
    }

    @GetMapping("done/{id}")
    fun successful(@PathVariable("id") id: String) : String{
        orderHereServiceImpl.deleteHere(id)
        orderServiceImpl.deleteOrder(id)
        println(id)
        return "Giao hàng thành công"
    }
}