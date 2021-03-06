import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'
import PharmacyView from '@/views/PharmacyView'
import PharmacyViewPatient from '@/views/PharmacyViewPatient'

import CalendarPage from '@/views/CalendarPage'
import EmployeeProfilePage from '@/views/EmployeeProfilePage'
import PastAppointmentsPage from '@/views/PastAppointmentsPage'
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
import AppointmentHistoryView from '@/views/AppointmentHistoryView'
import PenaledScreen from '@/views/PenaledScreen'
import PatientsPrescribedTherapiesView from '@/views/PatientsPrescribedTherapiesView'
import PatientsEPrescriptionsView from '@/views/PatientsEPrescriptionsView'



import PharmacyRegistration from '@/views/PharmacyRegistration'
import UserRegistrationPage from '@/views/UserRegistrationPage'
import PharmacyAdminRegistrationPage from '@/views/pharmacyAdminRegistrationPage'
import MedicationRegistrationPage from '@/views/MedicationRegistrationPage'
import LoginPage from '@/views/LoginPage'
import SystemAdminRegistrationPage from '@/views/SystemAdminRegistrationPage'
import SupplierRegistrationPage from '@/views/SupplierRegistrationPage'
import DermatologistRegistrationPage from '@/views/DermatologistRegistrationPage'
import SupplierMedicationReserves from '@/views/SupplierMedicationReserves'
import SupplierOrdersPage from '@/views/SupplierOrdersPage'
import SupplierOrdersOffersPage from '@/views/SupplierOrdersOffersPage'
import PharmacyComplaintPage from '@/views/PharmacyComplaintPage'
import EmployeeComplaintPage from '@/views/EmployeeComplaintPage'
import AdminPharmacyComplaintPage from '@/views/AdminPharmacyComplaintPage'
import AdminEmployeeComplaintPage from '@/views/AdminEmployeeComplaintPage'
import ComplaintPharmacyUserPage from '@/views/ComplaintPharmacyUserPage'
import ComplaintEmployeeUserPage from '@/views/ComplaintEmployeeUserPage'
import LoginFirstTimePage from '@/views/LoginFirstTimePage'
import ProfileAdminSupplierPage from '@/views/ProfileAdminSupplierPage'
import AdminHomePage from '@/views/AdminHomePage'
import QrCodeSearchPage from '@/views/QrCodeSearchPage'
import LoyaltyProgramDefinitionPage from '@/views/LoyaltyProgramDefinitionPage'

import MedicationReservationView from '@/views/MedicationReservationView'

import PharmacyAdminReportView from '@/views/PharmacyAdminReportView'

import MedicationDetailsView from '@/views/MedicationDetailsView'

import * as CheckUser from '../router/CheckUser.js'

Vue.use(Router)
import { client } from "@/client/axiosClient";


