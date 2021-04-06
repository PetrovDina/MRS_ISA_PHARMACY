<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Price</th>
                    <th scope="col">Reservation</th>
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
                        <div v-show = "med.price_edit === false">
                            <label @click = "med.price_edit = true">{{med.price}}</label>
                        </div>
                        <input style="text-align:center"
                            size="15"
                            v-show = "med.price_edit == true"
                            v-model = "med.price" 
                            v-on:blur= "med.price_edit=false;" 
                            @keyup.enter = "med.price_edit=false; updateMedicationPrice(med, med.price)">
                    </td>
                    <td><Button 
                        class="btn-success" 
                        text="Reserve" 
                        bgd_color="green">
                    </Button></td>
                </tr>
            </tbody>
        </table>
        <!-- The Modal -->
        <ModalWindowAddMed 
            @add-medication="addMedicationInPharmacy" 
            @modal-closed = "closeModalWindow" 
            :modal_show = "modal_window_show" >
        </ModalWindowAddMed>
    </div>
</template>

<script>

import Button from './Button.vue';
import ModalWindowAddMed from './ModalWindowAddMed.vue';


export default {
    name: "MedicationsTable",
    components: {Button, ModalWindowAddMed},
    props: {
        medications: {
            type : Array,
            default() {
                return [];
            }
        },
    },
    data() {
        return {
            modal_window_show : false,
        };
    },
    methods: {
        openModalWindow : function(){
            this.modal_window_show = true;
        },
        closeModalWindow(){
            this.modal_window_show = false;
        },
        deleteRecord(id, name){
            // brisanje record-a
            if (confirm('Are you sure you want to delete "'+ name +'" from the pharmacy?')) {
                this.$emit('record-deleted', id);
            } else {
                alert('Deleting canceled.');
            }
        },
        addMedicationInPharmacy(med, price){
            this.$emit('add-med-into-pharmacy', med, price);
        },
        updateMedicationPrice: function(med, item){
            this.$emit('update-medication', med, item);
        },
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
</style>