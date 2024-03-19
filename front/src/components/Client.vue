
<template>
    <tr>
      <td v-show="!is_edit">{{ client.clientName }}</td>
      <td v-show="!is_edit">{{ client.address }}</td>
      <td v-show="!is_edit">{{ client.responsibleperson }}</td>
      <td v-show="!is_edit">{{ client.responsiblePhone }}</td>
      <td v-show="!is_edit">{{ client.contactPerson }}</td>
      <td v-show="!is_edit">{{ client.contactPhone }}</td>
      <td v-show="!is_edit">
        <!--用来放按钮-->
        <el-button type="success" @click="modify">修改</el-button>
        <el-button type="danger" @click="deleteForm">删除</el-button>
      </td>
  
      <td v-show="is_edit">
        {{  client.clientName }}
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="client.address" />
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="client.responsibleperson" />
      </td>
    
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="client.responsiblePhone" />
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="client.contactPerson" />
      </td>
      <td v-show="is_edit">
        <input type="text" class="w-50" v-model="client.contactPhone" />
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
    name: "Client",
      props:["client"],
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
              axios({
                  method:"post",
                  url:"http://localhost:8080/updateClient",
                  data:this.client
              }).then(res=>{
                //刷新页面表
                  console.log(res);
                  this.is_edit = false;
                  axios({
                      url: "http://localhost:8080/findClient",
                      method: "GET",
                    }).then((res) => {
                      console.log(res.data);
                      EventBus.$emit("client-updated", res.data);
                    });

              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
          },
          deleteForm(){
              axios({
                  method:"post",
                  url:"http://localhost:8080/deleteClient",
                  data:this.client
              }).then(res=>{
                  console.log(res);
                  //刷新页面表
                  this.is_edit = false;
                  axios({
                        url: "http://localhost:8080/findClient",
                        method: "GET",
                      }).then((res) => {
                        console.log(res.data);
                        EventBus.$emit("client-updated", res.data);
                      });
                  
              }).catch(err=>{
                  console.log(err);
                  this.$message.error(err);
              
              })
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