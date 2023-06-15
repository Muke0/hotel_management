import { createRouter, createWebHashHistory } from "vue-router";

import index from "../views/index.vue";

const routes = [
    { path: "/", component: index },
    {
        path: "/login",
        component: () =>
            import ("../views/login.vue")
    },
    {
        path: "/register",
        component: () =>
            import ("../views/register.vue")
    },
    {
        path: "/upload",
        component: () =>
            import ("../views/upload.vue")
    },
    {
        path: "/review",
        component: () =>
            import ("../views/review.vue")
    },
    {
        path: "/output",
        component: () =>
            import ("../views/output.vue")
    },
    {
        path: "/generate",
        component: () =>
            import ("../views/generate.vue")
    },
    {
        path: "/staff",
        component: () =>
            import ("../views/staff.vue")
    },
    {
        path: "/room_service",
        component: () =>
            import ("../views/room_service/room_service.vue")
    },
    {
        path: "/front_desk/user",
        component: () =>
            import ("../views/front_desk/user.vue")
    },
    {
        path: "/front_desk/room",
        component: () =>
            import ("../views/front_desk/room.vue")
    },
    {
        path: "/front_desk/appointment",
        component: () =>
            import ("../views/front_desk/appointment.vue")
    },
    {
        path: "/front_desk/vip",
        component: () =>
            import ("../views/front_desk/vip.vue")
    },
    {
        path: "/client/appointment",
        component: () =>
            import ("../views/client/appointment.vue")
    },
    {
        path: "/client/room_service",
        component: () =>
            import ("../views/client/room_service.vue")
    },
    {
        path: "/client/evalueate",
        component: () =>
            import ("../views/client/evaluate.vue")
    },
    {
        path: "/client/bill",
        component: () =>
            import ("../views/client/bill.vue")
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});
// 路由守卫， 检查是否登录
router.beforeEach((to, from, next) => {
    console.log(to, from);
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

export default router;