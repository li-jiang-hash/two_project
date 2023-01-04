<template>
    <div class="StudentList">
        <el-card class="box-card">
            <el-form size="mini" ref="souSuoForm" :inline="true" @submit.native.prevent :model="souSuoFormInline" class="demo-form-inline" style="margin-bottom: 0px">
                <el-form-item label="" prop="startTime">
                    <div class="block">
                        <span>时间范围</span>
                        <el-date-picker
                                :clearable="false"
                                v-model="valueTime"
                                type="datetimerange"
                                format="yyyy-MM-dd HH:mm:ss"
                                value-format="yyyy-MM-dd HH:mm:ss"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search()"  icon="el-icon-search">查询</el-button>
                    <el-button @click="resetForm('souSuoForm')" icon="el-icon-refresh">重置</el-button>
                </el-form-item>
            </el-form>

            <!--数据表格-->
            <el-table
                    :data="tableData"
                    border
                    style="width: 100%;margin-bottom: 20px;"
                    row-key="id"
                    :default-expand-all="false">

                <el-table-column
                        prop="id"
                        label="编号"
                        width="170px"
                        >
                </el-table-column>
                <el-table-column
                        prop="uname"
                        label="昵称"
                >
                </el-table-column>
                <el-table-column
                        prop="age"
                        label="年龄"
                >
                </el-table-column>
                <el-table-column
                        prop="location"
                        label="地址"
                >
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="createtime"
                        label="创建时间">
                </el-table-column>
                <el-table-column
                        prop="status"
                        label="状态"
                        align="center"
                >
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.status"
                                :active-value="1"
                                :inactive-value="0"
                                active-color="#ff4949"
                                inactive-color="#13ce66"
                                @change="changeStatus(scope.row.id,scope.row.status)"
                                active-text="冻结"
                                inactive-text="正常">
                        </el-switch>
                    </template>
                </el-table-column>

<!--                &lt;!&ndash;操作按钮&ndash;&gt;-->
<!--                <el-table-column-->
<!--                        label="操作"-->
<!--                        align="center"-->
<!--                >-->
<!--                </el-table-column>-->
            </el-table>
            <el-divider><i class="el-icon-moon-night"></i></el-divider>
            <!--分页栏-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    style="float: right;margin-top: 20px; margin-bottom: 22px"
                    :current-page="currentPage"
                    :page-sizes="[5, 10, 15]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "StudentList",
        data(){
            return{
                //时间范围
                valueTime:[],
                startTime:"",
                endTime:"",
                visible: false,
                id:"",
                //搜索表单
                souSuoFormInline:{
                    name:''
                },
                //表格数据
                tableData: [
                    {
                        id:"",
                        status:"",
                        createtime:"",
                        uname:"",
                        age:"",
                        location:""
                    }
                ],
                //分页信息
                currentPage:1,
                //当前页码数
                pageSize:5,
                //总条数
                total:0,

            }
        },
        //页面加载时初始化表格数据
        created() {
            this.initTable();
        },
        methods: {
            //模糊查询
            search() {
                this.currentPage = 1
                if (this.valueTime.length === 0) {
                    this.startTime = ''
                    this.endTime = ''
                    this.initTable();
                    return
                }
                this.startTime = this.valueTime[0];
                this.endTime = this.valueTime[1]
                //console.log(this.startTime + "......" + this.endTime)
                this.initTable();
            },

            //获取表格数据
            initTable() {
                const that = this;
                that.$http.post("/user/getUserAll?currentPage=" + this.currentPage + "&pageSize=" + this.pageSize + "&startTime=" + this.startTime + "&endTime=" + this.endTime).then(function (resp) {
                    if (resp.data.code === 2000) {
                        that.tableData = resp.data.data.records;
                        that.total = resp.data.data.total;
                        //that.$refs.souSuoForm.resetFields();//将搜索框清空
                    }
                })
            },
            //改变状态
            changeStatus(id,status){
                var that=this;
                this.$http.post('/user/updateByid?id='+id+"&status="+status).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message({
                            message:resp.data.msg,
                            type:"success"
                        })
                    }else{
                        that.$message({
                            message:resp.data.msg,
                            type:"error"
                        })
                    }
                    that.initTable()
                })
            },
            //页数改变时
            handleCurrentChange(val) {
                this.currentPage = val;
                this.initTable();
            },
            //每页数量发生改变时
            handleSizeChange(val) {
                this.pageSize = val;
                this.initTable();
            },
            //重置搜索表单
            resetForm(ref) {
                this.valueTime = []
                this.startTime = ""
                this.endTime = ""
                this.initTable();
            },

        }
    }
</script>

<style scoped>

</style>

