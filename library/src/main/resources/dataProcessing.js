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
    let currentClass, testDurations, testNames, testSuccess, testDurationSum, testBegin;
    classesTimestamps.forEach(function (currentClassTimestamps) {
        currentClass = {};
        currentClass.name = currentClassTimestamps[0][2];
        currentClass.begin = currentClassTimestamps[0][0];
        currentClass.end = currentClassTimestamps[currentClassTimestamps.length - 1][0];
        currentClass.newContexts = 0;
        currentClass.spring = 0;
        testDurations = [];
        testNames = [];
        testSuccess = [];
        testDurationSum = 0;
        testBegin = 0;

        currentClassTimestamps.forEach(function (currentEvent, index) {
            if (currentEvent[1] === "before test execution") {    // "before test execution" is the first event for each test
                if (testBegin === 0) {
                    testBegin = currentEvent[0];
                } else {
                    console.error("An error occurred while parsing the file!");
                }
            } else if (currentEvent[1] === "after test execution") {  // "after test execution" marks termination of the test
                if (testBegin === 0) {
                    console.error("An error occurred while parsing the file!");
                } else {
                    testDurations.push(currentEvent[0] - testBegin);
                    testNames.push(currentEvent[3]);
                    testDurationSum += currentEvent[0] - testBegin;
                    testBegin = 0;
                    testSuccess.push(currentEvent[4] === "false")
                }
            } else if (currentEvent[1] === "context refreshed") {
                currentClass.spring += currentEvent[0] - currentClassTimestamps[index-1][0];
                currentClass.newContexts++;
            }
        });

        currentClass.tests = testDurations;
        currentClass.testNames = testNames;
        currentClass.duration = currentClass.end - currentClass.begin;
        currentClass.other = currentClass.duration - testDurationSum - currentClass.spring;

        currentClass.testSuccess = testSuccess;
        if (currentClass.testSuccess.every(t => t))
            currentClass.testStatus = "success";
        else if (currentClass.testSuccess.every(t => !t))
            currentClass.testStatus = "failure";
        else
            currentClass.testStatus = "partial";

        classesDurations.push(currentClass);
    });
    return classesDurations;
}
