var ClassProcessing = {
    methods: {
        testStatus(testClass) {
            if (testClass.methods.every(m => m.successful)) return "success";
            else if (testClass.methods.every(m => !m.successful))
                return "failure";
            return "partial";
        },
        totalTime(testClass) {
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
        springShare(testClass) {
            if (this.totalTime(testClass) <= 0)
                return 0;
            return testClass.spring / this.totalTime(testClass);
        }
    }
};

export default ClassProcessing
