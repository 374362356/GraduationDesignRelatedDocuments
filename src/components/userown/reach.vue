<template>
  <div>
    <h1>参会信息页面</h1>
    <!-- 面包屑区域 -->
    <div class="header">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>参会信息管理</el-breadcrumb-item>
        <el-breadcrumb-item>签到列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="main">
      <!-- 搜索栏 -->
      <el-card>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input
              placeholder="根据用户名，电话查询"
              v-model="queryInfo.query"
              clearable
              @clear="getReachList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getUserListByfuzzy"
              ></el-button>
            </el-input>
          </el-col>
        </el-row>
        <template>
          <el-table :data="userList" style="width: 100%" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column prop="id" label="编号" width="80"></el-table-column>
            <el-table-column prop="reachTime" label="签到时间" width="170"></el-table-column>
            <!-- <el-table-column prop="confirm" label="签到" width="120"></el-table-column> -->
            <el-table-column prop="userName" label="用户名" width="100" ></el-table-column>
            <el-table-column prop="staffName" label="会议负责人名" width="130"> </el-table-column>
            <el-table-column prop="conferenceName" label="会议名" width="120"> </el-table-column>
            <el-table-column label="签到" width="150">
              <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.confirm === 11">已签到</el-tag>
                    <el-tag type="error" v-else-if="scope.row.confirm === 12">未签到</el-tag>
                    <el-tag type="warning" v-else>状态错误</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  icon="el-icon-edit"
                  @click="showEditDialog(scope.row.id)"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  @click="delReach(scope.row.id)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <el-pagination
          background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20, 40]" 
            :page-size="pagesize"     
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalCount"
          >
        </el-pagination>
      </el-card>

      <!-- 分配角色对话框 -->
      <el-dialog title="提示" :visible.sync="allotDialogVisible" width="30%">
        <el-form ref="form" :model="allotForm" label-width="100px">
          <el-form-item label="当前的用户">
