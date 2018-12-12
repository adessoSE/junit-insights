package de.adesso.junitinsights.tools

import org.junit.jupiter.api.extension.ExtensionContext

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
