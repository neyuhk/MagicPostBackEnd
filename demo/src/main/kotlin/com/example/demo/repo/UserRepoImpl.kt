package com.example.demo.repo

import com.example.demo.model.User
import com.example.demo.model.UserReq
import org.springframework.beans.factory.annotation.Value
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.server.ResponseStatusException

@Component
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

    override fun updateUser(user: User): User {
        return transactionTemplate.execute { _ ->
            try {
                val query = Query()
                query.addCriteria(Criteria.where("id").isEqualTo(user.id))
                mongoTemplate.findAndRemove(query, User::class.java, userCol)
                mongoTemplate.save(user, userCol)
            } catch (e: DuplicateKeyException) {
                throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "User information is already exist"
                )
            }
            return@execute user
        }!!
    }

    override fun findUserById(id: String): User {
        return mongoTemplate.findById(id, User::class.java)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find user with id $id")

    }

    override fun findUserByEmail(email: String): User {
        val query = Query()
        query.addCriteria(Criteria.where("email").isEqualTo(email))
        val users = mongoTemplate.find(query, User::class.java)
        if (users.isNotEmpty() && users.size == 1) {
            return users[0]
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found user with email $email")
    }

    override fun findUserByUserName(username: String): User {
        val query = Query()
        query.addCriteria(Criteria.where("username").isEqualTo(username))
        val users = mongoTemplate.find(query, User::class.java)
        if (users.isNotEmpty() && users.size == 1) {
            return users[0]
        } else throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot found user with username $username")

    }

    override fun findUserByUsernameStartWith(username: String): List<User> {
        TODO("Not yet implemented")
    }
}