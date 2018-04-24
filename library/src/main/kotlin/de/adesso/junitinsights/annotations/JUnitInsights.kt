package de.adesso.junitinsights.annotations

import de.adesso.junitinsights.tests.TestBenchmarkExtension
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Annotate your test classes with this method to activate JUnit-Insights for the given class.
 *
 * Now all methods will be measured, except the methods thats contain the NoJUnitInsights-Annotation.
 * @see NoJUnitInsights
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(value = AnnotationRetention.RUNTIME)
@ExtendWith(TestBenchmarkExtension::class)
annotation class JUnitInsights