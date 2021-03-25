<template>
    <div class="topnav">
        <a @click="homeRedirect()" class="homeNav">{{ typeUser }} Home</a>

        <a
            @click="loginRedirect()"
            v-show="isUserType('GUEST')"
            class="loginNav"
            >Log in</a
        >
        <a
            @click="registerRedirect()"
            v-show="isUserType('GUEST')"
            class="registerNav"
            >Register</a
        >
        <a @click="testRedirect()" class="testNav">Test dummy data</a>
        <a @click="testLogin()" class="testNav">Test dermatologist login</a>

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
        return {
            userType: localStorage.USER_TYPE,
        };
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

            this.$router.push({ name: "Home" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        testRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router.push({ name: "TestComponent" }).catch((err) => {
                // Ignore the vuex err regarding  navigating to the page they are already on.
                if (err.name != "NavigationDuplicated") {
                    // But print any other errors to the console
                    console.error(err);
                }
            });
        },

        logoutRedirect: function () {
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

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
            //TODO: promeni putanju kasnije kada budu kreirane kommponente

            this.$router
                .push({ name: "TestDermatologistLoginComponent" })
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
    },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.topnav {
    background-color: rgba(15, 95, 72, 0.8);
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
