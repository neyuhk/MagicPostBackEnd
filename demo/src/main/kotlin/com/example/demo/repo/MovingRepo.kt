package com.example.demo.repo

import com.example.demo.model.Moving
import com.example.demo.model.Upcoming

interface MovingRepo {
    fun createMoving(moving: Moving) : Moving

    fun deleteUpcoming(orderId : String)
}