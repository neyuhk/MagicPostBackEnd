package com.example.demo.repo

import com.example.demo.model.OrderHere
import com.example.demo.model.Upcoming

interface OrderHereRepo {
    fun createOrderHere(orderHere: OrderHere) : OrderHere

    fun deleteHere(orderId : String)
    fun findIdHere(orderId: String) : String
    fun findbyAddressId(addressId : String) : OrderHere
    fun ListHere(addressId: String) : List<OrderHere>
}