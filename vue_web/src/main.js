import Vue from 'vue'
import './plugins/element.js'
import App from './App.vue'
import router from './router'
import './assets/css/account.scss'
import './assets/css/main.scss'
import  '/src/plugins/message'
import "vue-dplayer/dist/vue-dplayer.css";


import  VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

//播放视频
import VueDPlayer from "vue-dplayer";
import "vue-dplayer/dist/vue-dplayer.css";
Vue.use(VueDPlayer);

Vue.use(VueQuillEditor)


//引入
import echarts from 'echarts'
import ElementUI from 'element-ui';
Vue.prototype.$echarts = echarts

import './plugins/wyz-echarts/wyz-echarts.js'

// Vue.use(Vuex)

//导入axios
import axios from 'axios'
//引入全局样式
import './assets/css/all.css'
Vue.config.productionTip = false

//axios添加请求拦截器
axios.interceptors.request.use(config => {
    const homeToken = sessionStorage.getItem("token");
    if (homeToken) {
        config.headers.token = homeToken;  //设置请求头数据
    }
    return config;
})

//设置路径的默认前缀
axios.defaults.baseURL = "http://localhost:7500";
//把axios挂载到vue对象
Vue.prototype.$http = axios;

Vue.config.productionTip = false

// Vue.prototype.axiox = axios;

//路由守卫  路由之前做的事
router.beforeEach((to, from, next) => {
    const path = to.path;
    if (path === "/qianLogin") {
        return next();
    }
    if (path === "/courses"||path === "/list"||path.concat("/DetailId")||path.concat("http://unitradeprod.alipaydev.com/appAssign.htm?alipay_exterface_invoke_assign_target=") ||path === "/paySuccess") {
        return next();
    }
    if (path === "/qianLogin"||path === "/reset"||path === "/hourGuanLi") {//个人中心放行
        return next();
    }
    if (path === "/"||path === "/recruit"||path === "/apply"||path === "/hourGuanLi") {//个人中心放行
        return next();
    }
    const homeToken = sessionStorage.getItem("token");
    if (homeToken) {
        return next();
    } else {
        return next("/qianLogin");
    }
})

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
