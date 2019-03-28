import { mount } from "@vue/test-utils"
import GeneralInformation from "@/components/GeneralInformation.vue"

describe("GeneralInformation.vue", () => {
    it("shows the number of tested classes", () => {
        const wrapper = mount(GeneralInformation, {
            propsData: {testedClasses: 42, testedMethods: 4711}
        })
        expect(wrapper.text()).toContain("Tested classes: 42")
    })
    it("shows the number of tested methods", () => {
        const wrapper = mount(GeneralInformation, {
            propsData: {testedClasses: 42, testedMethods: 4711}
        })
        expect(wrapper.text()).toContain("Tested methods: 4711")
    })
    it("does not render Spring contexts if it is 0", () => {
        const wrapper = mount(GeneralInformation, {
            propsData: {testedClasses: 42, testedMethods: 4711, springContextsCreated: 0}
        })
        expect(wrapper.text()).not.toContain("Spring Contexts created")
    })
    it("does render Spring contexts if it is greater than 0", () => {
        const wrapper = mount(GeneralInformation, {
            propsData: {testedClasses: 42, testedMethods: 4711, springContextsCreated: 13}
        })
        expect(wrapper.text()).toContain("Spring Contexts created: 13")
    })
})
