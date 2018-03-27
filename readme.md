[![GitHub issues](https://img.shields.io/github/issues/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights/issues)
[![GitHub forks](https://img.shields.io/github/forks/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights/network)
[![GitHub stars](https://img.shields.io/github/stars/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights/stargazers)
[![GitHub license](https://img.shields.io/github/license/adessoAG/junit-insights.svg?style=flat-square)](https://github.com/adessoAG/junit-insights)
![](https://img.shields.io/badge/Nice-100%25-brightgreen.svg)


# JUnit Insigths for Spring

JUnit Insights is an extension for the use of JUnit 5 in combination with the Spring framework, which
1. measures the time for setup, execution and teardown for each test method in each test class
2. and creates a nice looking report that visualizes the data.

**Background:** When building integration tests with Spring (e.g. with @SpringBootTest), sometimes a Spring application context has to be started and sometimes it doesn't.
For the user of the test classes, it looks like some tests take a long time to execute, although the actual test runs fairly quickly.
To make this behavior transparent, a report is created.

# How to use

Add the dependency to your project.

`//TODO`

Add @JUnitInsights to the test-classes you want to benchmark.
Be aware that ExtendsWith(SpringExtension::class) needs to be used as Runner-class.
