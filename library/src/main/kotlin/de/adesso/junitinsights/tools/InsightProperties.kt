package de.adesso.junitinsights.tools

import org.junit.jupiter.api.extension.ExtensionContext

object InsightProperties {
    private var configurationSet = false
    var reportpath: String = ""
    var templates: List<String>? = null

    fun setConfiguration(context: ExtensionContext) {
        if (configurationSet)
            return
        reportpath = context.getConfigurationParameter("de.adesso.junitinsights.reportpath")
                .orElse("")

        templates = context.getConfigurationParameter("de.adesso.junitinsights.templatepath")
                .orElse("variables.js,utility.js,dataProcessing.js,chartUtility.js,style.css,htmlTemplate.html")
                .split(",")

        configurationSet = true
    }
}