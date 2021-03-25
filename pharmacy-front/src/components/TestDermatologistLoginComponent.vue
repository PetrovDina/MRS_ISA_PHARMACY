<template>
    <h1>{{ msg }}</h1>
</template>

<script>
import { client } from "@/client/axiosClient";

export default {
    name: "TestDermatologistLoginComponent",
    data() {
        return {
            msg: "Simulacija logovanja nekog dermatologa, pogledaj navigaciju - nestao je login i reg a pojavio se logout!"
        };
    },

    methods: {},

    mounted() {
        //simulacija logovanja - da bih videla da li se menja navigacija u skladu sa tipom korisnika
        console.log("TEST DERMATOLOGIST LOGIN COMPONENT: modified user type")
        this.$root.$emit('type-changed', "DERMATOLOGIST");
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
