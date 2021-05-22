<template>
    <div v-if="modal_show === true" id="myModal" class="modal">
        <div class="modal-content">
            <Button id="close_btn" @action-performed="closeWindow" class="close" text="X" color="white"></Button>
            <label>Medication available for promoting - Click on it to promotion.</label>
            <AddNewMedicationTable 
                @clicked-on-row="addPromotionItem" 
                :medications="medications">
            </AddNewMedicationTable>
            <div id="newPromotionItems">
                <div class="md-form mx-5 my-5">
                    <label for="date">Pick up due date for promotion</label>
                    <input type="date" id="date" name="date" :min="today"
                        class="form-control"/>
                </div>
                <label>Promoted medication - Double click on price to edit it.</label>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Manufacturer</th>
                            <th scope="col">Form</th>
                            <th scope="col">Promo price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr :key="promotionItem.id" v-for="promotionItem in promotionItems">
                            <td><button @click="deletePromotionItem(promotionItem.medication.id)"><i class="fa fa-trash fa-lg"></i></button></td>
                            <td>{{promotionItem.medication.name}}</td>
                            <td>{{promotionItem.medication.manufacturer}}</td>
                            <td>{{promotionItem.medication.form}}</td>
                            <td style="background-color:rgba(155, 82, 151, 0.527);">
                                <div v-show = "promotionItem.price_edit === false">
                                    <label @click = "promotionItem.price_edit = true">{{promotionItem.price}}</label>
                                </div>
                                <input style="text-align:center"
                                    size="1"
                                    v-show = "promotionItem.price_edit == true"
                                    v-model = "promotionItem.price" 
                                    v-on:blur= "promotionItem.price_edit=false;" 
                                    @keyup.enter = "promotionItem.price_edit = false; updatePromotionItemPrice(promotionItem.medication.id, promotionItem.price)">
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <Button 
                    @action-performed="makePromotion()" 
                    text="Save promotion" 
                    bgd_color="rgba(15, 95, 72, 0.95)" 
                    style="color:white; width:200px; text-align:center;">
                </Button>
            </div>
        </div>
    </div>
</template>

<script>
import Button from "./Button";
import AddNewMedicationTable from "./AddNewMedicationTable.vue";
import moment from 'moment';

export default {
    name : "ModalWindowEditPharmacyData",
    components : {Button, AddNewMedicationTable},
    props : {
        modal_show : Boolean,
        medications : {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            promotionItems : [],
            today: moment().format("YYYY-MM-DD"),
        }
    },
    mounted() {},
    methods : {
        closeWindow : function(){
            this.promotionItems = [];
            this.$emit('modal-closed');
        },
        addPromotionItem : function(selectedMedication) {
    		// ako sam vec taj dodavao
            for(const promotionItemId in this.promotionItems){
                if(this.promotionItems[promotionItemId].medication.id === selectedMedication.id){
                    this.$toasted.show('Medication '+ this.promotionItems[promotionItemId].medication.name +' is already promoted!', {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return;
                }
            }
            this.promotionItems.push({
                "id": selectedMedication.id,
                "price" : selectedMedication.price,
                "medication" : selectedMedication,
                "price_edit" : false
            });
    	},
        updatePromotionItemPrice : function(promotionItemIdArrved, price){
            for(const promotionItemId in this.promotionItems){
                if(this.promotionItems[promotionItemId].medication.id === promotionItemIdArrved){
                    this.promotionItems[promotionItemId].price = parseDouble(price);
                    break;
                }
            }
        },
        deletePromotionItem : function(promotionItemIdArrved){
            // brisemo iz orderItem-a
            for(const promotionItemId in this.promotionItems){
                if(this.promotionItems[promotionItemId].medication.id === promotionItemIdArrved){
                    this.promotionItems.splice(promotionItemId, 1);
                    break;
                }
            }          
        },
        isPriceChanged : function(promotionId){
            this.medications.forEach(medication => {
                if(medication.id === this.promotionItems[promotionId].medication.id){
                    if(medication.price >= parseInt(this.promotionItems[promotionId].price)){
                        return true;
                    }
                    else
                        return false;
                }
            });
        },
        makePromotion : function(){
            if(document.getElementById('date'))
                if(document.getElementById('date').value === ''){
                    this.$toasted.show("Date must be selected!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                    return;
                }
            if(this.promotionItems.length === 0){
                this.$toasted.show("You need to select at least one medication!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                return;
            }
            for(const promotionItemId in this.promotionItems){
                for(const medicationId in this.medications){
                    if(this.medications[medicationId].id === this.promotionItems[promotionItemId].medication.id){
                        if(this.medications[medicationId].price <= parseInt(this.promotionItems[promotionItemId].price)){
                            this.$toasted.show('Medication '+ this.promotionItems[promotionItemId].medication.name +' has '+ this.promotionItems[promotionItemId].price +' RSD price.', {
                                theme: "toasted-primary",
                                position: "top-center",
                                duration: 2000,
                            });
                            return;
                        }
                    }
                }
            }
            this.$toasted.show('Promotion successfully has been created!', {
                theme: "toasted-primary",
                position: "top-center",
                duration: 2000,
            });
            // saljemo promociju
            this.$emit('promotion-sent', {
                'dueDate': document.getElementById('date').value,
                'promotionItems': this.promotionItems
            });

            this.closeWindow();
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

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

</style>