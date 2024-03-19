<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>办公场地表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取办公场地信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加办公场地</el-button
            >
            <!-- 添加的弹窗 -->
            <el-dialog title="添加办公场地信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="场地ID" :label-width="formLabelWidth">
                  <el-input v-model.number="form.spaceId" autocomplete="off"></el-input>
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
                <el-form-item label="地址" :label-width="formLabelWidth">
                  <el-input v-model="form.workAddress" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="办公面积" :label-width="formLabelWidth">
                  <el-input v-model.number="form.workArea" autocomplete="off"></el-input>
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
              <th scope="col">场地ID</th>
              <th scope="col">所属研究室</th>
              <th scope="col">地址</th>
              <th scope="col">办公面积</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <OfficeSpace
              v-for="dir in officeSpace"
              :key="dir.spaceId"
              :officeSpace="dir"
            ></OfficeSpace>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import OfficeSpace from "../components/OfficeSpace.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "OfficeSpacePage",
    components: {
        OfficeSpace,
      SideBar,
    },
    data() {
      return {
        officeSpace: [], //学生信息的json数组
        dialogFormVisible: false,
        form: {
            spaceId: "",
            labName: null,
            workAddress: "",
            workArea: "",
        },
        formLabelWidth: "120px",
        options: [],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findOfficeSpace",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          this.officeSpace = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertOfficeSpace",
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
        spaceId: "",
        labName: null,
        workAddress: "",
        workArea: "",
      };
    },
    },
    created() {
      EventBus.$on("officeSpace-updated", (data) => {
        this.officeSpace = data;
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