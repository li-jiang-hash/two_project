<template>
	<div class="courselist_page">
		<app-header @getCourseByCourseName="getCourseByCourseName"></app-header>
		<div>
			<!--背景色设置-->
			<div class="teacher_bg">
				<img src="../../../assets/img/1.jpg" height="110" width="1920" />
			</div>
			<!--讲师信息展示-->
			<div class="login_Avatar" style="float: left">
				<!--商家头像-->
				<img :src="business.bicon" height="434" width="434" />
			</div>
			<div style="font-size: 20px;margin-left: 120px;margin-top: -50px;display: block;color: red">
				{{business.sname}}</div>
		</div>
		<!-- 右边通过v-for循环展示出的内容 -->
		<div style="margin:25px 120px;">
			<ul class="">
				<!-- v-for key值 -->
				<li class="phone_item" v-for="(good,index) in goods" :key="index">
					<div @click="tiaozhuan(good.id)">
						<!-- 图片 -->
						<div class="ph">
							<img :src="good.img" width="230" height="140px">
						</div>
						<!-- 名字 -->
						<div class="phone_title">{{good.gname}}</div>
						<!-- 备注 -->
						<p class="desc">{{good.desc}}</p>
						<!-- 价格 -->
						<p class="price">
							<span>{{good.price}}</span>
							元起
						</p>
					</div>
				</li>
			</ul>
			<d-page v-if="pageObj.totalPage > 1" :page="pageObj" @btnClick="getPage"></d-page>
		</div>

	</div>
</template>

<script>
	import DPage from '@/components/Page'
	import HeaderList from "@/components/HeaderList";
	import RightTap from "@/components/RightTap";
	import AppHeader from "@/components/common/header";
	import AppFooter from "@/components/common/footer";
	export default {
		name: "BusinessXinX",
		components: {
			HeaderList,
			RightTap,
			AppFooter,
			AppHeader,
			DPage,
		},
		data() {
			return {
				results: [],
				imgUrl: require("/src/assets/img/3.jpg"),
				weiVisable: true,
				openVip: false,
				free: '',
				bid: "",
				courseName: '',
				teacherDate: {},
				//课程信息
				pageObj: {
					//当前页面--》page
					pageCurrent: '1',
					//每页条数--》limit
					pageSize: '8',
					//总条数
					totalCount: '',
					//total的页数（有几页）【totalCount/pageSize】
					totalPage: '',
					list: {},
				},
				business: {},
				goods: {},
				shenfen: ""

			}
		},
		created() {
			this.bid = this.$route.params.id;

			this.inintCourse()
			this.$http.post("user/getShenfen").then(resp => {
				this.shenfen = resp.data.data;
				console.log(this.shenfen)
			})

		},
		methods: {
			getCourseByCourseName() {

				this.inintCourse();
			},
			tiaozhuan(gid) {
				var token = sessionStorage.getItem("token");
				if (token && this.shenfen === "BUSINESS_PHONE") {
					this.$message.warning("请使用用户身份登录")
				} else if (token) {
					this.$router.push({
						name: 'DetailId',
						params: {
							id: gid
						}
					})
				} else {
					this.$message.warning("登录后才能访问")
				}
			},
			//获取讲师课程信息
			inintCourse() {
				var that = this
				this.$http.get(
					`/syssystem/g-goods/findGoodsByBid/${this.pageObj.pageCurrent}/${this.pageObj.pageSize}/${this.bid}`
					).then(function(resp) {
					if (resp.data.code === 2000) {
						that.business = resp.data.data.business;
						console.log(that.business)
						that.goods = resp.data.data.goods.records;
						console.log(that.goods)
						that.pageObj.totalCount = resp.data.data.goods.total;
					} else {
						that.pageObj.totalCount = 0;
					}
					that.pageObj.totalPage = Math.ceil(that.pageObj.totalCount / 8)
				})
			},

			//监听分页的触发
			getPage: function(int) {
				this.pageObj.pageCurrent = int;
				this.inintCourse();
			},
		}
	}
