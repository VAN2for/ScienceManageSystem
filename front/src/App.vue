<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  mounted() {
    if (this.$route.path !== '/login') {
      this.$router.push('/login');
    }
  }
}
</script>

<style>

</style>