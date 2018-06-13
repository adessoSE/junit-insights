package de.adesso.junitinsights.autoconfiguration

import de.adesso.junitinsights.spring.ContextListener
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(InsightReportProperties::class)
class InsightConfiguration {
    @Bean
    fun springContextListener(): ContextListener {
        return ContextListener()
    }
}
