<template>
    <div id="app">
        <Navigation :typeUser="typeUser"></Navigation>
        <router-view />
    </div>

</template>

<script>
import Navigation from "./components/Navigation";

export default {
    name: "App",
    components: {
        Navigation,
    },

    data() {
        return {
            //prvi nacin cuvanja tipa ulogovanog korisnika - App data, da bi se mogao u Navigaciju poslati,
            // pitati katarinu da li je pogresan pristup
            typeUser: "GUEST",
        };
    },
    mounted() {
        //Drugi nacin cuvanja tipa ulogovanog korisnika - localstorage: Pitati Katarinu da li su ovo losi pristupi
        //kada se kreira nasa aplikacija cuvamo u lokalnom skladistu da je tip korisnika inicijalno gost
        localStorage.USER_TYPE = "GUEST";
                alert("hello");

        //kada neka child component promeni tip korisnika (npr LoginComponent) ona emituje da je doslo do promene
        //App.vue ce da sacuva te promene i u data i u localStorage
        //U data da bi se Navigacija azurirala
        //U local storage da bi sve komponente lako pristupile
        this.$root.$on("type-changed", (t) => {
            console.log("APP: user changeed");
            this.typeUser = t;
            localStorage.USER_TYPE = t;
        });
    },
};
</script>


<style>
/* Univerzalni font za celu app, trebalo bi svuda da se primenjuje samo od sebe */
@import url("https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap");

* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

#app {
    font-family: "Poppins", sans-serif;
    text-align: center;
    margin-top: 80px; /* Da bi nam inicijalno sav content bio ispod navigacije koja je fiksirana na vrhu */
    width: 100%;
}
</style>
