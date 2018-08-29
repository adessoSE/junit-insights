<template>
    <div>
        <h1>{{this.testClass.name}}</h1>
        <div v-if="shouldDraw()">
            <div :id="chartId" class="chartCanvas"></div>
            <!-- TODO: Dropdown diagrams for individual methods -->
        </div>
        <div v-else>
            The tests took no measurable time to run.
        </div>
    </div>
</template>

<script>
import ChartBase from "./ChartBase.vue";
export default {
  extends: ChartBase,
  props: ["testClass"],
  created: function() {
    this.layout.height = 60;
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
      var classTime = this.testClass.methods
        .map(method => method.before + method.exec + method.after)
        .reduce((sum, time) => (sum += time), 0);
      return classTime >= 5;
    }
  }
};
</script>
