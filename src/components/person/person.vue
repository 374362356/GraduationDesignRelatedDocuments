<template>
  <div>
    <h1>参会人员列表页面</h1>
    <!-- 面包屑区域 -->
    <div class="header">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>人员列表</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="main">
      <!-- 搜索栏 -->
      <el-card>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input
              placeholder="根据人员名，会议名，电话查询"
              v-model="queryInfo.query"
              clearable
              @clear="getPersonList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getPersonListByfuzzy"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="4"
            ><el-button type="primary" @click="addDialogVisible = true"
              >添加用户</el-button></el-col>
        </el-row>
        <template>
          <el-table :data="personList" style="width: 100%" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column prop="id" label="id" width="80"></el-table-column>
            <el-table-column prop="name" label="会议名" width="120"></el-table-column>
            <el-table-column prop="pname" label="姓名" width="100"></el-table-column>
            <el-table-column prop="age" label="年龄" width="80"></el-table-column>
            <el-table-column prop="gender" label="性别" width="80" :formatter="stateFormat"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="140"></el-table-column>
            <el-table-column prop="phone" label="电话" width="120"></el-table-column>
            <el-table-column prop="title" label="职称" width="120"></el-table-column>
            <!-- <el-table-column prop="roles" label="角色"></el-table-column> -->
            <el-table-column label="状态">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.mg_state" @change="userStateChange(scope.row)"></el-switch>
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
        <el-pagination
          background
          layout="prev, pager, next"
          :page-size="queryInfo.pagesize"
          @current-change="handleCurrentChange"
          :total="total">
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

      <!-- 添加用户对话框 -->
     <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
        <el-form :model="ruleForm" :rules="rules" ref="formRef" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="pname">
            <el-input v-model="ruleForm.pname"></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="ruleForm.age"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio v-model="ruleForm.gender" label="1">男</el-radio>
            <el-radio v-model="ruleForm.gender" label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="ruleForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="职称" prop="title">
            <el-input v-model="ruleForm.title"></el-input>
          </el-form-item>
          <el-form-item label="参与会议" prop="name">
            <el-select v-model="ruleForm.name" placeholder="请选择" filter-method="SelectOption">
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
          <el-button type="primary" @click="addPersonSureBtn">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 修改用户对话框 -->
      <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
        <el-form :model="editForm" :rules="editRules" ref="edtFormRef" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="pname">
            <el-input v-model="editForm.pname" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="editForm.age"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="gender">
            <el-radio v-model="editForm.gender" label="1">男</el-radio>
            <el-radio v-model="editForm.gender" label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="editForm.phone"></el-input>
          </el-form-item>
          <el-form-item label="职称" prop="title">
            <el-input v-model="editForm.title"></el-input>
          </el-form-item>
          <el-form-item label="参与会议" prop="name">
            <el-select v-model="editForm.name" placeholder="请选择" filter-method="SelectOption">
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
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editPersonSureBtn">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {

  data:function() {
    // 验证邮箱的规则
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (regEmail.test(value)) {
        return callback()
      } else {
        return callback(new Error('请输入合法的邮箱'))
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
      options: [
        // {
        //   value: '选项1',
        //   label: '黄金糕'
        // }, {
        //   value: '选项2',
        //   label: '双皮奶',
        //   disabled: true
        // }
      ],
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 5,
      },
      personList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      allotDialogVisible: false,
      allotForm: '',
      rolesList: '',
      selectedRoleId: '',
      ruleForm: {
        pname: '',
        age: '',
        gender: '',
        email: '',
        phone: '',
        title: '',
        name: ''
      },

      editForm: {},
      rules: {
        pname: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' },
        ],
      },
      editRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请选择会议', trigger: 'blur' },
        ],
      },
      token:localStorage.getItem('token')
    }
  },
  created() {
    this.getPersonList()
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
    //模糊查询
    getPersonListByfuzzy(){
      let _this = this;
      this.$axios({
          method:'get',
          url:'person/fuzzy?pname='+this.queryInfo.query+'&name='+this.queryInfo.query+'&phone='+this.queryInfo.query
      }).then((res) =>{
           _this.personList = res.data.data
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
    getPersonList() {
      let _this = this;
      this.$axios({
        method:'get',
        url:'person/findAll',
        // url:'list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
        .then((res) => {
          _this.personList = res.data.data;
          //alert(JSON.stringify(_this.personList))
          this.total = res.data.data.total
        })
        .catch(() => {
          this.$message.error('获取人员列表失败')
        })
    },
    handleCurrentChange(newpage) {
      this.queryInfo.pagenum = newpage
      this.getPersonList()
    },
    userStateChange(userinfo) {
      this.$axios
        .put(`users/${userinfo.id}/state/${userinfo.mg_state}`)
        .then(() => {
          this.$message.success('更新状态成功')
        })
        .catch((res) => {
          userinfo.mg_state = !userinfo.mg_state
          this.$message.error(res.meg)
        })
    },
    addDialogClose() {
      this.$refs.formRef.resetFields()
    },
    addPersonSureBtn() {
      this.$refs.formRef.validate((boo) => {
        if (!boo) {
          return
        } else {
          // this.$axios
          this.$axios({
            method:'post',
            data: this.ruleForm,
            // dataType:JSON,
            url:'person/person_insert',
              headers:{
                'token':window.sessionStorage.getItem("token")
              }
          })
          //    .get('power/user_insert', this.ruleForm)
            .then(() => {
              alert(this.ruleForm)
              this.addDialogVisible = false
              this.getPersonList()
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
      this.$axios.get('person/toUpdate?id=' + id).then((res) => {
        this.editForm = res.data
        //alert(JSON.stringify(this.editForm))
      })
    },
    editDialogClose() {
      this.$refs.edtFormRef.clearValidate()
    },
    editPersonSureBtn() {
      this.editDialogVisible = false
      this.$refs.edtFormRef.validate((boo) => {
        if (boo) {
          this.$axios
            .put('person/person_update/'  , {
              id: this.editForm.id,
              pname: this.editForm.pname,
              age: this.editForm.age,
              gender: this.editForm.gender,
              email: this.editForm.email,
              phone: this.editForm.phone,
              title: this.editForm.title,
              name: this.editForm.name
            })
            .then(() => {
              this.getPersonList()
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
    delUser(id) {
        this.$confirm('此操作将永久删除该会议, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('person/person_delete?id=' + row.id)
            .then(() => {
              this.getPersonList()
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
            this.getPersonList()
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
