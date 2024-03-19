<template>
    <tr>
      <td v-show="!is_edit">{{ paticipant.paticipantName }}</td>
      <td v-show="!is_edit">{{ paticipant.workHour }}</td>
      <td v-show="!is_edit">{{ paticipant.joinTime }}</td>
      <td v-show="!is_edit">{{ paticipant.money }}</td>
      <td v-show="!is_edit">
        <!--用来放按钮-->
        <el-button type="success" @click="modify">修改</el-button>
        <el-button type="danger" @click="deleteForm">删除</el-button>
      </td>
  
      <td v-show="is_edit">
        {{ paticipant.paticipantName }}
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model.number="paticipant.workHour" />
      </td>
      <td v-show="is_edit">
        <el-date-picker v-model="paticipant.joinTime" type="date" placeholder="选择日期"></el-date-picker>
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="paticipant.money" />
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
      props:["paticipant"],
      data(){
          return{
              is_edit:false,
          }
      },
      methods:{
          modify(){
              this.is_edit = true;
          },
          save(){
            const originalDate = new Date(this.paticipant.joinTime);
            this.paticipant.joinTime=originalDate;
              axios({
                  method:"post",
                  url:"http://localhost:8080/updatePaticipant",
                  data:this.paticipant
              }).then(res=>{
                  console.log(res);
                  this.is_edit = false;
                  axios({
          url: "http://localhost:8080/findPaticipant",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          EventBus.$emit("paticipant-updated", res.data);
        });
              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
              
          },
          deleteForm(){
            const originalDate = new Date(this.paticipant.joinTime);
            this.paticipant.joinTime=originalDate;
              axios({
                  method:"post",
                  url:"http://localhost:8080/deletePaticipant",
                  data:this.paticipant
              }).then(res=>{
                  console.log(res);
                  this.is_edit = false;
                  axios({
          url: "http://localhost:8080/findPaticipant",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          EventBus.$emit("paticipant-updated", res.data);
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