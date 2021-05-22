<template>
    <div id="appointments">
        <p class="appointments-title">Dermatologist appointment history</p>

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
                                                    appointment.employee.rating
                                                }} </b
                                            ><star-rating
                                                active-color="rgba(155, 82, 151, 0.527)"
                                                :inline="true"
                                                :star-size="20"
                                                :read-only="true"
                                                :show-rating="false"
                                                :rating="
                                                    appointment.employee.rating
                                                "
                                                :increment="0.1"
                                            ></star-rating>
                                        </p>
                                        <p class="card-text">
                                            Price:
                                            <b>
                                                {{ appointment.price }},00 RSD
                                            </b>
                                        </p>

                                        <p class="card-text">
                                            <Button
                                                v-if="
                                                    appointment.status !=
                                                    'PENALED'
                                                "
                                                @action-performed="
                                                    rateClicked(
                                                        appointment.employee
                                                    )
                                                "
                                                bgd_color="rgba(155, 82, 151, 0.527)"
                                                text="Rate dermatologist"
                                            >
                                            </Button>
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
        <!-- Modal -->
        <div
            class="modal fade bd-example-modal-lg .modal-lg"
            id="rateModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div
                class="modal-dialog modal-dialog-centered .modal-lg"
                role="document"
            >
                <div class="modal-content justify-content-center">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            How would you rate your appointment with dermatologist {{employeeForRate.firstName}} {{employeeForRate.lastName}}?
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
                    <div class="modal-body w-100 text-center justify-content-center">
                        <star-rating
                            style="margin: 0 auto;"
                            v-model="patientsRating"
                            active-color="rgba(155, 82, 151, 0.527)"
                        ></star-rating>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            @click="saveRating()"
                        >
                            Save rating
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
import $ from "jquery";
import StarRating from "vue-star-rating";

export default {
    name: "DermAppointReservation",

    components: { Button, StarRating },

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
                "duration long",
            ],
            employeeForRate: {},
            patientsRating: 0.0,
        };
    },

    methods: {
        rateClicked(empl) {
            this.employeeForRate = empl;

            //getting patients rating if it already exists
            client({
                url: "employee/getRating",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    employeeId: empl.id,
                },
                method: "GET",
            }).then((response) => (this.patientsRating = response.data));
            $("#rateModal").modal("show");
        },

        saveRating() {
            client({
                url: "employee/rateEmployee",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    employeeId: this.employeeForRate.id,
                    ratedValue: this.patientsRating,
                },
                method: "GET",
            }).then((response) => {
                //refreshing
                client({
                    url: "appointments/dermHistoryByPatient",
                    params: {
                        patientUsername: localStorage.getItem("USERNAME"),
                    },
                    method: "GET",
                }).then((response) => (this.appointments = response.data));

                this.appointmentSortResults = this.appointments;
            });
        },

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

                    return durationA - durationB;
                });
            } else if (sortCriterium === "duration short") {
                this.appointments = this.appointments.sort(function (a, b) {
                    let startTimeA = moment(a.timePeriod.startTime, "hh:mm");
                    let endTimeA = moment(a.timePeriod.endTime, "hh:mm");
                    let durationA = moment.duration(startTimeA.diff(endTimeA));

                    let startTimeB = moment(b.timePeriod.startTime, "hh:mm");
                    let endTimeB = moment(b.timePeriod.endTime, "hh:mm");
                    let durationB = moment.duration(startTimeB.diff(endTimeB));

                    return durationB - durationA;
                });
            }
        },
    },

    mounted() {
        //get appointment history
        client({
            url: "appointments/dermHistoryByPatient",
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

.modal-body {
    align-items: center;
  justify-content: center;
  margin: 0 auto;
}

.star-rating{
    margin: 0 auto;
}


</style>

