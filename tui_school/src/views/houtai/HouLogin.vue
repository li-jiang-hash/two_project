<template>
  <div id="houLogin">
    <div class="loginConbox">
      <div class="header"></div>
      <div class="loginBox">
        <div class="loginCon">
          <p class="title"><a href="http://localhost:8085/">☆</a>CAI先生后台登录系统</p>
          <el-card shadow="always" class="login-module" v-if="smdl">
            <div slot="header" class="clearfix formTitlt">
              <span>密码登录</span>
            </div>
            <el-form
              :model="loginFormData"
              status-icon
              label-width="100px"
              class="demo-ruleForm"
              :rules="loginFromRule"
              ref="loginFromRef"
            >
              <el-form-item prop="username">
                <el-input
                  type="text"
                  v-model="loginFormData.telephone"
                  auto-complete="off"
                  placeholder="请输入登录账号"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  type="password"
                  v-model="loginFormData.password"
                  auto-complete="off"
                  placeholder="请输入登录密码"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  class="subBtn"
                  type="primary"
                  @click="HouLogin"
                  @keydown.enter=""
                  >登录</el-button
                >
              </el-form-item>
            </el-form>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import qs from "qs";
export default {
  name: "houLogin",
  data() {
    return {
      smdl: true,
      //登录信息都是一起发送过去的
      loginFormData: {
        telephone: "110",
        password: "123456",
        loginType: "EMP_PHONE",
      },
      loginFromRule: {
        username: [
          // {required: true, message: '内容不能为空', trigger: 'blur'},
          // {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 5, max: 16, message: "密码的长度必须5~16", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    const t = this;
    document.onkeydown = function (e) {
      if (window.event == undefined) {
        var key = e.keyCode;
      } else {
        var key = window.event.keyCode;
      }
      //enter的ASCII码是13
      if (key == 13) {
        t.HouLogin();
      }
    };
  },
  methods: {
    //表单重置
    resetFrom() {
      //表单的重置  resetFields()
      this.$refs.loginFromRef.resetFields();
    },
    //登录验证
    HouLogin() {
      const that = this;
      //验证输入框都符合了才能提交
      this.$refs.loginFromRef.validate((valid) => {
        if (valid) {
          this.$http
            .post("/syssso/login", qs.stringify(this.loginFormData))
            .then( resp => {
              if (resp.data.code === 2000) {
                this.$message.success(resp.data.msg);
                sessionStorage.setItem("token", resp.data.data);
                sessionStorage.setItem("houtelephone", that.loginFormData.telephone);
                sessionStorage.setItem("password", that.loginFormData.password);
                this.$router.push("/dashboard");
              } else {
                this.$message.error(resp.data.msg);
              }
            }).then(
              this.$http
              .get(`/syssystem/login?telephone=${this.loginFormData.telephone}`)
              .then(resp => {
                if (resp.data.code === 2000) {
                  sessionStorage.setItem("rid",resp.data.data.rid)
                  sessionStorage.setItem("checker",resp.data.data.ename)
                  this.$message.success(resp.data.msg);
                } else {
                  this.$message.error(resp.data.msg);
                }
              })
            );
        }
      });
    },
  },
};
</script>

<style lang="scss">
#houLogin {
  width: 100%;
  height: 100%;
  background-color: #2d3a4b;

  .loginConbox {
    background: #2d3a4b;
  }

  .header {
    height: 60px;
    position: relative;
    background: #2d3a4b;
    /*border-bottom: 1px solid rgba(255, 255, 255, 0.3);*/
    .logo {
      margin-left: 30px;
      width: 500px;
      float: left;
      height: 40px;
      padding-top: 10px;

      img {
        height: 100%;
      }
    }
  }

  .loginBox {
    .iconcolor {
      color: #409eff;
    }

    padding: 74px 0 118px;

    .loginCon {
      width: 990px;
      margin: auto;
      position: relative;
      height: 388px;

      .el-card__header {
        border-bottom: 0px;
      }

      .title {
        font-size: 36px;
        font-weight: 600;
        color: #ffffff;
        width: 500px;
        float: left;
        margin-top: 0px;

        &:first-child {
          font-size: 34px;
          margin-top: 50px;
          margin-bottom: 30px;
        }
      }

      .login-module {
        width: 380px;
        height: 325px;
        margin-top: 60px;
        border: none;
        position: absolute;
        right: 0;

        .formTitlt {
          font-size: 18px;
          font-weight: 400;

          .titIconbox {
            float: right;

            .pointer {
              cursor: pointer;
            }
          }
        }

        .smalltxt {
          text-align: right;

          .a {
            text-decoration: none;
            color: #999999;
            font-size: 12px;
            margin-left: 8px;
          }
        }
      }

      .el-form-item__content {
        margin-left: 0px !important;

        .subBtn {
          width: 100%;
        }
      }
    }

    .el-input__inner,
    .el-button,
    .el-card,
    .el-message {
      border-radius: 0px !important;
    }

    .el-form-item__content .ico {
      position: absolute;
      top: 0px;
      left: 0px;
      z-index: 999;
      width: 40px;
      height: 39px;
      text-align: center;
      border-right: 1px solid #ccc;
    }

    .ewmbox {
      width: 100%;
      height: 240px;
      margin-top: -25px;

      .ewm {
        width: 140px;
        height: 140px;
        margin: 20px auto;

        p {
          font-size: 12px;
          padding-left: 40px;
          margin: 0;
        }
      }

      .ewmicon {
        width: 140px;
        margin: 20px auto 0;

        .iconfont {
          float: left;
        }

        p {
          font-size: 12px;
          padding-left: 40px;
          margin: 0;
        }
      }
    }
  }
}
</style>
