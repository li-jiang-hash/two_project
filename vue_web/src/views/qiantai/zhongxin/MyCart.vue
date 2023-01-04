<template>
    <div>
        <!-- 网页端样式  -->
        <div v-if="!isMobile" class="cart-web">
            <!-- 导航栏
              左边logo 右边搜索
              内容 单栏购物车  -->
            <div class="cart-contain">
                <div class="cart-table-container">
                    <div class="cart-filter-bar">
                        <span class="switch-cart">购物车（全部{{ cartTotalCount }}）</span>
                        <!-- <div class="cart-sum" style="display: none">
                          <span class="pay-text">已选商品</span>
                          <span class="total-symbol">{{ cartTotalPrice }}</span>
                          <div class="pay-btn inactive-btn">结算</div>
                        </div> -->
                    </div>
                    <div class="cart-list-content">
                        <el-table
                                ref="cartTable"
                                :data="cartList"
                                v-loading="loading"
                                size="mini"
                                style="width: 95%; margin: 0 auto"
                                v-on:selection-change="handleSelectionChange"
                        >
                            <el-table-column
                                    header-align="center"
                                    align="center"
                                    type="selection"
                                    width="50"
                            ></el-table-column>
                            <el-table-column label="商品图片" width="180" header-align="center" align="center">
                                <template slot-scope="scope">
                                    <el-image
                                            :src="scope.row.img"
                                            style="width: 192px; height: 108px"
                                    >
                                        <div slot="placeholder" class="image-slot">
                                            Loading<span class="dot">...</span>
                                        </div>
                                        <div slot="error" class="image-slot">
                                            <el-image
                                                    style="width: 192px; height: 108px"
                                                    src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png"
                                            ></el-image>
                                        </div>
                                    </el-image>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    header-align="center"
                                    align="center"
                                    label="商品名称"
                                    prop="gname"
                            ></el-table-column>
                            <el-table-column header-align="center" align="center" label="单价" prop="price"></el-table-column>
                            <el-table-column header-align="center" align="center" label="数量" width="220">
                                <template slot-scope="scope">
                                     库存剩余:{{scope.row.residue}}
                                    <el-input-number
                                            setp="1"
                                            :min="1"
                                            :max="scope.row.residue"
                                            v-model="scope.row.num"
                                            plain
                                            v-on:change="calTotalPrice()"
                                    ></el-input-number>
                                </template>
                            </el-table-column>
                            <el-table-column label="金额" header-align="center" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.num * scope.row.price }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column header-align="center" align="center" label="操作" fixed="right" >
                                <template slot-scope="scope">
                                    <el-button
                                            size="medium"
                                            type="danger"
                                            v-on:click="delGood(scope.row.id)"
                                    >删除
                                    </el-button
                                    >
                                </template>
                            </el-table-column>
                        </el-table>

                        <div class="block">
                            <el-pagination
                                    style="float: right"
                                    @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange"
                                    :current-page="currentPage"
                                    :page-sizes="pageSizes"
                                    :page-size="pageSize"
                                    layout="total, sizes, prev, pager, next, jumper"
                                    :total="total">
                            </el-pagination>
                        </div>

                    </div>
                    <div class="cart-filter-bar-bottom">
                        <div class="bar-bottom-left">
              <span class="all-check">
                <div>
                  <el-checkbox
                          class="all-check-checkbox"
                          v-model="isAllChecked"
                          v-on:change="checkAll"
                  >全选</el-checkbox
                  >
                </div>
              </span>
                            <span class="all-del" v-show="isAllChecked">
                <el-button type="danger" size="small" v-on:click="delAll"
                >删除</el-button
                >
              </span>
                        </div>
                        <div class="cart-sum">
                            <span class="pay-text">已选商品</span>
                            <span class="total-text">合计：</span>
                            <span class="total-symbol">{{ cartTotalPrice }}</span>
                            <div v-if="selectionData.length > 0" class="pay-btn-active" @click="buyCourse">
                                结算
                            </div>
                            <div v-else class="pay-btn-inactive" >结算</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <el-dialog title="收银台" :visible.sync="dialogFormVisible" width="600px" @close="flush">
            <div data-v-1ee14d65="" style="margin: auto">
                <div style="float: right;margin-right: 40px;color: red;font-size: 20px;font-family: 幼圆;margin-top: -37px;">
                    总价:￥{{cartTotalPrice}}
                </div>
                <div data-v-1ee14d65="" class="remark" style="margin-top: 23px;padding-left: 75px">
                    <div style="margin-bottom: 20px;margin-left: -73px;border-radius: 10px;width: 535px;border: 1px solid darkgray;box-shadow: 10px 10px 5px rgba(0,0,0,0.9)" v-for="item in orderInfo">
                        <div >
                            <div style="float: left;margin-top: 10px;margin-left: 10px" class="block"><el-avatar shape="square" :size="35" :src="item[0].bicon"></el-avatar></div>
                        </div>
                        <div style="font-size: 17px;font-family: 幼圆;margin-top: 17px;margin-left: 61px;">
                            {{item[0].sname}}
                        </div>
                        <div style="margin-top: 20px" v-for="items in item">
                            <img style="margin-left: 52px;margin-top: 19px;" :src="items.img" width="96" height="54">
                            <div style="float: right;margin-top: 20px;">
                                <div style="margin-right: 252px;font-family: 幼圆;font-size: 17px">
                                    {{items.gname}}
                                </div>
                            </div>
                            <div style="margin-left: 418px;margin-top: -40px;margin-bottom: 20px;border: 2px solid grey;border-radius: 10px;width:42ppx;height: 26px;padding-top: 5px;padding-left: 4px;">
                                ×{{items.num}}
                            </div>
                            <el-divider style="width: 400px"></el-divider>
                        </div>
                    </div>
                    <el-form ref="form" :model="payForm" style="margin-left: -69px">
                        <label data-v-1ee14d65="" style="font-family: 幼圆;font-size: 15px">请选择收获地址:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                        <el-select size="small" v-model="payForm.addrid" placeholder="请选择收货地址"
                                    @change="aaaa">
                            <el-option
                                    v-for="item in addrForm"
                                    :label="item.addr"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                        <el-link type="primary"
                                 style="font-family: 幼圆;font-size: 15px;margin-left: 20px"
                                 href="http://localhost:8080/geRenCenter">点我添加新的地址
                        </el-link>
                    </el-form>
                </div>
                <el-button type="primary" style="margin-left: 210px;margin-top: 25px;" @click="choosePay">立即购买</el-button>


            </div>
            <!--                                </div>-->
        </el-dialog>


        <!--支付弹出层-->
        <el-dialog
                title="请选择支付类型"
                :visible.sync="payDialogVisible"
                width="50"
                @close="aboutpay">
            <!--支付宝和微信选择-->
            <div style="height: 520px;margin-top: 20px;margin-left: 100px">
                <el-button @click="alipay">
                    <img style="padding: 0" width="200" height="60"
                         src="data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABGAAD/4QMsaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzE0MCA3OS4xNjA0NTEsIDIwMTcvMDUvMDYtMDE6MDg6MjEgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozMjlEQTRGOTRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozMjlEQTRGQTRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjMyOURBNEY3NEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjMyOURBNEY4NEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQABAMDAwMDBAMDBAYEAwQGBwUE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">
                </el-button>
                <el-button @click="weixinpay">
                    <img width="200" height="60"
                         src="data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABGAAD/4QMsaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzE0MCA3OS4xNjA0NTEsIDIwMTcvMDUvMDYtMDE6MDg6MjEgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozMjlEQTRGRDRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozMjlEQTRGRTRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjMyOURBNEZCNEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjMyOURBNEZDNEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQABAMDAwMDBAMDBAYEAwQGBwUE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">
                </el-button>
                <span id="weixin"
                      style="display: inline-block;margin-left: 60px;border: 2px solid lightgrey">
                                    </span><br>
                <div style="margin-top: 25px;font-family: 幼圆;font-size: 15px">
                    请扫描下方二维码支付:<br>
                    (请在30分钟内完成支付,超时则订单无效)
                </div>
                <div style="border: 2px solid lightslategrey;width: 490px;height: 349px">
                    <vue-qr v-if="this.payStatus===0"
                            style="padding-left:10px;padding-left: 86px;width: 300px;margin-top: 27px; "
                            :text="payResult.codeUrl"
                            :margin="0"
                            colorDark="green"
                            :logoSrc="require('/src/assets/img/wx.jpg')"
                            colorLight="#fff"
                            :size="200">
                    </vue-qr>
                    <vue-qr
                            v-if="this.payStatus===1"
                            style="padding-left:10px;padding-left: 86px;width: 300px;margin-top: 27px; "
                            :text="payResultAli.codeUrl"
                            :margin="0"
                            colorDark="blue"
                            :logoSrc="require('/src/assets/img/ali.png')"
                            colorLight="#fff"
                            :size="200">
                    </vue-qr>
                </div>
            </div>
        </el-dialog>

        <el-dialog
                title="订单提示"
                :visible.sync="orderDialogVisible"
                width="30%">
                                <span style="font-family: 幼圆;font-size: 15px">
                                    当前支付尚未完成,若当前页面意外关闭,该订单将自动保存到<br>
                                    <span style="color:red;">"个人中心->我的订单"</span>中,可在我的订单中继续完成支付<br>
                                    确认要关闭当前订单吗?
                                </span>
            <span slot="footer" class="dialog-footer">
                                    <el-button @click="quxiaoOrder">取 消</el-button>
                                    <el-button type="primary" @click="closeOrder">确 定</el-button>
                                </span>
        </el-dialog>


    </div>
