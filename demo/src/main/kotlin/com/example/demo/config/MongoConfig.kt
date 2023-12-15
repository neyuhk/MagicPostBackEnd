package com.example.demo.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ReadConcern
import com.mongodb.WriteConcern
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoConfig(
        @Value("\${data.mongodb.uri}") val mongoUri: String,
        @Value("\${data.mongodb.database:#{'morax'}}") val dbName: String
) {

    @Bean
    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
        return MongoTransactionManager(dbFactory)
    }

    fun mongoClient(mongoUri: String): MongoClient {
        val connectionStr = ConnectionString(mongoUri)
        val mongoClientSettings = MongoClientSettings.builder()
                .retryWrites(true)
                .applyConnectionString(connectionStr)
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .readConcern(ReadConcern.AVAILABLE)
                .build()
        return MongoClients.create(mongoClientSettings)
    }

    fun mongoTemplate(dbName: String, mongoUri: String): MongoTemplate {
        val template = MongoTemplate(mongoClient(mongoUri), dbName)
//        template.setReadPreference(ReadPreference.secondaryPreferred())
        template.setWriteConcern(WriteConcern.MAJORITY)
        return template
    }

    @Bean
    @Primary
    fun mongoTemplate(): MongoTemplate {
        return this.mongoTemplate(this.dbName, this.mongoUri)
    }

}