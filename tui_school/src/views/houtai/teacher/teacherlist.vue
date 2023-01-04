<template>
    <div class="pad20">
        <div class="filter-container">
            <!--查询的表单数据-->
            <el-form :inline="true" :model="map" size="mini">
                <el-form-item label="讲师名称:">
                    <el-input v-model.trim="map.name"></el-input>
                </el-form-item>
                <el-form-item label="手机号：">
                    <el-input v-model.trim="map.mobile"></el-input>
                </el-form-item>
                <el-form-item label="状态:">
                    <el-select v-model="map.status" class="auto-width" clearable filterable placeholder="状态" style="width: 85px">
                        <el-option
                                v-for="item in opts.isPutawayList"
                                :key="item.code"
                                :label="item.desc"
                                :value="item.code">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button icon='el-icon-search' type="primary" @click="handleCheck">查询</el-button>
                    <el-button icon='el-icon-refresh' class="filter-item" @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <!--修改的弹出框-->
        <el-dialog
                title="修改"
                :visible.sync="visible"
                @close="closeCllback"
                width="60%">
            <el-form ref="formDataRef" :model="formData" label-width="100px">
                <el-form-item label="用户手机：">
                    <el-input v-model="formData.mobile"></el-input>
                </el-form-item>
                <el-form-item label="讲师名称：">
                    <el-input v-model="formData.name"></el-input>
                </el-form-item>
                <el-form-item label="邮箱：">
                    <el-input v-model="formData.email"></el-input>
                </el-form-item>
                <el-form-item label="讲师简介:">
                    <quill-editor class="editor"
                                  v-model="formData.intro"
                                  ref="myQuillEditor"
                                  :options="editorOption">
                    </quill-editor>
                </el-form-item>
                <el-row style="margin-top:17px; ">
                    <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="visible = false">取 消</el-button>
                    <el-button style="float:right" size="mini" type="primary" @click="submitForm">确定</el-button>
                </el-row>
            </el-form>
        </el-dialog>
        <!--数据表格-->
        <el-table  size="medium" :data="list" stripe border>
            <el-table-column type="index" label="序号" width="100" align="center">
            </el-table-column>
            <el-table-column label="手机号" align="center" prop="mobile">
                <template slot-scope="scope">
                    <span @click="xinxi(scope.row)" v-text="scope.row.mobile" style="color: #409EFF;cursor: pointer"></span>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="讲师名称" align="center">
            </el-table-column>
            <el-table-column prop="email" label="邮箱" align="center">
            </el-table-column>
            <el-table-column
                    width="150"
                    prop="status"
                    label="状态"
                    align="center">
                <template slot-scope="scope">
                    <el-switch
                            v-model="scope.row.status"
                            @change="handleChangeIsPutaway(scope.row.id,scope.row.status)"
                            active-value="1"
                            inactive-value="0"
                            active-color="#ff4949"
                            inactive-color="#13ce66"
                            active-text="禁用"
                            inactive-text="正常">
                    </el-switch>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                    <el-button  type="success" @click="handleEdit(scope.row)" size="mini">修改</el-button>
                </template>
            </el-table-column>
        </el-table>
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

        <el-dialog
                title="讲师信息"
                :visible.sync="teachervisible"
                :before-close="closeCllback2"
                width="50%">
            <el-form :inline="true" :data="xin">
                <el-card >
                    <el-divider style="font-weight:bold; background-color:#f4f4f5">讲师个人信息</el-divider>
                    <el-row>
                        <el-col :span="8"><div>
                            <el-form-item label="头像:">
                                <div class="demo-image">
                                    <div class="block">
                                        <el-image style="width: 100px; height: 100px" :src="xin.avatar"></el-image>
                                    </div>
                                </div>
                            </el-form-item>
                        </div></el-col>
                        <el-col :span="8"><div>
                            <el-form-item label="讲师编号:">
                                <span >{{xin.id}}</span>
                            </el-form-item>
                            <br/>
                            <el-form-item label="讲师名称:">
                                <span >{{xin.name}}</span>
                            </el-form-item>
                        </div></el-col>
                        <br/><br/><br/><br/>
                        <el-col :span="8"><div>
                            <el-form-item label="状态:">
                                <template slot-scope="scope">
                                    <span>{{xin.status == 1 ? "禁用" : "正常"}}</span>
                                </template>
                            </el-form-item>
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><div>
                            <el-form-item label="手机号:">
                                <span >{{xin.mobile}}</span>
                            </el-form-item>
                        </div></el-col>
                        <el-col :span="8"><div>
                            <el-form-item label="邮箱:">
                                <span >{{xin.email}}</span>
                            </el-form-item>
                        </div></el-col>
                        <el-col :span="8"><div>
                            <el-form-item label="职位:">
                                <span v-text="xin.level===0?'高级讲师':'首席讲师'"></span>
                            </el-form-item>
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8"><div>
                            <el-form-item label="注册时间:">
                                <span >{{xin.gmtCreate}}</span>
                            </el-form-item>
                        </div></el-col>
                    </el-row>
                </el-card>
                <br/>
                <el-card >
                    <el-divider style="font-weight:bold; background-color:#f4f4f5">简介</el-divider>
                    <el-form-item v-html="xin.intro">
                    </el-form-item>
                </el-card>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "teacherlist",
        data() {
            return {
                editorOption:{},
                xin:{},
                editor: {},
                dialogVisible: false,
                editVisible: false,
                teachervisible:false,
                visible: false,
                map: {},
                formData: {},
                list: [],
                opts: {
                    isFreeList: [{
                        code: 1,
                        desc:"收费"
                    },{
                        code: 0,
                        desc:"免费"
                    }],
                    isPutawayList: [{
                        code: 0,
                        desc:"正常"
                    },{
                        code: 1,
                        desc:"禁用"
                    }]
                },
                page: {
                    beginPageIndex: 1,
                    currentPage: 1,
                    endPageIndex: 8,
                    pageCurrent: 1,
                    pageSize: 5,
                    totalCount: 0,
                    totalPage: 0
                }
            }
        },
        created() {
            this.searchTeacher()
        },
        methods: {
            closeCllback2(){
                this.teachervisible = false
            },
            //信息框
            xinxi(row){
                this.teachervisible = true
                this.xin = row;
            },
            // 课程分类分页列表接口
            searchTeacher(){
                var that =this
                this.$http.post(`/core/teacher/findTeacher/${this.page.pageCurrent}/${this.page.pageSize}`,this.map).then(function (resp) {
                    if (resp.data.code==200){
                        that.list = resp.data.result.records
                        that.page.totalCount = resp.data.result.total
                        that.page.pageSize = resp.data.result.size
                    }
                })
            },
            //提交修改的表单
            submitForm(){
                var that = this
                this.$http.post(`/core/teacher/updateTeacher`,this.formData).then(function (resp) {
                    if (resp.data.code==200){
                        that.$message.success(resp.data.msg);
                        that.visible = false
                    }else {
                        that.$message.error(resp.data.msg);
                        that.visible = false
                    }
                })
            },
            //查询
            handleCheck() {
                this.searchTeacher()
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            handleSizeChange(val) {
                this.page.pageSize = val
                this.searchTeacher()
            },
            handleCurrentChange(val) {
                this.page.pageCurrent = val
                this.searchTeacher()
            },
            closeCllback2(){
                this.teachervisible = false
            },
            // 刷新当前页面
            reload() {
                this.map = {}
                this.formData = {}
                this.searchTeacher()
            },
            // 关闭编辑弹窗回调
            closeCllback() {
                //this.dialogVisible = false;
                //this.editVisible = false;
                this.visible = false;
                this.$refs.formDataRef.resetFields();
                this.reload()
            },
            // 修改上下架状态
            handleChangeIsPutaway(id, status) {
                const title = {"1": '禁用', "0": '正常'}
                this.$confirm(`确定要${title[status]}吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.changeIsPutaway(id, status)
                    //this.reload()
                }).catch(() => {
                    this.reload()
                })
            }
            ,
            // 请求更新状态方法
            changeIsPutaway(id, status) {
                var that =this
                this.$http.post(`/core/teacher/changeStatus`, {"id":id,"status":status}).then(function (resp) {
                    if (resp.data.code==200){
                        that.$message.success(resp.data.msg);
                    }
                })
                that.reload()
            },
            // 修改弹窗
            handleEdit(row) {
                this.formData = row
                this.visible = true
            }
        }
    }
</script>

<style scoped>

</style>
