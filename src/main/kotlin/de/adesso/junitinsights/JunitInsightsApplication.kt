package de.adesso.junitinsights

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.Resource

@SpringBootApplication
class JunitInsightsApplication

fun main(args: Array<String>) {
    runApplication<JunitInsightsApplication>(*args)
}
