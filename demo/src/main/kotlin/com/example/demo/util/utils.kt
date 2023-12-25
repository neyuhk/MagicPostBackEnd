package com.example.demo.util
import com.example.demo.service.ServiceAddressService
import com.example.demo.service.UserService
import com.example.demo.service.UserServiceImpl
import com.fasterxml.uuid.Generators

class utils{
    companion object {
        fun newUUID(): String {
            return Generators.timeBasedGenerator().generate().toString()
        }
    }
}