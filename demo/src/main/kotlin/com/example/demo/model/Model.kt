package com.example.demo.model

import com.example.demo.util.utils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

interface DemoIndentity{
    val id : String
    val username : String
    val email : String
    val password : String
    val phone_number : String
    val role : Role
}

data class User(
        override var id: String = "",
        override val username: String = "",
        override val email: String = "",
        override val password: String = "",
        override val phone_number : String = "",
        override val role: Role = Role.USER,

        ):DemoIndentity{
    companion object{
        var currentUser : User = User()
        fun newUser(userReq: UserReq): User {
            val id = utils.newUUID()
            return User(
                    id,
                    username = userReq.username,
                    email = userReq.email,
                    password = userReq.password,
                    phone_number = userReq.phone_number,
                    role = Role.USER
            )
        }

    }
    fun update(userReq: UserReq): User {
        return User(
                this.id,
                userReq.username,
                userReq.email,
                userReq.password,
                userReq.phone_number
        )
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

enum class Role(emptySet: MutableSet<Permission>) {
    USER(Collections.emptySet()),
    MANAGER(
            setOf(
                    Permission.ACCOUNT_USER,
                    Permission.ACCOUNT_STAFF,
                    Permission.ACCOUNT_MANAGE,
                    Permission.ACCOUNT_WAREHOUSE_STAFF,
                    Permission.ACCOUNT_WAREHOUSE_STAFF,
                    Permission.ACCOUNT_WAREHOUSE_STAFF_MANAGE,
                    Permission.ACCOUNT_BOSS
            ).toMutableSet()
    );

    private val permissions: Set<Permission> = setOf()
    val authorities: List<SimpleGrantedAuthority>
        get() {
            val authorities = permissions
                    .stream()
                    .map { permission -> SimpleGrantedAuthority(permission.permission) }
                    .collect(Collectors.toList())
            authorities.add(SimpleGrantedAuthority("ROLE_$name"))
            return authorities

        }
}

enum class Permission(val permission: String) {
    ACCOUNT_USER("account:user"),
    ACCOUNT_STAFF("account:staff"),
    ACCOUNT_MANAGE("account:manage"),
    ACCOUNT_WAREHOUSE_STAFF("account:warehouse_staff"),
    ACCOUNT_WAREHOUSE_STAFF_MANAGE("account:warehouse_staff_manage"),
    ACCOUNT_BOSS("account:boss");
}

data class Token(
        val id: String,
        val token: String,
        var revoked: Boolean = false,
        var expired: Boolean = false,
        val userId: String
)