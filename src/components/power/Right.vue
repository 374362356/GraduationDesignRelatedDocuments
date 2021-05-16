<template>
    <div>
        <!-- 面包屑导航区域 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>权限管理</el-breadcrumb-item>
            <el-breadcrumb-item>权限列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 卡片视图 -->
        <el-card>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-input
                    placeholder="根据权限名或父路径查询"
                    v-model="queryInfo.query"
                    clearable
                    @clear="getRightsList"
                    >
                    <el-button
                        slot="append"
                        icon="el-icon-search"
                        @click="getPermissionByfuzzy"
                    ></el-button>
                    </el-input> 
                </el-col>
                <el-col :span="6"
                    >
                 <el-button type="primary" @click="addDialogVisible = true"
                            >添加权限</el-button>
                </el-col>
            </el-row>    
            <el-table :data="rightsList.slice((currentPage-1)*PageSize,currentPage*PageSize)" border stripe>
                <el-table-column label="编号" prop="id" width="80"></el-table-column>
                <el-table-column label="权限名称" prop="pname" width="140"></el-table-column>
                <el-table-column label="创建时间" prop="createTime" width="150"></el-table-column>
                <el-table-column label="路径" prop="url" width="280"></el-table-column>
                <el-table-column label="状态" prop="status" width="110">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.status === 0">路径冻结</el-tag>
                        <el-tag type="success" v-else-if="scope.row.status === 1">路径启用</el-tag>
                        <el-tag type="warning" v-else>无状态</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="父路径" prop="path" width="140"></el-table-column>
                <el-table-column label="权限等级" prop="level" width="100">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.level === 0">一级</el-tag>
                        <el-tag type="success" v-else-if="scope.row.level === 1">二级</el-tag>
                        <el-tag type="warning" v-else>三级</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                    <el-button
                        type="primary"
                        icon="el-icon-edit"
                        size="small"
                        @click="editPermission(scope.row)"
                        >编辑</el-button
                    >
                    <el-button
                        type="danger"
                        icon="el-icon-delete"
                        size="small"
                        @click="delPermission(scope.row)"
                        >删除</el-button
                    >
                    </template>
                </el-table-column>
            </el-table>

            <!-- 添加权限 -->
            <el-dialog title="提示" :visible.sync="addDialogVisible" width="30%">
                <el-form ref="addRightRef" :model="addPermissionForm" :rules="rules" label-width="80px">
                    <el-form-item label="权限名称" prop="pname">
                    <el-input v-model="addPermissionForm.pname"></el-input>
                    </el-form-item>
                    <el-form-item label="权限路径" prop="url">
                    <el-input v-model="addPermissionForm.url"></el-input>
                    </el-form-item>
                    <el-form-item label="父路径">
                    <el-input v-model="addPermissionForm.path"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="addDialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addPermission">确 定</el-button>
                </span>
            </el-dialog>

            <!-- 编辑权限 -->
            <el-dialog title="提示" :visible.sync="editDialogVisible" width="30%">
                <el-form ref="addPermissionRef" :model="editPermissionForm" :rules="rules" label-width="80px">
                    <el-form-item label="权限名称" prop="pname">
                        <el-input v-model="editPermissionForm.pname"></el-input>
                    </el-form-item>
                    <el-form-item label="权限路径" prop="url">
                        <el-input v-model="editPermissionForm.url"></el-input>
                    </el-form-item>
                    <el-form-item label="父路径">
                        <el-input v-model="editPermissionForm.path"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="editPerSureBtn">确 定</el-button>
                </span>
            </el-dialog>
        </el-card>
        <div>
            <el-pagination @size-change="handleSizeChange" 
                            @current-change="handleCurrentChange" 
                            :current-page="currentPage" 
                            :page-sizes="pageSizes" 
                            :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper" 
                            :total="totalCount">
               </el-pagination>
          </div>
    </div>
</template>

<script>export default {
    data() {
        return {
            offset: 0,
            limit: 20,
            queryInfo: {
                query: '',
            },
            currentPage:1, //初始页
            PageSize:6,
            userList: [],
            totalCount: 1,
            pageSizes:[5,10,20],
            addPermissionForm:{
                pname:'',
                url:'',
                path:''
            },
            // 权限列表
            rightsList: [],
            addDialogVisible: false,
            editDialogVisible: false,
            editPermissionForm: {},
            rules: {
            pname: [
                { required: true, message: '请输入权限名', trigger: 'blur' },
            ],
            url: [
                { required: true, message: '请输入权限路径', trigger: 'blur' },
            ],
      },
        }
    },
    created() {
        // 获取所有的权限
        this.getRightsList()
    },
    methods: {
        editPermission(row){
            this.editPermissionForm = {
                                        id: row.id, 
                                        pname: row.pname, 
                                        url: row.url, 
                                        path: row.path}
            //this.editRoleId = row.id
            this.editDialogVisible = true
        },
        editPerSureBtn(){
          this.$axios
            .put('permission/permission_update',this.editPermissionForm)            
            .then((res)=>{  
                this.getRightsList()
                this.$message.success('编辑成功')
            }).catch(()=>{
                this.$$message.error('编辑失败')
            })
            this.editDialogVisible = false;
        },
        getPermissionByfuzzy(){
            let _this = this;
                this.$axios({
                method:'get',
                url:'permission/fuzzy?pName='+this.queryInfo.query+'&path='+this.queryInfo.query
                }).then((res)=>{
                _this.rightsList = res.data
                //alert(_this.tableData) 
                }).catch(()=>{
                this.$message.error('模糊查询失败')
             })
        },
        addPermission(){
          this.$axios.post('permission/permission_insert', this.addPermissionForm)
            .then((res) => {
                if(res.data.code==400){
                    this.$message.error('添加失败，无此权限')
                }else{
                    this.getRightsList()
                    this.$message.success('添加权限成功')
                }
            }).catch(()=>{
                this.$message.error('添加权限失败')
            })
            this.addDialogVisible = false
        },
        // 获取权限列表
         getRightsList() {
            let _this = this;
                  this.$axios({
                    method:'get',
                    url:'permission/permission_list?offset='+this.offset+'&limit='+this.limit,
                    // url:'list',
                    headers:{
                      'token':window.sessionStorage['token']
                    }
                  }).then((res) => {
                      this.totalCount = res.data.data.length
                      _this.rightsList = res.data.data
                      if(res.data.code == '400'){
                        this.$message.error(res.data.msg)
                      }else
                      if (res.data.code !== '200') {
                          return this.$message.error('获取权限列表失败！')
                      }
                  }).catch(() => {
                          this.$message.error('获取权限列表失败')
                })
        },
        delPermission(row){
            this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
            .then(() => {
            this.$axios
                .delete('permission/permission_del?id=' + row.id)
                .then(() => {
                this.getRightsList()
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
    }
}
</script>

<style lang="less" scoped>
</style>
