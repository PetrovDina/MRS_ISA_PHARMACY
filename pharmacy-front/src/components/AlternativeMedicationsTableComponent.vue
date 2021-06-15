<template>
    <div id="medicationsViewDiv">
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Manufacturer</th>
                    <th scope="col"></th>
                </tr>
            </thead>

            <tbody>
                <tr :key="med.id" v-for="med in medications">
                    <td>{{med.name }}</td>
                    <td>{{med.manufacturer}}</td> 
                    <td>
                        <button v-if="toAdd=='true'" style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Registration" 
                        @click="onClick(med.id)">Set alternative</button>
                        <button v-else style="background: rgba(15, 95, 72, 0.95)" class="btn btn-info btn-md" value="Registration" 
                        @click="onClick(med.id)">Remove</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "MedicationsView",

    props: ["medications", "toAdd"],

    data() {
        return {};
    },

    mounted() {

    },

    methods: {

        onClick : function(id){
            this.$emit('add-alternative', id);
        }
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>

