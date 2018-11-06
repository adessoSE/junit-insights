<template>
    <div id="app">
        <h1 style="font-size: 50px; display: inline-block;">{{ this.report.projectName }}</h1>
        <button type="button" class="btn btn-light help-button" v-on:click="showHelp = true"><h2>?</h2></button>
        <HelpDialog v-if="showHelp" v-on:close="showHelp = false"></HelpDialog>
        <overview-chart :chartId="'overview'" :test-classes="report.testClasses"/>
        <general-information
                :spring-contexts-created="this.report.springContextsCreated"
                :tested-classes="this.numberOfTestedClasses"
                :tested-methods="this.numberOfTestedMethods"/>
        <div>
            <h1 style="margin-top: 30px;">Time spent on individual test classes</h1>
            <test-class-filter @changed="filterFunction = $event"/>
            <test-class-sorter @changed="sortFunction = $event"/>
        </div>
        <class-chart v-for="testClass in filteredAndSorted"
                     :key="testClass.name"
                     :test-class="testClass"
                     :chartId="testClass.name"/>
    </div>
</template>

<script>
    import TestClassSorter from "./components/TestClassSorter.vue";
    import TestClassFilter from "./components/TestClassFilter.vue";
    import OverviewChart from "./components/OverviewChart.vue";
    import ClassChart from "./components/ClassChart.vue";
    import GeneralInformation from "./components/GeneralInformation.vue";
    import HelpDialog from "./components/HelpDialog.vue"

    export default {
        name: "app",
        props: ["report"],
        data() {
            return {
                sortFunction: () => -1,
                filterFunction: () => true,
                showHelp: false
            };
        },
        computed: {
            filteredAndSorted: function () {
                return this.report.testClasses
                    .filter(this.filterFunction)
                    .sort(this.sortFunction);
            },
            numberOfTestedClasses: function () {
                return this.report.testClasses.length;
            },
            numberOfTestedMethods: function () {
                return this.report.testClasses
                    .map(tc => tc.methods.length)
                    .reduce((a, b) => a + b, 0);
            }
        },
        components: {
            TestClassSorter,
            TestClassFilter,
            OverviewChart,
            ClassChart,
            GeneralInformation,
            HelpDialog
        }
    };
</script>

<style scoped>
    h1 {
        margin: 10px;
    }

    .help-button {
        float: right;
        width: 50px;
        height: 50px;
        margin-top: 20px;
        margin-right: 20px;
    }
</style>
