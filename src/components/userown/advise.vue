<template>
    <div>
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>工作信息管理</el-breadcrumb-item>
        <el-breadcrumb-item>发表建议列表</el-breadcrumb-item>
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
            <el-col :span="4"
            >
        <el-button type="primary" @click="addDialogVisible = true">发表建议</el-button>
      </el-col>
          </el-row>
        <!-- 表格 -->
        <template>
          <div v-for="s in suggestionsList" :key="s.id">
              <el-collapse accordion>
                <el-collapse-item>
                  <template slot="title">
                    {{s.suggestionTitle}}
                    <i class="header-icon el-icon-info"></i>
                  </template>
                  <div>{{s.content}}</div>
                <!-- <el-button
                      type="danger"
                      icon="el-icon-delete"
                      @click="delSuggestion(s.id)"
                    ></el-button> -->
                </el-collapse-item>
              </el-collapse>
          </div>
        </template>

     <!-- 添加建议对话框 -->
     <el-dialog title="添加建议" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
        <el-form :model="ruleForm" :rules="rules" ref="formRef" label-width="100px" class="demo-ruleForm">
          <el-form-item label="建议主题" prop="suggestionTitle">
            <el-input v-model="ruleForm.suggestionTitle"></el-input>
          </el-form-item>
          <el-form-item label="建议内容" prop="content">
            <el-input v-model="ruleForm.content"></el-input>
          </el-form-item>
          </el-form-item>
          <el-form-item label="建议会议" prop="conferName">
            <el-select v-model="ruleForm.conferName" placeholder="请选择" filter-method="SelectOption">
              <el-option
                v-for="item in options"
                :key="item"
                :label="item"
                :value="item"
                :disabled="item.disabled">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSuggestionSureBtn">确 定</el-button>
        </span>
      </el-dialog>
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
        options:[],
        suggestionsList: [],
        offset: 1,
        limit: 5,
        queryInfo: {
          query: '',
        },
        addDialogVisible: false,
        ruleForm:{
            suggestionTitle:'',
            content:'',
            conferName:''
        },

    rules: {
        suggestionTitle: [
          { required: true, message: '请输入建议主题', trigger: 'blur' },
        ],
        content: [{ required: true, message: '请输入内容', trigger: 'blur' }],
        conferName: [
          { required: true, message: '请选择会议', trigger: 'blur' },
        ],
      },
      }
    },
    created() {
      this.getSuggestionsList()
      this.SelectOption()
    },
    methods: {
    //下拉搜索会议
    SelectOption(){
      let _that = this;
        this.$axios({
          methods: 'get',
          url: 'person/selectAllName'
        }).then((res)=>{
          _that.options = res.data.data;
          //alert(JSON.stringify(_that.options))
        }).catch(()=>{
          this.$message.error('获取会议列表失败')
        })
    },
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
      },
      addSuggestionSureBtn() {
        this.$refs.formRef.validate((boo) => {
          if (!boo) {
            return
            } else {
            this.$axios({
                method:'post',
                data: this.ruleForm,
                // dataType:JSON,
                url:'suggestion/suggestion_insert',
                headers:{
                    'token':window.sessionStorage.getItem("token")
                }
            })
              .then(() => {
                    alert(this.ruleForm)
                    this.addDialogVisible = false
                    this.getSuggestionsList()
                    this.$message.success('添加建议成功')
                })
                .catch(() => {
                    this.$message.error('添加建议失败')
                })
            }
        })
    },
      addDialogClose() {
      this.$refs.formRef.resetFields()
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
  