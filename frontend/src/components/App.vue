<template>
  <div id="app">
    <navbar></navbar>
    <div class="router">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  }
};
</script>

<style>
  .router {
    margin-top: 50px;
    padding: 16px;
  }
</style>