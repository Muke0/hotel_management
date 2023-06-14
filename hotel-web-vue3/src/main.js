import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import router from "./router/router.js";
import { Menu, Delete } from "@element-plus/icons-vue";
import axios from "axios";

axios.defaults.baseURL = "http://127.0.0.1:9000";

const app = createApp(App);

app.use(router);
app.component(Menu.name, Menu);
app.component(Delete.name, Delete);

app.mount("#app");