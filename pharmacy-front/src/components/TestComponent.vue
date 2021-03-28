<template>
    <div class="hello">
        
        <h1>{{ msg }}</h1>
        <ul id="test-test">
            <li :key="med.id" v-for="med in medication">
                {{ med.id }} - {{ med.name }} - {{ med.manufacturer }} -
                {{ med.prescriptionReq }} -- {{ med.form }}
            </li>
        </ul>
        <button v-on:click="test()">Test</button>
    </div>
</template>

<script>
import { client } from "@/client/axiosClient";

export default {
    name: "TestComponent",
    data() {
        return {
            msg: "Here we can test our backend data",
            medication: [],
        };
    },

    mounted() {},

    methods: {
        test: function () {
            //ovde ce biti neki poziv ka bekendu da vidimo da li uspevamo da dobavimo podatke o nekom leku
            client({
                url: "med/all",
                method: "GET",
            }).then((response) => (this.medication = response.data));
        },
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
ul {
    list-style-type: none;
    padding: 0;
}
li {
    margin: 0 10px;
}
</style>

