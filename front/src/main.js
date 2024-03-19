import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import VueRouter from 'vue-router';
Vue.config.productionTip = false
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap.min.js"//bootstrap js

import HomePage from './App.vue';
import StudentPage from './page/StudentPage.vue';
import SecretaryPage from './page/SecretaryPage.vue';
import DirectorPage from './page/DirectorPage.vue';
import LabPage from './page/LabPage.vue';
import login from './page/login.vue';
import ResearcherPage from './page/ResearcherPage.vue';
import PartnerPage from './page/PartnerPage.vue';
import OfficeSpacePage from './page/OfficeSpacePage.vue';
import MonitorPage from './page/MonitorPage.vue';
import ClientPage from './page/ClientPage.vue';
import ProjectPage from './page/ProjectPage.vue';
import PaticipantPage from './page/PaticipantPage.vue';
import PartAndProjectPage from './page/PartAndProjectPage.vue';
import SubProjectPage from './page/SubProjectPage.vue';
import AchievementPage from './page/AchievementPage.vue';
Vue.use(ElementUI)
Vue.use(VueRouter);


const routes = [
  { path: '/', component: HomePage },
  { path: '/login', component: login },
  { path: '/secretaryPage', component: SecretaryPage },
  { path: '/StudentPage', component: StudentPage },
  { path: '/directorPage', component: DirectorPage },
  { path: '/labPage', component: LabPage },
  { path: '/researcherPage', component: ResearcherPage },
  { path: '/partnerpage', component: PartnerPage },
  { path: '/officeSpacepage', component: OfficeSpacePage },
  { path: '/monitorPage', component: MonitorPage },
  { path: '/clientPage', component: ClientPage },
  { path: '/projectPage', component: ProjectPage },
  { path: '/paticipantPage', component: PaticipantPage },
  { path: '/partAndProjectPage', component: PartAndProjectPage },
  { path: '/subProjectPage', component: SubProjectPage },
  { path: '/achievementPage', component: AchievementPage },

];

const router = new VueRouter({
  routes
});
new Vue({
  router,  // 使用 router
  render: h => h(App),
}).$mount('#app')

