<template>
    <div>
        <el-header id="header">
            <span class="hideAside" @click="collapse">菜先生<i class="fa fa-indent fa-lg"></i></span>
            <ul class="personal">
                <li class="fullScreen" @click="fullScreen">
                    <el-tooltip class="item" effect="dark" content="全屏" placement="bottom">
                        <i class="fa fa-arrows-alt fa-lg">CAI</i>
                    </el-tooltip>
                </li>
                <li>
                    <el-dropdown @command="handleCommand">
                      <span class="el-dropdown-link">
                        可可&奈奈<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item command="editPassword">修改密码</el-dropdown-item>
                                <el-dropdown-item command="houLogout" divided>退出</el-dropdown-item>
                            </el-dropdown-menu>
                    </el-dropdown>
                </li>
                <li class="icon"><img :src="imgUrl"/></li>
            </ul>
        </el-header>
        <tabNav></tabNav>

        <!--修改密码的抽屉-->
        <el-drawer
                title="修改密码"
                :visible.sync="drawer"
                direction="rtl"
                @closed="restPassword"
                style="padding-top:50px"
                >
            <el-form ref="updatePassword" :model="updatePasswordDate"  :rules="passwordRules"  label-width="90px" style=";margin-top: 50px"
                     class="demo-ruleForm">
                <el-form-item label="原密码" prop="oldPassword" style=";margin-top: 50px">
                    <el-input type="password" v-model="updatePasswordDate.oldPassword"  autocomplete="off" style="width: 320px"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="newPassword" style=";margin-top: 50px">
                    <el-input type="password" v-model="updatePasswordDate.newPassword"  autocomplete="off" style="width: 320px"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPassword" style=";margin-top: 50px">
                    <el-input type="password" v-model="updatePasswordDate.checkPassword" autocomplete="off" style="width: 320px"></el-input>
                </el-form-item>
                <el-form-item style=";margin-top: 50px">
                    <el-button type="primary" style="margin-right: 100px" @click="readyUpdate">提交</el-button>
                    <el-button @click="restPassword">重置</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </div>
</template>

