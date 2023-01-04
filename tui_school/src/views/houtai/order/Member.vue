<template style="text-align: center">
    <el-table-column
            prop="memberId"
            label="用户信息" width="92px" style="text-align: center;">

        <template slot-scope="scope">
            {{scope.row.nickname}}


            <el-dialog title="用户信息" :visible.sync="MemberTableVisible">
                <!--                                第一张卡片-->
                <el-card class="box-card">
                    <el-form   :inline="true" :model="MemberData" class="demo-form-inline">
                        <el-divider>账户基础信息</el-divider>
                        <el-row :gutter="20">
                            <el-col :span="11">
                                <el-form-item label="用户编号：" property="id">
                                    {{MemberData.id}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="2">
                                <el-form-item label="用户类型:">
                                    <el-button type="text">
                                        <el-row v-if="MemberData.isType==1" type="success">讲师</el-row>
                                        <el-row v-if="MemberData.isType==0" type="brandColor">会员</el-row>
                                    </el-button>

                                </el-form-item>
                            </el-col>

                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="11">
                                <el-form-item label="　手机号：" property="mobile">
                                    {{MemberData.mobile}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="2">
                                <el-form-item label="昵称：" property="nickname">
                                    {{MemberData.nickname}}
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="11" >
                                <el-form-item label="注册时间：" property="gmtCreate">
                                    {{MemberData.gmtCreate}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" :offset="2">
<!--                                <el-form-item label="状态：">-->
<!--                                    <span v-if="MemberData.isDisabled" >正常</span>-->
<!--                                    <span v-else>禁用</span>-->
<!--                                </el-form-item>-->
                                <el-form-item label="状态:">
                                    <el-button type="text">
                                        <el-row v-if="MemberData.isDisabled==0" type="success">正常</el-row>
                                        <el-row v-if="MemberData.isDisabled==1" type="brandColor">禁用</el-row>
                                    </el-button>

                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-form>
                    <!--                                第二张卡片-->
                </el-card>
                <el-card class="box-card">
                    <el-form   :inline="true" :model="MemberData" class="demo-form-inline">
                        <el-divider>用户基础信息表</el-divider>

                        <el-row :gutter="20">
                            <el-col :span="9">
                                <el-image :src="MemberData.avatar"  style="height: 120px;width: 120px">

                                </el-image>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="9">
                                <el-form-item label="　性别：" property="sex">

                                    <el-row type="success" v-if="MemberData.sex==1">女</el-row>
                                    <el-row type="brandColor" v-if="MemberData.sex==0">男</el-row>
                                </el-form-item>
                            </el-col>
                            <el-col :span="9" :offset="2">
                                <el-form-item label="年龄：" property="age">
                                    {{MemberData.age}}
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <div class="el-row" style="margin-top: 17px;">
                            <button type="button" @click="closeMember" class="el-button el-button--danger el-button--mini is-plain"
                                    style="float: right; margin-left: 6px;"><!----><!----><span>取 消</span></button></div>
                    </el-form>
                </el-card>
            </el-dialog>
            <el-row>【<el-button type="text" @click="queryUser(scope.row.memberId,scope.row)" >详情</el-button>】</el-row>

        </template>

    </el-table-column>
</template>

<script>
    export default {
        name: "Member",
        data(){
            return {
                //用户弹出框
                MemberTableVisible: false,
                MemberData:{
                    id:"",
                    isType:"",
                    mobile:"",
                    nickname:"",
                    gmtCreate:"",
                    isDisabled:"",
                    avatar:"",
                    sex:"",
                    age:"",
                    openid:"",
                    password:"",
                    sign:"",
                    isDeleted:"",
                    gmtModified:"",
                    salt:"",
                },
            }
        },
        methods:{

            //查询用户信息
            queryUser(memberId){
                // console.log(memberId)
                this.MemberTableVisible=true;

                var that=this;
                that.$http.post("/order/member/queryAllMemberById/"+memberId).then(function (resp) {
                    // console.log(resp)
                    that.MemberData=resp.data.result;
                    // console.log(that.MemberData)

                })

            },
            closeMember(){
                this.MemberTableVisible=false;
            },
        }
    }

</script>

<style scoped>

</style>