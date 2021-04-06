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

                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications" @click="clickedOnRow(med)" v-bind:class="{selected : selectedMedication.id===med.id}">
                    <td>{{med.id}}</td>
                    <td>{{med.name }}</td>
                    <td>{{med.manufacturer}}</td> 
                    <td>{{med.prescriptionReq? "required":"not required"}}</td> 
                    <td>{{med.form}}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "MedicationsView",

    props: ["medications"],

    data() {
        return {
            selectedMedication : {},
        };
    },

    mounted() {

    },

    methods: {
        clickedOnRow : function(med){
            this.selectedMedication = med 
            this.$emit('clicked-on-row', med);
        }
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#medicationsViewDiv{
    margin: 30px 60px 30px 60px;
}

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}

table {
    cursor: pointer;
}

tr.selected {
	 background-color: #aaaaaa;
}

</style>

