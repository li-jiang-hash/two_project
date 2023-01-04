<template>
    <div class="pad20">
        <el-card class="box-card">
        <el-form :inline="true" size="mini" :model="formData"  >
            <el-form-item label="手机号码：" prop="name" >
                <el-input type="text" v-model="formData.mobile"  placeholder="请输入订单编号"></el-input>
            </el-form-item>
            <el-form-item label="课程名称：" >
                <el-input type="text" v-model="formData.courseTitle"  placeholder="请输入课程名称"></el-input>
            </el-form-item>
<!--            <el-form-item label="支付时间：" >-->
<!--                <div>-->
<!--                    <datePicker style="width: 357px" ref="dataRange" type="daterange"></datePicker>-->
<!--                </div>-->
<!--            </el-form-item>-->
<!--            日期-->
<!--            value-format="yyyy-MM-dd"-->
            <el-form-item >
                <el-date-picker
                        align="right"
                        type="date"
                        v-model="formData.gmtCreate"
                        placeholder="选择开始日期"

                        value-format="yyyy-MM-dd"
                        >
                </el-date-picker>
            </el-form-item>
            <el-form-item >
                <el-date-picker
                        align="right"
                        type="date"
                        v-model="formData.gmtModified"
                        placeholder="选择结束日期"
                        :picker-options="gmtModified">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="订单状态：">
<!--                <el-select  class="auto-width" clearable filterable placeholder="订单状态" style="width: 100px">-->
<!--                <el-input type="text" v-model="formData.status"  placeholder="请输入订单编号"></el-input>-->
                    <el-select v-model="formData.status" clearable placeholder="订单状态">
                        <el-option label="已支付" key="1+''" :value="1"></el-option>
                        <el-option label="未支付" key="2+''" :value="0"></el-option>
                    </el-select>
<!--         el-select>       </-->
            </el-form-item>
            <el-form-item label="订单编号：">
                <el-input type="text" v-model="formData.orderNo"  placeholder="请输入订单编号"></el-input>
            </el-form-item>

            <el-form-item label="讲师名称：">
                <el-input type="text" v-model="formData.teacherName" placeholder="请输入讲师名称"></el-input>
            </el-form-item>
            <el-form-item label="支付方式：">

                <el-select v-model="formData.payType" clearable placeholder="支付类型" style="width: 100px">
                    <el-option label="微信" key="1+''" :value="0"></el-option>
                    <el-option label="支付宝" key="2+''" :value="1"></el-option>
                </el-select>
            </el-form-item>
          <br/>
            <el-form-item >
                <el-button icon='el-icon-search' type="primary" @click="MoHuQuery">查询</el-button>
                <el-button icon='el-icon-refresh' class="filter-item" @click="handleReset">重置</el-button>
<!--                <div style="display:inline" v-has="'/course/pc/order/info/statistical'">-->
                    <el-form-item >
                        <div>
                            <span >    平台收入:{{totalMoney}} 【元】</span>
                        </div>
                    </el-form-item>


<!--                </div>-->
            </el-form-item>
        </el-form>

        <template>


            <el-table  :summary-method="getSummaries"
                       show-summar
                    :data="tableData"
                    style="width: 100%" >

                <!--                根据id查询信息-->
                <OrderDetail></OrderDetail>

                <el-table-column
                        prop="orderNo"
                        label="订单号"
                        width="170px">
                </el-table-column>
<!--                查询课程信息-->
                <el-table-column
                        align="center"
                        prop="courseTitle"
                        label="课程信息" width="152px">

                    <template slot-scope="scope">
                        {{scope.row.courseTitle}}
<!--                        <el-button  type="text" >{{scope.row.courseId}}</el-button>-->
<!--                        <el-button  type="text" >{{scope.row}}</el-button>-->
                        <!--        弹出课程信息-->
<!--                        <el-button type="text" @click="dialogTableVisible = true">打开嵌套表格的 Dialog</el-button>-->

                        <el-dialog title="课程详情" :visible.sync="dialogTableVisible">
                            <el-table :data="courseData">
                                <el-table-column property="id" label="课程ID" ></el-table-column>
                                <el-table-column property="title" label="课程标题"></el-table-column>
                                <el-table-column property="price" label="课程价格"></el-table-column>
                                <el-table-column property="lessonNum" label="总课时"></el-table-column>
                                <el-table-column property="buyCount" label="课程价格"></el-table-column>
                                <el-table-column property="viewCount" label="浏览数量"></el-table-column>
                                <el-table-column property="gmtModified" label="更新时间"></el-table-column>
                            </el-table>

                        </el-dialog>
                        <el-row>【<el-button type="text" @click="queryClass(scope.row.courseId,scope.row)" >详情</el-button>】</el-row>
                    </template>
                </el-table-column>
<!--                查询教师信息-->

                        <Teacher></Teacher>
