package com.example.demo.repo

import com.example.demo.model.User
import com.example.demo.model.UserReq
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException

class UserRepoImpl(
        private val mongoTemplate : MongoTemplate,
        val transactionTemplate: TransactionTemplate,
        @Value("\${data.mongodb.table.user}") val userCol: String
):UserRepo {
    override fun addUser(userReq: UserReq): User {
        val newUser = User.newUser(userReq)
        return transactionTemplate.execute { _ ->
            try {
                mongoTemplate.insert(newUser, userCol)
            } catch (e: DuplicateKeyException) {
                throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "User information is already exist"
                )
            }
            return@execute newUser
        }!!
    }
}