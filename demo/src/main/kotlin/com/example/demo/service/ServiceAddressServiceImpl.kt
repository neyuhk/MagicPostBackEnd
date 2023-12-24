package com.example.demo.service

import com.example.demo.model.OrderResp
import com.example.demo.model.ServiceAddress
import com.example.demo.model.ServiceAddressReq
import com.example.demo.model.ServiceAddressResp
import com.example.demo.repo.ServiceAddressRepoImpl
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Service
//@RequestMapping("api/v1/serviceAddress")
class ServiceAddressServiceImpl(
        val serviceAddressRepo : ServiceAddressRepoImpl
) : ServiceAddressService {
    override fun addServiceAddress(serviceAddressReq: ServiceAddressReq): ServiceAddressResp {
        val newServiceAddress = ServiceAddress(serviceAddressReq)
        return ServiceAddressResp(serviceAddressRepo.addServiceAddress(newServiceAddress))
    }

    override fun getServiceAddress(id: String): ServiceAddressResp {
        return ServiceAddressResp(serviceAddressRepo.getServiceAddressById(id))
    }

    override fun listServiceAddress(): List<ServiceAddressResp> {
        val serviceAddress = serviceAddressRepo.getServiceAddress().map {serviceAddress -> ServiceAddressResp(serviceAddress)}
        return serviceAddress
    }
}