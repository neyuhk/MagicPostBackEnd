package com.example.demo.repo

import com.example.demo.model.Moving
import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming

interface MovingRepo {
    fun createMoving(moving: Moving) : Moving
    fun deleteMove(orderId : String)
    fun ListMove(addressId: String) : List<Moving>
}