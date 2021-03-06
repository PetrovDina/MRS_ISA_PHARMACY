<template>
    <div id="appointments">
        <p class="appointments-title">Available dermatologist appointments</p>

        <p style="font-size: 20px" v-if="appointments.length == 0">
            Unfortunately, there aren't any available appointments at this time.
            Please check back soon.
        </p>
        <div id="sort-and-filter" v-if="appointments.length != 0">
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
                                                <star-rating
                                                    active-color="rgba(155, 82, 151, 0.527)"
                                                    :inline="true"
                                                    :star-size="20"
                                                    :read-only="true"
                                                    :show-rating="false"
                                                    :rating="
                                                        appointment.employee
                                                            .rating
                                                    "
                                                    :increment="0.1"
                                                ></star-rating>
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
                                    </div>
                                </div>
                            </div>
                        </div>

                        <Button
                            @action-performed="bookAppointment(appointment)"
                            text="Book appointment"
                            class="book-button"
                        >
                        </Button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div
            class="modal fade"
            id="bookModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Successfully booked!
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
                        {{successMessage}}
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
        <!-- invalid Modal -->
        <div
            class="modal fade"
            id="invalidModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Booking unavailable
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
                        You have already cancelled this appointment!
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
import $ from "jquery";
import StarRating from "vue-star-rating";

export default {
    name: "DermAppointReservation",

    components: { Button, StarRating },

    data() {
        return {
            appointments: [],
            options: ["-", "price low first", "price high first", "rating"],

            successMessage: "",

        };
    },

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        sortSelected(event) {
            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") return;
            else if (sortCriterium === "rating") {
                this.appointments = this.appointments.sort(function (a, b) {
                    return b.employee.rating - a.employee.rating;
                });
            } else if (sortCriterium === "price low first") {
                this.appointments = this.appointments.sort(function (a, b) {
                    return a.price - b.price;
                });
            } else if (sortCriterium === "price high first") {
                this.appointments = this.appointments.sort(function (a, b) {
                    return b.price - a.price;
                });
            }
        },

        bookAppointment(apt) {
            client({
                url: "appointments/checkIfCanBookDerm",
                method: "GET",
                params: {
                    appointmentId: apt.id,
                    patientUsername: localStorage.getItem("USERNAME"),
                },
            }).then((response) => {
                if (!response.data) {
                    $("#invalidModal").modal("show");
                    return;
                }
                client({
                    url: "appointments/book",
                    params: {
                        patientUsername: this.$store.getters.getLoggedUsername,
                        appointmentId: apt.id,
                    },
                    method: "GET",
                }).then((response) => {

                    this.successMessage = response.data;
                    $("#bookModal").modal("show");
                    //refreshing available appointments

                    client({
                        url: "appointments/allDermatologistAvailable",
                        method: "GET",
                    }).then((response) => {
                        this.appointments = response.data;
                    });
                });
            });

        },
    },

    mounted() {
        //check if user is barred
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => {
            if (response.data.penaltyPoints >= 3) {
                this.$router.push({ name: "PenaledScreen" });
                return;
            }
        });

        //get appointments
        client({
            url: "appointments/allDermatologistAvailable",
            method: "GET",
        }).then((response) => {
            this.appointments = response.data;
        });
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

