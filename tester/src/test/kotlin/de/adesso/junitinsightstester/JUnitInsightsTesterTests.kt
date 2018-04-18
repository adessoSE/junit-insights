package de.adesso.junitinsightstester

import de.adesso.junitinsights.annotations.JUnitInsights
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.context.SpringBootTest

/**
 * Some basic test
 */
@SpringBootTest
@JUnitInsights
class JUnitInsightsTesterTests {

    @Test
    fun contextLoads() {
        assertEquals(1, 1);
    }

}
