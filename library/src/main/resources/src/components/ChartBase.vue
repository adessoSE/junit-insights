<template>
    <div :id="chartId" class="chartCanvas"></div>
</template>

<script>
import Plotly from "plotly.js";

export default {
  props: ["chartId"],
  data() {
    return {
      chartEntries: [],
      layout: {
        showlegend: false,
        font: {
          family: "Oswald",
          size: 16
        },
        barmode: "stack",
        xaxis: {
          zeroline: false,
          showgrid: false,
          showticklabels: false
        },
        yaxis: {
          showticklabels: false
        },
        margin: {
          l: 0,
          r: 0,
          b: 0,
          t: 0
        },
        height: 200
      }
    };
  },
  mounted: function() {
    if (this.shouldDraw()) {
      Plotly.newPlot(this.chartId, this.chartEntries, this.layout);
      this.$on("resize", () => Plotly.Plots.resize(this.chartId, {}));
      // Commented out to prevent error messages in browser console
      // Might lead to memory leak
      // this.$once("hook:beforeDestroy", () => Plotly.purge(this.chartId))
    }
  },
  methods: {
    // Override in inheriting component to define specific logic
    shouldDraw: function() { return true },
    getChartEntry(data, text, color) {
      return {
        x: [data],
        text: text + " (" + data + "ms)",
        textposition: "inside",
        marker: {
          color: color
        },
        hoverinfo: "none",
        type: "bar",
        orientation: "h"
      };
    }
  }
};
</script>
