<template>
    <div class="patientsPrescriptions">
        <p class="reservations-title">
            {{ this.$store.getters.getLoggedUsername }}'s reservations
        </p>
        <p style="font-size:20px; margin-top:30px" v-if="reservations.length == 0">You don't have any reservations.</p>

        <div id="sort-and-filter" v-if="reservations.length != 0">
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
        <div :key="reservation.id" v-for="reservation in reservations">
            <div class="card mx-auto">
                <div class="card-body">
                    <h5 class="card-title">
                        {{ reservation.medication.name }}
                    </h5>

                    <div class="container">
                        <div class="row">
                            <div class="col-sm">
                                <div class="c1">
                                    <p class="card-text">
                                        Pharmacy:
                                        <b>{{ reservation.pharmacy.name }}</b>
                                    </p>
                                    <p class="card-text">
                                        Pick up address:
                                        <b
                                            >{{
                                                reservation.pharmacy.location
                                                    .street
                                            }}
                                            {{
                                                reservation.pharmacy.location
                                                    .streetNum
                                            }}
                                        </b>
                                    </p>
                                    <p class="card-text">
                                        City:
                                        <b
                                            >{{
                                                reservation.pharmacy.location
                                                    .zipcode
                                            }}

                                            {{
                                                reservation.pharmacy.location
                                                    .city
                                            }}
                                        </b>
                                    </p>
                                </div>
                            </div>
                            <div class="col-sm">
                                <div class="c2">
                                    <p class="card-text">
                                        Unit price:
                                        <b>{{reservation.medicationPrice}},00 RSD </b>
                                    </p>
                                    <p class="card-text">
                                        Quantity:
                                        <b> {{ reservation.quantity }} </b>
                                    </p>

                                    <p class="card-text">
                                        Pick up due date:
                                        <b
                                            >{{
                                                convertDate(reservation.dueDate)
                                            }}
                                        </b>
                                    </p>
                                    <p class="card-text">
                                        Status:
                                        <b>{{ reservation.status }} </b>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <button
                        v-if="
                            reservation.status == 'CREATED' &&
                            !checkCancellationDate(reservation.dueDate)
                        "
                        class="btn btn-primary cancel-btn"
                        :class="{ disabled: reservation.status !== 'CREATED' }"
                        @click="selectReservation(reservation)"
                        data-toggle="modal"
                        data-target="#exampleModalCenter"
                    >
                        cancel reservation
                    </button>

                    <div
                        v-if="
                            checkCancellationDate(reservation.dueDate) &&
                            reservation.status == 'CREATED'
                        "
                        class="cancellation-alert alert alert-dark"
                        role="alert"
                    >
                        Cancellation period has ended!
                    </div>

                    <!-- Modal -->
                    <div
                        class="modal fade"
                        id="exampleModalCenter"
                        tabindex="-1"
                        role="dialog"
                        aria-labelledby="exampleModalCenterTitle"
                        aria-hidden="true"
                    >
                        <div
                            class="modal-dialog modal-dialog-centered"
                            role="document"
                        >
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5
                                        class="modal-title"
                                        id="exampleModalLongTitle"
                                        style="text-align: justify"
                                    >
                                        Confirm cancellation
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

                                    <p style="text-align: justify">
                                        Are you sure you want to cancel the
                                        reservation?
                                    </p>

                                </div>
                                <div class="modal-footer">
                                    <button
                                        type="button"
                                        class="btn btn-secondary"
                                        data-dismiss="modal"
                                    >
                                        No
                                    </button>
                                    <button
                                        type="button"
                                        class="btn btn-confirm"
                                        data-dismiss="modal"
                                        @click="cancelReservation()"
                                    >
                                        Yes
                                    </button>
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

export default {
    name: "PatientsReservations",
    data() {
        return {
            reservations: [],
            selectedReservation: null,
            options: ["-", "due date older first", "due date newer first", "status"]

        };
    },

    methods: {
        convertDate(d) {
            return moment(d).format("MMMM Do yyyy.");
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

        selectReservation(r) {
            this.selectedReservation = r;
        },

        cancelReservation() {
            client({
                url: "reservation/cancel",
                params: { reservationId: this.selectedReservation.id, patientUsername: localStorage.getItem("USERNAME")},
                method: "GET",
            }).then((response) => {
                client({
                    url: "reservation/findByPatient",
                    params: { username: this.$store.getters.getLoggedUsername },

                    method: "GET",
                }).then((response) => (this.reservations = response.data)); //refreshing page
            });
        },

        sortSelected(event) {
            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") {
                this.reservations = this.reservations;
                return;
            } else if (sortCriterium === "due date newer first") {
                this.reservations = this.reservations.sort(function (a, b) {
                    let dateA = new Date(a.dueDate);
                    let dateB = new Date(b.dueDate);
                    return dateB - dateA;
                });
            } else if (sortCriterium === "due date older first") {
                this.reservations = this.reservations.sort(function (a, b) {
                    let dateA = new Date(a.dueDate);
                    let dateB = new Date(b.dueDate);
                    return dateA - dateB;
                });
            }else if(sortCriterium === "status"){
                this.reservations = this.reservations.sort(function (a, b) {
                    return (a.status > b.status) ? 1 : -1;
                });
            
            }
        },
    },

    mounted() {
        //get reservations of the patient
        client({
            url: "reservation/findByPatient",
            params: { username: this.$store.getters.getLoggedUsername },

            method: "GET",
        }).then((response) => (this.reservations = response.data));
    },
};
</script>


<style scoped>
.reservations-title {
    font-size: 5vh;
    margin-top: 30px;
}
.card {
    width: 80%;
    margin-top: 40px;
    border: 1px solid rgba(63, 63, 63, 0.349);
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

.card-text {
    margin-top: 20px;
}

.cancel-btn {
    background-color: maroon;
    margin-top: 20px;
}

.btn-confirm {
    background-color: rgba(15, 95, 72, 0.95);
    color: white;
}

.cancellation-alert {
    margin-top: 10px;
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
    width: 90%;
}
</style>

