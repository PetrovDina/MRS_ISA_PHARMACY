<template>
<div class="container">
  <p class="titl">Appointment in progress</p>
  <br>
  <div id="homeDiv">
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >Appointment report</div>
                <v-spacer></v-spacer>
                
            </v-card-title> 
            <v-card-text>
                <v-textarea v-model="report">
                </v-textarea>
            </v-card-text>
        </v-card>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >Write a therapy</div>
                <v-spacer></v-spacer>
            </v-card-title> 
            <v-card-text>
                <div
                    class="mx-auto"
                    max-width="1000"
                    tile
                >
                <v-list>
                  <v-row justify="center">
                  <v-subheader >Add therapy items</v-subheader></v-row>
                  <v-btn plain
                  @click.stop="dialog = true"
                  >
                      <v-icon color="green">fa fa-plus-circle</v-icon>
                  </v-btn>
                  <v-list-item-group
                      v-model="selectedItem"
                      color="primary"
                  >
                  
                      <v-list-item
                      v-for="(item, i) in items"
                      :key="i"
                      >
                      <v-list-item-content>
                          <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          >
                          <v-list-item-title v-text="item.medicine.nameAndForm"></v-list-item-title>
                          </v-col>
                          <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          >
                          <v-list-item-title v-text="item.medicine.manufacturer"></v-list-item-title>
                          </v-col>
                          <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          >
                          <v-list-item-title v-text="'x ' + item.quantity"></v-list-item-title>
                          </v-col>
                          <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          >
                          <v-btn plain @click="removeItem(item)">remove item</v-btn>
                          </v-col>

                      </v-list-item-content>
                      </v-list-item>
                  </v-list-item-group>
                  </v-list>
                </div>
            </v-card-text>
            <v-card-actions>
              <v-row justify="center">
              <v-btn
                plain
                color="green"
                @click="newAppointmentDialog = true"
              >
                Book another appointment
              </v-btn>
              <v-btn
                plain
                color="green"
                @click="submit()"
              >
                Submit
              </v-btn>
              <!--<v-btn color="green darken-4" plain
                    @click="cancel()">
                Cancel
              </v-btn>-->
              </v-row>
            </v-card-actions>
        </v-card>
  </div>

<!-- New prescription item-->
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
    >
      
      <v-card>
        <v-card-title>
          <span class="headline">New therapy item</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-autocomplete
                  label="Medication*"
                  required
                  v-model="medicineInput"
                  :items="storage"
                  item-text="medication.nameAndForm"
                  item-value="id"
                  return-object
                ></v-autocomplete>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Quantity"
                  type="number"
                  :min="1"
                  v-model="quantityInput"
                ></v-text-field>
                </v-col>
                <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Therapy duration (days)"
                  type="number"
                  :min="1"
                  v-model="therapyDurationInput"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green"
            plain
            @click="testAvailability()"
          >
            Test availability
          </v-btn>
          <v-btn
            color="green"
            plain
            @click="addPrescriptionItem()"
            v-if="itemAvailable === true"
          >
            Save
          </v-btn>
          <v-btn
              color="green"
              plain
              @click="showAlternatives()"
              v-if="showAltBtn === true "
            >
            Choose alternative
          </v-btn>
          <v-btn
            plain
            @click="dialog = false"
            color="grey darken-4"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
  
<!-- Alternatives -->
<v-row justify="center">
    <v-dialog
      v-model="alternativesDialog"
      persistent
      max-width="600px"
    >
      
      <v-card>
        <v-card-title>
          <span class="headline">New therapy alternatives</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-autocomplete
                  label="Medication*"
                  required
                  v-model="alternativeInput"
                  :items="alternatives"
                  item-text="medication.nameAndForm"
                  item-value="id"
                  return-object
                ></v-autocomplete>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Quantity"
                  type="number"
                  :min="1"
                  v-model="quantityInput"
                ></v-text-field>
                </v-col>
                <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  label="Therapy duration (days)"
                  type="number"
                  :min="1"
                  v-model="therapyDurationInput"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green"
            plain
            @click="testAvailability()"
          >
            Test availability
          </v-btn>
          <v-btn
            color="green"
            plain
            @click="addPrescriptionItem()"
            v-if="itemAvailable === true"
          >
            Save
          </v-btn>
          <v-btn
            plain
            @click="alternativesDialog = false"
            color="grey darken-4"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>

