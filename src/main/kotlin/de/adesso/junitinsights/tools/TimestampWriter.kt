package de.adesso.junitinsights.tools

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var deltaMode = false
var logOutput = false

object TimestampWriter {
    private var currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
    private var bufferedWriter = File("insight_$currentTime.csv").bufferedWriter()
    private var lastTimestamp: Long = 0

    private var logger: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        bufferedWriter.write("timestamp;event;test class;test function")
        bufferedWriter.newLine()
    }

    fun writeTimestamp(timestamp: Long, event: String, testClass: String, testFunction: String) {
        var tstamp: Long = timestamp
        if (deltaMode) {
            if (lastTimestamp == 0.toLong()) {
                lastTimestamp = timestamp
            } else {
                tstamp = timestamp - lastTimestamp
                lastTimestamp = timestamp
            }
        }
        bufferedWriter.write(tstamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction))
        bufferedWriter.newLine()
        if (logOutput)
            logger.info("########" + tstamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction) + "\n")
    }

    fun flush() {
        bufferedWriter.flush()
    }

    private fun trimObjectString(string: String): String {
        return string.replace("Optional.empty", "")
                .replace("Optional", "")
                .replace("[", "")
                .replace("]", "")
    }
}
