<template>
    <div id="ordersViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">Duedate</th>
                    <th scope="col">Pharmacy admin</th>
                    <th scope="col">Order content</th>
                    <th scope="col">Make offer</th>
                </tr>
            </thead>
            <tbody>
                <tr :key="order.id" v-for="order in orders">
                    <!-- <td><button @click="deleteRecord(med.id, med.name)"><i class="fa fa-trash fa-lg"></i></button></td> -->
                    <td>{{convertDate(order.dueDate)}}</td>
                    <td>{{order.pharmacyAdmin.username}}</td>
                    <td><button @click="showMore(order)"><i class="fa fa-ellipsis-h fa-2x"></i></button></td>
                    <td><button @click="showOfferModal(order)"><i class="fa fa-credit-card fa-2x" aria-hidden="true"></i></button></td>
                </tr>
            </tbody>
        </table>

        <ModalWindowOrderContent
            @modal-closed = "mw_show_order_content = false"
            :modal_show = "mw_show_order_content"
            :order = "orderToShow">
        </ModalWindowOrderContent>

        <MakeOfferModalWindow 
            @modal-closed = "mw_show_offer = false" 
            @send-offer-modal = "sendOffer"
            :modal_show = "mw_show_offer"
            :order = "orderToShow">
        </MakeOfferModalWindow>
    </div>
</template>

<script>
import {client} from "@/client/axiosClient";
import moment from "moment";
import ModalWindowOrderContent from './ModalWindowOrderContent.vue';
import MakeOfferModalWindow from './MakeOfferModalWindow.vue';

export default {
    name: "OrdersTableSupplier",
    components: {ModalWindowOrderContent, MakeOfferModalWindow},
    props: {
        orders: {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            mw_show_order_content : false,
            mw_show_offer : false,
            orderToShow: null
        };
    },
    methods: {

        convertDate : function(date)
        {
            return moment(date).format('DD. MMM YYYY.');
        },

        showMore: function(order)
        {
            this.orderToShow = order
            this.mw_show_order_content = true;
        },

        showOfferModal: function(order)
        {
            this.orderToShow = order
            this.mw_show_offer = true;
        },

        sendOffer: function(order, price, deliveryDueDate)
        {
            this.$emit("send-offer-table", order, price, deliveryDueDate);
        }

    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#ordersViewDiv{
    margin: 30px 10px 10px 10px;;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>