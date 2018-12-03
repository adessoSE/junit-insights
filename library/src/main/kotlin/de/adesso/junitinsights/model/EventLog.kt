package de.adesso.junitinsights.model

import de.adesso.junitinsights.tools.InsightProperties

object EventLog {
    internal var events: ArrayList<Event> = ArrayList()

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

}
