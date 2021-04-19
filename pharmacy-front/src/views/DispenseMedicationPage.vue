<template>
    <SearchReservationsView class="main-container"/>
</template>

<script>
import SearchReservationsView from "../components/SearchReservationsView.vue";

export default {
    name: "DispenseMedicationPage",
    components: {
            SearchReservationsView
        }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.main-container{
        margin: 5%;
    }
</style>
