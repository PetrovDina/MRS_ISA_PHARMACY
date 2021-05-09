<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div id="editOrderItems">
                <div class="md-form mx-5 my-5">
                    <label for="date">Pick up new date</label>
                    <input type="date" id="date" name="date" :min="today" :value="order.dueDate"
                        class="form-control"/>
                </div>
                <div v-if="edit_enable == true">
                    <label>Double click on quantity to edit it.</label>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Manufacturer</th>
                                <th scope="col">Form</th>
                                <th scope="col">Ordered quantity</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr :key="orderItem.id" v-for="orderItem in orderItemsEdited">
                                <td>{{orderItem.medication.name}}</td>
                                <td>{{orderItem.medication.manufacturer}}</td>
                                <td>{{orderItem.medication.form}}</td>
                                <td style="background-color:rgba(155, 82, 151, 0.527);">
                                    <div v-show = "orderItem.quantity_edit === false">
                                        <label @click = "orderItem.quantity_edit = true">{{orderItem.quantity}}</label>
                                    </div>
                                    <input style="text-align:center"
                                        size="1"
                                        v-show = "orderItem.quantity_edit == true"
                                        v-model = "orderItem.quantity" 
                                        v-on:blur= "orderItem.quantity_edit=false;" 
                                        @keyup.enter = "orderItem.quantity_edit = false; updateOrderItemQuantity(orderItem.id, quantity)">
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <Button v-if="order.orderStatus == 'NEW'"
                    @action-performed="saveEdited()"
                    text="Save edited" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center; position: absolute; right: 80px; bottom: 15px;">
                </Button>
                <Button v-if="order.orderStatus == 'NEW' && edit_enable == false"
                    @action-performed="enableEditQuantity()"
                    text="Edit order items" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center; position: absolute; left: 80px; bottom: 15px;">
                </Button><Button v-if="order.orderStatus == 'NEW' && edit_enable == true"
                    @action-performed="disableEditQuantity()"
                    text="Close editing" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center; position: absolute; left: 80px; bottom: 15px;">
                </Button>
            </div>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import moment from "moment";

export default {
    name : "ModalWindowOrderContent",
    components : {Button},
    props : {
        modal_show : Boolean,
        order : Object,
        sthChanged : false,
    },
    data() {
        return {
            edit_enable : false,
            today: moment().format("YYYY-MM-DD"),
            orderItemsEdited : [],
        }
    },
    methods : {
        closeWindow : function(){
            this.disableEditQuantity();
            this.$emit('modal-closed');
        },
        convertDate : function(date){
            return moment(date).format('DD. MMM YYYY.');
        },
        enableEditQuantity : function(){
            this.edit_enable = true;
            for(const orderItemId in this.order.orderItems){
                 this.orderItemsEdited.push({
                    "id": this.order.orderItems[orderItemId].id,
                    "quantity" : this.order.orderItems[orderItemId].quantity,
                    "medication" : this.order.orderItems[orderItemId].medication,
                    "quantity_edit" : false
                });
            }
        },
        disableEditQuantity : function(){
            this.orderItemsEdited = [];
            this.edit_enable = false;
        },
        isDueDateEdited : function(){
            return document.getElementById('date').value != this.order.dueDate;
        },
        isOrderItemsQuantityEdited : function(){
            for(const orderItemId in this.orderItemsEdited){
                if(this.orderItemsEdited[orderItemId].quantity != this.order.orderItems[orderItemId].quantity)
                   return true;
            }
            return false;
        },
        saveEdited: function(){
            let orderItemsQuantityEdited = this.isOrderItemsQuantityEdited();
            if(this.isDueDateEdited() || orderItemsQuantityEdited){
                if(orderItemsQuantityEdited)
                    this.$emit('order-updated', {
                        'id': this.order.id,
                        'dueDate': document.getElementById('date').value,
                        'orderItems': this.orderItemsEdited,
                        'orderStatus' : 'NEW'
                    });
                else
                    this.$emit('order-updated', {
                        'id': this.order.id,
                        'dueDate': document.getElementById('date').value,
                        'orderItems': this.order.orderItems,
                        'orderStatus' : 'NEW'
                    });
            }
            else{
                this.$toasted.show("Change something to save edited!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            }
            this.disableEditQuantity();
        },
        updateOrderItemQuantity : function(orderItemIdArrived, quantity){
            for(const orderItemId in this.orderItemsEdited){
                if(this.orderItemsEdited[orderItemId].id === orderItemIdArrived){
                    this.orderItemsEdited[orderItemId].quantity = quantity;
                    break;
                }
            }
        },
    }
};
</script>


<style scoped>

#close_btn{
    height: 30px;
    width: 25px;
}

body {font-family: Arial, Helvetica, sans-serif;}

/* The Modal (background) */
.modal {
  display: block; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

#dermos {
    margin: 30px 10px 10px 10px;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

#editOrderItems {
    margin: 10px 60px 30px 60px;
}

.orderTable {
    margin: 10px 60px 30px 60px;
}

/* Solid border */
hr.solid {
  border-top: 3px solid rgba(0,0,0,.075);;
}
</style>