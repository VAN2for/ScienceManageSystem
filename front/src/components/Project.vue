
<template>
    <tr>
      <td v-show="!is_edit">{{ project.projectNo }}</td>
      <td v-show="!is_edit">{{ project.projectLeader }}</td>
      <td v-show="!is_edit">{{ project.projectName }}</td>
      <td v-show="!is_edit">{{ project.researchContent }}</td>

      <td v-show="!is_edit">{{ project.totalFunding }}</td>
      <td v-show="!is_edit">{{ project.startDate }}</td>
      <td v-show="!is_edit">{{ project.completionDate }}</td>
      <td v-show="!is_edit">{{ project.clientName }}<el-button type="info" plain @click="getClientInfo">信息</el-button></td>
      <td v-show="!is_edit">{{ project.monitorName }}<el-button type="info" plain @click="getMonitorInfo">信息</el-button></td>
      <td v-show="!is_edit">
        <!--用来放按钮-->
        <el-button type="success" @click="modify">修改</el-button>
        <el-button type="danger" @click="deleteForm">删除</el-button>
      </td>
  
      <td v-show="is_edit">
        {{ project.projectNo }}
      </td>

      <td v-show="is_edit">
        <el-select v-model="project.projectLeader" placeholder="请选择负责人" @focus="selectDirector">
                        <el-option
                          v-for="item in littleoptions"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="project.projectName" />
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="project.researchContent" />
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model.number="project.totalFunding" />
      </td>
      <td v-show="is_edit">
        <el-date-picker v-model="project.startDate" type="date" placeholder="选择开始日期"></el-date-picker>
      </td>
      <td v-show="is_edit">
        <el-date-picker v-model="project.completionDate" type="date" placeholder="选择结束日期"> </el-date-picker>
      </td>
      <td v-show="is_edit">
        <el-select v-model="project.clientName" placeholder="请选择委托方" @focus="selectClient">
                        <el-option
                          v-for="item in littleoptions2"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
      </td>
      <td v-show="is_edit">
        <el-select v-model="project.monitorName" placeholder="请选择质量监测方" @focus="selectMonitor">
                        <el-option
                          v-for="item in littleoptions3"
                          :key="item"
                          :label="item"
                          :value="item">
                        </el-option>
                      </el-select>
      </td>

      <td v-show="is_edit">
        <!--用来放按钮-->
        <el-button type="success" @click="save">保存</el-button>
        <el-button type="danger" @click="deleteForm">删除</el-button>
      </td>
    </tr>
  </template>
  
  <script>
  import axios from "axios";
  import { EventBus } from "./EventBus.js";
  export default {
    name: "Project",
      props:["project"],
      data(){
          return{
              is_edit:false,
              littleoptions: [],
              littleoptions2:[],
              littleoptions3:[],
          }
      },
      methods:{
          modify(){
              this.is_edit = true;
          },
          save(){
            const originalDate = new Date(this.project.startDate);
            this.project.startDate=originalDate;
            const originalDate2 = new Date(this.project.completionDate);
            this.project.completionDate=originalDate2;
              axios({
                  method:"post",
                  url:"http://localhost:8080/updateResearchProject",
                  data:this.project
              }).then(res=>{
                //刷新页面表
                  console.log(res);
                  this.is_edit = false;
                  axios({
                      url: "http://localhost:8080/findResearchProject",
                      method: "GET",
                    }).then((res) => {
                      console.log(res.data);
                      EventBus.$emit("project-updated", res.data);
                    });

              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
          },
          deleteForm(){
            const originalDate = new Date(this.project.startDate);
            this.project.startDate=originalDate;
            const originalDate2 = new Date(this.project.completionDate);
            this.project.completionDate=originalDate2;
              axios({
                  method:"post",
                  url:"http://localhost:8080/deleteResearchProject",//deleteResearchProject
                  data:this.project
              }).then(res=>{
                  console.log(res);
                  //刷新页面表
                  this.is_edit = false;
                  axios({
                        url: "http://localhost:8080/findResearchProject",
                        method: "GET",
                      }).then((res) => {
                        console.log(res.data);
                        EventBus.$emit("project-updated", res.data);
                      });
                  
              }).catch(err=>{
                console.log(this.project)
                  console.log(err);
                  this.$message.error(err);
              
              })
          },


          selectDirector(){
            console.log("selectDirector")
            axios({
          url: "http://localhost:8080/findResearcherName",
          method: "GET",
        })
          .then(response => {
            const data = response.data;
            console.log(data);
    
            this.littleoptions=data;
            console.log(this.options)
    
          })
          .catch(error => {
            console.error(error);
          });
        },
        selectClient(){
            axios({
          url: "http://localhost:8080/findClientName",
          method: "GET",
        })
          .then(response => {
            const data = response.data;
            console.log(data);
    
            this.littleoptions2=data;
            console.log(this.options2)
    
          })
          .catch(error => {
            console.error(error);
          });
        },

        selectMonitor(){
            axios({
          url: "http://localhost:8080/findQualityMonitorName",
          method: "GET",
        })
          .then(response => {
            const data = response.data;
            console.log(data);
    
            this.littleoptions3=data;
            console.log(this.options3)
    
          })
          .catch(error => {
            console.error(error);
          });
        },
        getClientInfo(){
            axios({
                  method:"post",
                  url:"http://localhost:8080/searchClientByName",
                  data:{ name: this.project.clientName}
              }).then(res=>{
                //刷新页面表
                  console.log(res.data);
                  this.is_edit = false;
                  this.$alert(res.data, '委托方信息', {
                confirmButtonText: '确定',
             });
              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
        },
        getMonitorInfo(){
            axios({
                  method:"post",
                  url:"http://localhost:8080/searchMonitorName",
                  data:{ name: this.project.monitorName}
              }).then(res=>{
                //刷新页面表
                  console.log(res.data);
                  this.is_edit = false;
                  this.$alert(res.data, '质量监测方信息', {
                confirmButtonText: '确定',
             });
              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
        }
    }
  };
  </script>
  
  <style>
  </style>
  
  
  <!-- 冒号（:）在Vue.js中用作属性绑定的简写形式，也称为v-bind指令。动态地将一个Vue实例的数据属性绑定到HTML元素的属性上。:style="{ backgroundColor: selectedColor } :src="imageUrl"
  v-model是Vue.js框架中的一个指令，用于在表单元素和组件上实现双向数据绑定。它通常用于将表单输入的值与Vue实例中的数据属性进行关联，以便在用户输入时自动更新数据，并在数据更新时更新表单元素的值。
  {{}}（双大括号）是Vue.js中的插值语法，用于在模板中显示Vue实例中的数据。通过将表达式包裹在双大括号中，Vue会将该表达式的结果渲染到视图中。这使得我们能够动态地显示数据，并在数据更新时自动更新视图。
  -->