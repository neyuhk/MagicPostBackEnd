package com.example.demo.model

import java.time.LocalDate

data class UserResp(
        val id: String = "",
        val username: String = "",
        val email: String = "",
){
    constructor(user: User):this(
        id = user.id,
        username = user.username,
        email = user.email
    )
}

data class UserReq(
        val username: String,
        val email: String,
        var password: String,
        var rePassword: String,
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
    val status : String,
    val date : LocalDate
){
    constructor(orderStatus: OrderStatus) : this(
            orderStatus.orderId,
//            orderStatus.positionId,
            orderStatus.status,
            orderStatus.date
    )
}