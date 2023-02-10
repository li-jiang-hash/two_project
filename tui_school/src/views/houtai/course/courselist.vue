<template>
    <div class="pad20">
        <div>
            <el-form :inline="true" :model="map" size="mini">
                <el-form-item label="储藏方式:">
                    <el-select v-model="map.depositname" filterable placeholder="请选择商品类型" style="width: 130px">
                        <el-option
                                v-for="item in sorts"
                                :label="item.depositname"
                                :value="item.depositname">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="审核状态：" >
                    <el-select v-model="map.status" class="auto-width" clearable filterable placeholder="请选择审核状态" style="width: 130px">
                        <el-option
                                label="待审核"
                                :value="2">
                        </el-option>
                        <el-option
                                label="审核通过"
                                :value="0">
                        </el-option>
                        <el-option
                                label="审核驳回"
                                :value="1">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button icon='el-icon-search' type="primary" @click="handleCheck">查询</el-button>
                    <el-button icon='el-icon-refresh' class="filter-item" @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table
                    :data="tableData"
                    style="width: 100%"
                    border
                    :default-expand-all="false">
                <el-table-column
                        header-align="center"
                        align="center"
                        type="int"
                        sortable
                        prop="id"
                        label="储藏id">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        prop="depositname"
                        label="储藏方式名称">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        prop="checkContent"
                        label="审核备注">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        prop="status"
                        label="审核状态">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status===0">审核成功</span>
                        <span v-if="scope.row.status===1">审核驳回</span>
                        <span v-if="scope.row.status===2">待审核</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作"
                                 header-align="center"
                                 align="center">
                    <template slot-scope="scope">
                        <el-button v-if="scope.row.status===2"  type="primary" @click="shenhe(scope.row.id)" size="mini">去审核</el-button>
                        <el-button v-if="scope.row.status!==2"  type="info"  size="mini">已审核</el-button>
                        <el-button  type="danger" @click="handleDelete(scope.row.id)" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!--审核的弹出层-->
            <el-dialog
                    title="储藏方式审核"
                    :visible.sync="checkvisible"
                    :before-close="closeCllback1"
                    width="30%">
                <el-form ref="checkformDataRef" :model="checkformData" label-width="100px">
                    <el-form-item label="审核状态：" >
                        <el-radio-group v-model="checkformData.status">
                            <el-radio :label="0">通过</el-radio>
                            <el-radio :label="1">不通过</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="备注：" style="margin-left: -40px">
                        <el-input type="textarea" :rows="3" placeholder="请输入备注内容" v-model="checkformData.checkContent"></el-input>
                    </el-form-item>
                </el-form>
                <el-row style="margin-top:17px; ">
                    <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="resetCheck">取 消</el-button>
                    <el-button style="float:right" size="mini" type="primary" @click="submitCheckForm">确定</el-button>
                </el-row>
            </el-dialog>
            <el-pagination
                    background
                    style="float: right;margin-top: 20px; margin-bottom: 22px"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :page-size="page.pageSize"
                    :page-sizes="[5, 8, 10, 15, 20, 30]"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="page.totalCount">
            </el-pagination>
        </div>
    </div>
</template>
<script>
import qs from 'qs'
    export default {
        data() {
            return {
                sorts:[],
                checkformData:{},
                //审核的弹出层
                checkvisible:false,
                map: {},
                formData: {},
                tableData: [],
                page: {
                    beginPageIndex: 1,
                    currentPage: 1,
                    endPageIndex: 8,
                    pageSize: 5,
                    totalCount: 0,
                    totalPage: 0
                },               
            }
        },
        created() {
            this.searchSubject()
            this.goodsDeposit();
        },
        methods: {
            // 查询储藏表数据
            searchSubject(){
                var that =this
                this.$http.post(`/syssystem/g-deposit/findDeposits?currentPage=${this.page.currentPage}&pageSize=${this.page.pageSize}`,qs.stringify(this.map)).then(function (resp) {
                    if (resp.data.code===2000){
                        that.tableData = resp.data.data.records
                        that.page.totalCount = resp.data.data.total
                        that.page.pageSize = resp.data.data.size
                    }
                })
            },
            // 查询储藏名称
            goodsDeposit(){
                this.$http.get(`/syssystem/g-deposit/findAllDeposit`).then(res=>{
                    if (res.data.code===2000){
                        this.sorts = res.data.data;
                    }
                })
            },
            //提交审核的表单
            submitCheckForm(){
                var that = this
                this.$http.post(`/syssystem/g-deposit/shenhe`,this.checkformData).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                        that.checkvisible = false
                        that.searchSubject()
                    }else {
                        that.$message.error(resp.data.msg);
                        that.checkvisible = false
                        that.searchSubject()
                    }
                })
            },
            //审核取消后清空
            resetCheck(){
                this.checkvisible = false
                this.checkformData.checkContent = ""
                this.reload()
            },
            // 删除
            handleDelete(id) {
                var that = this
                this.$confirm(`确定要删除吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(()=>{
                    this.$http.post(`/syssystem/g-deposit/${id}`).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                    }
                    that.searchSubject()
                })})
            },
            //点击审核的弹出层
            shenhe(id){
                this.checkformData.id = id
                this.checkvisible = true
            },
            handleSizeChange(val) {
                this.page.pageSize = val
                this.searchSubject()
            },
            handleCurrentChange(val) {
                this.page.pageCurrent = val
                this.searchSubject()
            },
            // 查询条件
            handleCheck() {
                this.searchSubject()
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },  
            // 关闭弹窗回调
            closeCllback1(){
                this.checkvisible = false
                this.reload()
            },
            // 刷新当前页面
            reload() {
                this.map = {}
                this.searchSubject()
            }
        }
    }
</script>
