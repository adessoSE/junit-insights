package de.adesso.junitinsights

import java.util.*
import javax.persistence.*

@Entity
data class TimestampEvent (@Id @GeneratedValue var id: Long, var timestamp: Date, var event: String, var testClass: String, var testMethod: String) {

    override fun toString(): String {
        return "TimestampEvent(id=$id, timestamp=$timestamp, event=$event, testClass='$testClass', testMethod='$testMethod')"
    }
}