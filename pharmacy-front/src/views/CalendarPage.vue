<template>
  <div id="calendar">
     <img src="../assets/calendar.png">
    <router-view/>
  </div>
</template>

<script>
export default {
    name: "CalendarPage",

    data() {
        return {};
    },

    mounted() {

    },

    methods: {},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#calendar {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}


</style>