<script>

    import tabNav from "./tabNav"

    export default {
        name: "Header",
        components: {
            tabNav,
        },
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === "") {
                    callback(new Error("请输入密码"))
                } else if (value.toString().length < 6) {
                    callback(new Error("密码长度不能低于6位"))
                } else {
                    if (this.updatePasswordDate.checkPassword !== "") {
                        this.$refs.updatePassword.validateField("checkPassword")
                    }
                    callback()
                }
            }
            var authenticationPass = (rule, value, callback) => {
                if (value !== sessionStorage.getItem("password")) {
                    callback(new Error("密码输入错误，请重新输入"))
                } else {
                    callback()
                }
            }
            var validatePass2 = (rule, value, callback) => {
                if (value === "") {
                    callback(new Error("请再次输入密码"))
                } else if (value.toString().length < 6) {
                    callback(new Error("密码长度不能低于6位"))
                } else if (value !== this.updatePasswordDate.newPassword) {
                    callback(new Error("两次输入密码不一致!"))
                } else {
                    callback()
                }
            };
            return {
                drawer:false,
                //头像
                imgUrl:require("@/assets/img/3.jpg"),
                isfullScreen: true,
                avatar: "./static/images/icon.jpg",
                dialogPassVisible: false,
                title: "",
                userId: "",
                phone: '',

                //修改密码的数据
                updatePasswordDate: {
                    oldPassword: "",
                    newPassword: "",
                    checkPassword: ""
                },
                //修改密码的规则
                passwordRules: {
                    oldPassword: [
                        {required: true, validator: validatePass, trigger: "blur"},
                        {required: true, validator: authenticationPass, trigger: "blur"}
                    ],
                    newPassword: [
                        {required: true, validator: validatePass, trigger: "blur"}
                    ],
                    checkPassword: [
                        {required: true, validator: validatePass2, trigger: "blur"}
                    ]
                }
            }
        },
        created(){
            console.log(this.phone = sessionStorage.getItem("houtelephone"));
        },
        methods: {
            collapse() {
                // this.$store.dispatch("collapse")
            },
            editPwdCallback() {
                this.dialogPassVisible = false
            },

            //刷新密码抽屉
            restPassword(){
                this.updatePasswordDate={
                    oldPassword: "",
                    newPassword: "",
                    checkPassword: ""
                };
                this.$refs.updatePassword.resetFields();
            },

            fullScreen() {
                if (this.isfullScreen) {
                    //获取头部导航右键的操作，，关闭其他，关闭当前，关闭所有
                    var docElm = document.documentElement
                    if (docElm.requestFullscreen) {
                        docElm.requestFullscreen()
                    } else if (docElm.mozRequestFullScreen) {
                        docElm.mozRequestFullScreen()
                    } else if (docElm.webkitRequestFullScreen) {
                        docElm.webkitRequestFullScreen()
                    } else if (elem.msRequestFullscreen) {
                        elem.msRequestFullscreen()
                    }
                    this.isfullScreen = false
                } else {
                    if (document.exitFullscreen) {
                        document.exitFullscreen()
                    } else if (document.mozCancelFullScreen) {
                        document.mozCancelFullScreen()
                    } else if (document.webkitCancelFullScreen) {
                        document.webkitCancelFullScreen()
                    } else if (document.msExitFullscreen) {
                        document.msExitFullscreen()
                    }
                    this.isfullScreen = true
                }
            },
            handleCommand(command) {
                if (command === "editPassword") {//修改密码操作
                    this.drawer=true;
                } else if (command === "houLogout") {//退出操作
                    this.$store.dispatch("removeOtherTab", {all: true, router: this.$router,p:'pp'})
                    this.tuichu();
                }
            },

            //退出登录方法
            tuichu(){
                var that=this;
                this.$http.get("/syssso/logout").then(function (resp) {
                    sessionStorage.clear();//清空sessionStorage中的请求头
                    that.$router.push("/houLogin")//重定向到登录页面
                })
            },

            //修改密码方法
            readyUpdate(){
                console.log("mia");
                var that=this;
                this.$refs.updatePassword.validate((valid) => {//判断表单内是否有标红的地方
                console.log(valid);
                    if (valid) {
                        this.$http.post(`/syssystem/e-emp-info/updatePassword/${this.phone}/${this.updatePasswordDate.newPassword}`).then(function (resp) {
                            if (resp.data.code === 2000) {//修改成功
                                that.$message.success(resp.data.msg);
                                that.tuichu();
                            } else {
                                that.$message.error(resp.data.msg);
                            }
                        })
                    }else {
                        that.$message.error("修改有误");
                    }
                })
            }
        }
    }
</script>

<style lang="scss">

    $top: top;
    $bottom: bottom;
    $left: left;
    $right: right;
    $leftright: ($left, $right);
    %w100 {
        width: 100%;
    }

    %h100 {
        height: 100%;
    }

    %cursor {
        cursor: pointer;
    }

    html, body, #app, .el-container, #asideNav, ul.el-menu {
        @extend %h100;
    }

    @mixin set-value($side, $value) {
        @each $prop in $leftright {
            #{$side}-#{$prop}: $value;
        }
    }

    #header {
        max-height: 50px;
        line-height: 50px;
        box-shadow: 0 1px 2px 0 rgba(0, 0, 0, .05);
        display: flex;
        /*background-color: #d3dce6 !important;*/
        justify-content: space-between;

        .hideAside {
            @extend %cursor;
        }

        .personal {
            display: flex;
            flex-direction: row;

            li {
                @include set-value(margin, 13px);
                font-size: 13px;
            }

            .fullScreen {
                @extend %cursor;
            }

            .el-dropdown-link {
                @extend %cursor;
            }

            .icon img {
                margin-#{$top}: 7px;
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
                width: 40px;
                height: 40px;
            }
        }
    }
</style>
