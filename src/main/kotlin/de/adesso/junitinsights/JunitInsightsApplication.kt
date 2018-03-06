package de.adesso.junitinsights

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.Resource

@SpringBootApplication
open class JunitInsightsApplication {
    //@Resource
    //lateinit var repository: EventRepository

    fun test() {

    }
}

fun main(args: Array<String>) {
    runApplication<JunitInsightsApplication>(*args)
}
