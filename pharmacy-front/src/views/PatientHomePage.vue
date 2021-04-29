<template>
    <div id="homeDiv">
        <v-card class="cardCollection staticPosition ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div class="cardCollectionTitle">APPOINTMENTS</div>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >Book pharmacist appointment</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/dermatologistReservation">
                            <v-card-title class="wordBreak"
                                >Book dermatologist appointment</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >Dermatologist appointment history</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >Pharmacist appointment history</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >Scheduled appointments</v-card-title
                            >
                        </v-card>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-card>

        <v-card class="cardCollection staticPosition ma-5">
            <v-card-title>
                <v-spacer></v-spacer>

                <div class="cardCollectionTitle">
                    RESERVATIONS & PRESCRIPTIONS
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >My ePrescriptions</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >ePrescription history</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/patientsReservations">
                            <v-card-title class="wordBreak"
                                >Reserved medications</v-card-title
                            >
                        </v-card>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-card>
        <v-card class="cardCollection staticPosition ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div class="cardCollectionTitle">ACCOUNT</div>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >My profile</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >My subscriptions</v-card-title
                            >
                        </v-card>
                    </v-col>

                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >File a complaint</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/">
                            <v-card-title class="wordBreak"
                                >Penals</v-card-title
                            >
                        </v-card>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-card>
    </div>
</template>

<script>
export default {
    name: "PatientHomePage",

    components: {},

    data: function () {
        return {};
    },
    methods: {},

    mounted() {},
};
</script>

<style scoped>
#homeDiv {
    margin: 5vh;
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

.cardCollectionTitle {
    color: rgb(155, 82, 151);
    font-weight: 600;
    font-size: 22px;
}

.wordBreak {
    word-break: normal !important;
}
</style>