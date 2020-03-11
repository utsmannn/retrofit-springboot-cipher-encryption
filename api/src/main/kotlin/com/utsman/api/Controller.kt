package com.utsman.api

import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class Controller {

    @GetMapping("/hello")
    fun hello(): String {
        val responses = Responses("ok", "hello")
        return responses.encrypt()
    }

    @PostMapping("/hello_post")
    fun helloPost(@RequestParam("hello") hello: String): String {

        val decryptHello = hello.decrypt().replace("\"", "")
        println(" -- \nrawParam -> $hello \ndecryptParam $decryptHello")

        val responses = Responses("ok", decryptHello)
        println("response decrypt -> $responses")
        return responses.encrypt()
    }
}