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
        <label>Context creation</label>
        <input type="checkbox" @change="updateFunc" v-model="includeSpring"> Spring tests
        <input type="checkbox" @change="updateFunc" v-model="includeNonSpring"> Non-Spring tests
        <br>
        <label>Test class outcome</label>
        <input type="checkbox" @change="updateFunc" v-model="includeSuccess"> Success
        <input type="checkbox" @change="updateFunc" v-model="includePartial"> Partial failure
        <input type="checkbox" @change="updateFunc" v-model="includeFailure"> Failure
        <br>
        <button type="button" v-on:click="clearClicked()">Clear</button>
    </div>
</template>

<script>
    import ClassProcessing from "../mixins/ClassProcessing"

    export default {
        mixins: [ClassProcessing],
        data() {
            return {
                minSpringShare: "",
                maxSpringShare: "",
                minTime: "",
                maxTime: "",
                includeSpring: true,
                includeNonSpring: true,
                includeSuccess: true,
                includePartial: true,
                includeFailure: true,
                func: () => true
            };
        },
        methods: {
            updateFunc: function () {
                this.func = testClass => {
                    if ((this.minSpringShare !== "" && this.springShare(testClass) < this.minSpringShare/100) ||
                    (this.maxSpringShare !== "" && this.springShare(testClass) > this.maxSpringShare/100) ||
                    (this.minTime !== "" && this.totalTimeClass(testClass) < this.minTime) ||
                    (this.maxTime !== "" && this.totalTimeClass(testClass) > this.maxTime) ||
                    (testClass.spring > 0 && !this.includeSpring) ||
                    (testClass.spring <= 0 && !this.includeNonSpring) ||
                    (this.testStatus(testClass) === "success" && !this.includeSuccess) ||
                    (this.testStatus(testClass) === "partial" && !this.includePartial) ||
                    (this.testStatus(testClass) === "failure" && !this.includeFailure))
                        return false;
                    else
                        return true;
                };
                this.$emit("changed", this.func);
            },
            clearClicked: function() {
                this.minSpringShare = "";
                this.maxSpringShare = "";
                this.minTime = "";
                this.maxTime = "";
                this.includeSpring = true;
                this.includeNonSpring = true;
                this.includeSuccess = true;
                this.includePartial = true;
                this.includeFailure = true;
            }
        }
    };
</script>

<style scoped>
    .filter {
        display: inline;
    }
</style>
