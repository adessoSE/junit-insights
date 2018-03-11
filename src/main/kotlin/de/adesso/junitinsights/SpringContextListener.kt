package de.adesso.junitinsights

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.*
import org.springframework.stereotype.Component

@Component
class SpringContextListener {
    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }


    @EventListener(ContextRefreshedEvent::class)
        fun catchContextInitialization(event: ContextRefreshedEvent){
        log.info("ContextRefreshedEvent Received \n" +
                "ApplicationContext was initialized")
        //TODO Check if first init before closeing initial, so that its not a refresh

    }

    @EventListener(ContextClosedEvent::class)
    fun test(event: ContextClosedEvent){
        log.info("ContextClosedEvent Received \n" +
                "ApplicationContext was closed")
    }

}
