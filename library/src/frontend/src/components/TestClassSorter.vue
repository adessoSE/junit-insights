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
                <button class="btn up-down-button" type="button" @click="ascending = !ascending; updateFunc();">
                    {{ascending ? "&darr;" : "&uarr;"}}
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
                sortByValue: "name",
                ascending: true,
                func: (a, b) => a.name.localeCompare(b.name)
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
                        this.func = (a, b) => a.name.localeCompare(b.name);
                        break;
                    case "totalTime":
                        this.func = (a, b) =>
                            this.totalTimeClass(a) < this.totalTimeClass(b)
                                ? -1
                                : this.totalTimeClass(a) === this.totalTimeClass(b) ? 0 : 1;
                        break;
                    case "springTime":
                        this.func = (a,b) =>
                            a.spring < b.spring
                            ? -1
                            : a.spring === b.spring ? 0 : 1;
                        break;
                    case "nonSpringTime":
                        this.func = (a,b) =>
                            (this.totalTimeClass(a) - a.spring) < (this.totalTimeClass(b) - b.spring)
                                ? -1
                                : (this.totalTimeClass(a) - a.spring) === (this.totalTimeClass(b) - b.spring) ? 0 : 1;
                        break;
                    case "chronological":
                        this.func = (a,b) => {
                            return a.firstTimestamp < b.firstTimestamp ? -1
                            : a.firstTimestamp == b.firstTimestamp ? 0
                            : 1;
                        };
                        break;
                }
                this.$emit(
                    "changed",
                    this.ascending ? this.func : (a, b) => -1 * this.func(a, b)
                );
            }
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
