package de.adesso.junitinsights.model

import java.util.Date

data class Event(
        var name: String,
        var timeStamp: Date,
        var className: String,
        var methodName: String,
        var successful: Boolean
)
