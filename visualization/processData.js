function processData(file) {
    Papa.parse(file, {
        complete: function(results) {
            var data = results.data.filter(element => element.length === 4);
            data.shift();
            var classesTimestamps = splitIntoClasses(data);
            console.log(classesTimestamps);
            var classesDurations = calculateDurations(classesTimestamps);
            console.log(classesDurations);
        }
    });
}

function splitIntoClasses(dataIn) {
    var classesTimestamps = [];
    var currentClass = [];
    console.log(dataIn);
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
                    testDurationSum += dataIn[i][j][0] - testBegin;
                    testBegin = 0;
                }
            }
        }
        currentClass["tests"] = testDurations;
        currentClass["spring"] = currentClass["end"] - currentClass["begin"] - testDurationSum;

        classesDurations.push(currentClass);
    }
    return classesDurations;
}