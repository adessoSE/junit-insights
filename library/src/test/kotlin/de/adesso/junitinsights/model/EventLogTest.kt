package de.adesso.junitinsights.model

import de.adesso.junitinsights.tools.InsightProperties
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class EventLogTest {

    @Test
    fun basicLoggingTest() {
        InsightProperties.enabled = true

        createTestEvents()

        assertEquals(7, EventLog.eventCount())
    }

    @AfterEach
    fun clearEvents() = EventLog.clearEvents()

    private fun createTestEvents() {
        EventLog.log(Event("before all", Date(0), "test-class"))
        EventLog.log(Event("context refreshed", Date(1)))
        EventLog.log(Event("before each", Date(3), "test-class", "test-method"))
        EventLog.log(Event("before test execution", Date(6), "test-class", "test-method"))
        EventLog.log(Event("after test execution", Date(10), "test-class", "test-method"))
        EventLog.log(Event("after each", Date(15), "test-class", "test-method"))
        EventLog.log(Event("after all", Date(21), "test-class"))
    }
}
