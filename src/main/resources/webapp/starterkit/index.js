import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import Vue from "vue";
import App from "./App.vue";

import VueI18n from "./i18n/index";

import Router from "./router/router";

document.write('<div id="starterkit"></div>');

const v = new Vue({
    el: "#starterkit",
    i18n: VueI18n,
    router: Router,
    render: (h) => h(App),
});

export default v;
