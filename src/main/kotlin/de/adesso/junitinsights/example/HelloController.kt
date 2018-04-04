package de.adesso.junitinsights.example

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * A simple controller used in the tests.
 */
@RestController
class HelloController {

    @RequestMapping("/")
    fun index(): String {
        return "Greetings from Spring Boot!"
    }
}