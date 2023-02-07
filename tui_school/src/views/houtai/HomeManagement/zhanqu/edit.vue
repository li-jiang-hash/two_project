<template>
    <!--弹窗-->
    <el-dialog
            center
            width="35%"
            :title="title"
            :visible.sync="visible"
            :before-close="handleClose">
        <el-form :model="formData" :rules="rules" ref="formData" label-width="100px">
            <el-form-item label="专区名称：" prop="zoneName">
                <el-input v-model="formData.zoneName" ></el-input>
            </el-form-item>
            <el-form-item label="专区排序：" prop="sort">
                <el-input-number v-model="formData.sort" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
            </el-form-item>
            <el-form-item label="专区描述：" prop="zoneDesc">
                <el-input v-model="formData.zoneDesc" type="textarea"></el-input>
            </el-form-item>
        </el-form>
        <el-row style="margin-top:17px; ">
            <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="handleClose">取 消</el-button>
            <el-button style="float:right" size="mini" type="primary" @click="submitForm1('formData')" v-if="title=='新增'">确定</el-button>
            <el-button style="float:right" size="mini" type="primary" @click="submitForm2('formData')" v-if="title=='编辑'">确定</el-button>
        </el-row>
    </el-dialog>
</template>
<script>

    export default {
        name: 'Edit',
        data() {
            return {
                ctrl: {
                    dialogVisible: true
                },
                form: {},
                rules: {
                    zoneName: [
                        { required: true, message: '请输入专区名称', trigger: 'blur', autocomplete: 'on' }
                    ],
                    zoneDesc: [
                        { required: true, message: '请输入专区描述', trigger: 'blur', autocomplete: 'on' }
                    ]
                }
            }
        },
        props: {
            // route object
            formData: {
                type: Object,
                default: () => {}
            },
            visible: {
                type: Boolean,
                default: false
            },
            title: {
                type: String,
                default: ''
            }
        },
        methods: {
            //关闭弹窗
            handleClose(done) {
                this.$refs['formData'].resetFields()
                this.$emit('close-callback')
                this.ctrl.dialogVisible=false
            },
            handleChange(value) {
                this.formData.sort = value
            },
            submitForm1(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (!this.formData.zoneName) {
                            this.$message({
                                type: 'error',
                                message: '请输入专区名称'
                            });
                            return false
                        }
                       this.$http.post("/syssystem/tb-zone/updatePrefecture",this.formData).then( resp => {
                           if (resp.data.code===2000){
                               this.$message({
                                   message:resp.data.msg,
                                   type:"success"
                               })
                           }else{
                               this.$message({
                                   message:resp.data.msg,
                                   type:"error"
                               })
                           }
                           this.handleClose()
                       })
                    } else {
                        this.$message({
                            type: 'error',
                            message: "提交失败"
                        });
                    }
                });
            },
            submitForm2(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (!this.formData.zoneName) {
                            this.$message({
                                type: 'error',
                                message: '请输入专区名称'
                            });
                            return false
                        }
                        this.$http.post("/syssystem/tb-zone/updatePrefecture",this.formData).then( resp => {
                            if (resp.data.code===2000){
                                this.$message({
                                    message:resp.data.msg,
                                    type:"success"
                                })
                            }else{
                                this.$message({
                                    message:resp.data.msg,
                                    type:"error"
                                })
                            }
                            this.handleClose()
                        })
                    } else {
                        this.$message({
                            type: 'error',
                            message: "提交失败"
                        });
                    }
                });
            }
        }
    }
</script>
