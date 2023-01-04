<template>
  <div class="applymsg_page">
    <div class="register">
      <div class="register_body">
        <div class="register_header">
          <div class="register_logo">
            <router-link :to="{name: 'Home'}">
              <img :src="webInfo.logoImg" alt="" v-if="webInfo && webInfo.logoImg">
            </router-link>
          </div>
        </div>
        <div class="register_content">
          <div class="register_title">{{applyTitle}}</div>
          <div class="steps">
            <span class="step" :class="{step_ok: step1}">签订协议</span>
            <span class="step" :class="{step_ok: step2}">填写资料</span>
            <span class="step" :class="{step_ok: step3}">审核</span>
          </div>
          <div v-if="tab === 1">
            <div class="step_info">
              <div class="clearfix" v-html="webInfo.entryAgreement">这里是协议内容</div>
            </div>
            <footer class="info_footer">
              <input type="checkbox" id="isRead" v-model="isRead">
              <label for="isRead">
                我已阅读并同意此协议
              </label>
              <span class="c_red">（温馨提示：阅读整个协议，才能进行下一步。）</span>
            </footer>
            <div>
              <button @click="goNext()" class="next_btn">下一步</button>
            </div>
          </div>



          <div v-show="tab === 2">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
              <div class="form_group">
              <el-form-item label="店铺头像"  prop="bicon">
                <div class="form_ctl">
                <el-upload
                        class="avatar-uploader"
                        action="http://192.168.1.23:8000/business/upload"
                        :show-file-list="false"
                        :headers="token"
                        :on-success="handleAvatarSuccess1"
                        :before-upload="beforeAvatarUpload">
                  <img v-model="ruleForm.bicon" v-if="ruleForm.bicon" :src="ruleForm.bicon" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                </div>
              </el-form-item>
              </div>
              <div class="form_group">
              <el-form-item label="商铺名称" prop="sname">
                <div class="form_ctl">
                <el-input v-model="ruleForm.sname"></el-input>
                </div>
              </el-form-item>
              </div>
              <div class="form_group">
                <el-form-item label="身份证名字" prop="bname">
                  <div class="form_ctl">
                    <el-input v-model="ruleForm.bname"></el-input>
                  </div>
                </el-form-item>
              </div>
              <div class="form_group">
                <el-form-item label="营业执照"  prop="license">
                  <div class="form_ctl">
                    <el-upload
                        class="avatar-uploader"
                        action="http://192.168.1.23:8000/business/upload"
                        :show-file-list="false"
                        :headers="token"
                        :on-success="handleAvatarSuccess2"
                        :before-upload="beforeAvatarUpload">
                      <img v-model="ruleForm.license" v-if="ruleForm.license" :src="ruleForm.license" class="avatar">
                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </div>
                </el-form-item>
              </div>



              <div class="form_group">
                <el-form-item label="支付宝账号" prop="ailiappid">
                  <div class="form_ctl">
                    <el-input v-model="ruleForm.ailiappid"></el-input>
                  </div>
                </el-form-item>
              </div>

              <div class="form_group">
                <el-form-item label="身份证正面"  prop="cardfront">
                  <div class="form_ctl">
                    <el-upload
                        class="avatar-uploader"
                        action="http://192.168.1.23:8000/business/upload"
                        :show-file-list="false"
                        :headers="token"
                        :on-success="handleAvatarSuccess3"
                        :before-upload="beforeAvatarUpload">
                      <img v-model="ruleForm.cardfront" v-if="ruleForm.cardfront" :src="ruleForm.cardfront" class="avatar">
                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </div>
                </el-form-item>
              </div>

              <div class="form_group">
                <el-form-item label="身份证反面"  prop="cardreverse">
                  <div class="form_ctl">
                    <el-upload
                        class="avatar-uploader"
                        action="http://192.168.1.23:8000/business/upload"
                        :show-file-list="false"
                        :headers="token"
                        :on-success="handleAvatarSuccess4"
                        :before-upload="beforeAvatarUpload">
                      <img v-model="ruleForm.cardreverse" v-if="ruleForm.cardreverse" :src="ruleForm.cardreverse" class="avatar">
                      <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                  </div>
                </el-form-item>
              </div>

              <div class="form_group">
                <el-form-item label="身份证号" prop="idcard">
                  <div class="form_ctl">
                    <el-input v-model="ruleForm.idcard"></el-input>
                  </div>
                </el-form-item>
              </div>

              <div class="form_group">
              <el-form-item label="经营类别">
                <el-select v-model="ruleForm.sortid">
                  <el-option
                      v-for="item in options"
                      :key="item.id"
                      :label="item.sortname"
                      :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
              </div>


              <div class="form_group">
                <el-form-item label="所属地址" prop="address">
                  <div class="form_ctl">
                    <el-input v-model="ruleForm.address"></el-input>
                  </div>
                </el-form-item>
              </div>

              <div class="form_group">
                <el-form-item label="申请人手机" prop="telephone">
                  <div class="form_ctl">
                    <el-input v-model="ruleForm.telephone"></el-input>
                  </div>
                </el-form-item>
              </div>


              <div class="form_group">
              <el-form-item>
                <div class="form_ctl">
                <el-button type="primary" @click="submitForm('ruleForm')">立即申请</el-button>
                </div>
              </el-form-item>
              </div>
            </el-form>
          </div>

          <div v-show="tab === 3">
            <div class="success_msg">
              <i class="iconfont">&#xe69f;</i>
              <p class="success_one">资料提交成功！</p>
              <p class="success_two">审核结果将在2个工作日内完成，请留意。！</p>
            </div>
            <div>
              <a href="javascript:" class="next_btn" @click="goIndex">返回首页</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>