</template>

<script>
    import vueQr from 'vue-qr'
    export default {
        name: "MyCart",

        data() {
            return {
                orderDialogVisible: false,
                payStatus: null,
                jiesuan:{},
                //库存余量
                stockNum: 0,
                //当前用户所有的收获地址数据
                addrForm: [],
                payForm:{
                },
                dialogFormVisible: false,
                //总条数
                total: 0,
                //接受一个整型数组，数组元素为显示的选择每页显示的个数的选项
                pageSizes: [4, 6, 8, 10],
                //默认显示几条
                pageSize: 4,
                //当前显示的页面变量
                currentPage: 1,
                //选中数据
                selectionData: [],
                // 购物车列表
                cartList: [],
                // 加载
                loading: false,
                // 购物车总数
                cartTotalCount: 0,
                // 购物车总价格
                cartTotalPrice: 0.0,
                // 是否是移动端
                isMobile: false,
                // 是否全选
                isAllChecked: false,
                //订单编号
                code:"",
                //订单信息
                orderInfo:{},
                //支付弹出层
                payDialogVisible: false,
                //得到响应的结果
                payResult: {
                    codeUrl: "", //二维码的地址
                    code: "", //订单号
                    logoSrc: "/src/assets/img/美女.jpg" //微信二维码中间小logo
                },
                //得到响应的结果
                payResultAli: {
                    codeUrl: "", //二维码的地址
                    code: "", //订单号
                    logoSrc: "/src/assets/img/ali.png" //微信二维码中间小logo
                },
            }
        },
        created() {
            let _this = this;
            // 初始化数据
            _this.initData();
            //获取当前用户所有的地址
            this.getAddr();
        },
        mounted() {

            let _this = this;
            // 自适应
            _this.resizeLayout();
            window.onresize = function () {
                _this.resizeLayout();
            };
        },
        components: {
            vueQr
        },
        methods: {
            //选择器强制刷新
            aaaa(){
                this.$forceUpdate()
            },
            flush(){
              this.getList()
            },
            //取消关闭
            quxiaoOrder() {
                this.orderDialogVisible = false;
                this.payDialogVisible = true;
            },
            //关闭支付页面
            closeOrder() {
                //消除定时器
                clearInterval(this.timer);
                this.timer = null;
                this.orderDialogVisible = false;
                this.payDialogVisible = false;
                this.dialogFormVisible = false;
            },
            //支付状态
            queryPayStatus(code) {
                //继续支付
                var that = this;
                this.$http.get("order/payLog/paystatus/" + code).then(r => {
                    if (r.data.code === 2000) {
                        this.payResult.codeUrl = "",
                        this.payResultAli.codeUrl = "",
                        clearInterval(this.timer);
                        //如果支付成功，清除定时器
                        that.$message.success(r.data.msg);
                        //消除定时器
                        clearInterval(this.timer);
                        that.timer = null;
                        that.payDialogVisible = false;
                        that.dialogFormVisible = false;
                        //刷新界面
                        that.getList();
                    }
                })
            },
            //生成支付二维码
            weixinpay() {
                clearInterval(this.timer);
                this.timer = null;
                this.payStatus = 0;
                this.payForm.code = this.code;
                this.payForm.totalmoney = this.cartTotalPrice
                if (this.payResult.codeUrl !== '') {
                    //定时器
                    this.timer = setInterval(() => {
                        this.queryPayStatus(this.payResult.code);
                    }, 6000)
                    return
                }
                this.payForm.paysort = this.payStatus
                console.log(this.payForm)
                this.$http.post("order/order/buyCart", this.payForm).then(res => {
                    if (res.data.code === 2000) {
                        this.payResult.codeUrl = res.data.data.codeUrl;
                        this.payResult.code =this.code;
                        this.payResult.total_fee = this.cartTotalPrice;
                        //定时器
                        this.timer = setInterval(() => {
                            this.queryPayStatus(this.payResult.code);
                        }, 6000)
                    }else if (res.data.code===5001){
                        this.$message.error(res.data.msg)
                    } else {
                        this.$message.error("当前库存不充足,请更改数量后再尝试")
                        this.$http.post("order/cart/changeCart",this.selectionData).then(res=>{

                        })
                        this.getList();
                    }
                })
            },
            alipay() {
                clearInterval(this.timer);
                this.timer = null;
                this.payStatus = 1;
                this.payForm.code = this.code;
                this.payForm.totalmoney = this.cartTotalPrice
                if (this.payResultAli.codeUrl !== '') {
                    this.timer = setInterval(() => {
                        this.queryPayStatus(this.payResultAli.code);
                    }, 6000)
                    return;
                }
                this.payForm.paysort = this.payStatus
                this.$http.post("order/order/buyCart", this.payForm).then(res => {
                    if (res.data.code === 2000) {
                        this.payResultAli.codeUrl = res.data.data.codeUrl;
                        this.payResultAli.code = this.code
                        this.payResultAli.total_fee = this.cartTotalPrice;
                        this.timer = setInterval(() => {
                            this.queryPayStatus(this.payResultAli.code);
                        }, 6000)
                    }else if (res.data.code===5001){
                        this.$message.error(res.data.msg)
                    } else {
                        this.$message.error("当前库存不充足,请更改数量后再尝试")
                        this.$http.post("order/order/changeCart",this.selectionData).then(res=>{

                        })
                        this.getList()
                    }
                })
            },
            //关闭支付弹出层
            aboutpay() {
                if (this.payResult.codeUrl==="" && this.payResultAli.codeUrl==="" || this.payResult.codeUrl===null && this.payResultAli.codeUrl===null){

                }else {
                    this.orderDialogVisible = true;
                }
            },
            choosePay() {
                if (this.payForm.addrid != null && this.payForm.addrid!=="") {
                    this.payDialogVisible = true;
                } else {
                    this.$message.warning("请选择收货地址后再购买")
                }

            },
            //获取当前用户所有收获地址
            getAddr() {
                this.$http.post("commodity/goods/getAllAddr").then(res => {
                    if (res.data.code === 2000) {
                        this.addrForm = res.data.data;
                        console.log(this.addrForm)
                    }
                })
            },
            //购买事件
            buyCourse() {
                var item = sessionStorage.getItem("token");
                // alert(this.playStatus)
                if (item == null) {
                    this.$msgBox({
                        content: '请登录后再购买',
                        isShowCancelBtn: false
                    }).then(() => {
                        this.$router.push({
                            path: '/qianLogin'
                        }); //登录
                    })
                    return
                }
                console.log(this.selectionData)
                //生成订单
                this.$http.post("order/order/settlement",this.selectionData).then(res=>{
                    if (res.data.code===2000){
                        this.$message.success(res.data.msg)
                        this.code = res.data.data;
                    }
                })
                setTimeout(() => {
                    //根据订单号查询订单信息
                    this.$http.get("order/order/getOderInfo?code="+this.code).then(res=>{
                        if (res.data.code===2000){
                            console.log(res.data.data)
                            this.orderInfo = res.data.data
                            var info = this.orderInfo[0];
                            this.payForm.addrid = info[0].addrid

                        }
                    })
                }, 500);
                var totalMoney = 0;
                for (var i=0;i<this.selectionData.length;i++){
                    totalMoney = totalMoney+this.selectionData[i].num*this.selectionData[i].price;
                }
                this.jiesuan.totalmoney = totalMoney;
                this.dialogFormVisible = true;

            },
            //每页显示的条数大小改变时触发的方法
            handleSizeChange(val) {
                this.pageSize = val;
                this.getList()
            },
            //页码改变时触发的方法
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getList()
            },
            // 选择当前商品
            checkCurrent(item) {
                let _this = this;
                // 如果商品是被勾选的
                if (item.checked) {
                    // 从被选择数组中清除
                    _this.selectionData.forEach((sItem, sIndex) => {
                        if (sItem.id === item.id) {
                            _this.selectionData.splice(sIndex, 1);
                        }
                    });
                    // 勾选状态变为false
                    _this.cartList.forEach((cItem, cIndex) => {
                        if (cItem.id === item.id) {
                            _this.cartList[cIndex]["checked"] = false;
                        }
                    });
                } else {
                    // 如果商品是未被勾选的
                    // 勾选状态变为true
                    _this.cartList.forEach((cItem, cIndex) => {
                        if (cItem.id === item.id) {
                            _this.cartList[cIndex]["checked"] = true;
                        }
                    });
                    // 添加到被选择数组中
                    _this.selectionData.push(item);
                }
                // 如果被选择数组没有数据 则全选为false
                if (_this.selectionData.length <= 0) {
                    _this.isAllChecked = false;
                }
                // 如果被选择数组数组长度和购物车数组长度相同 则全选为true
                if (_this.selectionData.length === _this.cartList.length) {
                    _this.isAllChecked = true;
                }
                // 计算商品总价和总数量
                _this.calTotalPrice();
            },
            //响应式布局
            resizeLayout() {
                // 浏览器宽
                let width =
                    document.documentElement.offsetWidth || document.body.offsetWidth;
                let height =
                    document.documentElement.clientHeight || document.body.clientHeight;
                let _this = this;
                if (width > 700) {
                    _this.isMobile = false;
                } else {
                    _this.isMobile = true;
                }
            },

            // 初始化数据
            initData() {
                let _this = this;
                _this.getList();
            },

            // 获取购物车列表
            getList() {
                this.$http.get("order/cart/findCart/"+this.currentPage+"/"+this.pageSize).then(res=>{
                    if (res.data.code===2000){
                        this.total = res.data.data.total
                        this.cartList = res.data.data.records
                    }
                })
            },

            // 计算总价和总数量
            calTotalPrice() {
                let _this = this;
                _this.cartTotalPrice = 0;
                _this.cartTotalCount = 0;
                // 如果有选中项
                if (_this.selectionData.length > 0) {
                    _this.selectionData.forEach((item) => {
                        _this.cartTotalPrice += item.num * item.price;
                        _this.cartTotalCount += item.num;
                    });
                }
            },
            // 控制选择商品
            handleSelectionChange(data) {
                let _this = this;
                _this.selectionData = data;
                // 这里嵌套循环需要优化
                _this.cartList.forEach((good) => {
                    if (_this.selectionData.length > 0) {
                        // 如果在被选择数组中存在 则checked为true
                        _this.selectionData.forEach((selectedItem) => {
                            if (selectedItem.id === good.id) {
                                good.checked = true;
                            } else {
                                good.checked = false;
                            }
                        });
                    } else {
                        // 如果在被选择数组中没有数据则checked全设置为false
                        good.checked = false;
                    }
                });
                let cartTotalChecked = _this.selectionData.length;
                if (_this.cartList.length === cartTotalChecked) {
                    _this.isAllChecked = true;
                } else {
                    _this.isAllChecked = false;
                }
                _this.calTotalPrice();
            },
            // 	全部删除
            delAll() {
                let _this = this;
                _this
                    .$confirm("确定要删除全部商品吗?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                    .then(function () {
                        console.log(_this.selectionData)
                        var ids = [];
                        for (var i = 0;i<_this.selectionData.length;i++){
                            ids[i] = _this.selectionData[i].id
                        }
                        _this.$http.post("order/cart/deleteByIds",ids).then(res=>{
                            if (res.data.code===2000){
                                _this.$message.success(res.data.msg);
                                _this.getList();
                            }
                        })

                       // 这里只是前端删除
                       //  _this.cartList = [];
                       //  _this.isAllChecked = false;
                       //  //删除后需要重新获取数据刷新页面

                    })
                    .catch((e) => {
                        console.log(e);
                    });
            },
            // 单个删除
            delGood(id) {
                let _this = this;
                if (!id) {
                    _this.$message.error("商品有误，请刷新后重试");
                    return;
                }
                _this
                    .$confirm("确定要删除此商品吗?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                    .then(function () {
                        let goodIndex = 0;
                        _this.cartList.forEach((item, index) => {
                            if (item.id === id) {
                                goodIndex = index;
                            }
                        });
                        _this.cartList.splice(goodIndex, 1);
                        _this.$http.get("order/cart/deleteCart/"+id).then(res=>{
                            if (res.data.code===2000){
                                _this.$message.success(res.data.msg)
                            }
                        })
                        //   _this.getList();
                    })
                    .catch((e) => {
                        console.log(e);
                    });
            },
            // 全选
            checkAll() {
                let _this = this;
                // 如果全选
                if (_this.isAllChecked) {
                    _this.getList();
                    // 将购物车列表复制给被选择数组
                    _this.selectionData = JSON.parse(JSON.stringify(_this.cartList));
                    _this.cartList.forEach((cItem, cIndex) => {
                        _this.cartList[cIndex]["checked"] = true;
                    });
                    if (_this.$refs.cartTable) {
                        _this.$refs.cartTable.clearSelection();
                        // el table全部选中
                        _this.$refs.cartTable.toggleAllSelection();
                    }
                } else {
                    _this.cartList.forEach((cItem, cIndex) => {
                        _this.cartList[cIndex]["checked"] = false;
                    });
                    _this.selectionData = [];
                    if (_this.$refs.cartTable) {
                        // el table清空选中
                        _this.$refs.cartTable.clearSelection();
                    }
                }
                _this.calTotalPrice();
            },
        },
    };
