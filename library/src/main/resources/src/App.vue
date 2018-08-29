<template>
  <div id="app">
    <test-class-sorter @changed="sortFunction = $event"/>
    <test-class-filter @changed="filterFunction = $event"/>
    <div v-for="testClass in filteredAndSorted" v-bind:key="testClass.name">
      <p>{{ testClass }}</p>
    </div>
    <overview-chart v-bind:chartId="'someChart'" :data="{}" />
  </div>
</template>

<script>
import TestClassSorter from "./components/TestClassSorter.vue";
import TestClassFilter from "./components/TestClassFilter.vue";
import OverviewChart from "./components/OverviewChart.vue";

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
    OverviewChart
  }
};
</script>
