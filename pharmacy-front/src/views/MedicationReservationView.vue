<template>
    <div v-if="medication != null" id="reservationDiv">
        <p class="titl">Pharmacies that carry {{ medication.name }}</p>
        <p>Please select one</p>

        <PharmaciesWithPriceComponent
            :results="results"
            @pharmacySelected="chosenPharmacySelected"
        ></PharmaciesWithPriceComponent>
        <br>
        <div>
            <span>
                <input
                    v-model="chosenQuantity"
                    type="number"
                    class="form-control"
                    id="quantityInput"
                    placeholder="Enter quantity"
                    min = 0
                    :max="[chosenRow? chosenRow.availableQuantity : 100]"
                />
            </span>

            <span>
                <label for="dueDate">Pick up due date</label>
                <input type="date" id="dueDate" name="dueDate" :min="today"
                    class="form-control" v-model="chosenDueDate"/>
            </span>
            <br><br><br>
            <button class="cancelButton btn btn-secondary" @click="goBack()"> Cancel </button>
            <button class="finishButton btn btn-primary" @click="finishReservation()"> Complete reservation </button>

        </div>
    </div>
</template>

<script>
import PharmaciesWithPriceComponent from "../components/PharmaciesWithPriceComponent.vue";
import moment from 'moment'
import { client } from "@/client/axiosClient";


export default {
    name: "MedicationReservationView",

    components: { PharmaciesWithPriceComponent  },

    props: {
        medication: {
            type: Object,
            required: false,
            default: null
        },

    },

    data() {
        return {
            mode: "selection",
            pharmaciesWithMedication: {},
            results: [],

            chosenRow: null,
            chosenQuantity: null,
            chosenDueDate: null,

            today: moment().format("YYYY-MM-DD"),

        };
    },

    methods: {

        chosenPharmacySelected(row) {
            this.chosenQuantity = null;
            this.chosenRow = row;
        },

        finishReservation(){

            if (!this.chosenRow || !this.chosenQuantity || !this.chosenDueDate){
                alert("Please select all fields");
                //todo modalni prozor ovde umesto alert
                return;
            }

            if (this.chosenQuantity == 0 || this.chosenQuantity > this.chosenRow.availableQuantity){
                alert("Please enter valid quantity!");
                //todo modalni prozor ovde umesto alert!
                return;
            }

            
            let p = {
                id : 2, //ovo je samo bitno, ostalo nije jer ce na osnovu id-a na beku da se vadi iz baze
                username: "pera",
                password: "pera",
                email: "pera@gmail.com",
                firstName: "pera",
                lastName: "peric",
                penaltyPoints: 0
            };

            client({
                    url: "reservation/create",
                    method: "POST",
                    data: { 
                        patient: p,
                        pharmacy: this.chosenRow.pharmacy,
                        medication: this.medication,
                        quantity: this.chosenQuantity,
                        dueDate: this.chosenDueDate

                    }
            }).then((response) => (console.log(response.data)));
            //todo add modal here that says success
            this.$router.push({name:"Home"});

        },

        goBack(){
            this.$router.push({name:"Home"});

        }
    },


    mounted() {
        //check if medication passed as prop
        //we need this in case someone presses the back or forward button
        if (!this.medication){
            this.$router.push({name:"Home"});
            return;
        }

        client({
            url: "pharmacyStorageItem/allByMedicationAndQuantity",
            params: {medicationId: this.medication.id},
            method: "GET",
        }).then((response) => (this.results = response.data));
    },
};
</script>

<style scoped>
input {
    width: 25%;
    display: inline;
}

span {
    margin: 10px;
    padding: 20px;
}

.form-control:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

.finishButton {
    background-color: rgba(15, 95, 72, 0.95);;
    border-color: rgba(15, 95, 72, 0.95);
}

#reservationDiv{
    margin: 100px 60px 30px 60px;
}

p{
    font-size: 20px;
}

.titl{
    font-size: 24px;

}

</style>
