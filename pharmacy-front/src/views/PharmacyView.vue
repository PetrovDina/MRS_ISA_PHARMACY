<template>
    <div id="pharmacy" class="container-fluid">
        <h1>{{pharmacyName}}</h1>
        <h1>{{address}}, {{city}} {{zipCode}}</h1>
        <Button @action-performed="toggleDermos" class="btn btn-success" :text="!showDermos ? 'Show dermatologists' : 'Hide dermatologists'" color="green"></Button>
        <Button @action-performed="toggleMedication" class="btn btn-success" :text="!showMedication ? 'Show medications' : 'Hide medications'" color="green"></Button>
        <div class="container-fluid">
            <div class="row">
                <div :class="showMedication ? 'col-lg-6': 'col-lg-12'" v-show="showDermos">
                    <Dermatoligists :dermatologists = "dermatologistsToSend"></Dermatoligists>
                </div>
                <div :class="showDermos ? 'col-lg-6': 'col-lg-12'" v-show="showMedication">
                    <MedicationsView :medications = "medicationToSend"></MedicationsView>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Dermatoligists from '../components/Dermatoligists.vue';
import MedicationsView from '../components/Medications.vue';
import Button from '../components/Button';

export default {
  components: { Button, Dermatoligists, MedicationsView },
    data() {
        return {
            showDermos: false,
            showMedication: false,
            pharmacy : null,
            pharmacyName: '',
            address: '',
            city: '',
            zipCode: '',
            nesto : [],
            dermatologistsToSend: [
                {
                    id: 1,
                    firstName: "Pera",
                    lastName: "Peric",
                    rating: 3,
                },
                {
                    id: 2,
                    firstName: "Dragan",
                    lastName: "Miljic",
                    rating: 4.3,
                },
                {
                    id: 3,
                    firstName: "Simka",
                    lastName: "Simic",
                    rating: 2,
                },
            ],
            medicationToSend: [],
            // lista svih slobodnih termina treba da se doda
            rating: 0.0
        };
    },

    methods: {
        toggleDermos : function(){
            this.showDermos = !this.showDermos;
        },
        toggleMedication : function(){
            this.showMedication = !this.showMedication;
        }
    },

    mounted() {
        // Za apoteku ce ovo biti kompletan izgled koji ce na pocetku da povuce podatak iz baze o konkretnoj apoteci
        // /pharmacy/{id} i dobavice sve podatke vezano za apoteku
        // primjera radi dobavljamo apoteku sa ID-em 1
        client({
                url: "pharmacy/1",
                method: "GET",
            }).then((response) => {
                this.pharmacy = response.data
                this.pharmacyName = this.pharmacy.name;
                this.address = this.pharmacy.location.street;
                this.city = this.pharmacy.location.city;
                this.zipCode = this.pharmacy.location.zipcode;
            });
            // dobavljamo sve lijekove iz apoteke
        client({
            url: "/pharmacyStorageItem/1",
            method: "GET",
        }).then((response) => {
            this.nesto = response.data
        });
        // dobavljamo lijek iz baze
        client({
            url: "/med/2",
            method: "GET",
        }).then((response) => {
            this.medicationToSend = [...this.medicationToSend, response.data];
        })
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#pharmacy{
    align-self: flex-start;
}
</style>