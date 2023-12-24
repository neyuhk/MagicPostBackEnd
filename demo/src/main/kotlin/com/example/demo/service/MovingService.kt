package com.example.demo.service

import com.example.demo.model.MovingResp
import com.example.demo.model.Order
import com.example.demo.model.UpcomingResp

interface MovingService {
    fun addMoving(serviceAddressId : String, order : Order) : MovingResp
    fun deleteMoving(orderId : String)
    fun listMove(serviceAddressId : String) : List<MovingResp>
}