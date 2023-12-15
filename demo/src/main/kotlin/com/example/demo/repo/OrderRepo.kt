package com.example.demo.repo

import com.example.demo.model.OrderStatusReq
import com.example.demo.model.Order
import com.example.demo.model.OrderStatusResp

interface OrderRepo {
    fun addOrder(order : Order) : Order
    fun getOrder() : List<Order>
    fun orderById(id : String) : Order
    fun updateStatus(orderStatusResp: OrderStatusResp) : Order
}