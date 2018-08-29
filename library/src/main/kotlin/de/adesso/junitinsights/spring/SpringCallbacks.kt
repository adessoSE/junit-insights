package de.adesso.junitinsights.spring

import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.EventLog
import de.adesso.junitinsights.tools.TimestampWriter
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener

/**
 * Listens to the spring-context-events to register starting and stopping of the context
 */
class SpringCallbacks {
    @EventListener(ContextRefreshedEvent::class)
    fun onContextStart(event: ContextRefreshedEvent) = EventLog.log(Event("context refreshed"))

    @EventListener(ContextClosedEvent::class)
    fun onContextEnd(event: ContextClosedEvent) = EventLog.log(Event("context closed"))
}
