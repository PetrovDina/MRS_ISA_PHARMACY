// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import axios from 'axios'
import VueAxios from 'vue-axios'

import vuetify from './plugins/vuetify';
import 'vuetify/dist/vuetify.min.css';

import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

import 'vue2-toast/lib/toast.css';
import toasted from 'vue-toasted'

Vue.use(toasted);




Vue.use(Vuex)

const store = new Vuex.Store({

    plugins: [createPersistedState({
        storage: window.sessionStorage, //TODO Use sessionStorage.clear(); when user logs out manually.
    })],

    state: {
        loggedUserRole: "GUEST",
        loggedUsername: "",
        lastPharmacyPatientView: -1,
        logginFirstTimeDisableNav: false
    },

    mutations: {
        changeLoggedUserRole(state, newRole) { //poziva se kao store.commit('changeLoggedUserRole', 'DERMATOLOGIST')
            state.loggedUserRole = newRole;
        },

        changeLoggedUsername(state, newUsername) { //poziva se kao store.commit('changeLoggedUserRole', 'DERMATOLOGIST')
            state.loggedUsername = newUsername;
        },

        changeLastPharmacyPatientView(state, newPharmacyId) { 
            state.lastPharmacyPatientView = newPharmacyId;
        },

        changeLogginFirstTimeDisableNav(state, newLogginFirstTimeDisableNav) { 
            state.logginFirstTimeDisableNav = newLogginFirstTimeDisableNav;
        }

    },

    getters: {
        getLoggedUserRole: state => {
            return state.loggedUserRole

        },

        getLoggedUsername: state => {
            return state.loggedUsername

        },

        getLastPharmacyPatientView: state => {
            return state.lastPharmacyPatientView

        },

        getLogginFirstTimeDisableNav: state => {
            return state.logginFirstTimeDisableNav

        },

    }

})


Vue.use(VueAxios, axios)

Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    vuetify,
    components: { App },
    template: '<App/>',
    store: store
})
