package com.example.demo.controller

import com.example.demo.model.BossResp
import com.example.demo.model.IdReq
import com.example.demo.model.OrderResp
import com.example.demo.service.BossService
import com.example.demo.service.BossServiceImpl
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/boss")
class BossController(
        val bossServiceImpl: BossServiceImpl
) {
    @GetMapping()
    fun listComing() : List<BossResp> {
        val listData = bossServiceImpl.getListBossResp()
        return listData
    }
}