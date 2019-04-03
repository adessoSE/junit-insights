package de.adesso.junitinsights.spring

import org.springframework.test.context.ContextConfigurationAttributes
import org.springframework.test.context.ContextCustomizer
import org.springframework.test.context.ContextCustomizerFactory

/**
 * Spring factory that is used to modify the method for creating a context customizer.
 * This is being registered in the spring.factories file and therefore gets used automatically.
 * @see ContextCustomizer
 */
class BenchmarkContextCustomizerFactory : ContextCustomizerFactory {

    /**
     * Overridden method that registers listeners for Spring context events before the context is started.
     * @see ContextRefreshedListener
     * @see ContextClosedListener
     */
    override fun createContextCustomizer(testClass: Class<*>, configAttributes: MutableList<ContextConfigurationAttributes>): ContextCustomizer? {
        return ContextCustomizer { context, _ ->
            context.addApplicationListener(ContextRefreshedListener())
            context.addApplicationListener(ContextClosedListener())
        }
    }
}
