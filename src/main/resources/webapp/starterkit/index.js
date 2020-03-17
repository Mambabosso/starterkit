import "bootstrap/dist/css/bootstrap.min.css";

import Vue from "vue";
import App from "./App.vue";

import "./components/index";
import router from "./router/router";


document.write("<div id=\"starterkit\"></div>");

const v = new Vue({
    el: "#starterkit",
    router: router,
    render: h => h(App)
});

