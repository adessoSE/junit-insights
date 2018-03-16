package de.adesso.junitinsights.tools

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

var deltaMode = false
var logOutput = false

object TimestampWriter {
    private var file = File("timestamps.csv").bufferedWriter()
    private var lastTimestamp: Long = 0

    private var logger: Logger = LoggerFactory.getLogger(this::class.java)

    init {
        file.write("event;test class;test function;timestamp")
        file.newLine()
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
        file.write(tstamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction))
        file.newLine()
        if (logOutput)
            logger.info("########" + tstamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction) + "\n")
    }

    fun flush() {
        file.flush()
    }

    private fun trimObjectString(string: String): String {
        return string.replace("Optional.empty", "")
                .replace("Optional", "")
                .replace("[", "")
                .replace("]", "")
    }
}
