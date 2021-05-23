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
        >{{reservationFixed.patientFirstName}} {{reservationFixed.patientLastName}} • {{reservationFixed.dateAsString}}</v-card-subtitle>
        
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

        searchQuery: function(){
            client({
                method: 'GET',
                url: 'employments/pharmacyOfLoggedInPharmacist',
                params: {username : localStorage.getItem('USERNAME')},
            })
            .then((response) => {
                if(response.data != null){
                    var link = 'reservation/pickup/' + this.query;
                    client({
                        method: 'GET',
                        url: link,
                        params: {pharmId : response.data.id},
                    })
                    .then((response) => {
                        if(response.data.status != null){
                            console.log(response.data);
                            this.resultFound = true;
                            var reservation = response.data;
                            this.reservationFixed = reservation;
                        } else {
                            this.resultFound = false;
                            this.snackbarText = "No reservation found for the entered id!";
                            this.snackbar = true;
                        }
                    })
                }
            })

            
        },
        resetList: function(){
            this.resultFound = false;
            this.reservationFixed = null;
        },
        confirm () {
            console.log(this.reservationFixed);

            const id = this.reservationFixed.id;
            client({
                method: 'POST',
                url: 'reservation/confirm-pickup',
                data: {
                        reservationId: id
                    }
            })
            .then((response) => {
                if(response.data){
                    this.snackbarText = "Successfully confirmed!";
                    this.snackbar = true;
                    this.resetList();
                }
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

