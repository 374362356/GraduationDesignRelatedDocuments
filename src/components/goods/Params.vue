<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>参数管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card class="box-card">
      <!-- 警告 -->
      <el-alert
        title="注意：只允许为第三级分类设置相关参数！"
        type="warning"
        :closable="false"
        show-icon
      >
      </el-alert>
      <!-- 选择 -->
      <div class="block">
        <span>选择商品分类: </span>
        <el-cascader
          v-model="selectedCateKeys"
          :options="cateList"
          @change="handleChange"
          :props="{ value: 'cat_id', label: 'cat_name', children: 'children' }"
        ></el-cascader>
      </div>
      <!-- 最下方 -->
      <!-- 标签页 -->
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="动态参数" name="first">
          <el-button type="primary" :disabled="isDisabled1">添加参数</el-button>
        </el-tab-pane>
        <el-tab-pane label="静态属性" name="second">
          <el-button type="primary" :disabled="isDisabled2">添加属性</el-button>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cateList: [],
      selectedCateKeys: [],
      activeName: 'first',
      isDisabled1: true,
      isDisabled2: true,
    }
  },
  created() {
    this.getCateList()
  },
  methods: {
    getCateList() {
      this.$axios.get('categories').then((res) => {
        this.cateList = res.data.data
      })
    },
    handleChange() {
      if (this.selectedCateKeys.length !== 3) {
        this.selectedCateKeys = []
        this.isDisabled1 = true
        this.isDisabled2 = true
        return
      } else {
        this.isDisabled1 = false
        this.isDisabled2 = false
        console.log(this.selectedCateKeys)
      }
    },
    handleTabClick(tab, event) {
      console.log(tab)
      console.log(event)
    },
  },
}
</script>

<style lang="less" scoped></style>