<!--                查看用户信息-->
                <Member></Member>





                <!--                手机号-->
                <el-table-column
                        prop="mobile"
                        label="手机号"
                        width="116px">
                </el-table-column>
                <el-table-column label="交易类型 / 渠道" width="132px">
                    <template slot-scope="scope">
                        <el-row  type="success">线上支付pc端</el-row>
                    </template>
                </el-table-column>
                <el-table-column label="支付方式 / 价格"   prop="total_fee" width="132px">
                    <template slot-scope="scope">
                        <el-row v-if="scope.row.payType === 0" type="success">微信支付</el-row>
                        <el-row v-if="scope.row.payType === 1" type="brandColor">支付宝支付</el-row>
                    </template>
                </el-table-column>

                <el-table-column label="订单状态 / 时间"  >
                    <template slot-scope="scope">
                        <el-row v-if="scope.row.status === 1" type="success">已支付</el-row>
                        <el-row v-if="scope.row.status === 0" type="brandColor">未支付</el-row>
                        <el-row>￥{{scope.row.payLog.payTime}}</el-row>
                    </template>
                </el-table-column>



            </el-table>
        </template>
<!--        </el-table>-->

        <el-pagination
                background
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="currentPage"
                :page-sizes="[3, 5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
        </el-pagination>
        </el-card>
    </div>
</template>

<script>
    import Teacher from "./Teacher";
    import Member from "./Member";
    import OrderDetail from "./OrderDetail";
    export default {
        name: "list",
        components:{
            Member,
            Teacher,
            OrderDetail,
        },
        data() {
            return {
                //课程弹出框
                dialogTableVisible: false,
                // //教师弹出框
                // //用户弹出框
                //根据id弹出框

                //总条数
                total:0,
                tableData:[],
                //总金额
                totalMoney:0.0,
                formData: {
                    courseName:"",
                    gmtCreate:"",
                    gmtModified:"",
                    orderNo:"",
                    teacherName:"",
                    status:"",
                    payType:"",
                },
                currentPage: 1,
                pageSize:3,

            // //    课程数据
                courseData:[],
            // //    教师数据
            //     TeacherData:[],
            //    用户信息
            //     MemberData:[],
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
                },
                payLog:[],
                //开始的时间
                gmtCreate: {
                    disabledDate(time) {
                        return time.getTime() >Date.now();
                    },
                },
                //结束时间
                gmtModified: {
                    disabledDate: (time) => {
                        // console.log(time)
                        if(this.formData.gmtCreate!= ""){
                            return time.getTime() < new Date(this.formData.gmtCreate).getTime() || time.getTime() >Date.now();//禁用开始日期之前的日期
                        }
                    }
                },
            }
        },
        created() {

            this.initTable();


        },

        //查询所有的
        methods:{
            handleReset(){
                this.formData={},
                this.totalMoney=0.0;
                this.initTable();


            },
            //模糊查询
            MoHuQuery(){
                // console.log(this.initTable);
                this.totalMoney=0.0;
                this.initTable();

            },
            getSummaries(param) {
                const { columns, data } = param;
                const sums = [];
                columns.forEach((column, index) => {
                    if (index === 0) {
                        sums[index] = '总价';
                        return;
                    }
                    const values = data.map(item => Number(item[column.property]));
                    if (!values.every(value => isNaN(value))) {
                        sums[index] = values.reduce((prev, curr) => {
                            const value = Number(curr);
                            if (!isNaN(value)) {
                                return prev + curr;
                            } else {
                                return prev;
                            }
                        }, 0);
                        sums[index] += ' 元';
                    } else {
                        sums[index] = 'N/A';
                    }
                });
                console.log(sums)
                this.totalMoney=sums;
                return sums;
            },
            initTable(){
                var that=this;
                // console.log(this.formData.gmtCreate);
                // that.$http.post("http://localhost:8041/order/queryAllOrder/"+this.currentPage+"/"+this.pageSize,this.formData).then(function (resp) {
                that.$http.post("/order/order/getAllOrder/"+this.currentPage+"/"+this.pageSize,this.formData).then(function (resp) {
                    if(resp.data.code===2000){
                        that.tableData=resp.data.result.records;
                        that.payLog=resp.data.result.records.payLog;
                        // that.initTable();
                        // that.tableData.forEach(item=>{
                        //     that.totalMoney=that.totalMoney+item.totalFee;
                        //     // that.initTable();
                        // }
                        // )
                        that.tableData.forEach(function (item) {
                            that.totalMoney=that.totalMoney+item.totalFee;
                        })
                        that.total=resp.data.result.total;
                    }
                })

            },
            //    查询课程信息
            queryClass(courseId){
                this.dialogTableVisible=true;
                var that=this;
                that.$http.post("/order/course/queryAllCourse/"+courseId).then(function (resp) {
                    // console.log(resp)
                    that.courseData=resp.data.result;
                    // console.log(that.courseData)
                })
            },
            // //根据id查找信息




            //分页查询
            handleSizeChange(val) {
                this.pageSize=val;
                this.totalMoney=0.0;
                this.initTable();
            },
            handleCurrentChange(val) {
                // console.log(val);
                this.currentPage=val;
                this.totalMoney=0.0;
                this.initTable();
            },
        },

    }
</script>

<style scoped>

</style>