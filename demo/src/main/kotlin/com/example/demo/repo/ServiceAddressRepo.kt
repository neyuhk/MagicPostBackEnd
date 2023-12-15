package com.example.demo.repo

import com.example.demo.model.ServiceAddress

interface ServiceAddressRepo {
    fun addServiceAddress(serviceAddress: ServiceAddress) : ServiceAddress
    fun getServiceAddress() : List<ServiceAddress>
    fun getServiceAddressById(id : String) : ServiceAddress
}