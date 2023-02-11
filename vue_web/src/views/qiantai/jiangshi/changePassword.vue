<template>
    <div class="updatePassword">
        <ul class="tabs clearfix">
            <a class="tab on">修改密码</a>
        </ul>
        <div class="main_cont form">
            <form action="" @submit="regSubmit">
                <div class="form_group">
                    <div class="label">手机号:</div>
                    <div class="form_ctl">
                        <input type="text" :disabled="true" v-model="pobj.mobile" class="form_input" placeholder="请输入手机号">
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">验证码:</div>
                    <div class="form_ctl">
                        <input type="text" v-model="pobj.code" maxlength="6" class="form_input" placeholder="请输入手机验证码">
                        <button class="yzm_btn" type="button" :disabled="disabled" @click="getCode">{{txt}}</button>
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">重置密码:</div>
                    <div class="form_ctl">
                        <input type="password" v-model="pobj.newPassword" class="form_input" placeholder="请输入密码">
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">确定密码:</div>
                    <div class="form_ctl">
                        <input type="password" v-model="pobj.confirmPassword" class="form_input" placeholder="请再次输入新密码">
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">&nbsp;</div>
                    <div class="form_ctl" >
                        <button class="submit_btn"  v-if="submitBtn">确认</button>
                        <button class="submit_btn" v-else disabled="disabled">确认</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        name: "UpdatePassword",
        data () {
            return {
                submitBtn: false,
                errTip1: '',
                errTip2: '',
                errTip3: '',
                pobj: {
                    mobile: '',
                    code: '',
                    newPassword: '',
                    confirmPassword: ''
                },
                //获取到的验证码
                shureCode:'',
                /*验证码的获取*/
                txt: '获取验证码',
                disabled: false,
            }
        },
        mounted() {
            this.pobj.mobile = sessionStorage.getItem("telephone")
            },
        methods: {
            //返回修改结果信息
            showMsg (msg) {
                this.$message({
                    type: "error",
                    message: msg,
                });
            },
            //用于确定修改操作
            regSubmit: function (e) {
                e.preventDefault();
                if (!this.pobj.code) {
                    this.showMsg('请输入手机验证码')
                    return false;
                }
                if (this.pobj.newPassword.length < 6 || this.pobj.newPassword.length > 16) {
                    this.showMsg('请输入6-16位的登录密码,区分大小写,不可有空格')
                    return false;
                }
                if (this.pobj.newPassword !== this.pobj.confirmPassword) {
                    this.showMsg('两次输入密码不一致')
                    return false;
                }

                if(this.pobj.code!==this.shureCode){
                    this.$message.error("验证码错误");
                    return false;
                }

                var that=this;

                this.$http.post(`/syssystem/e-emp-info/updatePassword/${this.pobj.mobile}/${this.pobj.newPassword}`).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                        that.$router.push("/qianLogin")
                    }else {
                        that.$message.error(resp.data.msg);
                    }
                })

            },

            //验证码
            getCode () {
                var that=this;
                this.submitBtn = true;
                //通过手机号获取验证码
                this.$http.get(`/syssystem/user/noteByPhone/${this.pobj.mobile}`).then(function (resp) {
                    if (resp.data.code===2000){
                        that.shureCode=resp.data.data;
                        that.timeOut();
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

        }
    }
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
    .yzm_btn{
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
    .submit_btn{
        margin-left: 110px;
    }
</style>