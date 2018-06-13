package de.adesso.junitinsights.autoconfiguration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "junitinsights.report")
object InsightReportProperties {
    var path: String = ""
    val templatepath: String = "/htmlTemplate.html"
}