package de.adesso.junitinsights.tools

object EventLog {
    var events: ArrayList<Event> = ArrayList()
    fun log(e: Event) = events.add(e)
}