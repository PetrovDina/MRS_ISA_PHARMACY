<template>
    <div id="pharmaciesViewDiv">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Rating</th>
                </tr>
            </thead>

            <tbody>
                <tr :key="ph.id" v-for="ph in pharmacies">
                    <td>{{ph.name }}</td>
                    <td>{{ph.location.street}} {{ph.location.streetNum}}, {{ph.location.zipcode}} {{ph.location.city}}</td> 
                    <td>{{ph.rating}}/5</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import Button from './Button.vue';

export default {
  components: { Button },
    name: "PharmaciesView",

    props: ["pharmacies"],

    data() {
        return {
            selected : {},
        };
    },

    mounted() {

        
    },


    methods: {
        clickedId: function(id){
            this.$router
                .push({ 
                    name: "PharmacyView",
                    params: {
                        pharmacyId: id
                    }
                });
        },
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#pharmaciesViewDiv{
    margin: 10px 40px 30px 40px;
}


thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}



.selected{
    background-color:  rgba(155, 82, 151, 0.527);

}
</style>

