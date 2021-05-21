<template>
  <div id="banned">
        <img
            src="@/assets/stop.png"
            height="300"
            width="300"
        />
        <h2>Access denied</h2>
        <h5 >You have been banned from using this service.</h5>
        <h5>For more information about our penalty and reward system, please visit <a @click="goToProfile()">your profile</a>.</h5>

  </div>
</template>

<script>
export default {
    name: "PaneledScreen",

    methods:{
        goToProfile(){
            this.$router
                .push({ name: "PatientProfile" });
        }
    }
}
</script>

<style scoped>

div{
    margin: 50px;
}

img{
    margin: 30px 0 30px 0;
}

a{
    color: rgb(138, 1, 1) !important;
}


</style>

