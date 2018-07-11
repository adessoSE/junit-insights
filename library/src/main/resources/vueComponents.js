"use strict";

Vue.component('individual-chart', {
    props: ['data', 'col', 'chartid'],
    data: function () {
        return {
            expanded: false
        }
    },
    template: `
    <div class="test-class-container">
        <div class="row">
            <h4 v-bind:style="{color: col}">{{ data.name }} ({{ data.duration }}ms)</h4>
        </div>
        <p v-if="data.duration <= 0" v-bind:id="chartid">The test took no measurable time to complete</p>
        <div v-else class="row chartrow" v-bind:id="chartid"></div>
        <button type="button" class="btn" v-on:click="expanded = !expanded">{{expanded ? "&#x25BC;" : "&#x25BA;"}}</button>
        <single-test-case-chart 
            v-if="expanded"
            v-for="i in data.tests.length"
            v-bind:key="i-1"
            v-bind:test="data.tests[i-1]"
        >
        </single-test-case-chart>
    </div>`
});

/*
    Almost the same thing but with a smaller heading and a different CSS class for styling
    (TODO: What is 'col' above?)
    (TODO: Migrate to x-templates?)
*/
Vue.component('single-test-case-chart', {
    props: ['test', 'chartid'],
    template: `
    <div class="single-test-case-chart">
        <div class="row"><h5>{{ test.name }} ({{ test.duration }}ms)</h5></div>
        <p v-if="test.duration <= 0" v-bind:id="chartid">The test took no measurable time to complete</p>
        <div v-else class="row" v-bind:id="chartid"></div>
    </div>`
});
