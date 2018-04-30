package de.adesso.junitinsights.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "junitinsights.report")
object JUnitInsightsReportProperties {
    var path: String = ""
    val templatepath: String = "/htmlTemplate.html"
}