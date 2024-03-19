<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>项目参与人员表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取参与人员信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加参与人员</el-button
            >
            <el-dialog title="添加参与人员信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="参与人名字" :label-width="formLabelWidth">
                    <el-select v-model="form.paticipantName" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="工作量" :label-width="formLabelWidth">
                  <el-input v-model.number="form.workHour" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="参与时间" :label-width="formLabelWidth">
                  <el-date-picker
                    v-model="form.joinTime"
                    type="date"
                    placeholder="选择日期"
                  >
                  </el-date-picker>
                </el-form-item>
                <el-form-item label="可分配经费" :label-width="formLabelWidth">
                  <el-input v-model.number="form.money" autocomplete="off"></el-input>
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
              <th scope="col">项目参与人员姓名</th>
              <th scope="col">工作量</th>
              <th scope="col">参与时间</th>
              <th scope="col">可分配经费</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Paticipant
              v-for="dir in paticipant"
              :key="dir.paticipantName"
              :paticipant="dir"
            ></Paticipant>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Paticipant from "../components/Participant.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "PaticipantPage",
    components: {
        Paticipant,
      SideBar,
    },
    data() {
      return {
        paticipant: [], //学生信息的json数组
        options:[],
        dialogFormVisible: false,
        form: {
            paticipantName: "",
            workHour: "",
            joinTime: "",
            money: "",
        },
        formLabelWidth: "120px",
      };
    },
    methods: {
        getForm() {
        axios({
          url: "http://localhost:8080/findPaticipant",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          const originalDate = new Date(this.paticipant.joinTime);
          this.paticipant.joinTime = originalDate;
          this.paticipant = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertPaticipant",
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
        paticipantName: "",
        workHour: "",
        joinTime: "",
        money: "",
      };
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
      EventBus.$on("paticipant-updated", (data) => {
        this.paticipant = data;
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