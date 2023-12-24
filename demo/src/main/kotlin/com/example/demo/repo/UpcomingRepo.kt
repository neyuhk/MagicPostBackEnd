package com.example.demo.repo

import com.example.demo.model.Order
import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming

interface UpcomingRepo {
    fun createUpcoming(upcoming: Upcoming) : Upcoming
    fun deleteUpcoming(orderId : String)
    fun ListComing(addressId: String) : List<Upcoming>
}