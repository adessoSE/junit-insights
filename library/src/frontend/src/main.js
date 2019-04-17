import App from './App.vue'
import Vue from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'

// Dummy data for testing purposes
var report = {
    "reportTitle": "JUnit Insights Report 12.04.2019 17:35:47",
    "created": "Apr 12, 2019 5:35:47 PM",
    "springContextsCreated": 3,
    "testClasses": [{
        "name": "AnotherHelloControllerTest",
        "firstTimestamp": 1555083342869,
        "methods": [{
            "name": "getAnotherHello()",
            "firstTimestamp": 1555083345755,
            "before": 92,
            "exec": 82,
            "after": 3,
            "successful": true
        }, {
            "name": "getHello()",
            "firstTimestamp": 1555083345974,
            "before": 1,
            "exec": 3,
            "after": 1,
            "successful": true
        }],
        "beforeAll": 42,
        "before": 93,
        "exec": 85,
        "after": 4,
        "afterAll": 2,
        "between": 0,
        "spring": 2886,
        "springContextCount": 1
    }, {
        "name": "HelloControllerTest",
        "firstTimestamp": 1555083345982,
        "methods": [{
            "name": "Second test method",
            "firstTimestamp": 1555083345999,
            "before": 1,
            "exec": 2,
            "after": 1,
            "successful": true
        }, {
            "name": "First test method",
            "firstTimestamp": 1555083346005,
            "before": 2,
            "exec": 3,
            "after": 2,
            "successful": true
        }],
        "beforeAll": 19,
        "before": 3,
        "exec": 5,
        "after": 3,
        "afterAll": 0,
        "between": 0,
        "spring": 0,
        "springContextCount": 0
    }, {
        "name": "NestedSpringTest",
        "firstTimestamp": 1555083346016,
        "methods": [{
            "name": "gameActive()",
            "firstTimestamp": 1555083346807,
            "before": 1,
            "exec": 3,
            "after": 1,
            "successful": true
        }, {
            "name": "strikeRolled()",
            "firstTimestamp": 1555083346880,
            "before": 1,
            "exec": 1,
            "after": 0,
            "successful": true
        }, {
            "name": "testGutterGame()",
            "firstTimestamp": 1555083346893,
            "before": 1,
            "exec": 0,
            "after": 1,
            "successful": true
        }, {
            "name": "testSpareGame()",
            "firstTimestamp": 1555083346897,
            "before": 1,
            "exec": 1,
            "after": 0,
            "successful": true
        }],
        "beforeAll": 26,
        "before": 4,
        "exec": 5,
        "after": 2,
        "afterAll": 2,
        "between": 3,
        "spring": 850,
        "springContextCount": 2
    }, {
        "name": "NewTest",
        "firstTimestamp": 1555083346910,
        "methods": [{
            "name": "basicTest()",
            "firstTimestamp": 1555083346920,
            "before": 1,
            "exec": 0,
            "after": 0,
            "successful": true
        }, {
            "name": "basicTest3()",
            "firstTimestamp": 1555083346925,
            "before": 1,
            "exec": 1,
            "after": 0,
            "successful": true
        }, {
            "name": "delayTest()",
            "firstTimestamp": 1555083346928,
            "before": 1,
            "exec": 301,
            "after": 1,
            "successful": true
        }],
        "beforeAll": 15,
        "before": 3,
        "exec": 302,
        "after": 1,
        "afterAll": 0,
        "between": 0,
        "spring": 0,
        "springContextCount": 0
    }]
}
// Replace dummy data with real one if content of this variable is set (see index.html)
if (Object.keys(OVERRIDE_REPORT).length > 0)
    report = OVERRIDE_REPORT

new Vue({
    el: '#app',
    render: h => h(App, {
        props: { report: report }
    })
})
