package de.adesso.junitinsights

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@JUnitInsights
@SpringBootTest
@AutoConfigureMockMvc
class AnotherHelloControllerTest {

    companion object {
        var logger : Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Autowired
    lateinit var mvc : MockMvc

    @Test
    fun getHello() {
        logger.info("### Test 1 started ###")
        mvc.perform(MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")))
    }

    @Test
    fun getAnotherHello() {
        logger.info("### Test 2 started ###")
        mvc.perform(MockMvcRequestBuilders
                .get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")))
    }

}