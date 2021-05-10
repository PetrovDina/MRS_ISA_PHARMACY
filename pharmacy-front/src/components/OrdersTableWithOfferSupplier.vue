<template>
    <div id="ordersViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">Duedate</th>
                    <th scope="col">Pharmacy admin</th>
                    <th scope="col">Delivery due date</th>
                    <th scope="col">Price</th>
                    <th scope="col">Order Status</th>
                    <th scope="col">Offer Status</th>
                    <th scope="col">Order content</th>
                    <th scope="col">Change offer</th>
                </tr>
            </thead>
            <tbody>
                <tr :key="offer.id" v-for="offer in offers">
                    <!-- <td><button @click="deleteRecord(med.id, med.name)"><i class="fa fa-trash fa-lg"></i></button></td> -->
                    <td>{{convertDate(offer.order.dueDate)}}</td>
                    <td>{{offer.order.pharmacyAdmin.username}}</td>
                    <td>{{convertDate(offer.deliveryDueDate)}}</td>
                    <td>{{offer.price}}</td>
                    <td>{{offer.order.orderStatus}}</td>
                    <td>{{offer.status}}</td>
                    <td><button @click="showMore(offer.order)"><i class="fa fa-ellipsis-h fa-2x"></i></button></td>
                    <td><button 
                        :disabled="isNotChangeable(offer)" 
                        @click="showOfferModal(offer.order, offer)">
                        <i class="fa fa-credit-card fa-2x" aria-hidden="true"></i>
                    </button></td>
                </tr>
            </tbody>
        </table>

        <ModalWindowOrderContent
            @modal-closed = "mw_show_order_content = false"
            :modal_show = "mw_show_order_content"
            :order = "orderToShow">
        </ModalWindowOrderContent>

        <ChangeOfferModalWindow 
            @modal-closed = "mw_show_offer = false" 
            @change-offer-modal = "changeOffer"
            :modal_show = "mw_show_offer"
            :order = "orderToShow"
            :offer= "offerToShow"
            :title="titleForModal">
        </ChangeOfferModalWindow>
    </div>
</template>

<script>
import {client} from "@/client/axiosClient";
import moment from "moment";
import ModalWindowOrderContent from './ModalWindowOrderContent.vue';
import ChangeOfferModalWindow from './ChangeOfferModalWindow.vue';

export default {
    name: "OrdersTableSupplier",
    components: {ModalWindowOrderContent, ChangeOfferModalWindow},
    props: {
        offers: {
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
            orderToShow: null,
            offerToShow: null,
            titleForModal: "Change offer"
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

        showOfferModal: function(order, offer)
        {
            this.orderToShow = order
            this.offerToShow = offer
            this.mw_show_offer = true;
        },

        changeOffer: function(order, price, deliveryDueDate, offerId)
        {
            this.$emit("update-offer-table", order, price, deliveryDueDate, offerId);
        },

        isNotChangeable: function(offer)
        {
            return offer.status != 'PENDING';
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