<template style="text-align: center">
    <!--查询用户信息-->
    <el-table-column
            prop="teacherName"
            label="讲师信息" style="text-align: center"
            width="122px">

        <template slot-scope="scope" >
            {{scope.row.teacherName}}

            <el-dialog title="讲师个人信息" :visible.sync="TeacherTableVisible">
                <!--                                第一张卡片-->
                <el-card class="box-card">
                    <el-form   :inline="true" :model="TeacherData" class="demo-form-inline">
                        <el-divider>讲师个人信息</el-divider>
                        <el-row :gutter="20"  v-if="TeacherData.avatar!=null">
                            <el-col :span="9">
                                <el-form-item label="用户头像：" property="avatar" >
                                    <el-image :src="TeacherData.avatar"  style="height: 120px;width: 120px">

                                    </el-image>
                                </el-form-item>

<!--                                        <el-image :src="TeacherData.avatar"  style="height: 120px;width: 120px">-->

<!--                                        </el-image>-->

<!--                                <el-row :gutter="20">-->
<!--                                <el-form-item label="头像：" property="avatar">-->
<!--                                    {{TeacherData.avatar}}-->
<!--                                </el-form-item>-->
                            </el-col>
                            <el-col :span="11" :offset="0">
                                <el-form-item label="教师编号：" property="id">
                                    {{TeacherData.id}}
                                </el-form-item>
                                <el-form-item label="讲师名称：" property="name">
                                    {{TeacherData.name}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="0">
                                <el-form-item >

                                </el-form-item>

                                <el-form-item label="状态：" property="isDeleted">
                                    <el-button type="text"  ><el-row v-if="TeacherData.status==0" type="success">在职</el-row>
                                    <el-row v-if="TeacherData.status==1" type="brandColor">离职</el-row></el-button>
                                </el-form-item>

                            </el-col>



                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="9">
                                <el-form-item label="　手机号：" property="mobile">
                                    {{TeacherData.mobile}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="0">
                                <el-form-item label="邮箱：" property="email">
                                    {{TeacherData.email}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="0">

                                <el-form-item label="职位" property="level">

                                        <el-button type="text"  >
                                            <el-row v-if="TeacherData.level==1" type="success">首席讲师</el-row>
                                            <el-row v-if="TeacherData.level==0" type="brandColor">高级讲师</el-row>
                                        </el-button>

                                </el-form-item>
                            </el-col>

                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="9" >
                                <el-form-item label="注册时间：" property="gmtCreate">
                                    {{TeacherData.gmtCreate}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="0">
                                <el-form-item label="排序：" property="sort">
                                    {{TeacherData.sort}}
                                </el-form-item>
                            </el-col>

                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="9" >

                            </el-col>

                        </el-row>
                    </el-form>

                </el-card>
                <!--                                第二张卡片-->
                <el-card class="box-card">
                    <el-form   :inline="true" :model="TeacherData" class="demo-form-inline">
                        <el-divider>讲师个人信息</el-divider>
                        <el-row :gutter="20">
                            <el-col :span="27">
                                <el-form-item label="讲师简介：" property="intro">
                                    {{TeacherData.intro}}
                                </el-form-item>
                            </el-col>

                        </el-row>


                        <div class="el-row" style="margin-top: 17px;">
                            <button type="button" @click="closeTeacher" class="el-button el-button--danger el-button--mini is-plain" style="float: right; margin-left: 6px;">
                                <!----><!----><span>取 消</span>
                            </button>
                        </div>
                    </el-form>
                    <!--                                第二张卡片-->
                </el-card>
            </el-dialog>


            <el-row>【<el-button type="text" @click="queryTeacher(scope.row.teacherName,scope.row)" >详情</el-button>】</el-row>

        </template>

    </el-table-column>

</template>

<script>
    export default {
        name: "Teacher",
        data() {
            return {
                //教师弹出框
                TeacherTableVisible: false,
                //    教师数据
                TeacherData:{
                    // avatar:""
                },
            }
        },
        methods: {
            //查询所有的教师信息
            queryTeacher(teacherName){
                this.TeacherTableVisible=true;
                var that=this;
                that.$http.post("order/teacher/queryAllTeacherBYTeacherName/"+teacherName).then(function (resp) {
                    // console.log(resp)
                    that.TeacherData=resp.data.result;

                })
            },
            //关闭按钮
            closeTeacher(){
                this.TeacherTableVisible=false;
            },
        }
    }

</script>

<style scoped>

</style>