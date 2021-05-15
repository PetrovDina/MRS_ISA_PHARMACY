<template>
<div class="main-container">
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
                <div >Write a prescription</div>
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
                  <v-subheader >Add prescription items</v-subheader></v-row>
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
                      <v-list-item-icon color="green">
                          <v-icon>fa fa-cart-plus</v-icon>
                      </v-list-item-icon>
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
                Book another checkup
              </v-btn>
              <v-btn
                plain
                color="green"
                @click="submit()"
              >
                Submit
              </v-btn>
              <v-btn color="green darken-4" plain
                    @click="cancel()">
                Cancel
              </v-btn>
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
          <span class="headline">New item</span>
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
          <span class="headline">New item alternatives</span>
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

  <!-- TODO New appointment -->
    
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
  export default {
    name: "AppointmentInProgress",
    data () {
      return {
        disabledDates: {
          to: new Date(Date.now() - 8640000)
        },
        datePicker: null,
        patientUsername: null,
        report: null,
        medicineInput: null,//storageItem
        quantityInput: 0,
        dialog: false,
        alternativesDialog: false,
        alternatives: [],
        showAltBtn: false,
        alternativeInput: null,
        selectedItem: 1,
        storage: [],
        items: [],
        snackbar: false,
        snackbarText: null,
        vertical: false,
        therapyDurationInput: 0,
        itemAvailable: false,
        pharmacyId: null,
        newAppointmentDialog: false,
        dateAvailable: false,
        priceInput: 0,
        appointmentId: null,
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
    },

    methods: {
        addPrescriptionItem: function(){
            this.items.push({
                medicine: this.medicineInput.medication,
                quantity: this.quantityInput,
                therapyDuration: this.therapyDurationInput,
                storageId: this.medicineInput.id,
            })
            this.medicineInput = null;
            this.alternativeInput = null;
            this.quantityInput = 0;
            this.therapyDurationInput = 0;
            this.itemAvailable = false;
            this.showAltBtn = false;
        },
        removeItem: function(toRemove){
            const backup = this.items;
            this.items = [];
            for(var item of backup){
                if(!(item.text === toRemove.text && item.quantity === toRemove.quantity)){
                    this.items.push(item);
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
            if(this.dialog === true)
              input = this.medicineInput;
            else
              input = this.alternativeInput;
            if(input.quantity >= this.quantityInput){
              for(var i = 0; i < this.storage.length; i++){
                if(this.storage[i].id === input.id){
                  this.storage[i].quantity = input.quantity - this.quantityInput;
                }
              }
              this.itemAvailable = true;
              this.showAltBtn = false;
              this.snackbarText = "Item AVAILABLE, click 'save' to add prescription.";
              this.snackbar = true;
            }else{
              this.itemAvailable = false;
              
              this.snackbarText = this.quantityInput + " NOT AVAILABLE " + ",  " + input.quantity + " " + input.medication.nameAndForm + " available.";
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
                  url: 'eprescription/create',
                  params: { appointmentId: this.appointmentId}
                })
                .then((response) => {
                  for(var item of this.items){
                  client({
                    method: 'GET',
                    url: 'prescriptionItem/addPrescription',
                    params: { storageId: item.storageId, quantity: item.quantity, duration: item.therapyDuration,
                            pharmacyId: this.pharmacyId, ePrescriptionId: response.data.id},
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