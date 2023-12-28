package com.example.demo.repo

import com.example.demo.model.BossResp
import com.example.demo.model.User
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

@Component
class BossRepoImpl(
        val mongoTemplate: MongoTemplate,
        val serviceAddressRepoImpl: ServiceAddressRepoImpl,
        val userRepoImpl: UserRepoImpl,
        val orderHereRepoImpl: OrderHereRepoImpl
) : BossRepo {
    override fun getBossResp(): List<BossResp> {
        var listBossResp : MutableList<BossResp> = mutableListOf()
        val listServiceAddress = serviceAddressRepoImpl.getServiceAddress()

        for(serviceAddress in listServiceAddress){
            val serviceAddressId = serviceAddress.id
                println(serviceAddressId)
                val user : User = userRepoImpl.findUserByServiceAddressId(serviceAddressId)
                val countUser : String = userRepoImpl.countUserByServiceAddressId(serviceAddressId)
                val countOrder : Int = orderHereRepoImpl.ListHere(serviceAddressId).size
                val bossResp = BossResp(serviceAddress, user, countUser, countOrder)
                listBossResp.add(bossResp)
        }
        return listBossResp
    }
}