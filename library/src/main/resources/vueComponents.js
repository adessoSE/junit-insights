"use strict";

Vue.component('individual-chart', {
    props: ['data', 'col', 'chartid'],
    template: `
    <div class="test-class-container">
        <div class="row">
            <h4 v-bind:style="{color: col}">{{ data.name }} ({{ data.duration }}ms)</h4>
        </div>
        <p v-if="data.duration <= 0" v-bind:id="chartid">The test took no measurable time to complete</p>
        <div v-else class="row" v-bind:id="chartid"></div>
    </div>`
});
