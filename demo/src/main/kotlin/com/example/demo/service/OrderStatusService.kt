package com.example.demo.service

import com.example.demo.model.*

interface OrderStatusService {
//    fun createStatus(orderStatusReq: OrderStatusReq, serviceAddressReq: ServiceAddressReq, orderReq: OrderReq) : OrderStatusResp
//    fun createStatus(orderReq: OrderReq) : OrderStatusResp
    fun updateOrderStatus(orderStatusResp: OrderStatusResp): OrderResp
    fun updateStatus(id: String, serviceAddressReq: ServiceAddressReq) : OrderStatusResp
    fun getOrderStatusById(orderId : String) : OrderStatusResp
    fun listStatus() : List<OrderStatusResp>
    fun listStatusById(orderId: String) : List<OrderStatusResp>
}