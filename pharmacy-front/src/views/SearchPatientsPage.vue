<template>
<div class="container">
    <div id="patientsDiv">
        <p class="titl">Patients with upcoming appointments</p>
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
        <div id="sort-and-filter" v-if="patients.length != 0">
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
        <br>
        <div :key="item.patient.id" v-for="item in patients">
            <v-card
            class="mx-auto my-12"
            max-width="800">
            <v-card-title>{{item.patient.firstName}} {{item.patient.lastName}}
            <v-btn :right="true" :absolute="true" @click="loadUpcomingAppointments(item.appointments)">
                <v-icon >fa fa-angle-right</v-icon>
            </v-btn>
            </v-card-title>
            </v-card>
        </div>

        <v-snackbar
        v-model="snackbar"
        :vertical="vertical"
        light
        timeout="2000"
        >
        {{snackbarText}}

        <template v-slot:action="{ attrs }">
            <v-btn
            color="indigo"
            text
            v-bind="attrs"
            @click="snackbar = false"
            >
            Close
            </v-btn>
        </template>
        </v-snackbar>

        <v-row justify="center">
            <v-dialog
            v-model="dialog"
            max-width="700px"
            >
            
            <v-card>
                <v-card-title>
                <span class="headline">{{dialogHeadlineText}}</span>
                </v-card-title>
                <v-card-text>
                <v-container>
                <v-list>
                <v-list-item-group
                    v-model="selectedItem"
                    color="primary"
                >
                    <v-list-item
                    v-for="item of allAppointments"
                    :key="item.id"
                    >
                    <v-list-item-content>
                        <v-row>
                            <v-col
                          cols="12"
                          sm="6"
                          md="6"
                          ><v-list-item-title >Start date: {{item.fixedDate}}</v-list-item-title>
                            </v-col>
                            <v-col
                          cols="12"
                          sm="6"
                          md="6"
                          >
                        <v-list-item-title >Pharmacy: {{item.pharmacy.name}}</v-list-item-title>
                            </v-col>
                        </v-row>
                        <v-col
                        cols="12"
                        >
                        <v-btn
                        plain 
                        color="green"
                        @click="startAppointment(item)"
                        v-if="item.available === true">
                        Start Appointment
                        </v-btn>

                        <v-btn plain
                        color="error"
                        @click="didntShowUp(item)"
                        v-if="item.available === true">
                        Didnt show up
                        </v-btn>
                        </v-col>

                    </v-list-item-content>
                    </v-list-item>
                </v-list-item-group>
                </v-list>
                </v-container>
                </v-card-text>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    plain
                    @click="closeDialog()"
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
import {client} from '@/client/axiosClient';
import moment from "moment";
export default {
    name: "SearchPatientsPage",

    data() {
        return {
            patients: [],
            allAppointments : [],
            query1: '',
            query2: '',
            dialog: false,
            dialogHeadlineText: '',
            snackbarText: '',
            snackbar: false,
            vertical: false,
            selectedItem: null,
            options: [
                "-",
                "date older",
                "date recent",
                "name descending",
                "name ascending",
                "surname descending",
                "surname ascending",
            ],
        };
    },

    mounted() {
        this.getAllPatients();
        
    },

    methods: {
        resetList: function(){
            this.getAllPatients();
        },

        closerDate(olddate, newdate){
            var parts1 = olddate.startDate.split('-');
            var parts2 = newdate.startDate.split('-');
            if((moment(new Date(parts1[0], parts1[1]-1, parts1[2])).format("MMMM Do yyyy") > moment(new Date(parts2[0], parts2[1]-1, parts2[2])).format("MMMM Do yyyy"))){
                return newdate;
            }else{
                return olddate;
            }
        },

        getAllPatients: function(){
            var patients = [];
            client({
            url: "appointments/upcomingPatientsForEmployee",
            params: { username : localStorage.getItem('USERNAME') },
            method: "GET",
            }).then((response) => {
                for(var pat of response.data){
                    var link = "appointments/upcomingAppointmentsForPatient";
                    client({
                        url: link,
                        method: 'GET',
                        params: { patientUsername : pat.username, employeeUsername : localStorage.getItem('USERNAME'),
                        },
                    })
                    .then((response) => {
                        var apps = [];
                        var clos = null;
                        var p = null;
                        for(var appointment of response.data){
                            p = appointment.patient;
                            var avail = false;
                            var parts1 = appointment.timePeriod.startDate.split('-');
                            var mon1 = parts1[1]-1;
                            if(appointment.status === 'RESERVED' && !appointment.inProgress &&
                            ((moment(new Date(parts1[0], mon1, parts1[2])).format("MMMM Do yyyy") == moment(new Date()).format("MMMM Do yyyy")))){
                                avail = true;
                            } 
                            appointment.available = avail;
                            appointment.fixedDate = this.toDateTime(appointment.timePeriod.startDate, appointment.timePeriod.startTime, appointment.timePeriod.endTime);
                            if(clos==null){
                                clos = appointment;
                            }else{
                                var parts1 = clos.timePeriod.startDate.split('-');
                                var parts2 = appointment.timePeriod.startDate.split('-');
                                if((moment(new Date(parts1[0], parts1[1]-1, parts1[2])).format("MMMM Do yyyy") > moment(new Date(parts2[0], parts2[1]-1, parts2[2])).format("MMMM Do yyyy"))){
                                    clos = appointment;
                                }
                            }
                            apps.push(appointment);
                        }    
                        patients.push({
                            patient: p,
                            appointments: apps,
                            closest: clos,
                        });
                        });
                }
                this.patients = patients});
        },

        toDateTime: function(d, t1, t2){
            return d + " " + t1 + "-" + t2;
        },

        closeDialog: function(){
            this.dialog = false;
            this.allAppointments = [];
        },

        loadUpcomingAppointments: function(appointments){
            this.allAppointments = appointments;
            this.dialog = true;
            if(localStorage.getItem("USER_TYPE") === "PHARMACIST"){
                this.dialogHeadlineText = "Select a counseling!";
            } else {
                this.dialogHeadlineText = "Select a checkup!";
            }
        },

        searchQuery(){
            if (this.query1 != "") {
                this.patients = this.patients.filter(p => {
                    return p.patient.firstName.toLowerCase().includes(this.query1);
                });
            } 
            if (this.query2 != "") {
                this.patients = this.patients.filter(p => {
                    return p.patient.lastName.toLowerCase().includes(this.query2);
                });
            } 
        },

        sortSelected(event) {
            let sortCriterium = event.target.value;
            let self = this;
            if (sortCriterium === "-") {
                this.patients = this.patients;
                return;
            } else if (sortCriterium === "date recent") {
                this.patients = this.patients.sort(function (a, b) {
                    let dateA = new Date(a.closest.timePeriod.startDate);
                    let dateB = new Date(b.closest.timePeriod.startDate);
                    return dateB - dateA;
                });
            } else if (sortCriterium === "date older") {
                this.patients = this.patients.sort(function (a, b) {
                    let dateA = new Date(a.closest.timePeriod.startDate);
                    let dateB = new Date(b.closest.timePeriod.startDate);
                    return dateA - dateB;
                });
            }else if(sortCriterium === "name ascending"){
                this.patients = this.patients.sort(function (a, b) {
                    return (a.patient.firstName > b.patient.firstName) ? 1 : -1;
                });
            }else if(sortCriterium === "name descending"){
                this.patients = this.patients.sort(function (a, b) {
                    return (a.patient.firstName < b.patient.firstName) ? 1 : -1;
                });
            }else if(sortCriterium === "surname ascending"){
                this.patients = this.patients.sort(function (a, b) {
                    return (a.patient.lastName > b.patient.lastName) ? 1 : -1;
                });
            }else if(sortCriterium === "surname descending"){
                this.patients = this.patients.sort(function (a, b) {
                    return (a.patient.lastName < b.patient.lastName) ? 1 : -1;
                });
            }
        },

        startAppointment: function(appointment){
            client({
                method: 'GET',
                url: 'appointments/setAppointmentInProgress',
                params: {appointmentId: appointment.appointmentId}
            })
            .then((response) => {
                if(response.data == "ok"){
                    var link = '/appointmentInProgress';
                    const encoded = encodeURI(link + '?patientUsername=' + appointment.patient.username + '&pharmacyId=' + 
                                                appointment.pharmacy.id + '&appointmentId=' + appointment.id);
                    this.$router.push(encoded);
                }else{
                    this.snackbarText = response.data;
                    this.snackbar = true;
                }
            })
            
        },

         didntShowUp: function(appointment){
            client({
            method: 'GET',
            url: 'appointments/patientDidntShowUp',
            params: {appointmentId: appointment.id}
            })
            
            this.getAllPatients();
            this.closeDialog();
        },
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.m-l{
    margin-left: 2%;
}

.titl{
    font-size: 30px;
}


p{
    font-size: 20px;
}

span{
        color: grey;
}

.btn{
    margin-left: 1%;
    margin-right: 1%;
}

#patientsDiv{
    margin: 30px 60px 30px 60px;
}

.shrink{
    width: 500px;
}

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

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

