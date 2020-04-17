import Start from "./../components/Start.vue";
import NotFound from "./../components/NotFound.vue";
import Account from "./../components/AccountForm.vue";
import Settings from "./../components/SettingsForm.vue";
import Register from "./../components/RegisterForm.vue";
import Login from "./../components/LoginForm.vue";

export default [
    { path: "/", component: Start },
    { path: "/notfound", component: NotFound },
    { path: "/account", component: Account },
    { path: "/settings", component: Settings },
    { path: "/register", component: Register },
    { path: "/login", component: Login },
];
