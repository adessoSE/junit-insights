package de.adesso.junitinsights.listener

import de.adesso.junitinsights.repositories.ApplicationContextEventType
import de.adesso.junitinsights.services.ApplicationContextEventService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import javax.annotation.Resource

@Component
class SpringContextListener {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    lateinit var applicationContextEventService: ApplicationContextEventService

    @EventListener(ContextRefreshedEvent::class)
    fun catchContextStart(event: ContextRefreshedEvent) {
        log.info("ContextRefreshedEvent Received \n ApplicationContext was initialized")
        log.info("AppContextId: ${event.applicationContext.id}")
        //TODO Check if first init before closeing initial, so that its not a refresh
        applicationContextEventService.putEventIntoDatabaseNow(applicationContextEventType = ApplicationContextEventType.APP_CONTEXT_START, context = null)
        log.info("ApplicationContextEvents in Database: \n ${applicationContextEventService.findAll()}")
    }

    @EventListener(ContextClosedEvent::class)
    fun catchContextEnd(event: ContextClosedEvent) {
        log.info("ContextClosedEvent Received \n ApplicationContext was closed")
        log.info("AppContextId: ${event.applicationContext.id}")
        applicationContextEventService.putEventIntoDatabaseNow(applicationContextEventType = ApplicationContextEventType.APP_CONTEXT_END, context = null)
        log.info("ApplicationContextEvents in Database: \n ${applicationContextEventService.findAll()}")
    }
}
