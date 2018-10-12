<template>
    <div class="classChart">
        <h2>{{this.testClass.name}} ({{ this.totalTime(this.testClass) }}ms)</h2>
        <div v-if="shouldDraw()">
            <div :id="chartId" class="chartCanvas"></div>
            <button @click="expanded = !expanded">{{expanded ? "-" : "+"}}</button>
            <keep-alive v-if="expanded">
                <method-chart v-for="method in testClass.methods"
                              :key="method.name"
                              :test-method="method"
                              :chartId="method.name"/>
            </keep-alive>
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
        },
        methods: {
            shouldDraw: function () {
                return this.totalTime(this.testClass) >= 5;
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
</style>
