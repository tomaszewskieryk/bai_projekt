<template>
  <div class="main">
    <ul class="list-group">
      <li
        class="list-group-item"
        v-for="fridge of fridges"
        :key="fridge.fridgeName"
        @click="navigate(fridge.fridgeStateID)"
      >{{fridge.fridgeName}}</li>
    </ul>
    <b-button variant="success" v-b-modal.modal-1>Stwórz nową</b-button>

    <b-modal ref="my-modal" id="modal-1" hide-footer title="Nowa lista">
      <form>
        <div class="form-group">
          <label for="name">Nazwa listy</label>
          <input type="text" v-model="newName" class="form-control" id="name" />
        </div>
        <button type="button" @click="add()" v-b-modal.modal-1 class="btn btn-success">Zapisz</button>
      </form>
    </b-modal>
  </div>
</template>


<script>
import toast from "../resources/toast";

export default {
  data() {
    return {
      fridges: [],
      newName: ""
    };
  },
  mounted() {
    this.getFridges();
  },
  methods: {
    getFridges() {
      this.axios
        .get(
          `http://localhost:8100/users/${localStorage.getItem("user")}/fridges`
        )
        .then(response => {
          this.fridges = response.data;
        })
        .catch(function() {
          toast.error("Nie udało się pobrać list", "Błąd");
        });
    },
    navigate(id) {
      this.$router.push(`fridge/${id}`);
    },
    add() {
      this.axios
        .post(`http://localhost:8100/fridges`, {
          fridgeName: this.newName,
          userID: localStorage.getItem("user")
        })
        .then(() => {
          this.$refs["my-modal"].hide();
          toast.success("Dodano listę", "Sukces!");
          this.getFridges();
        })
        .catch(function() {
          toast.error("Nie udało się dodać list", "Błąd");
        });
    }
  }
};
</script>

<style scoped>
li {
  cursor: pointer;
  text-align: center;
}

button {
  clear: both;
  float: right;
  margin-top: 10px;
}
@media (min-width: 1000px) {
  .main {
    width: 60%;
    margin: auto;
  }
}
@media (min-width: 800px) and (max-width: 999px) {
  .main {
    width: 70%;
    margin: auto;
  }
}
@media (min-width: 600px) and (max-width: 799px) {
  .main {
    width: 80%;
    margin: auto;
  }
}
</style>