import {uploadPic} from '/src/api/upload.js'

import CodeButton from "../../../../components/common/CodeButton";


export default {
  head () {
      return {
        title: this.applyTitle,
       /* meta: [
            {
                hid: 'keywords',
                name: 'keywords',
                content: ''
            },
            {
                hid: 'description',
                name: 'description',
                content: ''
            }
        ]*/
      }
  },
  data () {

    let telephone = (rule, value, callback) => {
      let regExp = /^1(?:3\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\d|9\d)\d{8}$/;
      if (regExp.test(value) === false) {
        callback(new Error('请输入正确的手机号'));
      } else {
        callback();
      }
    };
    let idcard = (rule, value, callback) => {
      let regExp = /^1(?:3\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\d|9\d)\d{8}$/;
      if (regExp.test(value) === false) {
        callback(new Error('请输入正确的手机号'));
      } else {
        callback();
      }
    };
    return {
    /*  webInfo:
              {
                'copyright':'领课网络 版权所有 © 2015-2020',
                'icp':'粤ICP备16009964号-1',
                'prn':'粤公网安备 44010602005774号'
              }
      ,*/
      token:{
        "token":sessionStorage.getItem("token")
      },
      ruleForm: {
        bicon:'',
        sname: '',
        bname: '',
        license:'',
        telephone:'',
        ailiappid:'',
        cardfront:'',
        cardreverse:'',
        idcard:'',
        level:'',
        sortid:'',
        address:'',
        code: '',
        status:2,
        salerdeptid:'',
        state:0,

      },
      rules: {
        sname: [
          { required: true, message: '请输入店铺名称', trigger: 'blur' },
          {min:2 , max:6 ,message: '请输入长度在3到5之间的名称',trigger: 'blur'}

        ],
        bname: [
          { required: true, message: '请输入申请人名称', trigger: 'blur' },
          {min:2 , max:6 ,message: '请输入长度在3到5之间的名称',trigger: 'blur'}

        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' },
          {min:2 , max:6 ,message: '请输入长度在3到5之间的名称',trigger: 'blur'}

        ],
        idcard: [
          {  required: true, message: '请输入身份证号', trigger: 'blur' },
          { validator:idcard ,trigger: 'blur'}
        ],
        telephone: [
          {  required: true, message: '请输入手机号', trigger: 'blur' },
          { validator:telephone ,trigger: 'blur'}
        ],

      },
      clientData: '',
      webInfo: {
        'logoImg':require('@/assets/logo.jpg'),
        'entryAgreement':' '
      },
      yanzhengcode:'',
      userInfo: '',
      recruitType: 1,
      applyType: '',
      applyTitle: '',
      submitBtn: false,
      isReferralCode: false,
      sendCode: false,
      isRead: false,
      step1: true,
      step2: false,
      step3: false,
      tab: 1,
      /*定义钱前端接收容器*/
      options:[],

    }
  },
  created(){
    this.getstatus();
  },
  methods: {
    handleAvatarSuccess1(res, file) {
      this.ruleForm.bicon = res.data;
    },
    handleAvatarSuccess2(res, file) {
      this.ruleForm.license = res.data;
    },
    handleAvatarSuccess3(res, file) {
      this.ruleForm.cardfront = res.data;
    },
    handleAvatarSuccess4(res, file) {

      this.ruleForm.cardreverse = res.data;


      console.log(this.ruleForm.avatar)
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    goNext () {
      if (!this.isRead) {
        this.$msgBox({
          content: '请先阅读协议',
          isShowCancelBtn: false
        })
        return
      }
      this.step2 = true
      this.tab = 2
    },
    submitForm () {
      var that=this;
           this.$http.post("/business/storeinsert",this.ruleForm).then(function (resp) {
                  if (resp.data.code===2000){
                    that.$message({
                      message:resp.data.msg,
                      type:'success'
                    });
                    that.step3 = true
                    that.tab=3
                  }else{
                    that.$message({
                      message:resp.data.msg,
                      type:'error'
                    })
                  }
           })

    },



    //获取营业类别
    getstatus(){

      this.$http.get("/commodity/sort/findAllSort").then(result => {

        this.options=result.data.data
        console.log(this.status)
      })
    },
    goIndex () {
      this.tab = 1
      this.step2 = false
      this.step3 = false
      this.$router.push({name:'QianHome'})
    },
    goLogin () {
      this.$router.push({path: '/login'});
    },
    // 编辑器上传图片
    editorUpload (files, insert) {
      let file = files[0];
      let param = new FormData();
      param.append('picFile', file, file.name);
      uploadPic(param, function (int) {
      }).then(res => {
        insert(res)
      }).catch(() => {
        this.$msgBox({
          content: '上传图片出错，请稍后重试',
          isShowCancelBtn: false
        })
      })
    },
  },

  components: {
    CodeButton
  }
}
</script>
<style lang="scss" rel="stylesheet/scss">

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }


  hr {
    margin-bottom: 30px;
  }
  .applymsg_page {
    .register {
      background: rgb(247, 247, 247);
      padding-bottom: 30px;
      .register_body {
        width: 900px;
        margin: 0 auto 30px;
      }
      .foot_text, .text_p {
        text-align: center;
        font-size: 12px;
        color: #999;
        padding: 10px 0;
      }
    }
    .register_header {
      height: 112px;
      // padding-top: 30px;
      position: relative;
      .register_logo {
        position: absolute;
        top: 30px;
        img {
          width: 186px;
          height: 52px;
        }
      }
      .text_p {
        font-size: 14px;
        text-align: right;
        height: 18px;
        line-height: 18px;
        margin-top: 48px;
        a {
          color: #0099FF;
          &:hover {
            text-decoration: none;
          }
        }
        span {
          display: inline-block;
          width: 1px;
          height: 18px;
          background: #333;
          position: relative;
          top: 4px;
          margin: 0 10px;
        }
      }
    }
    .register_content {
      border-radius: 8px;
      background: #fff;
      padding-bottom: 30px;
      min-height: 600px;
      .register_title {
        border-radius: 8px 8px 0 0;
        height: 80px;
        line-height: 80px;
        background: rgb(228, 228, 228);
        font-size: 18px;
        text-align: center;
        color: #333;
      }
    }
    .steps {
      width: 840px;
      margin: 0 auto;
      .step {
        display: inline-block;
        line-height: 30px;
        color: #999;
        font-size: 13px;
        width: 270px;
        margin: 30px 1px;
        background: rgba(242, 242, 242, 1);
        text-align: center;
        &.step_ok {
          background: rgba(0, 153, 255, 1);
          color: #fff;
        }
      }
    }
    .step_info {
      width: 798px;
      padding: 20px;
      border: 1px solid rgb(228, 228, 228);
      margin: 0 auto;
      .info_title {
        font-size: 16px;
        font-weight: 700;
        color: #333;
        text-align: center;
      }
      .text_p {
        font-size: 14px;
        text-align: left;
        color: #333;
      }
    }
    .info_footer {
      width: 798px;
      line-height: 30px;
      padding: 0 20px;
      margin: 0 auto;
      font-size: 14px;
      border: 1px solid rgb(228, 228, 228);
    }
    .next_btn {
      display: inline-block;
      width: 320px;
      line-height: 46px;
      color: #fff;
      font-size: 14px;
      text-align: center;
      background: rgba(213, 20, 35, 1);
      border-radius: 6px;
      position: relative;
      left: 50%;
      margin-left: -160px;
      margin-top: 30px;
      border: none;
      &:hover {
        color: #fff;
        text-decoration: none;
      }
      &.b_dis {
        background: #ccc;
      }
    }
    .form_group{
      margin-bottom: 20px;
      font-size: 14px;
      .label{
        text-align: right;
        float: left;
        display: block;
        width: 110px;
        line-height: 36px;
        color: #333;
      }
      .form_ctl{
        margin-left: 12px;
        float: left;
        width: 700px;
        position: relative;
        .photo_text {
          width: 176px;
          position: absolute;
          top: 15px;
          left: 116px;
          font-size: 12px;
          color: #999;
        }
        input {
          width: 310px;
          height: 46px;
          padding-left: 10px;
          border-radius: 6px;
          font-size: 14px;
          margin-top: -5px;
          &.reset_yzm {
            width: 200px;
            padding-right: 110px;
          }
          &.check_item {
            width: 14px;
            height: 14px;
            position: relative;
            top: 3px;
          }
        }
        .yzm_btn {
          width: 100px;
          height: 46px;
          position: absolute;
          left: 210px;
          top: -5px;
          line-height: 48px;
          background: rgb(213, 20, 35);
          border-radius: 0 6px 6px 0;
          text-align: center;
          color: #fff;
          cursor: pointer;
          border: none;
          &:disabled {
            background: #999;
          }
        }
      }
      .text{
        color: #333;
        line-height: 36px;
      }
      &:after{
        content: '';
        display: block;
        clear: both;
      }
    }
    .no_phone {
      width: 96px;
      height: 96px;
      background: rgb(242, 242, 242);
      border-radius: 6px;
      position: relative;
      text-align: center;
      line-height: 80px;
      font-weight: 700;
      color: #999;
      font-size: 20px;
      span {
        font-size: 12px;
        position: absolute;
        text-align: center;
        width: 96px;
        top: 20px;
        left: 0;
        font-weight: 400;
      }
    }
    .success_msg {
      width: 380px;
      margin: 0 auto;
      position: relative;
      i {
        color: green;
        font-size: 70px;
      }
      p {
        position: absolute;
        left: 90px;
        &.success_one {
          font-size: 20px;
          color: #333;
          top: 0
        }
        &.success_two {
          color: #999;
          font-size: 14px;
          top: 44px;
          padding: 0;
          text-align: left;
        }
      }
    }
    .err{
      color: red;
      padding-top: 5px;
    }
    .user_photo {
      width: 96px;
      height: 96px;
    }
    .select_box {
      height: 80px;
      width: 146px;
      margin: -5px 3px 0 0;
    }
    .text_box {
      font-size: 14px;
      color: #000;
      padding: 10px;
      border-radius: 6px;
      resize: none;
    }
    .checks {
      display: inline-block;
      margin-left: 10px;
      &:first-child {
        margin-left: 0;
      }
    }
    .prn_icon {
      width: 16px;
      height: 16px;
      position: relative;
      top: 3px;
    }
  }
</style>
