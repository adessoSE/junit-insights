import ClassProcessing from "@/mixins/ClassProcessing.js";

describe("ClassProcessing.js", () => {
    it("calculates total time for a method", () => {
        const testMethod = {
            "before": 1,
            "exec": 2,
            "after": 3
        }
        expect(ClassProcessing.methods.totalTimeMethod(testMethod)).toEqual(6)
    })
    it("calculates total time for a class", () => {
        const testClass = {
            beforeAll: 2,
            before: 2,
            exec: 2,
            after: 2,
            afterAll: 2,
            between: 2,
            spring: 108
        }
        expect(ClassProcessing.methods.totalTimeClass(testClass)).toEqual(120)
    })
    it("calculates spring share for a class", () => {
        const testClass = {
            beforeAll: 2,
            before: 2,
            exec: 2,
            after: 2,
            afterAll: 2,
            between: 2,
            spring: 108
        }
        expect(ClassProcessing.methods.springShare(testClass)).toEqual(0.9)
    })
    it("recognises successful test class status", () => {
        const testclass = {
            methods: [
                {successful: true},
                {successful: true}
            ]
        }
        expect(ClassProcessing.methods.testStatus(testclass)).toEqual("success")
    })
    it("recognises partially successful test class status", () => {
        const testclass = {
            methods: [
                {successful: false},
                {successful: true}
            ]
        }
        expect(ClassProcessing.methods.testStatus(testclass)).toEqual("partial")
    })
    it("recognises failed test class status", () => {
        const testclass = {
            methods: [
                {successful: false},
                {successful: false}
            ]
        }
        expect(ClassProcessing.methods.testStatus(testclass)).toEqual("failure")
    })
})