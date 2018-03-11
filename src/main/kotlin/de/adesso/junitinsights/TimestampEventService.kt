package de.adesso.junitinsights

import org.junit.jupiter.api.extension.ExtensionContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.annotation.Resource

@Service
class TimestampEventService {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    lateinit var eventRepository: TimestampEventRepository
    fun createEventNow(eventType: EventType, context: ExtensionContext?): TimestampEvent? {
        var timestampEvent = createTimestampEvent(eventType, context)
        when (eventType) {
            EventType.APP_CONTEXT_START -> {
                log.info("${eventType.name} saved. \n $timestampEvent")
                saveTimestampEvent(timestampEvent)
            }
            EventType.APP_CONTEXT_END -> {
                log.info("${eventType.name} saved. \n $timestampEvent")
                saveTimestampEvent(timestampEvent)

            }
            EventType.TEST_CLASS_START -> {
                log.info("${eventType.name} saved. \n $timestampEvent")
                saveTimestampEvent(timestampEvent)

            }
            EventType.TEST_CLASS_END -> {
                log.info("${eventType.name} saved. \n $timestampEvent")
                saveTimestampEvent(timestampEvent)

            }
            EventType.TEST_METHOD_START -> {
                log.info("${eventType.name} saved. \n $timestampEvent")
                saveTimestampEvent(timestampEvent)

            }
            EventType.TEST_METHOD_END -> {
                log.info("${eventType.name} saved. \n $timestampEvent")
                saveTimestampEvent(timestampEvent)

            }
        }
        return null
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