<template>
  <div id="app">
    <h1>{{ this.report.projectName }}</h1>
    <overview-chart :chartId="'overview'" :test-classes="report.testClasses"/>
    <div class="toolbar">
      <test-class-sorter @changed="sortFunction = $event"/>
      <test-class-filter @changed="filterFunction = $event"/>
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

export default {
  name: "app",
  props: ["report"],
  data() {
    return {
      sortFunction: () => -1,
      filterFunction: () => true
    };
  },
  computed: {
    filteredAndSorted: function() {
      return this.report.testClasses
        .filter(this.filterFunction)
        .sort(this.sortFunction);
    }
  },
  components: {
    TestClassSorter,
    TestClassFilter,
    OverviewChart,
    ClassChart
  }
};
</script>

<style scoped>
  h1 {
    margin: 10px;
  }
  .toolbar {
    border: solid 1px lightgray;
    border-radius: 6px;
    margin: 10px;
    padding: 10px;
  }
</style>
