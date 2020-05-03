import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './components/App.vue';
import Register from './components/Register.vue';
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import NavBar from './components/NavBar.vue';
import ActualFridge from './components/ActualFridge.vue';
import FridgesList from './components/FridgesList.vue';
import Fridge from './components/Fridge.vue';
import Lists from './components/Lists.vue';
import SingleList from './components/SingleList.vue';
import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faHome, faSignInAlt, faUserPlus, faBook, faArrowCircleRight, faClipboardList } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import axios from 'axios';
import VueAxios from 'vue-axios';
import vSelect from "vue-select";

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(VueAxios, axios);

//font awesome icons
library.add(faHome, faSignInAlt, faUserPlus, faBook, faArrowCircleRight, faClipboardList);

Vue.component('navbar', NavBar);
Vue.component('font-awesome-icon', FontAwesomeIcon);
Vue.component("v-select", vSelect);

const routes = [
    {path: '/register', component: Register},
    {path: '/login', component: Login},
    {path: '/actual', component: ActualFridge},
    {path: '/fridges', component: FridgesList},
    {path: '/fridge/:id', component: Fridge},
    {path: '/lists', component: Lists},
    {path: '/list/:id', component: SingleList},
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