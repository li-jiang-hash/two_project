<template>
    <div class="pad20">
        <div>
            <el-card class="box-card">
                <el-breadcrumb separator-class="el-icon-arrow-right">
                    <el-breadcrumb-item>{{searchformData.zoneName}}商家管理</el-breadcrumb-item>
                </el-breadcrumb>
            </el-card>
            <!-- 分隔符-->
            <el-divider></el-divider>
            <el-form :inline="true" :model="searchformData" class="demo-form-inline" size="mini">
                <el-form-item label="店铺名称">
                    <el-input v-model="searchformData.sname" placeholder="请输入店铺名称"></el-input>
                </el-form-item>
                <el-form-item label="状态：">
                    <el-select v-model="searchformData.isDisable" class="auto-width" clearable filterable
                               placeholder="状态" style="width: 85px">
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
                    <el-button icon='el-icon-refresh' size="mini" @click="handleReset">重置</el-button>
                    <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="handleAddRow()">
                        添加
                    </el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-table v-loading="ctrl.loading" size="medium" :data="tableData" stripe border style="width: 100%">
                <el-table-column type="index" label="序号">
                </el-table-column>
                <el-table-column prop="id" v-if="false" label="编号">
                </el-table-column>
                <el-table-column prop="sname" label="店铺名称">
                </el-table-column>
                <el-table-column label="所属分类" prop="sortname">
                    <!--<template slot-scope="scope">
                        <span v-if="scope.row.parentname!=null">
                            {{scope.row.parentname}}->【
                        </span>
                        <span v-if="scope.row.subjecttitle!=null">
                            {{scope.row.subjecttitle}}】
                        </span>
                    </template>-->

                </el-table-column>
                <el-table-column align="center" prop="gmt_create" label="添加时间" :formatter="dateFormat">
                </el-table-column>
                <el-table-column prop="sort" label="排序" width="50">
                </el-table-column>
                <el-table-column
                        width="170"
                        prop="is_disable"
                        label="状态"
                        align="center"
                >
                    <template slot-scope="scope">
                        <el-switch
                                v-model="scope.row.isDisable"
                                :active-value="1"
                                :inactive-value="0"
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
                        width="250"
                        align="center">
                    <template slot-scope="scope">
                        <ul class="list-item-actions">
                            <li>
                                <el-button type="success" @click="handleEditRow(scope.row)" size="mini">排序</el-button>
                                <el-button type="danger" @click="handleDelRow(scope.row.id)" size="mini">删除</el-button>
                            </li>
                        </ul>
                    </template>
                </el-table-column>
            </el-table>


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
        <add :id="id" :visible="ctrl.addDialogVisible" :title="ctrl.dialogTitle" @close-callback="closeCallback"></add>
        <edit :formData="formData" :visible="dialogVisible" :title="title" @close-callback="closeCallbackedit"></edit>
    </div>

</template>
<script>
    import { longStackSupport } from "q";
import qs from "qs";
    import Add from "./add";
    import Edit from "./edit"

    export default {
        components: {Add,Edit},
        data() {
            return {
                searchformData: {
                    id: "",
                    sname: "",
                    isDisable: ""
                },
                currentPage: 1,
                pageSize: 10,
                //总条数
                total: 0,
                ctrl: {
                    loading: false,
                    addDialogVisible: false,
                    editDialogVisible: false
                },
                map: {},
                formData: {},
                // 表单数据, 例如新增编辑子项，页面表单
                id: '',
                tableData: [],
                page: {
                    beginPageIndex: 1,
                    pageCurrent: 1,
                    endPageIndex: 8,
                    pageSize: 20,
                    totalCount: 0,
                    totalPage: 0
                },
                //下拉选项框的数据
                options: [{
                    value: '0',
                    label: '正常'
                },
                    {
                        value: '1',
                        label: '禁用'
                    },
                ],
                //排序对话框
                dialogVisible:false,
                title:"",

            }
        },
        created() {
            this.searchformData.id = this.$route.query.zoneId
            this.init()
        },
        methods: {

            //修改 排序
            handleEditRow(row){
                this.title=row.businessInfo.bname;
                this.dialogVisible = true;
                this.formData.sort = row.sort;
                this.formData.id = row.id;
            },

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


            //改变当前页数
            handleSizeChange(val) {
                this.pageSize = val;
                this.init();
            },
            //改变当前每页的条数
            handleCurrentChange(val) {
                this.currentPage = val;
                this.init();
            },

            //改变状态
            changeStatus(id, isDisable) {
                this.$http.post(`/syssystem/tb-zone-business/changeStatus/${id}/${isDisable}`).then(resp => {
                    if (resp.data.code === 2000) {
                        this.$message({
                            message: resp.data.msg,
                            type: "success"
                        })
                    } else {
                        this.$message({
                            message: resp.data.msg,
                            type: "error"
                        })
                    }
                    this.init()
                })
            },
            //新增
            handleAddRow(id) {
                this.id = this.$route.query.zoneId
                this.ctrl.dialogTitle = '新增'
                this.ctrl.addDialogVisible = true
            },
            //删除
            handleDelRow(id) {
                this.$confirm(`确定删除这条数据?`, '我要删除', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$http.delete(`/syssystem/tb-zone-business/deleteZoneBusiness/${id}`).then(resp => {
                        if (resp.data.code === 2000) {
                            this.$message({
                                message: resp.data.msg,
                                type: "success"
                            })
                        } else {
                            this.$message({
                                message: resp.data.msg,
                                type: "error"
                            })
                        }
                        this.init();
                    })
                })
            },
            // 关闭编辑弹窗回调
            closeCallback() {
                this.ctrl.dialogVisible = false
                this.ctrl.addDialogVisible = false
                this.ctrl.editDialogVisible = false
                this.reload()
            },
            // 关闭编辑弹窗回调
            closeCallbackedit() {
                this.dialogVisible = false
                this.reload()
            },
            // 查询
            handleCheck() {
                this.init()
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            reload() {
                this.searchformData = {}
                this.searchformData.id = this.$route.query.zoneId
                console.log("this.$route.query.zoneId="+this.$route.query.zoneId)
                this.init()
                console.log("this.init()="+this.init());
            },

            init() {
                // this.searchformData.zoneId = this.$route.query.zoneId
                this.$http.post("/syssystem/tb-zone-business/managementZone?currentPage=" +this.currentPage +"&pageSize=" + this.pageSize, qs.stringify(this.searchformData)).then(resp => {
                    if (resp.data.code === 2000) {
                        console.log("resp.data.data="+resp.data.data)
                        this.tableData = resp.data.data.records;
                        this.total = resp.data.data.total;
                    }
                    if (resp.data.code === 5000) {
                        this.$message({
                            message: resp.data.msg,
                            type: "error",
                        })
                        this.tableData = [];
                        this.currentPage = 1
                    }
                })
            }
        }
    }
</script>
