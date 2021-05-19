<template>
    <div>
        <!-- 头部的面包屑 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>工作信息管理</el-breadcrumb-item>
            <el-breadcrumb-item>工作人员列表</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片 -->
        <el-card class="box-card">
            <el-col :span="7">
              <el-input
              placeholder="根据人名，邮箱，电话，职称查询"
              v-model="queryInfo.query"
              clearable
              @clear="getStaffList"
            >
              <el-button
                slot="append"
                icon="el-icon-search"
                @click="getStaffByfuzzy"
              ></el-button>
            </el-input> 
          </el-col>
          <el-col :span="4"
            >
        <el-button type="primary" @click="addDialogVisible = true">添加人员</el-button>
      </el-col>
            <template>
                <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%" stripe border>
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
                    <!-- <el-table-column type="index"></el-table-column> -->
                    <el-table-column prop="id" label="编号"  width="80"></el-table-column>
                    <el-table-column prop="sname" label="人员姓名"  width="100"></el-table-column>
                    <el-table-column prop="gender" label="性别" width="70" :formatter="stateFormat"></el-table-column>
                    <el-table-column prop="email" label="邮箱"  width="150"></el-table-column>
                    <el-table-column prop="phone" label="电话"  width="140"></el-table-column>
                    <el-table-column prop="position" label="职位"  width="100"></el-table-column>
                    <el-table-column prop="conferenceName" label="负责的会议名"  width="100"></el-table-column>

                    <!-- <el-table-column label="状态" prop="status" width="100">
                        <template slot-scope="scope">
                            <el-tag v-if="scope.row.leave === 8">请假</el-tag>
                            <el-tag type="success" v-else-if="scope.row.leave === 9">工作</el-tag>
                            <el-tag type="warning" v-else>错误状态</el-tag>
                        </template>
                    </el-table-column> -->
                    <el-table-column label="状态" width="140">
                        <template slot-scope="scope">
                          <el-switch
                              v-model="scope.row.leave"
                              :active-value="9"
                              :inactive-value="8"
                              @change="userStateChange($event, scope.row)"
                              active-color="#13ce66"
                              inactive-color="#ff4949"
                              active-text="工作"
                              inactive-text="请假"
                                  >
                          </el-switch>
                        </template>
                      </el-table-column>

                    <el-table-column label="操作" width="300">
                        <template slot-scope="scope">
                            <el-button type="primary" icon="el-icon-edit" size="small" @click="editStaff(scope.row)">
                                编辑</el-button>
                            <!-- 删除按钮 -->
                            <el-button type="danger" icon="el-icon-delete" size="small" @click="delStaff(scope.row)">
                                删除</el-button>
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
                <div>
                    <el-pagination @size-change="handleSizeChange" 
                                    @current-change="handleCurrentChange" 
                                    :current-page="currentPage" 
                                    :page-sizes="pageSizes" 
                                    :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper" 
                                    :total="totalCount">
                    </el-pagination>
                </div>
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

       <!-- 添加人员-->
       <el-dialog title="提示" :visible.sync="addDialogVisible" width="30%">
           <el-form ref="addRoleRef" :model="addStaffForm" :rules="rules" label-width="80px">
               <el-form-item label="人员名称">
                   <el-input v-model="addStaffForm.sname"></el-input>
               </el-form-item>
               <el-form-item label="人员性别">
                    <el-radio v-model="addStaffForm.gender" label="1">男</el-radio>
                    <el-radio v-model="addStaffForm.gender" label="0">女</el-radio>
               </el-form-item>
               <el-form-item label="人员邮箱" prop="email">
                   <el-input v-model="addStaffForm.email"></el-input>
               </el-form-item>
               <el-form-item label="人员电话" prop="phone">
                   <el-input v-model="addStaffForm.phone"></el-input>
               </el-form-item>
               <el-form-item label="人员职称">
                   <el-input v-model="addStaffForm.position"></el-input>
               </el-form-item>
               <el-form-item label="负责会议">
                   <el-select v-model="addStaffForm.conferenceName" placeholder="请选择" filter-method="SelectOption">
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
            <el-button type="primary" @click="addStaff">确 定</el-button>
          </span>
       </el-dialog>

        <!-- 编辑人员 -->
       <el-dialog title="提示" :visible.sync="editDialogVisible" width="30%">
           <el-form ref="addRoleRef" :model="editStaffForm" :rules="editRules" label-width="80px">
               <el-form-item label="人员名称">
                   <el-input v-model="editStaffForm.sname"></el-input>
               </el-form-item>
               <el-form-item label="人员性别">
                    <el-radio v-model="editStaffForm.gender" label="1">男</el-radio>
                    <el-radio v-model="editStaffForm.gender" label="0">女</el-radio>
               </el-form-item>
               <el-form-item label="人员邮箱" prop="email">
                   <el-input v-model="editStaffForm.email"></el-input>
               </el-form-item>
               <el-form-item label="人员电话" prop="phone">
                   <el-input v-model="editStaffForm.phone"></el-input>
               </el-form-item>
               <el-form-item label="人员职称">
                   <el-input v-model="editStaffForm.position"></el-input>
               </el-form-item>
               <el-form-item label="负责会议" prop='name'>
                   <el-select v-model="editStaffForm.conferenceName" placeholder="请选择" filter-method="SelectOption">
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
                <el-button type="primary" @click="editStaffSureBtn">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'Roles',
        created() {
            this.getStaffList()
            this.SelectOption()
        },
        data() {
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
                offset: 0,
                limit: 6,
                queryInfo: {
                    query: '',
                },
                currentPage:1, //初始页
                PageSize:6,
                totalCount: 1,
                pageSizes:[5,10,20],
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
                addStaffForm: {
                    sname: '',
                    gender: '',
                    email:'',
                    phone:'',
                    position:'',
                    conferenceName:''
                },
                editStaffForm: {},
                editDialogVisible: false,
                rules: {
                    userName: [
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
        methods: {
            getStaffByfuzzy(){
              let _this = this;
                this.$axios({
                    method:'get',
                    url:'staff/fuzzy?name='+this.queryInfo.query+'&email='+this.queryInfo.query+'&phone='+this.queryInfo.query+'&position='+this.queryInfo.query
                }).then((res) =>{
                    _this.tableData = res.data
                    //alert(_this.tableData)
                }).catch(() =>{
                    this.$message.error('模糊查询失败')
                })
            },
            getStaffList() {
                let _this = this;
                this.$axios({
                    method:'get',
                    url:'staff/findAll',
                    // url:'list',
                    headers:{
                        'token':window.sessionStorage['token']
                    }
                }).then((res) => {
                    //alert(JSON.stringify(res.data.data))
                    _this.tableData = res.data.data
                    this.totalCount = res.data.data.length
                })
                .catch(() => {
                    this.$message.error('获取人员列表失败')
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
            SelectOption(){
                let _that = this;
                    this.$axios({
                    methods: 'get',
                    url: 'person/selectAllName'
                    }).then((res)=>{
                    _that.options = res.data.data;
                    }).catch(()=>{
                    this.$message.error('获取会议列表失败')
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
                        this.getStaffList()
                    })
                    .catch(() => {
                        this.$message.error('修改权限失败')
                    })
            },
            //删除人员
            delStaff(row) {
                this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                })
                .then(() => {
                    this.$axios
                        .delete('staff/staff_delete?id=' + row.id)
                        .then(() => {
                            this.getStaffList()
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
            //添加人员
            addStaff() {
                this.$axios.post('staff/staff_insert', this.addStaffForm).then(() => {
                    this.$message.success('添加人员成功')
                    this.getStaffList()
                })
                .catch(()=>{
                    this.$message.error('添加人员失败')
                })
                this.addDialogVisible = false
            },
            //编辑人员
            editStaff(row) {
                this.editStaffForm = { id: row.id,
                                       sname: row.sname, 
                                       gender: row.gender, 
                                       email: row.email,
                                       phone: row.phone,
                                       position: row.position,
                                       conferenceName:row.conferenceName
                                       }
                this.id = row.id
                this.editDialogVisible = true
            },
            //编辑人员确定按钮
            editStaffSureBtn() {
                this.$axios
                    .put('staff/staff_update', this.editStaffForm)
                    .then(() => {
                        this.getStaffList()
                        this.$message.success('编辑成功')
                    })
                    .catch(() => {
                        this.$$message.error('编辑失败')
                    })

                this.editDialogVisible = false
            },
             //用户状态改变
    userStateChange($event,row) {
        alert(JSON.stringify(row))
      this.$axios
      .put('staff/staff_update',row)
        .then((res) => {
          alert(JSON.stringify(res.data.data))
          this.$message.success('更新状态成功')
        })
        .catch((res) => {
          this.$message.error(res.msg)
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
