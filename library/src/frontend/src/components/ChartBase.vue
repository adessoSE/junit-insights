<template>
    <div :id="chartId" class="chartCanvas"></div>
</template>

<script>
    import Plotly from "plotly.js";

    const colors = ["rgb(220, 82, 74)",
        "rgb(238, 223, 123)",
        "rgb(60, 125, 160)",
        "rgb(109, 179, 63)",
        "rgb(180, 180, 180)",
        "rgb(234, 201, 80)",
        "rgb(130, 180, 208)"];

    export default {
        props: ["chartId"],
        data() {
            return {
                BEFORE_ALL_COLOR: colors[2],
                BEFORE_COLOR: colors[1],
                EXEC_COLOR: colors[0],
                AFTER_COLOR: colors[5],
                BETWEEN_COLOR: colors[4],
                AFTER_ALL_COLOR: colors[6],
                SPRING_COLOR: colors[3],
                chartEntries: [],
                layout: {
                    showlegend: false,
                    font: {
                        family: "Nunito",
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
        mounted: function () {
            if (this.shouldDraw()) {
                Plotly.newPlot(this.chartId, this.chartEntries, this.layout);
                window.addEventListener("resize", this.handleResize);
                this.handleResize();
            }
        },
        beforeDestroy: function () {
            if (this.shouldDraw()) {
                window.removeEventListener("resize", this.handleResize);
                // Plotly.purge(this.chartId);
            }
        },
        methods: {
            // Override in inheriting component to define specific logic
            shouldDraw: function () {
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
