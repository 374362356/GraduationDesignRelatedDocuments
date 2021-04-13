<template>
    <div>
      <el-container class="home_container">
        <!-- 头部 -->
        <el-header>
          <div>
            <img src alt />
            <span>数学与计算机学院会议信息管理系统</span>
          </div>
          <div>
            <span >欢迎您，{{username}}</span>
          </div>
          <div>
            <el-button type="primary" @click="fitDialogVisible = true">设置</el-button>
            <el-button type="info" @click="logout">退出</el-button>
          </div>
        </el-header>
        <!-- 内容 -->
        <el-container>
          <!-- 侧边栏 -->
          <el-aside :width="isCollapse ? '64px' : '200px'">
            <div class="toggle-button" @click="toggleCollapse">|||</div>
            <el-menu
              background-color="#333744"
              text-color="#fff"
              active-text-color="#2020ff"
              unique-opened
              :collapse="isCollapse"
              :collapse-transition="false"
              router
              :default-active="defaultActive"
            >
              <el-submenu
                :index="item.id + ''"
                v-for="item in asideList"
                :key="item.id"
              >
                <template slot="title">
                  <i :class="fontsobj[item.id]"></i>
                  <span>{{ item.authName }}</span>
                </template>
                <el-menu-item
                  :index="'/' + subitem.path"
                  v-for="subitem in item.children"
                  :key="subitem.id"
                  @click="secondaryMenu('/' + subitem.path)"
                >
                  <i class="el-icon-menu"></i>
                  <span>{{ subitem.authName }}</span>
                </el-menu-item>
              </el-submenu>
            </el-menu>
          </el-aside>
          <!-- 主要内容 -->
          <el-main><router-view></router-view></el-main>
        </el-container>
      </el-container>

    <el-dialog title="设置信息" :visible.sync="fitDialogVisible" width="50%">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="nickName">
          <el-input v-model="ruleForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
            <el-radio v-model="ruleForm.gender" label="1">男</el-radio>
            <el-radio v-model="ruleForm.gender" label="0">女</el-radio>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkpassword">
          <el-input type="password" v-model="ruleForm.checkpassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    // 验证手机的规则
      var checkPhone = (rule, value, callback) => {
        const regMobile = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]14[57])[0-9]{8}$/
        if (regMobile.test(value)) {
          return callback()
        } else {
          return callback(new Error('请输入正确的手机号'))
        }
      }
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkpassword !== '') {
            this.$refs.ruleForm.validateField('checkpassword');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
      asideList:[
            {
            authName:"用户管理",
            children:[{
                    authName:"用户列表",
                    children:[],
                    id:110,
                    order:null,
                    path:"users"
             },{
               authName:"参会人员列表",         /* 修改*/ 
                    children:[],
                    id:101,
                    order:null,
                    path:"person"
             }],
            id: 125,
            order:1,
            path:"users"

      },

            {
              authName:"权限管理",
              children:[{
                  authName:"角色列表",
                    children:[],
                    id:111,
                    order:null,
                    path:"roles"},
                {
                    authName:"权限列表",
                    children:[],
                    id:112,
                    order:null,
                    path:"rights"}],
              id:103,
              order:2,
              path:"rights"},
        {
          authName:"信息管理",
          children:[
                {
                    authName:"会议列表",
                    children:[],
                    id:104,
                    order:1,
                    path:"conference"
            },{
                    authName: "公告管理",
                    children: [],
                    id: 115,
                    order: 2,
                    path: "news"
            },],
                id: 101,
                order: 3,
                path: "conference"
        },
        {
            authName:"工作信息管理",
            children:[
            {
                authName: "工作人员列表",
                children: [],
                id: 107,
                order: null,
                path: "staff"
            },
            {
                authName: "会议文件列表",
                children: [],
                id: 108,
                order: null,
                path: "upload"
            },
            ],
            id: 102,
            order: 4,
            path: "staff"
        },
        {
            authName:"参会信息管理",
            children:[
            {
                authName: "签到列表",
                children: [],
                id: 109,
                order: null,
                path: "reach1"
            },
            {
                authName: "建议管理",
                children: [] ,
                id: 121,
                order: 3,
                path: "suggestion"
            },],
            id: 119,
            order: 4,
            path: "reach1"
        },
        {
            authName:"数据统计",
            children: [{
                authName: "数据报表类型1",
                children: [],
                id: 146,
                order: null,
                path:"reports"
            },
            {
                authName: "数据报表类型2",
                children: [],
                id: 147,
                order: null,
                path:"reports2"
            }],
            id:145,
            order:5,
            path:"reports"
        }
      ],
      fontsobj: {
        '125': 'iconfont icon-users',
        '103': 'iconfont icon-tijikongjian',
        '101': 'iconfont icon-3702mima',
        '102': 'iconfont icon-danju',
        '145': 'iconfont icon-baobiao',
        '119': 'iconfont icon-danju',
      },
      isCollapse: false,
      defaultActive: '',
      id:'',
      username:'',
      fitDialogVisible: false,
      ruleForm: {
          id: '',
          nickName:'',
          gender: '',
          password: '',
          checkpassword: '',
          phone: ''
        },
        rules: {
          nickName: [
            { required: true,message: '请输入名称', trigger: 'blur' },
            { min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: 'blur' }
          ],
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkpassword: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          phone: [
            { message: '请输入手机号码', trigger: 'blur' },
            { validator: checkPhone, trigger: 'blur' },
          ],
        }
      };
  },
  created() {
    this.$axios
      .get('menus')
      .then((res) => {
        
        if (res.data.meta.status === 200) {
          this.asideList = res.data.data
        }
      })
      .catch((err) => {
        //取出浏览器里的用户名
        //alert(localStorage.getItem("userName"))
        this.username = localStorage.getItem("username");
        console.log(err)
      }),
      this.getUserInfo()
  },
  methods: {
    //设置回显用户信息
    getUserInfo(){
      let _that = this;
      this.$axios({
        method: 'get',
        url: 'user/getUserInfo?username='+localStorage.getItem("username"),
      }).then((res)=>{
        _that.ruleForm = res.data.data;
        //alert(_that.ruleForm)
      })
      .catch(()=>{
          this.$message.error('回显信息失败')
          //alert(this.userName)
      })
    },
    logout() {
      window.sessionStorage.removeItem('token')
      this.$router.push('/login')
    },
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    secondaryMenu(path) {
      this.defaultActive = path
    },
    //修改信息
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.
              put('user/update',{       //  15189263748
                id: this.ruleForm.id,
                nickName: this.ruleForm.nickName,
                password: this.ruleForm.password,
                gender: this.ruleForm.gender,
                phone: this.ruleForm.phone
            }).then(()=>{
              this.$message.success('修改信息成功')
              this.logout();
            })
            .catch(()=>{
              this.$message.error('修改信息失败')
            })

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
    resetForm(formName) {
        this.$refs[formName].resetFields();
      }
  },
}
</script>

<style lang="less" scoped>
.home_container {
  width: 100%;
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  font-size: 16px;
  align-items: center;
  color: #fff;
}
.el-aside {
  background-color: #333744;
  .toggle-button {
    background-color: #4a5064;
    font-size: 10px;
    text-align: center;
    color: #fff;
    cursor: pointer;
    line-height: 24px;
  }
  i {
    margin-right: 10px;
  }
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: #eaedf1;
}
.el-header button:first-child{
    margin-right: 2px;
}
</style>
