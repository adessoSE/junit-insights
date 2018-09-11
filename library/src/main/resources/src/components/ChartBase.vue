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
        height: 80,
      }
    };
  },
  mounted: function() {
    if (this.shouldDraw()) {
      Plotly.newPlot(this.chartId, this.chartEntries, this.layout);
      window.addEventListener("resize", this.handleResize);
      this.handleResize();
    }
  },
  beforeDestroy: function() {
    if (this.shouldDraw()) {
      window.removeEventListener("resize", this.handleResize);
      // Plotly.purge(this.chartId);
    }
  },
  methods: {
    // Override in inheriting component to define specific logic
    shouldDraw: function() {
      return true;
    },
    handleResize() {
      Plotly.Plots.resize(this.chartId);
    },
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
