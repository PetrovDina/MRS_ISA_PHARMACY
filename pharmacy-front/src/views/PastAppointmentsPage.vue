<template>
<div class="container">
    <div id="appointments">
        <p class="titl">Patients with concluded appointments</p>
        <br>
        <br>
        <v-row justify="center">
            <v-col >
                <v-text-field
                v-model="query1"
                        clearable
                        label="Patient name"
                        type="text"
                        outlined
                        class="m-l "
                        
                ></v-text-field>
            </v-col>
            <v-col >
                <v-text-field
                v-model="query2"
                        clearable
                        label="Patient surname"
                        type="text"
                        outlined
                        class="m-l "
                        
                ></v-text-field>
            </v-col>
        </v-row>
        <v-row justify="center"> 
        <v-btn class="finishButton btn btn-primary" color=" green lighten-2" @click="searchQuery()" >Search</v-btn>
        <v-btn class="cancelButton btn btn-secondary" color=" grey lighten-1 "  @click="resetList()" >Reset</v-btn>
        </v-row>
        <br>
         <p style="font-size:20px; margin-top:50px" v-if="appointments.length == 0">You don't have any concluded appointments.</p>

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
                                            Patient:
                                            <b
                                                >{{
                                                    appointment.patient
                                                        .firstName
                                                }}
                                                {{
                                                    appointment.patient
                                                        .lastName
                                                }}
                                            </b>
                                        </p>

                                        <p class="card-text">
                                            Price:
                                            <b>
                                                {{ appointment.price }},00 RSD
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
                        <v-row justify="center"> 
                            <v-btn class="finishButton btn btn-primary" color=" purple lighten-3" @click="reportQuery(appointment)" >View report</v-btn>
                        </v-row>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <v-row justify="center">
            <v-dialog
            v-model="reportDialog"
            max-width="700px"
            v-if="reportDialog === true"
            >
            
            <v-card>
                <v-card-title>
                <span class="headline">Appointment report</span>
                </v-card-title>
                <v-card-text>
                <v-container>
                <v-card class="ma-5">
                    <v-card-text >
                        <span>{{this.selectedAppointment.report}}</span>
                    </v-card-text>
                </v-card>
                </v-container>
                </v-card-text>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    plain color="grey darken-4"
                    @click="reportDialog = false"
                >
                    Close
                </v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
        </v-row>
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
    name: "PastAppointmentsPage",

    components: { Button, StarRating },

    data() {
        return {
            query1: '',
            query2: '',
            reportDialog: false,
            selectedAppointment: null,
            appointments: [],
            appointmentSortResults: [],
            options: [
                "-",
                "date older",
                "date recent",
                "name descending",
                "name ascending",
                "surname descending",
                "surname ascending",
            ],

            employeeForRate: {},
            patientsRating: 0.0,
        };
    },

    mounted() {
        this.getAllAppointments();        
    },

    methods: {

        getAllAppointments: function(){
            client({
            url: "appointments/allConcludedAppointmentsForEmployee",
            params: { employeeUsername : localStorage.getItem('USERNAME') },
            method: "GET",
            }).then((response) => (
                this.appointments = response.data));
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
            }else if(sortCriterium === "name ascending"){
                this.appointments = this.appointments.sort(function (a, b) {
                    return (a.patient.firstName > b.patient.firstName) ? 1 : -1;
                });
            }else if(sortCriterium === "name descending"){
                this.appointments = this.appointments.sort(function (a, b) {
                    return (a.patient.firstName < b.patient.firstName) ? 1 : -1;
                });
            }else if(sortCriterium === "surname ascending"){
                this.appointments = this.appointments.sort(function (a, b) {
                    return (a.patient.lastName > b.patient.lastName) ? 1 : -1;
                });
            }else if(sortCriterium === "surname descending"){
                this.appointments = this.appointments.sort(function (a, b) {
                    return (a.patient.lastName < b.patient.lastName) ? 1 : -1;
                });
            }
        },

        searchQuery(){
            if (this.query1 != "") {
                this.appointments = this.appointments.filter(app => {
                    return app.patient.firstName.toLowerCase().includes(this.query1);
                });
            } 
            if (this.query2 != "") {
                this.appointments = this.appointments.filter(app => {
                    return app.patient.lastName.toLowerCase().includes(this.query2);
                });
            } 
        },

        resetList: function(){
            this.getAllAppointments();
            this.query2 = "";
            this.query1 = "";
        },

        reportQuery: function(appointment){
            this.selectedAppointment = appointment;
            this.reportDialog = true;

        }

    }

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.m-l{
    margin-left: 2%;
}

.titl{
    font-size: 30px;
    margin: 30px 60px 30px 60px;
}

.appointments-title {
    font-size: 5vh;
    margin-top: 40px;
}
.card {
    width: 80%;
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
    width: 90%;
}

#appointmentCards {
    margin-top: 50px;
}

.btn{
    margin-left: 1%;
    margin-right: 1%;
}
</style>
