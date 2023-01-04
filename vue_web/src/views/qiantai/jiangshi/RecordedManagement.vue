<template>
    <div id="recordedManagement">
            <div style='position: relative;'>
                <el-tabs v-model="activeName" @tab-click="handleClick" style="padding-left: 10px">
                    <el-tab-pane label="全部课程" name="qbkc"></el-tab-pane>
                    <el-tab-pane label="审核通过" name="shtg"></el-tab-pane>
                    <el-tab-pane label="审核中" name="shz"></el-tab-pane>
                    <el-tab-pane label="审核失败" name="shsb"></el-tab-pane>
                </el-tabs>
                <router-link :to="{ name: 'ModifyCourse'}" class="fr solid_btn" style='position: absolute;right:5px;top:0px;'>新增课程</router-link>
            </div>
            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        prop="date"
                        label="">
                    <template slot-scope="scope">
                        <img :src="scope.row.cover" width="200px" height="80px"/>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="title"
                        label="课程名称">
                </el-table-column>
                <el-table-column
                        label="是否付费"
                >
                    <template slot-scope="scope">
                        <span v-text="scope.row.price>0? '付费' : '免费'" :style="scope.row.price>0? 'color:blue' : ''"></span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="price"
                        label="价格">
                    <template slot-scope="scope">
                        <span style="color: red" v-text="'￥'+scope.row.price" v-if="scope.row.price"></span>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="status"
                        label="状态">
                    <template slot-scope="scope" >
                        <span style="color: forestgreen" v-if="scope.row.checkStatus===1">审核通过</span>
                        <span style="color: #107ec4" v-if="scope.row.checkStatus===2">审核中</span>
                        <el-popover
                                placement="top-start"
                                title="驳回原因:"
                                width="100"
                                trigger="hover"
                                v-if="scope.row.checkStatus===0"
                                :content="scope.row.checkContent">
                            <span style="color: red" id="pppl" slot="reference"  >审核失败</span>
                        </el-popover>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="sxj"
                        label="上下架">
                    <template slot-scope="scope">
                        <el-button style="padding: 2px 5px" type="primary" v-if="scope.row.status==='Normal'&&scope.row.checkStatus===1" @click="updateStatus(scope.row.id,scope.row.status)">课程上架</el-button>
                        <el-button style="padding: 2px 5px" type="danger" v-if="scope.row.status==='Draft'&&scope.row.checkStatus===1" @click="updateStatus(scope.row.id,scope.row.status)">课程下架</el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="操作"
                        align="center"
                    >
                    <template slot-scope="scope">
                         <span>
                             <router-link :to="{path: '/modifyCourse', query: { no: scope.row }}" class="text_link">修改</router-link>
                        </span>
                        <br>
                        <el-button type="text" size="small" @click="ksgl(scope.row.id)">课时管理</el-button>
                    </template>
                </el-table-column>
            </el-table>
        <d-page v-if="pageObj.totalPage > 1 && !notdata" :page="pageObj" @btnClick="getPage"></d-page>
    </div>
</template>

<script>
    import DPage from '@/components/Page'
    export default {
        name: "recordedManagement",
        components: {
            DPage,
        },

        data(){
            return{
                //用于记录头部点击
                nowTab:{
                    index:'',
                },
                //分页，判断是否有数据
                notdata: false,
                pageObj: {
                    //当前页面--》page
                    pageCurrent: '1',
                    //每页条数--》limit
                    pageSize: '5',
                    //总条数
                    totalCount: '',
                    //total的页数（有几页）【totalCount/pageSize】
                    totalPage: '',
                },

                //手机号
                teacherMobile:'',
                status:0,
                //驳回的原因
                tableData: [{
                    id:'',
                    cover:'',
                    title: '',
                    checkContent:'',
                    price:'',
                    checkStatus:'',
                    sxj:'',//0上架(Normal)。。1下架(Draft)
                },
                ],
                activeName: 'qbkc',
            }
        },
        created() {
            this.teacherMobile=window.sessionStorage.getItem("mobile");
            this.tableData.checkStatus='8';
            this.initTable()
        },
        methods:{
            //监听分页的触发
            getPage: function (int) {
                this.pageObj.pageCurrent=int;
                this.tableData.checkStatus="8";
                if (this.nowTab.index==='0'){
                    this.tableData.checkStatus="8";
                }else if (this.nowTab.index==='3'){
                    this.tableData.checkStatus='0';
                }else if (this.nowTab.index==='1'){
                    this.tableData.checkStatus = '1';
                }else if (this.nowTab.index==='2'){
                    this.tableData.checkStatus = '2';
                }
                this.initTable();
            },

            //数据表格
            initTable(){
                var that=this;
                this.$http.get(`/core/course/getCoursByTeacherPhone/${this.pageObj.pageCurrent}/${this.pageObj.pageSize}/${this.teacherMobile}/${this.tableData.checkStatus}`).then(function (resp) {
                    that.tableData=resp.data.result.records;
                    that.pageObj.totalCount=resp.data.result.total;
                    if (resp.data.result.total===0){
                        that.notdata=true;
                    }
                    that.pageObj.totalPage=Math.ceil(that.pageObj.totalCount/5)
                })
            },
            //头部点击事件
            handleClick(tab, event) {
                this.nowTab.index=tab.index;
                if (tab.index==='0'){
                    this.tableData.checkStatus="8";
                }else if (tab.index==='3'){
                    this.tableData.checkStatus='0';
                }else {
                    this.tableData.checkStatus = tab.index;
                }
                this.pageObj.pageCurrent = 1;
                this.pageObj.pageSize = 5;
                this.pageObj.totalPage='',
                this.initTable();
            },
            //课时管理按钮
            ksgl(id){
                this.$router.push('/updateCourse?courseId='+id);
            },
            updateStatus(id,status){
                const title = { 'Draft': '上架', 'Normal': '下架' }
                this.$confirm(`确定要《《${title[status]}》》该课程吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {//确定删除
                    if (status==='Draft'){//未上架
                        status='Normal'
                    }else {
                        status='Draft'
                    }
                    this.$http.post(`core/course/changeStatus`,{id:id,status:status}).then((resp) => {
                        if (resp.data.code===200){
                            this.$message.success(resp.data.msg);
                            this.tableData.checkStatus="8";
                            if (this.nowTab.index==='0'){
                                this.tableData.checkStatus="8";
                            }else if (this.nowTab.index==='3'){
                                this.tableData.checkStatus='0';
                            }else if (this.nowTab.index==='1'){
                                this.tableData.checkStatus = '1';
                            }else if (this.nowTab.index==='2'){
                                this.tableData.checkStatus = '2';
                            }
                            this.initTable()
                        }else {
                            this.$message.error(resp.data.msg);
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '删除已取消'
                    });
                })


            }
        }
    }
</script>

<style scoped>
    .clearfix>span{
        font-size: 15px;
        margin-left: 30px;
        cursor:pointer;
    }
    .clearfix>span:hover{
        color: red;
    }
    .text_link{
        font-size: 12px;
        color: #0099FF;
    }
    #pppl:hover{
        cursor:pointer;
    }
</style>