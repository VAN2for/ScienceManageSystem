
<template>
    <tr>
      <td v-show="!is_edit">{{ partAndProject.projectId }}</td>
      <td v-show="!is_edit">{{ partAndProject.participantName }}</td>
      <td v-show="!is_edit">
        <!--用来放按钮-->
        <!-- <el-button type="success" @click="modify">修改</el-button> -->
        <el-button type="danger" @click="deleteForm">删除</el-button>
      </td>
  
      <td v-show="is_edit">
        {{ partAndProject.projectId }}
      </td>


      <td v-show="is_edit">
        <el-select v-model="partAndProject.participantName" placeholder="请选择项目参与人" @focus="selectPaticipant">
                        <el-option
                          v-for="item in littleoptions2"
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
      props:["partAndProject"],
      data(){
          return{
              is_edit:false,
              littleoptions2:[],
          }
      },
      methods:{
          modify(){
              this.is_edit = true;
          },
          save(){
              axios({
                  method:"post",
                  url:"http://localhost:8080/updatePaticipantAndProject",
                  data:this.partAndProject
              }).then(res=>{
                //刷新页面表
                  console.log(res);
                  this.is_edit = false;
                  axios({
                      url: "http://localhost:8080/findPaticipantAndProject",
                      method: "GET",
                    }).then((res) => {
                      console.log(res.data);
                      EventBus.$emit("partAndProject-updated", res.data);
                    });

              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
          },
          deleteForm(){
              axios({
                  method:"post",
                  url:"http://localhost:8080/deletePaticipantAndProject",
                  data:this.partAndProject
              }).then(res=>{
                  console.log(res);
                  //刷新页面表
                  this.is_edit = false;
                  axios({
                        url: "http://localhost:8080/findPaticipantAndProject",
                        method: "GET",
                      }).then((res) => {
                        console.log(res.data);
                        EventBus.$emit("partAndProject-updated", res.data);
                      });
                  
              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
          },

          selectPaticipant(){
            axios({
          url: "http://localhost:8080/findPaticipantName",
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
     
    }
  };
  </script>
  
  <style>
  </style>
  
  
  <!-- 冒号（:）在Vue.js中用作属性绑定的简写形式，也称为v-bind指令。动态地将一个Vue实例的数据属性绑定到HTML元素的属性上。:style="{ backgroundColor: selectedColor } :src="imageUrl"
  v-model是Vue.js框架中的一个指令，用于在表单元素和组件上实现双向数据绑定。它通常用于将表单输入的值与Vue实例中的数据属性进行关联，以便在用户输入时自动更新数据，并在数据更新时更新表单元素的值。
  {{}}（双大括号）是Vue.js中的插值语法，用于在模板中显示Vue实例中的数据。通过将表达式包裹在双大括号中，Vue会将该表达式的结果渲染到视图中。这使得我们能够动态地显示数据，并在数据更新时自动更新视图。
  -->