<template>
    <h1>{{ msg }}</h1>
</template>

<script>
import { client } from "@/client/axiosClient";

export default {
    name: "Home",
    data() {
        return {
            msg:
                "Homepage aplikacije, ovde cu prikazati lekove kako je rekla katarina",

            medication: [],
        };
    },

    methods: {},

    mounted() {
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
