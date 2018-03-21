function uploadFile() {
    inputElement = document.getElementById("csv_input");
    var inputFile = inputElement.files[0];
    processData(inputFile);
}

function processData(file) {
    Papa.parse(file, {
        complete: function(results) {
            var data = results.data.filter(element => element.length === 4);
            data.shift();
            var classesTimestamps = splitIntoClasses(data);
            var classesDurations = calculateDurations(classesTimestamps);
            console.log(classesDurations);
            drawOverviewPie(classesDurations);
            drawPerTestPie(classesDurations);
        }
    });
}

function splitIntoClasses(dataIn) {
    var classesTimestamps = [];
    var currentClass = [];
    for (var i = 0; i < dataIn.length; i++) {
        currentClass.push(dataIn[i]);
        if (dataIn[i][1] === "after all") {
            classesTimestamps.push(currentClass);
            currentClass = [];
        }
    }
    return classesTimestamps;
}

function calculateDurations(dataIn) {
    var classesDurations = [];
    for (var i = 0; i < dataIn.length; i++) {
        var currentClass = {};
        currentClass["name"] = dataIn[i][0][2];
        currentClass["begin"] = dataIn[i][0][0];
        currentClass["end"] = dataIn[i][dataIn[i].length-1][0];
        var testDurations = [];
        var testNames = [];
        var testDurationSum = 0;
        var testBegin = 0;
        for (var j = 0; j < dataIn[i].length; j++) {
            if (dataIn[i][j][1] === "before each") {
                if (testBegin === 0) {
                    testBegin = dataIn[i][j][0];
                } else {
                    console.error("An error occurred while parsing the file!");
                }
            } else if (dataIn[i][j][1] === "after each") {
                if (testBegin === 0) {
                    console.error("An error occurred while parsing the file!");
                } else {
                    testDurations.push(dataIn[i][j][0] - testBegin);
                    testNames.push(dataIn[i][j][3]);
                    testDurationSum += dataIn[i][j][0] - testBegin;
                    testBegin = 0;
                }
            }
        }
        currentClass["tests"] = testDurations;
        currentClass["testNames"] = testNames;
        currentClass["duration"] = currentClass["end"] - currentClass["begin"];
        currentClass["spring"] = currentClass["duration"] - testDurationSum;

        classesDurations.push(currentClass);
    }
    return classesDurations;
}

function drawOverviewPie(classesData) {
    springTime = 0;
    testTime = 0;
    for (var i = 0; i < classesData.length; i++) {
        springTime += classesData[i]["spring"];
        for (var j = 0; j < classesData[i]["tests"].length; j++) {
            testTime += classesData[i]["tests"][j]
        }
    }

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
        title: "Time spent on Spring initialization vs. test execution"
    };

    Plotly.newPlot("overviewChart", pieChartData, pieChartLayout);
}

function drawPerTestPie(classesData) {
    var data = [];
    var countY = Math.ceil(classesData.length / 3);
    var heightPerPie = 1/countY;
    var rowNumber = countY-1;
    var columnNumber = 0;
    var annotations = [];
    for (var i = 0; i < classesData.length; i++) {
        var currentData = {
            values: [classesData[i]["spring"]],
            labels: ["Spring"],
            title: classesData[i]["name"],
            marker: {
                colors: ["rgb(109, 179, 63)",
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

        for (var j = 0; j < classesData[i]["tests"].length; j++) {
            currentData["values"].push(classesData[i]["tests"][j]);
            currentData["labels"].push(classesData[i]["testNames"][j]);
        }

        data.push(currentData);

        annotations.push({
            xanchor: "center",
            yanchor: "center",
            showarrow: false,
            text: classesData[i]["name"] + " (" + classesData[i]["duration"] + "ms)",
            x: (columnNumber*0.66 + 0.33)/2,
            y: (rowNumber-0.07)*heightPerPie
        });

        if (columnNumber === 2) {
            columnNumber = 0;
            rowNumber--;
        } else {
            columnNumber++;
        }
    }

    var individualChartLayout = {
        showlegend: false,
        title: "Time spent on individual test classes",
        annotations: annotations,
        height: (countY)*400
    };

    Plotly.newPlot("individualChart", data, individualChartLayout);
}

window.onresize = function() {
    Plotly.relayout("individualChart", {autosize: true});
    Plotly.relayout("overviewChart", {autosize:true})
};