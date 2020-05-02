<template>
  <div>
    <table class="table">
      <thead>
        <tr>
          <th scope="col">Nazwa</th>
          <th scope="col">Ilość</th>
          <th scope="col">Akcja</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in fridge.products" :key="product.product.productName">
          <td>{{product.product.productName}}</td>
          <td>{{product.amount}}</td>
          <td>
            <b-button variant="light" v-b-modal.modal-1 @click="setProduct(product)">Edytuj</b-button>
          </td>
        </tr>
        <tr>
          <td></td>
          <td></td>
          <td>
            <b-button variant="success" v-b-modal.modal-1 @click="setProduct(null)">Dodaj</b-button>
          </td>
        </tr>
      </tbody>
    </table>

    <b-modal id="modal-1" hide-footer title="Produkt">
      <form>
        <div class="form-group">
          <label for="exampleInputEmail1">Nazwa produktu</label>
          <input type="text" v-model="product.productName" class="form-control" id="name" />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Ilość</label>
          <input type="text" v-model="product.amount" class="form-control" id="name" />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Szacowana cena</label>
          <input type="text" v-model="product.price" class="form-control" id="name" />
        </div>
        <div class="form-group">
          <label for="exampleInputEmail1">Jednostka</label>
          <v-select :options="units" v-model="product.unit"></v-select>
        </div>
        <button type="button" @click="save()" class="btn btn-success">Submit</button>
      </form>
    </b-modal>
  </div>
</template>


<script>
import toast from "../resources/toast";
import "vue-select/dist/vue-select.css";

export default {
  data() {
    return {
      units: ["kg", "l", "p"],
      fridge: {
        products: []
      },
      product: {
        productName: "",
        productID: 0,
        unit: "",
        price: 0,
        amount: 0
      }
    };
  },
  mounted() {
    this.axios
      .get(`http://localhost:8100/fridgeState/${localStorage.getItem("user")}`)
      .then(response => {
        this.fridge = response.data;
        toast.success(response, "Sukces");
      })
      .catch(function(error) {
        toast.error(error, "Błąd");
      });
  },
  methods: {
    setProduct(product) {
      if (product == null) {
        this.product.productName = "";
        this.product.productID = 0;
        this.product.unit = null;
        this.product.price = 0;
        this.product.amount = 0;
      } else {
        this.product.productName = product.product.productName;
        this.product.productID = product.product.productID;
        this.product.unit = product.product.unit;
        this.product.price = product.product.price;
        this.product.amount = product.amount;
      }
    },
    save() {
      if (this.product.productID == 0) {
        this.axios
          .post(`http://localhost:8100/product/${this.fridge.fridgeID}`, {
            productName: this.product.productName,
            unit: this.product.unit,
            price: this.product.price,
            amount: this.product.amount
          })
          .then(() => {
            toast.success("Dodano produkt", "Sukces");
            this.$router.push("actual");
          })
          .catch(function(error) {
            toast.error(error, "Błąd");
          });
      } else {
        this.axios
          .put(`http://localhost:8100/product`, {
            productName: this.product.productName,
            productID: this.product.productID,
            unit: this.product.unit,
            price: this.product.price,
            amount: this.product.amount
          })
          .then(() => {
            toast.success("Edycja pomyślna", "Sukces");
            this.$router.push("actual");
          })
          .catch(function(error) {
            toast.error(error, "Błąd");
          });
      }
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