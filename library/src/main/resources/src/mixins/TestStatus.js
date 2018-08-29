var TestStatus = {
    methods: {
        testStatus(testClass) {
            if (testClass.methods.every(m => m.successful)) return "success";
            else if (testClass.methods.every(m => !m.successful))
                return "failure";
            return "partial";
        }
    }
}
export default TestStatus