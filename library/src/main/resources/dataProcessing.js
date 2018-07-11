"use strict";

/**
 * Methods for processing the csv data before it can be displayed
 */

/**
 * Kicks off the processing of the csv data and the creation of the plots
 * @param csvData The csv string containing all timestamps
 */
function processData(csvData) {
    Papa.parse(csvData, {
        complete: function (content) {
            let data = content.data.filter(function (element) {
                return element.length === 5;    // remove irrelevant lines like blank lines
            });
            let classesTimestamps = splitIntoClasses(data);
            let classesDurations = calculateDurations(classesTimestamps);
            prepareChartElements(classesDurations);
            drawOverviewChart();
            drawPerTestCharts();
            showGeneralData();
        }
    });
}

/**
 * Splits the raw timestamp data into test classes for further processing
 * @param rawData Raw timestamp data from the input file
 * @returns {Array}
 */
function splitIntoClasses(rawData) {
    let classesTimestamps = [];
    let currentClass = [];
    rawData.forEach(function (entry) {
        currentClass.push(entry);
        if (entry[1] === "after all") {     // each class terminates with "after all"
            classesTimestamps.push(currentClass);
            currentClass = [];              // and the next class starts immediately after that
        }
    });
    return classesTimestamps;
}

/**
 * Calculates durations for the important events from raw timestamp data
 * @param classesTimestamps Input which is already split into test classes
 * @returns {Array}
 */
function calculateDurations(classesTimestamps) {
    let classesDurations = [];
    let currentClass, testDurationSum, testBegin;
    classesTimestamps.forEach(function (currentClassTimestamps) {
        currentClass = {};
        currentClass.name = currentClassTimestamps[0][2];
        currentClass.begin = currentClassTimestamps[0][0];
        currentClass.end = currentClassTimestamps[currentClassTimestamps.length - 1][0];
        currentClass.newContexts = 0;
        currentClass.spring = 0;
        currentClass.tests = []
        testDurationSum = 0;
        testBegin = 0;

        currentClassTimestamps.forEach(function (currentEvent, index) {
            switch (currentEvent[1]) {
                case "before test execution": {
                    if (testBegin === 0) {
                        testBegin = currentEvent[0];
                    } else {
                        console.error("An error occurred while parsing the file!");
                    }
                }; break;
                case "after test execution": {
                    if (testBegin === 0) {
                        console.error("An error occurred while parsing the file!");
                    } else {
                        currentClass.tests.push({
                            name: currentEvent[3],
                            duration: currentEvent[0] - testBegin,
                            succeeded: currentEvent[4] === "false"
                        });
                        testDurationSum += currentEvent[0] - testBegin;
                        testBegin = 0;
                    }
                }; break;
                case "context refreshed": {
                    currentClass.spring += currentEvent[0] - currentClassTimestamps[index-1][0];
                    currentClass.newContexts++;
                };
            }
        });

        currentClass.duration = currentClass.end - currentClass.begin;
        currentClass.other = currentClass.duration - testDurationSum - currentClass.spring;

        if (currentClass.tests.every(test => test.succeeded))
            currentClass.testStatus = "success";
        else if (currentClass.tests.every(test => !test.succeeded))
            currentClass.testStatus = "failure";
        else
            currentClass.testStatus = "partial";

        classesDurations.push(currentClass);
    });
    return classesDurations;
}
