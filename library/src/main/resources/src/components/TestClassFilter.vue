<template>
    <div class=filter>
        <h2>Filter</h2>
        <label>Minimum Spring share (%)</label>
        <input type="number" v-model="minSpringShare" @change="updateFunc">
        <br>
        <label>Maximum Spring share (%)</label>
        <input type="number" v-model="maxSpringShare" @change="updateFunc">
        <br>
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
    import ClassProcessing from "../mixins/ClassProcessing"

    export default {
        mixins: [ClassProcessing],
        data() {
            return {
                minSpringShare: 0,
                maxSpringShare: 100,
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
                        this.springShare(testClass) >= this.minSpringShare/100 &&
                        this.springShare(testClass) <= this.maxSpringShare/100 &&
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
