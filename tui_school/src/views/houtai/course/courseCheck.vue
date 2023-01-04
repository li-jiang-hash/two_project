<template>
    <div class="pad20">
        <div class="filter-container">
            <!--查询的表单数据-->
            <el-form :inline="true" :model="map" size="mini">
                <el-form-item label="商品名称:">
                    <el-input v-model.trim="map.goodsname"></el-input>
                </el-form-item>
                <el-form-item label="店铺名称:">
                    <el-select v-model="map.businessid" filterable placeholder="请选择店铺名称">
                        <el-option
                                v-for="item in shops"
                                :label="item.sname"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商品类型:">
                    <el-select v-model="map.sortid" filterable placeholder="请选择商品类型">
                        <el-option
                                v-for="item in sorts"
                                :label="item.sortname"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="审核状态:">
                    <el-select v-model="map.state" filterable placeholder="请选择审核状态">
                        <el-option
                                label="待审核"
                                value="2">
                        </el-option>
                        <el-option
                                label="审核通过"
                                value="0">
                        </el-option>
                        <el-option
                                label="审核驳回"
                                value="1">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button icon='el-icon-search' type="primary" @click="handleCheck">查询</el-button>
                    <el-button icon='el-icon-refresh' class="filter-item" @click="handleReset">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
<!--        &lt;!&ndash;修改的弹出框&ndash;&gt;-->
<!--        <el-dialog-->
<!--                title="修改"-->
<!--                :visible.sync="visible"-->
<!--                @close="closeCllback"-->
<!--                width="70%">-->
<!--            <el-form ref="formDataRef" :model="formData" label-width="100px">-->
<!--                <el-form-item label="课程名称：">-->
<!--                    <el-input v-model="formData.title"></el-input>-->
<!--                </el-form-item>-->
<!--                <el-form-item label="是否免费：">-->
<!--                    <el-radio-group v-model="formData.price" v-if="formData.price!=0">-->
<!--                        <el-radio :label="formData.price">收费</el-radio>-->
<!--                        <el-radio :label="0.00">免费</el-radio>-->
<!--                    </el-radio-group>-->
<!--                    <el-radio-group v-model="formData.price" v-if="formData.price==0">-->
<!--                        <el-radio :label="1">收费</el-radio>-->
<!--                        <el-radio :label="0.00">免费</el-radio>-->
<!--                    </el-radio-group>-->
<!--                </el-form-item>-->

<!--                <el-row v-if="formData.price != 0">-->
<!--                    <el-col :span="12">-->
<!--                        <el-form-item label="原价：">-->
<!--                            <el-input type="text" style="width: 120px" placeholder="请输入价格" v-model="formData.price"></el-input> 元-->
<!--                        </el-form-item>-->
<!--                    </el-col>-->
<!--                </el-row>-->
<!--                <el-form-item label="课程简介:">-->
<!--                    &lt;!&ndash;<el-input-->
<!--                            type="textarea"-->
<!--                            autosize-->
<!--                            placeholder="请输入内容"-->
<!--                            v-model="formData.remark">-->
<!--                    </el-input>&ndash;&gt;-->
<!--                    <quill-editor class="editor"-->
<!--                                  v-model="formData.description"-->
<!--                                  ref="myQuillEditor"-->
<!--                                  :options="editorOption">-->
<!--                    </quill-editor>-->
<!--                </el-form-item>-->
<!--                <el-row style="margin-top:17px; ">-->
<!--                    <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="visible = false">取 消</el-button>-->
<!--                    <el-button style="float:right" size="mini" type="primary" @click="submitForm">确定</el-button>-->
<!--                </el-row>-->
<!--            </el-form>-->
<!--        </el-dialog>-->
        <!--审核的弹出框-->
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
                    <el-input type="textarea" :rows="3" placeholder="系统默认通过" v-model="checkformData.checkContent"></el-input>
                </el-form-item>
            </el-form>
            <el-row style="margin-top:17px; ">
                <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="resetCheck">取 消</el-button>
                <el-button style="float:right" size="mini" type="primary" @click="submitCheckForm">确定</el-button>
            </el-row>
        </el-dialog>
        <!--数据表格-->
        <el-table  size="medium" :data="list" stripe border>
            <el-table-column prop="id" label="商品编号" width="100" align="center" fixed>
            </el-table-column>
            <el-table-column prop="businessInfo.sname" label="所属店铺" width="100" align="center" >
            </el-table-column>
            <el-table-column prop="img" label="商品图片" width="122" fixed header-align="center" align="center">
                <template slot-scope="scope">
                    <img :src="scope.row.img"  width="100" height="60">
                </template>
            </el-table-column>
            <el-table-column prop="gname" label="商品名称" width="100" align="center" fixed>
            </el-table-column>
            <el-table-column prop="remark" label="商品描述" width="180" header-align="center" align="center">
            </el-table-column>
            <el-table-column prop="price" label="价格" width="150"  header-align="center" align="center">
                <template slot-scope="scope"  >
                    <span v-if="scope.row.price !== 0">
                        {{parseFloat(scope.row.price).toFixed(2)}}元
                    </span>
                </template>
            </el-table-column>
            <el-table-column prop="brand.brandname" label="商品品牌" width="80">
            </el-table-column>
            <el-table-column prop="sort.sortname" label="商品类别" width="80">
            </el-table-column>
            <el-table-column prop="deposit.depositname" label="储藏方式" width="80">
            </el-table-column>
            <el-table-column prop="unit.unitname" label="商品单位" width="80">
            </el-table-column>
            <el-table-column prop="addtime" label="上架时间" width="180">
            </el-table-column>
            <el-table-column prop="expirationtime" label="保质期" width="180">
            </el-table-column>
            <el-table-column prop="checkContent" label="审查备注" width="180">
            </el-table-column>
            <el-table-column
                    label="审核状态"
                    prop="state"
                    width="80"
                    align="center"
                    fixed="right">
                <template slot-scope="scope">
                    <span v-if="scope.row.state === 0">审核通过</span>
                    <span v-if="scope.row.state === 1">审核驳回</span>
                    <span v-if="scope.row.state === 2">待审核</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" header-align="center" fixed="right" width="300px" align="center" prop="status">
                <template slot-scope="scope">
                    <!--<el-button  type="success" @click="handleEdit(scope.row)" size="mini">修改</el-button>-->
                    <el-button  type="primary" @click="handleCheckCourse(scope.row.id)" size="mini" v-if="scope.row.state===2">去审核</el-button>
                    <el-button  type="info" size="mini" v-if="scope.row.state!==2">已审核</el-button>

                    <!--<el-switch
                            style="margin-left: 20px"
                            v-model="scope.row.status"
                            @change="handleChangeIsPutaway(scope.row.id,scope.row.status)"
                            :active-value="1"
                            :inactive-value="0"
                            active-color="#ff4949"
                            inactive-color="#13ce66"
                            active-text="下架"
                            inactive-text="上架">
                    </el-switch>-->
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                background
                style="float: right;margin-top: 20px; margin-bottom: 22px"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :page-size="page.pageSize"
                :page-sizes="[2,5, 8, 10, 15, 20, 30]"
                layout="total, sizes, prev, pager, next, jumper"
                :total="page.totalCount">
        </el-pagination>
    </div>
