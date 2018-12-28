export default {
    methods: {
        resizeAllPlots(listOfPlotNames) {
            if (this.isRunningInBrowser()) {
                Plotly.Plots.resize("overview");
                listOfPlotNames.forEach(name => {
                    Plotly.Plots.resize(name);
                });
            }
        },
        createNewPlot: function(chartId, chartEntries, layout) {
            if (this.isRunningInBrowser())
                Plotly.newPlot(chartId, chartEntries, layout, {responsive: true, displayModeBar: false});
        },
        isRunningInBrowser: function() {
            return process.env.NODE_ENV != "test";
        }
    }
}
