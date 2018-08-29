package de.adesso.junitinsights.model

object EventLog {
    var events: ArrayList<Event> = ArrayList()
    fun log(e: Event) = events.add(e)
}