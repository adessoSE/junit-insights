<template>
    <div class=filter>
        <h2>Filter</h2>
        <label>Minimum test time (ms)</label>
        <input type="number" v-model="minTime" @change="updateFunc">
        <br>
        <label>Maximum test time (ms)</label>
        <input type="number" v-model="maxTime" @change="updateFunc">
        <br>
        <label>Test class outcome</label>
        <input type="checkbox" @change="updateFunc" v-model="includeSuccess"> Success
        <input type="checkbox" @change="updateFunc" v-model="includePartial"> Partial failure
        <input type="checkbox" @change="updateFunc" v-model="includeFailure"> Failure
    </div>
</template>

<script>
    import TestStatus from "../mixins/TestStatus"
    import TotalTime from "../mixins/TotalTime"

    export default {
        mixins: [TestStatus, TotalTime],
        data() {
            return {
                minTime: 0,
                maxTime: 1000000,
                includeSuccess: true,
                includePartial: true,
                includeFailure: true,
                func: () => true
            };
        },
        methods: {
            updateFunc: function () {
                this.func = testClass => {
                    return this.totalTime(testClass) >= this.minTime &&
                        this.totalTime(testClass) <= this.maxTime &&
                        ((this.testStatus(testClass) == "success" && this.includeSuccess) ||
                            (this.testStatus(testClass) == "partial" && this.includePartial) ||
                            (this.testStatus(testClass) == "failure" && this.includeFailure));
                };
                this.$emit("changed", this.func);
            }
        }
    };
</script>

<style scoped>
    .filter {
        display: inline;
    }
</style>
