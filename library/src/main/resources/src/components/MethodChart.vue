<template>
    <div>
        <h2>{{this.testMethod.name}}</h2>
        <div :id="chartId" v-if="shouldDraw()"></div>
        <div v-else>The method took no measurable time to run.</div>
    </div>
</template>

<script>
    import ChartBase from "./ChartBase.vue";

    export default {
        extends: ChartBase,
        props: ["testMethod"],
        created: function () {
            this.layout.height = 30;
            this.chartEntries = [
                this.getChartEntry(this.testMethod.before, "Before", "rgb(109, 179, 63)"),
                this.getChartEntry(this.testMethod.exec, "Execution", "rgb(180, 79, 63)"),
                this.getChartEntry(this.testMethod.after, "After", "rgb(169, 179, 163)")
            ];
        },
        methods: {
            shouldDraw() {
                var methodTime =
                    this.testMethod.before + this.testMethod.exec + this.testMethod.after;
                return methodTime >= 5;
            }
        }
    };
</script>
