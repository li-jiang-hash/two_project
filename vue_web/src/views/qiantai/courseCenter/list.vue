<template>

    <div class="courselist_page">
        <!--课程中心-->
        <div class="course_content" >
            <ul class="clearfix business_list"  style="width: 1220px">
                <div class="big_text">
                    <span class="col_block"></span>
                    <span class="col_block"></span>
                    {{ zoneName }}
                    <span class="small_text">{{ zoneDesc }}</span>
                </div>
                <br/><br/>
                <li v-for="(item, index) in menuData" :key="index" v-if="id==item.zoneId">
                    <div class="course_info">
                    <router-link  :to="{name: 'DetailId', params: {id: item.zoneId}}">
                        <div class="img_box" >
                            <img class="course_img" :src="item.businessInfo.bicon" alt="">
                        </div>
                        <!-- 店铺名 -->
                        <p style="font-size: 18px;color:red;font-family: 黑体">{{ item.businessInfo.sname }}</p>

                        <!-- 成交信息 -->
                        <p class="productStatus">
                            <span v-if="item.businessInfo.sales!=null">月成交<span style="font-size: 14px;">{{item.businessInfo.sales}}</span>单</span>
                            <span v-if="item.businessInfo.sales==null" style="font-size: 14px;color: #8c939d">暂未出单</span>
                        </p>

                        <div class="price_box" v-if="item.businessInfo.minprice!=null">￥{{Number(item.businessInfo.minprice).toFixed(2)}}起</div>
                        <div class="price_box" v-if="item.businessInfo.minprice==null">￥0.00起</div>
                    </router-link>
                    </div>
                </li>
            </ul>
        </div>
        <RightTap :weiVisable="weiVisable" ></RightTap>
        <!--<div class="pageclass" >
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes, prev, pager, next, jumper"
                    :current-page="pageObj.pageCurrent"
                    :page-size="pageObj.pageSize"
                    :page-sizes="[12, 20, 50, 100]"
                    :total="pageObj.totalCount">
            </el-pagination>
        </div>-->
    </div>
</template>

<script>
    import HeaderList from "@/components/HeaderList";
    import RightTap from "@/components/RightTap";

    export default {
        name: "list",
        components: {
            HeaderList,
            RightTap
        },
        data () {
            return {
                id: this.$route.params.id,
                zoneName: this.$route.params.zoneName,
                zoneDesc: this.$route.params.zoneDesc,
                weiVisable:true,
                menuData:{},
                openVip: false,
                free: '',
                search: {},
                activityList: [],
                classList: [],
                pageObj : {
                    list: {},
                    pageCurrent: 1,
                    pageSize: 12,
                    totalCount: 0,
                    totalPage: ''
                }
            }
        },
        created() {
            //this.getInit()
            this.getAllMenu()
        },
        methods:{
            getAllMenu(){
                var that=this;
                this.$http.get("home/zoneBusiness/selectAllZoneBusiness").then(function (resp) {
                    that.menuData=resp.data.data;
                })
            },
            getCourseByMenu(categoryObj){
                this.search.oneSubjectId=categoryObj.categoryNo1;
                this.search.twoSubjectId=categoryObj.categoryNo2;
                this.search.isFee=categoryObj.isFee
                this.getCourse()
            },
            getCourse(){
                var that = this
                this.$http.post(`/core/course/fontFindCourse/${this.pageObj.pageCurrent}/${this.pageObj.pageSize}`,this.search).then(function (resp) {
                    that.pageObj.list = resp.data.result.records
                    that.pageObj.totalCount = resp.data.result.total;
                })
            },
            getInit(){
                this.search.oneSubjectId = this.$route.query.categoryno1
                this.search.twoSubjectId = this.$route.query.categoryno2
                this.search.isFree = this.$route.query.isFree
                console.log(this.search)
                this.getCourse()
            },
            //分页查询
            handleSizeChange(val) {
                this.pageObj.pageSize=val;
                this.getCourse();
            },
            handleCurrentChange(val) {
                this.pageObj.pageCurrent=val;
                this.getCourse();
            },
        }
    }

</script>

<style lang="scss" rel="stylesheet/scss" scoped>
    .small_text {
        // text-align: center;
        font-size: 14px;
        color: #999;
        margin-top: 10px;
    }
    .col_block {
        display: inline-block;
        width: 4px;
        height: 24px;
        background: #333;
        position: relative;
        top: 4px;
    }
    .big_text {
        // text-align: center;
        font-size: 24px;
    }
    .pageclass{
        text-align: center;
    }
    .courselist_page {
        .course_content {
            background: rgb(245, 245, 245);
            padding: 30px 0 10px 0;
            ul {
                width: 1200px;
                margin: 0 auto;
            }
            li {
                float: left;
                border-radius: 6px;
                background: #fff;
                margin: 0px 20px 20px 0px;

            }
        }
        .course_info {
            display: block;
            width: 285px;
            height: 200px;
            border-radius: 6px;
            position: relative;
            &:hover {
                text-decoration: none;
                color: #000;
                box-shadow: 0px 3px 18px rgba(0, 0, 0, 0.2);
                transform: translateY(-2px);
                transition: all .3s;
            }
            .img_box {
                position: relative;
                width: 285px;
                height: 140px;
                .qizi {
                    background: url('../../../assets/image/activity/qizi.png') no-repeat center;
                    position: absolute;
                    top: 0;
                    left: 10px;
                    width: 46px;
                    height: 43px;
                    padding-top: 3px;
                    color: #fff;
                    font-size: 14px;
                    text-align: center;
                }
                .course_img {
                    width: 285px;
                    height: 140px;
                    border-radius: 6px 6px 0 0;
                }
                .live_time {
                    position: absolute;
                    bottom: 0;
                    left: 0;
                    font-size: 12px;
                    line-height: 16px;
                    width: 285px;
                    background: rgba(0, 0, 0, 0.2);
                    color: #fff;
                    padding-bottom: 2px;
                }
            }
            p {
                font-size: 16px;
                margin-top: 5px;
                padding: 0 10px;
                word-break: break-all;
            }
            .price_box {
                font-size: 16px;
                position: absolute;
                bottom: 15px;
                right: 20px;
                color: red;
            }
        }
    }
</style>
