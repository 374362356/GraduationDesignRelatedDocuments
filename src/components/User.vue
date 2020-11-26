<template>
  <div class="about">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="审批人">
        <el-input v-model="formInline.user" placeholder="审批人"></el-input>
      </el-form-item>
      <el-form-item label="活动区域">
        <el-select v-model="formInline.region" placeholder="活动区域">
          <el-option label="区域一" value="shanghai"></el-option>
          <el-option label="区域二" value="beijing"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        formInline: {
          user: '',
          region: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
		const _this = this;
		
		_this.$axios.post("login",{"id":"1200", "name":"wu", "address": "chengdu"}).then(
			function(res){
				console.log("111");
				const jwt = res.headers['authorization'];
				console.log(jwt); // jwt 利用Vuex状态管理，存储jwt，store/index.js
				_this.$store.commit('SET_TOKEN', jwt);
			}
		)
      }
    }
  }
</script>