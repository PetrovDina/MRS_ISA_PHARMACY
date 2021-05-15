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
import AppointmentInProgress from '@/components/AppointmentInProgress'

import PatientHomePage from '@/views/PatientHomePage'
import PatientProfile from '@/views/PatientProfile'

import PatientsReservations from '@/views/PatientsReservations'
import DermAppointReservation from '@/views/DermAppointReservation'
import PharmAppointReservation from '@/views/PharmAppointReservation'
import ScheduledAppointmentsView from '@/views/ScheduledAppointmentsView'
import DermAppointHistory from '@/views/DermAppointHistory'
import PharmAppointHistory from '@/views/PharmAppointHistory'

import PharmacyRegistration from '@/views/PharmacyRegistration'
import UserRegistrationPage from '@/views/UserRegistrationPage'
import PharmacyAdminRegistrationPage from '@/views/pharmacyAdminRegistrationPage'
import MedicationRegistrationPage from '@/views/medicationRegistrationPage'
import LoginPage from '@/views/LoginPage'
import SystemAdminRegistrationPage from '@/views/systemAdminRegistrationPage'
import SupplierRegistrationPage from '@/views/SupplierRegistrationPage'
import DermatologistRegistrationPage from '@/views/DermatologistRegistrationPage'
import SupplierMedicationReserves from '@/views/SupplierMedicationReserves'
import SupplierOrdersPage from '@/views/SupplierOrdersPage'
import SupplierOrdersOffersPage from '@/views/SupplierOrdersOffersPage'
import PharmacyComplaintPage from '@/views/PharmacyComplaintPage'
import EmployeeComplaintPage from '@/views/EmployeeComplaintPage'


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
            component: PharmacyView,
            beforeEnter: function(to, from, next){
              let user = CheckUser.getLoggedUserData();
              if(user.userType == 'PHARMACY_ADMIN' || user.userType == "SYSTEM_ADMIN"){
                next();
              }
              else{
                ({path: '/'});
              }
            }
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
                if(user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST'){
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
                if(user.userType == 'DERMATOLOGIST'){
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
                if(user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST'){
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
                if(user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST'){
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
                if(user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST'){
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
                if(user.userType == 'DERMATOLOGIST'){
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
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/patientProfile',
            name: 'PatientProfile',
            component: PatientProfile,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
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
          path: '/systemAdminRegistrationPage',
          name: 'SystemAdminRegistrationPage',
          component: SystemAdminRegistrationPage    
        },

        {
          path: '/supplierRegistrationPage',
          name: 'SupplierRegistrationPage',
          component: SupplierRegistrationPage    
        },

        {
          path: '/dermatologistRegistrationPage',
          name: 'DermatologistRegistrationPage',
          component: DermatologistRegistrationPage    
        },
        
        {
            path: '/medicationRegistrationPage',
            name: 'MedicationRegistrationPage',
            component: MedicationRegistrationPage    
        },

        {
          path: '/supplierMedicationReserves',
          name: 'SupplierMedicationReserves',
          component: SupplierMedicationReserves    
        },

        {
          path: '/supplierOrdersPage',
          name: 'SupplierOrdersPage',
          component: SupplierOrdersPage    
        },

        {
          path: '/supplierOrdersOffersPage',
          name: 'SupplierOrdersOffersPage',
          component: SupplierOrdersOffersPage    
        },

        {
          path: '/pharmacyComplaintPage',
          name: 'PharmacyComplaintPage',
          component: PharmacyComplaintPage
        },

        {
          path: '/employeeComplaintPage',
          name: 'EmployeeComplaintPage',
          component: EmployeeComplaintPage
        },

        {
            path: '/pharmacist-home-page',
            name: 'PharmacistHomePage',
            component: PharmacistHomePage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST'){
                  next();
                }
                else{
                    ({path: '/'});
                }
              }
        },

        {
            path: '/dispense-medication-page',
            name: 'DispenseMedicationPage',
            component: DispenseMedicationPage,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PHARMACIST'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/medicationReservation',
            name: 'MedicationReservationView',
            props: true,
            component: MedicationReservationView,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/patientsReservations',
            name: 'PatientsReservations',
            props: true,
            component: PatientsReservations,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/dermatologistReservation',
            name: 'DermAppointReservation',
            component: DermAppointReservation,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/pharmacistReservation',
            name: 'PharmAppointReservation',
            component: PharmAppointReservation,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/scheduledAppointments',
            name: 'ScheduledAppointmentsView',
            component: ScheduledAppointmentsView,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
            path: '/dermAppointHistory',
            name: 'DermAppointHistory',
            component: DermAppointHistory,
            beforeEnter: function(to, from, next){
                let user = CheckUser.getLoggedUserData();
                if(user.userType == 'PATIENT'){
                  next();
                }
                else{
                  ({path: '/'});
                }
              }
        },

        {
        path: '/pharmAppointHistory',
        name: 'PharmAppointHistory',
        component: PharmAppointHistory,
        beforeEnter: function(to, from, next){
            let user = CheckUser.getLoggedUserData();
            if(user.userType == 'PATIENT'){
              next();
            }
            else{
              ({path: '/'});
            }
          }
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

        {
          path: '/appointmentInProgress',
          name: 'AppointmentInProgress',
          component: AppointmentInProgress,
          beforeEnter: function(to, from, next){
            let user = CheckUser.getLoggedUserData();
            if(user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST'){
              next();
            }
            else{
              ({path: '/'});
            }
          }
        },

    ]
})


