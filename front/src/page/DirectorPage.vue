<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>负责人表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getDirector"
              >获取负责人信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加负责人</el-button
            >
            <el-input type="text" placeholder="按序号搜索负责人" style="width:350px" v-model="search_name"/><el-button type="success" @click="search">搜索</el-button>
            <el-dialog title="添加负责人信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="负责人号" :label-width="formLabelWidth">
                  <el-input
                    v-model="form.directorNo"
                    autocomplete="off"
                  ></el-input>
                </el-form-item>
                <el-form-item label="任用时间" :label-width="formLabelWidth">
                  <el-date-picker
                    v-model="form.startDate"
                    type="date"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="任期（年）" :label-width="formLabelWidth">
                  <el-input v-model="form.term" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究人员姓名" :label-width="formLabelWidth">
                  <!-- <el-input
                    v-model.number="form.researchername"
                    autocomplete="off"
                  ></el-input> -->
                  <el-select v-model="form.researchername" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addDirector">添 加</el-button
                ><!--dialogFormVisible = false-->
              </div>
            </el-dialog>
          </div>
        </div>
  
        <table class="table captain-top table-hover table-striped">
          <thead>
            <tr>
              <th scope="col">负责人号</th>
              <th scope="col">上任时间</th>
              <th scope="col">任期</th>
              <th scope="col">研究人员姓名</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Director
              v-for="dir in director"
              :key="dir.directorNo"
              :director="dir"
            ></Director>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Director from "../components/Director.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "DirectorPage",
    components: {
        Director,
      SideBar,
    },
    data() {
      return {
        director: [], //学生信息的json数组
        options:[],
        search_name:"",
        dialogFormVisible: false,
        form: {
          directorNo: "",
          startDate: "",
          term: "",
          researchername: null,
        },
        formLabelWidth: "120px",
      };
    },
    methods: {
      getDirector() {
        axios({
          url: "http://localhost:8080/findDirector",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          const originalDate = new Date(this.director.startDate);
          this.director.startDate = originalDate;
          this.director = res.data;
        });
      },
  
      addDirector() {
        axios({
          url: "http://localhost:8080/insertDirector",
          method: "post",
          data: this.form,
        }). then(() => {
        this.dialogFormVisible = false;
        this.getDirector();
        this.resetForm(); // 重置表单
      })
      .catch(error => {
        this.$message.error(error);
      });
      },
  
      resetForm() {
      this.form = { // 重置表单数据
        directorNo: "",
        startDate: "",
        term: "",
        researchername: null,
      };
    },
    search(){
      axios({
        url: "http://localhost:8080/searchDirectorByName",
        method: "post",
        data:{
          name:this.search_name,
        }
      }).then(res => {
        console.log("res:",res);
        if(res.status==200){
        this.director = res.data;
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

    },
    created() {
      EventBus.$on("director-updated", (data) => {
        this.director = data;
      });
    },
  };
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