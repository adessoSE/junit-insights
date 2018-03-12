package de.adesso.junitinsights.services

import de.adesso.junitinsights.repositories.EventType
import de.adesso.junitinsights.repositories.TimestampEvent
import de.adesso.junitinsights.repositories.TimestampEventRepository
import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class TimestampEventService(
        @Autowired var eventRepository: TimestampEventRepository
){
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    fun createEventNow(eventType: EventType, context: ExtensionContext?) {
        var timestampEvent = createTimestampEvent(eventType, context)
        log.info("${eventType.name} saved. \n $timestampEvent")
        saveTimestampEvent(timestampEvent)
    }

    fun createTimestampEvent(eventType: EventType, context: ExtensionContext?): TimestampEvent {
        if(context != null) {
            return TimestampEvent(timestamp = Date.from(Instant.now()), event = eventType, testClass = context.testClass.toString(), testMethod = context.testMethod.toString())
        }
        return TimestampEvent(timestamp = Date.from(Instant.now()), event = eventType)
    }

    fun saveTimestampEvent(timestampEvent: TimestampEvent): TimestampEvent {
        return eventRepository.save(timestampEvent)
    }
    fun findAllTimestampEvent(timestampEvent: TimestampEvent): MutableIterable<TimestampEvent> {
        return eventRepository.findAll()
    }

    fun deleteTimestampEvent(timestampEvent: TimestampEvent){
        eventRepository.delete(timestampEvent)
    }
}