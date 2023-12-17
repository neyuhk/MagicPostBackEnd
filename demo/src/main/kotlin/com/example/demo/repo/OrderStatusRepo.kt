package com.example.demo.repo

import com.example.demo.model.OrderStatus
import com.example.demo.model.ServiceAddressReq

interface OrderStatusRepo {
    fun addOrderStatus(orderStatus: OrderStatus) : OrderStatus
    fun updateStatus(orderId:String,serviceAddressReq: ServiceAddressReq) : OrderStatus
    fun getStatusById(orderId : String) : OrderStatus
    fun getListStatus() : List<OrderStatus>
    fun getListStatusById(orderId: String) : List<OrderStatus>
}