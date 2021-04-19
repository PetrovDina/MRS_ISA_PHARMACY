import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'
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
import MedicationRegistrationPage from '@/views/medicationRegistrationPage'
import LoginPage from '@/views/LoginPage'

import MedicationReservationView from '@/views/MedicationReservationView'

import * as CheckUser from '../router/CheckUser.js'

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
            component: CalendarPage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST' || this.userType == 'DERMATOLOGIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/',
            name: 'DermatologistPage',
            component: DermatologistPage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(this.userType == 'DERMATOLOGIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/new-appointment-page',
            name: 'NewAppointmentPage',
            component: NewAppointmentPage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST' || this.userType == 'DERMATOLOGIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/request-absence-page',
            name: 'RequestAbsencePage',
            component: RequestAbsencePage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST' || this.userType == 'DERMATOLOGIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/search-patients-page',
            name: 'SearchPatientsPage',
            component: SearchPatientsPage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST' || this.userType == 'DERMATOLOGIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/derm-home',
            name: 'DermatologistHomePage',
            component: DermatologistHomePage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(this.userType == 'DERMATOLOGIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/patientHomePage',
            name: 'PatientHomePage',
            component: PatientHomePage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(this.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/userRegistrationPage',
            name: 'UserRegistrationPage',
            component: UserRegistrationPage,
            beforeEnter: function(to, from, next){
                if(CheckUser.isUserLoggedIn()){
                  ({path: '/'}).catch(()=>{});
                }else{
                  next();
                }
              }    
        },

        {
            path: '/pharmacyAdminRegistrationPage',
            name: 'PharmacyAdminRegistrationPage',
            component: PharmacyAdminRegistrationPage    
        },
        
        {
            path: '/medicationRegistrationPage',
            name: 'MedicationRegistrationPage',
            component: MedicationRegistrationPage    
        },

        {
            path: '/pharmacist-home-page',
            name: 'PharmacistHomePage',
            component: PharmacistHomePage/*,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST'){
                  next();
                }
                else{
                    ({path: '/'});
                }
              }*/
        },

        {
            path: '/dispense-medication-page',
            name: 'DispenseMedicationPage',
            component: DispenseMedicationPage/*,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }*/
        },

        {
            path: '/medicationReservation',
            name: 'MedicationReservationView',
            props: true,
            component: MedicationReservationView
        },

        {
            path: '/LoginPage',
            name: 'LoginPage',
            component: LoginPage,
            beforeEnter: function(to, from, next){
                if(CheckUser.isUserLoggedIn()){
                  ({path: '/'}).catch(()=>{});
                }else{
                  next();
                }
              }

        },

    ]
})


