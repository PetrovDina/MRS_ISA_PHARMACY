<template>
    <div id="detailsDiv">
        <div>
            <h1>{{medication.name}}</h1>
            <star-rating
                        data-toggle="tooltip"
                        data-placement="top"
                        :title="medication.rating"
                        active-color="rgba(155, 82, 151, 0.527)"
                        :star-size="50"
                        :read-only="true"
                        :show-rating="false"
                        :rating="medication.rating"
                        :increment="0.1"
                    ></star-rating>
                    <Button
                        v-if="isPatient() == true"
                        @action-performed="rateClicked()"
                        text="Rate medication"
                        style="margin-top: 10px"
                    >
                    </Button>
            <div class="dajv">
                <table id="medicationTable" class="table">
                    <thead>
                        <tr>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Prescription</th>
                            <th scope="col">Form</th>
                            <th scope="col">Loyalty points</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>{{ medication.manufacturer }}</td>
                            <td>{{ medication.prescriptionReq ? "required" : "not required" }}</td>
                            <td>{{ medication.form }}</td>
                            <td>{{ medication.loyaltyPoints }}</td>
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
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Prescription</th>
                            <th scope="col">Form</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr :key="medication.id" v-for="medication in medication.alternatives">
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

        <!-- modal can rate  -->
        <div
            class="modal fade bd-example-modal-lg .modal-lg"
            id="rateModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div
                class="modal-dialog modal-dialog-centered .modal-lg"
                role="document"
            >
                <div class="modal-content justify-content-center">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            How would you rate {{ medication.name }}?
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
                    <div
                        class="modal-body w-100 text-center justify-content-center"
                    >
                        <star-rating
                            style="margin: 0 auto"
                            v-model="patientsRating"
                            active-color="rgba(155, 82, 151, 0.527)"
                        ></star-rating>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            @click="saveRating()"
                        >
                            Save rating
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- modal cant rate -->
        <div
            class="modal fade bd-example-modal-lg .modal-lg"
            id="cantRateModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalCenterTitle"
            aria-hidden="true"
        >
            <div
                class="modal-dialog modal-dialog-centered .modal-lg"
                role="document"
            >
                <div class="modal-content justify-content-center">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">
                            Rating unavailable
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
                    <div
                        class="modal-body w-100 justify-content-center"
                    >
                        <p style="text-align:justify">Unfortunately, you can't rate {{ medication.name }}.<br> In
                        order to rate a medication, you have to complete at least
                        one <b>reservation</b>, or <b>ePrescription</b> with that medication.</p>
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
import Button from "@/components/Button";
import PharmaciesWithPriceComponent from "@/components/PharmaciesWithPriceComponent.vue";
import StarRating from "vue-star-rating";
import $ from "jquery";

export default {
    name : "MedicationDetailsView",
    components : {Button, PharmaciesWithPriceComponent, StarRating},
    props : {
        medicationId: Number
    },
    data() {
        return {
            medication: { id: -1},
            pharmacies: [],
            top: 100,
            patientsRating: 0.0,
            canRate: true
        }
    },

    methods : {

        isPharmacyAdmin : function(){
            return localStorage.getItem('USER_TYPE') === "PHARMACY_ADMIN";
        },

        isPatient: function () {
            return localStorage.getItem("USER_TYPE") == "PATIENT";
        },

        rateClicked() {
            //getting patients rating if it already exists
            client({
                url: "med/getRating",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    medicationId: this.medicationId,
                },
                method: "GET",
            }).then((response) => {
                this.patientsRating = response.data;
                    client({
                        url: "med/checkCanRate",
                        params: {
                            patientUsername: localStorage.getItem("USERNAME"),
                            medicationId: this.medicationId,
                        },
                        method: "GET",
                    }).then((response) => {
                        this.canRate = response.data;
                        if (this.canRate) {
                            $("#rateModal").modal("show");
                        } else {
                            $("#cantRateModal").modal("show");
                        }
                    });
                
            });

        },

        saveRating() {
            client({
                url: "med/rateMedication",
                params: {
                    patientUsername: localStorage.getItem("USERNAME"),
                    medicationId: this.medicationId,
                    ratedValue: this.patientsRating,
                },
                method: "GET",
            }).then((response) => {
                //refreshing
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
            });
        },
    },

    mounted(){
        if(this.medicationId != undefined && this.medication.id != this.medicationId)
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
            else{
                this.$router.push({ name: "Home" });
            }
    }
};
</script>


<style scoped>

#detailsDiv{
    margin:50px;
}

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

h1{
    margin-top: 30px;
}

</style>