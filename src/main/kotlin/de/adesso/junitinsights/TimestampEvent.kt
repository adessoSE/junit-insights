package de.adesso.junitinsights

import java.util.*
import javax.persistence.EnumType
import javax.persistence.Enumerated

data class TimestampEvent (var id: Long, var timestamp: Date, var event: String, var testClass: String, var testMethod: String) {

    override fun toString(): String {
        return "TimestampEvent(id=$id, timestamp=$timestamp, event=$event, testClass='$testClass', testMethod='$testMethod')"
    }
}