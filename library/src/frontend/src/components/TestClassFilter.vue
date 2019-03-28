<template>
    <div class="filter">
        <div class="grid-wrapper">
            <div class="right-addon input-group">
                <input type="number" class="form-control" v-model="minSpringShare" @change="updateFunc"
                    placeholder="Min. Spring share"
                    data-toggle="tooltip" title="The minimum portion of time spent on Spring initialization">
                <div class="input-group-append"><span class="input-group-text">%</span></div>
            </div>
            <div class="right-addon input-group">
                <input type="number" class="form-control" v-model="maxSpringShare" @change="updateFunc"
                    placeholder="Max. Spring share"
                    data-toggle="tooltip" title="The maximum portion of time spent on Spring initialization">
                <div class="input-group-append"><span class="input-group-text">%</span></div>
            </div>
            <div class="right-addon input-group">
                <input type="number" class="form-control" v-model="minTime" @change="updateFunc"
                       placeholder="Min. test time"
                       data-toggle="tooltip" title="The minimum total duration of a test class">
                <div class="input-group-append"><span class="input-group-text">ms</span></div>
            </div>
            <div class="right-addon input-group">
                <input type="number" class="form-control" v-model="maxTime" @change="updateFunc"
                       placeholder="Max. test time"
                       data-toggle="tooltip" title="The maximum total duration of a test class">
                <div class="input-group-append"><span class="input-group-text">ms</span></div>
            </div>
            <div class="button-group input-group" data-toggle="tooltip"
                 title="Filter tests that create their own Spring context and those that don't">
                <button type="button" class="btn form-control dropdown-toggle" data-toggle="dropdown"
                        style="white-space: nowrap;">
                    Context creation<span class="caret" ></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="dropdown-checkbox">
                        <input type="checkbox" id="include_spring_tests" checked
                               @change="updateFunc" v-model="includeSpring"/>
                        <label for="include_spring_tests"
                               >Include Spring tests</label>
                    </li>
                    <li class="dropdown-checkbox">
                        <input type="checkbox" id="include_non_spring_tests" checked
                               @change="updateFunc" v-model="includeNonSpring"/>
                        <label for="include_non_spring_tests"
                               >Include non Spring tests</label>
                    </li>
                </ul>
            </div>
            <div class="button-group input-group" data-toggle="tooltip"
                 title="Filter tests based on their success">
                <button type="button" class="btn form-control dropdown-toggle" data-toggle="dropdown"
                        style="white-space: nowrap;">
                    Test success<span class="caret" ></span>
                </button>
                <ul class="dropdown-menu">
                    <li class="dropdown-checkbox">
                        <input type="checkbox" id="include_succeeded_tests" checked
                               @change="updateFunc" v-model="includeSuccess"/>
                        <label for="include_succeeded_tests"
                               >Include succeeded tests</label>
                    </li>
                    <li class="dropdown-checkbox">
                        <input type="checkbox" id="include_partial_tests" checked
                               @change="updateFunc" v-model="includePartial"/>
                        <label for="include_partial_tests"
                               >Include partial succeeded tests</label>
                    </li>
                    <li class="dropdown-checkbox">
                        <input type="checkbox" id="include_failed_tests" checked
                               @change="updateFunc" v-model="includeFailure"/>
                        <label for="include_failed_tests"
                               >Include failed tests</label>
                    </li>
                </ul>
            </div>
            <button type="button" class="btn clear-button" v-on:click="clearClicked()">Clear</button>
        </div>
    </div>
</template>

<script>
    import ClassProcessing from "../mixins/ClassProcessing"

    export default {
        name: "TestClassFilter",
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
                // Copy over the values from the form elements and emit a "changed" event with a filtering function
                // Used in App.vue
                var minSpringShare = parseInt(this.minSpringShare);
                var maxSpringShare = parseInt(this.maxSpringShare);
                var minTime = parseInt(this.minTime);
                var maxTime = parseInt(this.maxTime);
                var includeSpring = this.includeSpring;
                var includeNonSpring = this.includeNonSpring;
                var includeSuccess = this.includeSuccess;
                var includePartial = this.includePartial;
                var includeFailure = this.includeFailure;

                this.func = testClass => {
                    if ((minSpringShare !== NaN && this.springShare(testClass) < minSpringShare/100) ||
                    (maxSpringShare !== NaN && this.springShare(testClass) > maxSpringShare/100) ||
                    (minTime !== NaN && this.totalTimeClass(testClass) < minTime) ||
                    (maxTime !== NaN && this.totalTimeClass(testClass) > maxTime) ||
                    (testClass.spring > 0 && !includeSpring) ||
                    (testClass.spring <= 0 && !includeNonSpring) ||
                    (this.testStatus(testClass) === "success" && !includeSuccess) ||
                    (this.testStatus(testClass) === "partial" && !includePartial) ||
                    (this.testStatus(testClass) === "failure" && !includeFailure))
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
    .grid-wrapper {
        display: grid;
        grid-gap: 10px;
        grid-template-columns: repeat(auto-fill, minmax(207px, 1fr) ) ;
    }

    .input-group-text {
        background-color: white;
    }

    .form-control {
        border-right: 0;
    }

    .dropdown-toggle {
        border: solid 1px lightgrey;
    }

    .dropdown-toggle:hover, .dropdown-toggle:focus, .dropdown-toggle:active {
        background-color: #EEEEEE;
    }

    .dropdown-checkbox {
        white-space: nowrap;
    }

    .dropdown-menu {
        padding: 5px;
    }

    .clear-button {
        background-color: #FFFFFF;
        border: solid 1px lightgrey;
    }

    .clear-button:hover, .clear-button:focus, .clear-button:active {
        background-color: #EEEEEE;
    }
</style>
