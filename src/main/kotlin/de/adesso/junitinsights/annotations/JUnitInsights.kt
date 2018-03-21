package de.adesso.junitinsights.annotations

import de.adesso.junitinsights.tests.TestBenchmarkExtension
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Annotate your test classes with this method to activate JUnit-insights.
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(value = AnnotationRetention.RUNTIME)
@ExtendWith(TestBenchmarkExtension::class)
annotation class JUnitInsights