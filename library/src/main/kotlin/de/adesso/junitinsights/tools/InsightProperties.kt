package de.adesso.junitinsights.tools

import org.junit.jupiter.api.extension.ExtensionContext

object InsightProperties {
    private var configurationSet = false
    var reportpath: String = ""
    var templatepath: String = "/htmlTemplate.html"

    fun setConfiguration(context: ExtensionContext) {
        if (configurationSet)
            return
        reportpath = context.getConfigurationParameter("de.adesso.junitinsights.reportpath").orElse("")
        templatepath = context.getConfigurationParameter("de.adesso.junitinsights.templatepath").orElse("/htmlTemplate.html")
        configurationSet = true
    }
}