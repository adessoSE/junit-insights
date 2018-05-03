package de.adesso.junitinsights.config

import de.adesso.junitinsights.listener.SpringContextListener
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JUnitInsightsReportProperties::class)
class JUnitInsightsConfiguration {
    @Bean
    fun springContextListener(): SpringContextListener {
        return SpringContextListener()
    }
}
