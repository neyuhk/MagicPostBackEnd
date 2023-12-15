package com.example.demo.repo

import com.example.demo.model.OrderStatus
import com.example.demo.model.ServiceAddressReq

interface OrderStatusRepo {
    fun addOrderStatus(orderStatus: OrderStatus) : OrderStatus
    fun updateStatus(serviceAddressReq: ServiceAddressReq) : OrderStatus
    fun getStatus(orderId : String) : OrderStatus
    fun getListStatus() : List<OrderStatus>
    fun getListStatusById(orderId: String) : List<OrderStatus>
}