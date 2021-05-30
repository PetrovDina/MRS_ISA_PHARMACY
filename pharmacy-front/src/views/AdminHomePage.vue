<template>
    <div id="homeDiv">
        <v-card class="cardCollection staticPosition ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div class="cardCollectionTitle">USERS</div>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/systemAdminRegistrationPage">
                            <v-card-title class="wordBreak"
                                >Register new system admin</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/pharmacyAdminRegistrationPage">
                            <v-card-title class="wordBreak"
                                >Register new pharmacy admin</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/dermatologistRegistrationPage">
                            <v-card-title class="wordBreak"
                                >Register new dermatologist</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/supplierRegistrationPage">
                            <v-card-title class="wordBreak"
                                >Register new supplier</v-card-title
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
                    COMPLAINTS
                </div>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/adminPharmacyComplaintPage">
                            <v-card-title class="wordBreak"
                                >Pharmacy complaints</v-card-title
                            >
                        </v-card>
                    </v-col>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/adminEmployeeComplaintPage">
                            <v-card-title class="wordBreak"
                                >Employee complaints</v-card-title
                            >
                        </v-card>
                    </v-col>
                </v-row>
            </v-card-text>
        </v-card>
        <v-card class="cardCollection staticPosition ma-5">
            <v-card-title>
                <v-spacer></v-spacer>
                <div class="cardCollectionTitle">MEDICATION AND PHARMACY</div>
                <v-spacer></v-spacer>
            </v-card-title>
            <v-card-text>
                <v-row>
                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/medicationRegistrationPage">
                            <v-card-title class="wordBreak"
                                >Register new medication into system</v-card-title
                            >
                        </v-card>
                    </v-col>


                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/pharmacyRegistration">
                            <v-card-title class="wordBreak"
                                >Register new pharmacy</v-card-title
                            >
                        </v-card>
                    </v-col>

                    <v-col class="staticPosition" md="3">
                        <v-card class="oneCard" to="/loyaltyProgramDefinitionPage">
                            <v-card-title class="wordBreak"
                                >Define loyalty program</v-card-title
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
    name: "AdminHomePage",

    components: {},

    data: function () {
        return {};
    },
    methods: {

    },

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