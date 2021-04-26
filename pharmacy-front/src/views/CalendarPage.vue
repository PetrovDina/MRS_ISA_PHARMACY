<template>
  <WorkCalendar class="fill-height" id="calendar"/>
  <!--<div id="calendar">
     <img src="../assets/calendar.png">
    <router-view/>
  </div>-->
</template>

<script>
import WorkCalendar from "../components/WorkCalendar.vue";
export default {
    name: "CalendarPage",
    components: {
            WorkCalendar
        }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

#calendar {
    margin-top: 30px;
}


</style>

