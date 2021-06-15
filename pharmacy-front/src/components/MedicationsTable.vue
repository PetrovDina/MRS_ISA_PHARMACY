<template>
    <div id="medicationsViewDiv">
        <a id="medOutOfStock" @click="showMedicationOutOfStock">Check for medication out of stock →</a><br><br>
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col"> </th>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                    <th scope="col">Quantity</th>
                    <th scope="col" >Price</th>
                    <th scope="col" ></th>
                    <td>
                        <button @click="openModalWindow">
                                <i class="fa fa-plus-circle fa-2x"></i>
                        </button>
                    </td>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications">
                    <td><button @click="deleteRecord(med.id, med.name)"><i class="fa fa-trash fa-lg"></i></button></td>
                    <td>{{med.name }}</td>
                    <td>{{med.manufacturer}}</td> 
                    <td>{{med.prescriptionReq? "required":"not required"}}</td> 
                    <td>{{med.form}}</td>
                    <td>{{med.quantity}}</td>
                    <td>
                        <span v-show = "med.price.price_edit === false">
                            <label @click = "med.price.price_edit = true">{{med.price.priceValue}} RSD</label>
                        </span>
                        <input style="text-align:center"
                            size="2"
                            v-show = "med.price.price_edit == true"
                            v-model = "med.price.priceValue" 
                            v-on:blur= "med.price.price_edit=false;" 
                            @keyup.enter = "med.price.price_edit=false; updateMedicationPrice(med, med.price.priceValue)">
                    </td>
                    <td style="text-align:center">
                        <span v-if="!med.price.hasPromo"> Regular </span>
                        <span v-if="med.price.hasPromo"> -{{med.price.percentage}}%
                            <span class="fa-stack fa-sm">
                                <i class="fa fa-certificate fa-stack-2x"></i>
                                <i class="fa fa-tag fa-stack-1x fa-inverse"></i>
                            </span>
                        </span>
                    </td>
                </tr>
            </tbody>
        </table>
        <Button
            @action-performed = "openPromotionWindow"
            class="btn" 
            text="Make promotion" 
            bgd_color="rgba(15, 95, 72, 0.85)" 
            :style="{color : 'rgba(255,255,255, 0.9)', padding: '5px 5px', float:'left'}">
        </Button>
        <!-- The Modal -->
        <ModalWindowAddMed 
            @add-medication="addMedicationIntoPharmacyStorage" 
            @modal-closed = "closeModalWindow" 
            :modal_show = "modal_window_show"
            :medications = "medicationsForAdd">
        </ModalWindowAddMed>
        <ModalWindowMakePromotion
        @modal-closed = "modal_window_make_promotion = false"
        @promotion-sent = "addPromotion"
        :medications = "medicationsForPromotion"
        :modal_show = "modal_window_make_promotion"
        >
        </ModalWindowMakePromotion>
        <ModalWindowMedOutOfStock
        @modal-closed = "modal_window_medicationOutOfStock = false"
        :modal_show="modal_window_medicationOutOfStock"
        :medications="medicationOutOfStock"
        > 
        </ModalWindowMedOutOfStock>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";
import Button from './Button.vue';
import ModalWindowAddMed from './ModalWindowAddMed.vue';
import ModalWindowMakePromotion from "./ModalWindowMakePromotion.vue";
import ModalWindowMedOutOfStock from "./ModalWindowMedOutOfStock.vue"

export default {
    name: "MedicationsTable",
    components: {
        Button,
        ModalWindowAddMed,
        ModalWindowMakePromotion,
        ModalWindowMedOutOfStock
    },
    props: {
        medications: {
            type : Array,
            default() {
                return [];
            }
        },
        pharmacyId : Number
    },
    data() {
        return {
            modal_window_show : false,
            modal_window_make_promotion : false,
            modal_window_medicationOutOfStock : false,
            medicationsForAdd : [],
            medicationsForPromotion : [],
            medicationOutOfStock : []
        };
    },
    methods: {
        openModalWindow : function(){
            client({
                url: "/med/forPharmacyToAdd/" + this.pharmacyId,
                method: "GET",
            }).then((response) => {
                this.medicationsForAdd = response.data;
                if(response.data.length == 0)
                    this.$toasted.show("There are no medications to add into pharmacy storage!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                else
                    this.modal_window_show = true;
            });
        },
        openPromotionWindow : function(){
            client({
                url: "/pharmacyStorageItem/fromPharmacyToPromote/" + this.pharmacyId,
                method: "GET",
            }).then((response) => {
                this.medicationsForPromotion = [];
                let current_price = 0;
                for (const psiId in response.data) {
                    const pharmacyStorageItem = response.data[psiId];
                    for (const itemPriceId in pharmacyStorageItem.itemPrices) {
                        const itemPrice = pharmacyStorageItem.itemPrices[itemPriceId];
                        if(itemPrice.current === true){
                            current_price = itemPrice.price;
                            break;
                        } 
                    }
                    let medication = {
                            id : pharmacyStorageItem.id,
                            name: pharmacyStorageItem.medication.name,
                            manufacturer: pharmacyStorageItem.medication.manufacturer,
                            prescriptionReq: pharmacyStorageItem.medication.prescriptionReq,
                            form: pharmacyStorageItem.medication.form,
                            price : current_price,
                            price_edit : false,
                        };
                    this.medicationsForPromotion.push(medication);
                }
                if(response.data.length == 0)
                    this.$toasted.show("There are no medications to promote from pharmacy storage!", {
                        theme: "toasted-primary",
                        position: "top-center",
                        duration: 2000,
                    });
                else
                    this.modal_window_make_promotion = true;
            });
        },
        closeModalWindow(){
            this.modal_window_show = false;
        },
        deleteRecord(id, name){
            // brisanje record-a
            if (confirm('Are you sure you want to delete "'+ name +'" from the pharmacy?')) {
                this.$emit('record-deleted', id);
            } else {
                this.$toasted.show("Deleting canceled!", {
                    theme: "toasted-primary",
                    position: "top-center",
                    duration: 2000,
                });
            }
        },
        addMedicationIntoPharmacyStorage(med, price){
            this.$emit('add-med-into-pharmacy', med, price);
        },
        updateMedicationPrice: function(med, item){
            this.$emit('update-medication', med, item);
        },
        addPromotion : function(promotion){
            this.$emit("promotion-created", promotion);
        },
        showMedicationOutOfStock : function(){
            client({
                url: "pharmacyStorageItem/getPharmacyStorageItemsOutOfStock/" + this.pharmacyId,
                method: "GET",
            }).then((response) => {
                console.log(response.data);
                this.medicationOutOfStock = response.data;
                this.modal_window_medicationOutOfStock = true;
            });
        }
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#medicationsViewDiv{
    margin: 30px 10px 10px 10px;;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

#medOutOfStock{
    float: left;
    color: rgba(15, 95, 72, 1);
}

#medOutOfStock:hover {
    text-decoration: none;
}
</style>