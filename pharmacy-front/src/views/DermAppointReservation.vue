<template>
    <div id="appointments">
        <p class="appointments-title">Available dermatologist appointments</p>
        <div :key="appointment.id" v-for="appointment in appointments">
            <div class="card mx-auto">
                <div class="card-body">
                    <!-- <h5 class="card-title">nesto ovde idk sta</h5> -->

                    <div class="container">
                        <div class="row">
                            <div class="col-sm">
                                <div class="c1">
                                    <p class="card-text">
                                        Dermatologist:
                                        <b
                                            >{{
                                                appointment.employee.firstName
                                            }}
                                            {{ appointment.employee.lastName }}
                                        </b>
                                    </p>
                                    <p class="card-text">
                                        Price:
                                        <b> {{ appointment.price }},00 RSD </b>
                                    </p>
                                    <p class="card-text">
                                        Dermatologist rating:
                                        <b>
                                            {{ appointment.employee.rating }}
                                        </b>
                                    </p>
                                </div>
                            </div>
                            <div class="col-sm">
                                <div class="c2">
                                    <p class="card-text">
                                        Pharmacy:
                                        <b>{{ appointment.pharmacy.name }}</b>
                                    </p>
                                    <p class="card-text">
                                        Date:
                                        <b>{{
                                            formatDate(
                                                appointment.timePeriod.startDate
                                            )
                                        }}</b>
                                    </p>

                                    <p class="card-text">
                                        Time:
                                        <b>
                                            {{
                                                appointment.timePeriod.startTime
                                            }}
                                            -
                                            {{ appointment.timePeriod.endTime }}
                                        </b>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                <Button 
                    @action-performed="bookAppointment(appointment)" 
                    text="Book appointment" 
                    bgd_color="rgba(155, 82, 151, 0.7)" 
                    style="color:white">
                </Button>

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
                                    >
                                        Confirm reservation
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
                                    Are you sure you want to cancel the
                                    reservation?
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
import Button from "@/components/Button";

export default {
    name: "DermAppointReservation",

    components: {Button},

    data() {
        return {
            appointments: [],
        };
    },

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        bookAppointment(apt){
            alert(apt.id);
        }
    },

    mounted() {
        //get appointments
        client({
            url: "appointments/allDermatologistAvailable",
            method: "GET",
        }).then((response) => (this.appointments = response.data));
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


Button{
    margin-top:20px;
}
</style>

