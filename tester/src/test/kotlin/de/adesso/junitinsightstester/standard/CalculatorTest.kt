package de.adesso.junitinsightstester.standard

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CalculatorTest {

    @Test
    fun basicArithmetic() {
        assertEquals(1+1, 2)
    }
}