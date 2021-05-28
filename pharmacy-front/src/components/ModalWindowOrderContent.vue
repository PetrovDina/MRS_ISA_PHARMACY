<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <div class="orderTable">
                <label> Expired date - <span style="font-size: 20px; color:rgba(32, 102, 75, 0.50);">{{convertDate(order.dueDate)}}</span></label>
                <table class="table table-hover" style="margin-bottom: 60px;">
                    <thead>
                        <tr>
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Form</th>
                            <th scope="col">Ordered quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr :key="orderItem.id" v-for="orderItem in order.orderItems">
                            <td style="width:23%;">{{orderItem.medication.name}}</td>
                            <td style="width:23%;">{{orderItem.medication.manufacturer}}</td>
                            <td style="width:23%;">{{orderItem.medication.form}}</td>
                            <td style="background-color:rgba(155, 82, 151, 0.527);"> {{orderItem.quantity}}</td>
                        </tr>
                    </tbody>
                </table>
                <div v-if="isPharmacyAdmin()">
                    <hr class="solid">
                    <label v-if="order.offers.length == 0"><span style="font-size: 20px;">This order {{order.orderStatus == "NEW" ? "has no offers" : "has offers"}}</span></label>
                    <div v-if="order.offers.length != 0">
                        <label><span style="font-size: 20px;">Order's offers</span></label>
                        <table class="table table-hover" >
                            <thead>
                                <tr>
                                    <th scope="col">Delivery due date</th>
                                    <th scope="col">Supplier</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Offer Status</th>
                                    <th v-if="isAcceptableTableHeader()" scope="col">Accept?</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr :key="offer.id" v-for="offer in order.offers">
                                    <td style="width:24%;">{{convertDate(offer.deliveryDueDate)}}</td>
                                    <td style="width:24%;">{{offer.supplierUsername}}</td>
                                    <td style="width:24%;">{{offer.price}}</td>
                                    <td style="width:23%;">{{offer.status}}</td>
                                    <td v-if="isAcceptableTableHeader(offer)" style="width:5%;"><button @click="accept(offer)"><i class="fa fa-check fa-2x"></i></button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr class="solid">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from "./Button";
import moment from "moment";

export default {
    name : "ModalWindowOrderContent",
    components : {Button},
    props : {
        modal_show : Boolean,
        order : Object,
    },
    data() {
        return {
            today: moment().format("YYYY-MM-DD")
        }
    },
    methods : {
        closeWindow : function(){
            this.$emit('modal-closed');
        },
        convertDate : function(date){
            return moment(date).format('DD. MMM YYYY.');
        },
        accept : function(offer){
            if(moment().format('DD. MMM YYYY.') < this.convertDate(this.order.dueDate)){
                this.$toasted.show("This offer cannot be accepted due date "+ this.convertDate(this.order.dueDate), {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
                return;
            }
            client({
                url: "/offer/accept",
                method: "POST",
                data : {
                    id: offer.id,
                    supplierUsername: offer.supplierUsername,
                    orderId: offer.orderId,
                    price: offer.price
                }
            });
            this.order.offers.forEach(offerToUpdateStatus => {
                if(offerToUpdateStatus.id === offer.id)
                    offerToUpdateStatus.status = 'ACCEPTED';
                else
                    offerToUpdateStatus.status = 'REJECTED';
            });
            this.$emit('update-quantities-accpeted-order', this.order);
        },
        isPharmacyAdmin : function(){
            return localStorage.getItem('USER_TYPE') === "PHARMACY_ADMIN"
        },
        isAcceptableTableHeader : function(){
            return this.order.offers[0].status === 'PENDING' && localStorage.getItem('USERNAME') === this.order.pharmacyAdmin.username;
        },
        isAcceptableTableData : function(offer){
            return offer.status === 'PENDING' && localStorage.getItem('USERNAME') === this.order.pharmacyAdmin.username;
        }
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

.orderTable {
    margin: 10px 60px 30px 60px;
}

/* Solid border */
hr.solid {
  border-top: 3px solid rgba(0,0,0,.075);;
}
</style>