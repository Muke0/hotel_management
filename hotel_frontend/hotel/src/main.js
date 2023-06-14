import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import * as echarts from 'echarts'
import axios from 'axios'

axios.defaults.baseURL = "http://localhost:9000"
Vue.prototype.$http = axios
Vue.config.productionTip = false
Vue.prototype.$echarts = echarts
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')