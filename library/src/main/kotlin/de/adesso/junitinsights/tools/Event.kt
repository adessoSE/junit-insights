package de.adesso.junitinsights.tools

import java.util.Date

data class Event(
        var name: String,
        var timeStamp: Date,
        var className: String,
        var methodName: String,
        var successful: Boolean
)
