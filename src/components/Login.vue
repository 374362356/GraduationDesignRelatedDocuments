<template>
  <div class="login_bac">
    <div class="box">
      <div class="img">
        <img src="@/assets/img/rose.jpg" alt />
      </div>
      <el-form label-width="0px" class="form" :model="form" :rules="rules" ref="loginRef">
        <el-form-item prop="userName">
          <el-input prefix-icon="iconfont icon-user" v-model="form.userName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="iconfont icon-lock_fill" v-model="form.password" type="password"></el-input>
        </el-form-item>
        <!-- 角色下拉选择-->
        <el-form-item prop="roles">
            <el-select v-model="value" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
        </el-form-item>
        <el-form-item class="itemBtn">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
//import {getNewLists, choiceOthers} from '../../../api/api'
export default {
  name: "Login",
  data: function () {
    return {
      form: {
        userName: "admin",
        password: "admin",
      },
      //headers:{'Access-Control-Expose-Headers':'token'},
      options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }],
        value: '',
      rules: {
        userName: [
          { required: true, message: "请输用户名称", trigger: "blur" },
          {
            min: 2,
            max: 10,
            message: "长度在 2 到 10 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入用户密码", trigger: "blur" },
          {
            min: 4,
            max: 12,
            message: "长度在 6 到 12 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    reset() {
      this.$refs.loginRef.resetFields();
    },
    login() {
         this.$axios
              .post("login",this.form)
              .then((res)=> {
                if(res.data.code===200){
                  this.$message.success('登录成功')
                  window.localStorage.setItem('token',res.data.data.token)
                  window.localStorage.setItem('userName',this.form.userName)
                  alert(res.data.data.token)

                  this.$router.push('/home')
                }else{
                this.$message.error('登录失败')
                }
              })
              .catch(()=>{
                this.$message.error("Error!")
              });
     /* this.$refs.loginRef.validate((boo) => {
        if (!boo) return;

      });*/
    },
  },
};
</script>

<style lang="less" scoped>

.login_bac {
    width: 100%;
    height: 100%;
  background-color: #4b6b8b;
}
.box {
  width: 500px;
  height: 300px;
  background-color: #fff;
  position: absolute;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  margin: auto;
}

.img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 1px solid #eee;
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
}

.img img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.form {
  width: 100%;
  padding: 0 20px;
  position: absolute;
  bottom: 0;
  box-sizing: border-box;
}

.itemBtn {
  display: flex;
  justify-content: flex-end;
}
</style>