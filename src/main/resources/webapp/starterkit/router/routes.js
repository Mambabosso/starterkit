import Start from "./../components/Start.vue";
import NotFound from "./../components/NotFound.vue";
import Register from "./../components/RegisterForm.vue";

export default [
    { path: "/", component: Start },
    { path: "/notfound", component: NotFound },
    { path: "/register", component: Register }
];

