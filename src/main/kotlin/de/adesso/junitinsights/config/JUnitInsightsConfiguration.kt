package de.adesso.junitinsights.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "junitinsights")
object JUnitInsightsConfiguration {

    val reportPath: String = ""
    val reportTemplatePath: String = ""

}
