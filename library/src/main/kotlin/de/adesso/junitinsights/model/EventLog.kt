package de.adesso.junitinsights.model

import de.adesso.junitinsights.tools.InsightProperties

/**
 * Central object for storing Events during testing
 * @see Event
 * @see de.adesso.junitinsights.junit.JUnitCallbacks
 * @see de.adesso.junitinsights.spring.SpringCallbacks
 */
object EventLog {
    internal var events: ArrayList<Event> = ArrayList()

    /**
     * Takes a testing event and stores it for later processing by ReportCreator
     * @see de.adesso.junitinsights.tools.ReportCreator
     */
    fun log(e: Event) {
        // If JUnit Insights is disabled, no events have to be logged
        if (!InsightProperties.enabled)
            return

        events.add(e)
    }

    fun clearEvents() {
        events.clear()
    }

    fun eventCount() = events.count()

    fun containsEventWithName(str: String) = events.map { it.name }.contains(str)

}
