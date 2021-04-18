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
        };
    },
    mounted() {

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
