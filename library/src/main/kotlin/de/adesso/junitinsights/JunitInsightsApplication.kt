package de.adesso.junitinsights

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * JUnit Insights is an extension for the use of JUnit 5 in combination with the Spring framework, which
 * measures the time for setup, execution and teardown for each test method in each test class
 * and creates a nice looking report that visualizes the data.
 *
 * Background: When building integration tests with Spring (e.g. with @SpringBootTest),
 * sometimes a Spring application context has to be started and sometimes it doesn't. For the user of the
 * test classes, it looks like some tests take a long time to execute, although the actual test runs fairly
 * quickly. To make this behavior transparent, a report is created.
 */
@SpringBootApplication
class JunitInsightsApplication

fun main(args: Array<String>) {
    runApplication<JunitInsightsApplication>(*args)
}