</script>

<style lang="scss" rel="stylesheet/scss" scoped>
	.courselist_page {
		.course_content {
			background: rgb(245, 245, 245);
			padding: 30px 0 10px 0;

			ul {
				width: 1200px;
				margin: 0 auto;
			}

			li {
				float: left;
				border-radius: 6px;
				background: #fff;
				margin: 0px 20px 20px 0px;

				&:nth-child(4n) {
					margin-right: 0px;
				}
			}
		}

		.course_info {
			display: block;
			width: 285px;
			height: 240px;
			border-radius: 6px;
			position: relative;

			&:hover {
				text-decoration: none;
				color: #000;
				box-shadow: 0px 3px 18px rgba(0, 0, 0, 0.2);
				transform: translateY(-2px);
				transition: all .3s;
			}

			.img_box {
				position: relative;
				width: 285px;
				height: 140px;

				.course_img {
					width: 285px;
					height: 140px;
					border-radius: 6px 6px 0 0;
				}
			}

			p {
				font-size: 16px;
				margin-top: 5px;
				padding: 0 10px;
				word-break: break-all;
			}

			.price_box {
				font-size: 16px;
				position: absolute;
				bottom: 15px;
				left: 10px;
				color: red;
			}

			.font_12 {
				font-size: 12px;
			}

			.c_999 {
				color: #999;
			}

		}
	}

	.teacher_bg {
		width: 100%;
		height: 100px;
		background-image: url("../../../assets/img/jiangshi.jpg");
		/*background: no-repeat 50%;*/
		/*background-color: #1E1E1E;*/
	}

	.teacher_bg2 {
		width: 100%;
		height: 200px;
	}

	.zhognx {
		width: 1200px;
		height: 500px;
		margin: auto;
	}

	.login_Avatar {
		width: 88px;
		height: 88px;
		border-radius: 44px;
		overflow: hidden;
		border: 3px solid #fff;
		margin-top: -90px;
		position: relative;
	}

	.login_Avatar>img {
		max-width: 100%;
		width: 90px;
		height: 90px;
		border: none;
	}

	.teacher_info {
		width: 1060px;
		margin: -30px 0 0 20px;
		line-height: 20px;
	}

	.c_fff {
		color: #fff;
	}

	.font_18 {
		font-size: 18px;
	}

	.c_999 {
		color: #999;
	}

	.font_14 {
		font-size: 14px;
	}

	.mgt20 {
		margin-top: 20px;
	}

	body {
		background-color: #f5f5f5;
	}

	.box {
		width: 1300px;
		margin: 50px auto 0px;
		/* background-color: #9C82E8; */
	}

	.box_hd {
		width: 1300px;
		height: 60px;
		line-height: 60px;
		border-bottom: 2px #000000 solid;
		font-size: 18px;
	}

	.title {
		padding-left: 10px;
		float: left;
	}

	.more a {
		float: right;
		line-height: 60px;
		color: #000000;
	}

	.more a:hover {
		color: #ffb217;
	}

	.box_left {
		float: left;
		margin-top: 20px;
		margin-left: 10px;
	}

	.box_right {
		/* float: left; */
	}

	.phone_item {
		display: block;
		float: left;
		width: 280px;
		height: 250px;
		background-color: #FFFFFF;
		margin: 15px 0 0 30px;
		margin-top: 20px;

	}

	/* 手机配图 */
	.ph {
		text-align: center;
		margin: 10px 0;
	}

	/* 手机名称 */
	.phone_title {
		color: #000000;
		text-align: center;
		font-size: 18px;
		margin-bottom: 13px;
	}

	/* 备注 */
	.desc {
		color: #7a7c79;
		font-size: 13px;
		text-align: center;
		margin-bottom: 10px;
	}

	/* 价格 */
	.price {
		color: #ffac38;
		text-align: center;
		margin-top: 15px;
		font-weight: bold;
	}
</style>
