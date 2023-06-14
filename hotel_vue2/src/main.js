import Vue from 'vue'
import App from './App.vue'
import api from '@/utils/request'
import router from '@/router'

Vue.prototype.$api = api
Vue.config.productionTip = false

new Vue({
    router,
    render: h => h(App),
}).$mount('#app')