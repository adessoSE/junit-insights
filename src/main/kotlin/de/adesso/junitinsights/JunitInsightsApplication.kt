package de.adesso.junitinsights

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JunitInsightsApplication

fun main(args: Array<String>) {
    runApplication<JunitInsightsApplication>(*args)
}
