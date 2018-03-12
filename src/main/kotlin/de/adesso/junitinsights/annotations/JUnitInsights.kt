package de.adesso.junitinsights.annotations

import de.adesso.junitinsights.tests.TestBenchmarkExtension
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Annotate your testclasses with this method to activate Junit-insights.
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(value = AnnotationRetention.RUNTIME)
@ExtendWith(TestBenchmarkExtension::class)
annotation class JUnitInsights