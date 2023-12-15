package com.example.demo.model

import com.example.demo.util.utils.utils
import java.time.LocalDate

interface DemoIndentity{
    val id : String
    val username : String
    val email : String
    val password : String
}

data class User(
        override val id: String = "",
        override val username: String = "",
        override val email: String = "",
        val displayName: String = "",
        override val password: String = "",
):DemoIndentity{
    companion object{
        val currentUser : User = User()
        fun newUser(userReq: UserReq): User {
            val id = utils.newUUID()
            return User(
                    id,
                    username = userReq.username,
                    email = userReq.email,
                    password = userReq.password,
            )
        }
    }
}

data class Order(
    val id : String,
    val address : String,
    val weight : Int,
    val quantity : Int,
    val oderDate : LocalDate,
    val oderStatus : String
){
    constructor(oderReq : OrderReq):this(
        utils.newUUID(),
        oderReq.address,
        oderReq.weight,
        oderReq.quantity,
        LocalDate.now(),
        "Chuẩn bị giao hàng"
    )
    fun updateStatus(orderStatusResp: OrderStatusResp) : Order{
        return this.copy(
                this.id,
                this.address,
                this.weight,
                this.quantity,
                LocalDate.now(),
                orderStatusResp.status
        )
    }
}

data class ServiceAddress(
    val id : String,
    val name : String,
    val address: String,
){
    constructor(serviceAddressReq: ServiceAddressReq) : this(
        utils.newUUID(),
        serviceAddressReq.name,
        serviceAddressReq.address,
    )
}

data class OrderStatus(
    val orderId : String,
//    val positionId : String,
    val address : String,
    val status : String,
    val date : LocalDate
){
    constructor(orderId : String,orderReq: OrderReq) : this(
//        orderStatusReq.oderId,
//        serviceAddressReq.id,
        orderId,
        orderReq.address,
        "Đơn hàng đang được chuẩn bị",
        LocalDate.now()
    )

    private fun setStatus(address: String, position: String): String {
        if(position == address) {
            return "Đơn hàng đang được giao đến tay bạn"
        }
        else {
            return "Đã đến " + position
        }
    }

    fun update(serviceAddressReq: ServiceAddressReq) : OrderStatus {
        val today = LocalDate.now()
        return this.copy(
                orderId = this.orderId,
//                positionId = serviceAddressReq.id,
                status = setStatus(this.address, serviceAddressReq.name),
                date = today
        )
    }
}

data class UserDemo(
    val name : String,
    val age : Int
)