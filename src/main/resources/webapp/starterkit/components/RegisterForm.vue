<template>
  <div class="register-form">
    <form>
      <div class="form-group">
        <label>{{$t("components.register.username")}}</label>
        <input class="form-control" v-model="username" :readonly="loading" />
      </div>
      <div class="form-group">
        <label>{{$t("components.register.email")}}</label>
        <input type="email" class="form-control" v-model="email" :readonly="loading" />
      </div>
      <div class="form-group">
        <label>{{$t("components.register.password")}}</label>
        <input type="password" class="form-control" v-model="password" :readonly="loading" />
      </div>
      <div v-if="loading">
        <button class="btn btn-primary" type="submit" disabled>
          <span class="spinner-border spinner-border-sm" role="status"></span>
          {{$t("components.register.loading")}}
        </button>
      </div>
      <div v-else>
        <button
          class="btn btn-primary"
          type="submit"
          @click="register()"
        >{{$t("components.register.register")}}</button>
        <small v-if="status != null" class="status">{{status}}</small>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  name: "RegisterForm",
  data() {
    return {
      status: null,
      loading: false,
      username: "",
      email: "",
      password: ""
    };
  },
  methods: {
    register() {}
  }
};
</script>

<style scoped>
.register-form {
  padding: 8px;
  margin: auto;
  width: 60%;
  border: 1px solid lightgray;
}
.status {
  margin-left: 8px;
}
</style>