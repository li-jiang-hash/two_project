<template>
  <header class="side_box">
    <div class="menu_panel">
      <h3 class="title"><i class="iconfont">&#xe640;</i>讲师中心</h3>
      <ul class="menus">
        <li :class="{on: type == 'jsxx'}"><router-link :to="{path: '/teacherCenter'}">讲师信息</router-link></li>
        <li :class="{on: type == 'kcgl'}"><router-link :to="{path: '/recordedManagement'}">录播管理</router-link></li>
      </ul>
    </div>
    <div class="menu_panel">
      <h3 class="title"><i class="iconfont">&#xe60a;</i>个人资料</h3>
      <ul class="menus">
        <li :class="{on: type == 'grxx'}"><router-link :to="{path: '/geRenCenter'}">个人信息</router-link></li>
        <li :class="{on: type == 'xgmm'}"><router-link :to="{path: '/updatePassword'}">修改密码</router-link></li>
        <li :class="{on: type == 'wddd'}"><router-link :to="{path: '/myOrder'}">我的订单</router-link></li>
        <li :class="{on: type == 'xxjl'}"><router-link :to="{path: '/learningRecord'}">学习记录</router-link></li>
      </ul>
    </div>
  </header>
</template>
<script>
export default {
  props: {
    type: {
      type: String,
      default: 'grxx'
    }
  },
  data () {
    return {
      teacher: true,

    }
  },
  methods: {
    close () {
      this.$emit('close')
    }
  },
  mounted () {

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
          color: #D51423;
        }
      }
    }
    a{
      color: #333;
      font-size: 14px;
    }
  }
</style>
