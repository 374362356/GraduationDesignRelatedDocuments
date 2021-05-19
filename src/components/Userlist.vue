<template>
  <div>
    <h1>用户列表页面</h1>
    <!-- 面包屑区域 -->
    <div class="header">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
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
              @clear="getUserList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getUserListByfuzzy"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="4"
            ><el-button type="primary" @click="addDialogVisible = true"
              >添加用户</el-button></el-col>
        </el-row>
        <template>
          <el-table :data="userList.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="nickName" label="用户名" width="100"></el-table-column>
            <el-table-column prop="userName" label="账号" width="120"></el-table-column>
            <el-table-column prop="gender" label="性别" width="80" :formatter="stateFormat"></el-table-column>
            <el-table-column prop="phone" label="电话" width="130"> </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="160"> </el-table-column>
            <el-table-column prop="description" label="角色" width="100"></el-table-column>
            <el-table-column label="状态" width="140">
              <template slot-scope="scope">
                <!-- <el-switch v-model="value" @change="userStateChange(scope.row.id)"></el-switch> -->
                <el-switch
                    v-model="scope.row.status"
                    :active-value="1"
                    :inactive-value="-1"
                    @change="userStateChange($event, scope.row)"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                    active-text="活跃"
                    inactive-text="冻结"
                        >
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="210">
              <template slot-scope="scope">
                <el-button
                  type="primary"
                  icon="el-icon-edit"
                  @click="showEditDialog(scope.row.id)"
                ></el-button>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  @click="delUser(scope.row.id)"
                ></el-button>
                <el-tooltip class="item" effect="dark" content="分配角色" placement="top" :enterable="false">
                  <el-button
                    type="warning"
                    icon="el-icon-setting"
                    @click="allotUserRole(scope.row)"
                  ></el-button>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <div>
          <el-pagination @size-change="handleSizeChange" 
                          @current-change="handleCurrentChange" 
                          :current-page="currentPage" 
                          :page-sizes="pageSizes" 
                          :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper" 
                          :total="totalCount">
             </el-pagination>
        </div>
      </el-card>

      <!-- 分配角色对话框 -->
      <el-dialog title="提示" :visible.sync="allotDialogVisible" width="30%">
        <el-form ref="form" :model="allotForm" label-width="100px">
          <el-form-item label="当前的用户">
           {{ allotForm.userName }}
          </el-form-item>
          <el-form-item label="当前的角色">
            {{ allotForm.description }}
          </el-form-item>
          <el-form-item label="设置新的角色">
            <el-select v-model="selectedRoleId">
              <el-option v-for="item in rolesList" :key="item.rname" :label="item.rname" :value="item.rname"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="allotDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveRole">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 添加用户对话框 -->
     <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
        <el-form :model="ruleForm" :rules="rules" ref="formRef" label-width="100px" class="demo-ruleForm">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="ruleForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio v-model="ruleForm.gender" label="1">男</el-radio>
            <el-radio v-model="ruleForm.gender" label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="ruleForm.phone"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addUsersSureBtn">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 修改用户对话框 -->
      <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
        <el-form :model="editForm" :rules="editRules" ref="edtFormRef" label-width="100px" class="demo-ruleForm">
          <el-form-item label="账号">
            <el-input v-model="editForm.userName" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="nickName">
            <el-input v-model="editForm.nickName"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="editForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio v-model="editForm.gender" label="1">男</el-radio>
            <el-radio v-model="editForm.gender" label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="editForm.phone"></el-input>
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
      PageSize:6,
      userList: [],
      totalCount: 1,
      pageSizes:[5,10,20],
      addDialogVisible: false,
      editDialogVisible: false,
      allotDialogVisible: false,
      allotForm: '',
      rolesList:'',
      selectedRoleId: '',
      ruleForm: {
        userName: '',
        password: '',
        gender: '',
        phone: '',
      },

      roleform: {
        user_id: '',
        role_id: ''
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
    this.getUserList()
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
    getUserList() {
      let _this = this;
      this.$axios({
        method:'get',
        url:'user/findAll',
        // url:'list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
        .then((res) => {
          //alert(JSON.stringify(res.data))
          //_this.userList = res.data.data;
          _this.userList = res.data
          this.totalCount = res.data.length
          //alert(_this.userList)
        })
        .catch(() => {
          this.$message.error('获取用户列表失败')
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

    //用户状态改变
    userStateChange($event,row) {
      //alert(JSON.stringify(row))
      this.$axios
      .put('user/update',row)
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
          //    .get('power/user_insert', this.ruleForm)
            .then((res) => {
              this.addDialogVisible = false
              this.getUserList()
              if(res.data.code==200){
                this.$message.success('添加用户成功')
              }else{
                this.$message.error(res.data.msg)
              }
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
      this.$axios.get('user/toUpdate/' + id).then((res) => {
        this.editForm = res.data.data
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
            .put('user/update/'  , {
              id: this.editForm.id,
              nickName: this.editForm.nickName,
              password: this.editForm.password,
              gender: this.editForm.gender,
              phone: this.editForm.phone,
            })
            .then((res) => {
              this.getUserList()
              if(res.data.code ==200){
                this.$message.success('修改成功')
              }else{
                this.$message.error(res.data.msg)
              }
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
    delUser(id) {
      this.$confirm('此操作将永久删除该会议, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('user/user_delete?id=' + row.id)
            .then(() => {
              this.getUserList()
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
    // 删除用户
     /*delUser(id) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          // this.$axios
            this.$axios({

              method:'get',
              url:'user/user_delete?id='+id,
              // url:'list',
              // data:id,
              headers:{
                'token':window.sessionStorage['token']
              }
            })
          // console.log(id+'12w1321312312321')
            // .get("power/user_delete?id=8")
            // .get('delete/'+id)
            .then(() => {
              this.getUserList()
              this.$message.success('删除成功')
            })
            .catch(() => {
              this.$message.error('删除失败')
            })
        })
        .catch(() => {
          this.$message.info('已取消删除')
        })
    },*/
    //分配用户角色
    allotUserRole(row) {
      var _this = this;
      // this.$axios.get('power/role_list').then((res) => {
      //   this.rolesList = res.data.data
      // })
      this.$axios({
        method:'get',
        url:'role/role_list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
      .then((res) => {
        _this.rolesList = res.data.data
        this.findRole(row.id);
        //alert(JSON.stringify(row))

      })
      this.allotDialogVisible = true
      this.allotForm = row
      this.roleform = {
              user_id: row.id,
              role_id: this.selectedRoleId
      }
    },
    //保存用户选择的新的角色
    saveRole() {
      if (!this.selectedRoleId) {
        return this.$message.error('请选择新的角色')
      } else {
          this.$axios
          .put('role/updateRole', this.roleform={
             user_id: this.roleform.user_id,
             role_id: this.selectedRoleId
          })
          .then((res) => {
            //alert(JSON.stringify(this.roleform))
            if(res.data.code==200){
              this.getUserList()
              this.$message.success('修改用户角色成功')
            }
            else{
              this.$message.error(res.data.msg)
            }
          })
          .catch(() => {
            this.$message.error('修改用户角色失败')
          })
      }
      this.allotDialogVisible = false
    },
    findRole(row) {
      var _this = this;
      this.$axios({
        method:'get',
        url:'role/findRole?userId='+row,
        headers:{
          'token':window.sessionStorage['token']
        }
      })
      .then((res) => {
        _this.selectedRoleId = res.data.data
        //alert(JSON.stringify(_this.selectedRoleId))
      })
    },
  },
}
</script>

<style lang="less" scoped></style>>
