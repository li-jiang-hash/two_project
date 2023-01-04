<template>
    <div class="pad20">
        <div>
            <el-form :inline="true" :model="map" size="mini">
                <el-form-item label="商品类型:">
                    <el-select v-model="map.sortid" filterable placeholder="请选择商品类型" style="width: 130px">
                        <el-option
                                v-for="item in sorts"
                                :label="item.sortname"
                                :value="item.id">
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
<!--                    <el-button type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="addSubject(0,0)">添加</el-button>-->
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
                        label="类别编号">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        prop="sortname"
                        label="类别名称">
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
<!--                        <el-button  type="primary" size="mini" icon="el-icon-circle-plus-outline" @click="addSubject(scope.row.parentId,scope.row.id)">添加</el-button>-->
                        <el-button v-if="scope.row.status===2"  type="primary" @click="shenhe(scope.row.id)" size="mini">去审核</el-button>
                        <el-button v-if="scope.row.status!==2"  type="info"  size="mini">已审核</el-button>
<!--                        <el-button  type="success" @click="editSubject(scope.row)" size="mini">编辑</el-button>-->
                        <el-button  type="danger" @click="handleDelete(scope.row.id)" size="mini">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!--审核的弹出层-->
            <el-dialog
                    title="课程审核"
                    :visible.sync="checkvisible"
                    :before-close="closeCllback1"
                    width="30%">
                <el-form ref="checkformDataRef" :model="checkformData" label-width="100px">
                    <el-form-item label="审核状态：" >
                        <el-radio-group v-model="checkformData.state">
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

<!--            &lt;!&ndash;添加分类的对话框&ndash;&gt;-->
<!--            <el-dialog-->
<!--                    title="添加分类"-->
<!--                    :visible.sync="addDialogVisible"-->
<!--                    width="30%"-->
<!--                    @closed="closeCllback"-->
<!--            >-->
<!--                &lt;!&ndash;添加的表单的数据绑定&ndash;&gt;-->
<!--                <el-form :model="addSubjectFormData" :rules="addSubjectFormRule" ref="addSubjectFormRef"  label-width="100px" class="demo-ruleForm">-->
<!--                    <el-form-item label="分类名称:" prop="title">-->
<!--                        <el-input type="text" v-model="addSubjectFormData.title" autocomplete="off"></el-input>-->
<!--                    </el-form-item>-->
<!--                    <el-form-item label="备注:" prop="remark">-->
<!--                        <el-input type="text" v-model="addSubjectFormData.remark" autocomplete="off"></el-input>-->
<!--                    </el-form-item>-->
<!--                </el-form>-->
<!--                <span slot="footer" class="dialog-footer">-->
<!--                    <el-button @click="addDialogVisible = false">取 消</el-button>-->
<!--                    <el-button type="primary" @click="insertUser">确 定</el-button>-->
<!--                </span>-->
<!--            </el-dialog>-->

            <!--编辑分类的对话框-->
<!--            <el-dialog-->
<!--                    title="编辑品牌信息"-->
<!--                    :visible.sync="editDialogVisible"-->
<!--                    width="30%"-->
<!--                    @closed="closeCllback1"-->
<!--            >-->
<!--                &lt;!&ndash;编辑的表单的数据绑定&ndash;&gt;-->
<!--                <el-form :model="editSubjectFormData" :rules="editSubjectFormRule" ref="editSubjectFormRef"  label-width="100px" class="demo-ruleForm">-->
<!--                    <el-form-item label="品牌名称:" prop="brandname">-->
<!--                        <el-input type="text" v-model="editSubjectFormData.brandname" autocomplete="off"></el-input>-->
<!--                    </el-form-item>-->
<!--                    <el-form-item label="备注:" prop="remark">-->
<!--                        <el-input type="text" v-model="editSubjectFormData.remark" autocomplete="off"></el-input>-->
<!--                    </el-form-item>-->
<!--                </el-form>-->
<!--                <span slot="footer" class="dialog-footer">-->
<!--                <el-button @click="editDialogVisible = false">取 消</el-button>-->
<!--                <el-button type="primary" @click="updateSubject()">确 定</el-button>-->
<!--            </span>-->
<!--            </el-dialog>-->

           <!-- <add :visible="ctrl.addDialogVisible" :formData="formData" :title="ctrl.dialogTitle" @close-callback="closeCllback"></add>
            <edit :visible="ctrl.editDialogVisible" :formData="formData" :title="ctrl.dialogTitle" @close-callback="closeCllback"></edit>-->
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
    //import * as api from '@/api/course'
