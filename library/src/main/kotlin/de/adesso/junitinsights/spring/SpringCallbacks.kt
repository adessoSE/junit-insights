package de.adesso.junitinsights.spring

import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.EventLog
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.util.*

/**
 * Listens to the spring-context-events to register starting and stopping of the context
 */
class SpringCallbacks {
    @EventListener(ContextRefreshedEvent::class)
    fun onContextRefresh(event: ContextRefreshedEvent) = EventLog.log(Event("context refreshed", Date()))

    @EventListener(ContextClosedEvent::class)
    fun onContextClose(event: ContextClosedEvent) = EventLog.log(Event("context closed", Date()))
}
