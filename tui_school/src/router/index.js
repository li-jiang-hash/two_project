import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '../views/houtai/HouLogin'
import layout from "../views/houtai/layout/HouHome"
import HouLogin from '../views/houtai/HouLogin.vue'
import APPSide from "../components/account/Side";


Vue.use(VueRouter)

const routes = [
    //后台登录页面
    {
        path: '/houLogin',
        name: 'HouLogin',
        component: login,
        children: [],
    },
    //后台管理页面
    {
        path: '/houHome',
        name: 'HouHome',
        component: layout,
        children: [
            /*系统管理*/
            {
                path:'/user/list',
                name:'员工管理',
                component: () => import('../views/houtai/acl/UserList'),
                children: []
            },
            {
                path:'/role/list',
                name:'角色管理',
                component: () => import('../views/houtai/acl/RoleList'),
                children: []
            },
            {
                path:'/menu/list',
                name:'公告管理',
                component: () => import('../views/houtai/acl/MenuList'),
                children: []
            },
            {
                path:'/userInfo/list',
                name:'会员列表',
                component: () => import('../views/houtai/student/StudentList'),
                children: []
            },
            {
                path:'/total/user',
                name:'用户统计',
                component: () => import('../views/houtai/total/userTotal'),
                children: []
            },
            {
                path:'/total/business',
                name:'店铺统计',
                component: () => import('../views/houtai/total/businessTotal'),
                children: []
            },
            {
                path:'/total/price',
                name:'店铺收入',
                component: () => import('../views/houtai/total/businessPrice'),
                children: []
            },
            /*首页管理*/
            {
                path: '/home/headNav',
                name: '头部导航',
                component: () => import('../views/houtai/HomeManagement/headManagement/HeadNavigation'),
                children: []
            },
            {
                path: '/home/lunbo',
                name: '轮播管理',
                component: () => import('../views/houtai/HomeManagement/bannerpc/LunBoManagement'),
                children: []
            },
            {
                path: '/home/youqing',
                name: '友情链接',
                component: () => import('../views/houtai/HomeManagement/homelink/HomeLink'),
                children: []
            }
            ,{
                path: '/home/foot',
                name: '底部文章',
                component: () => import('../views/houtai/HomeManagement/bottomartic/index'),
                children: []
            },
            {
                path: '/home/foottext/detail',
                name: '文章管理',
                component: () => import('../views/houtai/HomeManagement/bottomarticdetail/detailindex'),
                children: []
            },
            {
                path: '/home/zhuanqu',
                name: '专区管理',
                component: () => import('../views/houtai/HomeManagement/zhanqu/Zhuanindex'),
                children: []
            },
            {
                path: '/home/zhuanqu/business',
                name: '专区店铺',
                component: () => import('../views/houtai/HomeManagement/zhanqu/zhanqucourse/businessindex'),
                children: []
            },

            //    订单管理
            {
                path: '/order/list',
                name: '订单管理',
                component: ()=> import('../views/houtai/order/list'),
                children: []
            },
            //  课程销量统计图
            {
                path: '/business/examine',
                name: '审核审查',
                component:()=> import('../views/houtai/acl/Examine'),
                children: []
            },
            //    类别统计图
            {
                path: '/daily/chart',
                name: '统计图表',
                component:()=> import('../views/houtai/statistics/chart'),
                children: []

            },

            //商家管理
            {
                /*跟数据库e权限保持一致*/
                path: '/business/appeal',
                name: '申诉管理',
                component:()=> import('../views/houtai/shangjia/shensu'),
                children: []

            },
            {
                /*跟数据库e权限保持一致*/
                path: '/business/enter',
                name: '入驻审核',
                component:()=> import('../views/houtai/shangjia/ruzhushenhe'),
                children: []

            },

            //课程管理
            {
                path: '/goods/brand',
                name: '品牌管理',
                component:()=>import('../views/houtai/course/subject')
                ,children: []
            },
            {
                path: '/goods/deposit',
                name: '储藏方式管理',
                component:()=>import('../views/houtai/course/courselist')
                ,children: []
            },
            {
                path: '/goods/sort',
                name: '类别管理',
                component:()=>import('../views/houtai/course/sortlist')
                ,children: []
            },
            {
                path: '/goods/check',
                name: '商品审核',
                component:()=>import('../views/houtai/course/courseCheck')
                ,children: []
            },
            //讲师管理
            {
                path: '/teacher/list',
                name: '讲师列表',
                component:()=>import('../views/houtai/teacher/teacherlist')
                ,children: []
            },
            {
                path: '/teacher/check',
                name: '讲师审核',
                component:()=>import('../views/houtai/teacher/teacherCheck')
                ,children: []
            },

            //学员管理
            {
                path: '/student/list',
                name: '学员管理',
                component: () => import('../views/houtai/student/StudentList'),
                children: []
            },

            {
                path: '/dashboard',
                name: '首页',
                component: () => import('../views/houtai/dashboard/index'),
                // 当菜单文件中没有该页面配置时，在标签栏显示的就是这里配置的标题
                meta: {title: '首页'},
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
