<template>
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
        <v-btn class="finishButton btn btn-primary" color=" green " @click="searchQuery()" >Search</v-btn>
        <v-btn class="cancelButton btn btn-secondary" color=" grey lighten-1 "  @click="resetList()" >Reset</v-btn>
        </v-row>
        <br>
        <br>
        <div :key="patient.id" v-for="patient in patients">
            <v-card
            class="mx-auto my-12"
            max-width="500">
            <v-card-title>{{patient.firstName}} {{patient.lastName}}
            <v-btn :right="true" :absolute="true" @click="loadUpcomingAppointments(patient.username)">
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
            persistent
            max-width="600px"
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
                        <v-list-item-title v-text="item.fixedDate"></v-list-item-title>
                        <v-list-item-title v-text="item.pharmacy.name"></v-list-item-title>
                        </v-row>
                        <v-col
                        cols="12"
                        >
                        <v-btn
                        plain 
                        @click="startAppointment(item)"
                        >
                        Start Appointment
                        </v-btn>

                        <v-btn plain
                        color="error"
                        @click="didntShowUp(item)">
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
</template>

<script>
import {client} from '@/client/axiosClient';
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
        };
    },

    mounted() {
        this.getAllPatients();
        
    },

    methods: {
        resetList: function(){
            this.getAllPatients();
        },
        getAllPatients: function(){
            client({
            url: "appointments/upcomingPatientsForEmployee",
            params: { username : localStorage.getItem('USERNAME') },
            method: "GET",
            }).then((response) => (this.patients = response.data));
        },

        toDateTime: function(d, t1, t2){
            return d + " " + t1 + "-" + t2;
        },

        closeDialog: function(){
            this.dialog = false;
            this.allAppointments = [];
        },

        loadUpcomingAppointments: function(username){
            var link = "appointments/upcomingAppointmentsForPatient";
            client({
                url: link,
                method: 'GET',
                params: { patientUsername : username, employeeUsername : localStorage.getItem('USERNAME'),
                },
            })
            .then((response) => {
                for(var appointment of response.data){
                    appointment.fixedDate = this.toDateTime(appointment.timePeriod.startDate, appointment.timePeriod.startTime, appointment.timePeriod.endTime);
                    this.allAppointments.push(appointment);
                }

                this.dialog = true;
                if(localStorage.getItem("USER_TYPE") === "PHARMACIST"){
                    this.dialogHeadlineText = "Select a counseling!";
                } else {
                    this.dialogHeadlineText = "Select a checkup!";
                }
                console.log(this.allAppointments);
            })
        },

        searchQuery: function(){
            var link = 'appointments/searchPatients';
            client({
                method: 'GET',
                url: link,
                params: { employeeUsername : localStorage.getItem('USERNAME'),
                            patientFirstName : this.query1,
                            patientLastName : this.query2
                },
            })
            .then((response) => {
                if(response.data.length === 0){
                    this.snackbar = true;
                    this.snackbarText = "No patients found!";
                }
                this.patients = response.data;
            })
        },

        startAppointment: function(appointment){
            var link = '/appointmentInProgress';
            const encoded = encodeURI(link + '?patientUsername=' + appointment.patient.username + '&pharmacyId=' + 
                                        appointment.pharmacy.id + '&appointmentId=' + appointment.id);
            this.$router.push(encoded);
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
</style>

