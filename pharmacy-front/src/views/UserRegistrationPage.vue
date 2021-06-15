<template>
    <RegistrationComponent typeToRegister="PATIENT"></RegistrationComponent>
</template>

<script>

import RegistrationComponent from "../components/RegistrationComponent.vue";

export default {

    name: 'UserRegistrationPage',

    components : {
        RegistrationComponent
    },

}
</script>