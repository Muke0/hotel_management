import Vue from 'vue'
import VueRouter from 'vue-router'
import main from '@/views/main'
import Login from "@/views/Login";
import forget from "@/views/forget";
import customer_table from "@/components/customer_table";
import room_table from "@/components/room_table";
import income_instay from "@/components/income_instay";
import checkin from "@/components/checkin";
import superUser from "@/components/superUser";
import store from "@/store";
Vue.use(VueRouter)


const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}
const routes = [{
        path: '/',
        redirect: "/login"
    },
    {
        path: '/login',
        name: '登录',
        component: Login
    },
    {
        path: '/forget',
        name: '忘记密码',
        component: forget
    }
]
const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})
const router_1 = {
    path: '/main',
    name: "main",
    component: main,
    children: [{
        path: "/manager",
        name: "manager",
        component: manager
    }]
}
const router_2 = {
    path: '/main',
    name: "main",
    component: main,
    children: [{
            path: "/customer_table",
            name: "customer_table",
            component: customer_table
        },
        {
            path: "/room_table",
            name: "room_table",
            component: room_table
        },
        {
            path: "/income_instay",
            name: "income_instay",
            component: income_instay
        },
        {
            path: "/checkin",
            name: "checkin",
            component: checkin
        }
    ]
}
router.beforeEach((to, from, next) => {
    if (to.path === '/login' | to.path === '/forget') {
        return next()
    }
    const token = window.sessionStorage.getItem('user')
    if (store.state.user_router == null) {
        if (token == "1") {
            router.addRoute(router_1)
            store.state.user_router = router
            router.push({
                path: '/main'
            })
        }
        if (token == "0") {
            router.addRoute(router_2)
            store.state.user_router = router
            router.push({
                path: '/main'
            })
        }
    }
    if (token) {
        next()
    } else {
        next("/login")
        alert("请登录")
    }
})
export default router