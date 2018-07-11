"use strict";

/**
 * Methods for creating the charts displaying the results of the data processing
 */

/**
 * Creates a plot that shows the time spent on spring initialization vs. actual test execution
 */
function drawOverviewChart() {
    let springTime = 0;
    let otherTime = 0;
    let testTime = 0;

    information.individualCharts.forEach(function (currentChart) {
        let currentClass = currentChart.data;
        springTime += currentClass.spring;
        otherTime += currentClass.other;
        currentClass.tests.forEach(function (currentTest) {
            testTime += currentTest.duration;
        })
    });

    function getChartObject(x, text, color) {
        return {
            x: [x],
            marker: {
                color: color
            },
            text: text + " (" + x + "ms)",
            textposition: "auto",
            hoverinfo: "none",
            type: "bar",
            orientation: "h"
        }
    }

    let barChartData = [
        getChartObject(springTime, "Spring", "rgb(109, 179, 63)"),
        getChartObject(testTime, "Tests", "rgb(220, 82, 74)"),
        getChartObject(otherTime, "Other", "rgb(180, 180, 180)")];

    if (testTime + springTime + otherTime <= 0) {
        information.showNoTime = true;
    } else {
        Plotly.newPlot("overviewChart", barChartData, overviewChartLayout);
    }
}

/**
 * Fills in the general information about the observed tests
 */
function showGeneralData() {
    let createdSpringContexts = 0;
    let testedMethods = 0;
    information.individualCharts.forEach(function (currentChart) {
        createdSpringContexts += currentChart.data.newContexts;
        testedMethods += currentChart.data.tests.length;
    });
    information.general.createdSpringContexts = createdSpringContexts;
    information.general.testedMethods = testedMethods;
    information.general.testedClasses = information.individualCharts.length;
}

/**
 * Creates a plot that shows the time spent on different events for each test class individually
 */
function prepareChartElements(classesDurations) {
    let currentChart;

    function getChartObject(x, text, color) {
        return {
            x: [x],
            text: text + " (" + x + "ms)",
            textposition: "inside",
            marker: {
                color: color
            },
            hoverinfo: "none",
            type: "bar",
            orientation: "h"
        }
    }

    classesDurations.forEach(function (currentClass, i) {
        currentChart = [getChartObject(currentClass.spring, "Spring", "rgb(109, 179, 63)")];

        for (let j = 0, col = 0; j < currentClass.tests.length; j++) {
            currentChart.push(getChartObject(currentClass.tests[j].duration, currentClass.tests[j].name, colors[col]));
            col = col < 18 ? col + 1 : 0;
        }

        currentChart.push(getChartObject(currentClass.other, "Other", "rgb(180, 180, 180)"));

        let textColor;
        if (currentClass.testStatus === "partial")
            textColor = "orange";
        else if (currentClass.testStatus === "failure")
            textColor = "red";
        else
            textColor = "black";

        let individualChart = {
            data: currentClass,
            chart: currentChart,
            col: textColor,
            chartid: "individualChart" + i
        };

        allIndividualCharts.push(individualChart);
    });
    information.individualCharts = allIndividualCharts;
}

async function drawPerTestCharts() {
    await new Promise(resolve => setTimeout(resolve, 50));      //sleep for 50ms to ensure that the divs are created first
    information.individualCharts.forEach(function (element) {
        Plotly.newPlot(element.chartid, element.chart, individualChartLayout);
    });
    resizePerTestCharts();
}

function removePerTestCharts() {
    information.individualCharts.forEach(function (element) {
        Plotly.purge(element.chartid);
    })
}

function resizePerTestCharts() {
    information.individualCharts.forEach(function (element) {
        Plotly.Plots.resize(element.chartid, {});
    });
}

window.onresize = resizePerTestCharts;