/*    import Add from './add'
    import Edit from './edit'*/
    export default {
        // components: { Add, Edit },
        data() {
            return {
                sorts:{},
                checkformData:{},
                //审核的弹出层
                checkvisible:false,
                map: {},
                formData: {},
                tableData: [],
                expands: [],
                addSubjectFormData: {
                    remark:""
                },
                editSubjectFormData:{},
                addDialogVisible: false,
                editDialogVisible:false,
                page: {
                    beginPageIndex: 1,
                    pageCurrent: 1,
                    endPageIndex: 8,
                    pageSize: 5,
                    totalCount: 0,
                    totalPage: 0
                },
                    statusIdList: {}
                ,
                //添加框的表单验证
                addSubjectFormRule:{
                    title:[
                        {required:true,message:"分类名称不能为空",trigger:"blur"}
                    ]
                },
                editSubjectFormRule:{
                    title:[
                        {required:true,message:"分类名称不能为空",trigger:"blur"}
                    ]
                }
            }
        },
        created() {
            this.searchSubject()
            this.findAllBrands();
            this.goodsSort();
        },
        methods: {
            goodsSort(){
                this.$http.get("commodity/sort/findAllSort").then(res=>{
                    if (res.data.code===2000){
                        this.sorts = res.data.data;
                    }
                })
            },
            //提交审核的表单
            submitCheckForm(){
                var that = this
                this.$http.post(`/commodity/sort/updateStatus`,this.checkformData).then(function (resp) {
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
            //点击审核的弹出层
            shenhe(id){
                this.checkformData.id = id
                this.checkvisible = true
            },
            //页面加载查询所有品牌
            findAllBrands(){
                this.$http.post("commodity/brand/findAllBrands").then(res=>{
                    if (res.data.code===2000){
                        this.statusIdList = res.data.data;
                    }
                })
            },
            //编辑分类框
            editSubject(row){
                this.editDialogVisible = true
                this.editSubjectFormData = row
                //alert(this.editSubjectFormData.id)
            },
            //添加分类框
            addSubject(parentId,id){
                this.addDialogVisible = true
                this.addSubjectFormData.parentId = parentId
                this.addSubjectFormData.id = id
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
            // 修改状态
            handleChangeStatusId(id, statusId,parentId) {
                const title = { 1: '正常', 0: '禁用' }
                this.$confirm(`确定要${title[statusId]}吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.changeStatusId(id, statusId,parentId)
                    this.reload()
                }).catch(() => {
                    this.reload()
                })
            },
            // 请求更新状态方法
            changeStatusId(id, statusId,parentId) {
                //alert(statusId)
                var that =this
                this.$http.post(`/core/subject/changeStatusId`, {"id":id,"statusId":statusId,"parentId":parentId}).then(function (resp) {
                    console.log(resp)
                    if (resp.data.code==2000){
                        that.$message.success(resp.data.msg);
                        that.reload()
                    }
                })
            },
            // 查询所有类别
            searchSubject(){
                var that =this
                this.$http.post(`/commodity/sort/findSortByCondition/${this.page.pageCurrent}/${this.page.pageSize}`,this.map).then(function (resp) {
                    if (resp.data.code===2000){
                        that.tableData = resp.data.data.records
                        that.page.totalCount = resp.data.data.total
                        that.page.pageSize = resp.data.data.size
                    }
                })
            },
            //添加分类方法
            insertUser(){
                var that = this
                this.$http.post(`/core/subject/insertsubject`,this.addSubjectFormData).then(function (resp) {
                    //console.log(resp)
                    if (resp.data.code==200){
                        that.$message.success(resp.data.msg);
                        that.addDialogVisible = false
                    }
                })
            },
            //修改分类的方法
            updateSubject(){
                //alert(this.editSubjectFormData.id)
                //alert(this.editSubjectFormData.title)
                var that = this
                this.$http.post(`/core/subject/updatesubject`,this.editSubjectFormData).then(function (resp) {
                    //console.log(resp)
                    if (resp.data.code==200){
                        that.$message.success(resp.data.msg);
                        that.editDialogVisible = false
                    }
                })
            },
            // 关闭弹窗回调
            closeCllback() {
                this.addDialogVisible = false
                this.$refs.addSubjectFormRef.resetFields();
                this.reload()
            },
            closeCllback1(){
                this.checkvisible = false
                this.$refs.editSubjectFormRef.resetFields();
                this.reload()
            },
            // 删除
            handleDelete(id) {
                //alert(id)
                var that = this
                this.$confirm(`确定要删除吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(()=>{
                    this.$http.post("/commodity/sort/deleteSort?id="+id).then(function (resp) {
                    console.log(resp)
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                    }
                    that.searchSubject()

                })})
                //this.reload()
            },
            // 刷新当前页面
            reload() {
                this.map = {}
                this.editSubjectFormData = {}
                this.searchSubject()
            },
            textClass(userType) {
                return {
                    c_red: userType === 0,
                    c_blue: userType === 2
                }
            }
        }
    }
</script>
