var ClassProcessing = {
    methods: {
        testStatus(testClass) {
            if (testClass.methods.every(m => m.successful)) return "success";
            else if (testClass.methods.every(m => !m.successful))
                return "failure";
            return "partial";
        },
        totalTimeClass(testClass) {
            return (
                testClass.beforeAll +
                testClass.before +
                testClass.exec +
                testClass.after +
                testClass.afterAll +
                testClass.between +
                testClass.spring
            );
        },
        totalTimeMethod(testMethod) {
            return (
                testMethod.before +
                testMethod.exec +
                testMethod.after
            );
        },
        springShare(testClass) {
            if (this.totalTimeClass(testClass) <= 0)
                return 0;
            return testClass.spring / this.totalTimeClass(testClass);
        }
    }
};

export default ClassProcessing
