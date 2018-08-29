<template>
    <div :id="chartId"></div>
</template>

<script>
import Plotly from "plotly.js";

function getChartEntry(data, text, color) {
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

let overviewChartLayout = {
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
};

export default {
  props: ["chartId", "data"],
  mounted: function() {
    var preparationTime = 120;
    var executionTime = 50;
    var tearDownTime = 75;
    var data = [
      getChartEntry(preparationTime, "Preparation", "rgb(109, 179, 63)"),
      getChartEntry(executionTime, "Execution", "rgb(220, 82, 74)"),
      getChartEntry(tearDownTime, "Tear-Down", "rgb(180, 180, 180)")
    ];
    window.addEventListener("resize", this.handleResize);
    Plotly.newPlot(this.chartId, data, overviewChartLayout);
  },
  beforeDestroy: function() {
    window.removeEventListener("resize", this.handleResize);
    Plotly.purge(this.chartId)
  },
  methods: {
    handleResize: function() {
      Plotly.Plots.resize(this.chartId, {});
    }
  }
};
</script>
