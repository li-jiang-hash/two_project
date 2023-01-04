<template>
    <el-dialog
            center
            width="35%"
            :title="title"
            :visible.sync="visible"
            :before-close="handleClose">
        <el-form :model="forData" :rules="rules" ref="formData" label-width="100px">
            <el-form-item label="专区排序：" prop="sort">
                <el-input-number v-model="forData.sort" @change="handleChange"  label="描述文字"></el-input-number>
            </el-form-item>
        </el-form>

        <el-row style="margin-top:17px; ">
            <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="handleClose">取 消</el-button>
            <el-button style="float:right" size="mini" type="primary" @click="handleUpdateRow()">确定</el-button>
        </el-row>
    </el-dialog>
</template>

<script>

    export default {
        name: "edit",
        data() {
            return {
                dialogVisible: false,
                forData: {sort:1},
                id:""
            }
        },
        props: {
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
            },
            rules: {
                sort: [
                    { required: true, message: '请选择排序', trigger: 'change', autocomplete: 'on' }
                ],
            }
        },
        watch: {
            visible: function(val) {
                if (val) {
                    this.forData.id = this.formData.id;
                    this.forData.sort = this.formData.sort
                }
            }
        },
        methods:{
            handleClose() {
                this.$emit('close-callback')
            },
            handleChange(value) {
                this.forData.sort = value
            },
            //编辑
            handleUpdateRow() {
                this.$refs["formData"].validate((valid) => {
                    if (valid) {
                        //console.log(this.forData)
                        this.$http.get(`home/zoneBusiness/changeSort/${this.forData.id}/${this.forData.sort}`).then(resp => {
                            if (resp.data.code === 2000) {
                                this.$message({
                                    message: resp.data.msg,
                                    type: "success"
                                })
                                this.handleClose()
                            } else {
                                this.$message({
                                    message: resp.data.msg,
                                    type: "error"
                                })
                            }
                        })
                    }
                })
            },
        }
    }
</script>

<style scoped>

</style>