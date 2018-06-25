"use strict";

let information = new Vue({
    el: '#information',
    data: {
        showNoTime: false,
        general: {
            createdSpringContexts: 0,
            testedClasses: 0,
            testedMethods: 0
        },
        individualCharts: []
    }
});
