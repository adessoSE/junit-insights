package de.adesso.junitinsights.spring

import de.adesso.junitinsights.tools.TimestampWriter
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener

/**
 * Listens to the spring-context-events to register starting and stopping of the context
 */
class ContextListener {
    private val timestampWriter = TimestampWriter

    /**
     * Listens for ContextRefreshedEvent
     * which appears when the context is updated or refreshed.
     * @see ContextRefreshedEvent
     */
    @EventListener(ContextRefreshedEvent::class)
    fun catchContextStart(event: ContextRefreshedEvent) {
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "context refreshed",
                "", "")
    }

    /**
     * Listens for ContextClosedEvent
     * which appears when the context is closed.
     * @see ContextClosedEvent
     */
    @EventListener(ContextClosedEvent::class)
    fun catchContextEnd(event: ContextClosedEvent) {
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "context closed",
                "", "")
    }
}
