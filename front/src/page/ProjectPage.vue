<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>科研项目表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm">获取科研项目信息</el-button>
            <el-button type="warning" @click="dialogFormVisible = true">添加科研项目</el-button>
            <el-input type="text" placeholder="按名字搜索项目" style="width:350px" v-model="search_name"/><el-button type="success" @click="search">搜索</el-button>
            <!-- 添加的弹窗 -->
            <el-dialog title="添加科研项目信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="项目号" :label-width="formLabelWidth">
                  <el-input v-model="form.projectNo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目负责人" :label-width="formLabelWidth">
                    <el-select v-model="form.projectLeader" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="项目名称" :label-width="formLabelWidth">
                  <el-input v-model="form.projectName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究内容" :label-width="formLabelWidth">
                  <el-input v-model="form.researchContent" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="经费总额" :label-width="formLabelWidth">
                  <el-input v-model.number="form.totalFunding" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="开始日期" :label-width="formLabelWidth">
                    <el-date-picker v-model="form.startDate" type="date" placeholder="选择开始日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="结束日期" :label-width="formLabelWidth">
                    <el-date-picker v-model="form.completionDate" type="date" placeholder="选择结束日期"></el-date-picker>
                </el-form-item>
                <el-form-item label="委托方名" :label-width="formLabelWidth">
                  <el-select v-model="form.clientName" placeholder="请选择">
                        <el-option
                          v-for="item in options2"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="质量监测方名" :label-width="formLabelWidth">
                  <!-- <el-input v-model="form.secretaryname" autocomplete="off"></el-input> -->
                  <el-select v-model="form.monitorName" placeholder="请选择">
                        <el-option
                          v-for="item in options3"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addForm">添 加</el-button
                ><!--dialogFormVisible = false-->
              </div>
            </el-dialog>
          </div>
        </div>
  
        <table class="table captain-top table-hover table-striped">
          <thead>
            <tr>
              <th scope="col">项目号</th>
              <th scope="col">项目负责人</th>
              <th scope="col">项目名称</th>
              <th scope="col">研究内容</th>
              <th scope="col">经费总额</th>
              <th scope="col">开始日期</th>
              <th scope="col">结束日期</th>
              <th scope="col">委托方名</th>
              <th scope="col">质量监测方名</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Project
              v-for="dir in project"
              :key="dir.projectNo"
              :project="dir"
            ></Project>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Project from "../components/Project.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "ProjectPage",
    components: {
        Project,
      SideBar,
    },
    data() {
      return {
        project: [], //学生信息的json数组
        dialogFormVisible: false,
        search_name:"",
        form: {
            projectNo: "",
        projectLeader: null,
        projectName: "",
        researchContent: "",

        totalFunding: "",
        startDate: "",
        completionDate: "",
        clientName: null,
        monitorName: null,
        },
        formLabelWidth: "120px",
        options: [],
        options2:[],
        options3:[],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findResearchProject",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          const originalDate = new Date(this.project.startDate);
        this.project.startDate = originalDate;
        const originalDate2 = new Date(this.project.completionDate);
        this.project.completionDate = originalDate2;
          this.project = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertResearchProject",
          method: "post",
          data: this.form,
        }). then(() => {
        this.dialogFormVisible = false;
        this.getForm();
        this.resetForm(); // 重置表单
      })
      .catch(error => {
        this.$message.error(error);
      });
      },
  
      resetForm() {
      this.form = { // 重置表单数据
        projectNo: "",
        projectLeader: null,
        projectName: "",
        researchContent: "",

        totalFunding: "",
        startDate: "",
        completionDate: "",
        clientName: null,
        monitorName: null,
      };
    },

    search(){
      axios({
        url: "http://localhost:8080/searchProjectByName",
        method: "post",
        data:{
          name:this.search_name,
        }
      }).then(res => {
        console.log("res:",res);
        if(res.status==200){
        this.project = res.data;
        this.$message({
              type: 'success',
              message: '查询成功!'
            })
         }else{
           this.$message.error(res.msg);
         }
        })
      .catch(error => {
        this.$message.error(error);
      });
    },
    },
    created() {
      EventBus.$on("project-updated", (data) => {
        this.project = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/findResearcherName",
      method: "GET",
    })
      .then(response => {
        const data = response.data;
        console.log(data);

        this.options=data;
        console.log(this.options)

      })
      .catch(error => {
        console.error(error);
      });

      axios({
      url: "http://localhost:8080/findClientName",
      method: "GET",
    })
      .then(response => {
        const data = response.data;
        console.log(data);

        this.options2=data;
        console.log(this.options2)

      })
      .catch(error => {
        console.error(error);
      });

      axios({
      url: "http://localhost:8080/findQualityMonitorName",
      method: "GET",
    })
      .then(response => {
        const data = response.data;
        console.log(data);

        this.options3=data;
        console.log(this.options3)

      })
      .catch(error => {
        console.error(error);
      });



      },
    }
 
  </script>
    
    <style>
  #app {
    display: flex;
    justify-content: space-between;
  }
  
  .sidebar {
    width: 200px;
  }
  
  .content {
    flex: 1;
    height: 100vh;
    width: 80vw;
  }
  
  .table {
    width: 100%;
  }
  
  .el-menu {
    height: 100%;
  }
  
  .el-menu-item-group,
  .el-submenu__title {
    display: flex;
    align-items: center;
  }
  .table-caption {
    text-align: center;
    font-size: 1.2em;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: column;
    align-content: center;
  }
  </style>