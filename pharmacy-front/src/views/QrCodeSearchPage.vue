<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>Qr code reader</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <div class="mb" style="margin-bottom: 40px; margin-top: 40px;">

            <qrcode-capture style="margin-left: 100px;" class="mb" @decode="onDecode" />

        </div>

        <div v-if="decoded !=''"  style="margin-bottom: 40px;">
            <h4>Here are all medications that are in qr code</h4>
            <MedicationsTableQR 
                :medications="medications">
            </MedicationsTableQR>
        </div>

        <div v-if="decoded !=''" style="margin-bottom: 200px;">
            <PharmaciesWithPriceQR 
                :results="results"
                @buy-medications="buyMedications"
                @sort-performed="sortPerformed">
            </PharmaciesWithPriceQR>
        </div>

        <div
            class="modal fade"
            id="exampleModal3"
            tabindex="-1"
            role="dialog"
            aria-labelledby="exampleModalLabel"
            aria-hidden="true"
        >
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">Purchase ended successfully!</div>
                    <div class="modal-body">
                        <p style="text-align: justify">
                            {{message}}
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button
                            type="button"
                            class="btn btn-secondary"
                            data-dismiss="modal"
                            @click="goBack()"
                        >
                            Go back
                        </button>
                    </div>
                </div>
            </div>
        </div>
        

    </div>
    
</template>


<script>

import { client } from "@/client/axiosClient";

import { QrcodeCapture } from 'vue-qrcode-reader'
import PharmaciesWithPriceQR from '../components/PharmaciesWithPriceQR.vue';
import MedicationsTableQR from '../components/MedicationsTableQR.vue';
import $ from "jquery";

export default {
    name: "QrCodeSearchPage",

    components: {
        QrcodeCapture,
        PharmaciesWithPriceQR,
        MedicationsTableQR
    },

    data () {

        return {
            decoded: '',

            results: [],

            medications: [],

            medicationsForBack: null,

            message: ""

        }

    },

    mounted(){
        //check if user is barred
        client({
            url: "patient/" + localStorage.getItem("USERNAME"),
            method: "GET",
        }).then((response) => {
            if (response.data.penaltyPoints >= 3) {
                this.$router.push({ name: "PenaledScreen" });
                return;
            }
        });
    },

    methods: {

        sortPerformed : function(sortCriterium) 
        {
            
            if (sortCriterium === "-") 
                return;

            else if (sortCriterium === "rating") 
            {
                this.results = this.results.sort(function (
                    a,
                    b
                ) {
                    return b.pharmacy.rating - a.pharmacy.rating;
                });
            }

            else if (sortCriterium === "price") 
            {
                this.results = this.results.sort(function (
                    a,
                    b
                ) {
                    return b.price - a.price;
                });
            }

            else if (sortCriterium === "city") 
            {
                this.results = this.results.sort(function (
                    a,
                    b
                ) {
                    return a.pharmacy.location.city > b.pharmacy.location.city ? 1 : -1;
                });
            }

            else if (sortCriterium === "name") 
            {
                this.results = this.results.sort(function (
                    a,
                    b
                ) {
                    return a.pharmacy.name > b.pharmacy.name ? 1 : -1;
                });
            }
            
        },

        buyMedications : function(pharmacyId, price)
        {
            client({

                url: "ePrescription/buyMedicationsQr",
                data: this.medicationsForBack,
                params: { pharmacyId: pharmacyId, username: localStorage.getItem("USERNAME") },
                method: "POST",

            }).then((response) => 
            {
                this.message = response.data;
                
                $("#exampleModal3").modal("show");
            });
        },

        goBack() 
        {
            this.$router.push({ name: "Home" });
        },

        onDecode (decoded) 
        {
            this.decoded = decoded;
            this.getResults();
        },

        getResults: function() 
        {
            var medications_list = JSON.parse(this.decoded);
            
            var medications = 
            {
                "medications": medications_list
            }

            this.medicationsForBack = medications;

            client({
                url: "pharmacy/getQrSearch",
                data: medications,
                method: "POST",
            })
            .then((response) => 
            {
                this.results = response.data;          
            });

            client({
                url: "med/getQrSearch",
                data: medications,
                method: "POST",
            })
            .then((response) => 
            {
                this.medications = response.data;         
            });

        }
    }




}


</script>
