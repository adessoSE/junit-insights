<template>
    <div class="classChart">
        <h2>{{this.testClass.name}} ({{ this.totalTime(this.testClass) }}ms)</h2>
        <div v-if="shouldDraw()">
            <div :id="chartId" class="chartCanvas"></div>
            <button @click="expanded = !expanded">Toggle</button>
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
import TotalTime from "../mixins/TotalTime.js"

export default {
  extends: ChartBase,
  props: ["testClass"],
  mixins: [TotalTime],
  data() {
    return {
      expanded: false
    };
  },
  created: function() {
    this.chartEntries = [
      this.getChartEntry(
        this.testClass.beforeAll,
        "Before All",
        "rgb(109, 179, 63)"
      ),
      this.getChartEntry(this.testClass.before, "Before", "rgb(109, 12, 63)")
    ];
  },
  methods: {
    shouldDraw: function() {
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
    border-radius: 6px;
    padding: 10px;
    margin: 10px;
  }
</style>
