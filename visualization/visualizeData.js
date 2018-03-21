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

        var textColor = classesData[i]["newContexts"] === 0 ? "black" : "red";

        annotations.push({
            xanchor: "center",
            yanchor: "center",
            showarrow: false,
            text: classesData[i]["name"] + " (" + classesData[i]["duration"] + "ms)",
            font: {
                color: textColor
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
