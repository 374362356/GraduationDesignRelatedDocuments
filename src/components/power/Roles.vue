<template>
  <div>
    <!-- 头部的面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card class="box-card">
      <el-button type="primary" @click="addDialogVisible = true"
        >添加角色</el-button
      >
      <template>
        <el-table :data="tableData" style="width: 100%" stripe border>
          <el-table-column type="expand">
            <template slot-scope="scope">
              <el-row v-for="item1 in scope.row.children" :key="item1.id">
                <el-col :span="6">
                  <el-tag
                    closable
                    @close="removeRightById(scope.row, item1.id)"
                    >{{ item1.authName }}</el-tag
                  >
                  <i class="el-icon-caret-right"></i>
                </el-col>
                <el-col :span="18">
                  <el-row v-for="item2 in item1.children" :key="item2.id">
                    <el-col :span="9">
                      <el-tag
                        type="success"
                        closable
                        @close="removeRightById(scope.row, item2.id)"
                        >{{ item2.authName }}</el-tag
                      >
                      <i class="el-icon-caret-right"></i>
                    </el-col>
                    <el-col :span="15">
                      <el-tag
                        v-for="item3 in item2.children"
                        :key="item3.id"
                        type="warning"
                        closable
                        @close="removeRightById(scope.row, item3.id)"
                        >{{ item3.authName }}</el-tag
                      >
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </template>
          </el-table-column>
          <el-table-column type="index"></el-table-column>
          <el-table-column prop="roleName" label="角色名称"> </el-table-column>
          <el-table-column prop="roleDesc" label="角色概述"> </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="scope">
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="small"
                @click="editRole(scope.row)"
                >编辑</el-button
              >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="small"
                @click="delRole(scope.row)"
                >删除</el-button
              >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="small"
                @click="showSetRightDilog(scope.row)"
                >分配权限</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </template>
    </el-card>
    <!-- 分配权限对话框 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <!-- 树形控件 -->
      <el-tree
        :data="rightsList"
        :props="defaultProps"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="defKeys"
        ref="treeRef"
      ></el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加角色 -->
    <el-dialog title="提示" :visible.sync="addDialogVisible" width="30%">
      <el-form ref="addRoleRef" :model="addRoleForm" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="addRoleForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色概述">
          <el-input v-model="addRoleForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑角色 -->
    <el-dialog title="提示" :visible.sync="editDialogVisible" width="30%">
      <el-form ref="addRoleRef" :model="editRoleForm" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="editRoleForm.roleName"></el-input>
        </el-form-item>
        <el-form-item label="角色概述">
          <el-input v-model="editRoleForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRoleSureBtn">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Roles',
  created() {
    this.getRolesList()
  },
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      rightsList: [],
      defaultProps: {
        children: 'children',
        label: 'authName',
      },
      defKeys: [],
      roleId: 0,
      addDialogVisible: false,
      addRoleForm: {
        roleName: '',
        roleDesc: '',
      },
      editRoleForm: {},
      editDialogVisible: false,
    }
  },
  methods: {
    getRolesList() {
      this.$axios
        .get('/book/findAll')
        .then((res) => {
          this.tableData = res.data.data
        })
        .catch(() => {
          this.$message.error('获取角色列表失败')
        })
    },
    removeRightById(role, rightId) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete(`roles/${role.id}/rights/${rightId}`)
            .then((res) => {
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              // this.getRolesList()
              role.children = res.data.data
            })
            .catch(() => {
              this.$message({
                type: 'error',
                message: '删除失败',
              })
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(() => {
          done()
        })
        .catch(() => {})
    },
    showSetRightDilog(row) {
      //默认选中的三级权限
      this.roleId = row.id
      this.defKeys = []
      row.children.forEach((element) => {
        element.children.forEach((item) => {
          item.children.forEach((subItem) => {
            this.defKeys.push(subItem.id)
          })
        })
      })
      this.dialogVisible = true
      this.$axios
        .get('rights/tree')
        .then((res) => {
          this.rightsList = res.data.data
        })
        .catch(() => {
          this.$message.error('获取权限列表失败')
        })
    },
    // 确定按钮，分配权限
    allotRights() {
      this.dialogVisible = false
      const keys = [
        ...this.$refs.treeRef.getCheckedKeys(),
        ...this.$refs.treeRef.getHalfCheckedKeys(),
      ]
      const keysStr = keys.join(',')
      this.$axios
        .post(`roles/${this.roleId}/rights`, { rids: keysStr })
        .then(() => {
          this.$message.success('修改权限成功')
          this.getRolesList()
        })
        .catch(() => {
          this.$message.error('修改权限失败')
        })
    },
    //删除角色
    delRole(row) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('roles/' + row.id)
            .then(() => {
              this.getRolesList()
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
    //添加角色
    addRole() {
      this.$axios.post('roles', this.addRoleForm).then(() => {
        this.getRolesList()
        this.$message.success('添加角色成功')
      })
      this.addDialogVisible = false
    },
    //编辑角色
    editRole(row) {
      this.editRoleForm = { roleName: row.roleName, roleDesc: row.roleDesc }
      this.editRoleId = row.id
      this.editDialogVisible = true
    },
    //编辑角色确定按钮
    editRoleSureBtn() {
      this.$axios
        .put('roles/' + this.editRoleId, this.editRoleForm)
        .then(() => {
          this.getRolesList()
          this.$message.success('编辑成功')
        })
        .catch(() => {
          this.$$message.error('编辑失败')
        })

      this.editDialogVisible = false
    },
  },
}
</script>

<style lang="less" scoped>
.el-tag {
  margin: 10px 0;
}
.el-row {
  display: flex;
  align-items: center;
}
</style>
