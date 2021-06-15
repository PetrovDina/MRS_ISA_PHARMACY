<template>
    <div id="appointments">
        <p class="appointments-title">Appointment history</p>

        <TabNav
            :tabs="['Dermatologist examinations', 'Pharmacist consultations']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Dermatologist examinations'">
                <derm-appoint-history></derm-appoint-history>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacist consultations'">
               <pharm-appoint-history></pharm-appoint-history>
            </Tab>
        </TabNav>

    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import moment from "moment";
import Button from "@/components/Button";
import TabNav from "../components/TabNav.vue";
import Tab from "../components/Tab.vue";
import StarRating from "vue-star-rating";

import $ from "jquery";
import DermAppointHistory from './DermAppointHistory.vue';
import PharmAppointHistory from './PharmAppointHistory.vue';

export default {
    name: "AppointmentHistoryView",

    components: { Button, TabNav, Tab, StarRating, DermAppointHistory, PharmAppointHistory },

    data() {
        return {
            selected: "Dermatologist examinations",

        };
    },

    methods: {
        setSelected(tab) {
            this.selected = tab;
        },
    },

    mounted() {

    },
};
</script>


<style scoped>
.appointments-title {
    font-size: 5vh;
    margin-top: 40px;
}
.card {
    width: 70%;
    margin-top: 40px;
    border: 1px solid rgba(63, 63, 63, 0.5);
}

.c1 {
    text-align: left;
}

.c2 {
    text-align: right;
}
.container {
    display: inline-block;
}
.card-title {
    font-weight: 700;
    font-size: 30px;
}

/* .book-button {
    margin-top: 20px;
    border: 1px solid rgba(63, 63, 63, 0.3);
    background-color: rgba(32, 102, 75, 0.2);
}

.book-button:hover {
    background-color: rgba(32, 102, 75, 0.4);
} */
</style>

