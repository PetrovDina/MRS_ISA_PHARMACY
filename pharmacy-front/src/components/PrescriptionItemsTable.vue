<template>
    <div id="itemsDiv">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Medication</th>
                    <th scope="col">Quantity</th>
                    <th v-if="mode==='therapy'" scope="col">Therapy duration</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="item.id" v-for="item in items">
                    <td>{{ item.medication.name }}</td>
                    <td>{{ item.quantity }}</td>
                    <td v-if="mode==='therapy'">{{ item.therapyDuration }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "PrescriptionItemsTable",
    props: {
        items: {
            type: Array,
            default() {
                return [];
            },
        },

        mode:{
            type:String,
            default:"therapy"
        }
    },
};
</script>

<style scoped>
thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>