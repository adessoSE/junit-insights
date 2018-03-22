"use strict";

/**
 * Creates a plot that shows the time spent on spring initialization vs. actual test execution
 * @param allClasses All data about the durations of important events for each test class
 */
function drawOverviewPie(allClasses) {
    var springTime = 0;
    var testTime = 0;
    allClasses.forEach(function(currentClass) {
        springTime += currentClass.spring;
        currentClass.tests.forEach(function(currentTest) {
            testTime += currentTest;
        })
    });

    var pieChartData = [{
        values: [springTime, testTime],
        labels: ["Spring", "Tests"],
        marker: {
            colors: ["rgb(109,179,63)","rgb(220,82,74)"]
        },
        textinfo: "percent+label+value",
        hoverinfo: "none",
        type: "pie"
    }];

    var pieChartLayout = {
        showlegend: false,
        font: {
            family: "Oswald",
            size:16
        }
    };

    Plotly.newPlot("overviewChart", pieChartData, pieChartLayout);
}

/**
 * Creates a plot that shows the time spent on different events for each test class individually
 * @param allClasses All data about the durations of important events for each test class
 */
function drawPerTestPie(allClasses) {
    var data = [];
    var countY = Math.ceil(allClasses.length / 3);
    var heightPerPie = 1/countY;
    var rowNumber = countY-1;   // because y grows towards the top of the page, start with the last row
    var columnNumber = 0;
    var annotations = [];
    var textColor, otherColor, otherText, currentData;
    allClasses.forEach(function(currentClass) {
        if (currentClass.newContexts === 0) {
            textColor = "black";
            otherColor = "rgb(180, 180, 180)";
            otherText = "Other";
        } else {
            textColor = "green";
            otherColor = "rgb(109, 179, 63)";
            otherText = "Spring";
        }

        currentData = {
            values: [currentClass.spring],
            labels: [otherText],
            title: currentClass.name,
            marker: {
                colors: [otherColor,
                    "rgb(179, 77, 102)",
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
                    "rgb(128, 128, 128)"]
            },
            hoverinfo: "label",
            textinfo: "percent",
            domain: {
                x: [columnNumber*0.33,columnNumber*0.33+0.33],
                y: [rowNumber*heightPerPie,rowNumber*heightPerPie+(heightPerPie*0.8)]
            },
            type: "pie"
        };

        for (var j = 0; j < currentClass.tests.length; j++) {
            currentData.values.push(currentClass.tests[j]);
            currentData.labels.push(currentClass.testNames[j]);
        }

        data.push(currentData);

        annotations.push({
            xanchor: "center",
            yanchor: "center",
            showarrow: false,
            text: currentClass.name + " (" + currentClass.duration + "ms)",
            font: {
                family: "Oswald",
                color: textColor,
                size: 16
            },
            x: (columnNumber*0.66 + 0.33)/2,
            y: (rowNumber-0.07)*heightPerPie
        });

        if (columnNumber === 2) {
            columnNumber = 0;
            rowNumber--;
        } else {
            columnNumber++;
        }
    });

    var individualChartLayout = {
        showlegend: false,
        font: {
            family: "Oswald",
            size: 16
        },
        annotations: annotations,
        height: (countY)*400
    };

    Plotly.newPlot("individualChart", data, individualChartLayout);
}

window.onresize = function() {
    Plotly.relayout("individualChart", {autosize: true});   // fit the plots in a resized window again
};
