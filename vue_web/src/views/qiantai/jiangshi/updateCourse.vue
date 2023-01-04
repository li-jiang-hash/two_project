<template>
    <div id="updateCourse">
            <div class="main_box">
                <div class="step_panel">
                    <div class="step on">1.填写课程信息</div>
                    <div class="step on">2.上传课程内容</div>
                    <div class="step">3.完成课程</div>
                </div>
                <div class="main_cont course_period">
                    <table class="table">
                        <thead>
                        <tr>
                            <th width="80">序号</th>
                            <th>章节名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="(item, index) in chapterList"  :key="item.id" v-if="item.chapterDesc != 'true'">
                            <td>第{{ index + 1 }}章</td>
                            <td class="name">{{item.title}}</td>
                            <td class="operate">
                                <a href="javascript:" class="text_link" @click="edit(index)">修改</a><br>
                                <router-link :to="{path: '/hourGuanLi', query: { chapterId: item.id,courseId: newData.courseId,sort:item.sort}}" class="text_link">
                                    课时管理
                                    <span v-if="item.periodNum" class="c_red">({{item.periodNum}})</span>
                                </router-link><br>
                                <a href="javascript:" @click="del(item)" class="text_link">删除</a>
                            </td>
                        </tr>
                        <tr v-else>
                            <td>第{{ index + 1 }}章</td>
                            <td class="name" style="margin-left: 50px"><input type="text" v-model="item.title" class="form_input" placeholder="请输入章节名称"></td>
                            <td class="operate">
                                <button @click="updatas(item)" class="solid_btn" :disabled="btnDab">保存</button>
                            </td>
                        </tr>
                        <tr>
                            <td>第{{num}}章</td>
                            <td class="name" style="margin-left: 50px"><input type="text" v-model="newData.title" class="form_input" placeholder="请输入章节名称"></td>
                            <td class="operate">
                                <button @click="addPeriod" class="solid_btn" :disabled="btnDab">保存</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <router-link to="/recordedManagement" class="cont_btn solid_btn">下一步</router-link>
                </div>
            </div>
        </div>

</template>

<script>
    export default {
        name: "updateCourse",
        data () {
            return {
                showModal: false,
                modalData: null,
                side: 'kcgl',
                num: 1,
                btnDab: false,
                chapterList: [],
                newData: {
                    id:'',
                    title: '',
                    courseId: '',
                    sort:''//第几章
                },

            }
        },
        created() {
            this.newData.courseId=this.$route.query.courseId;
            this.initCourse();
        },
        methods:{
            //初始化列表数据
            initCourse(){
                var that=this;
                this.$http.post(`/order/chapter/findChapterById/${this.newData.courseId}`).then(function (resp){
                    that.chapterList=resp.data.result;//将章节数据读取放入chapterList中
                    that.num = that.chapterList.length + 1;//num+1
                    that.newData.title = '';//新生成的div数据置空
                });

            },

            // 更新例题
            updatas (data) {
                if (data.chapterName === '') {
                    this.$message.error("请输入课时名称")
                    return
                }
                this.btnDab = true;//禁用保存按钮
                data.courseId=this.newData.courseId;
                var that=this;
                this.$http.post(`/order/chapter/insertChapterWithCourseId`,data).then(function (resp) {
                    that.btnDab = false;//取消禁用保存按钮
                    if (resp.data.code === 2000) {//如果修改成功
                        that.$message.success(resp.data.msg)
                        that.chapterList=resp.data.result;//将章节数据读取放入chapterList中
                        that.num = that.chapterList.length + 1;//num+1
                        that.newData.title = '';//新生成的div数据置空
                    } else {
                        that.$message.error(resp.data.msg)
                    }
                })
            },


            addPeriod () {
                var that=this;
                if (this.newData.title === '') {//未输入课时名称
                    this.$message.error("请输入课时名称")
                    return false;
                }
                this.btnDab = true;//禁用保存按钮
                this.newData.sort = this.num;//将章节信息存放如sort排序

                this.$http.post(`/order/chapter/insertChapterWithCourseId`,this.newData).then(function (resp) {
                    that.btnDab = false;//取消禁用保存按钮
                    if (resp.data.code === 2000) {//如果上传成功
                        that.$message.success(resp.data.msg)
                        that.chapterList=resp.data.result;//将章节数据读取放入chapterList中
                        that.num = that.chapterList.length + 1;//num+1
                        that.newData.title = '';//新生成的div数据置空
                    } else {
                        that.$message.error(resp.data.msg)
                    }
                })
            },

            // 删除例题
            del (data) {
                let that = this;
                data.courseId=this.newData.courseId;
                if (data.periodNum>0){//判断章下面是否有课程
                    this.$message({
                        type: 'error',
                        message: '请先删除该章的课程！！'
                    });
                }else {
                    this.$confirm(`确定要《《彻底删除》》该课程吗?`, {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {//确定删除
                        this.$http.post(`/order/chapter/deleteChapterWithCourseId`,data).then(function (resp){
                            that.btnDab = false;//取消禁用保存按钮
                            if (resp.data.code === 2000) {//如果删除成功
                                that.$message.success(resp.data.msg)
                                that.initCourse();
                            } else {//如果删除成功
                                that.$message.error(resp.data.msg)
                            }
                        });
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '删除已取消'
                        });
                    })
                }
            },


            // 修改例题
            edit (no) {
                let arr = this.chapterList
                arr[no].chapterDesc = 'true';
                this.chapterList = Object.assign([], arr);
            },
        }

    }
</script>

<style lang="scss" rel="stylesheet/scss">
    .step_panel{
        overflow: hidden;
        background-color: #eee;
        .step{
            float: left;
            width: 33.33333%;
            text-align: center;
            height: 50px;
            line-height: 50px;
            color: #333;
            font-size: 14px;
            position: relative;
            &:after{
                content: "";
                display: block;
                position: absolute;
                right: -12px;
                top: -5px;
                z-index: 1;
                width: 60px;
                height: 60px;
                box-shadow: 1px 1px 0 #fff;
                transform:rotate(-45deg);
            }
            &.on{
                color: #fff;
                background-color: #ec7e00;
                &:after{
                    background-color: #ec7e00;
                }
            }
        }
    }
    .course_period{
        label{
            margin-right: 8px;
            color: #333;
        }
        .form_input{
            height: 34px;
            border: 1px solid #e7e7e7;
            width: 278px;
            text-indent: 1em;
        }
        .form_input_min{
            text-indent: 1em;
            width: 130px;
            height: 34px;
            margin-right: 10px;
            border: 1px solid #e7e7e7;
        }
        .cont_btn {
            width: 280px;
            display: block;
            margin:20px auto 0;
        }
    }
    .dragging{
        color: #ec7e00;
    }
</style>