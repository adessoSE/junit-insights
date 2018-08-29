var TotalTime = {
    methods: {
        totalTime(testClass) {
            return (
                testClass.beforeAll +
                testClass.before +
                testClass.exec +
                testClass.after +
                testClass.afterAll +
                testClass.between
            );
        }
    }
};
export default TotalTime;
