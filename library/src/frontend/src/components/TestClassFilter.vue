<template>
    <div class="filter">
        <div class="form-inline" style="max-width: 1450px;">
            <div class="col">
                <div class="right-addon">
                    <span>%</span>
                    <input type="number" class="form-control" v-model="minSpringShare" @change="updateFunc"
                           placeholder="Min. Spring share"
                           data-toggle="tooltip" title="The minimum portion of time spent on Spring initialization">
                </div>
            </div>
            <div class="col">
                <div class="right-addon">
                    <span>%</span>
                    <input type="number" class="form-control" v-model="maxSpringShare" @change="updateFunc"
                           placeholder="Max. Spring share"
                           data-toggle="tooltip" title="The maximum portion of time spent on Spring initialization">
                </div>
            </div>
            <div class="col">
                <div class="right-addon">
                    <span>ms</span>
                    <input type="number" class="form-control" v-model="minTime" @change="updateFunc"
                           placeholder="Min. test time"
                           data-toggle="tooltip" title="The minimum total duration of a test class">
                </div>
            </div>
            <div class="col">
                <div class="right-addon">
                    <span>ms</span>
                    <input type="number" class="form-control" v-model="maxTime" @change="updateFunc"
                           placeholder="Max. test time"
                           data-toggle="tooltip" title="The maximum total duration of a test class">
                </div>
            </div>
            <div class="col">
                <div class="button-group" data-toggle="tooltip"
                     title="Filter tests that create their own Spring context and those that don't">
                    <button type="button" class="form-control dropdown-toggle" data-toggle="dropdown"
                            style="white-space: nowrap;">
                        Context creation<span class="caret" style="display: inline-block;"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li class="dropdown-checkbox">
                            <input type="checkbox" id="include_spring_tests" checked
                                   @change="updateFunc" v-model="includeSpring"/>
                            <label for="include_spring_tests"
                                   style="display: inline-block;">Include Spring tests</label>
                        </li>
                        <li class="dropdown-checkbox">
                            <input type="checkbox" id="include_non_spring_tests" checked
                                   @change="updateFunc" v-model="includeNonSpring"/>
                            <label for="include_non_spring_tests"
                                   style="display: inline-block;">Include non Spring tests</label>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col">
                <div class="button-group" style="margin-left: 10px;" data-toggle="tooltip"
                     title="Filter tests based on their success">
                    <button type="button" class="form-control dropdown-toggle" data-toggle="dropdown"
                            style="white-space: nowrap;">
                        Test success<span class="caret" style="display: inline-block;"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li class="dropdown-checkbox">
                            <input type="checkbox" id="include_succeeded_tests" checked
                                   @change="updateFunc" v-model="includeSuccess"/>
                            <label for="include_succeeded_tests"
                                   style="display: inline-block;">Include succeeded tests</label>
                        </li>
                        <li class="dropdown-checkbox">
                            <input type="checkbox" id="include_partial_tests" checked
                                   @change="updateFunc" v-model="includePartial"/>
                            <label for="include_partial_tests"
                                   style="display: inline-block;">Include partial succeeded tests</label>
                        </li>
                        <li class="dropdown-checkbox">
                            <input type="checkbox" id="include_failed_tests" checked
                                   @change="updateFunc" v-model="includeFailure"/>
                            <label for="include_failed_tests"
                                   style="display: inline-block;">Include failed tests</label>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col">
                <div class="col">
                    <button type="button" class="btn btn-light" v-on:click="clearClicked()" style="background-color: #FFFFFF; ">Clear</button>
                </div>
            </div>
        </div>
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

    .right-addon {
        position: relative;
        width: 200px;
    }

    .right-addon input {
        padding-right: 30px;
        width:200px;
    }

    .right-addon span {
        position: absolute;
        left: 163px;
        padding: 7px 12px;
        pointer-events: none;
    }

    .test-class-container .row {
        margin-left: 0px;
        margin-right: 0px;
    }

    .dropdown-checkbox {
        white-space: nowrap;
    }

    .dropdown-checkbox input {
        display: inline-block;
        margin-left: 10px;
    }

    .dropdown-checkbox label {
        margin-right: 10px;
    }

</style>
