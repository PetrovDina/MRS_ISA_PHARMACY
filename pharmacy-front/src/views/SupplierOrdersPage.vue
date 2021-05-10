<template>
    <div>
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >
                    <h2>Pharmacy orders</h2>
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
        </v-card>

        <OrdersTableSupplier 
            @send-offer-table="sendOffer"
            :orders="orders">
        </OrdersTableSupplier>

    </div>
    
</template>

<script>

import { client } from "@/client/axiosClient";
import OrdersTableSupplier from '../components/OrdersTableSupplier.vue';

export default {
    name: "SupplierOrdersPage",

    components: {OrdersTableSupplier},

    data() {
        return {
            orders: []
        }
    },

    methods: {
        sendOffer : function(order, price, deliveryDueDate) 
        {
            var offerDTO = 
            {
                "supplierUsername" : localStorage.user,
                "orderId" : order.id,
                "price" : price ,
                "deliveryDueDate": deliveryDueDate
            }
            
            client
            ({
                url : "offer/create/",
                method : "POST",
                data : offerDTO
            }).then((response) => {
                // Ako prodje uspesno onda skini sa liste ordera za koju nije dao ponudu
                alert("Offer successfuly sent to pharmacy.");
                for(let i = 0; i < this.orders.length; i++)
                {
                    if(this.orders[i].id == order.id)
                    {
                        this.orders.splice(i, 1);
                        break;
                    }
                }
            }).catch((response) => {
                alert("Not enough resources to make offer for selected order."); 
            });
        }
    },

    mounted() {

        client
        ({
            url : "order/allNotDone/" + localStorage.user,
            method : "GET",
        }).then((response) => {
            this.orders = response.data;
        })

    }
}


</script>
