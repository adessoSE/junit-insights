# JUnit Insights for Spring

JUnit Insights is an extension for the use of JUnit 5 in combination with the Spring framework, which
1. measures the time for setup, execution and teardown for each test method in each test class
2. creates a nice looking report that visualizes the data

**Background:** When building integration tests with Spring (e.g. with @SpringBootTest), sometimes a Spring application context has to be started and sometimes it doesn't.
For the user of the test classes, it looks like some tests take a long time to execute, although the actual test runs fairly quickly.
To make this behavior transparent, a report is created.

# How to use

You can add the extension via the provided [bintray package](https://bintray.com/adesso/junit-insights/junit-insights):

## Maven

```
<repositories>
	<repository>
		<id>bintray.com</id>
		<url>https://dl.bintray.com/adesso/junit-insights</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>de.adesso</groupId>
		<artifactId>junit-insights</artifactId>
		<version>0.0.1</version>
	</dependency>
</dependencies>
```

## Gradle

```
repositories {
    maven {
        url  "https://dl.bintray.com/adesso/junit-insights"
    }
}

dependencies {
	testCompile ('de.adesso:junit-insights:0.0.1')
}
```

## Usage

Add `@JUnitInsights` to the test-classes you want to benchmark.
If you want to exclude methods from the benchmark, add `@NoJUnitInsights` to those.
Be aware that ExtendsWith(SpringExtension::class) needs to be used as Runner-class.
