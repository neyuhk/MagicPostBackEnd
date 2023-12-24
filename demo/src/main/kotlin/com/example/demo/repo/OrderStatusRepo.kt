package com.example.demo.repo

import com.example.demo.model.OrderStatus
import com.example.demo.model.OrderStatusResp

interface OrderStatusRepo {
    fun addOrderStatus(orderStatus: OrderStatus) : OrderStatus
    fun updateStatus(orderId:String, serviceAddressId : String) : OrderStatus
    fun getStatusById(orderId : String) : OrderStatus
    fun getListStatus() : List<OrderStatus>
    fun getListStatusById(orderId: String) : List<OrderStatus>
}