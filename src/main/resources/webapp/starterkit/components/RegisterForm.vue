<template>
  <div class="register-form">
    <form>
      <div class="form-group">
        <label>Username</label>
        <input
          class="form-control"
          v-model="username"
          placeholder="Enter username"
          :readonly="loading"
        />
      </div>
      <div class="form-group">
        <label>Email</label>
        <input
          type="email"
          class="form-control"
          v-model="email"
          placeholder="Enter email"
          :readonly="loading"
        />
      </div>
      <div class="form-group">
        <label>Password</label>
        <input
          type="password"
          class="form-control"
          v-model="password"
          placeholder="Enter password"
          :readonly="loading"
        />
      </div>
      <div v-if="loading">
        <button class="btn btn-primary" type="submit" disabled>
          <span class="spinner-border spinner-border-sm" role="status"></span>
          Loading...
        </button>
      </div>
      <div v-else>
        <button class="btn btn-primary" type="submit" @click="register()">Register</button>
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
  width: 50%;
  border: 1px solid lightgray;
}
.status {
  margin-left: 8px;
}
</style>