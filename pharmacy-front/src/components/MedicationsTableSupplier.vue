<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col"></th>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Reserved Quantity</th>
                    <td>
                        <button @click="openModalWindow">
                                <i class="fa fa-plus-circle fa-2x"></i>
                        </button>
                    </td>
                </tr>
            </thead>

            <tbody>
                <tr :key="suppItem.id" v-for="suppItem in supplierStorageItems">
                    <td><button 
                        :disabled="suppItem.reservedQuantity!=0"
                        @click="deleteSuppItem(suppItem.id, suppItem.medication.name)"><i class="fa fa-trash fa-lg"></i></button>
                    </td>
                    <td>{{suppItem.id}}</td>
                    <td>{{suppItem.medication.name}}</td>
                    <td>{{suppItem.medication.manufacturer}}</td> 
                    <td>{{suppItem.medication.prescriptionReq? "required":"not required"}}</td> 
                    <td>{{suppItem.medication.form}}</td>
                    <td>{{suppItem.quantity}}</td>
                    <td>{{suppItem.reservedQuantity}}</td>
                </tr>
            </tbody>
        </table>
        <!-- The Modal -->
        <ModalWindowAddMedSupplier 
            @add-medication="addMedicationToSupplier" 
            @modal-closed = "closeModalWindow" 
            :modal_show = "modal_window_show" >
        </ModalWindowAddMedSupplier>
    </div>
</template>

<script>

import Button from './Button.vue';
import ModalWindowAddMedSupplier from './ModalWindowAddMedSupplier.vue';


export default {
    name: "MedicationsTableSupplier",
    components: {Button, ModalWindowAddMedSupplier},
    props: {
        supplierStorageItems: {
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
        openModalWindow : function()
        {
            this.modal_window_show = true;
        },
        closeModalWindow()
        {
            this.modal_window_show = false;
        },
        deleteSuppItem(id, name)
        {
            if (confirm('Are you sure you want to remove "'+ name +'" from your storage?')) 
            {
                this.$emit('supp-item-deleted', id);
            } 
        },

        addMedicationToSupplier(med, quantity)
        {
            this.$emit('add-med-to-supplier', med, quantity);
        },
    },

    mounted() {
        console.log(this.supplierStorageItems[0]);
    }
};
</script>


<style scoped>

#medicationsViewDiv
{
    margin: 30px 10px 10px 10px;;
}
thead 
{ 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>