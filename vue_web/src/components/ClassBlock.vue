<!-- 轮播图上的分类 -->
<template>
    <div class="class_block" v-if="classList.length">
        <ul :class="{left_block: true, show_scroll: classList.length > 10}">
            <li :class="{active: item.id == categoryno1}"
                :style="classList.length >= 5 && classList.length <= 10 &&'line-height:' + (height/classList.length) + 'px;'"
                v-for="(item, index) in classList" :key="index">
                <!--        <router-link :to="{name: 'list', query: {categoryno1: item.id}}">{{item.sortname}}</router-link>-->
                <div class="goIndex" @click="go('demoItem'+index)">{{item.zoneName}}</div>
                <span class="arrow"></span>
            </li>
        </ul>
        <!--<div class="big_block clearfix" :style="'width:' + width + 'px;'">
          <div class="list_items fl clearfix">
            <div class="list_item clearfix" v-for="(that, index) in twoList" :key="index">
              <router-link :to="{name: 'list', query: {categoryno1, categoryno2: that.id}}" :class="{class_header: true, has_three: that.children.length, fl: true}">{{that.title}}</router-link>
              <div class="fl three_box">
                <router-link :to="{name: 'list', query: {categoryno1, categoryno2: that.id, categoryno3: sonthat.id}}" class="three_link" v-for="(sonthat, num) in that.children" :key="num">{{sonthat.title}}</router-link>
              </div>
            </div>
          </div>
        </div>-->
    </div>
</template>
<script>

    export default {
        name: 'y-class-block',
        props: {
            height: {
                type: [String, Number]
            },
            classList: {}
        },
        data() {
            return {
                webInfo: '',
                width: 0,
                courseList: [],
                twoList: [],
                categoryno1: '',
            }
        },
        methods: {
            go(selector) {
                document.getElementById(selector).scrollIntoView({behavior: 'smooth'});
                // console.log(selector)
                // var anchor = this.$el.querySelector(selector) // 参数为要跳转到的元素id
                // console.log(anchor)
                // document.body.scrollTop = anchor.offsetTop; // chrome
                // document.documentElement.scrollTop = anchor.offsetTop; // firefox
            },
            toZhuanqu(index) {
                console.log(index)
                this.idx = index;
                VueScrollTo.scrollTo(document.getElementById("demoItem" + index), 1000, {
                    offset: -50,
                });
            },
            changeWidth(item) {
                this.width = 300
                this.categoryno1 = item.id
                //this.twoList = item.children
                /* this.courseList = item.courseList*/
            },
            hideWidth() {
                this.width = 0
                this.categoryno1 = ''
            },
            // 跳转详情页
            goDetail(id, type) {
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
        mounted() {

        }
    }
</script>
<style lang="scss" scoped>
    .goIndex {
        color: white;
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 18px;
        vertical-align: baseline;
        text-decoration: none;
        background: transparent;
        cursor: pointer;
    }

    .goIndex {
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
