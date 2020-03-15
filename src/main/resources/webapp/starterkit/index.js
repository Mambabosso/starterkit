import Vue from "vue";
import App from "./App.vue";


document.write("<div id='starterkit'></div>");

new Vue({
    el: "#starterkit",
    render: h => h(App)
});