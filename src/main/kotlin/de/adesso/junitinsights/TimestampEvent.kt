package de.adesso.junitinsights

import java.util.*
import javax.persistence.*

@Entity
data class TimestampEvent (@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0,
                           var timestamp: Date,
                           var event: EventType? = null,
                           var testClass: String? = null,
                           var testMethod: String? = null) {

    override fun toString(): String {
        return "TimestampEvent(id=$id, timestamp=$timestamp, event=$event, testClass='$testClass', testMethod='$testMethod')"
    }
}