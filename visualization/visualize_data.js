"use strict";

var DOM = {};
DOM._individualChartContainer = null;
DOM.individualChartContainer = function() {
    if (DOM._individualChartContainer == null) {
        DOM._individualChartContainer = document.getElementById("individualChart");
    }
    return DOM._individualChartContainer;
};

/**
 * Creates a plot that shows the time spent on spring initialization vs. actual test execution
 * @param allClasses All data about the durations of important events for each test class
 */
function drawOverviewBar(allClasses) {
    var springTime = 0;
    var testTime = 0;
    allClasses.forEach(function (currentClass) {
        springTime += currentClass.spring;
        currentClass.tests.forEach(function (currentTest) {
            testTime += currentTest;
        })
    });

    var barChartData = [{
        x: [springTime],
        marker: {
            color: "rgb(109, 179, 63)"
        },
        text: "Spring (" + springTime + "ms)",
        textposition: "auto",
        hoverinfo: "none",
        type: "bar",
        orientation: "h"
    }, {
        x: [testTime],
        marker: {
            color: "rgb(220, 82, 74)"
        },
        text: "Tests (" + testTime + "ms)",
        textposition: "auto",
        hoverinfo: "none",
        type: "bar",
        orientation: "h"
    }];

    var barChartLayout = {
        showlegend: false,
        font: {
            family: "Oswald",
            size: 16
        },
        barmode: "stack",
        xaxis: {
            zeroline: false,
            showgrid: false,
            showticklabels: false
        },
        yaxis: {
            showticklabels: false
        },
        margin: {
            l: 10,
            r: 10,
            b: 10,
            t: 10,
            pad: 4
        },
        height: 200
    };

    Plotly.newPlot("overviewChart", barChartData, barChartLayout);
}

/**
 * Creates a plot that shows the time spent on different events for each test class individually
 * @param allClasses All data about the durations of important events for each test class
 */
function drawPerTestBar(allClasses) {
    var colors = ["rgb(179, 77, 102)",
        "rgb(238, 223, 123)",
        "rgb(60, 125, 160)",
        "rgb(206, 148, 107)",
        "rgb(123, 66, 141)",
        "rgb(137, 222, 222)",
        "rgb(198, 103, 193)",
        "rgb(215, 232, 140)",
        "rgb(229, 199, 199)",
        "rgb(53, 117, 117)",
        "rgb(220, 200, 232)",
        "rgb(148, 118, 83)",
        "rgb(250, 248, 223)",
        "rgb(98, 34, 34)",
        "rgb(198, 240, 210)",
        "rgb(124, 124, 60)",
        "rgb(239, 219, 202)",
        "rgb(21, 21, 85)",
        "rgb(128, 128, 128)"];
    var otherColor, otherText, currentChart, chartDiv, chartLabel, chartLabelDiv;

    allClasses.forEach(function (currentClass, i) {
        if (currentClass.newContexts === 0) {
            otherColor = "rgb(180, 180, 180)";
            otherText = "Other";
        } else {
            otherColor = "rgb(109, 179, 63)";
            otherText = "Spring";
        }

        currentChart = [{
            x: [currentClass.spring],
            text: otherText + " (" + currentClass.spring + "ms)",
            textposition: "inside",
            marker: {
                color: otherColor
            },
            hoverinfo: "none",
            type: "bar",
            orientation: "h"
        }];

        for (var j = 0; j < currentClass.tests.length; j++) {
            currentChart.push({
                x: [currentClass.tests[j]],
                text: currentClass.testNames[j] + "(" + currentClass.tests[j] + "ms)",
                textposition: "inside",
                marker: {
                    color: colors[j]
                },
                hoverinfo: "none",
                type: "bar",
                orientation: "h"
            });
        }

        var individualChartLayout = {
            showlegend: false,
            font: {
                family: "Oswald",
                size: 16
            },
            barmode: "stack",
            xaxis: {
                zeroline: false,
                showgrid: false,
                showticklabels: false
            },
            yaxis: {
                showticklabels: false
            },
            margin: {
                l: 10,
                r: 10,
                b: 10,
                t: 10,
                pad: 4
            },
            height: 150
        };

        chartLabelDiv = document.createElement("div");
        chartLabelDiv.setAttribute("class", "row");
        chartLabelDiv.style.marginTop = "30px";
        chartLabel = document.createElement("h4");
        chartLabel.innerHTML = currentClass.name + " (" + currentClass.duration + "ms)";
        chartLabelDiv.appendChild(chartLabel);
        DOM.individualChartContainer().appendChild(chartLabelDiv);

        chartDiv = document.createElement("div");
        chartDiv.setAttribute("class", "row");
        chartDiv.setAttribute("id", "individualChart" + i);
        DOM.individualChartContainer().appendChild(chartDiv);

        Plotly.newPlot("individualChart" + i, currentChart, individualChartLayout);
    });
}

window.onresize = function () {
    DOM.individualChartContainer().childNodes.forEach(function (child) {
        if (child.id.startsWith("individualChart")) {
            Plotly.relayout(child.id, {autosize: true});   // fit the plots in a resized window again
        }
    });
};
