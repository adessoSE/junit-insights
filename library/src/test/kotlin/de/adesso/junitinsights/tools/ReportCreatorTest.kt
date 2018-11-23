package de.adesso.junitinsights.tools

import de.adesso.junitinsights.model.Event
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ReportCreatorTest {

    @Test
    fun noEventsProduceEmptyReport() {
        val events: ArrayList<Event> = ArrayList()
        val report = ReportCreator.createReport("test", events)
        assertTrue(report.testClasses.isEmpty())
        assertTrue(report.springContextsCreated == 0)
        assertTrue(report.created == Date() || report.created.before(Date()))
    }

    @Test
    fun singleClassInReport() {
        val events: ArrayList<Event> = ArrayList()
        events.add(Event("before all", Date(0), "test-class"))
        events.add(Event("context refreshed", Date(1)))
        events.add(Event("before each", Date(3), "test-class", "test-method"))
        events.add(Event("before test execution", Date(6), "test-class", "test-method"))
        events.add(Event("after test execution", Date(10), "test-class", "test-method"))
        events.add(Event("after each", Date(15), "test-class", "test-method"))
        events.add(Event("after all", Date(21), "test-class"))
        val report = ReportCreator.createReport("test", events)

        assertTrue(report.created == Date() || report.created.before(Date()))
        assertEquals(1, report.testClasses.size)
        assertEquals(1, report.testClasses.first().methods.size)

        assertEquals(1, report.testClasses.first().spring)
        assertEquals(2, report.testClasses.first().beforeAll)
        assertEquals(3, report.testClasses.first().methods.first().before)
        assertEquals(4, report.testClasses.first().methods.first().exec)
        assertEquals(5, report.testClasses.first().methods.first().after)
        assertEquals(6, report.testClasses.first().afterAll)
    }

    @Test
    fun twoMethodsInReport() {
        val events: ArrayList<Event> = ArrayList()
        events.add(Event("before all", Date(0), "test-class"))
        events.add(Event("before each", Date(1), "test-class", "test-method"))
        events.add(Event("before test execution", Date(3), "test-class", "test-method"))
        events.add(Event("after test execution", Date(6), "test-class", "test-method"))
        events.add(Event("after each", Date(10), "test-class", "test-method"))
        events.add(Event("before each", Date(15), "test-class", "another-method", false))
        events.add(Event("before test execution", Date(21), "test-class", "another-method", false))
        events.add(Event("after test execution", Date(28), "test-class", "another-method", false))
        events.add(Event("after each", Date(36), "test-class", "another-method", false))
        events.add(Event("after all", Date(45), "test-class"))
        val report = ReportCreator.createReport("test", events)

        assertEquals(1, report.testClasses.size)
        assertEquals(2, report.testClasses.first().methods.size)
        assertEquals(0, report.testClasses.first().firstTimestamp)
        assertEquals(1, report.testClasses.first().methods.first().firstTimestamp)
        assertEquals("test-class", report.testClasses.first().name)
        assertEquals("test-method", report.testClasses.first().methods.first().name)
        assertTrue(report.testClasses.first().methods.first().successful)
        assertFalse(report.testClasses.first().methods.last().successful)

        assertEquals(2 + 6, report.testClasses.first().before)
        assertEquals(3 + 7, report.testClasses.first().exec)
        assertEquals(4 + 8, report.testClasses.first().after)
        assertEquals(5, report.testClasses.first().between)

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
}
