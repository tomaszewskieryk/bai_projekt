<template>
  <div class="form">
    <h1>Rejestracja</h1>
    <b-form @submit="onSubmit">
      <b-form-group id="input-group-1" label="Adres email:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="form.email"
          type="email"
          required
          placeholder="Wprowadź email"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Nazwa użytkownika:" label-for="input-2">
        <b-form-input
          id="input-2"
          v-model="form.nickname"
          required
          placeholder="Wprowadź nazwę użytkownika"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-2" label="Hasło:" label-for="input-3">
        <b-form-input
          id="input-3"
          type="password"
          v-model="form.password"
          required
          placeholder="Wprowadź hasło"
        ></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="light">Zarejestruj</b-button>
    </b-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        email: "",
        nickname: "",
        password: ""
      }
    };
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      this.axios
        .post("http://localhost:8100/register", {
          email: this.form.email,
          nickname: this.form.nickname,
          password: this.form.password
        })
        .then(function(response) {
          console.log(response);
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
@media (min-width: 1000px) {
  .form {
    width: 40%;
    margin: auto;
  }
}
@media (min-width: 800px) and (max-width: 999px) {
  .form {
    width: 55%;
    margin: auto;
  }
}
@media (min-width: 600px) and (max-width: 799px) {
  .form {
    width: 70%;
    margin: auto;
  }
}
</style>