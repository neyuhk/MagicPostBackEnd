package com.example.demo.controller

import com.example.demo.model.OrderReq
import com.example.demo.model.OrderResp
import com.example.demo.model.ServiceAddressReq
import com.example.demo.model.ServiceAddressResp
import com.example.demo.service.ServiceAddressServiceImpl
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/serviceAddress")
class ServiceAddress(val serviceAddressServiceImpl: ServiceAddressServiceImpl) {
    @PostMapping( "",MediaType.MULTIPART_FORM_DATA_VALUE)
    fun createServiceAddress(@ModelAttribute serviceAddressReq: ServiceAddressReq): ServiceAddressResp {
        return serviceAddressServiceImpl.addServiceAddress(serviceAddressReq)
    }

    @GetMapping()
    fun getServiceAddress() : List<ServiceAddressResp>{
        return serviceAddressServiceImpl.listServiceAddress()
    }
}