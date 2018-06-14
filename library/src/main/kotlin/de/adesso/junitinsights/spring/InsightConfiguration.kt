package de.adesso.junitinsights.spring

import de.adesso.junitinsights.spring.ContextListener
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InsightConfiguration {
    @Bean
    fun springContextListener(): ContextListener {
        return ContextListener()
    }
}
