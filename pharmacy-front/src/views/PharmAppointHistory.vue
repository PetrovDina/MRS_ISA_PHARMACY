<template>
    <div id="appointments">
        <p class="appointments-title">Pharmacist appointment history</p>

        <div id="sort-and-filter">
            <div id="sort">
                <p class="sort-label">sort by</p>

                <select
                    class="sort-dropdown"
                    name="optionsSelect"
                    id="optionsSelect"
                    @change="sortSelected"
                >
                    <option
                        class="option"
                        v-for="option in options"
                        :key="option"
                        :value="option"
                    >
                        {{ option }}
                    </option>
                </select>
            </div>
        </div>

        <div id="appointmentCards">
            <div :key="appointment.id" v-for="appointment in appointments">
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
                                            Pharmacist rating:
                                            <b>
                                                {{
                                                    appointment.employee.rating
                                                }}
                                            </b>
                                        </p>
                                        <p class="card-text">
                                            Price:
                                            <b>
                                                {{ appointment.price }},00 RSD
                                            </b>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-sm">
                                    <div class="c2">
                                        <p class="card-text">
                                            Pharmacy:
                                            <b>{{
                                                appointment.pharmacy.name
                                            }}</b>
                                        </p>
                                        <p class="card-text">
                                            Date:
                                            <b>{{
                                                formatDate(
                                                    appointment.timePeriod
                                                        .startDate
                                                )
                                            }}</b>
                                        </p>

                                        <p class="card-text">
                                            Time:
                                            <b>
                                                {{
                                                    appointment.timePeriod
                                                        .startTime
                                                }}
                                                -
                                                {{
                                                    appointment.timePeriod
                                                        .endTime
                                                }}
                                            </b>
                                        </p>
                                        <p class="card-text">
                                            Status:
                                            <b>
                                                {{ appointment.status }}
                                            </b>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
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
import $ from "jquery";

export default {
    name: "PharmAppointReservation",

    components: { Button },

    data() {
        return {
            appointments: [],
            appointmentSortResults: [],
            options: [
                "-",
                "date older",
                "date recent",
                "price low",
                "price high",
                "duration short",
                "duration long"

            ],
        };
    },

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        sortSelected(event) {
            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") {
                this.appointmentSortResults = this.appointments;
                return;

            } else if (sortCriterium === "date recent") {
                this.appointments = this.appointments.sort(function (a, b) {
                    let dateA = new Date(a.timePeriod.startDate);
                    let dateB = new Date(b.timePeriod.startDate);
                    return dateB - dateA;
                });

            } else if (sortCriterium === "date older") {
                this.appointments = this.appointments.sort(function (a, b) {
                    let dateA = new Date(a.timePeriod.startDate);
                    let dateB = new Date(b.timePeriod.startDate);
                    return dateA - dateB;
                });

            } else if (sortCriterium === "price low") {
                this.appointments = this.appointments.sort(function (a, b) {
                    return a.price - b.price;
                });
            } else if (sortCriterium === "price high") {
                this.appointments = this.appointments.sort(function (a, b) {
                    return b.price - a.price;
                });
            } else if (sortCriterium === "duration long") {
                this.appointments = this.appointments.sort(function (a, b) {
                    let startTimeA = moment(a.timePeriod.startTime, "hh:mm");
                    let endTimeA = moment(a.timePeriod.endTime, "hh:mm");
                    let durationA = moment.duration(startTimeA.diff(endTimeA));

                    let startTimeB = moment(b.timePeriod.startTime, "hh:mm");
                    let endTimeB = moment(b.timePeriod.endTime, "hh:mm");
                    let durationB = moment.duration(startTimeB.diff(endTimeB));

                    return durationA-durationB;

                });
            } else if (sortCriterium === "duration short") {
                this.appointments = this.appointments.sort(function (a, b) {
                    let startTimeA = moment(a.timePeriod.startTime, "hh:mm");
                    let endTimeA = moment(a.timePeriod.endTime, "hh:mm");
                    let durationA = moment.duration(startTimeA.diff(endTimeA));

                    let startTimeB = moment(b.timePeriod.startTime, "hh:mm");
                    let endTimeB = moment(b.timePeriod.endTime, "hh:mm");
                    let durationB = moment.duration(startTimeB.diff(endTimeB));

                    return durationB-durationA;

            })};
        },
    },

    mounted() {
        //get appointment history
        client({
            url: "appointments/pharmHistoryByPatient",
            params: { patientUsername: localStorage.getItem("USERNAME") },
            method: "GET",
        }).then((response) => (this.appointments = response.data));

        this.appointmentSortResults = this.appointments;
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

.book-button {
    margin-top: 20px;
    border: 1px solid rgba(63, 63, 63, 0.3);
    background-color: rgba(32, 102, 75, 0.2);
}

.book-button:hover {
    background-color: rgba(32, 102, 75, 0.4);
}

/* sort */

.sort-dropdown {
    padding: 10px;
    border: 0.5px solid rgba(128, 128, 128, 0.473);
    display: inline-block;
}

#sort {
    float: right;
}

.sort-label {
    padding: 10px;
    display: inline-block;
}

#sort-and-filter {
    width: 85%;
}

#appointmentCards {
    margin-top: 80px;
}
</style>

