package com.example.demo.controller

import com.example.demo.model.LoginReq
import com.example.demo.model.LoginResp
import com.example.demo.model.UserReq
import com.example.demo.model.UserResp
import com.example.demo.service.UserServiceImpl
import org.springframework.http.MediaType

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/user")
class UserController(val userService: UserServiceImpl) {
    @PostMapping("/register/{serviceAddressId}")
    fun createManager(
            @PathVariable serviceAddressId : String,
            @RequestBody userReq: UserReq): Mono<UserResp> {
        return userService.createManager(userReq, serviceAddressId)
    }

    @PostMapping("/register/employee/{serviceAddressId}")
    fun createEmployee(
            @PathVariable serviceAddressId : String,
            @RequestBody userReq: UserReq): Mono<UserResp> {
        return userService.createEmployee(userReq, serviceAddressId)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginReq: LoginReq): Mono<LoginResp> {
        return userService.authenticate(loginReq)
    }

    @PutMapping("/{userId}", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun updateUser(@RequestBody userReq: UserReq, @PathVariable userId: String): Mono<UserResp> {
        return Mono.just(userService.updateUser(userReq, userId))
    }

    @GetMapping
    fun getCurrentUser(): Mono<UserResp> {
        return userService.getCurrentUser()
    }

    @PostMapping("/refresh-token")
    fun refreshToken() {

    }

    @GetMapping("/employee/{serviceAddressId}")
    fun getEmployee(
            @PathVariable serviceAddressId: String
    ): List<UserResp> {
        return userService.getListEmployee(serviceAddressId)
    }
}