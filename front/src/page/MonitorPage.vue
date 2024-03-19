<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>质量监测方表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm">获取质量监测方信息</el-button>
            <el-button type="warning" @click="dialogFormVisible = true">添加质量监测方</el-button>
            <el-input type="text" placeholder="按名字搜索监测方" style="width:350px" v-model="search_name"/><el-button type="success" @click="search">搜索</el-button>
            <!-- 添加的弹窗 -->
            <el-dialog title="质量监测方信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="质量监测方名" :label-width="formLabelWidth">
                  <el-input v-model="form.monitorName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" :label-width="formLabelWidth">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="负责人" :label-width="formLabelWidth">
                    <el-input v-model="form.responsiblePerson" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="负责人电话" :label-width="formLabelWidth">
                    <el-input v-model.number="form.responsiblePhone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人" :label-width="formLabelWidth">
                    <el-input v-model.number="form.contactPerson" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="联系人电话" :label-width="formLabelWidth">
                    <el-input v-model.number="form.contactPhone" autocomplete="off"></el-input>
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
              <th scope="col">质量监测方名</th>
              <th scope="col">地址</th>
              <th scope="col">负责人</th>
              <th scope="col">负责人电话</th>
              <th scope="col">联系人</th>
              <th scope="col">联系人电话</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Monitor
              v-for="dir in monitor"
              :key="dir.monitorName"
              :monitor="dir"
            ></Monitor>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Monitor from "../components/Monitor.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "MonitorPage",
    components: {
        Monitor,
      SideBar,
    },
    data() {
      return {
        monitor: [], //学生信息的json数组
        search_name:"",
        dialogFormVisible: false,
        form: {
            monitorName: "",
            address: "",
            responsiblePerson: "",
            responsiblePhone: "",
            contactPerson: "",
            contactPhone: "",
        },
        formLabelWidth: "120px",
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findQualityMonitor",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          this.monitor = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertQualityMonitor",
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
        monitorName: "",
        address: "",
        responsiblePerson: "",
        responsiblePhone: "",
        contactPerson: "",
        contactPhone: "",
      };
    },

    search(){
      axios({
        url: "http://localhost:8080/searchMonitorName",
        method: "post",
        data:{
          name:this.search_name,
        }
      }).then(res => {
        console.log("res:",res);
        if(res.status==200){
        this.monitor = res.data;
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
      EventBus.$on("monitor-updated", (data) => {
        this.monitor = data;
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