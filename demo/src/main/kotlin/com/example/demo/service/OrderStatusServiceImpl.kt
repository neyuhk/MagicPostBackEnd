package com.example.demo.service

import com.example.demo.model.*
import com.example.demo.repo.OrderRepoImpl
import com.example.demo.repo.OrderStatusRepoImpl
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("api/v1/status")
class OrderStatusServiceImpl(
        val orderStatusRepoImpl: OrderStatusRepoImpl,
        val orderRepoImpl: OrderRepoImpl
) : OrderStatusService {
//    override fun createStatus(orderStatusReq: OrderStatusReq, serviceAddressReq: ServiceAddressReq, orderReq: OrderReq): OrderStatusResp {
//        val newOrderStatus = OrderStatus(orderStatusReq, serviceAddressReq, orderReq)
//        return OrderStatusResp(orderStatusRepoImpl.addOrderStatus(newOrderStatus))
//    }
    override fun updateOrderStatus(orderStatusResp: OrderStatusResp): OrderResp {
        val newOrder = orderRepoImpl.updateStatus(orderStatusResp)
        val orderResp = OrderResp(newOrder)
        return orderResp
    }

    override fun updateStatus(id : String,serviceAddressReq: ServiceAddressReq): OrderStatusResp {
        val newStatus = orderStatusRepoImpl.updateStatus(id, serviceAddressReq)
        val orderStatusResp = OrderStatusResp(newStatus)
        updateOrderStatus(orderStatusResp)
        return orderStatusResp
    }

//    override fun getOrderStatusById(orderId: String): OrderStatusResp {
//        return OrderStatusResp(orderStatusRepoImpl.getStatusById(orderId))
//    }
    override fun getOrderStatusById(orderId: String): OrderStatusResp {
        return OrderStatusResp(orderStatusRepoImpl.getStatusById(orderId))
    }

    override fun listStatus(): List<OrderStatusResp> {
        val ordersStatus = orderStatusRepoImpl.getListStatus().map { orderStatus -> OrderStatusResp(orderStatus) }
        return ordersStatus
    }

    override fun listStatusById(orderId: String): List<OrderStatusResp> {
        val orderStatusById = orderStatusRepoImpl.getListStatusById(orderId).map { orderStatus -> OrderStatusResp(orderStatus) }
        return orderStatusById
    }


}