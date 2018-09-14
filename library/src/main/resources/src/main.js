import App from './App.vue'
import Vue from 'vue'
import 'bootstrap/dist/css/bootstrap.min.css'

var report = {
  "projectName": "JUnit Insights Report 2018-09-11-13:59:467",
  "created": "Aug 29, 2018 10:21:27 AM",
  "springContextsCreated": 6,
  "testClasses": [
    {
      "name": "AnotherHelloControllerTest",
      "methods": [
        {
          "name": "getAnotherHello()",
          "before": 125,
          "exec": 78,
          "after": 0,
          "successful": true
        },
        {
          "name": "getHello()",
          "before": 0,
          "exec": 0,
          "after": 0,
          "successful": true
        }
      ],
      "beforeAll": 3195,
      "before": 125,
      "exec": 78,
      "after": 0,
      "afterAll": 16,
      "between": 0
    },
    {
      "name": "HelloControllerTest",
      "methods": [
        {
          "name": "Second test method",
          "before": 0,
          "exec": 0,
          "after": 31,
          "successful": true
        },
        {
          "name": "First test method",
          "before": 0,
          "exec": 0,
          "after": 0,
          "successful": true
        }
      ],
      "beforeAll": 16,
      "before": 0,
      "exec": 0,
      "after": 31,
      "afterAll": 0,
      "between": 0
    },
    {
      "name": "NewTest",
      "methods": [
        {
          "name": "basicTest()",
          "before": 0,
          "exec": 16,
          "after": 0,
          "successful": true
        },
        {
          "name": "basicTest3()",
          "before": 0,
          "exec": 0,
          "after": 0,
          "successful": true
        },
        {
          "name": "delayTest()",
          "before": 15,
          "exec": 313,
          "after": 0,
          "successful": true
        }
      ],
      "beforeAll": 984,
      "before": 15,
      "exec": 329,
      "after": 0,
      "afterAll": 0,
      "between": 0
    },
    {
      "name": "CalculatorTest",
      "methods": [
        {
          "name": "basicArithmetic()",
          "before": 0,
          "exec": 0,
          "after": 0,
          "successful": true
        }
      ],
      "beforeAll": 0,
      "before": 0,
      "exec": 0,
      "after": 0,
      "afterAll": 0,
      "between": 0
    }
  ]
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
