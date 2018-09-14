<template>
  <div id="app">
    <h1>{{ this.report.projectName }}</h1>
    <overview-chart :chartId="'overview'" :test-classes="report.testClasses"/>
    <general-information 
      :spring-contexts-created="this.report.springContextsCreated" 
      :tested-classes="this.numberOfTestedClasses" 
      :tested-methods="this.numberOfTestedMethods"/>
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
import GeneralInformation from "./components/GeneralInformation.vue";

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
    },
    numberOfTestedClasses: function() {
      return this.report.testClasses.length;
    },
    numberOfTestedMethods: function() {
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
    GeneralInformation
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
