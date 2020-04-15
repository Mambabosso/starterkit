import Vue from "vue";
import VueI18n from "vue-i18n";

import Messages from "./translations/index";

Vue.use(VueI18n);

export default new VueI18n({
    fallbackLocale: "en_US",
    locale: "en_US",
    messages: Messages,
});
