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
    let durations = [];
    let currentClass;

    classesTimestamps.forEach(function (currentClassTimestamps) {
        currentClass = {
            name: currentClassTimestamps[0][2],
            timeBeforeFirst: 0,
            timeSumOfBefore: 0,
            timeForExecution: 0,
            timeBetweenAll: 0,
            timeAfterAll: 0,
            tests: []
        };

        currentClass.timeBeforeFirst = findTimeBeforeFirstForClass(currentClassTimestamps);
        currentClass.timeAfterAll = findTimeAfterAllForClass(currentClassTimestamps);

        let timestampBeforeEach = 0,
            timestampBeforeExecution = 0,
            timestampAfterExecution = 0,
            timestampAfterEach = 0;

        currentClassTimestamps.forEach(function (currentEvent, index) {
            switch (currentEvent[1]) {
                case "before each": timestampBeforeEach = currentEvent[0]; break;
                case "before test execution": { assert(timestampBeforeExecution === 0); timestampBeforeExecution = currentEvent[0];}; break;
                case "after test execution": { assert(timestampBeforeExecution !== 0); timestampAfterExecution = currentEvent[0];}; break;
                case "after each": {
                    timestampAfterEach = currentEvent[0];
                    timestampBeforeExecution = 0;

                    let timeSpentBeforeTest = timestampBeforeExecution - timestampBeforeEach;
                    let timeSpentForExecution = timestampAfterExecution - timestampBeforeExecution;
                    let timeSpentAfterTest = timestampAfterEach - timestampAfterExecution;

                    currentClass.tests.push({
                        name: currentEvent[3],
                        timeSpentBeforeTest: timeSpentBeforeTest,
                        timeSpentForExecution: timeSpentForExecution,
                        timeSpentAfterTest: timeSpentAfterTest,
                        succeeded: currentEvent[4] === "false"
                    });

                    currentClass.timeSumOfBefore += timeSpentBeforeTest;
                    currentClass.timeForExecution += timeSpentForExecution;
                    currentClass.timeBetweenAll += timestampBeforeEach - timestampAfterEach;
                }; break;
            }
        });

        if (currentClass.tests.every(test => test.succeeded))
            currentClass.testStatus = "success";
        else if (currentClass.tests.every(test => !test.succeeded))
            currentClass.testStatus = "failure";
        else
            currentClass.testStatus = "partial";

        durations.push(currentClass);
    });
    return durations;
}

function findTimeBeforeFirstForClass(currentClassTimestamps) {
    let timestampOfFirstBeforeEach = currentClassTimestamps.filter(entry => entry[1] === "before each")[0][0];
    assert(currentClassTimestamps[0][1] === "before all", "First event for class is not 'before all'");
    let timestampOfBeforeAll = currentClassTimestamps[0][0];
    return timestampOfFirstBeforeEach - timestampOfBeforeAll;
}

function findTimeAfterAllForClass(currentClassTimestamps) {
    var afterEachEvents = currentClassTimestamps.filter(entry => entry[1] === "after each");
    var lastAfterEachEvent = afterEachEvents[afterEachEvents.length - 1];

    var afterAllEvents = currentClassTimestamps.filter(entry => entry[1] === "after all");
    var lastAfterAllEvent = afterAllEvents[afterAllEvents.length - 1];

    return lastAfterAllEvent[0] - lastAfterEachEvent[0];
}

function assert(condition, message) {
    if (!condition) {
        message = message || "Assertion failed";
        if (typeof Error !== "undefined") {
            throw new Error(message);
        }
        throw message; // Fallback
    }
}
