<template>
  <div class="login-form">
    <form>
      <div class="form-group">
        <label>{{$t("components.login.username")}}</label>
        <input class="form-control" v-model="username" :readonly="loading" />
      </div>
      <div class="form-group">
        <label>{{$t("components.login.password")}}</label>
        <input type="password" class="form-control" v-model="password" :readonly="loading" />
      </div>
      <div v-if="loading">
        <button class="btn btn-primary" type="submit" disabled>
          <span class="spinner-border spinner-border-sm" role="status"></span>
          {{$t("components.login.loading")}}
        </button>
      </div>
      <div v-else>
        <button
          class="btn btn-primary"
          type="submit"
          @click="login()"
        >{{$t("components.login.login")}}</button>
        <small v-if="status != null" class="status">{{status}}</small>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "LoginForm",
  data() {
    return {
      status: null,
      loading: false,
      username: "",
      password: ""
    };
  },
  methods: {
    login() {}
  }
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