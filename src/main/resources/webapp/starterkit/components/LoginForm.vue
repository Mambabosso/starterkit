<template>
    <div class="login-form">
        <form>
            <div class="form-group">
                <label>{{ $t("components.login.username") }}</label>
                <input
                    class="form-control"
                    v-model="username"
                    required
                    :readonly="loading"
                />
            </div>

            <div class="form-group">
                <label>{{ $t("components.login.password") }}</label>
                <input
                    type="password"
                    class="form-control"
                    v-model="password"
                    required
                    :readonly="loading"
                />
            </div>

            <div v-if="loading">
                <button class="btn btn-primary" type="submit" disabled>
                    <span
                        class="spinner-border spinner-border-sm"
                        role="status"
                    ></span>
                    {{ $t("components.login.loading") }}
                </button>
            </div>

            <div v-else>
                <button class="btn btn-primary" type="submit" @click="login()">
                    {{ $t("components.login.login") }}
                </button>
                <small v-if="status != null" class="status">{{ status }}</small>
            </div>
        </form>
    </div>
</template>

<script>
    import login from "./../services/login";

    export default {
        name: "LoginForm",
        data() {
            return {
                status: null,
                loading: false,
                username: "",
                password: "",
                token: "",
            };
        },
        methods: {
            login() {
                this.loading = true;
                login(this.username, this.password)
                    .then((result) => {
                        window.localStorage.setItem("authToken", result.data.value.hash);
                        this.status = "OK";
                    })
                    .catch((ex) => {
                        let error = ex.response.data.error;
                        if (error) {
                            this.status = this.$t("error." + error.code);
                        } else {
                            this.status = ex.message;
                        }
                    })
                    .finally(() => {
                        this.loading = false;
                    });
            },
        },
    };
</script>

<style scoped>
    .login-form {
        padding: 8px;
        margin: auto;
        width: 60%;
        border: 1px solid lightgray;
    }
    .status {
        margin-left: 8px;
    }
</style>
