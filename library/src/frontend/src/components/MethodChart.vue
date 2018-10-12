<template>
    <div>
        <h3>{{this.testMethod.name}}</h3>
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
                this.getChartEntry(this.testMethod.before, "Before", this.BEFORE_COLOR),
                this.getChartEntry(this.testMethod.exec, "Execution", this.EXEC_COLOR),
                this.getChartEntry(this.testMethod.after, "After", this.AFTER_COLOR)
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
