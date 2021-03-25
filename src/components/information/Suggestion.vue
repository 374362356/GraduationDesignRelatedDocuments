<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>建议管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card class="box-card">
      <!-- 添加按钮 -->
      <el-row :gutter="20">
          <el-col :span="10">
            <el-input
              placeholder="根据用户名，电话查询"
              v-model="queryInfo.query"
              clearable
              @clear="getSuggestionsList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getSuggestionsByfuzzy"
              ></el-button>
            </el-input>
          </el-col>
        </el-row>
      <!-- 表格 -->
      <template>
        <!-- <el-table :data="categoriesList" style="width: 100%">
          <el-table-column type="index"> </el-table-column>
          <el-table-column prop="name" label="分类名称" width="180">
          </el-table-column>
          <el-table-column prop="cat_deleted" label="是否有效" width="180">
            <el-button type="success" icon="el-icon-check" circle size="mini"></el-button>
            <el-button type="danger" icon="el-icon-close" circle size="mini"></el-button>
          </el-table-column>
          <el-table-column prop="level" label="等级"> </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                      type="primary"
                      icon="el-icon-edit"
                      @click="showEditDialog(scope.row.id)"
              ></el-button>
              <el-button
                      type="danger"
                      icon="el-icon-delete"
                      @click="delUser(scope.row.user.id)"
              ></el-button>
              <el-tooltip class="item" effect="dark" content="查看下级" placement="top" :enterable="false">
                <el-button
                        type="warning"
                        icon="el-icon-setting"
                        @click="allotUserRole(scope.row)"
                ></el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table> -->
        <div v-for="s in suggestionsList" :key="s.id">
            <el-collapse accordion>
              <el-collapse-item>
                <template slot="title">
                  {{s.suggestionTitle}}
                  <i class="header-icon el-icon-info"></i>
                </template>
                <div>{{s.content}}</div>
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    @click="delSuggestion(s.id)"
                  ></el-button>
              </el-collapse-item>
            </el-collapse>
        </div>
      </template>
      <!-- 分页 -->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="suggestionsList.length"
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
      suggestionsList: [],
      offset: 1,
      limit: 5,
      queryInfo: {
        query: '',
      }
    }
  },
  created() {
    this.getSuggestionsList()
  },
  methods: {
    getSuggestionsByfuzzy(){
      let _this = this;
          this.$axios({
              method:'get',
              url:'suggestion/fuzzy?suggestionTitle='+this.queryInfo.query
          }).then((res) =>{
              _this.suggestionsList = res.data.data
              //alert(_this.userList)
          }).catch(() =>{
              this.$message.error('模糊查询失败')
          })
    },
    delSuggestion(id){
        this.$confirm('此操作将删除该建议, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('suggestion/suggestion_delete?id=' + id)
            .then(() => {
              this.getSuggestionsList()
              this.$message.success('删除成功')
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    //获取建议列表
    getSuggestionsList() {
      let _this = this;
      this.$axios
        .get('suggestion/findAll?offset='+this.offset+'&limit='+this.limit)
        .then((res) => {
          this.suggestionsList = res.data.data

        })
      /*this.$axios({
        method:'get',
        url:'category/category_list?id='+1,
        // data:{id:1},
        // url:'list',
        // params:id,
        headers:{
          'token':window.sessionStorage['token']
        }
      }).then((res) => {
        _this.categoriesList = res.data.data
        // if (res.meta.status !== 200) {
        //     return this.$message.error('获取权限列表失败！')
        // }
      }).catch(() => {
        this.$message.error('获取权限列表失败')
      })*/
    },
    handleSizeChange(val) {
      this.limit = val
    },
    handleCurrentChange(val) {
      this.offset = val
    },
  },
}
</script>

<style lang="less" scoped></style>
