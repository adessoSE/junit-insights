"use strict";

let information = new Vue({
    el: '#information',
    data: {
        visible: true,
        showNoTime: false,
        general: {
            createdSpringContexts: 0,
            testedClasses: 0,
            testedMethods: 0
        }
    }
});