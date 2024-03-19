<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>合作方表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm">获取合作方信息</el-button>
            <el-button type="warning" @click="dialogFormVisible = true">添加合作方</el-button>
            <!-- 添加的弹窗 -->
            <el-dialog title="添加合作方信息" :visible.sync="dialogFormVisible">
              <el-form :model="form">
                <el-form-item label="合作方ID" :label-width="formLabelWidth">
                  <el-input v-model="form.partnerId" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="项目ID" :label-width="formLabelWidth">
                  <el-select v-model="form.projectId" placeholder="请选择项目">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                
                <el-form-item label="合作方名" :label-width="formLabelWidth">
                  <el-input v-model="form.partnerName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" :label-width="formLabelWidth">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="负责人" :label-width="formLabelWidth">
                    <el-input v-model="form.responsiblepeople" autocomplete="off"></el-input>
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
              <th scope="col">合作方ID</th>
              <th scope="col">项目ID</th>
              <th scope="col">合作方名</th>
              <th scope="col">地址</th>
              <th scope="col">负责人</th>
              <th scope="col">负责人电话</th>
              <th scope="col">联系人</th>
              <th scope="col">联系人电话</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Partner
              v-for="dir in partner"
              :key="dir.partnerId"
              :partner="dir"
            ></Partner>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Partner from "../components/Partner.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "PartnerPage",
    components: {
        Partner,
      SideBar,
    },
    data() {
      return {
        partner: [], //学生信息的json数组
        dialogFormVisible: false,
        form: {
            partnerId: "",
            projectId: "",
            partnerName: "",
            address: "",
            responsiblepeople: "",
            responsiblePhone: "",
            contactPerson: "",
            contactPhone: "",
        },
        formLabelWidth: "120px",
        options:[],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findPartner",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          this.partner = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertPartner",
          method: "post",
          data: this.form,
        }). then(() => {
        this.dialogFormVisible = false;
        this.getForm();
        this.resetForm(); // 重置表单
      })
      .catch(error => {
        this.$message.error(error+"号码11位");
      });
      },
  
      resetForm() {
      this.form = { // 重置表单数据
        partnerId: "",
        projectId: "",
        partnerName: "",
        address: "",
        responsiblepeople: "",
        responsiblePhone: "",
        contactPerson: "",
        contactPhone: "",
      };
    },
    },
    created() {
      EventBus.$on("partner-updated", (data) => {
        this.partner = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/findResearchProjectName",
      method: "GET",
    })
      .then(response => {
        console.log("执行了")
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