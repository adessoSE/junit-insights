package de.adesso.junitinsights

import de.adesso.junitinsights.extensions.SpringInsightExtension
import org.junit.jupiter.api.extension.ExtendWith

/**
 * We define a custom annotation that:
 * - stands in for '@Test' so that the method gets executed
 * - has the tag "integration" so we can filter by that,
 * e.g. when running tests from the command line
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(value = AnnotationRetention.RUNTIME)
@ExtendWith(SpringInsightExtension::class)
annotation class JUnitInsights