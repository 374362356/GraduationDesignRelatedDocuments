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
      <el-card>
        <el-row :gutter="20">
          <el-col :span="9">
            <el-input
              placeholder="请输入内容"
              v-model="queryInfo.query"
              clearable
              @clear="getUserList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getUserList"
              ></el-button>
            </el-input>
          </el-col>
          <el-col :span="4"
            ><el-button type="primary" @click="addDialogVisible = true"
              >添加用户</el-button></el-col>
        </el-row>
        <template>
          <el-table :data="userList" style="width: 100%" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column prop="id" label="id" width="180"></el-table-column>
            <el-table-column prop="username" label="姓名" width="180"></el-table-column>
            <el-table-column prop="gender" label="性别" width="180"></el-table-column>
            <el-table-column prop="phone" label="电话"> </el-table-column>
            <el-table-column prop="role_name" label="角色"> </el-table-column>
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
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="分配角色"
                  placement="top"
                  :enterable="false"
                >
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
          :total="total"
        >
        </el-pagination>
      </el-card>

      <!-- 分配角色对话框 -->
      <el-dialog title="提示" :visible.sync="allotDialogVisible" width="30%">
        <el-form ref="form" :model="allotForm" label-width="100px">
          <el-form-item label="当前的用户">
            {{ allotForm.username }}
          </el-form-item>
          <el-form-item label="当前的角色">
            {{ allotForm.role_name }}
          </el-form-item>
          <el-form-item label="设置新的角色">
            <el-select v-model="selectedRoleId">
              <el-option
                v-for="item in rolesList"
                :key="item.id"
                :label="item.roleName"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="allotDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveRole">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 添加用户对话框 -->
      <el-dialog
        title="添加用户"
        :visible.sync="addDialogVisible"
        width="50%"
        @close="addDialogClose"
      >
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="formRef"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="ruleForm.mobile"></el-input>
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
          <el-form-item label="用户名">
            <el-input v-model="editForm.username" disabled="disabled"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="editForm.mobile"></el-input>
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
  data() {
    // 验证邮箱的规则
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
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
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 5,
      },
      userList: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible: false,
      allotDialogVisible: false,
      allotForm: '',
      rolesList: '',
      selectedRoleId: '',
      ruleForm: {
        username: '',
        password: '',
        email: '',
        mobile: '',
      },
      editForm: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' },
        ],
      },
      editRules: {
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: checkEmail, trigger: 'blur' },
        ],
        mobile: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: checkPhone, trigger: 'blur' },
        ],
      },
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    getUserList() {
      var _this = this;
      this.$axios({
        method:'get',
        url:'user_list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
        .then((res) => {
          _this.userList = res.data.data;
          this.total = res.data.data.total
        })
        .catch(() => {
          this.$message.error('获取用户列表失败')
        })
    },
    handleCurrentChange(newpage) {
      this.queryInfo.pagenum = newpage
      this.getUserList()
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
    addUsersSureBtn() {
      this.$refs.formRef.validate((boo) => {
        if (!boo) {
          return
        } else {
          this.$axios
            .post('users', this.ruleForm)
            .then(() => {
              this.addDialogVisible = false
              this.getUserList()
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
      this.$axios.get('users/' + id).then((res) => {
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
            .put('update/' + this.editForm.id, {
              email: this.editForm.email,
              mobile: this.editForm.mobile,
            })
            .then(() => {
              this.getUserList()
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
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .post("delete/" + id)
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
    },
    //分配用户角色
    allotUserRole(row) {
      this.$axios.get('roles').then((res) => {
        this.rolesList = res.data.data
      })
      this.allotForm = row
      this.allotDialogVisible = true
    },
    //保存用户选择的新的角色
    saveRole() {
      if (!this.selectedRoleId) {
        return this.$message.error('请选择新的角色')
      } else {
        this.$axios
          .put(`users/${this.allotForm.id}/role`, {
            rid: this.selectedRoleId,
          })
          .then(() => {
            this.getUserList()
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
