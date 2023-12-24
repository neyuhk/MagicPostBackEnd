package com.example.demo.service

import com.example.demo.model.Order
import com.example.demo.model.UpcomingResp

interface UpcomingService {
    fun addUpcoming(serviceAddressId : String, order : Order) : UpcomingResp
    fun deleteUpcoming(orderId: String)
    fun listComing(serviceAddressId : String) : List<UpcomingResp>
}