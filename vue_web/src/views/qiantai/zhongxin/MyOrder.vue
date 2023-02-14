<template>
	<div class="myOrder">
		<ul class="tabs clearfix">
			<a class="tab" :class="{on: num === 0}" @click="clicktab(0)">所有订单</a>
			<a class="tab" :class="{on: num === 1}" @click="clicktab(1)">待支付订单</a>
			<a class="tab" :class="{on: num === 2}" @click="clicktab(2)">已完成订单</a>
			<a class="tab" :class="{on: num === 5}" @click="clicktab(5)">未评价订单</a>
			<a class="tab" :class="{on: num === 3}" @click="clicktab(3)">已失效订单</a>
			<a class="tab" :class="{on: num === 4}" @click="clicktab(4)">已取消订单</a>
		</ul>
		<div class="notdata" v-if="notdata">
			<i class="iconfont">&#xe6be;</i>暂时没有数据
		</div>
		<div class="person_info" v-if="!notdata">
			<div class="order_content" v-for="(item, index) in pageObj.order" :key="index">
				<div class="order_title clearfix">
					<span class="order_num">订单号：{{item.code}}</span>
					<span class="time">下单时间：{{item.orderdate}}</span>
					<span id="sname"><i class="el-icon-s-shop"></i> {{item.sname}}</span>
					<el-link style="float: right;margin-right: 20px;color: #ec7e00" @click="delOrder(item.code)">
						<i class="el-icon-delete">删除订单</i>
					</el-link>
					<i class="money" style="float:right;margin-right: 30px;color: red">总计:￥{{item.totalmoney}}</i>
				</div>
				<ul class="body_right clearfix" style="margin-top: 10px">
					<li v-if="item.paysort === 0 && item.state===0">微信支付</li>
					<li v-if="item.paysort === 1 && item.state===0">支付宝支付</li>
					<li class="alipay" v-if="item.state === 1" v-loading="loading" element-loading-background="white">
						等待支付,剩余<br>
						<span style="color: red">
							{{ timeFormat(item.lastTime,item.code,index) }}
						</span>
					</li>
					<li class="alipay" v-if="item.state === 0">已支付</li>
					<li class="alipay" style="color: #d51423" v-if="item.state === 3">订单已失效</li>
					<li class="alipay" v-if="item.state === 4" style="color: #ec7e00">订单已取消</li>

					<li v-if="item.state === 1 ">
						<a class="go_btn go_pay" @click="choosePaySort(item.code)">
							继续支付
						</a>
						<a @click="quxiao(item.id)" class="cancel" style="cursor: pointer">取消订单</a>
					</li>
				</ul>
				<div class="order_body clearfix">
					<div class="body_left clearfix" v-for="(items,index) in item.goodsList" :key="index">
						<div v-if="items.img" style="height: 100px">
							<img :src="items.img" alt=""
								style="width: 192px;height: 108px;display: inline-block;float: left">
							<div style="margin-top: 0px;margin-left: 220px">
								<div style="color: brown;font-size: 16px;font-family: 幼圆">
									商品名称:{{items.gname}}
								</div>
								<div id="goumaishuliang">
									购买数量:{{item.num}}{{items.unitname}}
								</div>
							</div>
						</div>
						<router-link v-if="item.commentstatus===0 && item.state===0"
							:to="{name: 'DetailId',params:{id:items.goodid}}">
							<div style="float:right;margin-left: 356px;margin-top: -116px;">
								<el-button type="info" plain style="margin-right: 39px;">点我去评论</el-button>
							</div>
						</router-link>
						<div>
							<el-divider id="xian"></el-divider>
						</div>
					</div>

				</div>
			</div>
			<!--微信支付-->
			<el-dialog @close="closeWx" title="收银台" :visible.sync="dialogVisible" width="30%">
				<div style="text-align: center">
					<p>微信支付 {{payResult.price}}元</p>
					<div style="border: 1px solid #f3f3f3;width: 220px;padding: 10px;margin: 0px auto">
						<vue-qr :text="payResult.codeUrl" :margin="0" colorDark="green"
							:logoSrc="require('/src/assets/img/wx.jpg')" colorLight="#fff" :size="200">
						</vue-qr>
					</div>
				</div>
				<el-divider></el-divider>
				<div style="font-size: 13px">
					提示:<br>
					支付成功前请勿手动关闭页面<br>
					二维码两小时内有效,请及时扫码支付<br>
				</div>

			</el-dialog>
			<!--支付宝支付弹出层-->
			<el-dialog @close="closeAli" title="支付页面" :visible.sync="dialogVisible2" width="30%">
				<div style="text-align: center">
					<p>支付宝支付{{payResult.price}}元</p>
				</div>
				<div style="border: 1px solid #f3f3f3;width: 220px;padding: 10px;margin:0px auto;">
					<vue-qr style="padding-left:10px " :text="payResult.codeUrl" :margin="0" colorDark="blue"
						:logoSrc="require('/src/assets/img/ali.png')" colorLight="#fff" :size="200">
					</vue-qr>
				</div>
				<el-divider></el-divider>
				<div style="font-size: 13px">
					提示:<br>
					支付成功前请勿手动关闭页面<br>
					二维码两小时内有效，请及时扫码支付<br>
				</div>
			</el-dialog>
			<!--选择支付类型的弹出层-->
			<el-dialog title="提示" :visible.sync="dialogVisible3" width="40%" @close="clearCode">
				<span>
					<el-button @click="payByAli">
						<img style="padding: 0" width="200" height="60"
							src="data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABGAAD/4QMsaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzE0MCA3OS4xNjA0NTEsIDIwMTcvMDUvMDYtMDE6MDg6MjEgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozMjlEQTRGOTRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozMjlEQTRGQTRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjMyOURBNEY3NEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjMyOURBNEY4NEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQABAMDAwMDBAMDBAYEAwQGBwUE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">
					</el-button>
					<el-button @click="payByWx">
						<img width="200" height="60"
							src="data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAABGAAD/4QMsaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzE0MCA3OS4xNjA0NTEsIDIwMTcvMDUvMDYtMDE6MDg6MjEgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAoTWFjaW50b3NoKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDozMjlEQTRGRDRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDozMjlEQTRGRTRBODgxMUU4OEJBREIyOUE3Mjg5MjBCRCI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjMyOURBNEZCNEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjMyOURBNEZDNEE4ODExRTg4QkFEQjI5QTcyODkyMEJEIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQABAMDAwMDBAMDBAYEAwQGBwUE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">
					</el-button>
					<!--<el-button type="success" @click="payByWx">微信支付</el-button>
                    <el-button type="primary" @click="payByAli">支付宝支付</el-button>-->
				</span>
			</el-dialog>

			<d-page v-if="pageObj.totalPage > 1 && !notdata" :page="pageObj" @btnClick="getPage"></d-page>
		</div>
	</div>
