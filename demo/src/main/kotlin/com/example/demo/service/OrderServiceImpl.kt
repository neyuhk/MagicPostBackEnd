package com.example.demo.service

import com.example.demo.model.*
import com.example.demo.repo.OrderRepoImpl
import com.example.demo.repo.OrderStatusRepoImpl
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
//@RequestMapping("api/v1/order")
class OrderServiceImpl(
    val orderRepoImpl: OrderRepoImpl,
    val orderStatusRepoImpl: OrderStatusRepoImpl,
    val orderStatusServiceImpl: OrderStatusServiceImpl
) : OrderService {
    override fun addOrder(orderReq: OrderReq): OrderResp {
        val newOrder = Order(orderReq)
//        val newOrderStatus = OrderStatus(newOrder)
        createStatus(newOrder.id,orderReq)
//        orderStatusRepoImpl.addOrderStatus(newOrderStatus)
        return OrderResp(orderRepoImpl.addOrder(newOrder))
    }

    override fun createStatus(orderId : String, orderReq: OrderReq): OrderStatusResp {
        val newOrderStatus = OrderStatus(orderId, orderReq)
        return OrderStatusResp(orderStatusRepoImpl.addOrderStatus(newOrderStatus))
    }

    override fun listOrder(): List<OrderResp> {
        val orders = orderRepoImpl.getOrder().map { order -> OrderResp(order) }
        return orders
    }

    override fun getOrder(id: String): OrderResp {
        return OrderResp(orderRepoImpl.orderById(id))
    }

    override fun getOrderStatus(orderId: String): OrderStatusResp {
        return orderStatusServiceImpl.getOrderStatusById(orderId)
    }



//    override fun updateOrderStatus(orderStatusResp: OrderStatusResp): OrderResp {
//        val newOrder = orderRepoImpl.updateStatus(orderStatusResp)
//        val orderResp = OrderResp(newOrder)
//        return orderResp
//    }

//    override fun updateOrderStatus(orderStatusReq: OrderStatusReq): OrderResp {
//        val newOrder = orderRepoImpl.updateStatus(orderStatusReq)
//        val orderResp = OrderResp(newOrder)
//        return orderResp
//    }
}