<!--            {{ allotForm.user.username}}-->
          </el-form-item>
          <el-form-item label="当前的角色">
            {{ allotForm.roles }}
          </el-form-item>
          <el-form-item label="设置新的角色">
            <el-select v-model="selectedRoleId">
              <el-option v-for="item in rolesList" :key="item.name" :label="item.roleName" :value="item.name"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="allotDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveRole">确 定</el-button>
        </span>
      </el-dialog>


      <!-- 修改签到对话框 -->
      <el-dialog title="修改签到" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
        <el-form :model="editForm" :rules="editRules" ref="edtFormRef" label-width="100px" class="demo-ruleForm">
          <el-form-item label="账号">
            <el-input v-model="editForm.reachTime" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="签到 ">
              <template slot-scope="scope">
                <el-switch
                    v-model="scope.confirm"
                    :active-value="11"
                    :inactive-value="12"
                    @change="userStateChange($event, row)"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    active-text="已签到"
                    inactive-text="未签到"
                        >
                </el-switch>
              </template>
          </el-form-item>
          <el-form-item label="用户名">
            <el-input v-model="editForm.userName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="会议负责人名">
          <el-input v-model="editForm.staffName"  disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="会议名">
            <el-input v-model="editForm.conferenceName"  disabled="disabled"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editUsersSureBtn">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {

  data:function() {
    // 验证性别的规则
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^['男'|'女']$/
      if (regEmail.test(value)) {
        return callback()
      } else {
        return callback(new Error('请输入合法的性别'))
      }
    }
    // 验证手机的规则
    var checkPhone = (rule, value, callback) => {
      const regMobile = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]14[57])[0-9]{8}$/
      if (regMobile.test(value)) {
        return callback()
      } else {
        return callback(new Error('请输入正确的手机号'))
      }
    }
    return {
      queryInfo: {
        query: '',
        //pagenum: 1,
        //pagesize: 5,
      },
      currentPage:1, //初始页
      pagesize:5, 
      userList: [],
      totalCount: 1,
      addDialogVisible: false,
      editDialogVisible: false,
      allotDialogVisible: false,
      allotForm: '',
      rolesList: '',
      selectedRoleId: '',
      ruleForm: {
        userName: '',
        password: '',
        gender: '',
        phone: '',
      },

      editForm: {},
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        email: [
          { required: true, message: '请输入性别', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' },
        ],
      },
      editRules: {
        email: [
          { required: true, message: '请输入性别', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' },
        ],
      },
      token:localStorage.getItem('token')
    }
  },
  created() {
    this.getReachList()
  },
  methods: {
    //模糊查询
    getUserListByfuzzy(){
      let _this = this;
      this.$axios({
          method:'get',
          url:'user/fuzzy?nickName='+this.queryInfo.query+'&phone='+this.queryInfo.query
      }).then((res) =>{
           _this.userList = res.data
           //alert(_this.userList)
      }).catch(() =>{
          this.$message.error('模糊查询失败')
      })
    },
    //判断性别
    stateFormat(row){
      if(row.gender=='1'){
        return "男";
      }else{
        return "女";
      }
    },
    getReachList() {
      let _this = this;
      this.$axios({
        method:'get',
        url:'reach/findAll',
        // url:'list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
        .then((res) => {
          //alert(JSON.stringify(res.data.data))
          _this.userList = res.data.data
          //this.totalCount = res.data.length
        })
        .catch(() => {
          this.$message.error('获取签到列表失败')
        })
    },
    handleSizeChange: function (size) {
                this.pageSize = size
                this.getReachList()
        },
    handleCurrentChange: function(currentPage){
                this.currentPage = currentPage;
                this.getReachList()
    },
    //分页方法（重点）
   currentChangePage(list,currentPage) { 
      let from = (currentPage - 1) * this.pagesize;
      let to = currentPage * this.pagesize;
      this.tempList = [];
      for (; from < to; from++) {
        if (list[from]) {
          this.tempList.push(list[from]);
        }
      }
    },
    //用户状态改变
    userStateChange($event,row) {
      //alert(JSON.stringify(row))
      this.$axios
      .put('reach/reach_update',this.editForm)
        .then((res) => {
          //alert(JSON.stringify(res.data.data))
          this.$message.success('更新状态成功')
        })
        .catch((res) => {
          this.$message.error(res.msg)
        })
    },
    addDialogClose() {
      this.$refs.formRef.resetFields()
    },
    addUsersSureBtn() {
      this.$refs.formRef.validate((boo) => {
        if (!boo) {
          return
        } else {
          // this.$axios
          this.$axios({
            method:'post',
            data: this.ruleForm,
            // dataType:JSON,
            url:'user/user_insert',
              headers:{
                'token':window.sessionStorage.getItem("token")
              }
          })
            .then(() => {
              this.addDialogVisible = false
              this.getReachList()
              this.$message.success('添加用户成功')
            })
            .catch(() => {
              this.$message.error('添加用户失败')
            })
        }
      })
    },
    //修改按钮
    showEditDialog(id) {
      this.editDialogVisible = true
      this.$axios.get('reach/findOne/' + id).then((res) => {
        this.editForm = res.data.data
        //alert(JSON.stringify(res.data.data))
      })
    },
    editDialogClose() {
      this.$refs.edtFormRef.clearValidate()
    },
    editUsersSureBtn() {
      this.editDialogVisible = false
      this.$refs.edtFormRef.validate((boo) => {
        if (boo) {
          this.$axios
            .put('reach/reach_update/'  , {
              id: this.editForm.id,
              reachTime: this.editForm.reachTime,
              confirm: this.editForm.confirm,
              userName: this.editForm.userName,
              staffName: this.editForm.staffName,
              conferenceName: this.editForm.conferenceName,
            })
            .then(() => {
              this.getReachList()
              this.$message.success('修改成功')
            })
            .catch(() => {
              this.$message.error('修改失败')
            })
        } else {
          return
        }
      })
    },
    // 删除用户
    delReach(id) {
      this.$confirm('此操作将永久删除该项, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('reach/del_reach?id=' + row.id)
            .then(() => {
              this.getReachList()
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
    
    //分配用户角色
    allotUserRole(row) {
      var _this = this;
      // this.$axios.get('power/role_list').then((res) => {
      //   this.rolesList = res.data.data
      // })
      // var _this = this;
      this.$axios({
        method:'get',
        url:'power/role_list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
      .then((res) => {
       _this.rolesList = res.data.data})
      this.allotForm = row
      this.allotDialogVisible = true
    },
    //保存用户选择的新的角色
    saveRole() {
       // let id = this.allotForm.user.id
      // let name = this.selectedRoleId
      if (!this.selectedRoleId) {
        return this.$message.error('请选择新的角色')
      } else {
        /*this.$axios
          .put(`power/user_update/${this.allotForm.id}`, {
            rid: this.selectedRoleId,
          })*/
        this.$axios({
          method:'get',
          url:'power/user_update',
          // data:this.allotForm.user.id 90-,//rid:this.selectedRoleId,
          // dataType:JSON,
          params:{
            id:this.allotForm.user.id,
            name: this.selectedRoleId
          },

          headers:{
            'token':window.sessionStorage.getItem("token")
          }
        })
          .then(() => {
            this.getReachList()
            this.$message.success('修改用户角色成功')
          })
          .catch(() => {
            this.$message.error('修改用户角色失败')
          })
      }
      this.allotDialogVisible = false
    },
  },
}
</script>

<style lang="less" scoped></style>>
