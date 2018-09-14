<template>
    <div class="generalInformation">
        <h2>General Information</h2>
        <ul>
            <li v-if="this.springContextsCreated > 0">Spring Contexts created: {{ this.springContextsCreated }}</li>
            <li>Tested classes: {{ this.testedClasses }}</li>
            <li>Tested methods: {{ this.testedMethods }}</li>
        </ul>
    </div>
</template>

<script>
export default {
  props: {
    springContextsCreated: {
      type: Number,
      required: false,
      default: 0
    },
    testedClasses: {
      type: Number,
      required: true
    },
    testedMethods: {
      type: Number,
      required: true
    }
  }
};
</script>

<style scoped>
.generalInformation {
  border: solid 1px lightgray;
  border-radius: 6px;
  margin: 10px;
  padding: 10px;
}
</style>
