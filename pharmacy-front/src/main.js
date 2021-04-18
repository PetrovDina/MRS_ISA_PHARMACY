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


Vue.use(Vuex)

const store = new Vuex.Store({

    plugins: [createPersistedState({
        storage: window.sessionStorage, //TODO Use sessionStorage.clear(); when user logs out manually.
    })],

    state: {
        loggedUserRole: "GUEST"
    },

    mutations: {
        changeLoggedUserRole(state, newRole) { //poziva se kao store.commit('changeLoggedUserRole', 'DERMATOLOGIST')
            state.loggedUserRole = newRole;
        }
    },

    getters: {
        getLoggedUserRole: state => {
            return state.loggedUserRole

        }
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
