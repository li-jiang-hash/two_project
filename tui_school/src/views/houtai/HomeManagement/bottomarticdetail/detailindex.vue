<template>
    <div class="pad20">
        <el-form ref="formData" :model="formData" label-width="100px">
            <el-form-item label="文章名称：">
                <el-input v-model="formData.artTitle" placeholder="请输入文章名称"></el-input>
            </el-form-item>
            <el-form-item label="文章描述：" style="width:80%">
                <div id="artDesc" style="height:400px;max-height:500px;"></div>
            </el-form-item>
        </el-form>
        <el-row style="margin-top:17px; ">
            <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="handleClose">取 消
            </el-button>
            <el-button style="float:right" size="mini" type="primary" @click="submitForm('formData')">确定</el-button>
        </el-row>
    </div>
</template>
<script>
    import * as commonalityApi from '/src/api/commonality'
    import qs from "qs";
    export default {

        data() {
            return {
                ctrl: {
                    loading: false
                },
                formData: {
                    artTitle: '',
                    navId: ''
                },
            }
        },
        mounted() {
            this.formData.navId = this.$route.query.id

            this.getArticala(this.formData.navId)
            console.log(this.formData.navId);
        },
        methods: {
            createEdit() {
                const E = require('wangeditor')
                this.editor = new E('#artDesc')
                this.editor.destroy();//这里做了一次判断，判断编辑器是否被创建，如果创建了就先销毁。
                this.editor.create(); // 创建用户协议富文本编辑器
                setTimeout(() => {
                    if (this.formData.artDesc !== undefined && this.formData.artDesc !== '' && this.formData.artDesc !== null) {
                        this.editor.txt.html(this.formData.artDesc)
                    } else {
                        this.editor.txt.html('')
                    }
                }, 100)
            },
            getArticala(navId) {
                var that = this;
                console.log(navId);
                this.$http.post(`/syssystem/tb-article/getArticleByNavId/${navId}`).then(function (resp) {
                    if (resp.data.data != null) {
                        that.formData = resp.data.data;
                        console.log(that.formData);
                    } else {
                        that.formData = {};
                    }

                    that.createEdit();

                })
            },
            handleClose() {
                this.editor.txt.clear()
                this.$router.go(-1)
            },
            submitForm(formData) {
                var that = this;
                this.formData.artDesc = this.editor.txt.html()
                console.log(this.formData)
                this.$refs[formData].validate((valid) => {
                    if (valid) {
                        this.$http.post("/syssystem/tb-article/updateArticleByNavId", qs.stringify(this.formData)).then(function (resp) {
                            if (resp.data.code === 2000) {
                                that.$message({
                                    message: resp.data.msg,
                                    type: "success",
                                })
                            }
                            if (resp.data.code === 5000) {
                                that.$message({
                                    message: resp.data.msg,
                                    type: "error",
                                })
                            }
                            that.getArticala(that.formData.navId)
                        })
                    } else {
                        this.loading.hide()
                        this.$message({
                            type: 'error',
                            message: "更新失败"
                        });
                    }
                })
            }
        }
    }
</script>
