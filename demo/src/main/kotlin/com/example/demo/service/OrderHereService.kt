package com.example.demo.service

import com.example.demo.model.Order
import com.example.demo.model.OrderHereResp
import com.example.demo.model.UpcomingResp

interface OrderHereService {
    fun addHere(serviceAddressId : String, order : Order) : OrderHereResp
    fun deleteHere(orderId : String)
    fun listHere(serviceAddressId : String) : List<OrderHereResp>
}