<template>
    <div id="homeDiv">
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div class="cardCollectionTitle">Pages</div>
                <v-spacer></v-spacer>
            </v-card-title>  
            <v-card-text>
                <v-row>
                <v-col  md="3">
                    <v-card to="/search-patients-page" >
                    <v-card-title class="wordBreak">Search patients</v-card-title>
                    </v-card>
                </v-col>  
                <v-col md="3">
                    <v-card to="/calendar-page" >
                    <v-card-title class="wordBreak">View work calendar</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card to="/new-appointment-page" >
                    <v-card-title class="wordBreak">Add a new appointment</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card to="/request-absence-page">
                    <v-card-title class="wordBreak">Request absence</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card to="/dispense-medication-page">
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
    import NewAppointmentPage from '@/views/NewAppointmentPage.vue'
    import RequestAbsencePage from '@/views/RequestAbsencePage.vue'
    import DispenseMedicationPage from '@/views/DispenseMedicationPage.vue'    

    export default {
        name: "PharmacistHomePage",

        components: {
        SearchPatientsPage,
        CalendarPage,
        NewAppointmentPage,
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
</style>