<!-- 轮播图上的分类 -->
<template>
  <div class="class_block" @mouseleave="hideWidth" v-if="classList.length">
    <ul :class="{left_block: true, show_scroll: classList.length > 10}">
      <li @mouseenter="changeWidth(item)" :class="{active: item.id == categoryno1}" :style="classList.length >= 5 && classList.length <= 10 &&'line-height:' + (height/classList.length) + 'px;'" v-for="(item, index) in classList" :key="index">
        <router-link :to="{path: 'list', query: {categoryno1: item.id}}">{{item.categoryName}}</router-link>
        <span class="arrow"></span>
      </li>
    </ul>
    <div class="big_block clearfix" :style="'width:' + width + 'px;'">
      <div class="list_items fl clearfix">
        <div class="list_item clearfix" v-for="(item, index) in twoList" :key="index">
          <router-link :to="{path: 'list', query: {categoryno1, categoryno2: item.id}}" :class="{class_header: true, has_three: item.threeList.length, fl: true}">{{item.categoryName}}</router-link>
          <div class="fl three_box">
            <router-link :to="{path: 'list', query: {categoryno1, categoryno2: item.id, categoryno3: that.id}}" class="three_link" v-for="(that, num) in item.threeList" :key="num">{{that.categoryName}}</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name:'y-class-block',
  props: {
    height: {
      type: [String, Number]
    },
    classList: {
      default () {
        return [
          {
            'id':'108075615820572672',
            'categoryName':'龙果开源项目',
            twoList:[
               {
                 'id':'1',
                 'categoryName':'领课教育系统',
                  threeList:[]
               },
               {
                 'id':'',
                 'categoryName':'龙果支付系统',
                 threeList:[]
               },
               {
                 'id':'',
                 'categoryName':'龙果充值系统',
                 threeList:[]
               },
               {
                 'id':'1',
                 'categoryName':'龙果运维系统',
                 threeList:[]
               },
               {
                 'id':'1',
                 'categoryName':'龙果管理系统',
                 threeList:[]
               },
               {
                 'id':'1',
                 'categoryName':'龙果集成项目',
                 threeList:[]
               },
               {
                 'id':'1',
                 'categoryName':'龙果教程项目',
                 threeList:[]
               },
               {
                 'id':'1',
                 'categoryName':'龙果代码生成',
                 threeList:[]
               }
             ]
          },
          {
            'id': '108074124211781634',
            'categoryName':'一级分类',
            twoList:[
              {
                'id':'1',
                'categoryName':'二级',
                threeList:[
                  {
                    'id':'1',
                    'categoryName':'三级分类',
                  },
                  {
                    'id':'2',
                    'categoryName':'三级分类',
                  },
                  {
                    'id':'3',
                    'categoryName':'三级分类',
                  },
                ]
              },
              {
                'id':'',
                'categoryName':'二级分类',
                threeList:[]
              }
            ]
          },
          {
            'id': '1080750603160457217',
            'categoryName':'一级分类'
          },
          {
            'id': '1080387521456295937',
            'categoryName':'一级分类'
          },
          {
            'id': '1080744766018883585',
            'categoryName':'一级分类'
          },
        ]
      }
    }
  },
  data () {
    return {
      webInfo: '',
      width: 0,
      courseList: [],
      twoList: [],
      categoryno1: '',
    }
  },
  methods: {
    changeWidth (item) {
      this.width = 300
      this.categoryno1 = item.id
      this.twoList = item.twoList
      this.courseList = item.courseList
    },
    hideWidth () {
      this.width = 0
      this.categoryno1 = ''
    },
    // 跳转详情页
    goDetail (id, type) {
      let name = ''
      if (type === 1) {
        name = 'courselDetail'
      } else if (type === 2) {
        name = 'liveDetail'
      } else if (type === 3) {
        name = 'liveAndBunch'
      }
      this.$router.push({name, params: {id}})
    }
  },
  mounted () {

  }
}
</script>
<style lang="scss" scoped>
  a {
    &:hover {
      color: #D51423;
      text-decoration: none;
    }
  }
  .class_block {
    position: absolute;
    height: 100%;
    top: 0;
    left: 50%;
    z-index: 20;
    margin-left: -600px;
  }
  .left_block {
    width: 180px;
    font-size: 14px;
    height: 100%;
    background: rgba(0, 0, 0, 0.67843137254902);
    &.show_scroll {
      overflow-y: auto;
      &::-webkit-scrollbar {
        width: 4px;
      }
      &::-webkit-scrollbar-thumb {
        border-radius: 4px;
        background: rgba(245, 245, 245, 0.4);
      }
    }
    .arrow {
      display: inline-block;
      width: 5px;
      height: 5px;
      border-top: 1px solid #ccc;
      border-right: 1px solid #ccc;
      transform: rotate(45deg);
      position: absolute;
      right: 10px;
      top: 50%;
      margin-top: -3px;
    }
    li {
      line-height: 50px;
      padding: 0 10px;
      position: relative;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      &.active {
        background: rgba(255, 255, 255, 0.9);
        a {
          color: #D51423;
        }
        .arrow {
          border-color: #D51423;
        }
      }
    }
    a {
      color: #ccc;
      display: block;
      &:hover {
        color: #D51423;
      }
    }
  }
  .big_block {
    position: absolute;
    top: 0;
    left: 50%;
    height: 100%;
    margin-left: 90px;
    background: #fff;
    transition: width 0.1s;
    overflow: hidden;
    box-shadow: 1px 0px 1px rgba(0, 0, 0, 0.1);
  }
  .list_items {
    height: 100%;
    width: 300px;
    padding: 0 30px;
    overflow-y: auto;
    overflow-x: hidden;
    &::-webkit-scrollbar {
      width: 4px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 4px;
      background: rgba(210, 210, 210, 0.4);
    }
  }
  .list_item {
    padding-top: 20px;
    position: relative;
    line-height: 20px;
    .class_header {
      padding-right: 20px;
      background: #fff;
      font-size: 14px;
      font-weight: 700;
      &.has_three {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        width: 60px;
      }
    }
    .three_box {
      width: 300px;
    }
    .three_link {
      font-size: 14px;
      color: #999;
      display: inline-block;
      margin-right: 20px;
      &:hover {
        color: #D51423;
      }
    }
  }
  .foot_courses {
    padding: 0px 30px;
    height: 100%;
    width: 300px;
    background: rgba(247, 245, 248, 1);
    overflow-y: auto;
    overflow-x: hidden;
    &::-webkit-scrollbar {
      width: 4px;
    }
    &::-webkit-scrollbar-thumb {
      border-radius: 4px;
      background: rgba(0, 0, 0, 0.1);
    }
    .courses_top {
      font-size: 14px;
      font-weight: 700;
      padding: 20px 0px;
    }
    .foot_course {
      width: 220px;
      position: relative;
      margin-bottom: 20px;
    }
    .inline_box {
      display: inline-block;
      height: 43px;
    }
    .img_box {
      width: 110px;
      height: 63px;
      img {
        width: 100%;
        height: 100%;
        border-radius: 3px;
      }
    }
    .course_info {
      font-size: 14px;
      margin-left: 8px;
    }
    .course_name {
      position: absolute;
      width: 120px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      color: #333;
      top: 0;
      &:hover {
        text-decoration: none;
        color: #D51423;
      }
    }
    .course_price {
      position: absolute;
      color: #999;
      bottom: 2px;
    }
  }
</style>
