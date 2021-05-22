<template>
    <div>
        <p class="prescriptions-title">My ePrescriptions</p>

        <TabNav
            :tabs="['New prescriptions', 'Filled prescriptions']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'New prescriptions'">
                <patients-new-prescriptions-view></patients-new-prescriptions-view>


            </Tab>

            <Tab :isSelected="selected === 'Filled prescriptions'">
                <patients-filled-prescriptions-view></patients-filled-prescriptions-view>

            </Tab>
        </TabNav>

    </div>
    
</template>

<script>

import { client } from "@/client/axiosClient";
import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";
import PatientsFilledPrescriptionsView from "./PatientsFilledPrescriptionsView"
import PatientsNewPrescriptionsView from "./PatientsNewPrescriptionsView"

export default {

    name: 'PatientsAllPrescriptionsView',

    components : {
         TabNav, Tab, PatientsFilledPrescriptionsView, PatientsNewPrescriptionsView
    },

    data() {
        return {

            selected: 'New prescriptions'
        }
    },

    methods: {

        setSelected(tab) {
            this.selected = tab;
        },

        
    },

    mounted() {
            
        }
};
</script>

<style scoped>
.prescriptions-title {
    font-size: 5vh;
    margin-top: 40px;
}
</style>