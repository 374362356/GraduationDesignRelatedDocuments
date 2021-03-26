<template>
  <div>
    <!-- 头部的面包屑 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>会议列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片 -->
    <el-card class="box-card">
      <el-row :gutter="20">
          <el-col :span="6">
              <el-input
              placeholder="根据会议名，举办地，举办方查询"
              v-model="queryInfo.query"
              clearable
              @clear="getConferenceList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getConferenceByfuzzy"
              ></el-button>
            </el-input> 
          </el-col>
          <el-col :span="4"
            >
        <el-button type="primary" @click="addDialogVisible = true">添加会议</el-button>
      </el-col>
    </el-row>
      <template>
        <el-table :data="tableData" style="width: 100%" stripe border>
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="会议ID: ">
                  <span>{{ props.row.id }}</span>
                </el-form-item><br/>
                <el-form-item label="会议简要: ">
                  <span>{{ props.row.name }}</span>
                </el-form-item>     
              </el-form>
            </template>
           
          </el-table-column>
          <el-table-column type="index"></el-table-column>
          <el-table-column prop="id" label="id"> </el-table-column>
          <el-table-column prop="name" label="会议名称"> </el-table-column>
          <el-table-column prop="startTime" label="开始时间"> </el-table-column>
          <el-table-column prop="endTime" label="结束时间"> </el-table-column>
          <el-table-column prop="place" label="举办地点"> </el-table-column>
          <el-table-column prop="host" label="举办方"> </el-table-column>
          <el-table-column prop="product.status" label="状态"> </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="scope">
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="small"
                @click="editConference(scope.row)"
                >编辑</el-button
              >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="small"
                @click="delConference(scope.row)"
                >删除</el-button>

<!--              <el-button-->
<!--                type="warning"-->
<!--                icon="el-icon-setting"-->
<!--                size="small"-->
<!--                @click="showSetRightDilog(scope.row)"-->
<!--
         >分配权限</el-button-->
<!--              >-->
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

    <!-- 添加会议 -->
    <el-dialog title="提示" :visible.sync="addDialogVisible" width="60%">
      <el-form ref="addRoleRef" :model="addConferenceForm" label-width="80px">
        <el-form-item label="会议名称">
          <el-input v-model="addConferenceForm.name"></el-input>
        </el-form-item>
        <!--<el-form-item label="开始时间">
          <el-input v-model="addConferenceForm.startTime"></el-input>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-input v-model="addConferenceForm.endTime"></el-input>
        </el-form-item>-->
        <el-form-item label="开始时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="addConferenceForm.startTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="选择时间" v-model="addConferenceForm.startTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-time-picker>
          </el-col>
        </el-form-item>
         <el-form-item label="结束时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="addConferenceForm.endTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="选择时间" v-model="addConferenceForm.endTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-time-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="举办地点">
          <el-input v-model="addConferenceForm.place"></el-input>
        </el-form-item>
        <el-form-item label="举办方">
          <el-input v-model="addConferenceForm.host"></el-input>
        </el-form-item>
        <el-form-item label="角色概述">
          <el-input v-model="addConferenceForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addConference">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑会议 -->
    <el-dialog title="提示" :visible.sync="editDialogVisible" width="30%">
      <el-form ref="addRoleRef" :model="editConferenceForm" label-width="80px">
        <el-form-item label="会议名称">
          <el-input v-model="editConferenceForm.name"></el-input>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="editConferenceForm.startTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="选择时间" v-model="editConferenceForm.startTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-time-picker>
          </el-col>
        </el-form-item>
         <el-form-item label="结束时间">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="editConferenceForm.endTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="11">
            <el-time-picker placeholder="选择时间" v-model="editConferenceForm.endTime" style="width: 100%;" value-format="yyyy-MM-dd HH:mm:ss"></el-time-picker>
          </el-col>
        </el-form-item>
        <el-form-item label="举办地点">
          <el-input v-model="editConferenceForm.place"></el-input>
        </el-form-item>
        <el-form-item label="举办方">
          <el-input v-model="editConferenceForm.host"></el-input>
        </el-form-item>
        <el-form-item label="角色概述">
          <el-input v-model="editConferenceForm.roleDesc"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editConferenceSureBtn">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Roles',
  created() {
    this.getConferenceList()
  },
  data() {
    return {
      queryInfo: {
        query: '',
        pagenum: 1,
        pagesize: 5,
      },
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
      addConferenceForm: {
        name: '',
        startTime: '',
        endTime: '',
        place: '',
        host: '',
        roleDesc: '',
      },
      editConferenceForm: {},
      editDialogVisible: false,
    }
  },
  methods: {
    getConferenceByfuzzy(){
      let _this = this;
        this.$axios({
          method:'get',
          url:'conference/fuzzy?name='+this.queryInfo.query+'&place='+this.queryInfo.query+'&host='+this.queryInfo.query
        }).then((res)=>{
         _this.tableData = res.data
         //alert(_this.tableData)   
        }).catch(()=>{
          this.$message.error('模糊查询失败')
        })
    },
    getConferenceList() {
      let _this = this;
      this.$axios({
        method:'get',
        url:'conference/findAll',
        // url:'list',
        headers:{
          'token':window.sessionStorage['token']
        }
      }).then((res) => {
          _this.tableData = res.data
          //alert(JSON.stringify(_this.tableData))
        })
              /*
               let _this = this;
      this.$axios({
        method:'get',
        url:'power/user_list',
        // url:'list',
        headers:{
          'token':window.sessionStorage['token']
        }
      })
              */

        .catch(() => {
          this.$message.error('获取会议信息列表失败')
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
          this.getConferenceList()
        })
        .catch(() => {
          this.$message.error('修改权限失败')
        })
    },
    //删除会议
    delConference(row) {
      this.$confirm('此操作将永久删除该会议, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          this.$axios
            .delete('conference/conference_delete?id=' + row.id)
            .then(() => {
              this.getConferenceList()
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
    //添加会议
    addConference() {
      alert(JSON.stringify(this.addConferenceForm))
      this.$axios.post('conference/conference_insert', this.addConferenceForm
      
      ).then(() => {
        this.getConferenceList()
        this.$message.success('添加会议成功')
      })
      this.addDialogVisible = false
    },
    //编辑会议
    editConference(row) {
      this.editConferenceForm = { name: row.name,
                                  startTime: row.startTime,
                                  endTime: row.endTime,
                                  place: row.place,
                                  host: row.host,
                                  roleDesc: row.roleDesc }
      this.conferenceId = row.id
      this.editDialogVisible = true
    },
    //编辑会议确定按钮
    editConferenceSureBtn() {
      this.$axios
        .put('conference/conference_update/' + this.conferenceId, this.editConferenceForm)
        .then(() => {
          this.getConferenceList()
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
