<template>
    <div class="pad20">
        <el-card class="box-card">
        <!--头部搜索-->
        <el-form :inline="true" :model="searchformData" class="demo-form-inline" size="mini">
            <el-form-item label="链接名称">
                <el-input v-model="searchformData.linkName" placeholder="请输入链接名称"></el-input>
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
                <el-button icon='el-icon-search'size="mini" type="primary" @click="handleCheck">查询</el-button>
                <el-button icon='el-icon-refresh' size="mini" @click="handleReset" >重置</el-button>
                <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="handleAddRow()">添加</el-button>
            </el-form-item>
        </el-form>

        <!--表格数据-->
        <div>
            <el-table v-loading="ctrl.loading" size="medium" :data="tableData" stripe border style="width: 100%">
                <el-table-column type="index" label="序号" width="50" align="center" >
                </el-table-column>
                <el-table-column prop="linkId" v-if="false" label="id号"  align="center" >
                </el-table-column>
                <el-table-column prop="linkName,linkUrl" label="链接名称/链接地址" align="center" >
                    <template slot-scope="scope"><el-link type="primary" @click="jumpUrl(scope.row.linkUrl)" target="_blank">{{scope.row.linkName}}</el-link> 【{{scope.row.linkUrl}}】</template>
                </el-table-column>
                <el-table-column
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
                                @change="changeStatus(scope.row.linkId,scope.row.isDisable)"
                        >
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        align="center"
                >
                    <template slot-scope="scope">
                        <ul class="list-item-actions">
                            <li>
                                <el-button type="danger"
                                           size="mini"
                                           :disabled="scope.row.isDisable==0"
                                           @click="handleDelete(scope.row.linkId)">删除</el-button>
                                <el-button type="success" @click="handleUpdateRow(scope.row)" size="mini">修改</el-button>
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
        </div>
        <add :visible="ctrl.addDialogVisible" :title="ctrl.dialogTitle" @close-callback="closeCallback"></add>
        <edit :visible="ctrl.dialogVisible" :formData="formData" :title="ctrl.dialogTitle" @close-callback="closeCallback"></edit>
        </el-card>
    </div>
</template>

<script>

    import Add from "./add";
    import Edit from "./edit";
    export default {
        name: "HomeLink",
        components: {Edit, Add},
        data(){
            return{
                currentPage:1,
                pageSize:5,
                //总条数
                total:0,
                searchformData:{},
                formData:{},
                tableData:[],
                //下拉选项框的数据
                options:[{
                    value: '0',
                    label: '正常'
                },
                    {
                        value: '1',
                        label: '禁用'
                    },
                ],
                // 页面控制数据，例如形式弹窗，显示加载中等
                ctrl: {
                    loading: false,
                    addDialogVisible: false,
                    dialogVisible: false
                },
            }
        },
        created() {
            this.init()
        },
        methods:{
            //新增
            handleAddRow() {
                this.ctrl.dialogTitle = '新增'
                this.ctrl.addDialogVisible = true
            },
            //编辑
            handleUpdateRow(data) {
                this.formData = data
                this.ctrl.dialogTitle = data.linkName + ' —— 编辑'
                this.ctrl.dialogVisible = true
                this.init()
            },
            // 关闭编辑弹窗回调
            closeCallback() {
                this.ctrl.dialogVisible = false;
                this.ctrl.addDialogVisible = false
                this.reload()
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
            //条件查询方法
            handleCheck(){
                this.init()
            },
            //重置方法
            handleReset(){
                this.reload()
            },
            //清空查询的表单数据
            reload(){
                this.searchformData={}
                this.init()
            },
            // 友情链接跳转
            jumpUrl(url) {
                window.open(url)
            },
            /*页面加载时获取所有的链接信息*/
            init(){
                var that=this;
                this.$http.post(`/home/link/getAllLink/${this.currentPage}/${this.pageSize}`,this.searchformData).then(function (resp) {
                    if (resp.data.code===2000){
                        that.tableData=resp.data.data.records;
                        that.total=resp.data.data.total;
                    }
                    if (resp.data.code===5000){
                        that.$message({
                            message:resp.data.msg,
                            type:"error",
                        })
                        that.tableData=[];
                    }
                })
            },
            //改变状态
            changeStatus(linkId,isDisable){
                var that=this;
                this.$http.post(`/home/link/changeStatus/${linkId}/${isDisable}`).then(function (resp) {
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
                    that.init()
                })
            },
            //删除选中的头部导航
            handleDelete(linkId) {
                var that=this;
                this.$http.post(`/home/link/deleteLink/${linkId}`).then(function (resp) {
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
                    that.init();
                })
            },
        }
    }
</script>

<style scoped>

</style>