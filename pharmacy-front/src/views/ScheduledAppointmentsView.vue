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
                                    v-if="
                                        !checkCancellationDate(
                                            appointment.timePeriod.startDate
                                        )
                                    "
                                    text="Cancel dermatologist appointment"
                                    class="book-button"
                                    @action-performed="cancelDerm(appointment)"
                                >
                                </Button>
                                <div
                                    v-if="
                                        checkCancellationDate(
                                            appointment.timePeriod.startDate
                                        )
                                    "
                                    class="cancellation-alert alert alert-dark"
                                    role="alert"
                                >
                                    Cancellation period has ended!
                                </div>
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
                                    v-if="
                                        !checkCancellationDate(
                                            appointment.timePeriod.startDate
                                        )
                                    "
                                    text="Cancel pharmacist appointment"
                                    class="book-button"
                                    @action-performed="cancelPharm(appointment)"
                                >
                                </Button>
                                <div
                                    v-if="
                                        checkCancellationDate(
                                            appointment.timePeriod.startDate)
                                    "
                                    class="cancellation-alert alert alert-dark"
                                    role="alert"
                                >
                                    Cancellation period has ended!
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Tab>
        </TabNav>

        <!-- Modal -->
        <div
            class="modal fade"
            id="cancelModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Success!
                        </h5>
                        <button
                            type="button"
                            class="close"
                            data-dismiss="modal"
                            aria-label="Close"
                        >
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- <p style="text-align:justify"> You have received one (1) penalty point for cancelling an appointment.</p>
                        <p style="text-align:justify"> For more information about our reward and penalty system, visit your profile.</p> -->
                        <p style="text-align: justify">
                            Successfully cancelled appointment.
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                        >
                            Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
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

        cancelDerm(appointment) {
            client({
                url: "appointments/cancel",
                params: { appointmentId: appointment.id },
                method: "GET",
            }).then((response) => {
                client({
                    url: "appointments/scheduledDermByPatient",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                    },
                    method: "GET",
                }).then((response) => (this.dermAppointments = response.data)); //refreshing page
            });
            $("#cancelModal").modal("show");
        },

        cancelPharm(appointment) {
            client({
                url: "appointments/cancel",
                params: { appointmentId: appointment.id },
                method: "GET",
            }).then((response) => {
                client({
                    url: "appointments/scheduledPharmByPatient",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                    },
                    method: "GET",
                }).then((response) => (this.pharmAppointments = response.data)); //refreshing page
            });
            $("#cancelModal").modal("show");
        },

        checkCancellationDate(dueDate) {
            let todaysDate = moment().format("YYYY-MM-DD");
            dueDate = moment(dueDate).format("YYYY-MM-DD");

            let b = moment(todaysDate).isBefore(
                moment(dueDate).subtract(1, "day"),
                "day"
            );
            return !b;
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

