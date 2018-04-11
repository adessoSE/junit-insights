package de.adesso.junitinsights.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@EnableConfigurationProperties(JUnitInsightsReportProperties::class)
@PropertySource("classpath:junitinsights.properties")
@ComponentScan(basePackages = arrayOf("de.adesso.junitinsights"))
class JUnitInsightsConfiguration



