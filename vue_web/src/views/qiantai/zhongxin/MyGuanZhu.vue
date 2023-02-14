<template>
    <div>
        <div data-v-295bc851="" class="person_content">
            <ul data-v-295bc851="" class="person_title clearfix">
                <li data-v-295bc851="" class="now">我关注的店铺</li>
            </ul> 
            <div data-v-295bc851="" class="person_info">
            <div data-v-295bc851="" class="course_content" v-for="item in GuanZhuXinxi">
                <div data-v-295bc851="" class="img_box">
                    <img data-v-295bc851="" :src="item.bicon" alt="">
                </div>
                <p style="color: rgb(211, 42, 42);;left: 135px;margin: 5px" data-v-295bc851="" class="course_name">{{item.sname}}</p>
                <p v-if="item.status==0" style="position: absolute;left: 175px;margin: -35px">
                    <el-tag type="success" disable-transitions>
                        营业中
                    </el-tag>
                </p>
                <p v-if="item.status==1" style="position: absolute;left: 175px;margin: -35px">
                    <el-tag type="info" disable-transitions>
                        休息中
                    </el-tag>
                </p>
                <router-link :to="{name: 'BusinessXinX',params:{id:item.id}}"><a data-v-295bc851="">进入主页</a></router-link>
                <a data-v-295bc851="" class="close_collect" @click="cancel(item.cid)">取消关注</a>
            </div>
        </div>
    </div>

    </div>
</template>

<script>

    export default {
        name: "MyGuanZhu.vue",
        data(){
            return{
                GuanZhuXinxi:{},
                guanZhuData:{},
            }
        },
        created() {
        this.queryAllGuanZhuTeacher();
            },
        methods:{
            //查询所有关注的信息
            queryAllGuanZhuTeacher(){
                var that=this;
                this.$http.get("/syssystem/u-collection/findShop?uid="+ sessionStorage.getItem("userId")).then(function (resp) {
                    that.GuanZhuXinxi=resp.data.data;
                })
            },
            // //取消关注店铺
            cancel(cid){
                var that=this;
                this.$http.post("/syssystem/u-collection/unfollow/1?cid="+cid).then(function (resp) {
                    that.queryAllGuanZhuTeacher();
                })
            }
        }
    }

</script>

<style>
    .person_content[data-v-295bc851] {
        width: 1012px;
        float: right;
        background: #fff;
        border-radius: 6px;
    }
    .person_info[data-v-295bc851] {
        min-height: 400px;
        padding-bottom: 30px;
    }

    .person_content {
        width: 1012px;
        float: right;
        background: #fff;
        border-radius: 6px;
    }
    .person_title li[data-v-295bc851] {
        float: left;
        line-height: 60px;
        padding: 0 30px;
        font-size: 14px;
    }
    .person_title li.now {
        border-bottom: 2px solid #d51423;
    }
    .person_title li {
        color: #d51423;
        float: left;
        line-height: 60px;
        padding: 0 30px;
        font-size: 14px;
    }
    .course_content[data-v-295bc851] {
        padding: 25px 0;
        margin: 0 25px;
        border-bottom: 2px solid #f2f2f2;
        position: relative;
    }
    .course_content img[data-v-295bc851] {
        width: 130px;

    }
    .course_content p.course_name[data-v-295bc851] {
        top: 25px;
        color: #333;
    }
    .course_content a[data-v-295bc851] {
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
    .course_content a.close_collect[data-v-295bc851] {
        top: 80px;
        border: none;
    }
    .course_content p.course_name[data-v-295bc851] {
        top: 25px;
        color: #333;
    }
    .course_content p.course_name[data-v-295bc851] {
        top: 25px;
        color: #333;
    }
    .course_content p[data-v-295bc851] {
        position: absolute;
        font-size: 14px;
        left: 115px;
        color: #999;
    }
</style>