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
        <button type="button" class="btn" v-on:click="expanded = !expanded">{{expanded ? "▼" : "►"}}</button>
        <single-test-case-chart 
            v-if="expanded"
            v-for="i in [1,2,3]"
            v-bind:key="i"
            v-bind:data="i"
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
    props: ['data', 'chartid'],
    template: `
    <div class="single-test-case-chart">
        <div class="row"><h5>{{ data }} ({{ data }}ms)</h5></div>
        <p v-if="data.duration <= 0" v-bind:id="chartid">The test took no measurable time to complete</p>
        <div v-else class="row" v-bind:id="chartid"></div>
    </div>`
});
