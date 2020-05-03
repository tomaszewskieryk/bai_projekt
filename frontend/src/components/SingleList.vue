<template>
    <h1 class="headline">Jestem Twoją <i><font color="#CD5C5C">listą zakupów</font></i>!</h1>
</template>

<script>
import toast from "../resources/toast";

export default {
  data() {
    return {
      list: {
        products: []
      }
    };
  },
  mounted() {
    this.getList();
  },
  methods: {
    getList() {
      this.axios
        .get(`http://localhost:8100/lists/${this.$route.params.id}`)
        .then(response => {
          this.list = response.data;
          console.log(this.list);
        })
        .catch(function() {
          toast.error("Nie udało się pobrać listy", "Błąd");
        });
    },
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

</style>