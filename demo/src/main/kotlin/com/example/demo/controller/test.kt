package com.example.demo.controller

import com.example.demo.repo.DemoRepo
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class testController(
    val demoRepo: DemoRepo
){
    @GetMapping("/test")
    fun createTest():String{
        demoRepo.demo()
        return "test thoyyy"
    }

    @PostMapping("/test", MediaType.MULTIPART_FORM_DATA_VALUE)
    fun postTest(@ModelAttribute name : String):String{
        return "he lu " + name
    }
}