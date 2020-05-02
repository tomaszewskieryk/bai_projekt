<template>
  <div class="main">
    <table class="table">
      <thead>
        <tr>
          <th>Nazwa</th>
          <th>Ilość</th>
          <th>Akcja</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in fridge.products" :key="product.productName">
          <td>{{product.productName}}</td>
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

    <b-modal ref="my-modal" id="modal-1" hide-footer title="Produkt">
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
          <v-select :clearable="false" :options="units" v-model="product.unit"></v-select>
        </div>
        <button type="button" @click="save()" v-b-modal.modal-1 class="btn btn-success">Zapisz</button>
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
    this.getFridge();
  },
  methods: {
    getFridge() {
      this.axios
        .get(
          `http://localhost:8100/fridgeState/${localStorage.getItem("user")}`
        )
        .then(response => {
          this.fridge = response.data;
        })
        .catch(function(error) {
          toast.error(error, "Błąd");
        });
    },
    setProduct(product) {
      if (product == null) {
        this.product.productName = "";
        this.product.productID = 0;
        this.product.unit = null;
        this.product.price = 0;
        this.product.amount = 0;
      } else {
        this.product.productName = product.productName;
        this.product.productID = product.productID;
        this.product.unit = product.unit;
        this.product.price = product.price;
        this.product.amount = product.amount;
      }
    },
    save() {
      if (this.product.productID == 0) {
        this.axios
          .post(`http://localhost:8100/product/${this.fridge.fridgeStateID}`, {
            productName: this.product.productName,
            unit: this.product.unit,
            price: this.product.price,
            amount: this.product.amount
          })
          .then(() => {
            this.$refs['my-modal'].hide();
            toast.success("Dodano produkt", "Sukces");
            this.getFridge();
          })
          .catch(function(error) {
            toast.error(error, "Błąd");
          });
      } else {
        this.axios
          .put(`http://localhost:8100/product/${this.fridge.fridgeStateID}`, {
            productName: this.product.productName,
            productID: this.product.productID,
            unit: this.product.unit,
            price: this.product.price,
            amount: this.product.amount
          })
          .then(() => {
            this.$refs['my-modal'].hide();
            toast.success("Edycja pomyślna", "Sukces");
            this.getFridge();
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
 th {
   text-align: center;
 }
 td {
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