"use strict";

Vue.component('individual-chart', {
    props: ['data', 'col', 'chartid'],
    data: function () {
        return {
            expanded: false
        }
    },
    template: "#individualCharts-template"
});

Vue.component('single-test-case-chart', {
    props: ['test', 'chartid'],
    template: "#single-test-case-chart-template"
});
