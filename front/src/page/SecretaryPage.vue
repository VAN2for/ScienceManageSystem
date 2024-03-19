<template>
  <div id="app">
    <SideBar class="sidebar"></SideBar>

    <div class="content">
      <div class="table-caption">
        <caption>
          <h1>秘书表</h1>
        </caption>
        <div>
          <el-button type="primary" round @click="getSecretary"
            >获取秘书信息</el-button>
          <el-button type="warning" @click="dialogFormVisible = true">添加秘书</el-button>
          <el-input type="text" placeholder="按姓名搜索秘书" style="width:350px" v-model="search_name"/><el-button type="success" @click="search">搜索</el-button>
          <el-dialog title="添加秘书信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="秘书号" :label-width="formLabelWidth">
                <el-input
                  v-model="form.secretaryNo"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="姓名" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="性别" :label-width="formLabelWidth">
                <!-- <el-input
                    v-model.number="form.gender"
                    autocomplete="off"
                  ></el-input> -->
                <el-radio v-model="form.gender" label="男">男</el-radio>
                <el-radio v-model="form.gender" label="女">女</el-radio>
              </el-form-item>
              <el-form-item label="年龄" :label-width="formLabelWidth">
                <el-input
                  v-model.number="form.age"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="任用时间" :label-width="formLabelWidth">
                <el-date-picker v-model="form.hireDate" type="date" placeholder="选择日期"></el-date-picker>
              </el-form-item>
              <el-form-item label="职责" :label-width="formLabelWidth">
                <el-input
                  v-model.number="form.duty"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="addSecretary">添 加</el-button
              ><!--dialogFormVisible = false-->
            </div>
          </el-dialog>
        </div>
      </div>

      <table class="table captain-top table-hover table-striped">
        <thead>
          <tr>
            <th scope="col">秘书号</th>
            <th scope="col">姓名</th>
            <th scope="col">性别</th>
            <th scope="col">年龄</th>
            <th scope="col">任用时间</th>
            <th scope="col">职责</th>
            <th scope="col">操作</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <Secretary
            v-for="stu in secretary_for_page"
            :key="stu.secretaryNo"
            :secretary="stu"
          ></Secretary>
        </tbody>
      </table>
      <div class="text-center">
      <el-button-group>
      <el-button type="primary" icon="el-icon-arrow-left" @click="last_page">上一页</el-button>
      <el-button type="primary"  @click="next_page">下一页<i class="el-icon-arrow-right el-icon--right"></i></el-button>
      </el-button-group>
    </div>
    </div>
  </div>
</template>
  
  <script>
import axios from "axios";
import { EventBus } from "../components/EventBus.js";
import Secretary from "../components/Secretary.vue";
import SideBar from "../components/SideBar.vue";
export default {
  name: "SecretaryPage",
  components: {
    Secretary,
    SideBar,
  },
  data() {
    return {
      secretary: [], //学生信息的json数组
      search_name:"",
      page:1,
      dialogFormVisible: false,
      form: {
        secretaryNo: "",
        name: "",
        gender: "",
        age: "",
        hireDate: "",
        duty: "",
      },
      formLabelWidth: "120px",
    };
  },
  methods: {
    getSecretary() {
      axios({
        url: "http://localhost:8080/findSecretary",
        method: "GET",
      }).then((res) => {
        const originalDate = new Date(this.secretary.hireDate);
        this.secretary.hireDate = originalDate;
        this.secretary = res.data;
      });
    },
   search(){
      axios({
        url: "http://localhost:8080/searchSecretaryByName",
        method: "post",
        data:{
          name:this.search_name,
        }
      }).then(res => {
        console.log("res:",res);
        if(res.status==200){
        const originalDate = new Date(this.secretary.hireDate);
        this.secretary.hireDate = originalDate;
        this.secretary = res.data;
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
    addSecretary() {
      axios({
        url: "http://localhost:8080/insertSecretary",
        method: "post",
        data: this.form,
      }). then(res=> {
        if(res.status==200){
      this.dialogFormVisible = false;
      this.getSecretary();
      this.resetForm(); // 重置表单
      this.$message({
          type: 'success',
          message: '增加成功!'
        })
      }else{
        this.$message.error(res.msg);
      }
    })
    .catch(error => {
      // 获取错误信息并显示
      // const errorMessage = error.response.data.message;
      // this.$message.error(errorMessage);
      // const errorMessage = `Status: ${error.response.data.status}, Error: ${error.response.data.error}`;
      this.$message.error(error);
    });
    },

    resetForm() {
    this.form = { // 重置表单数据
      secretaryNo: "",
      name: "",
      gender: "",
      age: "",
      hireDate: "",
      duty: "",
      };
    },
    next_page(){
      this.page++;
    },
    last_page(){
      if(this.page>1){
        this.page--;
      }

    },

  },
  computed: {
    secretary_for_page() {
      return this.secretary.slice((this.page-1)*5,this.page*5);//对secretary的数据进行切片
    },
  },
  created() {
    EventBus.$on("secretary-updated", (data) => {
      this.secretary = data;
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