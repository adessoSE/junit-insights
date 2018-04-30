package de.adesso.junitinsights.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "junitinsights.report")
object JUnitInsightsReportProperties {
    var path: String = ""
    val templatepath: String = "/htmlTemplate.html"
}