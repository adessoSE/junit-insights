package de.adesso.junitinsights.model

import java.util.*

/**
 * Top-level data structure for a test report.
 * Contains general information and branches off into specific test classes.
 */
data class Report(
        var reportTitle: String,
        var created: Date,
        var springContextsCreated: Int,
        var testClasses: List<TestClass>
)