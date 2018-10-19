<template>
    <div class="classChart">
        <button class="btn btn-sm btn-light expandButton" @click="expanded = !expanded">{{expanded ? "-" : "+"}}</button>
        <h2 style="display: inline;">{{this.testClass.name}} ({{ this.totalTimeClass(this.testClass) }}ms)</h2>
        <div v-if="shouldDraw()">
            <div :id="chartId" class="chartCanvas"></div>
            <div v-if="expanded">
                <method-chart v-for="method in testClass.methods"
                              :key="method.name"
                              :test-method="method"
                              :chartId="method.name"/>
            </div>
        </div>
        <div v-else>
            The tests took no measurable time to run.
        </div>
    </div>
</template>

<script>
    import ChartBase from "./ChartBase.vue";
    import MethodChart from "./MethodChart.vue";
    import ClassProcessing from "../mixins/ClassProcessing.js"

    export default {
        extends: ChartBase,
        props: ["testClass"],
        mixins: [ClassProcessing],
        data() {
            return {
                expanded: false
            };
        },
        created: function () {
            this.chartEntries = [
                this.getChartEntry(this.testClass.spring, "Spring", this.SPRING_COLOR),
                this.getChartEntry(this.testClass.beforeAll, "Before All", this.BEFORE_ALL_COLOR),
                this.getChartEntry(this.testClass.before, "Before", this.BEFORE_COLOR),
                this.getChartEntry(this.testClass.exec, "Exec", this.EXEC_COLOR),
                this.getChartEntry(this.testClass.after, "After", this.AFTER_COLOR),
                this.getChartEntry(this.testClass.between, "Between", this.BETWEEN_COLOR),
                this.getChartEntry(this.testClass.afterAll, "After All", this.AFTER_ALL_COLOR)
            ];
            this.layout.xaxis.range = [0,this.totalTimeClass(this.testClass)];
        },
        methods: {
            shouldDraw: function () {
                return this.totalTimeClass(this.testClass) >= 5;
            }
        },
        components: {
            MethodChart
        }
    };
</script>

<style scoped>
    .classChart {
        border: solid 1px lightgray;
        border-radius: 2px;
        padding: 10px;
        margin: 10px;
    }

    .expandButton {
        display: inline;
        margin-top: -13px;
        font-size: 20px;
        background-color: #FFFFFF;
        width: 38px;
        height: 38px;
    }
</style>
