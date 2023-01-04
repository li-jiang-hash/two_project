<template>
    <!--弹窗-->
    <el-dialog
            width="35%"
            :title="title"
            :visible.sync="visible"
            :before-close="handleClose">
        <el-form :model="editformData" :rules="editrules" ref="editformData" label-width="100px">
            <el-form-item label="广告图片:" prop="imageUrl">
                <div>
                    <el-upload
                            class="avatar-uploader"
                            action="http://192.168.1.23:7000/home/banner/upload"
                            :show-file-list="false"
                            :headers="token"
                            :on-success="handleAvatarSuccess"
                            :before-upload="beforeAvatarUpload"
                    >
                        <img style="height: 50px;width: 200px" v-if="editformData.imageUrl" v-model="editformData.imageUrl" :src="editformData.imageUrl" class="avatar" alt="">
                        <i v-else  class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </div>
            </el-form-item>
            <el-form-item label="广告标题:" prop="title">
                <el-input v-model="editformData.title" ></el-input>
            </el-form-item>
            <el-form-item label="广告链接:" prop="linkUrl">
                <el-input v-model="editformData.linkUrl" ></el-input>
            </el-form-item>
            <el-form-item label="广告排序:" prop="sort">
                <el-input-number style="width: 300px;" v-model="editformData.sort"
                                 @change="handleChange" :min="1" :max="1000" label="描述文字"></el-input-number>
            </el-form-item>
            <el-form-item label="开始时间:" prop="gmtCreate">
                <el-date-picker
                        value-format="yyyy-MM-ddTHH:mm:ss"
                        format="yyyy-MM-dd HH:mm:ss"
                        v-model="editformData.gmtCreate"
                        type="datetime"
                        placeholder="选择日期时间"
                        :picker-options="gmtCreate"
                >
                </el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间:" prop="gmtModified" >
                <el-date-picker
                        format="yyyy-MM-dd HH:mm:ss"
                        value-format="yyyy-MM-ddTHH:mm:ss"
                        v-model="editformData.gmtModified"
                        type="datetime"
                        placeholder="选择日期时间"
                        :picker-options="gmtModified"
                >
                </el-date-picker>
            </el-form-item>
        </el-form>
        <el-row style="margin-top:17px; ">
            <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="handleClose">取 消</el-button>
            <el-button style="float:right" size="mini" type="primary" @click="submitForm">确定</el-button>
        </el-row>
    </el-dialog>
</template>
<script>
    export default {
        name: 'Edit',
        data() {
            return {
                token:{
                    "token":sessionStorage.getItem("token")
                },
                opts: {
                    advTargetList: []
                },
                imageUrl:'',
                gmtCreate: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                gmtModified:{
                    disabledDate: (time) => {
                        if(this.editformData.gmtCreate != ""){
                            return time.getTime() < new Date(this.editformData.gmtCreate).getTime() || time.getTime() <Date.now();//禁用开始日期之前的日期
                        }
                    }
                },
                editrules: {
                    imageUrl: [
                        { required: true, message: '请输入广告图片', trigger: 'blur', autocomplete: 'on' }
                    ],
                    title: [
                        { required: true, message: '请输入广告标题', trigger: 'blur' }
                    ],
                    linkUrl: [
                        { required: true, message: '请输入广告链接', trigger: 'blur' }
                    ],
                    sort: [
                        { required: true, message: '请输入广告排序', trigger: 'blur' }
                    ],
                    gmtCreate: [
                        { required: true, message: '请输入开始时间', trigger: 'blur' }
                    ],
                    gmtModified: [
                        { required: true, message: '请输入结束时间', trigger: 'blur' }
                    ]
                }
            }
        },
        props: {
            visible: {
                type: Boolean,
                default: false
            },
            editformData: {},
            title: {
                type: String,
                default: ''
            }
        },
        mounted() {
        },
        methods: {
            handleChange(value) {
                this.editformData.sort = value
            },
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
            handleClose(done) {
                this.$refs['editformData'].resetFields()
                this.$emit('close-callback')
            },
            handleAvatarSuccess(res, file) {
                this.editformData.imageUrl = res.data;
            },
            submitForm() {
                var that=this;
                this.$refs.editformData.validate((valid)=>{
                    if (valid){
                        this.$http.post("/home/banner/editBanner",this.editformData).then(function (resp) {
                            if (resp.data.code===2000){
                                that.$message({
                                    message:resp.data.msg,
                                    type:"success",
                                })
                                that.handleClose('close-callback')
                            }
                            if (resp.data.code===5000){
                                that.$message({
                                    message:resp.data.msg,
                                    type:"error",
                                })

                            }
                        })
                    }else {
                        this.$message({
                            message:"输入框不能为空",
                            type:"error"
                        })
                    }
                })
            }
        }
    }
</script>
<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 150px;
        height: 150px;
        line-height: 150px;
        text-align: center;
    }
    .avatar {
        width: 150px;
        height: 150px;
        display: block;
    }
</style>