<!-- Derm available appointment -->
  <v-row justify="center">
    <v-dialog
      v-model="dermAppointmentDialog"
      persistent
      max-width="700px"
    >
    <v-card>
        <v-card-title>
          <span class="headline">Available appointments</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-list-item-group
                      color="primary"
                  >
                  
                      <v-list-item
                      v-for="(appointment, i) in availableAppointments"
                      :key="i"
                      >
                      <v-list-item-content>
                          <v-col
                          cols="12"
                          sm="7"
                          md="4"
                          >
                          <v-list-item-title>{{formatDate(
                                                    appointment.timePeriod
                                                        .startDate
                                                )}}</v-list-item-title>
                          </v-col>
                          <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          >
                          <v-list-item-title >{{
                                                    appointment.timePeriod
                                                        .startTime
                                                }}
                                                -
                                                {{
                                                    appointment.timePeriod
                                                        .endTime
                                                }}</v-list-item-title>
                          </v-col>
                          
                          <v-col
                          cols="12"
                          sm="6"
                          md="3"
                          >
                          <v-btn color="green" plain @click="bookAvailableAppointment(appointment)">Book appointment</v-btn>
                          </v-col>

                      </v-list-item-content>
                      </v-list-item>
                  </v-list-item-group>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            plain
            @click="dermAppointmentDialog = false"
            color="grey darken-4"
          >
            Close
          </v-btn>
        </v-card-actions>
    </v-card>
    </v-dialog>
  </v-row>

  <!-- New appointment -->
  <v-row justify="center">
    <v-dialog
      v-model="newAppointmentDialog"
      persistent
      max-width="600px"
    >
    <v-card>
        <v-card-title>
          <span class="headline">New appointment</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <div class="col-sm">
                        <div class="c1">
                            <div class="md-form mx-5 my-5">
                                <label for="date">Choose date</label>
                                <input
                                    type="date"
                                    id="date"
                                    name="date"
                                    :min="today"
                                    class="form-control"
                                    v-model="chosenDate"
                                />
                            </div>
                        </div>
                    </div>

                    <div class="col-sm">
                        <div class="c2">
                            <div class="md-form mx-5 my-5">
                                <label class="label" for="start_time"
                                    >Choose time</label
                                >
                                <input
                                    type="time"
                                    id="start_time"
                                    class="form-control"
                                    v-model="chosenTime"
                                />
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
            @click="addAppointment()"
          >
            Add appointment
          </v-btn>
          <v-btn
            color="green"
            plain
            @click="chooseExistingClicked()"
            v-if="isDermatologist === true"
          >
            Choose existing 
          </v-btn>
          <v-btn
            plain
            @click="newAppointmentDialog = false"
            color="grey darken-4"
          >
            Close
          </v-btn>
        </v-card-actions>
    </v-card>
    </v-dialog>
  </v-row>
    
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

</div>
</template>

