<template>
    <div class="login_page">
        <!--        <y-header :hideTop="true" :hideSearch="true"></y-header>-->
        <div class="login_box clearfix">
            <div class="center_box">
                <div  :class="{login_form: true, rotate: tab == 2}">
                    <div :class="{tabs: true, clearfix: true, r180: tabp == 2}">
                        <div class="fl tab" @click="changetab(1)">
                            <span :class="{on: tab == 1}">登录</span>
                        </div>
                        <div class="fl tab" @click="changetab(2)">
                            <span :class="{on: tab == 2}">注册</span>
                        </div>
                    </div>
                    <div class="form_body" v-if="tabp == 1">
                        <form action="" @submit="loginSubmit" >
                            <input type="text" v-model="obj.telephone"  placeholder="请输入手机号或邮箱">
                            <div class="error_msg">{{errTip1}}</div>
                            <input type="password" v-model="obj.password" placeholder="6-20位密码，可用数字/字母/符号组合">
                            <div class="error_msg">{{errTip2}}</div>
                            <div id="changge">
                                <el-radio v-model="obj.loginType" label="USER_PHONE">用户平台</el-radio>
                                <el-radio v-model="obj.loginType" label="EMP_PHONE">商家平台</el-radio>
                            </div>
                            <input type="submit" v-if="subState" disabled="disabled" value="登录中···" class="btn" />
                            <input type="submit" v-else value="登录" class="btn" />
                        </form>
                    </div>
                    <div class="form_body r180" v-if="tabp == 2">
                        <form action="" @submit="regSubmit">
                            <input type="text" v-model="pobj.mobile" placeholder="请输入手机号">
                            <div class="phone_yzm">
                                <input type="text" name="code" placeholder="请输入手机验证码" class="phone" v-model="pobj.code" maxlength="6">
                                <button class="yzm_btn" type="button" :disabled="disabled" @click="getCode">{{txt}}</button>
                            </div>
                            <input type="password" v-model="pobj.qianPassword" placeholder="6-20位密码，可用数字/字母/符号组合">
                            <input type="password" v-model="pobj.repassword" placeholder="确认密码">
                            <div class="mgt20 font_14">
                                <input type="checkbox" id="tonyi" v-model="pobj.check">
                                <label for="tonyi">我已经阅读并同意</label><a href="jvascript:" class="c_blue" @click="xieyi = true">《用户协议》</a>
                            </div>
                            <input type="submit" v-if="subState" disabled="disabled" value="提交中···" class="btn">
                            <input type="submit" v-else value="注册" class="btn">
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="xieyi" v-if="xieyi" @click.self="xieyi = false">
            <div class="xieyi_content">
                <div class="xieyi_title">用户协议</div>
                <div class="xieyi_body">
                    <span style="color: red;text-align:center;font-size: 30px" >同意就行！！！</span>
                </div>
                <input type="button" class="xieyi_btn" value="确定" @click="xieyi = false">
            </div>
        </div>
        <div class="login_footer">
            <p>
                <span v-html="service.copyright"></span>
            </p>
            <p>
                <a href="http://www.aaa.com/" target="_blank" class="lingke_link">AAA-教育提供计算服务</a>
                <span>&nbsp;|&nbsp;</span>
                <a href="http://www.baidu.com/" class="lingke_link" target="_blank">{{service.icp}}</a>
                <span >&nbsp;|&nbsp;</span>
                <a :href="'http://www.beian.gov.cn/portal/index'" target="_blank" v-if="service.prn" class="lingke_link"><img src="@/assets/image/prn_icon.png" class="prn_icon" alt="">&nbsp;{{service.prn}}</a>
            </p>
        </div>
    </div>
