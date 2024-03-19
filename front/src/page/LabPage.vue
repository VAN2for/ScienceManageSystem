<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>研究室表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取研究室信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加研究室</el-button
            >
            <el-input type="text" placeholder="按名字搜索研究室" style="width:350px" v-model="search_name"/><el-button type="success" @click="search">搜索</el-button>
            <!-- 添加的弹窗 -->
            <el-dialog title="添加研究室信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="研究室名" :label-width="formLabelWidth">
                  <el-input
                    v-model="form.labName"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
                <el-form-item label="研究室方向" :label-width="formLabelWidth">
                  <el-input v-model="form.labDescription" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="主任" :label-width="formLabelWidth">
                  <el-select v-model="form.directorid" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="秘书" :label-width="formLabelWidth">
                  <!-- <el-input v-model="form.secretaryname" autocomplete="off"></el-input> -->
                  <el-select v-model="form.secretaryname" placeholder="请选择">
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
                <el-button type="primary" @click="addForm">添 加</el-button
                ><!--dialogFormVisible = false-->
              </div>
            </el-dialog>
          </div>
        </div>
  
        <table class="table captain-top table-hover table-striped">
          <thead>
            <tr>
              <th scope="col">实验室名</th>
              <th scope="col">研究室方向</th>
              <th scope="col">主任</th>
              <th scope="col">秘书</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Lab
              v-for="dir in lab"
              :key="dir.labName"
              :lab="dir"
            ></Lab>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Lab from "../components/ResearchLab.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "LabPage",
    components: {
      Lab,
      SideBar,
    },
    data() {
      return {
        lab: [], //学生信息的json数组
        search_name:"",
        dialogFormVisible: false,
        form: {
          labName: "",
          labDescription: "",
          directorid: null,
          secretaryname: null,
        },
        formLabelWidth: "120px",
        options: [],
        options2:[],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findResearchLab",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          this.lab = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertResearchLab",
          method: "post",
          data: this.form,
        }). then(() => {
        this.dialogFormVisible = false;
        this.getForm();
        this.resetForm(); // 重置表单
      })
      .catch(error => {
        console.log(this.form)
        this.$message.error(error);
      });
      },
  
      resetForm() {
      this.form = { // 重置表单数据
        labName: "",
          labDescription: "",
          directorid: null,
          secretaryname: null,
      };
    },

    search(){
      axios({
        url: "http://localhost:8080/searchLabByName",
        method: "post",
        data:{
          name:this.search_name,
        }
      }).then(res => {
        console.log("res:",res);
        if(res.status==200){
        this.lab = res.data;
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
      EventBus.$on("lab-updated", (data) => {
        this.lab = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/findDirectorName",
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
      url: "http://localhost:8080/findSecretaryName",
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