package com.example.demo.repo

import com.example.demo.model.User
import com.example.demo.model.UserReq

interface UserRepo {
    fun addManager(userReq: UserReq, serviceAddressId : String): User
    fun addEmployee(userReq: UserReq, serviceAddressId : String): User
    fun updateUser(user: User): User
    fun findUserById(id: String): User
    fun findUserByEmail(email: String): User
    fun findUserByUserName(username: String): User
    fun findUserByUsernameStartWith(username: String): List<User>
    fun countUserByServiceAddressId(serviceAddressId: String) : String
}