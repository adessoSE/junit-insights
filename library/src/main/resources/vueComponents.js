"use strict";

Vue.component('individual-chart', {
    props: ['data', 'col', 'chartid'],
    template: '<div>' +
    '<div class="row" style="margin-top: 30px">' +
    '<h4 v-bind:style="{color: col}">{{ data.name }} ({{ data.duration }}ms)</h4>' +
    '</div>' +
    '<p v-bind:id="chartid" v-if="data.duration <= 0">The test took no measurable time to complete</p>' +
    '<div class="row" v-bind:id="chartid" v-else></div>' +
    '</div>'
});