</script>

<style lang="scss" rel="stylesheet/scss">

    .view_info {
        float: right;
        width: 570px;
        height: 295px;
        position: relative;

        p {
            font-size: 18px;
            color: #fff;
            word-break: break-all;
        }

        .foot_box {
            position: absolute;
            bottom: 0px;
            width: 100%;
            height: 36px;
        }

        .buy_btn {
            margin-right: 30px;
            width: 136px;
            height: 36px;
            color: #fff;
            background: #D51423;
            border: none;
            border-radius: 6px;
            line-height: 36px;
            text-align: center;
            font-size: 14px;
            // position: absolute;
            // bottom: 0px;
            &:hover {
                text-decoration: none;
                color: #fff;
                cursor: pointer;
            }
        }
    }
    /* 购物车web端 */
    .cart-web {
        width: 100%;
        height: auto;
        background: #e8e2e0;
    }

    /* 购物车移动端 */
    .cart-phone {
        width: 100%;
        height: calc(100vh - 135px);
        background: #e8e2e0;
    }

    /* 购物车头部 */
    .cart-header {
        width: 100%;
        height: 58px;
        padding: 11px 0;
        background-color: #fff;
        display: flex;
        justify-content: center;
    }

    .cart-header .cart-header-contain {
        width: 80%;
        display: flex;
        align-items: center;
    }

    /* 购物车标题 */
    .cart-header .cart-header-contain .cart-title {
        font-size: 18px;
        font-weight: 500;
    }

    .cart-contain {
        width: 100%;
        height: auto;
        display: flex;
        justify-content: center;
        align-items: center;
        padding-top: 24px;
        background-color: #f5f5f5;
    }

    .cart-table-container {
        width: 100%;
        border-radius: 25px;
        padding: 10px 30px;
        background: #fff;
        min-height: calc(100vh - 130px);
        position: relative;
        margin-bottom: 20px;
        margin-top: -25px;
    }

    .cart-filter-bar {
        width: 100%;
        display: flex;
        justify-content: space-between;
    }

    .cart-filter-bar .switch-cart {
        overflow: hidden;
        color: #000;
        font-size: 18px;
        font-weight: 600;
        height: 72px;
        line-height: 72px;
    }

    .cart-filter-bar .cart-sum {
        font-size: 14px;
        display: flex;
        align-items: center;
    }

    .cart-sum .total-symbol {
        font-weight: 400;
        margin-right: 12px;
        font-size: 24px;
        color: #ff5000;
    }

    /* 激活状态的支付按钮 */
    .cart-sum .pay-btn-active {
        width: 74px;
        height: 42px;
        line-height: 42px;
        color: #fff;
        background: #ff5000;
        border-radius: 21px;
        text-align: center;
        cursor: pointer;
        text-decoration: none;
        font-size: 16px;
    }

    /* 未激活状态的支付按钮 */
    .cart-sum .pay-btn-inactive {
        width: 74px;
        height: 42px;
        line-height: 42px;
        color: #fff;
        background: #a3a3a3;
        border-radius: 21px;
        text-align: center;
        cursor: not-allowed;
        text-decoration: none;
        font-size: 16px;
    }

    .inactive-btn {
        background: #aaa !important;
        color: #fff;
        cursor: not-allowed !important;
    }

    .cart-list-content {
        max-height: 100vh;
        padding-bottom: 72px;
        overflow-y: auto;
    }

    /* 购物车底部栏 */
    .cart-filter-bar-bottom {
        width: 95%;
        display: flex;
        justify-content: space-between;
        position: absolute;
        bottom: 0;
        background: #fff;
        z-index: 999;
    }

    .cart-filter-bar-bottom .bar-bottom-left {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    /* 全选 */
    .cart-filter-bar-bottom .all-check {
        overflow: hidden;
        color: #000;
        height: 72px;
        line-height: 72px;
        font-size: 12px;
    }

    .cart-filter-bar-bottom .all-check .all-check-checkbox {
        margin-right: 10px;
    }

    /* 全部删除 */
    .cart-filter-bar-bottom .all-del {
        margin-left: 20px;
    }

    .cart-filter-bar-bottom .cart-sum {
        font-size: 14px;
        display: flex;
        align-items: center;
    }

    .cart-sum .pay-text {
        margin: 0 12px;
    }

    .cart-sum .total-text {
        margin: 0 0 0 12px;
    }

    .cart-box {
        padding: 10px 0;
    }

    .cart-list-item {
        position: relative;
        border-radius: 8px;
        overflow: hidden;
        border: 0;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin: 20px;
        background-color: #fff;
        padding: 8px 0;
    }

    .cart-list-item .cart-item-info {
        display: flex;
        align-items: center;
        padding: 0 0 0 15px;
    }

    .cart-list-item .cart-item-left {
        width: 100px;
        display: flex;
        flex-direction: row;
        align-items: center;
    }

    .cart-list-item .left-check {
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 8%;
    }

    /* 单个选择按钮 */
    .cart-list-item .left-check .checkbox {
        width: 14px;
        height: 14px;
        border-radius: 50%;
        background: #fff;
        border: 1px solid #b9b9b9;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    /* 单个选择按钮选中 */
    .cart-list-item .left-check .checkbox .selected-check {
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background: red;
    }

    .cart-item-right {
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        padding: 10px;
    }

    .cart-item-opera {
        display: flex;
        align-items: center;
    }

    /* 删除按钮 */
    .del-btn {
        position: absolute;
        right: 10px;
        bottom: 10px;
        font-size: 17px;
        color: red;
    }

    .cart-item-right .cart-item-info-content {
        margin-right: 20px;
        width: 70px;
    }

    .cart-item-right .goods-name {
        width: 100%;
        font-size: 15px;
        color: #333333;
        margin-top: 3px;
        text-align: left;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .cart-item-right .numbers {
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        margin-top: 10px;
    }

    .cart-item-right .numbers .price {
        font-size: 14px;
        line-height: 25px;
        color: #ff0000;
    }

    .bottom-all {
        height: 50px;
        width: 100%;
        padding: 0 2%;
        align-items: center;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        position: fixed;
        bottom: 0;
        left: 0;
        background-color: #fdfdfd;
        z-index: 11;
        border-top: 1px solid #f8f8f8;
    }

    .bottom-all .left {
        float: left;
        width: 28vw;
        font-size: 14px;
        position: relative;
    }

    .bottom-all .left .text {
        position: absolute;
        left: 35px;
        top: 50%;
        transform: translateY(-52%);
        color: #333333;
    }

    .bottom-all .del-text {
        color: red;
        position: absolute;
        left: 80px;
        top: 47%;
        transform: translateY(-52%);
    }

    .bottom-all .left-check {
        padding-left: 8px;
        flex-shrink: 0;
        height: 50px;
        margin-right: 8px;
        align-items: center;
        position: relative;
        display: flex;
    }

    .bottom-all .rights {
        width: 40vw;
        line-height: 50px;
        font-size: 16px;
        text-align: center;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    .bottom-all .settlement-inactive {
        padding: 0 4px;
        border-radius: 16px;
        width: 18vw;
        height: 35px;
        line-height: 35px;
        color: #ffffff;
        font-size: 17px;
        text-align: center;
        margin-right: 20px;
        cursor: not-allowed;
        background-color: #a3a3a3;
    }

    .bottom-all .settlement-active {
        padding: 0 4px;
        border-radius: 16px;
        width: 18vw;
        height: 35px;
        line-height: 35px;
        color: #ffffff;
        font-size: 17px;
        text-align: center;
        margin-right: 20px;
        background-color: #ff5000;
    }

    /* 购物车为空的样式 */
    .cart-no-data {
        text-align: center;
    }

    .total-pay-count {
        font-size: 24px;
        color: #ff5000;
    }

    @media (max-width: 700px) {
        /* 弹窗 */
        .el-message-box {
            width: 300px !important;
        }

        /* el-input-number样式 */
        .el-input-number {
            width: 100px !important;
            line-height: 28px !important;
        }

        .el-input-number .el-input__inner {
            padding-left: 0 !important;
            padding-right: 0 !important;
        }

        .el-input__inner {
            height: 30px !important;
        }

        .el-input-number__decrease,
        .el-input-number__increase {
            width: 20px !important;
        }
    }
</style>
