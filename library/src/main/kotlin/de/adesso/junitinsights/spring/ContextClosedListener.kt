package de.adesso.junitinsights.spring

import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.EventLog
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import java.util.*

/**
 * Listens to the Spring ContextClosedEvent to register stopping of the context
 * This callback is registered to the Spring Context in the BenchmarkContextCustomizerFactory
 * @see BenchmarkContextCustomizerFactory
 */
@Suppress("unused", "UNUSED_PARAMETER")
class ContextClosedListener: ApplicationListener<ContextClosedEvent> {

    /**
     * Logs the closing of Spring Contexts during testing.
     * @see EventLog
     */
    override fun onApplicationEvent(event: ContextClosedEvent) {
        EventLog.log(Event("context closed"))
    }
}
