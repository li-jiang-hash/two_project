<template>
  <header class="side_box">
    <div class="menu_panel" v-if="showSide === 'zm'">
      <h3 class="title">招募中心</h3>
      <ul class="menus">
        <li :class="{on: type == 'jszm'}"><router-link :to="{name: 'teacherRecruit'}">商家招募</router-link></li>
      </ul>
    </div>
    <div class="menu_panel" v-for="(item, index) in list" :key="index" v-if="showSide === 'wm'">
      <h3 class="title">{{item.name}}</h3>
      <ul class="menus">
        <li  @click="changeAbout(that.id)" :class="{on: now == that.id}" v-for="(that, index)  in item.children" :key="index">
          <router-link  :to="{name: 'terrace-id', params: {id: that.id}}">{{that.name}}</router-link>
        </li>
      </ul>
    </div>
  </header>
</template>
<script>


export default {
  props: {
    type: {
      type: String,
      default: 'jszm'
    },
    showSide: {
      type: String,
      default: 'zm'
    },

  },
  data () {
    return {
      list: [],
      now: ''
    }
  },
  methods: {
    changeAbout (id) {
      this.now = id
      this.$router.push({name: 'terrace-id', params: {id}})
    },
  },
 /* created() {
    var that=this;
    //获取所有的文章分类
    this.$http.get("home/qian/bottomArticle/getQianAllBottomArticle").then(function (resp) {
      that.list=resp.data.result;
    })
  },*/
  mounted () {
    if (this.$route.params.id) {
      this.now = this.$route.params.id
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
