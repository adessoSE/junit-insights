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
                this.getChartEntry(this.springTime, "Spring", "rgb(109, 82, 180"),
                this.getChartEntry(this.preparationTime, "Preparation", "rgb(109, 179, 63)"),
                this.getChartEntry(this.executionTime, "Execution", "rgb(220, 82, 74)"),
                this.getChartEntry(this.tearDownTime, "Tear-Down", "rgb(180, 180, 180)")
            ];
        }
    };
</script>

<style scoped>
    .overviewChart {
        border: solid 1px lightgray;
        border-radius: 6px;
        padding: 10px;
        margin: 10px;
    }
</style>
