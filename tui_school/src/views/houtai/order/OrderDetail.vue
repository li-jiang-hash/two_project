<template style="text-align: center">
    <div>
    <el-table-column
            prop="id"
            label="序号" width="170px">

        <template slot-scope="scope">
<!--            {{scope.row.id}}-->


            <el-dialog title="订单详情" :visible.sync="TableByIdVisible">
                <!--                                第一张卡片-->
                <el-card class="box-card">
                    <el-form   :inline="true" :model="tableDataById" class="demo-form-inline">
                        <el-divider>订单详情</el-divider>
                        <el-row :gutter="20">
                            <el-col :span="11">
                                <el-form-item label="用户编号：" property="id">
                                    {{tableDataById.id}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="10" :offset="2">
                                <el-form-item label="注册时间：" property="gmtCreate">
                                    {{tableDataById.gmtCreate}}
                                </el-form-item>

                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="11">
                                <el-form-item label="课程名称：" property="courseTitle">
                                    {{tableDataById.courseTitle}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="10" :offset="2">
                                <el-form-item label="讲师名称:" property="teacherName">
                                    {{tableDataById.teacherName}}
                                </el-form-item>

                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="10">
                                <el-form-item label="订单号：" property="orderNo">
                                    {{tableDataById.orderNo}}
                                </el-form-item>
                            </el-col>
                            <el-col :span="12" :offset="13">
                                <el-form-item label="流水号:" property="transactionId">
                                    {{tableDataById.transactionId}}
                                </el-form-item>

                            </el-col>
                        </el-row>
                        <el-row :gutter="20">
                            <el-col :span="11">
                                <el-form-item label="支付类型：" property="payType">
                                    <el-row  type="success">线上支付pc端</el-row>
                                </el-form-item>



                            </el-col>
                            <el-col :span="11" :offset="2">
                                <el-form-item label="支付时间:" property="payTime">
                                    {{tableDataById.payTime}}
                                </el-form-item>

                            </el-col>
                        </el-row>


                    </el-form>

                </el-card>
                <el-card class="box-card">
                    <el-form   :inline="true" :model="tableDataById" class="demo-form-inline">
                        <el-divider>金额分成</el-divider>
                        <el-row :gutter="20">
                            <el-col :span="11">
                                <el-form-item label="支付方式：" property="payType">

                                    <el-row v-if="tableDataById.payType==0" type="success">微信</el-row>
                                    <el-row v-if="tableDataById.payType==1" type="brandColor">支付宝</el-row>
                                </el-form-item>
                            </el-col>
                            <el-col :span="11" :offset="2">
                                <el-form-item label="购买渠道:" property="">
                                  <el-row type="success">PC端</el-row>
                                </el-form-item>

                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-dialog>
            <el-row class="cell"  style="width: 180px">
                <button type="button" class="el-button el-button--text" @click="TableByIdUser(scope.row.id,scope.row)" >
                   {{scope.row.id}}
                </button>
            </el-row>


        </template>

    </el-table-column>
    </div>
</template>

<script>
    export default {
        name: "OrderDetail",
        data(){
            return{
                //根据id弹出框
                TableByIdVisible:false,
                tableDataById:{},
            }
        },methods:{
            //根据id查找信息
            TableByIdUser(id){
                this.TableByIdVisible=true;
                var that=this;
                that.$http.post("order/order/queryOrderAndPayLog/"+id).then(function (resp) {
                    // console.log(resp)
                    that.tableDataById=resp.data.result;
                })
            },
        }
    }
</script>

<style scoped>
    .el-table .cell {
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: normal;
        word-break: break-all;
        line-height: 23px;
        padding-right: 10px;
    }
    .el-button--text {
        color: #409eff;
        background: 0 0;
        padding-left: 0;
        padding-right: 0;
    }
</style>