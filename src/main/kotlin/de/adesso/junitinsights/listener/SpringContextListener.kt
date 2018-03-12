package de.adesso.junitinsights.listener

import de.adesso.junitinsights.repositories.EventType
import de.adesso.junitinsights.services.TimestampEventService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.*
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class SpringContextListener {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    lateinit var timestampEventService: TimestampEventService

    @EventListener(ContextRefreshedEvent::class)
        fun catchContextStart(event: ContextRefreshedEvent){
        log.info("ContextRefreshedEvent Received \n ApplicationContext was initialized")
        log.info("AppContextId: ${event.applicationContext.id}")
        //TODO Check if first init before closeing initial, so that its not a refresh
        timestampEventService.createEventNow(eventType = EventType.APP_CONTEXT_START, context = null)
    }

    @EventListener(ContextClosedEvent::class)
    fun catchContextEnd(event: ContextClosedEvent){
        log.info("ContextClosedEvent Received \n ApplicationContext was closed")
        log.info("AppContextId: ${event.applicationContext.id}")
        timestampEventService.createEventNow(eventType = EventType.APP_CONTEXT_END, context = null)

    }

}
