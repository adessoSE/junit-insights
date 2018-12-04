package de.adesso.junitinsights.junit

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import de.adesso.junitinsights.annotations.NoJUnitInsights
import de.adesso.junitinsights.model.EventLog
import de.adesso.junitinsights.tools.InsightProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.support.AnnotationSupport.isAnnotated
import java.lang.reflect.AnnotatedElement
import java.util.*

class JUnitCallbacksTest {

    private fun mockedExtensionContext(): ExtensionContext {
        val mockedExtensionContext: ExtensionContext = mock()
        whenever(mockedExtensionContext.testClass).thenReturn(Optional.of(this.javaClass))
        whenever(mockedExtensionContext.getConfigurationParameter("de.adesso.junitinsights.enabled"))
                .thenReturn(Optional.of("true"))
        return mockedExtensionContext
    }

    @Test
    fun beforeAllSetsConfiguration() {
        // Arrange
        val mockedExtensionContext: ExtensionContext = mockedExtensionContext()
        val callbacks = JUnitCallbacks()

        // Act
        callbacks.beforeAll(mockedExtensionContext)

        // Assert
        assertTrue(InsightProperties.configurationSet)
    }

    @Test
    fun beforeAllCreatesEvent() {
        // Arrange
        val mockedExtensionContext: ExtensionContext = mockedExtensionContext()
        val callbacks = JUnitCallbacks()
        InsightProperties.enabled = true

        // Act
        callbacks.beforeAll(mockedExtensionContext)

        // Assert
        assertTrue(EventLog.containsEventWithName("before all"))
    }

    @Test
    fun beforeEachCreatesEvent() {
        // Arrange
        val mockedExtensionContext: ExtensionContext = mockedExtensionContext()
        val callbacks = JUnitCallbacks()
        InsightProperties.enabled = true

        // Act
        callbacks.beforeEach(mockedExtensionContext)

        // Assert
        assertTrue(EventLog.containsEventWithName("before each"))
    }

    @Test
    fun afterEachCreatesEvent() {
        // Arrange
        val mockedExtensionContext: ExtensionContext = mockedExtensionContext()
        val callbacks = JUnitCallbacks()
        InsightProperties.enabled = true

        // Act
        callbacks.afterEach(mockedExtensionContext)

        // Assert
        assertTrue(EventLog.containsEventWithName("after each"))
    }

    @Test
    fun afterAllCreatesEvent() {
        // Arrange
        val mockedExtensionContext: ExtensionContext = mockedExtensionContext()
        val callbacks = JUnitCallbacks()
        InsightProperties.enabled = true

        // Act
        callbacks.afterAll(mockedExtensionContext)

        // Assert
        assertTrue(EventLog.containsEventWithName("after all"))
    }

    @Test
    @Disabled
    fun excludedTestClassesDoNotLog() {
        // Arrange
        val mockedExtensionContext: ExtensionContext = mockedExtensionContext()
        //TODO: mock call to context.element to return element with NoJUnitInsights annotation
        //(might require reflection magic)
        val callbacks = JUnitCallbacks()
        InsightProperties.enabled = true

        // Act
        callbacks.beforeAll(mockedExtensionContext)

        // Assert
        assertEquals(0, EventLog.eventCount())
    }
}
