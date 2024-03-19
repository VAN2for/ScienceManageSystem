<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>子课题表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取子课题信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加子课题信息</el-button
            >
            <!-- 添加的弹窗 -->
            <el-dialog title="添加子课题" :visible.sync="dialogFormVisible">
              <el-form :model="form">
              <el-form-item label="子课题序号" :label-width="formLabelWidth">
                <el-input v-model="form.subprojectId" autocomplete="off"></el-input>
              </el-form-item>
                <el-form-item label="所属项目ID" :label-width="formLabelWidth">
                  <el-select v-model="form.projectNo" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="子课题负责人" :label-width="formLabelWidth">
                  <el-select v-model="form.subprojectLeader" placeholder="请选择">
                        <el-option
                          v-for="item in options2"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
              <el-form-item label="完成时间要求" :label-width="formLabelWidth">
                <el-date-picker v-model="form.completionDate" type="date" placeholder="选择日期"></el-date-picker>
              </el-form-item>
              <el-form-item label="可支配经费" :label-width="formLabelWidth">
                <el-input v-model.number="form.availableFunding" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="技术指标" :label-width="formLabelWidth">
                <el-input v-model="form.technicalIndicators" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addForm">添 加</el-button>
              </div>
            </el-dialog>
          </div>
        </div>
  
        <table class="table captain-top table-hover table-striped">
          <thead>
            <tr>
              <th scope="col">子课题序号</th>
              <th scope="col">所属项目ID</th>
              <th scope="col">子课题负责人</th>
              <th scope="col">完成时间要求</th>
              <th scope="col">可支配经费</th>
              <th scope="col">技术指标</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <SubProject
              v-for="dir in subProject"
              :key="dir.projectId"
              :subProject="dir"
            ></SubProject>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import SubProject from "../components/SubProject.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "SubProjectPage",
    components: {
        SubProject,
      SideBar,
    },
    data() {
      return {
        subProject: [], //学生信息的json数组
        dialogFormVisible: false,
        form: {
            subprojectId: "",
            projectNo: "",
            subprojectLeader: "",
            completionDate: "",
            availableFunding: "",
            technicalIndicators: "",
        },
        formLabelWidth: "120px",
        options: [],
        options2:[],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findSubProject",
          method: "GET",
        }).then((res) => {
          const originalDate = new Date(this.subProject.completionDate);
          this.subProject.completionDate = originalDate;
          this.subProject = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertSubProject",
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
        subprojectId: "",
        projectNo: "",
        subprojectLeader: "",
        completionDate: "",
        availableFunding: "",
        technicalIndicators: "",
      };
    },
    },
    created() {
      EventBus.$on("subProject-updated", (data) => {
        this.subProject = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/getProjectId",
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
      url: "http://localhost:8080/getPaticipantName",
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