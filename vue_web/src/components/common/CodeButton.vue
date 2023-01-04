<template>
  <button class="yzm_btn" type="button" :disabled="disabled" @click="getCode">{{txt}}</button>
</template>
<script>
export default {
  props: {
    mobile: {
      // 手机号
      type: [String, Number],
      default: 1
    }
  },
  data () {
    return {
      txt: '获取验证码',
      disabled: false,
      //获取到的验证码
      shureCode:'',
    }
  },
  computed: {

  },
  methods: {
    getCode () {
      var that=this;
      if (!/^1[3|4|5|8|7][0-9]\d{8}$/.test(parseInt(this.mobile))) {
        this.$message.error("请输入正确手机号码");
        return false;
      }
      this.$emit('cb', event)
      this.timeOut()


      //通过手机号获取验证码
      this.$http.get(`/student/member/getYanZhengMa/${this.mobile}`).then(function (resp) {
        if (resp.data.code===2000){
          that.shureCode=resp.data.result;
          that.timeOut();
          that.$emit('cb', that.shureCode)
        }else {
          this.$message.error("验证码获取失败");
        }
      })

    },
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
    }
  }
}
</script>
<style lang="scss" scoped>
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
</style>
