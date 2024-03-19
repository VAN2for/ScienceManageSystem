<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>科研人员表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取科研人员信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加科研人员</el-button
            >
            <!-- 添加的弹窗 -->
            <el-dialog title="添加科研人员信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="工号" :label-width="formLabelWidth">
                  <el-input v-model="form.researcherNo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="所属研究室" :label-width="formLabelWidth">
                  <el-select v-model="form.labName" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="姓名" :label-width="formLabelWidth">
                  <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="性别" :label-width="formLabelWidth">
                <el-radio v-model="form.gender" label="男">男</el-radio>
                <el-radio v-model="form.gender" label="女">女</el-radio>
              </el-form-item>
                <el-form-item label="职称" :label-width="formLabelWidth">
                  <el-input v-model="form.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="年龄" :label-width="formLabelWidth">
                  <el-input v-model.number="form.age" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="研究方向" :label-width="formLabelWidth">
                  <el-input v-model="form.researchDirection" autocomplete="off"></el-input>
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
              <th scope="col">工号</th>
              <th scope="col">所属研究室</th>
              <th scope="col">姓名</th>
              <th scope="col">性别</th>
              <th scope="col">职称</th>
              <th scope="col">年龄</th>
              <th scope="col">研究方向</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Researcher
              v-for="dir in researcher"
              :key="dir.researcherNo"
              :researcher="dir"
            ></Researcher>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Researcher from "../components/Researcher.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "ResearcherPage",
    components: {
        Researcher,
      SideBar,
    },
    data() {
      return {
        researcher: [], //学生信息的json数组
        dialogFormVisible: false,
        form: {
            researcherNo: "",
            labName: null,
            name: "",
            gender: "",
            title:"",
            age: "",
            researchDirection: "",
        },
        formLabelWidth: "120px",
        options: [],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findResearcher",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          this.researcher = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertResearcher",
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
        researcherNo: "",
            labName: null,
            name: "",
            gender: "",
            title:"",
            age: "",
            researchDirection: "",
      };
    },
    },
    created() {
      EventBus.$on("researcher-updated", (data) => {
        this.researcher = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/findResearchLabName",
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
    }


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