package de.adesso.junitinsights.listener

import de.adesso.junitinsights.repositories.ApplicationContextEventType
import de.adesso.junitinsights.services.ApplicationContextEventService
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
    lateinit var applicationContextEventService: ApplicationContextEventService

    @EventListener(ContextRefreshedEvent::class)
        fun catchContextStart(event: ContextRefreshedEvent){
        log.info("ContextRefreshedEvent Received \n ApplicationContext was initialized")
        log.info("AppContextId: ${event.applicationContext.id}")
        //TODO Check if first init before closeing initial, so that its not a refresh
        applicationContextEventService.createEventNow(applicationContextEventType = ApplicationContextEventType.APP_CONTEXT_START, context = null)
    }

    @EventListener(ContextClosedEvent::class)
    fun catchContextEnd(event: ContextClosedEvent){
        log.info("ContextClosedEvent Received \n ApplicationContext was closed")
        log.info("AppContextId: ${event.applicationContext.id}")
        applicationContextEventService.createEventNow(applicationContextEventType = ApplicationContextEventType.APP_CONTEXT_END, context = null)

    }

}
