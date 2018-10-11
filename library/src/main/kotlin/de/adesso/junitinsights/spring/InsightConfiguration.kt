package de.adesso.junitinsights.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InsightConfiguration {
    @Bean
    fun springContextListener(): SpringCallbacks {
        return SpringCallbacks()
    }
}
