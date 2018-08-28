package de.adesso.junitinsights.tools

data class TestClass(
        var name: String,
        var methods: List<TestMethod>,
        var beforeAll: Long,
        var before: Long,
        var exec: Long,
        var after: Long,
        var afterAll: Long,
        var between: Long
)