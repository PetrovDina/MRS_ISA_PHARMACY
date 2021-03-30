import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'
import TestDermatologistLoginComponent from '@/components/TestDermatologistLoginComponent'

import PharmacyRegistrationComponent from '@/components/PharmacyRegistrationComponent'
import UserRegistrationComponent from '@/components/UserRegistrationComponent'

import CalendarPage from '@/views/CalendarPage'
import DermatologistPage from '@/views/DermatologistPage'
import NewAppointmentPage from '@/views/NewAppointmentPage'
import RequestAbsencePage from '@/views/RequestAbsencePage'
import SearchPatientsPage from '@/views/SearchPatientsPage'
import DermatologistHomePage from '@/views/DermatologistHomePage'

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

        {
            path: '/userRegistration',
            name: 'UserRegistrationComponent',
            component: UserRegistrationComponent
        },

        {
            path: '/calendar-page',
            name: 'CalendarPage',
            component: CalendarPage
        },

        {
            path: '/',
            name: 'DermatologistPage',
            component: DermatologistPage
        },

        {
            path: '/new-appointment-page',
            name: 'NewAppointmentPage',
            component: NewAppointmentPage
        },

        {
            path: '/request-absence-page',
            name: 'RequestAbsencePage',
            component: RequestAbsencePage
        },

        {
            path: '/search-patients-page',
            name: 'SearchPatientsPage',
            component: SearchPatientsPage
        },

        {
            path: '/derm-home',
            name: 'DermatologistHomePage',
            component: DermatologistHomePage
        },
    ]
})


