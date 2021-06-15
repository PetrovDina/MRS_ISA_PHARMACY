<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications" @click="clickedOnRow(med)" 
                @dblclick="dblClickedOnRow(med)" v-bind:class="{selected : selectedMedication.id===med.id}">
                    <td>{{ med.name }}</td>
                    <td>{{ med.manufacturer }}</td>
                    <td>
                        {{ med.prescriptionReq ? "required" : "not required" }}
                    </td>
                    <td>{{ med.form }}</td>
                </tr>
            </tbody>
        </table>
        <ModalWindowMedicationDetail 
            @modal-closed = "closeModalWindow" 
            :modal_show = "modalWindowShowed"
            :medicationId = "selectedMedicationToPass.id">
        </ModalWindowMedicationDetail>
    </div>
</template>

<script>

import ModalWindowMedicationDetail from "../components/ModalWindowMedicationDetail";


export default {
    name: "AddNewMedicationTable",

    components: {ModalWindowMedicationDetail},

    props: ["medications"],

    data() {
        return {
            selectedMedication : {},
            selectedMedicationToPass: {},
            modalWindowShowed: false,
        };
    },

    mounted() {},

    methods: {
        clickedOnRow : function(med)
        {
            this.selectedMedication = med; 
            this.$emit('clicked-on-row', med);
        },
        dblClickedOnRow: function(med)
        {
            this.selectedMedicationToPass = med;
            this.modalWindowShowed = true;
        },
        closeModalWindow: function()
        {
            this.modalWindowShowed = false;
        }
    },

};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#medicationsViewDiv {
    margin: 30px 60px 30px 60px;
}

thead {
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295);
}

.reserveButton{
    background-color: rgba(155, 82, 151, 0.527); 
    padding: 5px;
    border-radius: 5px;

}

table {
    cursor: pointer;
}

tr.selected {
	 background-color: rgba(155, 82, 151, 0.527);
}

</style>

