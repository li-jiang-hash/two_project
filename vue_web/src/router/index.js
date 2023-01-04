import Vue from 'vue'
import VueRouter from 'vue-router'
import PersonalCenter from '../views/qiantai/PersonalCenter.vue'



Vue.use(VueRouter)

//解决vue路由重复导航错误
//获取原型对象上的push函数
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}


const routes = [

    {
        path: '/qianLogin',
        name: 'QianLogin',
        component: ()=>import('../views/qiantai/denglu/QianLogin'),
        children: []
    },{
        path: '/reset',
        name: 'Reset',
        component: ()=>import('../views/qiantai/denglu/Reset'),
        children: []
    },
    //前台首页等页面
    {
        path: '/',
        name: 'HomePage',
        component: () => import('../views/qiantai/HomePage'),
        children:[
            /*首页*/
            {
                path: '/',
                name: 'QianHome',
                component: ()=>import('../views/qiantai/shouye/QianHome'),
                children: []
            },
            //讲师招募
            {
                path: '/recruit',
                name: 'Recruit',
                component: ()=>import('../views/qiantai/shouye/teacher/Recruit'),
                children: []
            },
            //文章详情
            {
                path: '/terrace/:id',
                name: 'terrace-id',
                component: ()=>import('../views/qiantai/shouye/articledetail/articleDetail'),
            },

            /*课程中心*/
            {
                path: '/list',
                name: 'list',
                component: ()=>import('../views/qiantai/courseCenter/list'),
                children: []
            },
            {
                path: '/courses',
                name: 'courses',
                component:()=>import('../views/qiantai/courseCenter/courses'),
                children: []
            },
            /*详情页面*/
            {
                path: '/DetailId/:id',
                name: 'DetailId',
                component:()=>import('../views/qiantai/details/DetailId'),
                children: []
            },
            {
                path: '/paySuccess',
                name: 'PaySuccess',
                component:()=>import('../views/qiantai/details/PaySuccess'),
                children: []
            },
        ]
    },
    {
        path: '/apply',
        name: 'Apply',
        component: ()=>import('../views/qiantai/shouye/teacher/Apply'),
        children: []
    },
    {
        path: '/business/:name',
        name: 'Business',
        component: ()=>import('../views/qiantai/details/Business'),
        children: []
    },
    {
        path: '/businessXinX/:id',
        name: 'BusinessXinX',
        component: ()=>import('../views/qiantai/details/BusinessXinX'),
        children: []
    },
    //前台展示页面
    {
        path: '/personalCenter',
        name: 'PersonalCenter',
        component: PersonalCenter,
        children:[
            //  个人中心
            {
                path:'/geRenCenter',
                name:'GeRenCenter',
                component: () => import('../views/qiantai/zhongxin/GeRenCenter'),
                children: []
            },
            {
                path:'/updatePassword',
                name:'UpdatePassword',
                component: () => import('../views/qiantai/zhongxin/UpdatePassword'),
                children: []
            },
            {
                path:'/learningRecord',
                name:'LearningRecord',
                component: () => import('../views/qiantai/zhongxin/LearningRecord'),
                children: []
            },
            {
                path:'/myOrder',
                name:'MyOrder',
                component: () => import('../views/qiantai/zhongxin/MyOrder'),
                children: []
            },
            {
                path:'/myCart',
                name:'MyCart',
                component: () => import('../views/qiantai/zhongxin/MyCart'),
                children: []
            },
            {
                path:'/myShouCang',
                name:'MyShouCang',
                component: () => import('../views/qiantai/zhongxin/MyShouCang'),
                children: []
            },
            {
                path:'/myGuanZhu',
                name:'MyGuanZhu',
                component: () => import('../views/qiantai/zhongxin/MyGuanZhu'),
                children: []
            },
            //店铺管理,密码修改.订单查看
            {
                path:'/business',
                name:'business',
                component: () => import('../views/qiantai/jiangshi/business'),
                children: []
            },
            {
                path:'/dingdan',
                name:'dingdan',
                component: () => import('../views/qiantai/jiangshi/dingdan'),
                children: []
            },
            {
                path:'/changePassword',
                name:'changePassword',
                component: () => import('../views/qiantai/jiangshi/changePassword'),
                children: []
            },
            {
                path:'/shangpinguanli',
                name:'shangpinguanli',
                component: () => import('../views/qiantai/jiangshi/shangpinguanli'),
                children: []
            },

            {
                path:'/recordedManagement',
                name:'RecordedManagement',
                component: () => import('../views/qiantai/jiangshi/RecordedManagement'),
                children: []
            },
            {
                path:'/modifyCourse',
                name:'ModifyCourse',
                component: () => import('../views/qiantai/jiangshi/ModifyCourse'),
                children: []
            },
            {
                path:'/updateCourse',
                name:'UpdateCourse',
                component: () => import('../views/qiantai/jiangshi/updateCourse'),
                children: []
            },
            {
                path: '/hourGuanLi',
                name: 'hourGuanLi',
                component: ()=>import('../views/qiantai/jiangshi/HourGuanLi'),
                children: []
            },
        ]
    },

]

const router = new VueRouter({
    mode:'history',
    routes
})

export default router
