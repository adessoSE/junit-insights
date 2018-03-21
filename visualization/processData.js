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
            showGeneralData(classesDurations);
            document.getElementById("information").style.visibility="visible";
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
        currentClass["newContexts"] = 0;
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
            } else if (dataIn[i][j][1] === "context refreshed") {
                currentClass["newContexts"]++;
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

function showGeneralData(classesData) {
    var createdSpringContexts = 0;
    var testedMethods = 0;
    for (var i = 0; i < classesData.length; i++) {
        createdSpringContexts += classesData[i]["newContexts"];
        testedMethods += classesData[i]["tests"].length;
    }
    document.getElementById("createdSpringContexts").innerHTML = "Created Spring contexts: " + createdSpringContexts;
    document.getElementById("testedClasses").innerHTML = "Tested classes: " + classesData.length;
    document.getElementById("testedMethods").innerHTML = "Tested methods: " + testedMethods;
}