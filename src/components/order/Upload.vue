<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>工作信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>会议文件列表</el-breadcrumb-item>
    </el-breadcrumb>
        <el-card class="box-card">
          <el-row :gutter="20">
              <el-col :span="7">
                  <el-input
                  placeholder="根据文件名查询"
                  v-model="queryInfo.query"
                  clearable
                  @clear="getFilesList"
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="getFilesByfuzzy"
                    ></el-button>
                  </el-input> 
                </el-col>
              <el-col :span="4"
                >
           </el-col>
          </el-row>
          <el-table :data="files" style="width: 100%" stripe border>
            <el-table-column type="index"></el-table-column>
            <el-table-column prop="id" label="编号"  width="80"></el-table-column>
            <el-table-column prop="fileName" label="文件名"  width="120"></el-table-column>
            <el-table-column prop="fileUrl" label="文件路径" width="280"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"  width="180"></el-table-column>
                <el-table-column label="操作" width="300">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" size="small" @click="editFile(scope.row)">
                            编辑</el-button>
                        <el-button type="danger" icon="el-icon-delete" size="small" @click="delFile(scope.row)">
                            删除</el-button>
                        <el-button type="success" icon="el-icon-delete" size="small" @click="downloadFile(scope.row)">
                            下载</el-button> 
                    </template>
                </el-table-column>
          </el-table>
        <el-form :model="ruleForm"
                enctype="multipart/form-data"
                :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="任务名称:" prop="name">
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
              action=""
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
    </el-card>

      <el-dialog title="下载文件" :visible.sync="downloadVisible" width="40%">
            <el-link>{{fileLink}}</el-link>
      </el-dialog>

      <!-- 编辑文件 -->
       <el-dialog title="提示" :visible.sync="editDialogVisible" width="30%">
           <el-form ref="addFileRef" :model="editFileForm" :rules="editRules" label-width="80px">
               <el-form-item label="文件名称">
                   <el-input v-model="editFileForm.fileName"></el-input>
               </el-form-item>
               <el-form-item label="文件地址" prop="url">
                   <el-input v-model="editFileForm.fileUrl" disabled></el-input>
               </el-form-item>
               <el-form-item label="创建时间" prop="createTime">
                   <el-input v-model="editFileForm.createTime" disabled></el-input>
               </el-form-item>
           </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="editFileSureBtn">确 定</el-button>
            </span>
        </el-dialog>
  </div>
</template>

<script>
export default {
  name: "fileTask",
  data() {
    return {
      queryInfo: {
          query: '',
      },
      offset: 0,
      limit: 6,
      fileList: [],
      fileLink:'',
      editFileForm:{},
      ruleForm: {
        fileName: '',
        date1: [new Date().getTime(), new Date().setTime(new Date().getTime()+3600 * 1000 * 24)],
      },
      files:[],   //回显已上传的文件
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
      editDialogVisible:false,
      downloadVisible:false,
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
    //上传
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$refs.upload.submit();
          let _this = this;
          _this.uploadForm.append("fileName",_this.ruleForm.fileName);
        //   _this.uploadForm.append("taskCreateDate",_this.ruleForm.date1[0]);
        //   _this.uploadForm.append("taskCommitDate",_this.ruleForm.date1[1]);
          _this.$axios.post("upload/file_upload",_this.uploadForm)
          .then((res)=>{
               this.getFilesList()
               this.$message.success('添加成功!');        
          }).catch(()=>{
                this.$message.error('添加失败!');
          })
        } else {
            console.log('error submit!!');
            return false;
        }
      });
    },
    //下载
    downloadFile(row){
     this.fileLink = row.fileUrl;
     //alert(this.fileLink)
      this.downloadVisible=true;
    },
    getFilesList(){
      let _this = this;
      this.$axios({
        method:'get',
        url:'upload/findFiles?offset='+this.offset+'&limit='+this.limit,
        headers:{
          'token':window.sessionStorage['token']
        }
      })
        .then((res) => {
          _this.files = res.data.data;
          //alert(JSON.stringify(_this.files))
        })
        .catch(() => {
          this.$message.error('获取文件列表失败')
        })
    },
    //模糊查询
    getFilesByfuzzy(){
        let _this = this;
        this.$axios({
            method:'get',
            url:'upload/fuzzyFile?name='+this.queryInfo.query
        }).then((res) =>{
            _this.files = res.data.data;
            //alert(JSON.stringify(_this.files))
        }).catch(() =>{
            this.$message.error('模糊查询失败')
        })
    },
      //删除
      delFile(row) {                                              //七牛删除失败
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        })
        .then(() => {
            this.$axios
                .delete('upload/file_delete?id=' + row.id)
                .then(() => {
                    this.getFilesList()
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
    //编辑人员
      editFile(row) {
            this.editFileForm = {   id: row.id,
                                    fileName: row.fileName, 
                                    fileUrl: row.fileUrl, 
                                    createTime: row.createTime
                                    }
            this.id = row.id
            this.editDialogVisible = true
        },
        //编辑人员确定按钮
        editFileSureBtn() {
            this.$axios
                .put('upload/file_update', this.editFileForm)
                .then(() => {
                    this.getFilesList()
                    this.$message.success('编辑成功')
                })
                .catch(() => {
                    this.$$message.error('编辑失败')
                })
            this.editDialogVisible = false
        },
  },
  created(){
    this.getFilesList()
  }
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
