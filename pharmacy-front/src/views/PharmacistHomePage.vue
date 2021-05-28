<template>
    <div id="homeDiv">
        <v-card class="cardCollection staticPosition ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div class="cardCollectionTitle">Home page</div>
                <v-spacer></v-spacer>
            </v-card-title>  
            <v-card-text>
                <v-row>
                <v-col  md="3">
                    <v-card class="oneCard" to="/search-patients-page" >
                    <v-card-title class="wordBreak">Search patients</v-card-title>
                    </v-card>
                </v-col>  
                <v-col md="3">
                    <v-card class="oneCard" to="/calendar-page" >
                    <v-card-title class="wordBreak">View work calendar</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card class="oneCard" to="/past-appointments-page" >
                    <v-card-title class="wordBreak">Appointment history</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card class="oneCard" to="/request-absence-page">
                    <v-card-title class="wordBreak">Request absence</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card class="oneCard" to="/dispense-medication-page">
                    <v-card-title class="wordBreak">Dispense medication</v-card-title>
                    </v-card>
                </v-col> 
                <v-col></v-col>
                </v-row>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
    import SearchPatientsPage from '@/views/SearchPatientsPage.vue';
    import CalendarPage from '@/views/CalendarPage.vue';
    import PastAppointmentsPage from '@/views/PastAppointmentsPage.vue'
    import RequestAbsencePage from '@/views/RequestAbsencePage.vue'
    import DispenseMedicationPage from '@/views/DispenseMedicationPage.vue'    

    export default {
        name: "PharmacistHomePage",

        components: {
        SearchPatientsPage,
        CalendarPage,
        PastAppointmentsPage,
        RequestAbsencePage,
        DispenseMedicationPage,
        },

        data: function(){
            return {}
        },
        methods: {},

        mounted() {
        },
    }
</script>

<style scoped>

#homeDiv{
    margin: 5vh;
}

.cardCollectionTitle {
    color: rgb(155, 82, 151);
    font-weight: 600;
    font-size: 22px;
}

.wordBreak {
    word-break: normal !important;
}

.staticPosition {
    position: static;
}

.oneCard {
    height: 15vh;
    margin: 10px;
    text-align: center;
    position: static;
    background-color: rgba(15, 95, 72, 0.123);
}

.cardCollection {
    margin-top: 10vh;
    padding: 20px;
    background-color: rgba(218, 218, 218, 0.1);
}
</style>