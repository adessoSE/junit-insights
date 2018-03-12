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

    fun createEventNow(applicationContextEventType: ApplicationContextEventType, context: ExtensionContext?) {
        var timestampEvent = createTimestampEvent(applicationContextEventType, context)
        log.info("${applicationContextEventType.name} saved. \n $timestampEvent")
        saveTimestampEvent(timestampEvent)
    }

    fun createTimestampEvent(applicationContextEventType: ApplicationContextEventType, context: ExtensionContext?): ApplicationContextEvent {
        if(context != null) {
            return ApplicationContextEvent(timestamp = Date.from(Instant.now()), applicationContextEvent = applicationContextEventType, testClass = context.testClass.toString(), testMethod = context.testMethod.toString())
        }
        return ApplicationContextEvent(timestamp = Date.from(Instant.now()), applicationContextEvent = applicationContextEventType)
    }

    fun saveTimestampEvent(applicationContextEvent: ApplicationContextEvent): ApplicationContextEvent {
        return eventRepository.save(applicationContextEvent)
    }
    fun findAllTimestampEvent(applicationContextEvent: ApplicationContextEvent): MutableIterable<ApplicationContextEvent> {
        return eventRepository.findAll()
    }

    fun deleteTimestampEvent(applicationContextEvent: ApplicationContextEvent){
        eventRepository.delete(applicationContextEvent)
    }
}