</template>

<script>
    export default {
        name: "courseCheck",
        data() {
            return {
                //所有商品类型信息
                sorts:{},
                //所有店铺信息
                shops:{},
                editorOption:{},
                editor: {},
                dialogVisible: false,
                editVisible: false,
                visible: false,
                checkvisible:false,
                map: {},
                formData: {},
                checkformData:{},
                list: [],
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
            this.searchCourse()
            this.initShops()
            this.goodsSort()
        },
        methods: {
            goodsSort(){
               this.$http.get("commodity/sort/findAllSort").then(res=>{
                   if (res.data.code===2000){
                       this.sorts = res.data.data;
                   }
               })
            },
            initShops(){
              this.$http.get("/business/selectshop").then(res=>{
                  if (res.data.code===2000){
                      console.log(res.data.data)
                      this.shops = res.data.data;
                  }
              })
            },
            // 商品分类分页列表接口
            searchCourse(){
                var that =this
                this.$http.post(`/commodity/goods/findAllGoods/${this.page.pageCurrent}/${this.page.pageSize}`,this.map).then(function (resp) {
                    if (resp.data.code===2000){
                        that.list = resp.data.data.records
                        that.page.totalCount = resp.data.data.total
                        that.page.pageSize = resp.data.data.size
                    }
                })
            },
            //提交修改的表单
            submitForm(){
                var that = this
                this.$http.post(`/core/course/updateCourse`,this.formData).then(function (resp) {
                    if (resp.data.code==200){
                        that.$message.success(resp.data.msg);
                        that.visible = false
                    }else {
                        that.$message.error(resp.data.msg);
                        that.visible = false
                    }
                })
            },
            //提交审核的表单
            submitCheckForm(){
                /*if (this.checkformData.checkContent === ""){
                    this.checkformData.checkContent  = "系统默认通过";
                }*/
                var that = this
                this.$http.post(`/commodity/goods/updateState`,this.checkformData).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                        that.checkvisible = false
                        that.searchCourse()
                    }else {
                        that.$message.error(resp.data.msg);
                        that.checkvisible = false
                        that.searchCourse()
                    }
                })
            },
            //审核取消后清空
            resetCheck(){
                this.checkvisible = false
                this.checkformData.checkContent = ""
                this.reload()
            },
            //查询
            handleCheck() {
                this.searchCourse()
            },
            // 重置查询条件
            handleReset() {
                this.reload()
            },
            handleSizeChange(val) {
                this.page.pageSize = val
                this.searchCourse()
            },
            handleCurrentChange(val) {
                this.page.pageCurrent = val
                this.searchCourse()
            },
            // 刷新当前页面
            reload() {
                this.map = {}
                this.formData = {}
                this.searchCourse()
            },
            // 关闭编辑弹窗回调
            closeCllback() {
                this.visible = false;
                this.$refs.formDataRef.resetFields();
                this.$router.go(0)
            },
            //关闭审核弹框回调
            closeCllback1(){
                this.checkvisible = false;
                this.$refs.checkformDataRef.resetFields();
                this.reload()
            },
            // 修改上下架状态
            handleChangeIsPutaway(id, status) {
                const title = {"1": '下架', "0": '上架'}
                this.$confirm(`确定要${title[status]}吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.changeIsPutaway(id, status)
                }).catch(() => {
                    this.reload()
                })
            }
            ,
            // 请求更新上下架方法
            changeIsPutaway(id, status) {
                var that =this
                this.$http.post(`/commodity/goods/updateStatus`, {"id":id,"status":status}).then(function (resp) {
                    if (resp.data.code===2000){
                        that.$message.success(resp.data.msg);
                    }
                    that.reload()
                })
            },
            // 修改弹窗
            // handleEdit(row) {
            //     this.formData = row
            //     this.visible = true
            // },
            //审核课程
            handleCheckCourse(id,state){
                this.checkformData.id = id
                this.checkvisible = true
            },
        }

    }
</script>

<style scoped>

</style>
