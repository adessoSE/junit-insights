<template>
    <div>
        <h3>{{this.testMethod.name}} ({{this.totalTimeMethod(this.testMethod)}}ms)</h3>
        <div :id="chartId" v-if="shouldDraw()"></div>
        <div v-else>The method took no measurable time to run.</div>
    </div>
</template>

<script>
    import ChartBase from "./ChartBase.vue";
    import ClassProcessing from "../mixins/ClassProcessing.js"

    export default {
        extends: ChartBase,
        props: ["testMethod"],
        mixins: [ClassProcessing],
        created: function () {
            this.layout.height = 30;
            this.chartEntries = [
                this.getChartEntry(this.testMethod.before, "Before", this.BEFORE_COLOR),
                this.getChartEntry(this.testMethod.exec, "Execution", this.EXEC_COLOR),
                this.getChartEntry(this.testMethod.after, "After", this.AFTER_COLOR)
            ];
            this.layout.xaxis.range = [0,this.totalTimeMethod(this.testMethod)]
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
