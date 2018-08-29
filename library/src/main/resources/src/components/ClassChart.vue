<template>
    <div>
        <h1>{{this.testClass.name}}</h1>
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

export default {
  extends: ChartBase,
  props: ["testClass"],
  data() {
    return {
      expanded: false
    };
  },
  created: function() {
    this.layout.height = 80;
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
  },
  components: {
    MethodChart
  }
};
</script>
