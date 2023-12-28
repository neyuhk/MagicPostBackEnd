package com.example.demo.repo

import com.example.demo.model.BossResp

interface BossRepo {
    fun getBossResp() : List<BossResp>
}