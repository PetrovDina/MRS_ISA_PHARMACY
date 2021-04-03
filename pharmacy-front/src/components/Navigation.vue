<template>
    <div class="topnav">
        <!--unauthenticated user-->
        <div v-if="isUserType('GUEST')">
            <a @click="homeRedirect()" class="homeNav">{{ typeUser }} Home</a>
            <a @click="loginRedirect()" class="loginNav">Log in</a>
            <a @click="registerRedirect()" class="registerNav">Register</a>

            <a @click="testLogin()" class="testNav">Test dermatologist login</a>

            <a @click="testPharmacyView()" class="testPharmacy">Pharmacy view</a>
            <a @click="pharmacyRegisterRedirect()" class="registerNav">Pharmacy Registration</a>
        </div>

        <!--dermatologist-->
        <div v-if="isUserType('DERMATOLOGIST')">
            <a @click="dermHomeRedirect()">DERMATOLOGIST Home</a>
            <a @click="dermProfileRedirect()">My Profile</a>
        </div>

        <!--logout nek ide poslednji uvek i ide svim ulogovanim-->
        <a
            @click="logoutRedirect()"
            v-show="!isUserType('GUEST')"
            class="logoutNav"
            >Log out</a
        >
    </div>
</template>

<script>
export default {
    name: "Navigation",

    data() {
        return {};
    },

    props: {
        typeUser: String,
    },

    methods: {
        //NAPOMENA:
        //Za svako dugme iz NAVIGACIJE mora da se radi ovaj catch pri rutiranju ispod!
        //U suprotnom, ako se napise samo standardno "this.$router.push({ name: 'ImeKomponente' })" moze doci do NavigationDuplicated greske
        //Ona se desava ako je npr. korisnik vec na /Test putanji i oce da klikne na dugme koje vodi na istu tu putanju
        //Za ostale promene ruta koje nisu u navigaciji ne morate to pisati, nego samo "this.$router.push({ name: 'ImeKomponente' })"
        //Nikako ne koristiti window.location.href ili bilo sta na tu foru jer to sluzi za klasicne Web aplikacije
        //Vue je single page i menjanjem href-a dolazi do refresha a to nije poenta single page aplikacije!

        homeRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente
            this.$router.push({ name: "Home" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        loginRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router.push({ name: "Home" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        registerRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router
                .push({ name: "UserRegistrationComponent" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        logoutRedirect: function () {
            //SIMULACIJA LOGOUTA!
            this.$root.$emit("type-changed", "GUEST");

            this.$router.push({ name: "Home" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        testLogin: function () {
            this.$root.$emit("type-changed", "DERMATOLOGIST");

            this.$router
                .push({ name: "DermatologistHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        

        dermProfileRedirect: function () {
            this.$router.push({ name: "DermatologistPage" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        dermHomeRedirect: function () {
            this.$router
                .push({ name: "DermatologistHomePage" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },

        isUserType: function (ut) {
            console.log("Navigation check");
            return this.$props.typeUser === ut;
        },

        pharmacyRegisterRedirect: function () {
            this.$router
                .push({ name: "PharmacyRegistrationComponent" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        },
        testPharmacyView: function(){
            this.$router
                .push({ name: "PharmacyView" })
                .catch((err) => {
                    // Ignore the vuex err regarding  navigating to the page they are already on.
                    if (err.name != "NavigationDuplicated") {
                        // But print any other errors to the console
                        console.error(err);
                    }
                });
        }
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.topnav {
    background-color: rgba(15, 95, 72, 0.95);
    overflow: hidden;
    width: 100%;
    position: fixed;
    top: 0;
}

.topnav a {
    float: left;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 17px;
}

.topnav a:hover {
    background-color: #ddd;
    color: rgba(10, 66, 50, 0.8);
}

.topnav a.active {
    background-color: #272327;
    color: white;
}
</style>
