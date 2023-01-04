<!--头部导航-->
<template>
    <div class="pad20">
        <el-card class="box-card">
        <el-form :inline="true" :model="formData" class="demo-form-inline" size="mini">
            <el-form-item label="导航标题">
                <el-input v-model="formData.headName" placeholder="请输入导航标题"></el-input>
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
                <el-button type="primary" icon="el-icon-circle-plus-outline" size="mini" @click="open()">添加</el-button>
            </el-form-item>
        </el-form>

        <!--表格数据-->
        <el-table
                :data="tableData"
                stripe
                size="medium"
                style="width: 100%"
                :border="true"
        >
            <el-table-column
                    prop="headId"
                    label="序号"

            >
            </el-table-column>
            <el-table-column
                    prop="headName"
                    label="导航标题"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="href"
                    label="接口路径"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="isDisable"
                    label="状态"
                    align="center"
            >
            <template slot-scope="scope">
                <el-switch
                        v-model="scope.row.isDisable"
                        active-value="1"
                        inactive-value="0"
                        active-color="#ff4949"
                        inactive-color="#13ce66"
                        @change="changeStatus(scope.row.headId,scope.row.isDisable)"
                        active-text="禁用"
                        inactive-text="正常">
                </el-switch>
            </template>
            </el-table-column>

            <!--操作按钮-->
            <el-table-column
                    label="操作"
                    align="center"
            >
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="danger"
                            :disabled="scope.row.isDisable==0"
                            @click="handleDelete(scope.row.headId)">删除</el-button>
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.row)" type="success">编辑</el-button>
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
        </el-card>
        <!--添加用户的对话框-->
        <el-dialog  title="添加" :visible.sync="dialogFormVisible" width="600px" @closed="close">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForms" label-width="100px"  class="demo-ruleForm">
                <el-form-item label="导航标题:" prop="headId">
                    <el-select v-model="ruleForm.headId" class="auto-width" clearable filterable placeholder="导航标题" style="width: 200px">
                        <el-option
                                v-for=" item in addTableData"
                                :key="item.headId"
                                :label="item.headName"
                                :value="item.headId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="打开方式:" prop="skipWay">
                    <el-radio-group v-model="ruleForm.skipWay">
                        <el-radio :label="0" border>新窗口打开</el-radio>
                        <el-radio :label="1" border>同窗口打开</el-radio>
                    </el-radio-group>
                </el-form-item>

                <div class="eldialog">
                    <el-form-item>
                        <el-button type="primary" @click="addHeadNav()">提交</el-button>
                        <el-button @click="reset()" >重置</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>

        <!--修改用户的对话框-->
        <el-dialog
                @closed="editclose"
                :title="title"
                :visible.sync="editdialogFormVisible"
                width="30%"
                center>

            <el-form :model="editruleForm" :rules="editrules" ref="editruleForms" label-width="100px"  class="demo-ruleForm">
                <el-form-item label="导航标题:" prop="headName">
                    <el-input v-model="editruleForm.headName"></el-input>
                </el-form-item>
                <el-form-item label="打开方式:" prop="skipWay">
                    <el-radio-group v-model="editruleForm.skipWay">
                        <el-radio :label="0" border>新窗口打开</el-radio>
                        <el-radio :label="1" border>同窗口打开</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序:" prop="sort">
                    <el-input-number v-model="editruleForm.sort" @change="handleChange" :min="1" :max="100" label="描述文字"></el-input-number>
                </el-form-item>
                <div class="eldialog">
                    <el-form-item>
                        <el-button type="primary" @click="editHeadNav()">提交</el-button>
                        <el-button @click="editreset()" >重置</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "HeadNavigation",
        data(){
            return {
                //用来接受后台传来的表中的数据
                tableData: [],
                currentPage:1,
                pageSize:5,
                //总条数
                total:0,
                //表单数据对象
                formData:{
                    headName:"",
                    isDisable:"",
                    //href:'',
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
                dialogFormVisible:false,
                editdialogFormVisible:false,
                ruleForm: {
                    headId:'',
                    skipWay: '',
                },
                editruleForm: {
                    headId:'',
                    headName: '',
                    isDisable: '',
                    skipWay: '',
                    sort: 1
                },
                rules: {
                    headId: [
                        {required: true, message: '请选择头部导航栏名称', trigger: 'change'},
                    ],
                    skipWay: [
                        {required: true, message: '请选择打开方式', trigger: 'change'},
                    ]
                },
                editrules: {
                    headName: [
                        {required: true, message: '头部导航栏名称不能为空', trigger: 'blur'},
                    ],
                    skipWay: [
                        {required: true, message: '请选择打开方式', trigger: 'change'},
                    ]
                },
                //不启用的导航 用于添加导航回显
                addTableData:[],
                //修改会话框标题
                title:""

            }
        },
        created() {
            //页面加载时调用查询的方法
            this.init();
        },
        methods:{
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
            //条件查询
            handleCheck(){
                this.init()
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            //页面重载
            reload(){
                this.formData={}
                this.init()
            },
            //开启dialog层
            open(){
                this.dialogFormVisible=true;
                this.$http.get("/home/headNav/navigationSearch").then(resp => {
                    if(resp.data.code === 2000){
                        this.addTableData = resp.data.data;
                    }
                })
            },
            //重置表单数据
            reset(){
                this.$refs.ruleForms.resetFields();
            },
            editreset(){
                this.$refs.editruleForms.resetFields();
            },
            close(){
                this.$refs.ruleForms.resetFields();
            },
            editclose(){
                this.$refs.editruleForms.resetFields();
            },
            //分页查询当前的所有数据
            init(){
              this.$http.post("/home/headNav/fuzzySearch?currentPage="+this.currentPage+"&pageSize="+this.pageSize,this.formData).then(resp => {
                 if (resp.data.code===2000){
                     this.tableData=resp.data.data.records;
                     this.total=resp.data.data.totel;
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
            handleEdit(row) {
                this.title=row.headName;
                this.editdialogFormVisible=true
                this.editruleForm=row
                this.init()
            },
            handleChange(value) {
                this.editruleForm.sort=value
            },
            //删除选中的头部导航
            handleDelete(headId) {
                this.$http.get("/home/headNav/removeNavigation?headId="+headId).then(resp => {
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
                    this.init();
                })
            },
            //添加导航标题
            addHeadNav(){
                this.$refs.ruleForms.validate((valid)=>{
                    if (valid){
                        this.$http.post("/home/headNav/insertNavigation",this.ruleForm).then( resp => {
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
                            this.dialogFormVisible=false;
                            this.init();
                        })
                    }else {
                        this.$message({
                            message:"输入框不能为空",
                            type:"error"
                        })
                    }
                })
            },
            //改变状态
            changeStatus(headId,isDisable){
                var that=this;
                this.$http.post(`/home/headNav/updateDisable/${headId}/${isDisable}`).then(function (resp) {
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
            editHeadNav(){
                this.$refs.editruleForms.validate((valid)=>{
                    if (valid){
                        this.$http.post("/home/headNav/updateNavigation",this.editruleForm).then(resp => {
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
                            this.editdialogFormVisible=false;
                            this.init();
                        })
                    }else {
                        this.$message({
                            message:"输入框不能为空",
                            type:"error"
                        })
                    }
                })
        }
        }
    }
</script>

<style scoped>
    .elpage ,.eldialog{
        display: flex;
        justify-content: right;
    }
</style>