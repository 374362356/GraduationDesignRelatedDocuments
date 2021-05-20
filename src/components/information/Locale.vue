<template>
    <div>
      <h1>地点列表页面</h1>
      <!-- 面包屑区域 -->
      <div class="header">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>信息管理</el-breadcrumb-item>
          <el-breadcrumb-item>地点列表</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="main">
        <!-- 搜索栏 -->
        <el-card>
          <el-row :gutter="20">
            <el-col :span="6">
              <el-input
                placeholder="根据地点名查询"
                v-model="queryInfo.query"
                clearable
                @clear="getLocaleList"
              >
                <el-button
                  slot="append"
                  icon="el-icon-search"
                  @click="getLocaleListByfuzzy"
                ></el-button>
              </el-input>
            </el-col>
            <el-col :span="4"
              ><el-button type="primary" @click="addDialogVisible = true"
                >添加地点</el-button></el-col>
          </el-row>
          <template>
            <el-table :data="localeList.slice((currentPage-1)*PageSize,currentPage*PageSize)" style="width: 100%" border stripe>
              <el-table-column type="index"></el-table-column>
              <el-table-column prop="id" label="id" width="90"></el-table-column>
              <el-table-column prop="places" label="地点名" width="120"></el-table-column>
              <el-table-column prop="quantity" label="容纳数量" width="100"></el-table-column>
              <el-table-column prop="employ" label="状态" width="120">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.employ === 22">占用中</el-tag>
                    <el-tag type="success" v-else-if="scope.row.employ === 33">未占用</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="conferId" label="会议ID" width="100"></el-table-column>
              <!-- <el-table-column label="状态">
                <template slot-scope="scope">
                  <el-switch v-model="scope.row.mg_state" @change="userStateChange(scope.row)"></el-switch>
                </template>
              </el-table-column> -->
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
                    @click="delLocale(scope.row.id)"
                  ></el-button>
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
  
        <!-- 添加地点对话框 -->
       <el-dialog title="添加地点" :visible.sync="addDialogVisible" width="50%" @close="addDialogClose">
          <el-form :model="ruleForm" :rules="rules" ref="formRef" label-width="100px" class="demo-ruleForm">
            <el-form-item label="地点" prop="places">
              <el-input v-model="ruleForm.places"></el-input>
            </el-form-item>
            <el-form-item label="容纳数量" prop="quantity">
              <el-input v-model="ruleForm.quantity"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="employ">
              <el-radio v-model="ruleForm.employ" label="22">占用</el-radio>
              <el-radio v-model="ruleForm.employ" label="33">未占用</el-radio>
            </el-form-item>
            <el-form-item label="会议ID" prop="confer_id">
              <el-input v-model="ruleForm.conferId"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="addDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addLocaleSureBtn">确 定</el-button>
          </span>
        </el-dialog>
  
        <!-- 修改用户对话框 -->
        <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClose">
          <el-form :model="editForm" :rules="editRules" ref="edtFormRef" label-width="100px" class="demo-ruleForm">
            <el-form-item label="地点" prop="places">
              <el-input v-model="editForm.places" disabled="disabled"></el-input>
            </el-form-item>
            <el-form-item label="容纳数量" prop="quantity">
              <el-input v-model="editForm.quantity"></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="employ">
              <el-radio v-model="editForm.employ" label="22">占用</el-radio>
              <el-radio v-model="editForm.employ" label="33">未占用</el-radio>
            </el-form-item>
            <el-form-item label="会议ID" prop="confer_id">
              <el-input v-model="editForm.conferId"></el-input>
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
        currentPage:1, //初始页
        PageSize:5,
        userList: [],
        totalCount: 1,
        pageSizes:[5,10,20],
        localeList: [],
        total: 0,
        addDialogVisible: false,
        editDialogVisible: false,
        allotDialogVisible: false,
        allotForm: '',
        rolesList: '',
        selectedRoleId: '',
        ruleForm: {
          places: '',
          quantity: '',
          employ: '',
          conferId: ''
        },
  
        editForm: {},
        rules: {
          places: [
            { required: true, message: '请输入地点', trigger: 'blur' },
          ],
          quantity: [{ required: true, message: '请输入数量', trigger: 'blur' }],
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
          places: [
            { required: true, message: '请输入地点', trigger: 'blur' }
          ],
          quantity: [
            { required: true, message: '请输入数量', trigger: 'blur' }
          ],
          name: [
            { required: true, message: '请选择会议', trigger: 'blur' },
          ],
        },
        token:localStorage.getItem('token')
      }
    },
    created() {
      this.getLocaleList()
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
      getLocaleListByfuzzy(){
        let _this = this;
        this.$axios({
            method:'get',
            url:'locale/fuzzyLocale?places='+this.queryInfo.query
        }).then((res) =>{
             _this.localeList = res.data.data
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
      getLocaleList() {
        let _this = this;
        this.$axios({
          method:'get',
          url:'locale/findAll',
          // url:'list',
          headers:{
            'token':window.sessionStorage['token']
          }
        })
          .then((res) => {
            _this.localeList = res.data.data;
            //alert(JSON.stringify(_this.personList))
            this.totalCount = res.data.data.length
          })
          .catch(() => {
            this.$message.error('获取地点列表失败')
          })
      },
      handleCurrentChange(newpage) {
        this.queryInfo.pagenum = newpage
        this.getLocaleList()
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
      addLocaleSureBtn() {
        this.$refs.formRef.validate((boo) => {
          if (!boo) {
            return
          } else {
            // this.$axios
            this.$axios({
              method:'post',
              data: this.ruleForm,
              // dataType:JSON,
              url:'locale/locale_insert',
                headers:{
                  'token':window.sessionStorage.getItem("token")
                }
            })
              .then(() => {
                alert(JSON.stringify(this.ruleForm))
                this.addDialogVisible = false
                this.getLocaleList()
                this.$message.success('添加地点成功')
              })
              .catch(() => {
                this.$message.error('添加地点失败')
              })
          }
        })
      },
      //修改按钮
      showEditDialog(id) {
        this.editDialogVisible = true
        this.$axios.get('locale/toUpdate?id=' + id).then((res) => {
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
              .put('locale/locale_update/' , {
                id: this.editForm.id,
                places: this.editForm.places,
                quantity: this.editForm.quantity,
                employ: this.editForm.employ,
                conferId: this.editForm.conferId
              })
              .then(() => {
                this.getLocaleList()
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
      delLocale(id) {
          this.$confirm('此操作将永久删除该地点, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
        .then(() => {
          this.$axios
            .delete('locale/locale_del?id=' + id)
            .then(() => {
              this.getLocaleList()
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
              this.getLocaleList()
              this.$message.success('修改用户角色成功')
            })
            .catch(() => {
              this.$message.error('修改用户角色失败')
            })
        }
        this.allotDialogVisible = false
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
  
  <style lang="less" scoped></style>>
  