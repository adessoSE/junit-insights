package de.adesso.junitinsights.tools

data class TestMethod(
        var name: String,
        @Transient var timestampBefore: Long,
        @Transient var timestampAfter: Long,
        var before: Long,
        var exec: Long,
        var after: Long,
        var successful: Boolean
)