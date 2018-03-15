package de.adesso.junitinsights

import de.adesso.junitinsights.annotations.JUnitInsights
import de.adesso.junitinsights.annotations.NoJUnitInsights
import de.adesso.junitinsights.services.ApplicationContextEventService
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
    lateinit var applicationContextEventService: ApplicationContextEventService

    @Test
    fun basicTest() {
        assertEquals(2, 1 + 1)
    }

    @Test
    @NoJUnitInsights
    fun basicTest2() {
        assertEquals(0, 1 - 1)
    }

    @Test
    fun basicTest3() {
        assertEquals(3, 1 + 2)
    }
}