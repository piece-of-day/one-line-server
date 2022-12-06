package kr.pieceofday.onelineserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello(): String? {
        return "hello"
    }

    @GetMapping("/")
    fun testMain(): String? {
        return ""
    }
}