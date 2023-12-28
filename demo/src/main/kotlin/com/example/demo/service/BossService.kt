package com.example.demo.service

import com.example.demo.model.BossResp

interface BossService {
    fun getListBossResp() : List<BossResp>
}