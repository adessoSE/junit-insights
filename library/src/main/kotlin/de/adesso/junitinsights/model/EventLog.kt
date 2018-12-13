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
     * @param e The event that should be stored
     * @see de.adesso.junitinsights.tools.ReportCreator
     */
    fun log(e: Event) {
        // If JUnit Insights is disabled, no events have to be logged
        if (!InsightProperties.enabled)
            return

        events.add(e)
    }

    /**
     * Empties the list of events.
     */
    fun clearEvents() {
        events.clear()
    }

    /**
     * Delivers the number of events in the log.
     * @return Number of events
     */
    fun eventCount() = events.count()

    /**
     * Checks, if an event with the specified name is in the list of events.
     * @param str The name of the event whose existence should be checked
     * @return True if the event is present, false otherwise
     */
    fun containsEventWithName(str: String) = events.map { it.name }.contains(str)

}
