package de.adesso.junitinsightstester

import de.adesso.junitinsights.annotations.JUnitInsights
import de.adesso.junitinsights.annotations.NoJUnitInsights
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * Some basic tests
 */
@JUnitInsights
@SpringBootTest
@ExtendWith(SpringExtension::class)
class NewTest {
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

    @Test
    fun delayTest() {
        Thread.sleep(300)
        assertEquals(1,1)
    }
}
