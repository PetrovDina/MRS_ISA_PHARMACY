<template>
    <div id="appointments">
        <p class="appointments-title">Scheduled appointments</p>

        <TabNav
            :tabs="['Dermatologist examinations', 'Pharmacist consultations']"
            :selected="selected"
            @selected="setSelected"
        >
            <Tab :isSelected="selected === 'Dermatologist examinations'">
                <div id="appointmentCards">
                    <div
                        :key="appointment.id"
                        v-for="appointment in dermAppointments"
                    >
                        <div class="card mx-auto">
                            <div class="card-body">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm">
                                            <div class="c1">
                                                <p class="card-text">
                                                    Dermatologist:
                                                    <b
                                                        >{{
                                                            appointment.employee
                                                                .firstName
                                                        }}
                                                        {{
                                                            appointment.employee
                                                                .lastName
                                                        }}
                                                    </b>
                                                </p>
                                              
                                                <p class="card-text">
                                                    Dermatologist rating:
                                                    <b>
                                                        {{
                                                            appointment.employee
                                                                .rating
                                                        }}
                                                    </b>
                                                </p>
                                                  <p class="card-text">
                                                    Price:
                                                    <b>
                                                        {{
                                                            appointment.price
                                                        }},00 RSD
                                                    </b>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="col-sm">
                                            <div class="c2">
                                                <p class="card-text">
                                                    Pharmacy:
                                                    <b>{{
                                                        appointment.pharmacy
                                                            .name
                                                    }}</b>
                                                </p>
                                                <p class="card-text">
                                                    Date:
                                                    <b>{{
                                                        formatDate(
                                                            appointment
                                                                .timePeriod
                                                                .startDate
                                                        )
                                                    }}</b>
                                                </p>

                                                <p class="card-text">
                                                    Time:
                                                    <b>
                                                        {{
                                                            appointment
                                                                .timePeriod
                                                                .startTime
                                                        }}
                                                        -
                                                        {{
                                                            appointment
                                                                .timePeriod
                                                                .endTime
                                                        }}
                                                    </b>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <Button
                                    text="Cancel appointment"
                                    class="book-button"
                                >
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
            </Tab>

            <Tab :isSelected="selected === 'Pharmacist consultations'">
                <div id="appointmentCards">
                    <div
                        :key="appointment.id"
                        v-for="appointment in pharmAppointments"
                    >
                        <div class="card mx-auto">
                            <div class="card-body">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-sm">
                                            <div class="c1">
                                                <p class="card-text">
                                                    Pharmacist:
                                                    <b
                                                        >{{
                                                            appointment.employee
                                                                .firstName
                                                        }}
                                                        {{
                                                            appointment.employee
                                                                .lastName
                                                        }}
                                                    </b>
                                                </p>
                                                
                                                <p class="card-text">
                                                    Pharamacist rating:
                                                    <b>
                                                        {{
                                                            appointment.employee
                                                                .rating
                                                        }}
                                                    </b>
                                                </p>
                                                <p class="card-text">
                                                    Price:
                                                    <b>
                                                        {{
                                                            appointment.price
                                                        }},00 RSD
                                                    </b>
                                                </p>
                                            </div>
                                        </div>
                                        <div class="col-sm">
                                            <div class="c2">
                                                <p class="card-text">
                                                    Pharmacy:
                                                    <b>{{
                                                        appointment.pharmacy
                                                            .name
                                                    }}</b>
                                                </p>
                                                <p class="card-text">
                                                    Date:
                                                    <b>{{
                                                        formatDate(
                                                            appointment
                                                                .timePeriod
                                                                .startDate
                                                        )
                                                    }}</b>
                                                </p>

                                                <p class="card-text">
                                                    Time:
                                                    <b>
                                                        {{
                                                            appointment
                                                                .timePeriod
                                                                .startTime
                                                        }}
                                                        -
                                                        {{
                                                            appointment
                                                                .timePeriod
                                                                .endTime
                                                        }}
                                                    </b>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <Button
                                    text="Cancel appointment"
                                    class="book-button"
                                >
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>
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

import $ from "jquery";

export default {
    name: "ScheduledAppointmentsView",

    components: { Button, TabNav, Tab },

    data() {
        return {
            selected: "Dermatologist examinations",
            dermAppointments: [],
            pharmAppointments: [],
        };
    },

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        setSelected(tab) {
            this.selected = tab;
        },
    },

    mounted() {
        //get future derm appointments
        client({
            url: "appointments/scheduledDermByPatient",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => (this.dermAppointments = response.data));

        //get future pharm appointments
        client({
            url: "appointments/scheduledPharmByPatient",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => (this.pharmAppointments = response.data));
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

