package com.example.demo.model

import java.time.LocalDate

data class UserResp(
        val id: String = "",
        val username: String = "",
        val email: String = "",
        val phone_number: String
){
    constructor(user: User):this(
        id = user.id,
        username = user.username,
        email = user.email,
        phone_number = user.phone_number
    )
}

data class UserReq(
        val username: String,
        val email: String,
        var password: String,
        var rePassword: String,
        var phone_number : String
)

data class LoginReq(
        val email: String,
        val password: String
)

data class LoginResp(
    val user: UserResp,
    val accessKey: String,
    val refreshKey: String,
)

data class UpcomingResp(
        val serviceAddressId : String,
        val orderId : String
){
    constructor(upcoming: Upcoming) : this (
            upcoming.serviceAddressId,
            upcoming.orderId
    )
}

data class OrderHereResp(
        val serviceAddressId : String,
        val orderId : String
){
    constructor(orderHere: OrderHere) : this (
            orderHere.serviceAddressId,
            orderHere.orderId
    )
}

data class MovingResp(
        val serviceAddressId : String,
        val orderId : String
){
    constructor(moving: Moving) : this (
            moving.serviceAddressId,
            moving.orderId
    )
}

data class ServiceAddressReq(
//    val id : String,
    val name : String,
    val address: String,
)

data class ServiceAddressResp(
        val id: String,
        val name: String,
        val address: String,
){
    constructor(serviceAddress: ServiceAddress) : this(
            serviceAddress.id,
            serviceAddress.name,
            serviceAddress.address
    )
}

data class OrderReq(
//    val orderId : String,
    val address : String,
    val weight : Int,
    val quantity : Int,
    val oderStatus : String
)

data class OrderResp(
        val id: String,
        val address : String,
        val weight : Int,
        val quantity : Int,
        val oderDate : LocalDate,
        val oderStatus : String
){
    constructor(oder : Order): this(
        oder.id,
        oder.address,
        oder.weight,
        oder.quantity,
        oder.oderDate,
        oder.oderStatus
    )
}

data class OrderStatusReq(
    val oderId : String,
    val positionId : String,
    val status : String,
    val date : LocalDate
)
data class OrderStatusResp(
    val oderId : String,
//    val positionId : String,
    val statusNum : Int,
    val status : String,
    val date : LocalDate
){
    constructor(orderStatus: OrderStatus) : this(
            orderStatus.orderId,
//            orderStatus.positionId,
            orderStatus.statusNum!!,
            orderStatus.status,
            orderStatus.date
    )
}
data class UpdateReq(
        val orderId: String,
        val serviceAddressId: String
)
data class IdReq(
        val serviceAddressId: String
)
