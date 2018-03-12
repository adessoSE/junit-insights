package de.adesso.junitinsights

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@JUnitInsights
@SpringBootTest
@ExtendWith(SpringExtension::class)
class NewTest {
    companion object {
        val log = LoggerFactory.getLogger(this::class.java.name)
    }

    @Autowired
    lateinit var timestampEventService: TimestampEventService

    @Test
    fun basicTest() {
        assertEquals(2, 1 + 1)
    }
}