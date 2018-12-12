package de.adesso.junitinsights.tools

import org.junit.jupiter.api.extension.ExtensionContext

/**
 * Global properties for JUnit Insights.
 * Properties are read from the Gradle or Maven build files at runtime by JUnitCallbacks
 * @see de.adesso.junitinsights.junit.JUnitCallbacks.beforeAll
 */
object InsightProperties {
    internal var configurationSet = false
    var reportpath: String = "build/reports/"
    var enabled: Boolean = false

    fun setConfiguration(context: ExtensionContext) {
        if (configurationSet)
            return

        reportpath = context.getConfigurationParameter("de.adesso.junitinsights.reportpath")
                .orElse("build/reports/")

        enabled = context.getConfigurationParameter("de.adesso.junitinsights.enabled")
                .orElse("false")!!
                .toBoolean()

        configurationSet = true
    }
}
