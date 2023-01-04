<template>
    <div class="modifyCourse">
        <div class="" v-if="!obj.id">
            <div class="step_panel">
                <div class="step on">1.填写课程信息</div>
                <div class="step">2.上传课程内容</div>
                <div class="step">3.提交审核</div>
            </div>
        </div>
        <div class="main_cont form">
            <form action="" @submit="courseSave">
                <div class="form_group">
                    <div class="label">课程名称</div>
                    <div class="form_ctl">
                        <input type="text" v-model="obj.title" class="form_input" placeholder="请输入课程名称">
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">课程分类</div>
                    <div class="form_ctl">
                        <select v-model="obj.categoryId1" @change="changeCate1" name=""  class="form_input_min">
                            <option value="">--请选择--</option>
                            <option v-for="cate in cate1" :checked="obj.categoryId1 == cate.id" :key="cate.id" :value="cate.id" v-html="cate.title"></option>
                        </select>
                        <select name="" v-model="obj.categoryId2" @change="changeCate2"  v-if="cate2 && cate2.length" class="form_input_min">
                            <option value="">--请选择--</option>
                            <option v-for="cate in cate2" :key="cate.id" :value="cate.id" v-html="cate.title"></option>
                        </select>
                        <select name="" v-model="obj.categoryId3"  v-if="cate3 && cate3.length" class="form_input_min">
                            <option value="">--请选择--</option>
                            <option v-for="cate in cate3" :key="cate.id" :value="cate.id">{{cate.title}}</option>
                        </select>
                    </div>
                </div>
                <div class="form_group" v-if="!(this.$route.query.t === 'paper')">
                    <div class="label">课程介绍</div>
                    <div class="form_ctl">
                        <div id="courseInfo"></div>
                        <quill-editor class="editor"
                                      v-model="obj.description"
                                      ref="myQuillEditor"
                                      :options="editorOption">
                        </quill-editor>
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">是否免费</div>
                    <div class="form_ctl form_ctl_radio">
                        <input name="sex" v-model="obj.isFree" type="radio" value="0" id="sex1">
                        <label for="sex1">付费</label>
                        <input name="sex" v-model="obj.isFree" type="radio" value="1" id="sex2">
                        <label for="sex2">免费</label>
                    </div>
                </div>
                <div class="form_group" v-if="obj.isFree == 0">
                    <div class="label">课程售价</div>
                    <div class="form_ctl">
                        <input type="text" v-model="obj.price" class="form_input_min" placeholder="请输入价格"><span>元</span>
                    </div>
                </div>
                <div class="form_group" v-if="!(this.$route.query.t === 'paper')">
                    <div class="label">上传封面</div>
                    <div class="form_ctl upload_ctl">
                        <input type="hidden" v-model="obj.cover">
                        <el-upload
                                class="avatar-uploader"
                                action="http://localhost:9000/student/member/uploadMsg"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload"
                        >
                            <img style="height: 100px;width: 200px" v-if="obj.cover" v-model="obj.cover" :src="obj.cover" class="avatar" alt="">
                            <i v-else  class="el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </div>
                </div>
                <div class="form_group">
                    <div class="label">&nbsp;</div>
                    <div class="form_ctl">
                        <button class="submit_btn queren" :disabled="btnDis">确认</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        name:'ModifyCourse',
        data () {
            return {
                editorOption:{},
                btnDis: false,
                sideTy: 'kcgl',
                edit: true,
                cate1: [{
                    id:'',
                    title:"",
                    children:[]
                },
                ],
                cate2: false,
                cate3: false,
                change1: false,
                change2: false,
                tags: [],
                obj: {
                    id:'',
                    title: '',
                    price: '',
                    description:'',
                    categoryId1: '',
                    categoryId2: '',
                    categoryId3: '',
                    isFree: 0,
                    cover: '',
                    remark:'',
                    mobile:'',
                }
            }
        },
        // 页面打开完毕
        methods: {
            //图片上传成功后的回调
            handleAvatarSuccess(res, file) {
                this.obj.cover = res;
            },
            //图片选择后的判断，是否为图片及图片大小限制
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },

            //修改课程信息
            courseSave (e) {
                e.preventDefault();

                if (!this.obj.description) {
                    this.$message.error('请输入课程介绍！！')
                    return false;
                }
                if (!this.obj.title) {
                    this.$message.error('请输入课程名称！！')
                    return false;
                }
                if (!this.obj.categoryId1) {
                    this.$message.error('请选择课程分类！！')
                    return false;
                }
                if (parseInt(this.obj.isFree) !== 1) {
                    if (!this.obj.price) {
                        this.$message.error('请输入课程售价！！')
                        return false;
                    }
                    if (this.obj.price <= 0) {
                        this.$message.error('请输入正确的课程原价！！')
                        return false;
                    }
                } else {
                    this.obj.price = 0;
                }
                if (!this.obj.cover) {
                    this.$message.error('请上传课程封面！！')
                    return false;
                }

                var that=this;
                this.$http.post("/core/course/updateCourseSubject",this.obj).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg)
                        if (that.obj.id===""){//如果id为空则是添加，，成功后跳转到上传章节的页面，，并携带课程的courseId
                            that.$router.push('/updateCourse?courseId='+resp.data.result)
                        }else {
                            that.$router.push("/recordedManagement")
                        }
                    }else {
                        that.$message.error(resp.data.msg)
                    }
                })
            },
            //选择了一级下拉框，显示二级
            changeCate1 () {
                if (this.obj.categoryId2 == 0) {
                    this.obj.categoryId2 = ''
                }
                if (this.obj.categoryId3 == 0) {
                    this.obj.categoryId3 = ''
                }
                if (!this.$route.query.no) {
                    this.obj.categoryId2 = '';
                    this.obj.categoryId3 = '';
                    this.cate2 = [];
                    this.cate3 = [];
                }
                if (this.change1) {
                    this.obj.categoryId2 = '';
                    this.obj.categoryId3 = '';
                    this.cate2 = [];
                    this.cate3 = [];
                }
                let c1 = this.obj.categoryId1;
                let that = this;
                if (c1 === '') {
                    that.cate2 = false;
                    that.cate3 = false;
                } else {
                    let catels = that.cate1;
                    catels.map(function (item, i) {
                        if (item.id === c1) {
                            that.cate2 = item.children;
                            that.cate3 = false;
                        }
                    })
                    this.changeCate2();
                }
                if (this.$route.query.no) {
                    this.change1 = true
                }
            },
            //点击二级下拉框，显示三级
            changeCate2 () {
                if (!this.$route.query.no) {
                    this.obj.categoryId3 = '';
                    this.cate3 = [];
                }
                if (this.change2) {
                    this.obj.categoryId3 = '';
                    this.cate3 = [];
                }
                let c2 = this.obj.categoryId2;
                let that = this;
                if (c2 === '') {
                    that.cate3 = false;
                } else {
                    let catels = that.cate2;
                    catels.map(function (item, i) {
                        if (item.id === c2) {
                            if (item.children && item.children.length > 0) {
                                that.cate3 = item.children;
                            } else {
                                that.cate3 = false;
                            }
                        }
                    })
                }
                if (this.$route.query.no) {
                    this.change2 = true
                }
            },

            //修改时下拉框回显
            selectXiaLa(){
                var that=this;
                var pid=this.$route.query.no.subjectParentId;//父选择框的id，，父id为0表示其为一级下拉框，，否则就是二级以上
                var zid=this.$route.query.no.subjectId;//子选择框的id
                if (pid===0){//一级分类
                    that.obj.categoryId1=zid;
                    that.obj.categoryId2='';
                    that.obj.categoryId3='';
                }else {
                    //根据父id查询其父亲的父id,,如果为0表示为2级下拉框，，否则就是三级下拉框，，
                    this.$http.get(`/core/subject/findPidByid/${pid}`).then(function (resp) {
                        if (resp.data===0){//表示为二级目录
                            that.obj.categoryId1=pid;
                            that.obj.categoryId2=zid;
                            that.obj.categoryId3='';
                        }else {//表示为三级目录
                            that.obj.categoryId1=resp.data;
                            that.obj.categoryId2=pid;
                            that.obj.categoryId3=zid;
                        }
                    });
                }
            },

            //获取课程分类信息并填充下拉框
            inintXiaLa(){
                var that=this;
                //填充下拉溃疡
                this.$http.post("/core/subject/findsubjectByStatus/1/20",{}).then(function (resp) {
                    that.cate1=resp.data.result.records;
                    if (that.obj.categoryId1) {
                        that.changeCate1();
                        if (that.obj.categoryId2) {
                            that.changeCate2();
                        }
                    }
                });

            },
        },

        // 页面打开
        mounted () {
            this.obj.mobile=window.sessionStorage.getItem("mobile");
            if (this.$route.query.no){//根据是否携带参数，，判断是新增还是修改
                this.obj.id=this.$route.query.no.id;
                this.obj.title=this.$route.query.no.title;
                if (this.$route.query.no.price>0){
                    this.obj.price=this.$route.query.no.price;
                }else {
                    this.obj.isFree=1
                }
                this.obj.cover=this.$route.query.no.cover;
                this.obj.description=this.$route.query.no.description;
                this.selectXiaLa();
            }
            this.inintXiaLa();
        },
    }
</script>

<style lang="scss">
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
    }
    .upload_ctl{
    .preview{
        width: 200px;
        height: 120px;
        overflow: hidden;
        background-color: #f5f5f5;
        text-align: center;
        margin-bottom: 10px;
    i{
        line-height: 120px;
        font-size: 80px;
        color: #ddd;
    }
    }
    }
    .ac_ctl{
    .item_ac_op{
        line-height: 30px;
    }
    }
    .queren{
        margin-left: 110px;
    }
</style>