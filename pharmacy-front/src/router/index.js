import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'
import TestDermatologistLoginComponent from '@/components/TestDermatologistLoginComponent'
import PharmacyView from '@/views/PharmacyView'

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
            path: '/pharmacy',
            name: 'PharmacyView',
            component: PharmacyView
        },

    ]
})


