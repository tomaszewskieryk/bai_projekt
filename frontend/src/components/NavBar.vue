<template>
  <div>
    <b-navbar class="fixed-top" toggleable="lg" type="light" variant="light">
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/" v-if="user != null">
            <font-awesome-icon icon="home" /> Strona główna
          </b-nav-item>
          <b-nav-item href="/login" v-if="user == null">
            <font-awesome-icon icon="sign-in-alt" /> Logowanie
          </b-nav-item>
          <b-nav-item href="/register" v-if="user == null">
            <font-awesome-icon icon="user-plus" /> Rejestracja
          </b-nav-item>
          <b-nav-item href="/actual" v-if="user != null">
            <font-awesome-icon icon="book" /> Stan lodówki
          </b-nav-item>
          <b-nav-item href="/fridges" v-if="user != null">
            <font-awesome-icon icon="arrow-circle-right" /> Tworzenie list
          </b-nav-item>
          <b-nav-item href="/lists" v-if="user != null">
            <font-awesome-icon icon="clipboard-list" /> Aktualne listy zakupów
          </b-nav-item>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto" v-if="user != null">
          <b-button variant="danger" size="sm" @click="logout()">Wyloguj</b-button>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import toast from '../resources/toast'

export default {
  data() {
    return {
      user: null
    };
  },
  mounted() {
    this.getUser();
  },
  methods: {
    logout() {
      localStorage.clear();
      toast.success("Wylogowano", "Sukces!");
      this.user = null;
      this.$router.push('login');
    },
    getUser() {
      this.user = localStorage.getItem("user");
    }
  }
};
</script>