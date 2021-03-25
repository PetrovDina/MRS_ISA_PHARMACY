<template>
    <div id="app">
        <Navigation />
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

    mounted() {
        //Pitati Katarinu da li je ovo los pristup cuvanja informacija o korisniku, vrv jeste, onda cemo izmeniti!
        //kada se kreira nasa aplikacija cuvamo u lokalnom skladistu da je tip korisnika inicijalno gost
        //ko bude radio login i logout i register ce da izmeni ovu vrednost!
        //npr ako se uloguje dermatolog neka uradi localStorage.USER_TYPE = "DERMATOLOGIST"
        //Ovo je korisno da bismo modelovali razlicite funkcionalnosti koje su specificne za nekog tipa korisnika
        localStorage.USER_TYPE = "GUEST";
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
    margin-top: 70px; /* Da bi nam inicijalno sav content bio ispod navigacije koja je fiksirana na vrhu */
    width: 100%;
}
</style>
