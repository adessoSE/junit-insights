package de.adesso.junitinsightstester.spring

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
class NestedSpringTest {

    var bowlingGame = "First game of the day"
    var standingPins = 10

    fun newGame() {
        standingPins = 10
    }

    @Test
    fun gameActive() {
        assertNotEquals(bowlingGame, null);
    }

    @Nested
    inner class Rolls {
        fun rollStrike() {
            standingPins = 0
        }

        @Test
        fun strikeRolled() {
            newGame()
            rollStrike()
            assertEquals(standingPins, 0)
        }

        @Nested
        inner class NoPinsKnockedDown {
            @Test
            fun testGutterGame() {
                newGame()
                assertEquals(standingPins, 10)
            }
        }

        @Nested
        inner class SpareGame {
            @Test
            fun testSpareGame() {
                newGame()
                standingPins -= 5
                standingPins -= 5
                assertEquals(standingPins, 0)
            }
        }
    }
}
