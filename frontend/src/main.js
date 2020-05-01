import Vue from 'vue';
import VueRouter from 'vue-router';
import App from './components/App.vue';
import Register from './components/Register.vue';
import Login from './components/Login.vue';
import Home from './components/Home.vue';
import NavBar from './components/NavBar.vue';
import { BootstrapVue } from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faHome, faSignInAlt, faUserPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import VueToastr2 from 'vue-toastr-2';
import 'vue-toastr-2/dist/vue-toastr-2.min.css';

window.toastr = require('toastr');

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(VueToastr2);

//font awesome icons
library.add(faHome, faSignInAlt, faUserPlus);

Vue.component('navbar', NavBar);
Vue.component('font-awesome-icon', FontAwesomeIcon);

const routes = [
    {path: '/register', component: Register},
    {path: '/login', component: Login},
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