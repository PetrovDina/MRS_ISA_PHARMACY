<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Prescription</th>
                    <th scope="col">Form</th>
                    <th scope="col"></th>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications" @dblclick="dblClickedOnRow(med.id)" >
                    <td>{{ med.id }}</td>
                    <td>{{ med.name }}</td>
                    <td>{{ med.manufacturer }}</td>
                    <td>
                        {{ med.prescriptionReq ? "required" : "not required" }}
                    </td>
                    <td>{{ med.form }}</td>
                    <td><button
                        v-if="checkRole()"
                        class="reserveButton"
                        @click="reserveMedication(med)"
                    >
                        RESERVE
                    </button></td>
                </tr>
            </tbody>
        </table>
        <ModalWindowMedicationDetail 
            @modal-closed = "closeModalWindow" 
            :modal_show = "modalWindowShowed"
            :medicationId = "selectedMedicationId">
        </ModalWindowMedicationDetail>
    </div>
</template>

<script>

import ModalWindowMedicationDetail from "../components/ModalWindowMedicationDetail";

export default {
    name: "MedicationsView",

    components: {ModalWindowMedicationDetail},

    props: ["medications"],

    data() {
        return {
            selectedMedicationId: -1,
            modalWindowShowed: false,
        };
    },

    mounted() {},

    methods: {
        reserveMedication(med) {
            this.$router.push({
                name: "MedicationReservationView",
                params: {
                    medication: med
                },
            });
        },

        dblClickedOnRow: function(id)
        {
            this.selectedMedicationId = id;
            this.modalWindowShowed = true;
        },

        closeModalWindow: function()
        {
            this.modalWindowShowed = false;
        },

        checkRole(){
            return this.$store.getters.getLoggedUserRole == 'PATIENT';
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
</style>

