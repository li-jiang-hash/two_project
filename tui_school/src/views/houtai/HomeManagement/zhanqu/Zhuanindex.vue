<template>
    <div class="pad20">
            <el-card class="box-card">
            <!--头部搜索-->
            <el-form :inline="true" :model="searchformData" class="demo-form-inline" size="mini">
                <el-form-item label="专区名称">
                    <el-input v-model="searchformData.zoneName" placeholder="请输入专区名称"></el-input>
                </el-form-item>
                <el-form-item label="状态：" >
                    <el-select v-model="searchformData.isDisable" class="auto-width" clearable filterable placeholder="状态" style="width: 85px">
                        <el-option
                                v-for=" item in options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button icon='el-icon-search' size="mini" type="primary" @click="handleCheck">查询</el-button>
                    <el-button icon='el-icon-refresh' size="mini" @click="handleReset" >重置</el-button>
                    <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="handleAddRow()">添加</el-button>
                </el-form-item>
            </el-form>
            <el-table v-loading="ctrl.loading" size="medium" :data="tableData" stripe border style="width: 100%">
                <el-table-column type="index" label="序号" width="50">
                </el-table-column>
                <el-table-column prop="zoneName" label="专区名称">
                </el-table-column>
                <el-table-column prop="id" v-if="false" label="专区名称">
                </el-table-column>
                <el-table-column prop="zoneDesc" label="专区描述">
                </el-table-column>
                <el-table-column
                        width="170"
                        prop="isDisable"
                        label="状态"
                        align="center">
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.isDisable"
                                active-value="1"
                                inactive-value="0"
                                active-color="#ff4949"
                                inactive-color="#13ce66"
                                active-text="禁用"
                                inactive-text="正常"
                                @change="changeStatus(scope.row.id,scope.row.isDisable)">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        width="340">
                    <template slot-scope="scope">
                        <ul class="list-item-actions">
                            <li>
                                <el-button  type="primary" @click="handleBusinessRow(scope.row.id,scope.row.zoneName)" size="mini">商家管理</el-button>
                                <el-button :disabled="scope.row.isDisable==0" type="danger" @click="handleDelRow(scope.row.id)" size="mini">删除</el-button>
                                <el-button  type="success" @click="handleUpdateRow(scope.row)" size="mini">修改</el-button>
                            </li>
                        </ul>
                    </template>
                </el-table-column>
            </el-table>

                <el-divider><i class="el-icon-moon-night"></i></el-divider>
            <div class="mgt20">
                <el-pagination
                        background
                        style="float: right;margin-top: 20px; margin-bottom: 22px"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[1, 5, 10, 15]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>

        <edit :visible="ctrl.dialogVisible" :formData="formData" :title="ctrl.dialogTitle" @close-callback="closeCallback"></edit>
            </el-card>
    </div>
</template>
<script>

    import qs from "qs";
    import Edit from "./edit";
    export default {
        components: {Edit},
        data() {
            return {
                currentPage:1,
                pageSize:5,
                //总条数
                total:0,
                tableData:[],
                ctrl: {
                    loading: false
                },
                //下拉选项框的数据
                options:[
                    {
                    value: "0",
                    label: '正常'
                    },
                    {
                        value: "1",
                        label: '禁用'
                    },
                ],
                // 表单数据, 例如新增编辑子项，页面表单
                formData: {},
                list: [],
                searchformData:{},
                page: {
                    beginPageIndex: 1,
                    pageCurrent: 1,
                    endPageIndex: 8,
                    pageSize: 20,
                    totalCount: 0,
                    totalPage: 0
                }
            }
        },
        mounted() {
            this.init()
            this.reload()
        },
        methods: {
            //改变状态
            changeStatus(id,isDisable){
                this.$http.post(`/syssystem/tb-zone/updateDisable/${id}/${isDisable}`).then( resp => {
                    console.log(resp.data.msg);
                    console.log("12123mmm"+resp.data)
                    if (resp.data.code===2000){
                        this.$message({
                            message: resp.data.msg,
                            type:"success"
                        });
                    }else{
                        this.$message({
                            message: resp.data.msg,
                            type:"error"
                        });
                    }
                    this.init();
                })
            },
            //新增
            handleAddRow() {
                this.formData = {sort:1}
                this.ctrl.dialogTitle = '新增'
                this.ctrl.dialogVisible = true
            },
            //删除
            handleDelRow(id) {
                this.$confirm(`确定删除这条数据?`, '我要删除', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.delete(`/syssystem/tb-zone/removePrefecture/${id}`).then(resp => {
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
                        this.currentPage=1;
                        this.init();
                    })
                })
            },
            // 关闭编辑弹窗回调
            closeCallback() {
                this.ctrl.dialogVisible = false;
                this.reload()
            },
            //编辑
            handleUpdateRow(row) {
                this.formData = row
                this.ctrl.dialogTitle = '编辑'
                this.ctrl.dialogVisible = true
            },
            handleBusinessRow(id,zoneName) {
                this.$router.push({ path: '/home/zhuanqu/business', query: { zoneId: id ,zoneName:zoneName}});
            },
            // 查询条件
            handleCheck() {
                this.init();

            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            // 刷新当前页面
            reload() {
                this.searchformData = {}
                this.init()

            },
            //改变当前页数
            handleSizeChange(val) {
                this.pageSize=val;
                this.init();
            },
            //改变当前每页的条数
            handleCurrentChange(val) {
                this.currentPage=val;
                this.init();
            },
            /*页面加载时获取所有的链接信息*/
            // init(){
            //     this.$http.post(`/syssystem/tb-zone/fuzzySearch/${this.currentPage}/${this.pageSize}`,this.searchformData)).then(resp => {
            //         if (resp.data.code===2000){
            //             console.log(resp.data.data)
            //             this.tableData=resp.data.data.records;
            //             this.total=resp.data.data.total;
            //         }
            //         if (resp.data.code===5000){
            //             this.$message({
            //                 message:resp.data.msg,
            //                 type:"error",
            //             })
            //             this.tableData=[];
            //         }
            //     })
            // },
            init(){
                // syssystem/tb-zone/fuzzySearch?currentPage=1&pageSize=5&id=ttt
                this.$http.post("/syssystem/tb-zone/fuzzySearch?currentPage=" +this.currentPage +"&pageSize=" + this.pageSize,
                    qs.stringify(this.searchformData)).then(resp => {
                    if (resp.data.code===2000){
                        console.log(resp.data.data)
                        this.tableData=resp.data.data.records;
                        this.total=resp.data.data.total;
            
                    }
                    if (resp.data.code===5000){
                        this.$message({
                            message:resp.data.msg,
                            type:"error",
                        })
                        this.tableData=[];
                    }
                })
            },
        }
    }
</script>
