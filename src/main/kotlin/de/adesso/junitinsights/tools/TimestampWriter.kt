package de.adesso.junitinsights.tools

import java.io.File

object TimestampWriter {
    var file = File("timestamps.csv").bufferedWriter()

    init {
        file.write("event;test class;test function;timestamp")
        file.newLine()
    }

    fun writeTimestamp(timestamp: String, event: String, testClass: String, testFunction: String) {
        file.write(timestamp + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction))
        file.newLine()
    }

    fun close() {
        file.close()
    }

    private fun trimObjectString(string: String): String {
        return string.replace("Optional.empty", "")
                .replace("Optional", "")
                .replace("[", "")
                .replace("]", "")
    }
}
