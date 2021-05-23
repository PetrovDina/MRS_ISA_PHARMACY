<template>
<div class="container">
    <div id="patientsDiv">
        <p class="titl">Absence Requests</p>
        <br>
        <br>
        <v-row justify="center"> 
        <v-btn class="finishButton btn btn-primary" color=" green lighten-2" @click="newRequestDialogClicked()" >Create new request</v-btn>
        </v-row>
        <br>
        <br>
        <div :key="absence.id" v-for="absence in absences">
            <v-card
            class="mx-auto my-12"
            max-width="600">
            <v-card-title primary-title class="justify-center">From {{formatDate(absence.timePeriod.startDate)}} to {{formatDate(absence.timePeriod.endDate)}}
            <!--<v-btn :right="true" :absolute="true" @click="loadUpcomingAppointments(patient.username)">
                <v-icon >fa fa-angle-right</v-icon>
            </v-btn>-->
            </v-card-title>
            <v-card-subtitle v-if="isDermatologist === false">Type: {{absence.type}} <br> Status: {{absence.status}}</v-card-subtitle>
            <v-card-subtitle v-if="isDermatologist === true">Pharmacy: {{absence.pharmacy.name}} <br> Type: {{absence.type}} <br> Status: {{absence.status}}</v-card-subtitle>
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
            v-model="newDermRequestDialog"
            persistent
            max-width="800px"
            >
            <v-card>
                <v-card-title>
                <span class="headline">New absence request</span>
                </v-card-title>
                <v-card-text>
                <v-container>
                    <v-row>
                    <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <label for="date">Choose start date</label>
                                        <input
                                            type="date"
                                            id="startdate"
                                            name="startdate"
                                            :min="today"
                                            class="form-control"
                                            v-model="chosenStartDate"
                                        />
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <label for="date">Choose end date</label>
                                        <input
                                            type="date"
                                            id="enddate"
                                            name="enddate"
                                            :min="today"
                                            class="form-control"
                                            v-model="chosenEndDate"
                                        />
                                    </div>
                                </div>
                            </div>
                            </v-row>
                            <v-row>
                            <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <v-autocomplete
                                        label="Absence Type"
                                        required
                                        v-model="typeInput"
                                        :items="types"
                                        item-value="id"
                                        return-object
                                        ></v-autocomplete>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <v-autocomplete
                                        label="Pharmacy"
                                        required
                                        v-model="pharmacyInput"
                                        :items="pharmacies"
                                        item-value="id"
                                        item-text="name"
                                        return-object
                                        ></v-autocomplete>
                                    </div>
                                </div>
                            </div>
                    </v-row>
                </v-container>
                </v-card-text>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green"
                    plain
                    @click="addAbsence()"
                >
                    Save
                </v-btn>
                <v-btn
                    plain
                    @click="newDermRequestDialog = false"
                    color="grey darken-4"
                >
                    Close
                </v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
        </v-row>

        <v-row justify="center">
            <v-dialog
            v-model="newPharmRequestDialog"
            persistent
            max-width="800px"
            >
            <v-card>
                <v-card-title>
                <span class="headline">New absence request</span>
                </v-card-title>
                <v-card-text>
                <v-container>
                    <v-row>
                    <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <label for="date">Choose start date</label>
                                        <input
                                            type="date"
                                            id="startdate"
                                            name="startdate"
                                            :min="today"
                                            class="form-control"
                                            v-model="chosenStartDate"
                                        />
                                    </div>
                                </div>
                            </div>

                            <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <label for="date">Choose end date</label>
                                        <input
                                            type="date"
                                            id="enddate"
                                            name="enddate"
                                            :min="today"
                                            class="form-control"
                                            v-model="chosenEndDate"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm">
                                <div class="c1">
                                    <div class="md-form mx-5 my-5">
                                        <v-autocomplete
                                        label="Type*"
                                        required
                                        v-model="typeInput"
                                        :items="types"
                                        item-value="id"
                                        return-object
                                        ></v-autocomplete>
                                    </div>
                                </div>
                            </div>
                    </v-row>
                </v-container>
                </v-card-text>
                <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="green"
                    plain
                    @click="addAbsence()"
                >
                    Save
                </v-btn>
                <v-btn
                    plain
                    @click="newPharmRequestDialog = false"
                    color="grey darken-4"
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
    name: "RequestAbsencePage",
    data() {
        return {
            absences: [],
            today: moment().format("YYYY-MM-DD"),
            chosenStartDate: null,
            chosenEndDate: null,
            newDermRequestDialog: false,
            newPharmRequestDialog: false,
            types: ["VACATION", "ABSENCE"],
            typeInput: null,
            pharmacyInput: null,
            isDermatologist: false,
            pharmacies: [],
            snackbar: false,
            snackbarText: null,
            vertical: false,
        };
    },

    mounted() {
        this.getAllAbsences();
        this.checkUserType();
    },

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        getAllAbsences: function(){
            client({
            url: "absences/allAbsencesForEmployee",
            params: { username : localStorage.getItem('USERNAME') },
            method: "GET",
            }).then((response) => (
                this.absences = response.data));
        },

        checkUserType: function(){
            if(localStorage.getItem('USER_TYPE') == 'DERMATOLOGIST'){
                this.isDermatologist = true;
                client({
                url: "employments/pharmaciesOfLoggedInDermatologist",
                params: { username : localStorage.getItem('USERNAME') },
                method: "GET",
                }).then((response) => (
                    this.pharmacies = response.data));
            }
            if(localStorage.getItem('USER_TYPE') == 'PHARMACIST'){
                this.isDermatologist = false;
                client({
                url: "employments/pharmacyOfLoggedInPharmacist",
                params: { username : localStorage.getItem('USERNAME') },
                method: "GET",
                }).then((response) => (
                    this.pharmacies.push(response.data)));
                }
             
        },

        newRequestDialogClicked: function(){
            if(this.isDermatologist){
                this.newDermRequestDialog = true;
            }else{
                this.newPharmRequestDialog = true;
                this.pharmacyInput = this.pharmacies[0];
            }
        },

        addAbsence: function(){
            client({
                url: "absences/create",
                params: { pharmacyId: this.pharmacyInput.id, startDate: this.chosenStartDate, endDate: this.chosenEndDate,
                 employeeUsername : localStorage.getItem('USERNAME'), absenceType: this.typeInput },
                method: "GET",
                }).then((response) => {
                    if(response.data === true){
                        this.snackbarText = "Successfully created new absence request!";
                        this.snackbar = true;
                        this.appFree = false;
                        this.newDermRequestDialog = false;
                        this.newPharmRequestDialog = false;
                        this.pharmacyInput = null;
                        this.chosenStartDate = null;
                        this.chosenEndDate = null;
                        this.typeInput = null;
                        this.getAllAbsences();
                    }else{
                        this.snackbarText = "Cannot request absence while other appointments exist!";
                        this.snackbar = true;
                    }
                });
        }
    },

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .main-container{
        margin: 2%;
    }
    .cancel-btn{
        text-decoration: none;
        color: 'green lighten-1'
    }
    .titl{
        font-size: 30px;
    }
    #patientsDiv{
    margin: 30px 60px 30px 60px;
}
</style>
