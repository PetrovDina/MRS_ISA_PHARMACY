<template>
    <div v-if="!!isModalShow()" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <h4>{{medication.name}}</h4>
            <div class="dajv">
                <table id="medicationTable" class="table">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Prescription</th>
                            <th scope="col">Form</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>{{ medication.id }}</td>
                            <td>{{ medication.manufacturer }}</td>
                            <td>{{ medication.prescriptionReq ? "required" : "not required" }}</td>
                            <td>{{ medication.form }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="container" style="margin-bottom: 30px">
                <div class="row">
                    <div class="col"><label for="description"><b>Description:</b></label></div>
                    <div class="col"><label for="content"><b>Content:</b></label></div>
                    <div class="w-100"></div>
                    <div class="col"><textarea disabled cols="50" rows="5" style="border:solid 1px rgba(32, 102, 75, 0.295);" id="description"  v-model="medication.description"></textarea></div>
                    <div class="col"><textarea disabled cols="50" rows="5" style="border:solid 1px rgba(32, 102, 75, 0.295);" id="content"  v-model="medication.content"></textarea></div>
                </div>
            </div>
            <label><b>Alternatives</b></label>
            <div class="dajv">
                <table id="alternativesTable" class="table">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Prescription</th>
                            <th scope="col">Form</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr :key="medication.id" v-for="medication in medication.alternatives">
                            <td>{{ medication.id }}</td>
                            <td>{{ medication.name }}</td>
                            <td>{{ medication.manufacturer }}</td>
                            <td>{{ medication.prescriptionReq ? "required" : "not required" }}</td>
                            <td>{{ medication.form }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div v-if="!isPharmacyAdmin()">
                <label><b>Available in pharmacies</b></label>
                <PharmaciesWithPriceComponent
                            :results="pharmacies"
                            :canSelect=false
                ></PharmaciesWithPriceComponent>
            </div>
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";
import PharmaciesWithPriceComponent from "./PharmaciesWithPriceComponent.vue";

export default {
    name : "ModaWindowMedicationDetail",
    components : {Button, PharmaciesWithPriceComponent},
    props : {
        modal_show : Boolean,
        medicationId: Number
    },
    data() {
        return {
            medication: { id: -1},
            pharmacies: [],
            top: 100
        }
    },

    methods : {
        closeWindow: function()
        {
            this.$emit('modal-closed');
        },

        isModalShow: function()
        {
            if(this.medicationId != undefined && this.modal_show != false && this.medication.id != this.medicationId)
            {
                let url = "med/details/" + this.medicationId;
                client({
                        url: url,
                        method: "GET",
                }).then((response) => {
                        this.medication = response.data;
                        client({
                            url: "pharmacyStorageItem/allByMedicationAndQuantity",
                            params: {medicationId: this.medication.id},
                            method: "GET",
                        }).then((response) => (this.pharmacies = response.data));
                });

            }
                
            return this.modal_show === true;
        },
        isPharmacyAdmin : function(){
            return localStorage.getItem('USER_TYPE') === "PHARMACY_ADMIN";
        }
    }
};
</script>


<style scoped>

.dajv{
    margin: 30px 100px 30px 100px
}

thead {
    background-color: rgba(32, 102, 75, 0.295);
}

#close_btn{
    height: 30px;
    width: 25px;
}

.v-text-field{
    margin: 10px 60px;
    width: 300px;
}

body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
  display: block; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 0px;
  border: 1px solid #888;
  width: 75%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
</style>