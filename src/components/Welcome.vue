<template>
  <div>
    <h1>Welcome!</h1>
    <el-calendar v-model="value">
    </el-calendar>
  </div>
</template>

<script>
export default {
    data() {
      return {
        value: new Date()
      }
    }
}
</script>

<style lang="stylus" scoped>

</style>