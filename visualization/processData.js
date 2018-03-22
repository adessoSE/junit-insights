"use strict";

function uploadFile() {
    var inputElement = document.getElementById("csv_input");
    var inputFile = inputElement.files[0];
    processData(inputFile);
}

function processData(file) {
    Papa.parse(file, {
        complete: function(content) {
            var data = content.data.filter(function(element) {
                return element.length === 4;
            });
            data.shift();
            var classesTimestamps = splitIntoClasses(data);
            var classesDurations = calculateDurations(classesTimestamps);
            console.log(classesDurations);
            drawOverviewPie(classesDurations);
            drawPerTestPie(classesDurations);
            showGeneralData(classesDurations);
            document.getElementById("information").style.visibility="visible";
        }
    });
}

function splitIntoClasses(rawData) {
    var classesTimestamps = [];
    var currentClass = [];
    rawData.forEach(function(entry) {
        currentClass.push(entry);
        if (entry[1] === "after all") {
            classesTimestamps.push(currentClass);
            currentClass = [];
        }
    });
    return classesTimestamps;
}

function calculateDurations(classesTimestamps) {
    var classesDurations = [];
    var currentClass, testDurations, testNames, testDurationSum, testBegin;
    classesTimestamps.forEach(function(currentClassTimestamps) {
        currentClass = {};
        currentClass.name = currentClassTimestamps[0][2];
        currentClass.begin = currentClassTimestamps[0][0];
        currentClass.end = currentClassTimestamps[currentClassTimestamps.length-1][0];
        currentClass.newContexts = 0;
        testDurations = [];
        testNames = [];
        testDurationSum = 0;
        testBegin = 0;
        currentClassTimestamps.forEach(function(currentEvent) {
            if (currentEvent[1] === "before each") {
                if (testBegin === 0) {
                    testBegin = currentEvent[0];
                } else {
                    console.error("An error occurred while parsing the file!");
                }
            } else if (currentEvent[1] === "after each") {
                if (testBegin === 0) {
                    console.error("An error occurred while parsing the file!");
                } else {
                    testDurations.push(currentEvent[0] - testBegin);
                    testNames.push(currentEvent[3]);
                    testDurationSum += currentEvent[0] - testBegin;
                    testBegin = 0;
                }
            } else if (currentEvent[1] === "context refreshed") {
                currentClass.newContexts++;
            }
        });
        currentClass.tests = testDurations;
        currentClass.testNames = testNames;
        currentClass.duration = currentClass.end - currentClass.begin;
        currentClass.spring = currentClass.duration - testDurationSum;

        classesDurations.push(currentClass);
    });
    return classesDurations;
}

function showGeneralData(allClasses) {
    var createdSpringContexts = 0;
    var testedMethods = 0;
    allClasses.forEach(function(currentClass) {
        createdSpringContexts += currentClass.newContexts;
        testedMethods += currentClass.tests.length;
    });
    document.getElementById("createdSpringContexts").innerHTML = "Created Spring contexts: " + createdSpringContexts;
    document.getElementById("testedClasses").innerHTML = "Tested classes: " + allClasses.length;
    document.getElementById("testedMethods").innerHTML = "Tested methods: " + testedMethods;
}
