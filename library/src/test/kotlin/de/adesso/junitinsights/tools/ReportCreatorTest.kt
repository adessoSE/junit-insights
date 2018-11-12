package de.adesso.junitinsights.tools

import de.adesso.junitinsights.model.Event
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

class ReportCreatorTest {

    @Test
    fun noEventsProduceEmptyReport() {
        val events: ArrayList<Event> = ArrayList()
        val report = ReportCreator.createReport("test", events)
        assertTrue(report.testClasses.isEmpty())
        assertTrue(report.springContextsCreated == 0)
    }

    @Test
    fun reportNameComesFromInput() {
        val events: ArrayList<Event> = ArrayList()
        val report = ReportCreator.createReport("test", events)
        assert(report.projectName == "test")
    }

    @Test
    fun countsSpringContexts() {
        val events: ArrayList<Event> = ArrayList()
        events.add(Event("context created", Date()))
        events.add(Event("context refreshed", Date()))
        events.add(Event("context refreshed", Date()))
        val report = ReportCreator.createReport("test", events)
        assertEquals(3, report.springContextsCreated)
    }

    @Test
    fun singleClassInReport() {
        val events: ArrayList<Event> = ArrayList()
        events.add(Event("before all", Date(0), "test-class"))
        events.add(Event("before each", Date(10), "test-class", "test-method"))
        events.add(Event("after each", Date(100), "test-class", "test-method"))
        events.add(Event("after all", Date(110), "test-class"))
        val report = ReportCreator.createReport("test", events)
        assertEquals(1, report.testClasses.size)
        assertEquals(1, report.testClasses.first().methods.size)
    }
}
