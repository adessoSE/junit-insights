<template>
    <div :id="chartId" class="chartCanvas"></div>
</template>

<script>
    import PlotlyMixin from "../mixins/PlotlyMixin";

    const colors = [
        "rgb(220, 82, 74)", // EXEC
        "rgb(238, 223, 123)", // BEFORE
        "rgb(60, 125, 160)", // BEFORE ALL
        "rgb(109, 179, 63)", // SPRING
        "rgb(180, 180, 180)", // BETWEEN
        "rgb(234, 201, 80)", // AFTER
        "rgb(130, 180, 208)" // AFTER ALL
    ];

    export default {
        props: ["chartId"],
        mixins: [PlotlyMixin],
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
                        showticklabels: false,
                        fixedrange: true
                    },
                    yaxis: {
                        showticklabels: false,
                        fixedrange: true
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
                this.createNewPlot(this.chartId, this.chartEntries, this.layout);
            }
        },
        beforeDestroy: function () {
            if (this.shouldDraw()) {
            }
        },
        methods: {
            // Override in inheriting component to define specific logic
            shouldDraw: function () {
                return true;
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
