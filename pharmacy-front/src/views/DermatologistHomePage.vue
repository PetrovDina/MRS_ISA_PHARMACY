<template>
    <div id="homeDiv">
        <v-card class="ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div >Pages</div>
                <v-spacer></v-spacer>
            </v-card-title>  
            <v-card-text>
                <v-row>
                <v-col  md="3">
                    <v-card to="/search-patients-page" >
                    <v-card-title>Search patients</v-card-title>
                    </v-card>
                </v-col>  
                <v-col md="3">
                    <v-card to="/calendar-page" >
                    <v-card-title>View work calendar</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card to="/new-appointment-page" >
                    <v-card-title>Add a new appointment</v-card-title>
                    </v-card>
                </v-col> 
                <v-col md="3">
                    <v-card to="/request-absence-page">
                    <v-card-title>Request absence</v-card-title>
                    </v-card>
                </v-col> 
                <v-col>
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

    export default {
        name: "DermatologistHome",

        components: {
        SearchPatientsPage,
        CalendarPage,
        NewAppointmentPage,
        RequestAbsencePage
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
</style>