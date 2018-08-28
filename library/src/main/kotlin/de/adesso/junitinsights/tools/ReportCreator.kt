package de.adesso.junitinsights.tools

import java.util.*
import kotlin.collections.ArrayList

object ReportCreator {
    fun createReport(reportName: String, events: List<Event>): Report {
        val eventsGroupedByClass = groupEventsByClass(events)
        val testClasses = eventsGroupedByClass.map { classEvents -> processClassEvents(classEvents) }
        return Report(reportName, Date(), testClasses)
    }

    private fun processClassEvents(events: List<Event>): TestClass {
        val beforeAll = events[0].timeStamp.time
        val afterAll = events.last().timeStamp.time
        val eventsGroupedByMethods = groupEventsByMethod(events)
        val methods = eventsGroupedByMethods.map { methodEvents -> processMethodEvents(methodEvents) }

        var between = 0L
        for (i in 1..methods.lastIndex)
            between += methods[i].timestampBefore - methods[i - 1].timestampAfter

        return TestClass(
                events.last().className,
                methods,
                methods[0].timestampBefore - beforeAll,
                methods.map { method -> method.before }.sum(),
                methods.map { method -> method.exec }.sum(),
                methods.map { method -> method.after }.sum(),
                afterAll - methods.last().timestampAfter,
                between
        )
    }

    private fun processMethodEvents(events: List<Event>): TestMethod {
        var beforeEach = 0L
        var beforeTestExecution = 0L
        var afterEach = 0L
        var afterTestExecution = 0L
        for (event in events) {
            when (event.name) {
                "before each" -> beforeEach = event.timeStamp.time
                "before test execution" -> beforeTestExecution = event.timeStamp.time
                "after test execution" -> afterTestExecution = event.timeStamp.time
                "after each" -> afterEach = event.timeStamp.time
            }
        }
        return TestMethod(
                events.last().methodName,
                beforeEach,
                afterEach,
                beforeTestExecution - beforeEach,
                afterTestExecution - beforeTestExecution,
                afterEach - afterTestExecution,
                events.last().successful
        )
    }

    private fun groupEventsByClass(events: List<Event>) = groupEventsAfterKeyword(events, "after all")
    private fun groupEventsByMethod(events: List<Event>) = groupEventsAfterKeyword(events, "after each")

    private fun groupEventsAfterKeyword(events: List<Event>, keyword: String): List<List<Event>> {
        val result = ArrayList<List<Event>>()
        var currentEventGroup = ArrayList<Event>()
        for (event in events) {
            currentEventGroup.add(event)
            if (event.name == keyword) {
                result.add(currentEventGroup)
                currentEventGroup = ArrayList()
            }
        }
        return result
    }
}