<script>
import {client} from '@/client/axiosClient';
import moment from "moment";
  export default {
    name: "AppointmentInProgress",
    data () {
      return {
        disabledDates: {
          to: new Date(Date.now() - 8640000)
        },
        today: moment().format("YYYY-MM-DD"),
        patientUsername: null,
        report: null,
        medicineInput: null,//storageItem
        quantityInput: 0,
        dialog: false,
        dermAppointmentDialog: false,
        alternativesDialog: false,
        alternatives: [],
        showAltBtn: false,
        alternativeInput: null,
        selectedItem: 1,
        storage: [],
        items: [],
        availableAppointments: [],
        snackbar: false,
        snackbarText: null,
        vertical: false,
        therapyDurationInput: 0,
        itemAvailable: false,
        pharmacyId: null,
        newAppointmentDialog: false,
        appointmentId: null,
        chosenDate: null,
        chosenTime: null,
        appFree: false,
        isDermatologist: false,
      }
      
    },
    mounted(){
        this.patientUsername = this.$route.query.patientUsername;
        this.pharmacyId = this.$route.query.pharmacyId;
        this.appointmentId = this.$route.query.appointmentId;
        client({
            method: 'GET',
            url: 'pharmacyStorageItem/getPharmacyStorage',
            params: { pharmacyId : this.pharmacyId, patientUsername : this.patientUsername}
        })
        .then((response) => {
            this.storage = response.data;
        })
        if(localStorage.getItem("USER_TYPE")=='DERMATOLOGIST'){
            this.isDermatologist = true;
        }
    },

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },
        addPrescriptionItem: function(){
            var input = null;
            if(this.dialog == true){
              input = this.medicineInput;}
            else{
              input = this.alternativeInput;
            }
            var exists = false;
            for(var i of this.items){
              if(i.medicine.id === input.medication.id){
                exists = true;
                i.quantity = (Number)(i.quantity) + (Number)(this.quantityInput);
                i.therapyDuration = (Number)(i.therapyDuration) + (Number)(this.therapyDurationInput);
              }
            }
            if(exists === false){
                  this.items.push({
                    medicine: input.medication,
                    quantity: this.quantityInput,
                    therapyDuration: this.therapyDurationInput,
                    storageId: input.id,
                })
              
            }
            
            for(var i = 0; i < this.storage.length; i++){
                if(this.storage[i].medication.id === input.medication.id){
                  this.storage[i].quantity = input.quantity - this.quantityInput;
                }
              }

            this.medicineInput = null;
            this.alternativeInput = null;
            this.quantityInput = 0;
            this.therapyDurationInput = 0;
            this.itemAvailable = false;
            this.showAltBtn = false;
        },

        bookAvailableAppointment: function(appointment){
          client({
            method: 'GET',
            url: 'appointments/bookAvailableAppointment',
            params: { patientUsername : this.patientUsername, appointmentId : appointment.id}
          })
          .then((response) => {
              if(response.data === "Free"){
              this.snackbarText = "Successfully booked appointment!";
              this.snackbar = true;
              this.dermAppointmentDialog = false;
            }else{
              this.snackbarText = response.data;
              this.snackbar = true;
              //this.dermAppointmentDialog = false;
              chooseExistingClicked();
            }
          })
        },

        removeItem: function(toRemove){
            const backup = this.items;
            this.items = [];
            for(var item of backup){
                if(!(item.medicine.id === toRemove.medicine.id)){
                    this.items.push(item);                    
                }
            }     
            for(var i = 0; i < this.storage.length; i++){
              if(this.storage[i].medication.id === toRemove.medicine.id){
                this.storage[i].quantity = (Number)(this.storage[i].quantity) + (Number)(toRemove.quantity);
              }
            }     
        },

        cancel: function(){
            var pth = "";
            if(localStorage.getItem("USER_TYPE")=='PHARMACIST'){
                pth = '/pharmacist-home-page';
            }else{
                pth = '/derm-home';
            }
            setTimeout(() => { this.$router.push({path: pth})}, 1000);
        },

        showAlternatives: function(){            
            client({
                method: 'GET',
                url: 'pharmacyStorageItem/getAlternatives',
                params: { pharmacyId : this.pharmacyId, medicationId : this.medicineInput.medication.id, patientUsername : this.patientUsername}
            })
            .then((response) => {
                this.alternatives = response.data;
                if(this.alternatives.length === 0){
                    this.alternativesDialog = false;
                    this.snackbar = true;
                    this.snackbarText = "No alternatives available for " + this.medicineInput.medication.nameAndForm;
                }else{
                    this.dialog = false;
                    this.quantityInput = 0;
                    this.therapyDurationInput = 0;
                    this.itemAvailable = false;
                    this.alternativesDialog = true;
                }
            })
        },

        testAvailability: function(){
          if(this.quantityInput === 0 || this.medicineInput === null || this.therapyDurationInput === 0){
            this.snackbar = true;
            this.snackbarText = "Must fill in all fields!";
          }
          else{
            var input = null;
            if(this.dialog == true){
              input = this.medicineInput;
              }
            else{
              input = this.alternativeInput;
            }
            if(input.quantity >= this.quantityInput){
              
              this.itemAvailable = true;
              this.showAltBtn = false;
              this.snackbarText = "Item AVAILABLE, click 'save' to add prescription.";
              this.snackbar = true;
            }else{
              this.itemAvailable = false;
              
              this.snackbarText = this.quantityInput + " NOT AVAILABLE,  " + input.quantity + " " + input.medication.nameAndForm + " available.";
              this.snackbar = true;
              this.showAltBtn = true;
            }
          }
        },

        submit: function(){
            if(this.report === null){
              this.snackbar = true;
              this.snackbarText = "Must write report!";
            }else{
              var snackText = null;            
              for(var item of this.items){
                  client({
                    url: 'pharmacyStorageItem/checkAvailableQuantity',
                    method: 'GET',
                    params: { storageId : item.storageId, pharmacyId: this.pharmacyId },
                  })
                  .then((response) => {
                    if(response.data >= item.quantity){
                        snackText += item.quantity + " " + item.medicine.nameAndForm + " no longer available, " + response.data + " remaining.\n";
                    }
                  })
              }
              if(snackText === null){
                client({
                  method: 'GET',
                  url: 'therapy/create',
                  params: { appointmentId: this.appointmentId}
                })
                .then((response) => {
                  for(var item of this.items){
                  client({
                    method: 'GET',
                    url: 'therapyItem/addPrescription',
                    params: { storageId: item.storageId, quantity: item.quantity, duration: item.therapyDuration,
                            pharmacyId: this.pharmacyId, ePrescriptionId: response.data.id},
                    })
                    .then((response) => {
                        if(response.data != "ok"){
                          this.snackbar = true;
                          this.snackbarText = response.data;
                        }
                    })
                    
                }
                })
                
                this.finishAppointment();
              }else{
                this.snackbar = true;
                this.snackbarText = snackText;
                client({
                    method: 'GET',
                    url: 'pharmacyStorageItem/getPharmacyStorage',
                    params: { pharmacyId : this.pharmacyId, patientUsername : this.patientUsername}
                })
                .then((response) => {
                    this.storage = response.data;
                })
              }
              
          }
        },

        finishAppointment(){
          var pth = "";
          if(localStorage.getItem("USER_TYPE")=='PHARMACIST'){
              pth = '/pharmacist-home-page';
          }else{
              pth = '/derm-home';
          }
          client({
              method: 'GET',
              params: {appointmentId: this.appointmentId, report: this.report},
              url: 'appointments/finishAppointment',
          }).then((response) => {
              {
              setTimeout(() => { 
                  this.$router.push({path: pth})}, 1000);
          }
          this.snackbar = true;
          this.snackbarText = "Successfully finished appointment!";
          })
        },

        addAppointment: function(){
          var t = null;
          if(localStorage.getItem("USER_TYPE").equals("DERMATOLOGIST")) {
            t = (AppointmentType.DERMATOLOGIST_EXAMINATION);
          }
          if(localStorage.getItem("USER_TYPE").equals("PHARMACIST")) {
            t = (AppointmentType.PHARMACIST_CONSULTATION);
          }
          client({
            method: "POST",
                data: {
                    employee: {
                        username: localStorage.getItem("USERNAME"),
                    },
                    timePeriod: {
                        startDate: this.chosenDate,
                        startTime: this.chosenTime,
                        endDate: this.chosenDate,
                        endTime: this.chosenTime,
                    },

                    pharmacy: {
                        id: this.pharmacyId,
                    },

                    patient: {
                        username: this.patientUsername,
                    },

                    type: t,
                },
              /*method: 'GET',
              params: {employeeUsername: localStorage.getItem("USERNAME"), patientUsername: this.patientUsername, 
                      pharmacyId: this.pharmacyId, startDate: this.chosenDate, startTime: this.chosenTime,
                       userType: localStorage.getItem("USER_TYPE")},*/
              url: 'appointments/createNewAppointmentByEmployee',
          }).then((response) => {
            if(response.data == "Free"){
              this.snackbarText = "Successfully booked new appointment!";
              this.snackbar = true;
              this.appFree = false;
              this.chosenDate = null;
              this.chosenTime = null;
              this.newAppointmentDialog = false;
            }else{
              this.snackbarText = response.data;
              this.snackbar = true;
              this.appFree = false;
            }
          })
          
        },

        chooseExistingClicked: function(){          
          if(localStorage.getItem("USER_TYPE")=='DERMATOLOGIST'){
            this.isDermatologist = true;
              client({
                  method: 'GET',
                  url: 'appointments/getAvailableDermAppointments',
                  params: { employeeUsername: localStorage.getItem("USERNAME"), pharmacyId : this.pharmacyId}
              })
              .then((response) => {
                  this.availableAppointments = response.data;
              })            
          }
          this.newAppointmentDialog = false;
          this.dermAppointmentDialog = true;
        }
    },
  }
</script>

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

</style>