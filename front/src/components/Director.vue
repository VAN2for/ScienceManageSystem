<template>
    <tr>
      <td v-show="!is_edit">{{ director.directorNo }}</td>
      <td v-show="!is_edit">{{ director.startDate }}</td>
      <td v-show="!is_edit">{{ director.term }}</td>
      <td v-show="!is_edit">{{ director.researchername }}</td>
      <td v-show="!is_edit">
        <!--用来放按钮-->
        <el-button type="success" @click="modify">修改</el-button>
        <el-button type="danger" @click="deleteForm">删除</el-button>
      </td>
  
      <td v-show="is_edit">
        {{ director.directorNo }}
      </td>

      <td v-show="is_edit">
        <el-date-picker
      v-model="director.startDate"
      type="date"
      placeholder="选择日期">
    </el-date-picker>
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model.number="director.term" />
      </td>
      <td v-show="is_edit">
        <!-- <input type="text" class="w-50" v-model="director.researchername" /> -->
        <el-select v-model="director.researchername" placeholder="请对应科研人员" @focus="selectresearcher">
                        <el-option
                          v-for="item in littleoptions"
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
      props:["director"],
      data(){
          return{
              is_edit:false,
              littleoptions:[],
          }
      },
      methods:{
          modify(){
              this.is_edit = true;
          },
          save(){
            const originalDate = new Date(this.director.startDate);
            this.director.startDate=originalDate;
              axios({
                  method:"post",
                  url:"http://localhost:8080/updateDirector",
                  data:this.director
              }).then(res=>{
                  console.log(res);
                  this.is_edit = false;
                  axios({
          url: "http://localhost:8080/findDirector",
          method: "GET",
        }).then((res) => {
          console.log(res.data);
          EventBus.$emit("director-updated", res.data);
        });
              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
              
          },
          deleteForm(){
            console.log(this.director)
            const originalDate = new Date(this.director.startDate);
            this.director.startDate=originalDate;
              axios({
                  method:"post",
                  url:"http://localhost:8080/deleteDirector",
                  data:this.director
              }).then(res=>{
                  console.log(res);
                  this.is_edit = false;
                  axios({
                    url: "http://localhost:8080/findDirector",
                    method: "GET",
                  }).then((res) => {
                    console.log(res.data);
                    EventBus.$emit("director-updated", res.data);
                  });    
                 }).catch(err=>{
                     console.log(err);
                     this.$message.error(err);
                 
                 })
          },
          selectresearcher(){
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
      }
  };
  </script>
  
  <style>
  </style>
  
  
  <!-- 冒号（:）在Vue.js中用作属性绑定的简写形式，也称为v-bind指令。动态地将一个Vue实例的数据属性绑定到HTML元素的属性上。:style="{ backgroundColor: selectedColor } :src="imageUrl"
  v-model是Vue.js框架中的一个指令，用于在表单元素和组件上实现双向数据绑定。它通常用于将表单输入的值与Vue实例中的数据属性进行关联，以便在用户输入时自动更新数据，并在数据更新时更新表单元素的值。
  {{}}（双大括号）是Vue.js中的插值语法，用于在模板中显示Vue实例中的数据。通过将表达式包裹在双大括号中，Vue会将该表达式的结果渲染到视图中。这使得我们能够动态地显示数据，并在数据更新时自动更新视图。
  -->