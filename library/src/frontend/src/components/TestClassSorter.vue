<template>
    <div class="sorter">
        <div class="col" style="white-space: nowrap; margin-top: 10px;">
            <label style="display: inline-block;">
                Order by:
                <select class="form-control" v-model="sortByValue" @change="updateFunc" style="display: inline-block; width: 75%">
                    <option value="name">Test class name</option>
                    <option value="chronological">Chronological order</option>
                    <option value="totalTime">Total duration</option>
                    <option value="springTime">Spring duration</option>
                    <option value="nonSpringTime">Non-Spring duration</option>
                </select>
            </label>
            <div class="col" style="display: inline;">
                {{ascending ? "Ascending" : "Descending"}}
                <button class="btn up-down-button" type="button" @click="ascending = !ascending; updateFunc();">
                    Toggle
                </button>
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
                sortByValue: "totalTime",
                ascending: false,
                func: (a, b) => -1 * this.sortByTotalTime(a, b)
            };
        },
        created: function () {
            this.$emit("changed", this.func);
        },
        methods: {
            // Takes the selected values from the UI and turns them into a sorting function, then
            // emits it via the event name "changed". That function can be used to sort test classes.
            updateFunc: function () {
                switch (this.sortByValue) {
                    case "name":
                        this.func = this.sortByName;
                        break;
                    case "totalTime":
                        this.func = this.sortByTotalTime;
                        break;
                    case "springTime":
                        this.func = this.sortBySpringTime;
                        break;
                    case "nonSpringTime":
                        this.func = this.sortByNonSpringTime;
                        break;
                    case "chronological":
                        this.func = this.sortChronological;
                        break;
                }
                this.$emit(
                    "changed",
                    this.ascending ? this.func : (a, b) => -1 * this.func(a, b)
                );
            },
            sortByName: function (a, b) {
                return a.name.localeCompare(b.name);
            },
            sortByTotalTime: function (a, b) {
                return this.totalTimeClass(a) < this.totalTimeClass(b)
                    ? -1
                    : this.totalTimeClass(a) === this.totalTimeClass(b) ? 0 : 1;
            },
            sortBySpringTime: function (a, b) {
                return a.spring < b.spring
                    ? -1
                    : a.spring === b.spring ? 0 : 1;
            },
            sortByNonSpringTime: function (a, b) {
                return (this.totalTimeClass(a) - a.spring) < (this.totalTimeClass(b) - b.spring)
                    ? -1
                    : (this.totalTimeClass(a) - a.spring) === (this.totalTimeClass(b) - b.spring) ? 0 : 1;
            },
            sortChronological: function (a, b) {
                return a.firstTimestamp < b.firstTimestamp ? -1
                    : a.firstTimestamp === b.firstTimestamp ? 0
                        : 1;
            },
        }
    };
</script>

<style scoped>
    .sorter {
        display: inline;
    }

    .up-down-button {
        background-color: #FFFFFF;
        border: solid 1px lightgrey;
    }

    .up-down-button:hover, .up-down-button:focus, .up-down-button:active {
        background-color: #EEEEEE;
    }
</style>
