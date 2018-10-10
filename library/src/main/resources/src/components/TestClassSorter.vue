<template>
    <div class="sorter">
        <h2>Order</h2>
        <label>Test class outcome</label>
        <select v-model="sortByValue" @change="updateFunc">
            <option value="name">Test class name</option>
            <option value="totalTime">Total Duration</option>
        </select>
        <select v-model="ascending" @change="updateFunc">
            <option v-bind:value="true">Ascending</option>
            <option v-bind:value="false">Decending</option>
        </select>
    </div>
</template>

<script>
    import TotalTime from "../mixins/TotalTime"

    export default {
        mixins: [TotalTime],
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
            updateFunc: function () {
                switch (this.sortByValue) {
                    case "name":
                        this.func = (a, b) => a.name.localeCompare(b.name);
                        break;
                    case "totalTime":
                        this.func = (a, b) =>
                            this.totalTime(a) < this.totalTime(b)
                                ? -1
                                : this.totalTime(a) == this.totalTime(b) ? 0 : 1;
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
</style>
