<template>
  <div class="main">
    <h1 class="headline">{{list.shoppingListName}}</h1>
    <h3 class="price">Przybliżony koszt zakupów: <b><font color="#CD5C5C">{{list.price}}</font></b></h3>
    <table class="table">
      <thead>
        <tr>
          <th>Nazwa</th>
          <th>Ilość</th>
          <th>Akcja</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in list.products" :key="product.productName">
          <td>{{product.productName}}</td>
          <td>{{product.amount}}</td>
          <td>
            <b-button
              variant="success"
              @click="deleteProduct(product.productID)"
            >Kupione</b-button>
          </td>
        </tr>
      </tbody>
    </table>
    <b-button-group class="btns">
      <b-button variant="info" v-b-modal.modal-1>Udostępnij listę</b-button>
      <b-button variant="danger" @click="deleteList()">Usuń listę</b-button>
    </b-button-group>
    <b-modal ref="my-modal" id="modal-1" hide-footer title="Produkt">
      <form>
        <div class="form-group">
          <label for="name">Podaj email osoby, dla której chcesz udostępnić listę</label>
          <input type="text" v-model="email" class="form-control" id="name" />
        </div>
        <button type="button" @click="share()" v-b-modal.modal-1 class="btn btn-success">Udostępnij</button>
      </form>
    </b-modal>
  </div>
</template>

<script>
import toast from "../resources/toast";

export default {
  data() {
    return {
      list: {
        products: [],
        email: ""
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
          console.log(this.list)
          if (this.list.products.length == 0) {
            toast.success("Usuwanie pustej listy...");
            this.deleteList();
          }
        })
        .catch(function() {
          toast.error("Nie udało się pobrać listy", "Błąd");
        });
    },
    deleteProduct(id) {
      this.axios
        .delete(
          `http://localhost:8100/lists/${this.list.shoppingListID}/products/${id}`
        )
        .then(() => {
          toast.success("Usunięto produkt z listy", "Sukces");
          this.getList();
        })
        .catch(function() {
          toast.error("Nie udało się usunąć produktu z listy", "Błąd");
        });
    },
    deleteList() {
      this.axios
        .delete(`http://localhost:8100/lists/${this.list.shoppingListID}`)
        .then(() => {
          toast.success("Usunięto listę", "Sukces");
          this.$router.push("/lists");
        })
        .catch(function() {
          toast.error("Nie udało się usunąć listy", "Błąd");
        });
    },
    share() {
      this.axios
        .post(
          `http://localhost:8100/lists/${this.list.shoppingListID}/share`, {
              email: this.email
          }
        )
        .then(() => {
          toast.success("Udostępniono listę", "Sukces");
        })
        .catch(function() {
          toast.error("Nie udało się udostępnić listy", "Błąd");
        });
    }
  }
};
</script>

<style scoped>
.headline {
  color: #2f4f4f;
  font-size: 30px;
  font-family: Tahoma, Verdana, Segoe, sans-serif;
  text-align: center;
}
.price {
  color: #2f4f4f;
  font-size: 20px;
  font-family: Tahoma, Verdana, Segoe, sans-serif;
  text-align: center;
}
.btns {
  width: 100%;
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