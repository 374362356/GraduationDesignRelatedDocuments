<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card class="box-card">
      <!-- 添加按钮 -->
      <el-button type="primary">添加分类</el-button>
      <!-- 表格 -->
      <template>
        <el-table :data="categoriesList" style="width: 100%">
          <el-table-column type="index"> </el-table-column>
          <el-table-column prop="cat_name" label="分类名称" width="180">
          </el-table-column>
          <el-table-column prop="cat_deleted" label="是否有效" width="180">
            <el-button
              type="success"
              icon="el-icon-check"
              circle
              size="mini"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-close"
              circle
              size="mini"
            ></el-button>
          </el-table-column>
          <el-table-column prop="cat_level" label="排序"> </el-table-column>
          <el-table-column label="操作"> </el-table-column>
        </el-table>
      </template>
      <!-- 分页 -->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="categoriesList.length"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categoriesList: [],
      pagenum: 1,
      pagesize: 5,
    }
  },
  created() {
    this.getCategoriesList()
  },
  methods: {
    //获取分类列表
    getCategoriesList() {
      this.$axios
        .get('categories', {
          type: 3,
          pagenum: this.pagenum,
          pagesize: this.pagesize,
        })
        .then((res) => {
          this.categoriesList = res.data.data
          console.log(res)
        })
    },
    handleSizeChange(val) {
      this.pagesize = val
    },
    handleCurrentChange(val) {
      this.pagenum = val
    },
  },
}
</script>

<style lang="less" scoped></style>>=
