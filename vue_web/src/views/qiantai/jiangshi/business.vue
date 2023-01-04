<template>
  <div id="geRenCenter">
    <div class="main_box">
      <ul class="tabs clearfix">
        <a class="tab on">基础信息</a>
      </ul>
      <!--            v-if="isLogin"-->

      <div class="main_cont form" v-if="isLogin">
        <form action="" >
          <div class="form_group">
            <div class="label">店铺头像:</div>
            <div class="form_ctl upload_ctl">
              <input type="hidden" v-model="obj.bicon">
              <el-upload
                  class="avatar-uploader"
                  action="http://192.168.1.23:8000/user/upload01"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
              >
                <img style="height: 100px;width: 100px" v-if="obj.bicon" v-model="obj.bicon" :src="obj.bicon" class="avatar" alt="">
                <i v-else  class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
                            <p class="tip">* 图片尺寸为800x800，图片大小&lt;500KB，建议使用真人照片，便于品牌宣传效果</p>
            </div>
          </div>
          <div class="form_group">
            <div class="label">营业执照:</div>
            <div class="form_ctl upload_ctl">
              <input type="hidden" v-model="obj.license">
<!--              <el-upload
                  class="avatar-uploader"
                  action="http://192.168.1.23:8000/user/upload01"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
              >-->
                <img style="height: 100px;width: 100px" v-if="obj.license" v-model="obj.license" :src="obj.license" class="avatar" alt="">
                <i v-else  class="el-icon-plus avatar-uploader-icon"></i>
<!--              </el-upload>-->
<!--              <p class="tip">* 图片尺寸为800x800，图片大小&lt;500KB，建议使用真人照片，便于品牌宣传效果</p>-->
            </div>
          </div>
          <div class="form_group">
            <div class="label">店铺名:</div>
            <div class="form_ctl">
              <div class="text">{{obj.sname}}</div>
            </div>
          </div>
          <div class="form_group">
            <div class="label">店铺地址:</div>
            <div class="form_ctl">
              <div class="text">{{obj.address}}</div>
            </div>
          </div>
          <div class="form_group">
            <div class="label">商家余额:</div>
            <div class="form_ctl">
              <div class="text">{{obj.money}}</div>
            </div>
          </div>
          <div class="form_group">
            <div class="label">店铺注册人:</div>
            <div class="form_ctl">
              <div class="text">{{obj.bname}}</div>
            </div>
          </div>



          <div class="form_group">
            <div class="label">营业在状态:</div>
            <div class="form_ctl form_ctl_radio">
              <input type="radio" v-model="obj.state" class="radiobox" id="sex1" value="0" name="sex">
              <label for="sex1">营业</label>
              <input type="radio" v-model="obj.state" class="radiobox" id="sex2" value="1" name="sex">
              <label for="sex2">休息</label>

            </div>
          </div>

          <div class="form_group">
            <div class="label">&nbsp;</div>
            <div class="form_ctl">

              <el-button @click="baoCunYongHu" class="submit_btn xiugai">保存</el-button>

            </div>

            <div class="form_ctl">

              <button @click="shensu()" class="submit_btn xiugai" v-if="obj.state === 2" >申诉</button>

            </div>
          </div>
        </form>
      </div>



      <div class="main_cont form" v-else>
        <div class="form_group">
          <div class="label">店铺头像:</div>
          <div class="form_ctl upload_ctl">
            <input type="hidden" v-model="obj.bicon">
            <div class="preview">
              <img :src="obj.bicon" alt="" v-if="obj.bicon">
              <i class="iconfont" v-else>&#xe6b2;</i>
            </div>
          </div>
        </div>

        <div class="form_group">
          <div class="label">营业执照:</div>
          <div class="form_ctl upload_ctl">
            <input type="hidden" v-model="obj.license">
            <div class="preview">
              <img :src="obj.license" alt="" v-if="obj.license">
              <i class="iconfont" v-else>&#xe6b2;</i>
            </div>
          </div>
        </div>


        <div class="form_group">
          <div class="label">店铺名:</div>
          <div class="form_ctl">
            <div class="text">{{obj.sname}}</div>
          </div>
        </div>
        <div class="form_group">
          <div class="label">店铺地址:</div>
          <div class="form_ctl">
            <div class="text">{{ obj.address }}</div>
          </div>
        </div>
        <div class="form_group">
          <div class="label">商家余额:</div>
          <div class="form_ctl">
            <div class="text">{{ obj.money }}</div>
          </div>
        </div>
        <div class="form_group">
          <div class="label">店铺注册人:</div>
          <div class="form_ctl">
            <div class="text">{{ obj.bname }}</div>
          </div>
        </div>
        <div class="form_group">
          <div class="label">营业状态:</div>
          <div class="form_ctl">
            <div class="text" v-if="obj.state === 0">营业</div>
            <div class="text" v-else-if="obj.state === 1">休息</div>
            <div class="text" v-else-if="obj.state === 2">整顿</div>
          </div>
        </div>

        <div class="form_group">
          <div class="label">&nbsp;</div>
          <div class="form_ctl">
            <button @click="isLogin=true" class="submit_btn xiugai" v-if="obj.state !== 2" >修改</button>
          </div>
          <div class="form_ctl">
            <button @click="shensu()" class="submit_btn xiugai" v-if="obj.state === 2" >申诉</button>
          </div>
        </div>
      </div>
    </div>
    <!--审核的弹出层-->
    <el-dialog
        title="商家申诉"
        :visible.sync="checkvisible"
        width="30%">
      <el-form ref="checkformDataRef" :model="appeal" label-width="100px">
        <el-form-item label="备注：" style="margin-left: -40px">
          <el-input type="textarea"  placeholder="请输入内容" v-model="appeal.info"></el-input>
        </el-form-item>
      </el-form>
      <el-row style="margin-top:17px; ">
        <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="resetCheck">取 消</el-button>
        <el-button style="float:right" size="mini" type="primary" @click="Shangjiashensu">确定</el-button>
      </el-row>
    </el-dialog>
  </div>

