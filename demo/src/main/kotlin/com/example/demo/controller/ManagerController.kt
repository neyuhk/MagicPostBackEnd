package com.example.demo.controller

import com.example.demo.model.MovingResp
import com.example.demo.model.OrderHereResp
import com.example.demo.model.UpcomingResp
import com.example.demo.service.MovingServiceImpl
import com.example.demo.service.OrderHereServiceImpl
import com.example.demo.service.UpcomingServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@RestController
//@RequestMapping("api/v1/manager")
class ManagerController(
        val upcomingServiceImpl: UpcomingServiceImpl,
        val orderHereServiceImpl: OrderHereServiceImpl,
        val movingServiceImpl: MovingServiceImpl
) {
    @GetMapping("/coming")
    fun listComing(@ModelAttribute serviceAddreddId : String) : List<UpcomingResp> {
        return upcomingServiceImpl.listComing(serviceAddreddId)
    }

    @GetMapping("/here")
    fun listHere(@ModelAttribute serviceAddreddId : String) : List<OrderHereResp> {
        return orderHereServiceImpl.listHere(serviceAddreddId)
    }

    @GetMapping("/moving")
    fun listMoving(@ModelAttribute serviceAddreddId : String) : List<MovingResp> {
        return movingServiceImpl.listMove(serviceAddreddId)
    }
}