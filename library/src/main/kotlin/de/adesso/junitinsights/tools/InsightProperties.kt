package de.adesso.junitinsights.tools

import org.junit.jupiter.api.extension.ExtensionContext

object InsightProperties {
    var configurationSet = false
    var reportpath: String = ""
    var enabled: Boolean = false

    fun setConfiguration(context: ExtensionContext) {
        if (configurationSet)
            return

        reportpath = context.getConfigurationParameter("de.adesso.junitinsights.reportpath")
                .orElse("")

        enabled = context.getConfigurationParameter("de.adesso.junitinsights.enabled")
                .orElse("false")!!
                .toBoolean()

        configurationSet = true
    }
}
