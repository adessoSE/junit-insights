package de.adesso.junitinsights.model

import java.util.*

data class Report(
        var projectName: String,
        var created: Date,
        var testClasses: List<TestClass>
)