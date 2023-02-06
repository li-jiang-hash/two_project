<template>
    <!--头部-->
    <div class="h_header" >
        <div class="h_top">
            <div class="h_top_body">
                <ul class="top_list clearfix" >
                    <li><a href="/recruit" v-if="this.loginType=='USER_PHONE' && flag===false">入驻商家</a></li>
                    <li class="">
                        <router-link :to="{path: '/geRenCenter'}" v-if="this.loginType=='USER_PHONE'">个人中心</router-link>
                    </li>
                    <li class="">
                        <router-link :to="{path: '/business'}" v-if="this.loginType=='BUSINESS_PHONE'">我的店铺</router-link>
                    </li>
                    <li class="">
                        <router-link :to="{path: '/myorder'}" v-if="this.loginType=='USER_PHONE'">我的订单</router-link>
                    </li>
                    <li class="">
                        <router-link :to="{path: '/myCart'}" v-if="this.loginType=='USER_PHONE'">我的购物车</router-link>
                    </li>
                   <!-- <li class="s_left">
                        <router-link :to="{path: '/geRenCenter'}" v-if="this.loginType!=null">{{userInfo}}</router-link>
                    </li>-->
                    <li><a @click="tuichu" v-if="this.loginType!=null">退出</a></li>
                </ul>
                <ul class="top_list clearfix" >
                    <li class="s_left"><a href="javascript:" @click="login" v-if="this.loginType==null">登录</a></li>
                    <li><a href="javascript:" @click="register" v-if="this.loginType==null">注册</a></li>
                </ul>
            </div>
        </div>
        <div style="float: left;margin-left: 120px;margin-top: 10px">
            <a>
                <img src="@/assets/image/AAAlogo.jpg" alt="" style="margin-top: 20px">
            </a>
        </div>
        <div class="h_nav">

            <ul class="h_nav_ul clearfix" >
                <li v-for="(item, index) in navList" :key="index">
                    <router-link :class="{active: isNow === item.href}" :to="item.href" target="_self" v-if="item.skipWay==1 && item.headId!=6 && item.headId!=3 || item.headId==3 && token!='' && token!=null">{{item.headName}}</router-link>
                    <a :href="item.href" v-if="item.headId==6">{{item.headName}}</a>
                    <router-link tag="a" target="_blank" :to="item.href" v-if="item.skipWay==0">{{item.headName}}</router-link>
                </li>
            </ul>
            <div style="float: right;margin-top: 30px"  >
                <el-input placeholder="请输入菜品名或店铺名" v-model="name" v-on:keyup.enter.native="searchJIangshi" class="input-with-select">
                    <el-button v-on:click="searchJIangshi" slot="append" icon="el-icon-search"></el-button>
                </el-input>
            </div>
        </div>
    </div>
</template>
<script>

    export default {
        name: "app-header",
        props: {
            active: {
                type: String
            },
            hideTop: {
                type: Boolean
            },
            hideSearch: {
                type: Boolean
            },
        },
        data () {
            return {
                name:this.$route.params.name,
                teacherId:"",
                isTeacher:'',
                userInfo:'',
                webInfo:true,
                num: 0,
                interval: null,
                navList:[],
                isNow:'',
                prefecture:[],
                loginType:"",
                token:"",
                flag:""
            }
        },
        created () {
            var that=this;
            this.$http.get("/home/headNav/headNavigation").then(function (resp) {
                that.token = sessionStorage.getItem('token');
                console.log(that.token)
                that.navList=resp.data.data;
                //console.log(resp.data.data)
            })
            this.$http.get("/sso/isLoginAndBusiness").then(resp => {
                if(resp.data.code===2000){
                    this.loginType = resp.data.data;
                    console.log(this.loginType)
                }else{
                    this.loginType = resp.data.data
                }
            });
            this.$http.get("/business/tokenphone").then(function (resp) {
                that.flag=resp.data.data;
                console.log(resp.data.data)
            })
        },
        mounted(){
            //this.isTeacher=window.sessionStorage.getItem("isTeacher");

        },
        methods: {
            searchJIangshi(){
                this.$emit("getCourseByCourseName",this.name)
                if(!this.name){
                    return;
                }else {
                    console.log('搜索内容',this.name);
                    //路由跳转并传递参数
                    this.$router.push({
                        path: `/business/${this.name}`
                    });

                }
            },


            change () {
                this.interval = setInterval(() => {
                    if (this.num + 1 >= this.data.length) {
                        this.num = 0;
                    } else {
                        this.num++;
                    }
                }, 3000)
            },
            mout () {
                this.change();
            },
            mover () {
                clearInterval(this.interval);
            },
            //登录
            login(){
                this.$router.push("/qianLogin")
                this.$route.query.tab=1;
            },
            /*注册*/
            register(){
                this.$router.push("/qianLogin")
                this.$route.query.tab=2;
            },
            tuichu(){
                var that=this;
                this.$http.get("/sso/loginOut").then(function (resp) {
                    if (resp.data.code===2000){
                        sessionStorage.clear();//清空sessionStorage中的请求头
                        that.$router.push("/qianLogin");
                  }
              })

            }
        },
    }
</script>
<style lang="scss" rel="stylesheet/scss" scoped>

    .h_header {
        background: #fff;
    }
    .h_top {
        background: rgb(51, 51, 51);
        .h_top_body {
            width: 1200px;
            height: 30px;
            margin: 0px auto;
        }
    }
    .top_list {
        float: right;
        li {
            list-style-type:none;
            float: left;
            color: #ccc;
            font-size: 12px;
            margin-top: 9px;
            padding: 0px 10px 0px 0px;
            position: relative;
            img {
                width: 14px;
                height: 13px;
                position: absolute;
                left: -20px;
            }
            .vip_icon {
                position: relative;
                left: 0;
                top: 1px;
                cursor: pointer;
            }
            a {
                color: #ccc;
                &:hover {
                    text-decoration: none;
                    color: #fff;
                }
                &.c_gold {
                    color: #CA9E70;
                }
            }
            &.s_left {
                border-left: 1px solid #ccc;
                padding-left: 10px;
            }
        }
    }
    .h_nav {
        width: 1200px;
        margin: 0 auto;
        height: 110px;
        position: relative;
        .go_index {
            position: absolute;
            right: 10px;
            bottom: 20px;
        }
    }
    .h_logo {
        display: inline-block;
        position: absolute;
        top: 25px;
        // left: 20px;
        img {
            height: 52px;
        }
    }
    .h_nav_ul {
        display: inline-block;
        margin-left: 100px;
        li {
            float: left;
            height: 110px;
            line-height: 110px;
            font-size: 16px;
            padding: 0 15px;
            .active {
                color: red;
            }
            a:hover {
                color: red;
                text-decoration: none;
            }
        }
    }
    .search_box  {
        float: right;
        width: 300px;
        background-color: #e2e2e2;
        border-radius: 8px;
    }

    .search_input{
        padding-left: 12px;
        color: #333;
        height: 30px;
        border: none;
        width: 243px;
        overflow: hidden;

        background-color: #e2e2e2;
        border-radius: 8px;
    }

</style>