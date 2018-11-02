<template>
    <div class="modal-mask">
        <div class="modal-wrapper">
            <div class="modal-container">

                <div class="modal-header">
                    <slot name="header">
                        <h1>JUnit Insights help</h1>
                    </slot>
                </div>

                <div class="modal-body">
                    <slot name="body">
                        The information provided on this site is collected by using the <a href="https://junit.org/junit5/docs/5.2.0/api/org/junit/jupiter/api/extension/package-summary.html">JUnit Jupiter extension callbacks</a>.
                        <br>
                        The <b>Overview</b> chart breaks down the total test time into the following components:
                        <ul>
                            <li><b>Spring:</b> startup time for all Spring application contexts</li>
                            <li><b>Preparation:</b> sum of all time that passed before the BeforeAll and BeforeEach callbacks</li>
                            <li><b>Execution:</b> sum of all time that passed between the BeforeEach and AfterEach callbacks</li>
                            <li><b>Tear-Down:</b> like preparation but after the execution</li>
                        </ul>
                        <br>
                        The list down below shows an overview of the individual tested classes and the time spent on different tasks:
                        <ul>
                            <li><b>Spring:</b> startup time of the possibly created Spring application context</li>
                            <li><b>Before All:</b> tasks executed before any of the test methods are executed</li>
                            <li><b>Before:</b> sum of the time used before each individual test method</li>
                            <li><b>Exec:</b> sum of the execution time of all test methods</li>
                            <li><b>After:</b> like before but after the execution</li>
                            <li><b>After All:</b> like before all but after the execution</li>
                        </ul>
                        <br>
                        You can also expand the test classes and get the information about <b>Before</b>, <b>Execution</b> and <b>After</b> for the individual test methods.
                    </slot>
                </div>

                <div class="modal-footer">
                    <slot name="footer">
                        <button class="modal-default-button btn btn-light" @click="$emit('close')">
                            OK
                        </button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
    }

    .modal-wrapper {
        display: table-cell;
        vertical-align: middle;
    }

    .modal-container {
        width: 800px;
        margin: 0px auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
    }
</style>
