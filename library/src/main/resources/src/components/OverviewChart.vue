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
                preparationTime: 120,
                executionTime: 50,
                tearDownTime: 75
            };
        },
        created: function () {
            this.layout.height = 200;
            this.preparationTime = this.testClasses
                .map(tc => tc.methods)
                .reduce((acc, val) => acc.concat(val), []) // .flat() not yet implemented
                .reduce((sum, method) => (sum += method.before), 0);
            this.executionTime = this.testClasses
                .map(tc => tc.methods)
                .reduce((acc, val) => acc.concat(val), []) // .flat() not yet implemented
                .reduce((sum, method) => (sum += method.exec), 0);
            this.tearDownTime = this.testClasses
                .map(tc => tc.methods)
                .reduce((acc, val) => acc.concat(val), []) // .flat() not yet implemented
                .reduce((sum, method) => (sum += method.after), 0);
            this.chartEntries = [
                this.getChartEntry(
                    this.preparationTime,
                    "Preparation",
                    "rgb(109, 179, 63)"
                ),
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
