package de.adesso.junitinsightstester

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Project to integrate the junit-insights application testwise.
 *
 * The JUnit-Insights-Application is provided as a gradle dependency,
 * so you can test it with some JUnit 5 tests and to see how it can be used.
 */
@SpringBootApplication
class JUnitInsightsTester

fun main(args: Array<String>) {
    runApplication<JUnitInsightsTester>(*args)
}
