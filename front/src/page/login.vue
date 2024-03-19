<template>
    <div class="login-wrap">
      <el-form label-position="left" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm login-container">
        <h2 class="title">用户登录</h2>
        <div class="login-icon"><i class="el-icon-user"></i>用户名</div>
        <el-form-item prop="username" >
          <el-input type="text" v-model.trim="ruleForm.username" auto-complete="off" placeholder="账号"></el-input>
        </el-form-item>
        <div class="login-icon"><i class="el-icon-lock"></i>密码</div>
        <el-form-item prop="password">
          <el-input type="password" v-model.trim="ruleForm.password" auto-complete="off" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-button" @click="submitForm()" :loading="logining">登&nbsp;&nbsp;&nbsp;录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-button" @click="sentForm()" :loading="logining">注&nbsp;&nbsp;&nbsp;册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  <script type="text/ecmascript-6">
import axios from 'axios'
  export default {
    name: 'login',
    data() {
      return {
        //定义loading默认为false
        logining: false,
        // 记住密码
        rememberpwd: false,
        ruleForm: {
          //username和password默认为空
          username: '',
          password: '',
        },
        rules: {
          username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
          password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        }
      }
    },
  
    created() {
    //   // 获取存在本地的用户名密码
    //   this.getuserpwd()
  
    },
  
    methods: {
        submitForm(){
            axios({
                method:"post",
                url:"http://localhost:8080/login",
                data:this.ruleForm
            }).then(res=>{
                console.log(res)
                if(res.data == 1){
                    this.$router.push('/LabPage')
                }else{
                    console.log(this.ruleForm)
                    this.$message.error('账号或密码错误')
                }
            })
        },
        sentForm(){
          axios({
                method:"post",
                url:"http://localhost:8080/register",
                data:this.ruleForm
            }).then(res=>{
              if(res.status == 200){
                this.$message.success('注册成功')
              }else{
                this.$message.error('注册失败')
              }
               
            })
        }
    }
  }
  </script>
  
  <style scoped>
  .login-wrap {
    box-sizing: border-box;
    width: 100vw;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #112346;
    background-repeat: no-repeat;
    background-position: center right;
    background-size: 100%;
    color: #ffffff;
  }
  .login-container {
    height:320px;
    border-radius: 10px;
    margin: 0px auto;
    width: 350px;
    min-width: 350px;
    padding: 30px 35px 15px 35px;
    background: rgba(255, 255, 255,.15);
    border: 1px solid rgba(255, 255, 255,.2);
    text-align: left;
    box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
  }
  .title {
    margin: 0px auto 30px auto;
    color: #ffffff;
    text-align: center;
  }
  .login-icon {
    margin-bottom: 10px;
  }
  .login-icon i {
    margin-right: 8px;
  }
  .remember {
    color: #ffffff;
    margin: 22px 0px 35px 0px;
    float: left;
  }
  .login-button {
    width: 60%;
    margin-top: 10px;
    margin-left: 60px;
    float: left;
  }
  .code-box {
    text-align: right;
  }
  .codeimg {
    height: 40px;
  }
  </style>