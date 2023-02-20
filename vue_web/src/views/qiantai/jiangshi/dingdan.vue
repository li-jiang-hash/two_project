<template>
    <div class="userList">
        <!--卡片-->
        <el-card class="box-card">


            <!--定义搜索-->
            <el-form :inline="true" :model="formData" class="demo-form-inline" @submit.native.prevent>
                <el-form-item size="mini">
                    <el-input v-model="formData.gname" placeholder="请输入商品名"
                              @keyup.enter.native="findUserBySouSuo"></el-input>
                </el-form-item>
                <el-form-item size="mini">
                    <el-select v-model="formData.send" placeholder="请选择发货状态">
                        <el-option
                                label="未发货"
                                :value="0">
                        </el-option>
                        <el-option
                                label="已发货"
                                :value="1">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button round type="primary" @click="findUserBySouSuo" size="mini" icon="el-icon-search"
                               style="margin-left: 10px;margin-right: 10px">搜索
                    </el-button>
                    <el-button type="primary" icon="el-icon-refresh" size="mini" circle
                               @click="handleReset"></el-button>
                </el-form-item>
            </el-form>

            <!--数据表格-->
            <el-table
                    :data="tableData"
                    border
                    style="width: 100%">
                <el-table-column type = "expand">
                    <template slot-scope="props">
                        <div v-for="item in props.row.order" >
                            <div style="font-size: 16px;font-family: 幼圆">
                                <span style="float: left;margin-right: 140px;margin-left: 43px">
                                    <img :src="item.img" width="150px" height="80px">
                                </span>
                                <span style="color:darkblue">商品名称:</span><span style="margin-right: 110px"></span>{{item.gname}}<br>
                                <span style="color:darkblue">购买数量:</span><span style="margin-right: 110px"></span>{{item.num}}{{item.unitname}}<br>
                                <span style="color:darkblue">商品单价:</span><span style="margin-right: 110px"></span><span style="color: red;">￥</span>{{item.price}}元<br>
                                <span style="color:darkblue">商品总价:</span><span style="margin-right: 110px"></span><span style="color: red;">￥</span>{{item.totalmoney}}元
                            </div>
                            <el-divider></el-divider>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column
                        :formatter="dateFormat"
                        prop="code"
                        label="订单编号"
                        align="center">
                </el-table-column>
                <el-table-column
                        :formatter="dateFormat"
                        prop="orderdate"
                        label="订单时间"
                        align="center">
                </el-table-column>
                <el-table-column
                        prop="totalmoney"
                        label="订单总价"
                        align="center">
                    <template slot-scope="scope">
                        ￥{{scope.row.totalmoney}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="addr"
                        label="发货地址"
                        align="center">
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        prop="send"
                        label="发货状态">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.send===1" class="el-icon-circle-close">已发货</el-tag>
                        <el-tag type="danger" v-if="scope.row.send===0" class="el-icon-refresh-left">未发货</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        header-align="center"
                        align="center"
                        label="操作">
                    <template slot-scope="scope">
                                    <el-button type="primary" size="mini" v-if="scope.row.send === 0" @click="sendConfirm(scope.row)">点我已发货</el-button>
                                </template>
                            </el-table-column>
            
            
                        </el-table>
                        <el-divider><i class="el-icon-moon-night"></i></el-divider>
                        <el-pagination
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page="currentPage"
                                style="float: right;margin-top: 20px; margin-bottom: 22px"
                                :page-sizes="[5, 10, 15, 20]"
                                :page-size="pageSize"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="allTotal">
                        </el-pagination>
                    </el-card>
            
            
                </div>
</template>

<script>
export default {
    name: "UserList",
    created() {
        this.initTable();
        this.getRole();
    },
    data() {
        return {
            rolenames: [],
            //控制权限的弹框显示
            visible: false,

            checkAll: false,    //全选
            checkedRoles: [],   //用户具有的权限
            roles: [],          //查询具有的所有权限
            allRoles: [],
            userId: "",          //用户id
            isIndeterminate: true,

            roleFormData: {  //权限搜索框的内容
                roleName: "",
            },

            //当前页面--》page
            roleCurrentPage: 1,
            //每页几条--》limit
            rolePageSize: 5,
            //总条数
            roleAllTotal: 0,


            idlist: [],

            //新增用户的表单元素判断
            addFormRules: {
                ename: [
                    { required: true, message: '用户名不能为空' },
                    { min: 2, max: 10, message: "账号的长度[2~10]", trigger: "blur" },
                ],
            },

            //控制修改框的是否显示
            updateUserDialog: false,
            //修改用户的表单数据
            updateUserFormData: {},
            //修改的表单校验规则
            updateUserFormRule: {},

            //当前页面--》page
            currentPage: 1,
            //每页几条--》limit
            pageSize: 5,
            //总条数
            allTotal: 0,
            tableFormName: '',
            tableData: [
                {
                    rid: "",
                }
            ],
            formData: {
                gname: "",
                send: null,
            },

            //控制添加弹出对话框是否显示
            dialogFormVisible: false,
            //添加按钮弹出层表单数据
            form: {
                ename: ''
            },

        }

    },
    methods: {

        //确认发货按钮
        sendConfirm(row) {
            console.log(row);
            this.$confirm('确认将发货状态更改为已发货吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$http.post("sys-order/o-order/updateSend/" + row.id + "/" + row.code).then(res => {
                        if (res.data.code===2000){
                            this.$message.success(res.data.msg);
                            this.initTable()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
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
            quxiao() {
                this.updateUserDialog = false
                this.updateUserFormData = {}
                this.initTable()
            },


            //获取角色
            getRole() {

                this.$http.post("/emp/role/selectRoleAll").then(result => {

                    this.rolenames = result.data.data
                    console.log(this.rolenames)
                })
            },


            //加载表格数据
            initTable() {
                var that = this;

            this.$http.post("/sys-order/o-order/selectAll/" + sessionStorage.getItem("bisid") + "/" + this.currentPage + "/" + this.pageSize, this.formData).then(function (resp) {
                console.log(resp);
                that.tableData = resp.data.data.records
                    ;

                that.allTotal = resp.data.data.total;
                })
            },

            //关闭时清空表单
            flushAll() {
                this.$refs.formLabel.resetFields();
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            // 刷新当前页面
            reload() {
                this.formData = {}
                this.initTable()
            },
            //搜索按钮
            findUserBySouSuo() {
                this.initTable();
            },

            //分页
            handleSizeChange(val) {
                this.pageSize = val;
                this.initTable();
            },
            //分页按钮被点击，发生改变时
            handleCurrentChange(val) {
                this.currentPage = val;
                this.initTable();
            },
        },


    }
</script>

<style>
    #maxdd {
        background-color: rgba(255, 255, 255, 0.5)
    }

    /*解决elementUI中表格格子不对齐的问题*/
    .el-table th.gutter {
        display: table-cell !important;
    }

    .el-form-item__content {
        line-height: 0px;
        position: relative;
        font-size: 14px;
    }

</style>
