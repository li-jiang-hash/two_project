<template>
            <div data-v-049ea09a="" class="person_content">
                                <ul data-v-049ea09a="" class="person_title clearfix">
                                    <li data-v-049ea09a="" class="">
                                        <a data-v-049ea09a="" href="javascript:">商品收藏</a>
                                    </li>
                                </ul>
                <div data-v-049ea09a="" class="notdata" v-if="shouCangData===false">
                    <i data-v-049ea09a="" class="iconfont"></i>暂时没有数据
                </div> <!---->

                <div data-v-049ea09a="" class="person_info" v-if="shouCangData===true">
                    <!--            <div data-v-049ea09a="" class="course_content clearfix">-->
                    <div data-v-049ea09a="" class="course_content clearfix" v-for="item in shouCangXinxi">
                        <div class="order_title clearfix" style="background-color: rgba(204,204,204,0.27);height: 25px;line-height: 2.0;">
                            <span id="sname"><i class="el-icon-s-shop"></i></span>
                            <span class="order_num">店铺名：{{item.businessInfo.sname}}</span>

                        </div>
                        <div data-v-049ea09a="" class="img_box fl">
                            <!--                    <img data-v-049ea09a="" src="https://static.roncoo.com/roncoo/course/a9f80b7dbc6b483a843a0e12960d1030.jpg" alt="">-->
                            <img data-v-049ea09a="" :src="item.img" alt="">
                        </div>
                        <!--                <p data-v-049ea09a="" class="course_name fl">基于电商业务的全链路数据中台落地方案（全渠道、全环节、全流程）</p> &lt;!&ndash;&ndash;&gt;-->
                        <p style="color: #ff691a;margin: 20px" data-v-049ea09a="" class="course_name fl">商品名称：{{item.gname}}</p>
                        <p style="color: #ff691a" data-v-049ea09a="" class="course_name fl">商品价格：{{item.price}}元</p>

                        <!---->
                        <!--                <a data-v-049ea09a="" href="https://www.roncoo.com/view/1343053809300340737">进入学习</a> &lt;!&ndash;&ndash;&gt; &lt;!&ndash;&ndash;&gt; &lt;!&ndash;&ndash;&gt; &lt;!&ndash;&ndash;&gt; &lt;!&ndash;&ndash;&gt;-->
                        <router-link :to=" {name:'DetailId',params:{id:item.id}}"><a data-v-049ea09a="">进入购买</a>
                        </router-link>

                        <!--                <a data-v-049ea09a="" class="close_collect">取消收藏</a>-->
                        <a data-v-049ea09a="" class="close_collect" @click="cancel(item.id)">取消收藏</a>
                    </div>
                </div>
            </div>
</template>

<script>

    export default {
        name: "MyShouCang",
        data() {
            return {
                shouCangXinxi: [
                    {
                        id:'',
                        goodsid: '',
                        price:'',
                        img: '',
                        businessInfo: []
                    }
                ],
                //是否有数据
                shouCangData: false,
                shouChangData: {
                    goodsid: '',
                    //memberid: window.sessionStorage.getItem("memberId"),
                    //
                },
            }
        },
        created() {
            this.queryGuanZhuById();
        },
        methods: {
            //根据会员id查询当前用户的所有收藏课程
            queryGuanZhuById() {
                var that = this;
                //this.shouCangData = false;
                this.$http.get(`/user/collection/findGid`).then(function (resp) {
                    console.log(resp.data.data)
                    // if (resp.data.code === 5000) {
                    //     that.shouCangData = false;
                    // }else if (resp.data.code===2000){
                         that.shouCangData=true;
                         that.shouCangXinxi = resp.data.data;
                    // }

                })

            },
            // // 根据课程id和会员id取消收藏
            cancel(id) {
                // this.shouChangData.courseid = courseid;
                var that = this;
                this.$http.post("/user/collection/deleteGid?goodsid="+id).then(function (resp) {
                    that.queryGuanZhuById();
                })
            },
        }
    }

</script>

<style lang="scss" rel="stylesheet/scss" scoped>
    .person_info[data-v-049ea09a] {
        min-height: 400px;
        padding-bottom: 30px;
    }

    .course_content[data-v-049ea09a] {
        height: 86px;
        padding: 25px 0;
        margin: 0 25px;
        border-bottom: 2px solid #f2f2f2;
        position: relative;
    }

    .cl:before, .clearfix:before, .container:before {
        content: "";
        display: table;
    }

    .course_content .img_box[data-v-049ea09a] {
        display: inline-block;
        width: 175px;
        height: 86px;
    }

    .fl {
        float: left;
    }

    span {
       width: 300px;
    }

    .course_content img[data-v-049ea09a] {
        width: 175px;
        height: 86px;
    }

    img {
        max-width: 100%;
        border: none;
    }

    .course_content p.course_name[data-v-049ea09a] {
        margin-left: 20px;
        width: 640px;
        color: #333;
    }

    .course_content a[data-v-049ea09a] {
        display: block;
        width: 106px;
        height: 36px;
        line-height: 36px;
        text-align: center;
        border-radius: 6px;
        font-size: 14px;
        border: 1px solid #999;
        color: #999;
        background: #fff;
        position: absolute;
        top: 25px;
        right: 0;
        cursor: pointer;
    }

    a {
        margin: 0;
        padding: 0;
        border: 0;
        font-size: 100%;
        vertical-align: baseline;
        background: transparent;
        text-decoration: none;
    }

    .course_content a.close_collect[data-v-049ea09a] {
        top: 80px;
        border: none;
    }

    .course_content p.course_name[data-v-049ea09a] {
        margin-left: 20px;
        width: 640px;
        color: #333;
    }

    .course_content p[data-v-049ea09a] {
        font-size: 14px;
        color: #999;
    }

    .person_content[data-v-049ea09a] {
        width: 1012px;
        float: right;
        background: #fff;
        border-radius: 6px;
    }
    .notdata{
        text-align: center;
        padding: 60px 0;
        font-size: 30px;
        color: #999;
        min-height: 300px;
        i{
            font-size: 30px;
            padding-right: 12px;
        }
    }
    .person_title[data-v-049ea09a] {
        border-bottom: 1px solid #f2f2f2;
        position: relative;
    }

    .cl:before, .clearfix:before, .container:before {
        content: "";
        display: table;
    }

    .person_title li.now[data-v-049ea09a] {
        border-bottom: 2px solid #d51423;
    }

    .person_title li.now a[data-v-049ea09a], .person_title li a[data-v-049ea09a]:hover {
        color: #d51423;
    }

    .person_title li[data-v-049ea09a] {
        float: left;
        line-height: 60px;
        padding: 0 30px;
        font-size: 14px;
    }

    a {
        color: #333;
    }

    .person_title li[data-v-049ea09a] {
        float: left;
        line-height: 60px;
        padding: 0 30px;
        font-size: 14px;
    }

    a {
        color: #333;
    }
</style>