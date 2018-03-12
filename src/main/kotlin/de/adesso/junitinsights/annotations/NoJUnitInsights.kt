package de.adesso.junitinsights.annotations

/**
 * Annotate the functions you dont want to have insights about with this and they wont be benchmarked.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class NoJUnitInsights