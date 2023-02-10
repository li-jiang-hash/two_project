<template>
    <el-dialog
            width="60%"
            :title="title"
            :visible.sync="visible"
            :before-close="handleClose">
    <div class="pad20">
        <div class="filter-container">
            <!--查询的表单数据-->
            <el-form :inline="true" :model="map" size="mini">
                <el-form-item label="店铺名称:">
                    <el-input v-model.trim="map.sname"></el-input>
                </el-form-item>
                <el-form-item label="上下架:">
                    <el-select v-model="map.putaway" class="auto-width" clearable filterable placeholder="上下架" style="width: 85px">
                        <el-option
                                v-for="item in opts"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button icon='el-icon-search' type="primary" @click="handleCheck">查询</el-button>
                    <el-button icon='el-icon-refresh' class="filter-item" @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
        <!--数据表格-->
        <el-table  size="medium" :data="list" stripe border>
            <el-table-column type="index" label="序号" width="50">
            </el-table-column>
            <el-table-column prop="id" v-if="false" label="店铺id" width="50">
            </el-table-column>
            <!--<el-table-column prop="cover" label="店铺名称" width="122">
                &lt;!&ndash;<template slot-scope="scope">
                    <img :src="scope.row.cover" :alt="scope.row.title" height="48.75" width="100">
                </template>&ndash;&gt;
            </el-table-column>-->
            <el-table-column prop="sname" label="店铺名称" width="180">
            </el-table-column>>
            <el-table-column label="店铺分类" align="center" prop="sortname" width="150">
            </el-table-column>
            <el-table-column label="最低价格" prop="gprice" width="150">
                <!--<template slot-scope="scope">
                    <span v-if="scope.row.price !== 0">原价：{{parseFloat(scope.row.price).toFixed(2)}}<br>优惠价：{{(scope.row.price*0.8).toFixed(2)}}</span>
                    <span v-else>免费</span>
                </template>-->
            </el-table-column>
            <el-table-column
                    width="150"
                    prop="putaway"
                    label="上下架状态"
                    align="center">
                <template slot-scope="scope">
                    <el-tag
                            v-if="scope.row.putaway=='0'"
                            type=""
                            size="medium"
                            effect="dark">
                        上架
                    </el-tag>
                    <el-tag
                            v-else
                            type="danger"
                            effect="dark">
                        下架
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="100">
                <template slot-scope="scope">
                    <ul class="list-item-actions">
                        <li v-if="scope.row.putaway == 1">
                            <el-button type="success" @click="check(scope.row.id)" size="mini">选择</el-button>
                        </li>
                        <li v-if="scope.row.putaway == 0">
                            <el-link disabled>已添加</el-link>
                        </li>
                    </ul>
                </template>
            </el-table-column>
        </el-table>
        <div>
            <el-pagination
                    background
                    style="float: right;margin-top: 20px; margin-bottom: 22px"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :page-size="pageSize"
                    :page-sizes="[5, 10, 15, 20, 30]"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <br>
        <br>
    </div>
    </el-dialog>
</template>

<script>
import qs from "qs";
    export default {
        name: "add",
        data() {
            return {
                editor: {},
                dialogVisible: false,
                editVisible: false,
                map: {
                    id:"",
                    sname:"",
                    putaway:""
                },
                formData: {},
                list: [],
                opts: [
                        {
                            value: '0',
                            label:"上架"
                        },{
                            value: '1',
                            label:"下架"
                        }
                    ],
                beginPageIndex: 1,
                currentPage: 1,
                endPageIndex: 8,
                //pageCurrent: 1,
                pageSize: 5,
                total: 0,
                totalPage: 0
            }
        },
        props: {
            id: {
                type: String,
                default: ''
            },
            visible: {
                type: Boolean,
                default: false
            },
            title: {
                type: String,
                default: ''
            },
        },
        watch: {
            visible: function(val) {
                if (val) {
                    this.map.id=this.id;
                    this.searchBusiness()
                }
            }
        },
        methods: {
            handleClose() {
                this.$emit('close-callback')
            },
            // 商店分类分页列表接口
            searchBusiness(){
                this.map.id=this.id;
                this.$http.post("/syssystem/tb-zone-business/managementZone?currentPage="+this.currentPage +"&pageSize=" + this.pageSize,qs.stringify(this.map)).then(resp => {
                    if (resp.data.code===2000){
                        //console.log(resp.data)
                        this.list = resp.data.data.records
                        this.total = resp.data.data.total
                    }
                })
            },
            //查询
            handleCheck() {
                this.searchBusiness()
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            handleSizeChange(val) {
                this.pageSize = val
                this.searchBusiness()
            },
            handleCurrentChange(val) {
                this.currentPage = val
                this.searchBusiness()
            },
            // 刷新当前页面
            reload() {
                this.map = {}
                this.formData = {}
                this.searchBusiness()
            },
            // 关闭编辑弹窗回调
            closeCllback() {
                this.visible = false;
                this.$refs.formDataRef.resetFields();
                this.reload()
            },
            // 修改弹窗
            handleEdit(row) {

            },
            textClass(isFree) {
                return {
                    c_red: isFree === 0,
                    c_blue: isFree === 1
                }
            },
            check(id){
                this.$http.get(`home/zoneBusiness/addBusinessDetail/${id}`).then(resp => {
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
                    this.handleClose()
                })
            }
        }

    }
</script>

<style scoped>
    .cancel {
        text-align: right;
    }
    .button {
        padding: 5px 10px;
    }
    .el-dialog__body {
        height: 300px;
        overflow: auto;
    }
</style>