</template>

<script>

export default {
  name: "GeRenCenter",

  data () {
    return {
      isLogin: false,
      obj: {},
      appeal:{},
      //审核的弹出层
      checkvisible:false,
    }
  },
  created () {
    //初始时加载数据，获取用户信息
    this.initMember();
  },
  methods: {
    //修改用户信息的保存方法
    baoCunYongHu(){
      // console.log(this.obj)
      var that=this;
      this.$http.post('/business/changeshop',this.obj).then(function (resp) {
        that.$message.success(resp.data.msg);
        that.isLogin=false
        //重新加载页面
        that.initMember();
      })

    },

    //商家申诉
    Shangjiashensu(){
      // console.log(this.obj)
      var that=this;
     this.appeal.businessId =  this.obj.id
      this.$http.post('/business/appeal/shangjiashensu',this.appeal).then(function (resp) {
        that.$message.success(resp.data.msg);
        that.isLogin=false
        that.checkvisible = false
        //重新加载页面
        that.initMember();
      })

    },
    //审核取消后清空
    resetCheck(){
      this.checkvisible = false
      this.obj.checkContent = ""
      this.reload()
    },
    //点击审核的弹出层
    shensu(){
      console.log(this.obj)
      this.checkvisible = true
    },


    //图片上传成功后的回调
    handleAvatarSuccess(res, file) {
      this.obj.bicon = res.data;
    },
    //图片选择后的判断，是否为图片及图片大小限制
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

    //初始化用户信息
    initMember(){
      var that=this;
      this.$http.get(`/business/showshop`).then(function (result) {
        that.obj=result.data.data;
        //console.log(result)
      })
    }
  },


}
</script>

<style lang="scss" scoped>

.upload_ctl{
  .preview{
    width: 100px;
    background-color: #f5f5f5;
    text-align: center;
    margin-bottom: 10px;
    i{

      font-size: 80px;
      color: #ddd;
    }
  }
}
.xiugai{
  margin-left: 110px;
}
.form .submit_btn {
  text-align: center;
  line-height: 0px;
  color: #fff;
}
</style>