</template>
<script>
    import YButton from '@/components/common/CodeButton'
    import qs from "qs"
    export default {
        components: {
            // YHeader,
            YButton
        },
        data () {
            
            return {
                /*验证码的获取*/
                txt: '获取验证码',
                disabled: false,

                service:
                    {
                        'copyright':'AAA-教育网络 版权所有 © 2018-2021',
                        'icp':'粤ICP备16009964号-1',
                        'prn':'粤公网安备 44010602005774号'
                    }
                ,

                tab: this.$route.query.tab || 1,
                tabp: this.$route.query.tab || 1,
                subState: false, //提交状态
                xieyi: false,
                errTip1: '',
                errTip2: '',
                ipInfo: {},
                obj: {
                    telephone: '123',
                    password: '123456',
                    loginType:'USER_PHONE'
                },
                pobj: {
                    mobile: '',
                    code:'',
                    qianPassword: '',
                    repassword: '',
                    check: false
                },

                //获取到的验证码
                shureCode:'',
            }
        },

        methods: {
            //验证码
            getCode () {
                var that=this;
                if (!/^1[3|4|5|8|7][0-9]\d{8}$/.test(this.pobj.mobile)) {
                    this.$message.error("请输入正确手机号码");
                    return false;
                }

                //通过手机号获取验证码
                this.$http.get('/syssystem/user/noteByPhone/'+this.pobj.mobile).then(function (result) {
                    if (result.data.code===2000){
                         that.shureCode=result.data.data;
                        //  that.timeOut();
                        console.log(result)
                    }else {
                        this.$message.error("验证码获取失败");
                    }
                })
            },
            //验证码按钮倒计时
            timeOut () {
                this.disabled = true;
                let num = 60;
                this.txt = num;
                let nt = setInterval(() => {
                    if (num <= 0) {
                        clearInterval(nt);
                        this.txt = '重新获取';
                        this.disabled = false;
                    } else {
                        num--;
                        this.txt = num + 's';
                    }
                }, 1000)
            },


            changetab (int) {
                this.tab = int;
                let _that = this;
                setTimeout(function(){
                    _that.tabp = int
                },200)
            },
            loginSubmit (e) {
                e.preventDefault();
                if (this.subState) {
                    return false;
                }
                this.errTip1 = '';
                this.errTip2 = '';
                // if (!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(this.obj.mobile.trim())) && !(/^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/).test(this.obj.mobile.trim())) {
                //     this.errTip1 = '请输入正确手机号或者邮箱';
                //     return false;
                // }
                // if (this.obj.qianPassword.length < 6) {
                //     this.errTip2 = '请输入正确的账号或密码';
                //     return false;
                // }

                var that=this;
                this.subState = true;
                this.$http.post("/syssso/login",qs.stringify(this.obj)).then(function (resp) {
                    if (resp.data.code===2000){
                        //console.log(resp.data.result)
                        that.subState = false;
                        that.$message.success(resp.data.msg);
                        sessionStorage.setItem("token", resp.data.data);
                        // sessionStorage.setItem("isTeacher", resp.data.result.isTeacher);
                        // sessionStorage.setItem("mobile", resp.data.result.mobile);
                        // sessionStorage.setItem("memberId", resp.data.result.memberId);
                        that.$router.push("/");
                    }else {
                        that.subState = false;
                        that.$message.error(resp.data.msg);
                    }
                })
                return false;
            },

            // 注册
            regSubmit: function (e) {
                e.preventDefault();
                if (this.subState) {
                    return false;
                }
                if (!(/^1[3|4|5|8|7][0-9]\d{4,8}$/.test(this.pobj.mobile.trim())) || this.pobj.mobile.trim().length !== 11) {
                    this.$message.error("请输入正确手机");
                    return false;
                }
                if (!this.pobj.code || this.pobj.code.length !== 6) {
                    this.$message.error("请输入正确的手机验证码");
                    return false;
                }
                if (this.pobj.qianPassword.length < 6 || this.pobj.qianPassword.length > 16) {
                    this.$message.error("请输入6-16位的登录密码,区分大小写,不可有空格");
                    return false;
                }
                if (this.pobj.qianPassword !== this.pobj.repassword) {
                    this.$message.error("两次输入密码不一致");
                    return false;
                }
                if (!this.pobj.check) {
                    this.$message.error("请先阅读并同意用户协议");
                    return false;
                }

                if(this.pobj.code!==this.shureCode){
                    this.$message.error("验证码错误");
                    return false;
                }

                var that=this;

                this.$http.post(`/syssystem/user/signInsert/${this.pobj.mobile}/${this.pobj.qianPassword}`).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                        that.changetab(1);
                        that.pobj={};

                    }else if(resp.data.code===4000){
                        //该手机号以注册
                        that.$message.error(resp.data.msg);
                        that.changetab(1);
                        that.pobj={};
                    } else {
                        that.$message.error(resp.data.msg);
                        that.code='';
                        that.shureCode='';
                        that.pobj={};
                    }
                })
            }
        },
        mounted () {
        },

    }
</script>

