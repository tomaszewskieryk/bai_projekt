<template>
  <div class="main">
    <h1 class="headline">{{fridge.fridgeName}}</h1>
    <b-button-group class="btns">
      <b-button variant="info" v-b-modal.modal-2>Zmień nazwę</b-button>
      <b-button variant="danger" @click="deleteFridge()">Usuń</b-button>
      <b-button variant="success">Generuj listę</b-button>
    </b-button-group>

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
        <div class="form-group" v-if="!hideSelect">
          <label for="select">Wybierz z listy</label>
          <select class="form-control" @change="selected()" v-model="selectedProduct.id">
            <option :value="0">-----</option>
            <option v-for="item of proposedItems" :key="item.productName" :value="item.productID">{{item.productName}}</option>
          </select>
        </div>
        <p v-if="showNew && !hideSelect" style="text-align: center"><b>Lub stwórz nowy</b></p>
        <div class="form-group" v-if="showNew">
          <label for="exampleInputEmail1">Nazwa produktu</label>
          <input type="text" v-model="product.productName" class="form-control" id="name" />
        </div>
        <div class="form-group" v-if="showNew">
          <label for="exampleInputEmail1">Ilość</label>
          <input type="text" v-model="product.amount" class="form-control" id="name" />
        </div>
        <div class="form-group" v-if="!showNew">
          <label for="exampleInputEmail1">Ilość</label>
          <input type="text" v-model="selectedProduct.amount" class="form-control" id="name" />
        </div>
        <div class="form-group" v-if="showNew">
          <label for="exampleInputEmail1">Szacowana cena</label>
          <input type="text" v-model="product.price" class="form-control" id="name" />
        </div>
        <div class="form-group" v-if="showNew">
          <label for="exampleInputEmail1">Jednostka</label>
          <v-select :clearable="false" :options="units" v-model="product.unit"></v-select>
        </div>
        <button
          type="button"
          @click="saveProduct()"
          v-b-modal.modal-1
          class="btn btn-success" v-if="showNew"
        >Zapisz</button>
        <button
          type="button"
          @click="saveSelectedProduct()"
          v-b-modal.modal-1
          class="btn btn-success" v-if="!showNew"
        >Zapisz</button>
        <button
          type="button"
          v-if="product.productID != 0"
          @click="deleteProduct()"
          v-b-modal.modal-1
          class="btn btn-danger delete"
        >Usuń</button>
      </form>
    </b-modal>

    <b-modal ref="my-modal2" id="modal-2" hide-footer title="Zmiana nazwy">
      <form>
        <div class="form-group">
          <label for="name">Nowa nazwa</label>
          <input type="text" v-model="newName" class="form-control" id="name" />
        </div>
        <button type="button" @click="editName()" v-b-modal.modal-2 class="btn btn-success">Zapisz</button>
      </form>
    </b-modal>
  </div>
</template>

<script>
import toast from "../resources/toast";

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
      },
      newName: "",
      proposedItems: [],
      selectedProduct: {
          id: 0,
          amount: 0
      },
      showNew: true,
      hideSelect: false
    };
  },
  mounted() {
    this.getFridge();
  },
  methods: {
    getFridge() {
      this.axios
        .get(`http://localhost:8100/fridges/${this.$route.params.id}`)
        .then(response => {
          this.fridge = response.data;
        })
        .catch(function() {
          toast.error("Nie udało się pobrać lodówki", "Błąd");
        });

      this.axios
        .get(
          `http://localhost:8100/users/${localStorage.getItem(
            "user"
          )}/actualfridge`
        )
        .then(response => {
          this.proposedItems = response.data.products;
        })
        .catch(function() {
          toast.error("Nie udało się pobrać stanu lodówki", "Błąd");
        });
    },
    setProduct(product) {
    this.showNew = true;
      if (product == null) {
        this.hideSelect = false;
        this.product.productName = "";
        this.product.productID = 0;
        this.product.unit = null;
        this.product.price = 0;
        this.product.amount = 0;
      } else {
        this.hideSelect = true;
        this.product.productName = product.productName;
        this.product.productID = product.productID;
        this.product.unit = product.unit;
        this.product.price = product.price;
        this.product.amount = product.amount;
      }
    },
    saveProduct() {
      if (this.product.productID == 0) {
        this.axios
          .post(
            `http://localhost:8100/fridges/${this.fridge.fridgeStateID}/products`,
            {
              productName: this.product.productName,
              unit: this.product.unit,
              price: this.product.price,
              amount: this.product.amount
            }
          )
          .then(() => {
            this.$refs["my-modal"].hide();
            toast.success("Dodano produkt", "Sukces");
            this.getFridge();
          })
          .catch(function() {
            toast.error("Nie udało się dodać produktu", "Błąd");
          });
      } else {
        this.axios
          .put(
            `http://localhost:8100/fridges/${this.fridge.fridgeStateID}/products`,
            {
              productName: this.product.productName,
              productID: this.product.productID,
              unit: this.product.unit,
              price: this.product.price,
              amount: this.product.amount
            }
          )
          .then(() => {
            this.$refs["my-modal"].hide();
            toast.success("Edycja pomyślna", "Sukces");
            this.getFridge();
          })
          .catch(function() {
            toast.error("Nie udało się zedytować produktu", "Błąd");
          });
      }
    },
    deleteProduct() {
      this.axios
        .delete(
          `http://localhost:8100/fridges/${this.fridge.fridgeStateID}/products/${this.product.productID}`
        )
        .then(() => {
          this.$refs["my-modal"].hide();
          toast.success("Usunięto produkt", "Sukces");
          this.getFridge();
        })
        .catch(function() {
          toast.error("Nie udało się usunąć produktu", "Błąd");
        });
    },
    editName() {
      this.axios
        .put(`http://localhost:8100/fridges`, {
          fridgeStateID: this.fridge.fridgeStateID,
          fridgeName: this.newName
        })
        .then(() => {
          this.$refs["my-modal2"].hide();
          toast.success("Zmieniono nazwę", "Sukces!");
          this.getFridge();
        })
        .catch(function() {
          toast.error("Nie udało się zmienić nazwy", "Błąd");
        });
    },
    deleteFridge() {
      this.axios
        .delete(`http://localhost:8100/fridges/${this.fridge.fridgeStateID}`)
        .then(() => {
          toast.success("Usunięto listę", "Sukces!");
          this.$router.push("/fridges");
        })
        .catch(function() {
          toast.error("Nie udało się usunąć listy", "Błąd");
        });
    },
    selected() {
        if(this.selectedProduct.id == 0) {
            this.showNew = true;
        } else {
            this.showNew = false;
        }
    },
    saveSelectedProduct() {
        this.axios
          .post(
            `http://localhost:8100/fridges/${this.fridge.fridgeStateID}/products`,
            {
              productID: this.selectedProduct.id,
              amount: this.selectedProduct.amount
            }
          )
          .then(() => {
            this.$refs["my-modal"].hide();
            toast.success("Dodano produkt", "Sukces");
            this.getFridge();
          })
          .catch(function() {
            toast.error("Nie udało się dodać produktu", "Błąd");
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
.btns {
  width: 100%;
  margin-bottom: 10px;
}
th {
  text-align: center;
}
td {
  text-align: center;
}
.delete {
  clear: both;
  float: right;
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