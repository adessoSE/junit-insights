<template>
    <div class="overviewChart">
        <h2>Overview</h2>
        <div :id="chartId" class="chartCanvas"></div>
    </div>
</template>

<script>
    import ChartBase from "./ChartBase.vue";

    export default {
        extends: ChartBase,
        props: ["testClasses"],
        data() {
            return {
                preparationTime: 0,
                executionTime: 0,
                tearDownTime: 0,
                springTime: 0
            };
        },
        created: function () {
            this.layout.height = 200;
            this.layout.xaxis.range = [0,this.preparationTime + this.executionTime + this.tearDownTime + this.springTime];

            this.preparationTime = this.testClasses.reduce((sum, testClass) => sum += testClass.beforeAll, 0);
            this.tearDownTime = this.testClasses.reduce((sum, testClass) => sum += testClass.afterAll, 0);
            this.springTime = this.testClasses.reduce((sum, testClass) => sum += testClass.spring, 0);

            let flatMethods = this.testClasses
                .map(tc => tc.methods)
                .reduce((acc, val) => acc.concat(val), []); // .flat() not yet implemented
            this.preparationTime += flatMethods.reduce((sum, method) => (sum += method.before), 0);
            this.executionTime = flatMethods.reduce((sum, method) => (sum += method.exec), 0);
            this.tearDownTime += flatMethods.reduce((sum, method) => (sum += method.after), 0);

            this.chartEntries = [
                this.getChartEntry(this.springTime, "Spring", this.SPRING_COLOR),
                this.getChartEntry(this.preparationTime, "Preparation", this.BEFORE_COLOR),
                this.getChartEntry(this.executionTime, "Execution", this.EXEC_COLOR),
                this.getChartEntry(this.tearDownTime, "Tear-Down", this.AFTER_COLOR)
            ];
        }
    };
</script>

<style scoped>
    .overviewChart {
        border: solid 1px lightgray;
        border-radius: 2px;
        padding: 10px;
        margin: 10px;
    }
</style>
