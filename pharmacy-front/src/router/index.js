import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'
import TestDermatologistLoginComponent from '@/components/TestDermatologistLoginComponent'
import PharmacyView from '@/views/PharmacyView'

import CalendarPage from '@/views/CalendarPage'
import DermatologistPage from '@/views/DermatologistPage'
import NewAppointmentPage from '@/views/NewAppointmentPage'
import RequestAbsencePage from '@/views/RequestAbsencePage'
import SearchPatientsPage from '@/views/SearchPatientsPage'
import DermatologistHomePage from '@/views/DermatologistHomePage'
import PharmacistHomePage from '@/views/PharmacistHomePage'
import DispenseMedicationPage from '@/views/DispenseMedicationPage'

import PatientHomePage from '@/views/PatientHomePage'

import PharmacyRegistration from '@/views/PharmacyRegistration'
import UserRegistrationPage from '@/views/UserRegistrationPage'
import PharmacyAdminRegistrationPage from '@/views/pharmacyAdminRegistrationPage'


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

        {
            path: '/pharmacyRegistration',
            name: 'PharmacyRegistration',
            component: PharmacyRegistration
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

        {
            path: '/patientHomePage',
            name: 'PatientHomePage',
            component: PatientHomePage
        },

        {
            path: '/userRegistrationPage',
            name: 'UserRegistrationPage',
            component: UserRegistrationPage    
        },

        {
            path: '/pharmacyAdminRegistrationPage',
            name: 'PharmacyAdminRegistrationPage',
            component: PharmacyAdminRegistrationPage    
        },

        {
            path: '/pharmacist-home-page',
            name: 'PharmacistHomePage',
            component: PharmacistHomePage
        },

        {
            path: '/dispense-medication-page',
            name: 'DispenseMedicationPage',
            component: DispenseMedicationPage
        },

    ]
})


