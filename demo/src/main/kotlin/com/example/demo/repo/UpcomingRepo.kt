package com.example.demo.repo

import com.example.demo.model.Order
import com.example.demo.model.Upcoming

interface UpcomingRepo {
    fun createUpcoming(upcoming: Upcoming) : Upcoming

    fun deleteUpcoming(orderId : String)
}