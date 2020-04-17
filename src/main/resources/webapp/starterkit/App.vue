<template>
    <div id="sk-main">
        <div>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <router-link class="navbar-brand" to="/">{{
                    $t("app.starterkit")
                }}</router-link>
                <button
                    class="navbar-toggler"
                    type="button"
                    data-toggle="collapse"
                    data-target="#skNavbarToggle"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="skNavbarToggle">
                    <div class="navbar-nav">
                        <router-link class="nav-item nav-link" to="/settings">{{
                            $t("app.settings")
                        }}</router-link>

                        <router-link
                            v-if="loggedIn"
                            class="nav-item nav-link"
                            to="/account"
                            >{{ $t("app.account") }}</router-link
                        >

                        <router-link
                            v-if="!loggedIn"
                            class="nav-item nav-link"
                            to="/register"
                            >{{ $t("app.register") }}</router-link
                        >

                        <router-link
                            v-if="!loggedIn"
                            class="nav-item nav-link"
                            to="/login"
                            >{{ $t("app.login") }}</router-link
                        >
                    </div>
                </div>
            </nav>
        </div>
        <div class="router-content">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    export default {
        name: "App",
        data() {
            return {};
        },
        computed: {
            loggedIn() {
                return localStorage.getItem("authToken") != null;
            },
        },
    };
</script>

<style scoped>
    .link-is-active {
        background: red;
    }
    .router-content {
        padding-top: 16px;
    }
</style>
