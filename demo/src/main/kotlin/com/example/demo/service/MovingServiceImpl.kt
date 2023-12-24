package com.example.demo.service

import com.example.demo.model.Moving
import com.example.demo.model.MovingResp
import com.example.demo.model.Order
import com.example.demo.model.UpcomingResp
import com.example.demo.repo.MovingRepo
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
//@RequestMapping("api/v1/manager")
class MovingServiceImpl(
        val movingRepo : MovingRepo
) : MovingService {
    override fun addMoving(serviceAddressId: String, order: Order): MovingResp {
        val newMove = movingRepo.createMoving(Moving(serviceAddressId, order))
        return MovingResp(newMove)
    }

    override fun deleteMoving(orderId: String) {
        movingRepo.deleteMove(orderId)
    }

    override fun listMove(serviceAddressId: String): List<MovingResp> {
        val moving = movingRepo.ListMove(serviceAddressId).map {moving -> MovingResp(moving) }
        return moving
    }
}