<style lang="scss" rel="stylesheet/scss"
.>
    .login_page {
        .login_box {
            height: 600px;
            background: url('../../../assets/image/login_bg.jpg') no-repeat center center;
            .center_box {
                width: 1200px;
                margin: 0 auto;
                position: relative;
            }
        }
        .login {
            background: rgb(0, 153, 255);
            .login_header {
                width: 1200px;
                margin: 0 auto;
                height: 132px;
                position: relative;
                span {
                    position: absolute;
                    height: 28px;
                    line-height: 28px;
                    font-size: 26px;
                    color: #fff;
                    border-left: 2px solid #fff;
                    padding-left: 25px;
                    top: 46px;
                    left: 210px;
                }
            }
        }
        .login_logo {
            img {
                width: 186px;
                height: 52px;
                margin-top: 32px;
            }
        }
        .login_body {
            img {
                width: 186px;
                height: 52px;
                display: block;
                margin: 0 auto;
            }
            p {
                text-align: center;
                font-size: 14px;
                color: #fff;
                margin-top: 30px;
            }
        }
        .login_form {
            width: 380px;
            position: absolute;
            top: 80px;
            right: 0px;
            border-radius: 6px;
            transition: all 0.8s;
            transform: perspective(600px);
            &.rotate {
                transform: rotateY(-180deg);
            }
            .login_title {
                height: 95px;
                line-height: 95px;
                font-size: 18px;
                padding-left: 30px;
                color: #333;
                background: #fff;
                border-radius: 6px 6px 0 0;
                &.is_login {
                    padding-left: 0px;
                    text-align: center;
                }
            }
        }
        .form_body {
            background: #fff;
            padding: 0 30px 20px;
            border-radius: 0 0 6px 6px;
            input[type='text'], input[type='password'], input[type='button'], input[type='submit'] {
                width: 310px;
                height: 46px;
                padding-left: 10px;
                padding-right: 0px;
                margin-top: 20px;
                border-radius: 6px;
                font-size: 14px;
                border-color: rgb(230, 230, 230);
                &.btn {
                    background: rgb(213, 20, 35);
                    width: 320px;
                    color: #fff;
                    border: none;
                    outline: none;
                    cursor: pointer;
                    margin-bottom: 20px;
                }
            }
            .error_msg {
                width: 310px;
                color: #D51423;
                font-size: 12px;
            }
            .next_auto {
                font-size: 14px;
                color: #333;
            }
            .is_go {
                float: right;
                color: #0099FF;
                height: 18px;
                line-height: 18px;
                font-size: 14px;
                display: inline-block;
                padding: 0 10px;
                cursor:pointer;
                &:hover {
                    text-decoration: none;
                }
            }
        }
        .phone_yzm {
            position: relative;
            .phone {
                padding-right: 100px;
                width: 210px;
                font-size: 14px;
                border-color: rgb(230, 230, 230);
                padding-left: 10px;
                height: 46px;
                border-radius: 6px;
                margin-top: 20px;
            }
        }
        .yzm_btn {
            width: 100px;
            height: 46px;
            position: absolute;
            left: 222px;
            top: 21px;
            line-height: 48px;
            border-radius: 0 6px 6px 0;
            text-align: center;
            color: #fff;
            cursor: pointer;
            border: none;
        }
        .login_footer {
            padding-bottom: 30px;
            p {
                text-align: center;
                font-size: 12px;
                color: #1E1E1E;
                margin-top: 20px;
            }
        }
        .check {
            width: 14px;
            height: 14px;
            position: relative;
            top: -1px;
        }
        .img_box {
            height: 70px;
            img {
                width: 70px;
                height: 70px;
                display: block;
                border-radius: 50%;
                margin: 0 auto;
            }
        }
        .hellow_text {
            text-align: center;
            font-size: 14px;
            padding: 20px 0;
            border-bottom: 1px solid #ccc;
        }
        .btn_box {
            border-bottom: 1px solid #ccc;
            padding-bottom: 20px;
            li {
                float: left;
                width: 140px;
                text-align: center;
                line-height: 30px;
                margin: 20px 10px 0;
                border-radius: 6px;
                a {
                    display: block;
                    background: rgb(245, 245, 245);
                    border-radius: 6px;
                    &:hover {
                        color: #fff;
                        text-decoration: none;
                        background: skyblue;
                    }
                }
            }
        }
        .out_btn {
            display: inline-block;
            margin: 10px 0 0 100px;
            font-size: 16px;
            color: #333;
            width: 120px;
            line-height: 30px;
            text-align: center;
            &:hover {
                text-decoration: none;
            }
        }
        .prn_icon {
            width: 16px;
            height: 16px;
            position: relative;
            top: 3px;
        }
        .tabs {
            height: 60px;
            line-height: 60px;
            background: #fff;
            border-radius: 8px 8px 0 0 ;
            border-bottom: 1px solid rgb(230, 230, 230);
            .tab {
                font-size: 18px;
                width: 50%;
                text-align: center;
                cursor: pointer;
                line-height: 60px;
            }
            span {
                display: inline-block;
                height: 60px;
            }
            .on {
                border-bottom: 2px solid #D51423;
                color: #D51423;
            }
        }
        .r180 {
            transform: rotateY(-180deg);
        }
        .xieyi {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0, 0, 0, 0.3);
            z-index: 3;
            .xieyi_content {
                width: 900px;
                height: 500px;
                margin: 50px auto 0;
                border-radius: 8px;
                background: #fff;
                position: relative;
            }
            .xieyi_title {
                color: #333;
                font-size: 18px;
                line-height: 70px;
                text-align: center;
            }
            .xieyi_body {
                height: 350px;
                overflow-y: auto;
                padding: 0 20px;
            }
            .xieyi_btn {
                position: absolute;
                bottom: 10px;
                width: 166px;
                height: 46px;
                background: rgba(213, 20, 35, 1);
                color: #fff;
                border: none;
                outline: none;
                cursor: pointer;
                left: 50%;
                margin-left: -83px;
                border-radius: 6px;
            }
        }
    }

    button{
        line-height: 33px;
        padding: 0;
        width: 100px;
        background-color: #107bc5;
        border: 1px solid #0f75bb;
        color: #fff;
        border-radius: 3px;
        &:disabled{
            background-color: #ccc;
            border-color: #ddd;
            color: #aaa;
        }
    }
    #change{
        padding-top: 20px ;
    }
</style>
