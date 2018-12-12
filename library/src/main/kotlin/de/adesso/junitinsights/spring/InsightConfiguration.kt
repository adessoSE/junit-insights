package de.adesso.junitinsights.spring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Registers the SpringCallbacks when the context is created.
 * @see SpringCallbacks
 */
@Configuration
class InsightConfiguration {
    @Bean
    fun springContextListener(): SpringCallbacks {
        return SpringCallbacks()
    }
}