</template>

<script>
	import YSide from '@/components/account/Side'
	import DPage from '@/components/Page'
	import vueQr from 'vue-qr'

	export default {
		name: "MyOrder",

		data() {
			return {
				//订单编号
				code: 0,
				//选择支付类型的弹出层
				dialogVisible3: false,
				//支付宝支付弹出层
				dialogVisible2: false,
				//查询支付状态定时器
				timer: "",
				//加载
				loading: false,
				num: 0,
				showPay: false,
				payData: null,
				notdata: false,
				zhuangtai: "",
				//用户id
				HuiYuanId: window.sessionStorage.getItem("memberId"),
				timer1: '',
				dialogVisible: false, //弹出框
				//得到响应的结果
				payResult: {
					codeUrl: "", //二维码的地址
					code: "", //订单号
					price: 0.00, //支付的金额
					logoSrc: "/src/assets/img/wx.jpg" //微信二维码中间小logo
				},
				pageObj: {
					//当前页面--》page
					pageCurrent: '1',
					//每页条数--》limit
					pageSize: '5',
					//总条数
					totalCount: '',
					//total的页数（有几页）【totalCount/pageSize】
					totalPage: '',
					order: [{
						//倒计时时间
						lastTime: 0,
						id: '',
						code: '',
						orderdate: '',
						goodsList: [],
						businessInfo: [],
						totalmoney: '',
						paysort: '',
						state: '',
						time: '',
					}, ]
				},
				order_no: this.$route.query.out_trade_no,
			}
		},
		methods: {
			//微信支付
			payByWx() {
				var that = this;
				var code = this.code;
				this.$http.post("/order/order/payForOrder/0/" + this.code).then(function(resp) {
					if (resp.data.code === 2000) {
						that.dialogVisible = true
						that.payResult = resp.data.data;
						that.payResult.code = code;
						//定时器
						that.timer = setInterval(() => {
							that.queryPayStatus();
						}, 6000)
					} else if (resp.data.code === 5001) {
						that.$message.error(resp.data.msg)
					}
					if (resp.data.code === 7000) {
						that.$message.error(resp.data.msg);
						that.dialogVisible3 = false
						that.initOrderList();

					}

				})
			},
			//支付宝
			payByAli() {
				var that = this;
				var code = this.code;
				this.$http.post("/order/order/payForOrder/1/" + this.code).then(function(resp) {
					if (resp.data.code === 2000) {
						//显示支付宝二维码
						that.dialogVisible2 = true
						that.payResult = resp.data.data;
						that.payResult.code = code;
						//定时器
						that.timer = setInterval(() => {
							that.queryPayStatus();
						}, 6000)
					} else if (resp.data.code === 5001) {
						that.$message.error(resp.data.msg)
					}
					if (resp.data.code === 7000) {
						that.$message.error(resp.data.msg);
						that.dialogVisible3 = false
						that.initOrderList();
					}

				})
			},
			//关闭选择支付类型弹出层清除订单编号
			clearCode() {
				this.code = 0;
			},
			//选择支付类型的方法
			choosePaySort(code) {
				this.dialogVisible3 = true;
				this.code = code;

			},
			//关闭微信支付页面
			closeWx() {
				//消除定时器
				clearInterval(this.timer);
				this.timer = null;
			},
			closeAli() {
				//消除定时器
				clearInterval(this.timer);
				this.timer = null;
			},
			//倒计时
			daojishi(i, obj) {
				var that = this
				// 计算剩余时间戳
				var paytime = new Date(obj.time);
				paytime.setMinutes(paytime.getMinutes() + 30)
				let daojishi = setInterval(() => {
					let residue = paytime - new Date();
					if (residue >= 0) {
						// 对每行的剩余时间进行设置
						obj.lastTime = residue;
					} else {
						obj.lastTime = -1;
						// 清除定时器
						clearInterval(daojishi);
					}
				}, 1000);
			},
			//剩余时间格式化
			timeFormat(lastTime, code, index) {
				console.log(lastTime)
				if (lastTime > 1000) {
					let days = this.addZero(
						Math.floor(lastTime / 1000 / 60 / 60 / 24)
					); //天
					let hours = this.addZero(
						Math.floor((lastTime / 1000 / 60 / 60) % 24)
					); //时
					let minutes = this.addZero(
						Math.floor((lastTime / 1000 / 60) % 60)
					); //分
					let seconds = this.addZero(Math.floor((lastTime / 1000) % 60)); //秒
					return `${minutes}分钟${seconds}秒`;
				} else if (lastTime < 1000 && lastTime > 0) {
					setTimeout(() => {
						this.$http.post("order/order/updateStateToFailure?code=" + code).then(res => {
							if (res.data.code === 2000) {
								this.pageObj.order[index].state = 3
								that.pageObj.pageCurrent = 1;
								that.initOrderList();
							}
						})
						return "已超时";
					}, 1000);
					return "00分钟01秒";
				} else if (lastTime === -1) {
					this.$http.post("order/order/updateStateToFailure?code=" + code).then(res => {
						if (res.data.code === 2000) {
							this.pageObj.order[index].state = 3
							this.pageObj.pageCurrent = 1;
							this.initOrderList();
						}
					})
				}
			},
			// 个位补零
			addZero(d) {
				return parseInt(d) < 10 ? "0" + d : d;
			},
			//删除订单
			delOrder(code) {
				this.$http.get("/order/order/deleteOrder/" + code).then(res => {
					if (res.data.code === 2000) {
						this.$message.success(res.data.msg);
						this.pageObj.pageCurrent = 1;
						this.initOrderList();
					}
				})
			},
			queryPayStatus() {
				//继续支付
				var that = this;
				this.$http.get("order/payLog/paystatus/" + this.payResult.code).then(r => {
					if (r.data.code === 2000) {
						clearInterval(this.timer);
						//如果支付成功，清除定时器
						that.$message.success(r.data.msg);
						//消除定时器
						clearInterval(this.timer);
						that.timer = null;
						that.dialogVisible = false;
						that.dialogVisible2 = false;
						that.dialogVisible3 = false;
						//刷新界面
						that.initOrderList();
						//that.$router.go(0);
						//跳转到课程详情页面观看视频
						// that.$router.push({path: '/DetailId/:id' + that.payResult.course_id})
					}
				})
			},
			pay(item) {
				this.payData = item;
				this.showPay = true;
			},
			//头部选择的按钮
			clicktab(int) {
				this.num = int;
				this.pageObj.pageCurrent = 1;
				this.pageObj.pageSize = 5;
				this.pageObj.totalPage = '',
					this.getOrderList(int);
			},

			//监听分页的触发
			getPage: function(int) {
				this.pageObj.pageCurrent = int;
				this.initOrderList();
			},

			//订单数据重载，，判断要查询所有还是查询支付或未支付订单
			getOrderList(int) {
				if (int === 1) { //查询待支付订单
					this.zhuangtai = 1;
				} else if (int === 2) { //已支付订单
					this.zhuangtai = 0;
				} else if (int === 0) { //查询所有
					this.zhuangtai = "";
				} else if (int === 3) { //已失效订单
					this.zhuangtai = 3;
				} else if (int === 4) { //已取消订单
					this.zhuangtai = 4;
				} else if (int === 5) { //未评价订单
					this.zhuangtai = 5;
				}
				this.initOrderList()
			},

			//获取订单信息
			initOrderList() {
				this.notdata = false;
				var that = this;
				this.$http.get(`/sys-order/o-order/findOrder/${this.pageObj.pageCurrent}/${this.pageObj.pageSize}` +
					"?zhuangtai=" + this.zhuangtai + "&userId=" + sessionStorage.getItem("userId")).then(function(
					resp) {
					that.pageObj.order = resp.data.data.records;
					that.pageObj.totalCount = resp.data.data.total;
					setTimeout(() => {
						that.loading = false
					}, 1500);
					for (var i = 0; i < that.pageObj.order.length; i++) {
						that.daojishi(i, that.pageObj.order[i])
					}
					if (resp.data.data.total === 0) {
						that.notdata = true;
					}
					that.pageObj.totalPage = Math.ceil(that.pageObj.totalCount / 5)
				})
			},

			//取消支付
			quxiao(id) {
				this.$confirm(`确定要取消订单吗?`, {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var that = this;
					this.$http.get("/order/order/delOrder/" + id).then(function(resp) {
						if (resp.data.code === 2000) {
							that.$message.success(resp.data.msg);
						} else {
							that.$message.error(resp.data.msg);
						}
						that.pageObj.pageCurrent = 1;
						that.initOrderList();
						//that.$router.go(0);
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '取消支付失败'
					});
				})


			}

		},
		mounted() {
			this.getOrderList();
			this.loading = true;
		},
		components: {
			YSide,
			DPage,
			vueQr
		}

	}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
	#daojishi {
		height: 60px;
		width: 175px;
		float: right;
		margin-top: 100px;
		margin-right: -182px;
	}

	#goumaishuliang {
		color: lightslategray;
		font-size: 16px;
		margin-top: 68px;
		margin-left: 377px;
		float: left;
	}

	#xian {
		display: block;
		height: 1px;
		width: 720px;
		margin: 24px 0;
	}

	#sname {
		margin-left: 50px;
	}


	#goodsname {
		margin-left: 15px;
		margin-top: -10px;
	}

	#img {
		width: 50px;
		height: 50px;
	}

	.person_info {
		padding: 25px;
		min-height: 400px;
	}

	.order_content {
		border-bottom: 2px solid rgba(245, 245, 245, 1);
		margin-top: 20px;
		position: relative;

		&:first-child {
			margin-top: 0;
		}
	}

	.order_title {
		line-height: 36px;
		padding-left: 20px;
		background: rgba(245, 245, 245, 1);
		font-size: 14px;
		color: #999;

		.order_type {
			margin-right: 10px;
			color: #0099FF;
		}

		.time {
			margin-left: 30px;
		}
	}

	.order_body {
		margin: 15px 0;
	}

	.body_left {
		padding: 0 20px;
		width: 740px;

		// position: relative;
		.img_box {
			width: 120px;
			height: 59px;
			display: inline-block;
		}

		img {
			width: 120px;
			height: 59px;
		}

		p {
			// position: absolute;
			width: 336px;
			white-space: inherit;
			margin-left: 10px;
			// left: 160px;
			font-size: 14px;
			color: #999;
		}
	}

	.body_right {
		float: right;

		li {
			float: left;
			height: 50px;
			width: 100px;
			text-align: center;
			border-left: 2px solid rgba(245, 245, 245, 1);
			font-size: 14px;
			color: #999;

			&.alipay {
				color: #0099FF;
			}

			&.money {
				color: #D51423;
			}

			.cancel {
				display: block;
				text-align: center;
				color: #999;

				&:hover {
					color: #D51423;
				}
			}

			.go_btn {
				display: block;
				width: 76px;
				height: 30px;
				margin-left: 10px;
				line-height: 30px;
				background: #fff;
				border-radius: 6px;
				font-size: 14px;
				cursor: pointer;
				color: #999;
				border: 1px solid #999;

				&.go_pay {
					border-color: orange;
					color: orange;
				}

				&:hover {
					color: #D51423;
					border-color: #D51423;
				}
			}
		}
	}

	.notdata {
		text-align: center;
		padding: 60px 0;
		font-size: 30px;
		color: #999;
		min-height: 400px;

		i {
			font-size: 30px;
			padding-right: 12px;
		}
	}

	.page {
		margin-bottom: 20px;
		margin-top: 10px;
	}
</style>
