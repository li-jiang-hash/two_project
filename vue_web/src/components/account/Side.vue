<template>
  <header class="side_box">
    <div class="menu_panel" v-if="shenfen==='BUSINESS_PHONE'">
      <h3 class="title"><i class="iconfont">&#xe640;</i>商家中心</h3>
      <ul class="menus">
        <li :class="{on: type === 'jsxx'}" @click="changeColor('jsxx')"><router-link :to="{path: '/business'}">商铺管理</router-link></li>
<!--        <li :class="{on: type === 'kcgl'}" @click="changeColor('kcgl')" ><router-link :to="{path: '/recordedManagement'}">录播管理</router-link></li>-->
        <li :class="{on: type === 'changepass'}" @click="changeColor('changepass')" ><router-link :to="{path: '/changePassword'}">修改密码</router-link></li>
        <li :class="{on: type === 'dingdan'}" @click="changeColor('dingdan')" ><router-link :to="{path: '/dingdan'}">订单管理</router-link></li>
        <li :class="{on: type === 'spgl'}" @click="changeColor('spgl')" ><router-link :to="{path: '/shangpinguanli'}">商品管理</router-link></li>
<!--        <li :class="{on: type === 'tjsp'}" @click="changeColor('tjsp')" ><router-link :to="{path: '/tianjiashangpin'}">添加商品</router-link></li>-->





      </ul>
    </div>
    <div class="menu_panel" v-if="shenfen==='USER_PHONE'">
      <h3 class="title"><i class="iconfont">&#xe60a;</i>个人资料</h3>
      <ul class="menus">
        <li :class="{on: type === 'grxx'}" @click="changeColor('grxx')"><router-link :to="{path: '/geRenCenter'}">个人信息</router-link></li>
        <li :class="{on: type === 'xgmm'}" @click="changeColor('xgmm')"><router-link :to="{path: '/updatePassword'}">修改密码</router-link></li>
        <li :class="{on: type === 'wddd'}" @click="changeColor('wddd')"><router-link :to="{path: '/myOrder'}">我的订单</router-link></li>
        <li :class="{on: type === 'gwc'}" @click="changeColor('gwc')"><router-link :to="{path: '/myCart'}">购物车</router-link></li>
<!--        <li :class="{on: type == 'xxjl'}"><router-link :to="{path: '/learningRecord'}">学习记录</router-link></li>-->
        <li :class="{on: type === 'xxxjl'}" @click="changeColor('xxxj')"><router-link :to="{path: '/myShouCang'}">收藏商品</router-link></li>
        <li :class="{on: type === 'xajl'}" @click="changeColor('xajl')"><router-link :to="{path: '/myGuanZhu'}">收藏店铺</router-link></li>
      </ul>
    </div>
  </header>
</template>
<script>
export default {

  data () {
    return {
      teacher: true,
      type:"",
      shenfen:"",

    }
  },
  created() {
    //获取身份
    this.init();
  },
  methods: {
    init(){
      this.$http.post("user/getShenfen").then(res=>{
        if (res.data.code===2000){
          this.shenfen = res.data.data;
        }
      })
    },
    changeColor(obj){
      this.type=obj;
    }
  },
  mounted () {
      var tea=window.sessionStorage.getItem("isTeacher");
      if (tea==='1'){
        this.teacher=true;
      }else {
        this.teacher=false;
      }
  }
}
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .side_box{
    border-radius: 6px 6px 0 0;
    float: left;
    width: 168px;
  }
  .menu_panel{
    background-color: #fff;
    margin-bottom: 20px;
    border-radius: 6px 6px 0 0;
    .act_icon {
      width: 16px;
      margin-right: 2px;
    }
    .title{
      font-size: 14px;
      color: #333;
      line-height: 60px;
      padding: 0 35px;
      border-bottom: 1px solid rgb(242, 242, 242);
      i{
        padding-right: 6px;
        font-size: 16px;
        position: relative;
        left: -5px;
      }
    }
  }
  .menus{
    li{
      line-height: 40px;
      // padding-left: 35px;
      text-align: center;
      &:hover,&.on{
        a{
          color: #d51423;
        }
      }
    }
    a{
      color: #333;
      font-size: 14px;
    }
  }
</style>
