<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>公告管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card class="box-card">
      <!-- 警告 -->
<!--      <el-alert
        title="注意：只允许为第三级分类设置相关参数！"
        type="warning"
        :closable="false"
        show-icon
      >
      </el-alert>
      //选择 
      <div class="block">
        <span>选择商品分类: </span>
        <el-cascader
          v-model="selectedCateKeys"
          :options="cateList"
          @change="handleChange"
          :props="{ value: 'cat_id', label: 'cat_name', children: 'children' }"
        ></el-cascader>
      </div>
      //最下方
      // 标签页
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="动态参数" name="first">
          <el-button type="primary" :disabled="isDisabled1">添加参数</el-button>
        </el-tab-pane>
        <el-tab-pane label="静态属性" name="second">
          <el-button type="primary" :disabled="isDisabled2">添加属性</el-button>
        </el-tab-pane>
      </el-tabs>  -->
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane label="公告展示" name="first">
              <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%" stripe border>
                <el-table-column type="index"></el-table-column>
                <el-table-column prop="id" label="id" width="100"> </el-table-column>
                <el-table-column prop="contentTitle" label="公告标题" width="180"> </el-table-column>
                <el-table-column prop="newTime" label="发布时间" width="150"> </el-table-column>
                <el-table-column prop="content" label="发布内容" width="350"> </el-table-column>
                <el-table-column prop="publishName" label="发布人" width="120"></el-table-column>
                <el-table-column prop="product.status" label="状态" width="100"> </el-table-column>
                <el-table-column label="操作" width="200">
                  <template slot-scope="scope">
                    <el-button
                      type="primary"
                      icon="el-icon-edit"
                      size="small"
                      @click="editNews(scope.row)"
                      >编辑</el-button
                    >
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="small"
                      @click="delNews(scope.row)"
                      >删除</el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div>
              <el-pagination @size-change="handleSizeChange" 
                              @current-change="handleCurrentChange" 
                              :current-page="currentPage" 
                              :page-sizes="pageSizes" 
                              :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper" 
                              :total="totalCount">
                 </el-pagination>
            </div>
          </el-tab-pane>
          <el-tab-pane label="公告编写" name="second">
            <el-form class="table" :rules="rules">
              <el-form-item label="公告标题" prop="title">
                <el-input width="60px" v-model="editorTextUpload.contentTitle"></el-input>
              </el-form-item>
              <editor id="editor_id" height="450px" width="1200px" :content.sync="editorText"
                  :afterChange="afterChange()"
                  pluginsPath="../../../static/kindeditor/plugins/" 
                  :loadStyleMode="false"
                  @on-content-change="onContentChange">
              </editor>
            </el-form>
              <div><el-button type="primary" round @click="sub()">提交<i class="el-icon-upload el-icon--right"></i></el-button></div>
          </el-tab-pane>
          <el-tab-pane label="内容暂定" name="third">这里没有内容</el-tab-pane>
          <el-tab-pane label="定时任务补偿" name="fourth">
              <div class="block">
                <span class="demonstration">定时任务发布：</span>
                <el-date-picker
                  v-model="timing"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择日期时间"
                  align="center"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </div>
              <div>
                发布标题：<el-input v-model="editorTextUpload.contentTitle"/>
                发布内容：<el-input v-model="editorTextUpload.content"/>
              </div><br/>
              <div><el-button type="primary" round @click="subOnTime()">提交<i class="el-icon-upload el-icon--right"></i></el-button></div>
          </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 编辑公告 -->
    <el-dialog title="提示" :visible.sync="editDialogVisible" width="30%">
      <el-form ref="addnewsRef" :model="editNewsForm" label-width="80px">
        <el-form-item label="公告标题">
          <el-input v-model="editNewsForm.contentTitle"></el-input>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="editNewsForm.newTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="选择时间" v-model="editNewsForm.newTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-time-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="发布内容">
          <el-input v-model="editNewsForm.content"></el-input>
        </el-form-item>
        <el-form-item label="发布人员">
          <el-input v-model="editNewsForm.publishName"></el-input>
        </el-form-item>
        <el-form-item label="公告概述">
          <el-input v-model="editNewsForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editNewsSureBtn">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage:1, //初始页
      PageSize:6,
      userList: [],
      totalCount: 1,
      pageSizes:[5,10,20],
      activeName: 'first',
      tableData:[],
      editNewsForm: {},
      // cateList: [],
      // selectedCateKeys: [],
      // activeName: 'first',
      // isDisabled1: true,
      // isDisabled2: true,
      editorText: '请输入公告内容', // 双向同步的变量
      editorTextUpload: {
        contentTitle:'',
        content:'',
      },
      editDialogVisible: false,
      pickerOptions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
        },
      timing: '',
      rules: {
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.getNewsList();
    //this.getPublishName();
  },
  methods: {
    subOnTime(){
        let _that = this;
        this.$axios.
          put('news/timing_insert',{
             contentTitle: this.editorTextUpload.contentTitle,
             content: this.editorTextUpload.content,
             timeSelect: this.timing
          }).then((res)=>{
            if(res.data.code == 400){ 
            this.$message.error(res.data.msg)
          }
          alert(JSON.stringify(res.data.data.publishId))
          this.$message.success('发布公告成功')
          this.getNewsList();
        })
        .catch((res)=>{
          this.$message.error(res.data.data.msg)
          alert(this.editorTextUpload.contentTitle+"-"+this.editorTextUpload.content+"-"+this.timing)
        })
    },
    sub(){
        //alert(JSON.stringify(this.editorTextUpload))
        this.$axios({         //user添加一个标识，加入token，公告展示是哪个发布的
          method: 'post',
          data: this.editorTextUpload,
          url: 'news/news_insert',
          headers:{
                'token':window.sessionStorage.getItem("token")
              }
        }).then((res)=>{
          //alert(JSON.stringify(res.data.data.publishId))
          this.$message.success('发布公告成功')
          this.getNewsList();
        })
        .catch(()=>{
          this.$message.error("发布公告失败")
        })
      },
      //得到发布公告人员名
    getPublishName(publishId){
      this.$axios({
        method:'get',
        url:'news/findPublishName?publishId='+publishId,
        headers:{
          'token':window.sessionStorage['token']
        }
      }).then((res) => {
          //_this.tableData.publishId = res.data
          alert(JSON.stringify(res.data))
        })
        .catch(() => {
          this.$message.error('获取名字失败')
          alert(_this.getName.id+"-->"+_this.getName.publishId)
        })
      },
    handleClick(tab, event) {
        console.log(tab, event);
      },
    onContentChange (val) {
      this.editorTextUpload.content = val;
      //console.log(this.editorTextCopy)
    },
    afterChange () {

    },
    getNewsList() {
      let _this = this;
      this.$axios({
        method:'get',
        url:'news/findAll',
        headers:{
          'token':window.sessionStorage['token']
        }
      }).then((res) => {
          _this.tableData = res.data
          this.totalCount = res.data.length
          //alert(JSON.stringify(_this.tableData))
        })
        .catch(() => {
          this.$message.error('获取公告信息列表失败')
        })
    },
        //编辑会议
    editNews(row) {
      this.editNewsForm = {id: row.id,
                                  contentTitle: row.contentTitle,
                                  newTime: row.newTime,
                                  content: row.content,
                                  publishName: row.publishName,
                                  roleDesc: row.roleDesc }
      this.editDialogVisible = true
    },
    editNewsSureBtn(){
      alert(JSON.stringify(this.editNewsForm))
        this.$axios.
        put('news/news_update',this.editNewsForm)
        .then((res)=>{
          this.$message.success('更新公告信息成功')
          //this.editNewsForm = res.data.data;
          this.getNewsList();
        })
        .catch(()=>{
          this.$message.error('更新公告信息失败')
        })
        this.editDialogVisible=false;
    },
    delNews(row){
      this.$confirm('此操作将删除该公告, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('news/news_delete?id=' + row.id)
            .then(() => {
              this.getNewsList()
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
      // 每页显示的条数
      handleSizeChange(val) {
           // 改变每页显示的条数 
           this.PageSize=val
           // 注意：在改变每页显示的条数时，要将页码显示到第一页
           this.currentPage=1
       },
         // 显示第几页
       handleCurrentChange(val) {
           // 改变默认的页数
           this.currentPage=val
       },
    // getCateList() {
    //   this.$axios.get('categories').then((res) => {
    //     this.cateList = res.data.data
    //   })
    // },
    // handleChange() {
    //   if (this.selectedCateKeys.length !== 3) {
    //     this.selectedCateKeys = []
    //     this.isDisabled1 = true
    //     this.isDisabled2 = true
    //     return
    //   } else {
    //     this.isDisabled1 = false
    //     this.isDisabled2 = false
    //     console.log(this.selectedCateKeys)
    //   }
    // },
    // handleTabClick(tab, event) {
    //   console.log(tab)
    //   console.log(event)
    // },
  },
}
</script>

<style lang="less" scoped></style>