export default new Router({

    methods: {
        patientPenaled() {
            client({
                url: "patient/" + localStorage.getItem("USERNAME"),
                method: "GET",
            }).then((response) => {
                //console.log(response.data.penaltyPoints >= 3);
                return response.data.penaltyPoints >= 3;
            });
        }
    },
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
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACY_ADMIN') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },
        {
            path: '/pharmacyAdminReport',
            name: 'PharmacyAdminReportView',
            component: PharmacyAdminReportView,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACY_ADMIN') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/pharmacyViewPatient',
            name: 'PharmacyViewPatient',
            component: PharmacyViewPatient,
            props: true,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (true) {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/pharmacyRegistration',
            name: 'PharmacyRegistration',
            component: PharmacyRegistration,

            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/calendar-page',
            name: 'CalendarPage',
            component: CalendarPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/',
            name: 'EmployeeProfilePage',
            component: EmployeeProfilePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/past-appointments-page',
            name: 'PastAppointmentsPage',
            component: PastAppointmentsPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/request-absence-page',
            name: 'RequestAbsencePage',
            component: RequestAbsencePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/search-patients-page',
            name: 'SearchPatientsPage',
            component: SearchPatientsPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/derm-home',
            name: 'DermatologistHomePage',
            component: DermatologistHomePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/patientHomePage',
            name: 'PatientHomePage',
            component: PatientHomePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/patientProfile',
            name: 'PatientProfile',
            component: PatientProfile,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/userRegistrationPage',
            name: 'UserRegistrationPage',
            component: UserRegistrationPage,
            beforeEnter: function (to, from, next) {
                if (CheckUser.isUserLoggedIn()) {
                    ({ path: '/' }).catch(() => { });
                } else {
                    next();
                }
            }
        },

        {
            path: '/pharmacyAdminRegistrationPage',
            name: 'PharmacyAdminRegistrationPage',
            component: PharmacyAdminRegistrationPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/systemAdminRegistrationPage',
            name: 'SystemAdminRegistrationPage',
            component: SystemAdminRegistrationPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/supplierRegistrationPage',
            name: 'SupplierRegistrationPage',
            component: SupplierRegistrationPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/dermatologistRegistrationPage',
            name: 'DermatologistRegistrationPage',
            component: DermatologistRegistrationPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/medicationRegistrationPage',
            name: 'MedicationRegistrationPage',
            component: MedicationRegistrationPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/supplierMedicationReserves',
            name: 'SupplierMedicationReserves',
            component: SupplierMedicationReserves,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SUPPLIER") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/supplierOrdersPage',
            name: 'SupplierOrdersPage',
            component: SupplierOrdersPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SUPPLIER") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/supplierOrdersOffersPage',
            name: 'SupplierOrdersOffersPage',
            component: SupplierOrdersOffersPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SUPPLIER") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/pharmacyComplaintPage',
            name: 'PharmacyComplaintPage',
            component: PharmacyComplaintPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "PATIENT") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/employeeComplaintPage',
            name: 'EmployeeComplaintPage',
            component: EmployeeComplaintPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "PATIENT") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/adminPharmacyComplaintPage',
            name: 'AdminPharmacyComplaintPage',
            component: AdminPharmacyComplaintPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/adminEmployeeComplaintPage',
            name: 'AdminEmployeeComplaintPage',
            component: AdminEmployeeComplaintPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "SYSTEM_ADMIN") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/complaintPharmacyUserPage',
            name: 'ComplaintPharmacyUserPage',
            component: ComplaintPharmacyUserPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "PATIENT") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/complaintEmployeeUserPage',
            name: 'ComplaintEmployeeUserPage',
            component: ComplaintEmployeeUserPage,
            
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == "PATIENT") {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/pharmacist-home-page',
            name: 'PharmacistHomePage',
            component: PharmacistHomePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/adminHomePage',
            name: 'AdminHomePage',
            component: AdminHomePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'SYSTEM_ADMIN') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/dispense-medication-page',
            name: 'DispenseMedicationPage',
            component: DispenseMedicationPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/medicationReservation',
            name: 'MedicationReservationView',
            props: true,
            component: MedicationReservationView,
            beforeEnter: function (to, from, next) {
                let self = this;
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    //todo check if patient is penaled
                    next();




                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/patientsReservations',
            name: 'PatientsReservations',
            props: true,
            component: PatientsReservations,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/dermatologistReservation',
            name: 'DermAppointReservation',
            component: DermAppointReservation,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {

                    next();


                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/pharmacistReservation',
            name: 'PharmAppointReservation',
            component: PharmAppointReservation,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {

                    next();

                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/penaled',
            name: 'PenaledScreen',
            component: PenaledScreen,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {

                    next();

                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/patientsTherapies',
            name: 'PatientsPrescribedTherapiesView',
            component: PatientsPrescribedTherapiesView,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {

                    next();

                }
                else {
                    ({ path: '/' });
                }
            }
        },

                {
            path: '/patientsPrescriptions',
            name: 'PatientsEPrescriptionsView',
            component: PatientsEPrescriptionsView,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {

                    next();

                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/scheduledAppointments',
            name: 'ScheduledAppointmentsView',
            component: ScheduledAppointmentsView,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/appointmentHistory',
            name: 'AppointmentHistoryView',
            component: AppointmentHistoryView,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    next();
                    
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/LoginPage',
            name: 'LoginPage',
            component: LoginPage,
            beforeEnter: function (to, from, next) {
                if (CheckUser.isUserLoggedIn()) {
                    ({ path: '/' }).catch(() => { });
                } else {
                    next();
                }
            }

        },

        {
            path: '/loginFirstTimePage',
            name: 'LoginFirstTimePage',
            component: LoginFirstTimePage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();

                // Ako je neka od ovih uloga proveri dalje
                if (user.userType == 'SYSTEM_ADMIN'  || user.userType == 'PHARMACY_ADMIN' ||
                    user.userType == 'DERMATOLOGIST' || user.userType == 'PHARMACIST'     ||
                    user.userType == 'SUPPLIER') {
                    
                    // Provera jel se logovao prvi put

                    client({
                        url: '/auth/loggedFirstTime/' + localStorage.getItem("USERNAME"),
                        method: "GET",
                    })
                    .then((response) => {
                        let loggedFirstTime = response.data;

                        if(!loggedFirstTime)    // Ako se nije logovao
                        {
                            next();
                        }
                        else
                        {
                            ({ path: '/' });
                        }
                    })
                }

                // Ako je neka druga uloga vrati ga na pocetnu
                else {
                    ({ path: '/' });
                }
            }

        },

        {
            path: '/profileAdminSupplierPage',
            name: 'ProfileAdminSupplierPage',
            component: ProfileAdminSupplierPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'SYSTEM_ADMIN' || user.userType == 'PHARMACY_ADMIN' || user.userType == 'SUPPLIER') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },


        {
            path: '/appointmentInProgress',
            name: 'AppointmentInProgress',
            component: AppointmentInProgress,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PHARMACIST' || user.userType == 'DERMATOLOGIST') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/qrCodeSearchPage',
            name: 'QrCodeSearchPage',
            component: QrCodeSearchPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'PATIENT') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

        {
            path: '/medicationDetailsView',
            name: 'MedicationDetailsView',
            component: MedicationDetailsView,
            props: true

        },

        {
            path: '/loyaltyProgramDefinitionPage',
            name: 'LoyaltyProgramDefinitionPage',
            component: LoyaltyProgramDefinitionPage,
            beforeEnter: function (to, from, next) {
                let user = CheckUser.getLoggedUserData();
                if (user.userType == 'SYSTEM_ADMIN') {
                    next();
                }
                else {
                    ({ path: '/' });
                }
            }
        },

    ]
})


