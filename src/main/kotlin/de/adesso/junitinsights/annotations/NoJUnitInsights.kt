package de.adesso.junitinsights.annotations

/**
 * Annotate the functions in a testclass annotated with @JUnitInsights
 * you don't want to have insights about with this annotation and they won't be measured.
 * @see JUnitInsights
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class NoJUnitInsights