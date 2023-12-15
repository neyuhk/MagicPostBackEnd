package com.example.demo.repo

import com.example.demo.model.UserDemo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class DemoRepoImpl(
        val mongoTemplate: MongoTemplate
):DemoRepo {
    override fun demo() {
        val user : UserDemo = UserDemo("Huyn", 20)
        mongoTemplate.save(user,"name")
    }
}