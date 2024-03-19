<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>项目与科研人员关联表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取项目与科研人员关联信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加项目与科研人员关联信息</el-button
            >
            <!-- 添加的弹窗 -->
            <el-dialog title="添加研究室信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="项目ID" :label-width="formLabelWidth">
                  <el-select v-model="form.projectId" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="参与项目科研人员" :label-width="formLabelWidth">
                  <el-select v-model="form.participantName" placeholder="请选择">
                        <el-option
                          v-for="item in options2"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
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
              <th scope="col">项目ID</th>
              <th scope="col">参与项目科研人员</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <PartAndProject
              v-for="dir in partAndProject"
              :key="dir.projectId"
              :partAndProject="dir"
            ></PartAndProject>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import PartAndProject from "../components/PartAndProject.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "PartAndProjectPage",
    components: {
        PartAndProject,
      SideBar,
    },
    data() {
      return {
        partAndProject: [], //学生信息的json数组
        dialogFormVisible: false,
        form: {
            projectId: "",
            participantName: "",
        },
        formLabelWidth: "120px",
        options: [],
        options2:[],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findPaticipantAndProject",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          this.partAndProject = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertPaticipantAndProject",
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
        projectId: "",
        participantName: "",
      };
    },
    },
    created() {
      EventBus.$on("partAndProject-updated", (data) => {
        this.partAndProject = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/findResearchProjectName",
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
      url: "http://localhost:8080/findPaticipantName",
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