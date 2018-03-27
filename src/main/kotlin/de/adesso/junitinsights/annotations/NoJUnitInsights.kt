package de.adesso.junitinsights.annotations

/**
 * Annotate the functions you don't want to have insights about with this and they won't be benchmarked.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class NoJUnitInsights