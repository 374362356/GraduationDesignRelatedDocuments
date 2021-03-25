<template>
  <div>
    <el-form :model="ruleForm"
             enctype="multipart/form-data"
             :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="任务名称" prop="name">
        <el-input  v-model="ruleForm.fileName"></el-input>
      </el-form-item>
      <!-- <el-form-item label="时间设置" required prop="date1">
        <el-col :span="11">
            <el-date-picker
              v-model="ruleForm.date1"
              type="datetimerange"
              :picker-options="pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              >
            </el-date-picker>
        </el-col>
      </el-form-item> -->
      <el-form-item label="相关文件上传:" prop="region">
        <el-upload
          class="upload-demo"
          ref="upload"
          action="http://localhost:8001/teacherTask/create/"
          :file-list="fileList"
          :auto-upload="false"
          :on-change="changFile"
          :on-remove="onRemove"
          :http-request="uploadFile"
          :limit="1"
          :on-exceed="handleExceed">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="submitForm('ruleForm')">上传到服务器</el-button>
      </el-form-item>
        <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
    </el-form>
  </div>
</template>

<script>
export default {
  name: "fileTask",
  data() {
    return {
      fileList: [],
      ruleForm: {
        fileName: '',
        date1: [new Date().getTime(), new Date().setTime(new Date().getTime()+3600 * 1000 * 24)],
      },
      pickerOptions: {
        shortcuts: [
          {
            text: '一天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 );
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          },
          {
            text: '一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          },
          {
          text: '三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            end.setTime(start.getTime() + 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      rules: {
        fileName: [
          {required: true, message: '请输入文件名称', trigger: 'blur'},
        ],
      },
      uploadForm:new FormData(),
    };
  },
  methods: {
    uploadFile(file) {
      this.uploadForm.append('files', file.file);
    },
    onRemove(file, fileList) {
      this.fileList = fileList
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    changFile(file, fileList) {
      let existFile = fileList.slice(0, fileList.length - 1).find(f => f.name === file.name);
      if (existFile) {
        this.$message.error('当前文件已经存在!');
        fileList.pop();
      }
      this.fileList = fileList;
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$refs.upload.submit();
          let _this = this;
          _this.uploadForm.append("fileName",_this.ruleForm.fileName);
        //   _this.uploadForm.append("taskCreateDate",_this.ruleForm.date1[0]);
        //   _this.uploadForm.append("taskCommitDate",_this.ruleForm.date1[1]);
          _this.$axios.post("upload/fileUpload",_this.uploadForm)
          .then(function (res){
            if (res.data.msg === "成功") {
              _this.$router.push({
                path: '/Home/taskFind'
              });
              this.$message.success('添加成功!');
            }else {
              this.$message.error('添加失败!');
            }
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  },
}
</script>

<style scoped>
.container-fluid{
  border: 1px solid #DCDFE6;
  margin: 20px auto;
  padding: 50px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 25px #909399;
}
</style>
