package com.example.demo.service

import com.example.demo.model.OrderReq
import com.example.demo.model.OrderResp
import com.example.demo.model.OrderStatusReq
import com.example.demo.model.OrderStatusResp

interface OrderService {
    fun addOrder(orderReq: OrderReq) : OrderResp
    fun createStatus(orderId : String,orderReq: OrderReq) : OrderStatusResp
    fun listOrder() : List<OrderResp>
    fun getOrder(id : String) : OrderResp
    fun getOrderStatus(orderId : String) : OrderStatusResp
    fun deleteOrder(orderId: String)
//    fun updateOrderStatus(orderStatusResp: OrderStatusResp) : OrderResp
}