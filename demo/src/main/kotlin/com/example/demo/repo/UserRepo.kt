package com.example.demo.repo

import com.example.demo.model.User
import com.example.demo.model.UserReq
import com.example.demo.model.UserResp

interface UserRepo {
    fun addUser(userReq: UserReq): User
    fun updateUser(user: User): User
    fun findUserById(id: String): User
    fun findUserByEmail(email: String): User
    fun findUserByUserName(username: String): User
    fun findUserByUsernameStartWith(username: String): List<User>
}