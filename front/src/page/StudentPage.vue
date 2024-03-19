<template>
  <div id="app">
      <SideBar class="sidebar"></SideBar>
    <!-- <div class="sidebar">
      <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleMenuOpen"
        @close="handleMenuClose"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>导航一</span>
          </template>
          <el-menu-item index="1-1">选项1</el-menu-item>
          <el-menu-item index="1-2">选项2</el-menu-item>
          <el-menu-item index="1-3">选项3</el-menu-item>
          <el-menu-item index="1-4">选项4</el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>导航二</span>
          </template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
          <el-menu-item index="2-3">选项3</el-menu-item>
          <el-menu-item index="2-4">选项4</el-menu-item>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>导航三</span>
          </template>
          <el-menu-item index="3-1">选项1</el-menu-item>
          <el-menu-item index="3-2">选项2</el-menu-item>
          <el-menu-item index="3-3">选项3</el-menu-item>
          <el-menu-item index="3-4">选项4</el-menu-item>
        </el-submenu>
      </el-menu>
    </div> -->

    <div class="content">
      <div class="table-caption">
        <caption>
          <h1>学生表</h1>
        </caption>
        <div>
          <el-button type="primary" round @click="getStudent"
            >获取学生信息</el-button
          >
          <el-button type="warning" @click="dialogFormVisible = true"
            >添加学生</el-button
          >
          <el-dialog title="添加学生信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="学号" :label-width="formLabelWidth">
                <el-input v-model="form.number" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="姓名" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="年龄" :label-width="formLabelWidth">
                <el-input
                  v-model.number="form.age"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="语文" :label-width="formLabelWidth">
                <el-input
                  v-model.number="form.chi"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="数学" :label-width="formLabelWidth">
                <el-input
                  v-model.number="form.math"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <el-form-item label="英语" :label-width="formLabelWidth">
                <el-input
                  v-model.number="form.eng"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
              <!-- <el-form-item label="活动区域" :label-width="formLabelWidth">
                <el-select v-model="form.region" placeholder="请选择活动区域">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item> -->
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="addStudent">添 加</el-button
              ><!--dialogFormVisible = false-->
            </div>
          </el-dialog>
        </div>
      </div>

      <table class="table captain-top table-hover table-striped">
        <thead>
          <tr>
            <th scope="col">学号</th>
            <th scope="col">姓名</th>
            <th scope="col">年龄</th>
            <th scope="col">语文</th>
            <th scope="col">数学</th>
            <th scope="col">英语</th>
            <th scope="col">操作</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <Student
            v-for="stu in students"
            :key="stu.id"
            :student="stu"
          ></Student>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { EventBus } from "../components/EventBus.js";
import Student from "../components/Student.vue";
import SideBar from "../components/SideBar.vue";
export default {
  name: "StudentPage",
  components: {
    Student,
    SideBar,
  },
  data() {
    return {
      students: [], //学生信息的json数组
      dialogFormVisible: false,
      form: {
        number: "",
        name: "",
        age: "",
        chi: "",
        math: "",
        eng: "",
      },
      formLabelWidth: "120px",
    };
  },
  methods: {
    handleMenuOpen(key, keyPath) {
      console.log("Menu opened:", key, keyPath);
    },
    handleMenuClose(key, keyPath) {
      console.log("Menu closed:", key, keyPath);
    },
    getStudent() {
      axios({
        url: "http://localhost:8080/find",
        method: "GET",
      }).then((res) => {
        console.log(res.data);
        this.students = res.data;
      });
    },

    addStudent() {
      axios({
        url: "http://localhost:8080/insert",
        method: "post",
        data: this.form,
      }).then(() => {
    this.dialogFormVisible = false;
    this.getStudent();
  });

    },
  },
  created() {
    EventBus.$on("students-updated", (data) => {
      this.students = data;
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
  width:80vw;
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