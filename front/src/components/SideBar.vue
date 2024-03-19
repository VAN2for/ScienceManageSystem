<template>
   <div class="sidebar">
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
              <span>研究室</span>
            </template>
            <el-menu-item index="1-1" @click="goToLabPage">研究室管理</el-menu-item>
            <el-menu-item index="1-2" @click="goToOfficeSpacePage">办公场地管理</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>人员管理</span>
            </template>
            <el-menu-item index="2-1" @click="goToSecretaryPage">秘书管理</el-menu-item>
            <el-menu-item index="2-2" @click="goTosearcherPage" >科研人员管理</el-menu-item>
            <el-menu-item index="2-3" @click="goToDirectorPage">负责人管理</el-menu-item>
           
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>项目管理</span>
            </template>
            <el-menu-item index="3-1" @click="goToProjectPage">项目管理</el-menu-item>
            <el-menu-item index="3-2" @click="goToClientPage">委托方管理</el-menu-item>
            <el-menu-item index="3-3" @click="goToMonitorPage">质量检测方管理</el-menu-item>
            <el-menu-item index="3-4" @click="goTopartnerPage">合作方管理</el-menu-item>
            <el-menu-item index="3-5" @click="goToPaticipantPage">参与科研人员管理</el-menu-item>
            <el-menu-item index="3-6" @click="goToPartAndProjectPage">项目与科研人员</el-menu-item>
            <el-menu-item index="3-7" @click="goToSubProjectPage">子项目管理</el-menu-item>
            <el-menu-item index="3-8" @click="goToAchievementPage">科研成果管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </div>
</template>

<script>
export default {
    methods: {
    goToSecretaryPage() {
        if (this.$route.path !== '/secretaryPage') {
        this.$router.push('/secretaryPage');
      }
    },
    goToDirectorPage() {
        if (this.$route.path !== '/directorPage') {
        this.$router.push('/directorPage');
      }
    },
    goToLabPage(){
      if (this.$route.path !== '/labPage') {
        this.$router.push('/labPage');
     }
    },
    goTosearcherPage(){
      if (this.$route.path !== '/researcherPage') {
        this.$router.push('/researcherPage');
     }
    },
    goTopartnerPage(){
      if (this.$route.path !== '/partnerPage') {
        this.$router.push('/partnerPage');
     }
    },
    goToOfficeSpacePage(){
      if (this.$route.path !== '/officeSpacePage') {
        this.$router.push('/officeSpacePage');
     }
    },
    goToMonitorPage(){
      if (this.$route.path !== '/monitorPage') {
        this.$router.push('/monitorPage');
     }
    },
    goToClientPage(){
      if (this.$route.path !== '/clientPage') {
        this.$router.push('/clientPage');
     }
    },
    goToProjectPage(){
      if (this.$route.path !== '/projectPage') {
        this.$router.push('/projectPage');
     }
    },
    goToPaticipantPage(){
      if (this.$route.path !== '/paticipantPage') {
        this.$router.push('/paticipantPage');
     }
    },
    goToPartAndProjectPage(){
      if (this.$route.path !== '/partAndProjectPage') {
        this.$router.push('/partAndProjectPage');
     }
    },
    goToSubProjectPage(){
      if (this.$route.path !== '/subProjectPage') {
        this.$router.push('/subProjectPage');
     }
    },
    goToAchievementPage(){
      if (this.$route.path !== '/achievementPage') {
        this.$router.push('/achievementPage');
     }
    },








    }

}
</script>

<style>

</style>