<template>
    <div id="main-container" class="main-container">
        <div class="container">
        <p class="titl">Reserved medication dispensal</p>
        <br>
        <br>
        <v-text-field
        v-model="query"
                clearable
                label="Enter reservation code"
                type="text"
                outlined
                
        ></v-text-field>
        <v-btn class="finishButton btn btn-primary" color=" green lighten-2" @click="searchQuery()" >Search</v-btn>
        <v-btn class="cancelButton btn btn-secondary" color=" grey lighten-1 "  @click="resetList()" >Reset</v-btn>
        <br>
        <br>
        <div v-if="resultFound" class="inner-card">
        <v-card
        :loading="loading"
        class="mx-auto my-12"
        max-width="374"
    >

        <v-card-title>Reservation {{reservationFixed.code}}</v-card-title>

        <v-card-text>
            <div class="my-4 subtitle-1">
            {{reservationFixed.status}}
        </div>

        <div >
            {{reservationFixed.quantity}} x {{reservationFixed.medicationName}}
        </div>
        </v-card-text>
        <v-divider class="mx-4"></v-divider>

        <v-card-subtitle
        >{{reservationFixed.patientFirstName}} {{reservationFixed.patientLastName}} • {{formatDate(reservationFixed.pickUpDate)}}</v-card-subtitle>
        
        <v-card-actions>
        <v-btn
            v-if="reservationFixed.valid === true"
            color="indigo lighten-1"
            text
            @click="confirm"
        >
            Confirm
        </v-btn>

        </v-card-actions>
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
        </div>
    </div>
</template>

<script>
import {client} from '@/client/axiosClient';
import moment from "moment";
export default {
    name: "SearchReservationsView",

    data(){
            return {
                reservation: null,
                reservationFixed: null,
                reservationItems: [],
                query: '',
                snackbarText: '',
                snackbar: false,
                vertical: false,
                loading: false,
                selection: 1,
                resultFound: false,
            }
        },

    mounted() {},

    methods: {
        formatDate(d) {
            return moment(d).format("MMMM Do yyyy");
        },

        searchQuery: function(){
            client({
                method: 'GET',
                url: 'employments/pharmacyOfLoggedInPharmacist',
                params: {username : localStorage.getItem('USERNAME')},
            })
            .then((response) => {
                if(response.data != null){
                    client({
                        method: 'GET',
                        url: 'reservation/pickup' ,
                        params: { rCode : this.query, pharmId : response.data.id},
                    })
                    .then((response) => {
                        if(response.data.status != null){
                            this.resultFound = true;  
                            var res = response.data;                          
                            if(response.data.status == "CREATED" ){
                                res.valid = true;
                            }else{                                
                                res.valid = false;
                            }
                            this.reservationFixed = res;
                        } else {
                            this.resultFound = false;
                            this.snackbarText = "No reservation found for the entered code!";
                            this.snackbar = true;
                        }
                    }).catch((response) => {
                        this.resultFound = false;
                        this.snackbarText = "No reservation found for the entered code!";
                        this.snackbar = true;
                    })
                }
            })

            
        },
        resetList: function(){
            this.resultFound = false;
            this.reservationFixed = null;
        },
        confirm () {
            client({
                method: 'GET',
                url: 'reservation/confirmPickup',
                params: { rId: this.reservationFixed.id },
            })
            .then((response) => {
                if(response.data){
                    this.snackbarText = "Successfully confirmed!";
                    this.snackbar = true;
                    this.resetList();
                }else{
                    this.snackbarText = "Something went wrong, please try again.";
                    this.snackbar = true;
                    this.resetList();
                }
            })
            .catch((response) => {
                this.$toasted.show("Error ocured. Please try again.", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 5000,
                    });
            })
      },
    },

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
span{
        color: grey;
}
.btn{
    margin-left: 2%;
}
.finishButton {
    background-color: rgba(15, 95, 72, 0.95);;
    border-color: rgba(15, 95, 72, 0.95);
}

p{
    font-size: 20px;
}

.titl{
    font-size: 30px;

}

</style>

