<template>
    <div id="ordersViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">Duedate</th>
                    <th scope="col">Pharmacy admin</th>
                    <th scope="col">Order content</th>
                </tr>
            </thead>
            <tbody>
                <tr :key="order.id" v-for="order in orders">
                    <!-- <td><button @click="deleteRecord(med.id, med.name)"><i class="fa fa-trash fa-lg"></i></button></td> -->
                    <td>{{convertDate(order.dueDate)}}</td>
                    <td>{{order.pharmacyAdmin.username}}</td>
                    <td><button @click="showMore(order)"><i class="fa fa-ellipsis-h fa-2x"></i></button></td>
                </tr>
            </tbody>
        </table>
        <!-- The Modal -->
        <ModalWindowOrderContent
            @modal-closed = "mw_show_order_content = false"
            :modal_show = "mw_show_order_content"
            :order = "orderToSend">
        </ModalWindowOrderContent>
        <ModalWindowMakeNewOrder
            @order-sent = "saveOrder"
            @modal-closed = "mw_show_new_order = false"
            :modal_show = "mw_show_new_order">
        </ModalWindowMakeNewOrder>
        <Button
            @action-performed="makeANewOrder()"
            class="btn" 
            text="Make new order" 
            bgd_color="rgba(15, 95, 72, 0.85)" 
            :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'left'}">
        </Button>
    </div>
</template>

<script>
import {client} from "@/client/axiosClient";
import moment from "moment";
import Button from './Button.vue';
import ModalWindowOrderContent from './ModalWindowOrderContent.vue';
import ModalWindowMakeNewOrder from './ModalWindowMakeNewOrder.vue';

export default {
    name: "MedicationsTable",
    components: {Button, ModalWindowOrderContent, ModalWindowMakeNewOrder},
    props: {
        orders: {
            type : Array,
            default() {
                return [];
            }
        },
        pharmacyAdminId : Number,
    },
    data() {
        return {
            mw_show_order_content : false,
            mw_show_new_order : false,
            orderToSend: null
        };
    },
    methods: {
        closeModalWindow(){
            this.mw_show_order_content = false;
        },
        convertDate : function(date){
            return moment(date).format('DD. MMM YYYY.');
        },
        showMore: function(order){
            this.orderToSend = order
            this.mw_show_order_content = true;
        },
        makeANewOrder : function(){
            this.mw_show_new_order = true;
        },
        saveOrder : function(order){
            this.mw_show_new_order = false;
            order['pharmacyAdmin'] = {'username': localStorage.getItem('USERNAME')}
            client({
                url: "order",
                method: "POST",
                data: order
            }).then((response) => this.$emit('order-added', order))
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