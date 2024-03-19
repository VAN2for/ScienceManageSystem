<template>
    <div id="app">
      <SideBar class="sidebar"></SideBar>
  
      <div class="content">
        <div class="table-caption">
          <caption>
            <h1>科研成果表</h1>
          </caption>
          <div>
            <el-button type="primary" round @click="getForm"
              >获取科研成果信息</el-button
            >
            <el-button type="warning" @click="dialogFormVisible = true"
              >添加科研成果信息</el-button
            >
            <!-- 添加的弹窗 -->
            <el-dialog title="添加科研成果" :visible.sync="dialogFormVisible">
              <el-form :model="form">
              <el-form-item label="成果名" :label-width="formLabelWidth">
                <el-input v-model="form.achievementName" autocomplete="off"></el-input>
              </el-form-item>
                <el-form-item label="所属项目ID" :label-width="formLabelWidth">
                  <el-select v-model="form.projectId" placeholder="请选择">
                        <el-option
                          v-for="item in options"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
                <el-form-item label="成果贡献人" :label-width="formLabelWidth">
                  <el-select v-model="form.contributer" placeholder="请选择">
                        <el-option
                          v-for="item in options2"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
                </el-form-item>
              <el-form-item label="取得时间" :label-width="formLabelWidth">
                <el-date-picker v-model="form.achieveTime" type="date" placeholder="选择日期"></el-date-picker>
              </el-form-item>
              <el-form-item label="排名" :label-width="formLabelWidth">
                <el-input v-model.number="form.ranking" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="成果类型" :label-width="formLabelWidth">
                <el-radio-group v-model="form.classification">
                  <el-radio :label="'发明专利'">发明专利</el-radio>
                  <el-radio :label="'实用新型专利'">实用新型专利</el-radio>
                  <el-radio :label="'外观专利'">外观专利</el-radio>
                  <el-radio :label="'论文'">论文</el-radio>
                  <el-radio :label="'软件著作权'">软件著作权</el-radio>
                </el-radio-group>
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
              <th scope="col">科研成果名</th>
              <th scope="col">所属项目ID</th>
              <th scope="col">成果贡献人</th>
              <th scope="col">取得时间</th>
              <th scope="col">排名</th>
              <th scope="col">成果类型</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <Achievement
              v-for="dir in achievement"
              :key="dir.achievementName"
              :achievement="dir"
            ></Achievement>
          </tbody>
        </table>
      </div>
    </div>
  </template>
    
    <script>
  import axios from "axios";
  import { EventBus } from "../components/EventBus.js";
  import Achievement from "../components/Achievement.vue";
  import SideBar from "../components/SideBar.vue";
  export default {
    name: "AchievementPage",
    components: {
      Achievement,
      SideBar,
    },
    data() {
      return {
        achievement: [], //学生信息的json数组
        dialogFormVisible: false,
        form: {
            achievementName: "",
            projectId: "",
            contributer: "",
            achieveTime: "",
            ranking: "",
            classification: "",
        },
        formLabelWidth: "120px",
        options: [],
        options2:[],
      };
    },
    methods: {
      getForm() {
        axios({
          url: "http://localhost:8080/findAchievement",
          method: "GET",
        }).then((res) => {
          const originalDate = new Date(this.achievement.achieveTime);
          this.achievement.achieveTime = originalDate;
          this.achievement = res.data;
        });
      },
  
      addForm() {
        axios({
          url: "http://localhost:8080/insertAchievement",
          method: "post",
          data: this.form,
        }). then(res => {
            console.log(res)
            if (res.status === 200) {
        this.dialogFormVisible = false;
        this.getForm();
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
        console.log(this.form)
        console.log(error)
        this.$message.error(error);
      });
      },
  
      resetForm() {
      this.form = { // 重置表单数据
        achievementName: "",
        projectId: "",
        contributer: "",
        achieveTime: "",
        ranking: "",
        classification: "",
      };
    },
    },
    created() {
      EventBus.$on("achievement-updated", (data) => {
        this.achievement = data;
      });
    },

    mounted() {
      axios({
      url: "http://localhost:8080/getProjectId",
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
      url: "http://localhost:8080/getPaticipantName",
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