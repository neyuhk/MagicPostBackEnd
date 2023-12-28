package com.example.demo.service

import com.example.demo.model.BossResp
import com.example.demo.repo.BossRepo
import org.springframework.stereotype.Service

@Service
class BossServiceImpl(
        val bossRepo: BossRepo
) : BossService {
    override fun getListBossResp(): List<BossResp> {
        return bossRepo.getBossResp()
    }
}