<template>
  <div id="sk-main">
    <div>
      <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <ul class="navbar-nav">
          <li class="nav-item">
            <a class="nav-link" href="#">
              <router-link to="/">Starterkit</router-link>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <router-link to="/register">Register</router-link>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <router-link to="/login">Login</router-link>
            </a>
          </li>
        </ul>
      </nav>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {}
};
</script>

<style scoped>
</style>
