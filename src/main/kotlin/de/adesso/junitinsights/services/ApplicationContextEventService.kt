package de.adesso.junitinsights.services

import de.adesso.junitinsights.repositories.ApplicationContextEventType
import de.adesso.junitinsights.repositories.ApplicationContextEvent
import de.adesso.junitinsights.repositories.ApplicationContextEventRepository
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class ApplicationContextEventService(
        @Autowired var eventRepository: ApplicationContextEventRepository
){
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    /**
     * Create Event
     */
    fun putEventIntoDatabaseNow(applicationContextEventType: ApplicationContextEventType, context: ExtensionContext?) {
        var event = ApplicationContextEvent(timestamp = Date.from(Instant.now()), applicationContextEvent = applicationContextEventType)
        log.info("${applicationContextEventType.name} saved.")
        save(event)
    }

    //Repository-interactions

    fun save(applicationContextEvent: ApplicationContextEvent): ApplicationContextEvent {
        return eventRepository.save(applicationContextEvent)
    }
    fun findAll(): MutableIterable<ApplicationContextEvent> {
        return eventRepository.findAll()
    }

    fun delete(applicationContextEvent: ApplicationContextEvent){
        eventRepository.delete(applicationContextEvent)
    }
}