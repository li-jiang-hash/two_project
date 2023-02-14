<template>
  <div>
    <div class="person_body clearfix">
      <y-side :showSide="'wm'"  ></y-side>
      <div class="person_content">
        <div class="person_title">
          {{webInfo.artTitle}}
        </div>
        <div class="person_info">
          <p class="top_text" v-html="webInfo.artDesc"></p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import YSide from '/src/components/TerraceSide'


export default {
  head () {
      return {
        title: this.webInfo.artTitle,
      }
  },
  data () {
    return {
      webInfo: '',
      id:this.$router.history.current.params.id
    }
  },
  created() {
    //根据id获取全部信息
    this.get()
  },
  watch:{
    $route: {
      handler: function(val, oldVal){
       this.id=val.params.id;
       this.get()
      }
    }
  },
  methods:{
    get(){
      var that=this;
      this.$http.post(`/syssystem/tb-article/getArticle?id=`+this.id).then(function (resp) {
        that.webInfo=resp.data.data;
      })
    }
  },
  mounted () {
  },
  components: {
    YSide
  }
}
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
  .person_body {
    width: 1200px;
    margin: 30px auto 0;
    min-height: 1000px;
  }
  .person_content {
    width: 1012px;
    float: right;
    background: #fff;
    border-radius: 6px;
    .person_title {
      font-size: 14px;
      font-weight: 700;
      color: #333;
      text-align: center;
      line-height: 100px;
    }
  }
  .person_info {
    padding: 0px 50px 50px;
    min-height: 400px;
    font-size: 14px;
    color: #333;
    line-height: 28px;
  }
</style>
