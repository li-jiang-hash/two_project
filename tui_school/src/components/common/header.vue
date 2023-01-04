<template>
    <!--头部-->
    <div class="h_header" v-if="!hideTop">
        <div class="h_top">
            <div class="h_top_body">
                <ul class="top_list clearfix" >
                    <li class="s_left"><a href="javascript:" @click="login">登录</a></li>
                    <li><a href="javascript:" @click="register">注册</a></li>
                </ul>
            </div>
        </div>
        <div class="h_nav">
            <div class="h_logo">
                <a >
                    <img src="@/assets/image/activity/logo.jpg" alt="" v-if="webInfo">
                </a>
            </div>
            <ul class="h_nav_ul clearfix" v-if="!hideTop && navList">
                <li v-for="(item, index) in navList" :key="index">
                    <router-link :class="{active: isNow === item.navUrl}" :to="item.navUrl" :target="item.target">{{item.navTitle}}</router-link>
                </li>
            </ul>
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
                webInfo:true,
                num: 0,
                interval: null,
                navList:[
                    {
                        'navUrl':'1',
                        'navTitle':'首页'
                    },
                    {
                        'navUrl':'2',
                        'navTitle':'课程中心'
                    },
                    {
                        'navUrl':'3',
                        'navTitle':'龙果学院'
                    },
                    {
                        'navUrl':'4',
                        'navTitle':'系统介绍'
                    },
                    {
                        'navUrl':'5',
                        'navTitle':'体验环境(商业版)'
                    }
                ],
                isNow:'',
            }
        },
        methods: {
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
            login(){
                this.$router.push("/qianLogin")
            },
            register(){

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
        margin-left: 280px;
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
</style>