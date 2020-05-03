<template>
<div class="main">
    <h1 class="headline">Cześć, to my - Twoje <font color="#CD5C5C">listy zakupowe</font>!</h1>
    <ul class="list-group">
      <li
        class="list-group-item"
        v-for="list of lists"
        :key="list.shoppingListName"
        @click="navigate(list.shoppingListID)"
      >{{list.shoppingListName}}</li>
    </ul>
</div>
</template>

<script>
import toast from "../resources/toast";

export default {
  data() {
    return {
      lists: []
    };
  },
  mounted() {
    this.getLists();
  },
  methods: {
    getLists() {
      this.axios
        .get(
          `http://localhost:8100/users/${localStorage.getItem("user")}/lists`
        )
        .then(response => {
          this.lists = response.data;
          console.log(this.lists)
        })
        .catch(function() {
          toast.error("Nie udało się pobrać list", "Błąd");
        });
    },
    navigate(id) {
      this.$router.push(`list/${id}`);
    }
  }
};
</script>

<style scoped>
.headline{
    color: 	#2F4F4F;
    font-size: 30px;
    font-family: Tahoma, Verdana, Segoe, sans-serif;
    text-align: center;
    }
li {
  cursor: pointer;
  text-align: center;
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