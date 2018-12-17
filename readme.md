[ ![Download](https://api.bintray.com/packages/adesso/junit-insights/junit-insights/images/download.svg) ](https://bintray.com/adesso/junit-insights/junit-insights)
[![Build Status](https://travis-ci.org/adessoAG/junit-insights.svg?branch=master)](https://travis-ci.org/adessoAG/junit-insights)
[![codebeat badge](https://codebeat.co/badges/bac44e06-3560-4c28-814c-b5495bfa3c28)](https://codebeat.co/projects/github-com-adessoag-junit-insights-master)
# JUnit Insights
JUnit Insights is an extension for JUnit 5 (optionally in combination with the Spring framework), which
1. measures the time for setup, execution and teardown for each test method in each test class
2. (optional) measures how often Spring contexts were created and how long this takes
3. creates a nice looking report that visualizes the data (see screenshot below)

**Background:** When building integration tests with Spring (e.g. with @SpringBootTest), sometimes a Spring application context has to be started and sometimes it doesn't.
For the user of the test classes, it looks like some tests take a long time to execute, although the actual test runs fairly quickly.
To make this behavior transparent, a report is created.

# Usage

## Activating the extension

First of all, you need to tell JUnit Insights that it should be activated via a system property.

Gradle:
```gradle
test {
    systemProperty 'de.adesso.junitinsights.enabled', 'true'
}
```

Maven:
```xml
<plugins>
...
    <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.0</version>
        <configuration>
            <systemPropertyVariables>
                <de.adesso.junitinsights.enabled>true</de.adesso.junitinsights.enabled>
            </systemPropertyVariables>
        </configuration>
    </plugin>
...
</plugins>
```

## Adding individual classes to the benchmark

Add `@JUnitInsights` to the test classes you want to benchmark.
If you want to exclude methods from the benchmark, add `@NoJUnitInsights` to those.
Be aware that `ExtendsWith(SpringExtension::class)` needs to be used as Runner-class.

## Including all test classes

Alternatively, if you want to add the extension to all your test classes, you have to activate autodetection for JUnit 5 in your build file and activate JUnit Insights:

Gradle:
```gradle
test {
    systemProperty 'junit.jupiter.extensions.autodetection.enabled', 'true'
}
```

Maven:
```xml
<plugins>
...
    <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.0</version>
        <configuration>
            <systemPropertyVariables>
                <de.adesso.junitinsights.enabled>true</de.adesso.junitinsights.enabled>
                <junit.jupiter.extensions.autodetection.enabled>true</junit.jupiter.extensions.autodetection.enabled>
            </systemPropertyVariables>
        </configuration>
    </plugin>
...
</plugins>
```

Further information can be found [here](https://junit.org/junit5/docs/current/user-guide/#extensions-registration-automatic)

## Changing the destination path for the reports

By default, created reports are stored in the `build/reports` directory. You can change this, by changing the following system property.

Gradle:
```gradle
test {
    systemProperty 'de.adesso.junitinsights.reportpath', 'custom/report/directory/'
}
```
Maven:
```xml
<plugins>
...
    <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.0</version>
        <configuration>
            <systemPropertyVariables>
                <de.adesso.junitinsights.enabled>true</de.adesso.junitinsights.enabled>
                <junit.jupiter.extensions.autodetection.enabled>true</junit.jupiter.extensions.autodetection.enabled>
                <de.adesso.junitinsights.reportpath>reports/</de.adesso.junitinsights.reportpath>
            </systemPropertyVariables>
        </configuration>
    </plugin>
...
</plugins>
```

# Dependency

For your convenience, JUnit Insights is available through different maven repositories. Just add the necessary repository and the dependency to your build file.

## Release version from [JCenter](https://bintray.com/adesso/junit-insights/junit-insights)

You can get the most recent version of JUnit Insights via the official JCenter repository. 

Gradle:

```gradle
repositories {
	jcenter()
}

dependencies {
	testCompile ('de.adesso:junit-insights:0.0.1')
}
```

Maven:

```xml
<repositories>
	<repository>
		<id>jcenter</id>
		<url>https://jcenter.bintray.com/</url>
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

## Release version from [Bintray](https://bintray.com/adesso/junit-insights/junit-insights)

If the JCenter repository does not work for some reason, or you are not satisfied with using it, you can use the Bintray repository dicrectly, too.

Gradle:

```gradle
repositories {
    maven {
        url  "https://dl.bintray.com/adesso/junit-insights"
    }
}

dependencies {
	testCompile ('de.adesso:junit-insights:0.0.1')
}
```

Maven:

```xml
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

## SNAPSHOT version from [oss.jfrog.org](https://oss.jfrog.org/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/de/adesso/junit-insights)

For the most recent SNAPSHOT version of JUnit Insights, you can also use the artifactory repository oss.jfrog.org.

Gradle:

```gradle
repositories {
	maven {
        url  "https://oss.jfrog.org/artifactory/oss-snapshot-local"
    }
}

dependencies {
	testCompile ('de.adesso:junit-insights:0.0.1-SNAPSHOT')
}
```

Maven:

```xml
<repositories>
	<repository>
		<id>oss-snapshot-local</id>
		<url>https://oss.jfrog.org/artifactory/oss-snapshot-local</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>de.adesso</groupId>
		<artifactId>junit-insights</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
</dependencies>
```

# How time is measured
The extension captures certain events in during the test plan execution to measure the time for each phase:

TODO explain how

# Troubleshooting

If you get an error complaining about a missing JUnit platform launcher, for example

```
java.lang.ClassNotFoundException: org.junit.platform.launcher.TestExecutionListener
```
you need to add the dependency for the [appropriate package](https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher).

If you have any other issues, feel free to open an issue in our issue tracker.

# Screenshot
![Screenshot 1](./screenshots/screen1.png)
