<template>
  <b-list-group>
    <b-list-group-item>Cras justo odio</b-list-group-item>
    <b-list-group-item>Dapibus ac facilisis in</b-list-group-item>
    <b-list-group-item>Morbi leo risus</b-list-group-item>
    <b-list-group-item>Porta ac consectetur ac</b-list-group-item>
    <b-list-group-item>Vestibulum at eros</b-list-group-item>
  </b-list-group>
</template>


<script>
import toast from "../resources/toast";

export default {
  data() {
    this.axios
      .get("http://localhost:8100/fridgeState/1")
      .then(function(response) {
        console.log("response");
        console.log(response);
        toast.success(response, "Sukces");
      })
      .catch(function(error) {
        console.log("error");
        console.log(error);
        toast.error(error, "Błąd");
      });

      return {
          data : 0
      }
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      this.axios
        .post("http://localhost:8100/login", {
          username: this.form.username,
          password: this.form.password
        })
        .then(function(response) {
          console.log("response");
          console.log(response);
          toast.success(response, "Sukces");
        })
        .catch(function(error) {
          console.log("error");
          console.log(error);
          toast.error(error, "Błąd");
        });
    }
  }
};
</script>

<style scoped>
/* @media (min-width: 1000px) {
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
} */
</style>