<template>
	<div class="courselist_page">
		<app-header @getCourseByCourseName="getCourseByCourseName" :name="this.chindVal"></app-header>
		<div>
			<div class="zone_body">
				<ul class="clearfix business_list">
					<li v-for="(that, int) in pageObj.list" :key="int">
						<router-link :to="{name: 'BusinessXinX', params: {id: that.id}}">
							<div class="img_box">
								<img :src="that.bicon" alt="">
							</div>
							<!-- 店铺名 -->
							<p style="font-size: 18px;color:red;font-family: 黑体">{{ that.sname }}</p>

							<!-- 成交信息 -->
							<p class="productStatus">
								<span v-if="that.sales!=null">总成交<span
										style="font-size: 14px;">{{that.sales}}</span>单</span>
								<span v-if="that.sales==null" style="font-size: 14px;color: #8c939d">暂未出单</span>
							</p>

							<div class="price_box" v-if="that.minprice!=null">
								￥{{Number(that.minprice).toFixed(2)}}起</div>
							<div class="price_box" v-if="that.minprice==null">￥0.00起</div>
						</router-link>
					</li>
				</ul>
				<d-page v-if="pageObj.totalPage > 1" :page="pageObj" @btnClick="getPage"></d-page>
			</div>
			<RightTap :weiVisable="weiVisable"></RightTap>
		</div>
		<app-footer></app-footer>
	</div>
</template>

<script>
	import DPage from '@/components/Page'
	import HeaderList from "@/components/HeaderList";
	import RightTap from "@/components/RightTap";
	import AppHeader from "@/components/common/header";
	import AppFooter from "@/components/common/footer";
	export default {
		name: "Business",
		components: {
			HeaderList,
			RightTap,
			AppFooter,
			AppHeader,
			DPage,
		},
		props: {

		},
		data() {
			return {
				imgUrl: require("/src/assets/img/3.jpg"),
				weiVisable: true,
				openVip: false,
				free: '',
				//teacherId:this.$route.query.teacherId,
				chindVal: this.$route.params.name,
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
				}

			}
		},
		created() {
			this.inintCourse()

		},
		methods: {
			getCourseByCourseName(name) {
				this.chindVal = name;
				//console.log(name)
				this.inintCourse();
			},

			//获取商店信息
			inintCourse() {
				this.pageObj.list = {}
				var that = this
				this.$http.get("syssystem/tb-zone-business/findBusinessByName?chandVal=" + this.chindVal +
					"&pageCurrent=" + this
					.pageObj.pageCurrent + "&pageSize=" + this.pageObj.pageSize).then(function(resp) {
					if (resp.data.code === 2000) {

						that.pageObj.list = resp.data.data.records;
						console.log(that.pageObj.list)
					}
					//console.log(that.pageObj.list)
					if (resp.data.data.records.length > 0) {
						that.pageObj.totalCount = resp.data.data.total;

					} else {
						that.pageObj.totalCount = 0;
					}
					that.pageObj.totalPage = Math.ceil(that.pageObj.totalCount / 8)
				})

			},

			//获取讲师课程信息
			inintTeacherByTeacherId() {
				var that = this
				this.$http.get(`/core/course/findTeacherByTeacherId/${this.teacherId}`).then(function(resp) {
					that.teacherDate = resp.data.result;
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
	.zone_body {
		margin: 25px 150px;
	}

	.business_list {
		li {
			float: left;
			width: 285px;
			height: 204px;
			border-radius: 6px;
			margin: 0px 20px 20px 0px;

			&:nth-child(4n) {
				margin-right: 0px;
			}

			&:hover {
				box-shadow: 0px 3px 18px rgba(0, 0, 0, 0.2);
				transform: translateY(-2px);
				transition: all .3s;
			}

			a {
				display: block;
				width: 100%;
				height: 100%;
				position: relative;
				background: #fff;
				border-radius: 6px;

				.img_box {
					width: 285px;
					height: 140px;
					position: relative;

					.qizi {
						background: url('../../../assets/image/activity/qizi.png') no-repeat center;
						position: absolute;
						top: 0;
						right: 10px;
						width: 46px;
						height: 46px;
						color: #fff;
						font-size: 14px;
						text-align: center;
					}

					img {
						width: 285px;
						height: 140px;
						border-radius: 6px 6px 0 0;
					}

					.live_time {
						position: absolute;
						bottom: 0;
						left: 0;
						font-size: 12px;
						line-height: 16px;
						width: 285px;
						background: rgba(0, 0, 0, 0.2);
						color: #fff;
						padding-bottom: 2px;
					}
				}

				&:hover {
					color: #000;
					text-decoration: none;
				}

				p {
					font-size: 16px;
					margin-top: 5px;
					padding-left: 10px;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
					word-break: break-all;
				}

				.price_box {
					font-size: 16px;
					position: absolute;
					bottom: 10px;
					right: 20px;
					color: red;
				}
			}
		}
	}

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
		height: 100px;
	}

	.zhognx {
		width: 1200px;
		height: 100px;
		margin: auto;
	}

	.login_Avatar {
		width: 88px;
		height: 88px;
		border-radius: 44px;
		overflow: hidden;
		border: 3px solid #fff;
		margin-top: -50px;
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
</style>
