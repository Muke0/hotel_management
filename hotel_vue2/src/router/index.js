// router/index.js
import Vue from 'vue'
import VueRouter from 'vue-router'
import { createRouter, createWebHashHistory } from "vue-router";
import home from '@/views/home.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: home, },
    { path: "/login", component: () =>
            import ("../views/login.vue") },
    // {
    //     path: '/manager',
    //     name: 'manager',
    //     component: () =>
    //         import ('@/views/manager.vue'),
    // },
]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    console.log(to, from);
    // if (to.path === "/login" || to.path === "/register") {
    //     next();
    if (to.path === "/login" || to.path === "/register") {
        next();
    } else {
        const token = localStorage.getItem("token");
        if (token) {
            next();
        } else {
            next("/login");
        }
    }
});
export default new VueRouter({
    routes,
})