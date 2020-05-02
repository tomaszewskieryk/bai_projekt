import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './components/App.vue';
import Register from './components/Register.vue';
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import NavBar from './components/NavBar.vue';
import ActualFridge from './components/ActualFridge.vue';
import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faHome, faSignInAlt, faUserPlus, faBook } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import axios from 'axios';
import VueAxios from 'vue-axios';

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(VueAxios, axios);

//font awesome icons
library.add(faHome, faSignInAlt, faUserPlus, faBook);

Vue.component('navbar', NavBar);
Vue.component('font-awesome-icon', FontAwesomeIcon);

const routes = [
    {path: '/register', component: Register},
    {path: '/login', component: Login},
    {path: '/actual', component: ActualFridge},
    {path: '/', component: Home},
    {path: '*', component: Home}
];

const router = new VueRouter({
    routes,
    mode: 'history'
});

new Vue({
    el: '#app',
    router,
    render: h => h(App)
});