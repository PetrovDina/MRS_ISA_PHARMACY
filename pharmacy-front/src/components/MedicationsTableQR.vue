<template>
    <div id="medicationsViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col">Quantity</th>
                    <th scope="col">Details</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications">
                    <td>{{med.name }}</td>
                    <td>{{med.manufacturer}}</td> 
                    <td>{{med.quantity}}</td>
                    <td><button @click="moreDetails(med.id)"><i class="fa fa-ellipsis-h fa-2x"></i></button></td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>

export default {
    name: "MedicationsTableQR",

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
 
        };
    },
    methods: {

        moreDetails: function (id) 
        {
            this.$router.push({
                name: "MedicationDetailsView",
                params: {
                    medicationId: id,
                },
            });
        },

    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#medicationsViewDiv{
    margin: 30px 100px 30px 100px;
}
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>