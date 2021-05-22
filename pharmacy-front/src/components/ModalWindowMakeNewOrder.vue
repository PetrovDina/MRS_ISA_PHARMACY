<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <label>Medication available for ordering - Click on it to order.</label>
            <AddNewMedicationTable 
                @clicked-on-row="addOrderItem" 
                :medications="medications">
            </AddNewMedicationTable>
            <div id="newOrderItems">
                <div class="md-form mx-5 my-5">
                    <label for="date">Pick up date</label>
                    <input type="date" id="date" name="date" :min="today"
                        class="form-control"/>
                </div>
                <label>Medication in order - Double click on quantity to edit it.</label>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Form</th>
                            <th scope="col">Ordered quantity</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr :key="orderItem.id" v-for="orderItem in orderItems">
                            <td><button @click="deleteOrderItem(orderItem.medId)"><i class="fa fa-trash fa-lg"></i></button></td>
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
                                    @keyup.enter = "orderItem.quantity_edit = false; updateOrderItemQuantity(orderItem.medId, quantity)">
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <Button 
                    @action-performed="sendOrder()" 
                    text="Send order" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center;">
                </Button>
            </div>
            <ModalWindowAddMedFromOrder
                @modal-closed = "modal_show_add_price = false"
                @med-added = "addMedicationForPharmacyStorage"
                :modal_show = "modal_show_add_price"
                :medication = "selected_medication"
            >
            </ModalWindowAddMedFromOrder>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import { client } from "@/client/axiosClient";
import AddNewMedicationTable from './AddNewMedicationTable';
import ModalWindowAddMedFromOrder from './ModalWindowAddMedFromOrder.vue';
import moment from 'moment';

export default {
    name : "ModalWindowMakeNewOrder",
    components : {Button, AddNewMedicationTable, ModalWindowAddMedFromOrder},
    props : {
        modal_show : Boolean,
        medicationsInPharmacy: {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            medications : [],
            orderItems : [],
            newMedicationsInPharmacy : [],
            modal_show_add_price : false,
            today: moment().format("YYYY-MM-DD"),
            selected_medication : {}
        }
    },
    mounted() {
        client({
                url: "med/all",
                method: "GET",
        }).then((response) => (this.medications = response.data));
    },
    methods : {
        closeWindow : function(){
            this.newMedicationsInPharmacy = [];
            this.orderItems = [];
            this.$emit('modal-closed');
        },
        deleteOrderItem : function(orderItemIdArrved){
            // brisemo iz orderItem-a
            for(const orderItemId in this.orderItems){
                if(this.orderItems[orderItemId].medId === orderItemIdArrved){
                    this.orderItems.splice(orderItemId, 1);
                    break;
                }
            }
            // brisemo iz liste lijekova koji ce se dodati nakon sto se posalje porudzbina
            for(const medicationId in this.newMedicationsInPharmacy){
                if(this.newMedicationsInPharmacy[medicationId].medId === orderItemIdArrved.medicationId){
                    this.newMedicationsInPharmacy.splice(medicationId, 1);
                    break;
                }
            }           
        },
        updateOrderItemQuantity : function(orderItemIdArrved, quantity){
            for(const orderItemId in this.orderItems){
                if(this.orderItems[orderItemId].medId === orderItemIdArrved){
                    this.orderItems[orderItemId].quantity = quantity;
                    break;
                }
            }
        },
        addOrderItem : function(selectedMedication) {
    		// ako sam vec taj dodavao
            for(const orderItemId in this.orderItems){
                if(this.orderItems[orderItemId].medId === selectedMedication.id){
                    this.$toasted.show('Medication '+ this.orderItems[orderItemId].medication.name +' is already in order!', {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return;
                }
            }
            // provjera da li je taj lijek u apoteci
            for(const medicationIter in this.medicationsInPharmacy){
                if(this.medicationsInPharmacy[medicationIter].medicationId === selectedMedication.id){
                    // ako se prvi put dodaje i u apoteci je
                    this.orderItems.push({
                        "medId": selectedMedication.id,
                        "quantity" : 0,
                        "medication" : selectedMedication,
                        "quantity_edit" : false
                    });
                    return;
                }
            }

            // posto nije u apoteci treba ga dodati  
            this.selected_medication = selectedMedication;
            this.modal_show_add_price = true; // otvaramo prozor za dodavanje cijene
    	},
        sendOrder : function(){
            if(document.getElementById('date'))
                if(document.getElementById('date').value === ''){
                    this.$toasted.show("Date must be selected!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return;
                }
            if(this.orderItems.length === 0){
                this.$toasted.show("You need to select at least one medication!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                return;
            }
            for(const orderItemId in this.orderItems){
                if(parseInt(this.orderItems[orderItemId].quantity) === 0){
                    this.$toasted.show('Medication '+ this.orderItems[orderItemId].medication.name +' has quantity 0', {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return;
                }
            }

            // treba da dodamo sve lijekove koji su poruceni a nisu u apoteci
            if(this.newMedicationsInPharmacy.length != 0)
                this.$emit("new-medications-added-from-order", this.newMedicationsInPharmacy);

            // saljemo narudzbenicu
            this.$emit('order-sent', {
                'dueDate': document.getElementById('date').value,
                'orderItems': this.orderItems,
                'orderStatus' : 'NEW',
                'offers' : []
            });

            this.closeWindow();
        },
        addMedicationForPharmacyStorage : function(medication){
            this.modal_show_add_price = false;
            this.newMedicationsInPharmacy.push(medication);
            this.orderItems.push({
                "medId": medication.id,
                "quantity" : 0,
                "medication" : medication,
                "quantity_edit" : false
            });
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

#newOrderItems {
    margin: 30px 60px 30px 60px;
}
</style>