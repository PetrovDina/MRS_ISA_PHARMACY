<template>
    <div id="login">
		    
        <ModalWindowLoginFirstTime :modal_show="true" :username="username">

        </ModalWindowLoginFirstTime>

	</div>
</template>

<script>

import ModalWindowLoginFirstTime from '../components/ModalWindowLoginFirstTime.vue';

 window.onbeforeunload = function() { alert( "Your data will be lost!"); };

export default {

    name: "LoginFirstTimePage",

    components: { ModalWindowLoginFirstTime },

    methods: {
       

    },

    data() {
        return {
            username: ""
        }
    },

    mounted() {

        // Zbog navigacije
        this.$store.commit("changeLogginFirstTimeDisableNav", true);
        this.username = localStorage.getItem('USERNAME');
    }

}

</script>

<style scoped>
input:focus {
        border-color: rgba(155, 82, 151, 0.527);
        box-shadow: 0 0 0 0.1rem rgba(155, 82, 151, 0.527);;
}

</style>