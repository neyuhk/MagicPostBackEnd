package com.example.demo.service

import com.example.demo.model.OrderResp
import com.example.demo.model.ServiceAddressReq
import com.example.demo.model.ServiceAddressResp

interface ServiceAddressService {
    fun addServiceAddress(serviceAddressReq: ServiceAddressReq) : ServiceAddressResp
    fun listServiceAddress() : List<ServiceAddressResp>
    fun getServiceAddress(id : String) : ServiceAddressResp
}