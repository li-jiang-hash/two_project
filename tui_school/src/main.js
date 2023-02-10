import Vue from 'vue'
import './plugins/element.js'
import App from './App.vue'
import router from './router'
// import Vuex from 'vuex'
import store from "./vuex/index"
import './assets/css/account.scss'
import './assets/css/main.scss'
//  导入permissions.js文件
import  './permissions'

//富文本编辑器
import  VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
Vue.use(VueQuillEditor)
//main.js
//导入
import {
    message
} from './resetMessage';



Vue.use(ElementUI)
//挂载
Vue.prototype.$message = message;
//引入
import echarts from 'echarts'
import ElementUI, {Message} from 'element-ui';
Vue.prototype.$echarts = echarts

import './plugins/wyz-echarts/wyz-echarts.js'

// Vue.use(Vuex)

//导入axios
import axios from 'axios'
//引入全局样式
import './assets/css/all.css'
//引入session
// import VueSession from "vue-session/index.esm"; 'vue-session'
//引入cookies
// import VueCookies from 'vue-cookies'

// Vue.use(VueSession);
// Vue.use(VueCookies);

//axios添加请求拦截器
axios.interceptors.request.use(config => {
    var token = sessionStorage.getItem("token");
    if (token) {
        config.headers.token = token;  //设置请求头数据
    }
    return config;
})
//axios响应拦截器
/*axios.interceptors.response.use(response=>{
    //debugger
    console.log(response)
     if(response.data.code===2000){
          return response;
     }else if(response.data.code===4001){
             Message.error("权限不足")
             return Promise.reject(new Error("权限不足"));
     }else if(response.data.code===4002){
         Message.error("未登录")
         return Promise.reject(new Error("未登录"));
     }else {
         Message.error("操作有误")
         return Promise.reject(new Error("操作有误"));
     }

},error => {
    Message.error("查询失败")
    return Promise.reject(new Error("查询失败"));
})*/

//设置路径的默认前缀
axios.defaults.baseURL = "http://192.168.31.75:7500";
//把axios挂载到vue对象
Vue.prototype.$http = axios;

Vue.config.productionTip = false

// Vue.prototype.axiox = axios;

//路由守卫  路由之前做的事
router.beforeEach((to, from, next) => {
    const path = to.path;
    if (path === "/houLogin") {
        return next();
    }
    const token = sessionStorage.getItem("token");
    if (token) {
        return next();
    } else {
        return next("/houLogin");
    }
})

new Vue({
    // Vuex,
    store,
    router,
    render: h => h(App)
}).$mount('#app')
