import Vue from "vue";
import App from "./App.vue";

import "bootstrap/dist/css/bootstrap.min.css";


document.write("<div id=\"starterkit\"></div>");

new Vue({
    el: "#starterkit",
    render: h => h(App)
});