<template>
    <div id="ordersViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Duedate</th>
                    <th scope="col">Pharmacy admin</th>
                    <th scope="col">Status</th>
                    <th scope="col">Order content</th>
                </tr>
            </thead>
            <tbody>
                <tr :key="order.id" v-for="order in orders">
                    <td><button v-if="isDeletable(order)" @click="deleteRecord(order)"><i class="fa fa-trash fa-lg"></i></button></td>
                    <td>{{convertDate(order.dueDate)}}</td>
                    <td>{{order.pharmacyAdmin.username}}</td>
                    <td>{{order.orderStatus}}</td>
                    <td><button style="margin-right: 20px;" @click="showMore(order)"><i class="fa fa-ellipsis-h fa-2x"></i></button><button @click="editOrderContent(order)"><i class="fa fa-edit fa-2x"></i></button></td>
                </tr>
            </tbody>
        </table>
        <!-- The Modal -->
        <ModalWindowEditOrderContent
            @order-updated = "updateOrder"
            @modal-closed = "mw_show_edit_order_content = false"
            :modal_show = "mw_show_edit_order_content"
            :order = "orderToSend">
        </ModalWindowEditOrderContent>
        <ModalWindowOrderContent
            @modal-closed = "mw_show_order_content = false"
            @update-quantities-accpeted-order = "updateMedicationQuantites"
            :modal_show = "mw_show_order_content"
            :order = "orderToSend">
        </ModalWindowOrderContent>
        <ModalWindowMakeNewOrder
            @order-sent = "saveOrder"
            @new-medications-added-from-order = "addMedicationsIntoPharmacyStorage"
            @modal-closed = "mw_show_new_order = false"
            :modal_show = "mw_show_new_order"
            :medicationsInPharmacy = "medicationsInPharmacy">
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
import ModalWindowEditOrderContent from './ModalWindowEditOrderContent.vue';

export default {
    name: "MedicationsTable",
    components: {Button, ModalWindowOrderContent, ModalWindowMakeNewOrder, ModalWindowEditOrderContent},
    props: {
        orders: {
            type : Array,
            default() {
                return [];
            }
        },
        pharmacyAdminId : Number,
        medicationsInPharmacy : {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            mw_show_order_content : false,
            mw_show_new_order : false,
            mw_show_edit_order_content : false,
            orderToSend: null
        };
    },
    methods: {
        convertDate : function(date){
            return moment(date).format('DD. MMM YYYY.');
        },
        showMore: function(order){
            this.orderToSend = order
            this.mw_show_order_content = true;
        },
        editOrderContent : function(order){
            if(order.orderStatus == "NEW"){
                this.orderToSend = order
                this.mw_show_edit_order_content = true;
            }
            else
                this.$toasted.show("This order cannot be changed anymore!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
        },
        makeANewOrder : function(){
            this.mw_show_new_order = true;
        },
        isDeletable : function(order){
            return order.orderStatus === "NEW";
        },
        deleteRecord : function(order){
            // brisanje record-a
            if (confirm('You want to delete the order from "'+ order.pharmacyAdmin.username + ' - ' + this.convertDate(order.dueDate) +'"?')) {
                this.$emit('order-deleted', order);
            } else {
                this.$toasted.show("Deleting canceled!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            }
        },
        saveOrder : function(order){
            this.mw_show_new_order = false;
            order['pharmacyAdmin'] = {'username': localStorage.getItem('USERNAME')}
            client({
                url: "order",
                method: "POST",
                data: order
            }).then((response) => {
                order['id'] = response.data.id;
                for(const orderItemId in order.orderItems){
                    order.orderItems[orderItemId]['id'] = response.data.orderItems[orderItemId].id;
                }
                this.$emit('order-added', order);
            })
        },
        updateOrder : function(order){
            this.mw_show_edit_order_content = false;
            client({
                url: "order",
                method: "PUT",
                data: order
            }).then((response) => this.$emit('order-updated', order))
        },
        addMedicationsIntoPharmacyStorage : function(medications){
            this.$emit("new-medications-added-from-order", medications)
        },
        updateMedicationQuantites : function(order){
            this.$emit('update-quanities-for-medications', order);
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