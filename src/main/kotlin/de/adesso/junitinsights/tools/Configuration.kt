package de.adesso.junitinsights.tools

import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


object Configuration {
    var reportPath: String

    init {
        val file = File("junit-insights.conf")
        if (!file.isFile) {
            Files.copy(ClassPathResource("/configurationTemplate.conf").inputStream, Paths.get("junit-insights.conf"))
        }

        val props = Properties()
        props.load(FileInputStream("junit-insights.conf"))

        reportPath = props.getProperty("report_path")
    }
}
