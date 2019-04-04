package de.adesso.junitinsights.model

import java.util.Date

/**
 * POJO for storing various different events during testing.
 * Events come from JUnit or Spring callbacks
 * @see de.adesso.junitinsights.junit.JUnitCallbacks
 * @see de.adesso.junitinsights.spring.ContextRefreshedListener
 * @see de.adesso.junitinsights.spring.ContextClosedListener
 * @see EventLog
  */
data class Event(
        var name: String,
        var timeStamp: Date = Date(),
        var className: String = "",
        var methodName: String = "",
        var successful: Boolean = true
)
