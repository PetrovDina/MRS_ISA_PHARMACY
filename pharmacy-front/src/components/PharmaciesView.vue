<template>
    <div id="pharmaciesViewDiv">
        <table class="table table-hover" >
            <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Rating</th>

                </tr>
            </thead>

            <tbody>
                <tr :key="ph.id" v-for="ph in pharmacies">
                    <td>{{ph.id}}</td>
                    <td>{{ph.name }}</td>
                    <td>{{ph.address}}</td> 
                    <td>{{ph.rating}}/5</td> 
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
export default {
    name: "PharmaciesView",

    props: ["pharmacies"],

    data() {
        return {};
    },

    mounted() {

    },

    methods: {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#pharmaciesViewDiv{
    margin: 30px 60px 30px 60px;
}


thead { 
    /* background-color: rgba(15, 95, 72, 0.219); */
    background-color: rgba(32, 102, 75, 0.295)
}
</style>

