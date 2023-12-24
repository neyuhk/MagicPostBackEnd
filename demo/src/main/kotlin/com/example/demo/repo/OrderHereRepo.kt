package com.example.demo.repo

import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming

interface OrderHereRepo {
    fun createOrderHere(orderHere: OrderHere) : OrderHere

    fun deleteUpcoming(orderId : String)
}