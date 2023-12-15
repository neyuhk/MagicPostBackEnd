package com.example.demo.repo

import com.example.demo.model.User
import com.example.demo.model.UserReq
import com.example.demo.model.UserResp

interface UserRepo {
    fun addUser(userReq : UserReq) : User
}