package com.example.demo.service

import com.example.demo.model.Order
import com.example.demo.model.ServiceAddressResp
import com.example.demo.model.Upcoming
import com.example.demo.model.UpcomingResp
import com.example.demo.repo.UpcomingRepo
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
//@RequestMapping("api/v1/manager")
class UpcomingServiceImpl(
        val upcomingRepo: UpcomingRepo
) : UpcomingService {
    override fun addUpcoming(serviceAddressId: String, order : Order): UpcomingResp {
        val newUpcoming = upcomingRepo.createUpcoming(Upcoming(serviceAddressId,order))
        return UpcomingResp(newUpcoming)
    }

    override fun deleteUpcoming(orderId: String) {
        upcomingRepo.deleteUpcoming(orderId)
    }

    override fun listComing(serviceAddressId: String): List<UpcomingResp> {
        val coming = upcomingRepo.ListComing(serviceAddressId).map {coming -> UpcomingResp(coming) }
        return coming
    }
}