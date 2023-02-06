<template>
	<div class="index_page">
		<y-banner :data="data" :height="366"></y-banner>
		<el-dialog :visible="centerDialogVisible" width="30%" :before-close="closeBack" :show-close=false
			:lock-scroll=false
			style="background: url('http://pic.baike.soso.com/p/20131217/20131217111445-811802356.jpg') no-repeat center;"
			center>
		</el-dialog>
		<div class="i_content" v-for="(item, index) in zoneData" :key="index" :id="'demoItem'+index">
			<div class="i_zone">
				<div class="zone_header">
					<div class="big_text">
						<span class="col_block"></span>
						<span class="col_block"></span>
						{{ item.zoneName }}
						<span class="small_text">{{ item.zoneDesc }}</span>
						<router-link
							:to="{name: 'list', params: {id: item.id,zoneName: item.zoneName,zoneDesc: item.zoneDesc}}"
							class="fr small_text link_text">更多店铺 ></router-link>
					</div>
				</div>
				<div class="zone_body">
					<ul class="clearfix business_list">
						<li v-for="(that, int) in business" :key="int" v-if="that.zone_id==item.id">
							<router-link :to="{name: 'BusinessXinX', params: {id: that.bid}}">
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
					<ul class="test_list clearfix">
						<li :class="{test_option: true, right_0: (num % 2 == 1)}"
							v-for="(resource, num) in item.zoneCourseLibList" :key="resource.id">
							<router-link target="_blank" :to="{name: 'libraryDetail', params: {id: resource.id}}"><i
									class="iconfont">&#xe6be;</i>{{resource.courseName}}</router-link>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<RightTap :weiVisable="weiVisable" :key="weiVisable"></RightTap>
	</div>

</template>
<script>
	import YBanner from "@/components/Banner";
	import RightTap from "../../../components/RightTap";

	export default {
		props: {
			hideTop: {
				type: Boolean
			},
			classList: {
				type: [Object, Array],
				default () {
					return []
				}
			},
			height: {
				type: [String, Number],
				default: 456
			}
		},
		data() {
			return {
				weiVisable: false,
				centerDialogVisible: false,
				data: {},
				webInfo: true,
				num: 0,
				interval: null,
				navList: [],
				isNow: '',
				clientData: {},
				service: '',
				list: [],
				friendList: [],
				zoneData: {},
				business: {}
			}
		},
		methods: {
			//关闭广告框
			closeBack() {
				this.centerDialogVisible = false
				this.weiVisable = false
			},
			login() {

			},
			register() {

			}
		},
		components: {
			YBanner,
			RightTap

		},
		created() {
			this.centerDialogVisible = false;
			var that = this;
			this.$http.get("syssystem/tb-zone/zone/prefecture").then(function(resp) {
				that.zoneData = resp.data.data;
				// console.log(that.zoneData)
			})
			this.$http.get("syssystem/tb-zone-business/zoneBusiness/selectZoneBusiness").then(resp => {
				this.business = resp.data.data;
				// console.log(this.business)
			})

		},
		mounted() {
			var that = this;
			if (this.webInfo && this.webInfo.isEnableVip) {
				this.openVip = true
			}
			this.$http.get("syssystem/tb-banner/rotationalSeeding").then(function(resp) {
				that.data = resp.data.data;
				//console.log(that.data)
			})
		},
	}
</script>
<style lang="scss" rel="stylesheet/scss">
	.el-dialog--center .el-dialog__body {
		text-align: initial;
		padding: 25px 25px 30px;
		display: none !important;
	}

	.el-dialog__header {
		padding: 20px 20px 10px;
		display: none !important;
	}

	.index_page {

		.clearfix:before,
		.clearfix:after {
			content: "";
			display: table;
		}

		.clearfix:after {
			clear: both;
		}

		.i_content {
			background: rgb(245, 245, 245);

			&:nth-child(2n) {
				background: rgb(228, 228, 228);
			}

			.i_zone {
				width: 1200px;
				margin: 0 auto;
				padding: 50px 0 40px 0;
			}
		}

		.zone_header {
			.col_block {
				display: inline-block;
				width: 4px;
				height: 24px;
				background: #333;
				position: relative;
				top: 4px;
			}

			.big_text {
				// text-align: center;
				font-size: 24px;
			}

			.small_text {
				// text-align: center;
				font-size: 14px;
				color: #999;
				margin-top: 10px;
			}

			.link_text:hover {
				text-decoration: none;
				color: #333;
			}
		}

		.zone_body {
			margin-top: 25px;
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

		.test_list {
			.test_option {
				float: left;
				width: 520px;
				padding: 18px 25px;
				margin-right: 55px;
				margin-bottom: 20px;
				border-radius: 8px;
				font-size: 14px;
				background-color: #fff;

				&.right_0 {
					margin-right: 0px;
				}
			}

			.iconfont {
				margin-right: 6px;
			}
		}
	}
</style>
