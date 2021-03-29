import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'
import TestDermatologistLoginComponent from '@/components/TestDermatologistLoginComponent'
import PharmacyRegistrationComponent from '@/components/PharmacyRegistrationComponent'

Vue.use(Router)

export default new Router({
    routes: [

        {
            path: '/',
            name: 'Home',
            component: Home
        },

        {
            path: '/test',
            name: 'TestComponent',
            component: TestComponent
        },

        {
            path: '/testLogin',
            name: 'TestDermatologistLoginComponent',
            component: TestDermatologistLoginComponent
        },

        {
            path: '/pharmacyRegistration',
            name: 'PharmacyRegistrationComponent',
            component: PharmacyRegistrationComponent
        },


    ]
})


