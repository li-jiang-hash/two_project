<template>
    <div class="pad20">
        <el-card class="box-card">
            <el-form :inline="true" :model="formData" class="demo-form-inline" size="mini">
                <el-form-item label="广告标题">
                    <el-input v-model="formData.title" placeholder="请输入导航标题"></el-input>
                </el-form-item>
                <el-form-item label="状态：" >
                    <el-select v-model="formData.isDisable" class="auto-width" clearable filterable placeholder="状态" style="width: 85px">
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
                    <el-button icon='el-icon-refresh' size="mini" @click="handleReset">重置</el-button>
                    <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="handleAddRow()">添加</el-button>
                </el-form-item>
            </el-form>
            <div>
                <el-table v-loading="ctrl.loading" size="medium" :data="tableData" stripe border style="width: 100%">
                    <el-table-column type="index" label="序号" width="50" align="center">
                    </el-table-column>
                    <el-table-column prop="id" v-if="false" label="轮播id" width="50" align="center">
                    </el-table-column>
                    <el-table-column prop="imageUrl" label="广告图片" width="222" align="center">
                        <template slot-scope="scope">
                            <img  :src="scope.row.imageUrl" width="200" height="50"/>
                        </template>
                    </el-table-column>
                    <el-table-column prop="title" label="广告标题" align="center"></el-table-column>
                    <el-table-column prop="gmtCreate" label="开始时间" width="180" align="center" :formatter="dateFormat">
                    </el-table-column>
                    <el-table-column prop="gmtModified" label="结束时间" width="180" align="center" :formatter="dateFormat">
                    </el-table-column>
                    <el-table-column
                            label="状态"
                            prop="isDisable"
                            align="center"
                            width="200">
                        <template slot-scope="scope">
                            <el-switch
                                    v-model="scope.row.isDisable"
                                    active-value="1"
                                    inactive-value="0"
                                    active-color="#ff4949"
                                    inactive-color="#13ce66"
                                    active-text="禁用"
                                    inactive-text="正常"
                                    @change="changeStatus(scope.row.id,scope.row.isDisable)"
                            >
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            width="170"
                            align="center"
                    >
                        <template slot-scope="scope">
                            <ul class="list-item-actions">
                                <li>
                                    <el-button
                                            type="danger"
                                            size="mini"
                                            :disabled="scope.row.isDisable==0"
                                            @click="handleDelete(scope.row.id)"
                                    >删除</el-button>
                                    <el-button type="success" size="mini" @click="handleUpdateRow(scope.row)">编辑</el-button>
                                </li>
                            </ul>
                        </template>
                    </el-table-column>
                </el-table>

                <el-divider><i class="el-icon-moon-night"></i></el-divider>
                <!--分页-->
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
            <edit :visible="ctrl.dialogVisible" :editformData="editformData" :title="ctrl.dialogTitle" @close-callback="closeCallback"></edit>
        </el-card>
    </div>
</template>

<script>
    import Add from "./add";
    import Edit from "./edit";
    export default {
        name: "LunBoManagement",
        components: {Edit, Add},
        data(){
            return{
                currentPage:1,
                pageSize:5,
                //总条数
                total:0,
                editformData:{},
                //表单数据对象
                formData:{
                    title:"",
                    isDisable:"",
                },
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
                tableData:[],
                ctrl: {
                    loading: false,
                    remoteAuthorLoading: false,
                    dialogVisible: false,
                    addDialogVisible: false
                },
            }
        },
        created() {
            this.init()
        },
        methods:{
            //时间转换
            dateFormat(row, column, cellValue, index) {
                const daterc = row[column.property]
                //console.log(row, column)
                if (daterc) {
                    if (daterc.indexOf('T') === -1) return daterc
                    const arr = daterc.split('T')
                    const d = arr[0]
                    const darr = d.split('-')
                    const t = arr[1]
                    const tarr = t.split('.000')
                    const marr = tarr[0].split(':')
                    const dd = darr[0] + '-' + darr[1] + '-' + darr[2] + ' ' + marr[0] + ':' + marr[1] + ':' + marr[2].substring(0, 2)
                    return dd
                }
            },

            //新增
            handleAddRow() {
                this.ctrl.dialogTitle = '新增'
                this.ctrl.addDialogVisible = true
            },
            //编辑
            handleUpdateRow(data) {
                this.editformData = data
                this.ctrl.dialogTitle = '编辑'
                this.ctrl.dialogVisible = true
                this.init();
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
            handleCheck(){
                this.currentPage = 1
                this.init()
            },
            handleReset(){
                this.reload()
            },
            reload(){
                this.formData={}
                this.init()
            },
            // 关闭编辑弹窗回调
            closeCallback() {
                this.ctrl.dialogVisible = false;
                this.ctrl.addDialogVisible = false
                this.reload()
            },
            init(){
                var that=this;
                this.$http.post(`/home/banner/rotationSearch/${this.currentPage}/${this.pageSize}`,this.formData).then(function (resp) {
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
            changeStatus(id,isDisable){
                var that=this;
                this.$http.post(`/home/banner/changeStatus/${id}/${isDisable}`).then(function (resp) {
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
            handleDelete(id) {
                var that=this;
                this.$http.get(`/home/banner/deleteBanner/${id}`).then(function (resp) {
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
                    that.currentPage =1
                    that.init();
                })
            },
        }

    }
</script>

<style scoped>

</style>