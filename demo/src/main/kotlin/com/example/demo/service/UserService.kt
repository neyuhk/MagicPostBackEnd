package com.example.demo.service

import com.example.demo.model.LoginReq
import com.example.demo.model.LoginResp
import com.example.demo.model.UserReq
import com.example.demo.model.UserResp
import reactor.core.publisher.Mono

interface UserService {
    fun createManager(userReq: UserReq, serviceAddressId : String): Mono<UserResp>
    fun createEmployee(userReq: UserReq, serviceAddressId : String) : Mono<UserResp>
    fun authenticate(loginReq: LoginReq): Mono<LoginResp>
    fun updateUser(userReq: UserReq, userId: String): UserResp
    fun getCurrentUser(): Mono<UserResp>
    fun getUserById(id: String): Mono<UserResp>
    fun searchUser(): Mono<List<UserResp>>
}