import "bootstrap/dist/css/bootstrap.min.css";

import Vue from "vue";
import App from "./App.vue";

import "./components/index";


document.write("<div id=\"starterkit\"></div>");

new Vue({
    el: "#starterkit",
    render: h => h(App)
});

