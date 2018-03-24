<template>
  <div id="root">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'root'
}
</script>

<style>
html,body{
    height: 100%;
    width: 100%;
    margin:0px;
    padding:0px;
}
#root{
    width: 100%;
    height: 100%;
}
</style>
