import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/views/Home'
import TestComponent from '@/components/TestComponent'

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

    ]
})


