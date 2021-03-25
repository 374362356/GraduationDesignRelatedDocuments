<template>
  <el-container class="home_container">
    <!-- 头部 -->
    <el-header>
      <div>
        <img src alt />
        <span>会议信息管理系统</span>
      </div>
      <div>
        <span >欢迎您，{{userName}}</span>
      </div>
      <div>
        <el-button type="primary">设置</el-button>
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
</template>

<script>
export default {
  name: 'Home',
  data() {
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
            },{
                    authName: "建议管理",
                    children: [] ,
                    id: 121,
                    order: 3,
                    path: "suggestion"
            }],
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
                id: 107,
                order: null,
                path: "upload"
            },
            ],
            id: 102,
            order: 4,
            path: "staff"
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
      },
      isCollapse: false,
      defaultActive: '',
      userName:''
    }
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
        this.userName = localStorage.getItem("userName");
        console.log(err)
      })
  },
  methods: {
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
