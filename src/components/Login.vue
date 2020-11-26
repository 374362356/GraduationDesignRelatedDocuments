<template>
  <div class="login_bac">
    <div class="box">
      <div class="img">
        <img src="@/assets/img/download.jpg" alt />
      </div>
      <el-form label-width="0px" class="form" :model="form" :rules="rules" ref="loginRef">
        <el-form-item prop="username">
          <el-input prefix-icon="iconfont icon-user" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="iconfont icon-lock_fill" v-model="form.password" type="password"></el-input>
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
export default {
  name: "Login",
  data: function () {
    return {
      form: {
        username: "admin",
        password: "admin",
      },
      rules: {
        username: [
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
              .post("/login",this.form)
              .then((response)=> {
                if(response.data.code==200){
                  this.$message.success('登录成功')
                  window.sessionStorage.setItem('token',response.data.data.token)

                  this.$router.push('/home')
                }else{
                this.$message.error('登录失败')
                }
              })
              .catch((error)=>{
                this.$message.error(error)
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
</style>>
