import Start from "./../components/Start.vue";
import NotFound from "./../components/NotFound.vue";
import Register from "./../components/RegisterForm.vue";
import Login from "./../components/LoginForm.vue";

export default [
    { path: "/", component: Start },
    { path: "/notfound", component: NotFound },
    { path: "/register", component: Register },
    { path: "/login", component: Login }
];

