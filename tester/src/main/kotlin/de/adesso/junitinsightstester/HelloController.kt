package de.adesso.junitinsightstester

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * A simple example controller used in the example tests.
 */
@RestController
class HelloController {

    @RequestMapping("/")
    fun index(): String {
        return "Greetings from Spring Boot!"
    }
}