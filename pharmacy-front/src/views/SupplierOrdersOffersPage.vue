<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>Orders for my offers</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <!-- filter bar -->
        <div id="filterDiv" >
            <p class="sort-label">filter by</p>
            <select style="border: 1px solid #ddd;" class="sort-dropdown" @change="filterSelected">
                <option v-for="option in options" :key="option" :value="option">{{option}}</option>
            </select>
        </div>

        <OrdersTableWithOfferSupplier 
            @update-offer-table="updateOffer"
            :offers="offersFilterResults">
        </OrdersTableWithOfferSupplier>

    </div>
    
</template>

<script>

import { client } from "@/client/axiosClient";
import OrdersTableWithOfferSupplier from '../components/OrdersTableWithOfferSupplier.vue';

export default {
    name: "SupplierOrdersOffersPage",

    components: {OrdersTableWithOfferSupplier},

    data() {
        return {
            offers: [],
            offersFilterResults: [],
            options: ['-', 'ACCEPTED', 'REJECTED', 'PENDING']
        }
    },

    methods: {

        updateOffer : function(order, price, deliveryDueDate, offerId) 
        {
            var offerDTO = 
            {
                "id" : offerId,
                "supplierUsername" : localStorage.user,
                "orderId" : order.id,
                "price" : price ,
                "deliveryDueDate": deliveryDueDate
            }
            
            client
            ({
                url : "offer/update/",
                method : "PUT",
                data : offerDTO
            }).then((response) => {
                // Ako prodje uspesno izmeni i front
                alert("Offer successfuly changed.");
                for(let i = 0; i < this.offers.length; i++)
                {
                    if(this.offers[i].order.id == order.id)
                    {
                        this.offers[i].price = offerDTO.price;
                        this.offers[i].deliveryDueDate = offerDTO.deliveryDueDate;
                        break;
                    }
                }
            }).catch((response) => {
                alert("Not enough resources to change offer for selected order."); 
            });
        },

        filterSelected: function(event)
        {
            let filterStatus = event.target.value;
            if(filterStatus === '-')
            {
                this.offersFilterResults = this.offers; return;
            }

            this.offersFilterResults = this.offers.filter(function (
                offer
            ) {
                return (
                    offer.status
                        .toLowerCase()
                        .includes(filterStatus.toLowerCase())
                );
            });

        }
    },

    mounted() {

        client
        ({
            url : "offer/allFromSupplier/" + localStorage.user,
            method : "GET",
        }).then((response) => {
            this.offers = response.data;
            this.offersFilterResults = this.offers;
        })

    }
}


</script>

<style scoped>

#filterDiv{
    margin-top: 20px;
    margin-right: 50px;
    text-align: right;
}

</style>
