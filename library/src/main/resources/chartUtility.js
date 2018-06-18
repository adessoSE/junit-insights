"use strict";

/**
 * Methods for creating the charts displaying the results of the data processing
 */

let DOM = {};
DOM._individualChartContainer = null;
DOM.individualChartContainer = function () {
    if (DOM._individualChartContainer == null) {
        DOM._individualChartContainer = document.getElementById("individualCharts");
    }
    return DOM._individualChartContainer;
};

/**
 * Creates a plot that shows the time spent on spring initialization vs. actual test execution
 */
function drawOverviewBar() {
    let springTime = 0;
    let otherTime = 0;
    let testTime = 0;
    let infoText;
    classesDurations.forEach(function (currentClass) {
        springTime += currentClass.spring;
        otherTime += currentClass.other;
        currentClass.tests.forEach(function (currentTest) {
            testTime += currentTest;
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
        Plotly.newPlot("overviewChart", barChartData, barChartLayout);
    }
}

/**
 * Fills in the general information about the observed tests
 */
function showGeneralData() {
    let createdSpringContexts = 0;
    let testedMethods = 0;
    classesDurations.forEach(function (currentClass) {
        createdSpringContexts += currentClass.newContexts;
        testedMethods += currentClass.tests.length;
    });
    information.general.createdSpringContexts = createdSpringContexts;
    information.general.testedMethods = testedMethods;
    information.general.testedClasses = classesDurations.length;
}

/**
 * Creates a plot that shows the time spent on different events for each test class individually
 */
function drawPerTestBar() {
    let currentChart, chartDiv, chartLabel, chartLabelDiv;

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
            currentChart.push(getChartObject(currentClass.tests[j], currentClass.testNames[j], colors[col]));
            col = col < 18 ? col+1 : 0;
        }

        currentChart.push(getChartObject(currentClass.other, "Other", "rgb(180, 180, 180)"));

        let col;
        if (currentClass.testStatus === "partial")
            col ="orange";
        else if (currentClass.testStatus === "failure")
            col ="red";
        else
            col ="black";

        information.individualCharts.push({
            data: currentClass,
            chart: currentChart,
            col: col,
            chartid: "individualChart" + i
        })

        /*chartDiv = document.createElement("individual-chart");
        chartDiv.setAttribute("current", currentClass);
        if (currentClass.testStatus === "partial")
            chartDiv.setAttribute("col", "orange");
        else if (currentClass.testStatus === "failure")
            chartDiv.setAttribute("col", "red");
        else
            chartDiv.setAttribute("col", "black");
        chartDiv.setAttribute("chartid", "individualChart" + i);
        DOM.individualChartContainer().appendChild(chartDiv);*/
        //Plotly.newPlot("individualChart" + i, currentChart, individualChartLayout);
        //Plotly.relayout("individualChart" + i, {width: window.innerWidth, height: 150});
    });
}

function resize() {
    DOM.individualChartContainer().childNodes.forEach(function (child) {
        if (child.id.startsWith("individualChart")) {
            Plotly.relayout(child.id, {width: window.innerWidth, height: 150});   // fit the plots in a resized window again
        }
    });
}

window.onresize = resize;
