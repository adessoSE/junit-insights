"use strict";

/**
 * General application specific methods
 */

/**
 * Starts processing the data after the site fully loaded
 */
window.onload = function () {
    processData(csvString);
    $('[data-toggle="tooltip"]').tooltip();     // enable bootstrap tooltips on initialization
    drawPerTestCharts();
};

/**
 * Click handler for the refresh button
 * Applies filters and ordering for the individual test charts
 */
function refreshClicked() {
    let individualChart = document.getElementById("individualChart");
    while (individualChart.firstChild) {
        individualChart.removeChild(individualChart.firstChild);
    }

    applyFilters();
    orderList();

    prepareChartElements();
}

/**
 * Click handler for the clear button
 * Clears the filter fields and refreshes the list of test classes
 */
function clearClicked() {
    let formData = document.getElementById("filter_form");
    formData.elements[0].value = "";
    formData.elements[1].value = "";
    formData.elements[2].value = "";
    formData.elements[3].value = "";
    formData.elements[4].checked = false;
    formData.elements[6].checked = true;
    formData.elements[7].checked = true;
    formData.elements[8].checked = true;

    refreshClicked()
}

function orderToggled(button) {
    if (button.value === "\u21A7")
        button.value = "\u21A5";
    else
        button.value = "\u21A7";
}

/**
 * Refreshes the set of currently displayed test classes
 * according to the filter and ordering settings the user applied.
 */
function applyFilters() {
    let formData = document.getElementById("filter_form");
    let minSpringShare = formData.elements[0].value;
    let maxSpringShare = formData.elements[1].value;
    let minTestTime = formData.elements[2].value;
    let maxTestTime = formData.elements[3].value;
    let includeSpringTests = formData.elements[5].checked;
    let includeNonSpringTests = formData.elements[6].checked;
    let includeSucceededTests = formData.elements[8].checked;
    let includePartialTests = formData.elements[9].checked;
    let includeFailedTests = formData.elements[10].checked;

    // set default values if the fields are left blank
    if (minSpringShare === "") {
        minSpringShare = 0;
    }
    if (maxSpringShare === "") {
        maxSpringShare = 100;
    }
    if (minTestTime === "") {
        minTestTime = 0;
    }
    if (maxTestTime === "") {
        maxTestTime = Infinity;
    }

    classesDurations = [];
    allClassesDurations.forEach(function (currentClass) {
        if (currentClass.spring / currentClass.duration < minSpringShare / 100 ||
            currentClass.spring / currentClass.duration > maxSpringShare / 100 ||
            currentClass.duration < minTestTime ||
            currentClass.duration > maxTestTime ||
            (!includeSpringTests && currentClass.newContexts > 0) ||
            (!includeNonSpringTests && currentClass.newContexts < 1) ||
            (!includeSucceededTests && currentClass.testStatus === "success") ||
            (!includeFailedTests && currentClass.testStatus === "failure") ||
            (!includePartialTests && currentClass.testStatus === "partial")
        )
            return;
        classesDurations.push(currentClass);
    });
}

function orderList() {
    let selector = document.getElementById("order_select");
    let orderUpDown = document.getElementById("order_up_down");
    let orderBy = selector.options[selector.selectedIndex].value;

    switch (orderBy) {
        case "execution_order":
            break;
        case "duration":
            classesDurations = classesDurations.sort(function (a, b) {
                return (a.duration > b.duration) ? 1 : ((b.duration > a.duration) ? -1 : 0)
            });
            break;
        case "spring":
            classesDurations = classesDurations.sort(function (a, b) {
                return (a.spring > b.spring) ? 1 : ((b.spring > a.spring) ? -1 : 0)
            });
            break;
        case "tests_only":
            classesDurations = classesDurations.sort(function (a, b) {
                let actualA = a.duration - a.spring - a.other;
                let actualB = b.duration - b.spring - b.other;
                return (actualA > actualB) ? 1 : ((actualB > actualA) ? -1 : 0)
            });
            break;
        default:
            console.log("Something went wrong with the order selection element.");
            return
    }
    if (orderUpDown.value === "\u21A5")
        classesDurations = classesDurations.reverse();
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
