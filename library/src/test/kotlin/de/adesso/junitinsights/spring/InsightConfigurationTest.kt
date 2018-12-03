package de.adesso.junitinsights.spring

import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.context.support.GenericApplicationContext

class InsightConfigurationTest {

    @Test
    @Disabled
    fun springCallbacks() {
        // Arrange
        val insightConfigurationMock: InsightConfiguration = mock()
        val springCallbackMock: SpringCallbacks = mock()
        whenever(insightConfigurationMock.springContextListener()).thenReturn(springCallbackMock)

        // Act
        val ctx = GenericApplicationContext()
        ctx.registerBean(insightConfigurationMock::class.java)
        ctx.refresh()
        ctx.close()

        // Assert
        assertEquals(1, ctx.beanDefinitionCount)
        verify(springCallbackMock).onContextRefresh(any())
        validateMockitoUsage()
        verify(springCallbackMock).onContextClose(any())
        validateMockitoUsage()
    }
}
