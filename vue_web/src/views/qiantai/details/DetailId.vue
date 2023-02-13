<template>
	<div class="course_detail">

		<y-display :classData="classData" :ChildId="ChildId" :isTitileUpdate="isTitileUpdate" :isFree="isFree"
			:videoId="videoId" @TeacherVideoStatus="TeacherVideoStatus"></y-display>


		<div class=" detail_info detail_box clearfix">
			<div class="layout_left">
				<ul class="course_tab clearfix">
					<li :class="{on: tab === 'info'}"><a href="javascript:" @click="changeComments('info')">商品评论</a>
					</li>
					<li v-if="hasBuy===true" :class="{on: tab === 'big'}">
						<a href="javascript:" @click="changeComments('big')">我的评论</a>
					</li>
					<el-button v-if="hasBuy===true" type="primary"
						style="float: right;margin-right: 20px;margin-top: 15px" @click="addComment">添加评论</el-button>
				</ul>
				<!--商品评论-->
				<div class="content_info" v-if="tab === 'info'">
					<div v-for="item in comments">
						<el-avatar style="float: left" :size="47" :src="item.userInfo.uicon"></el-avatar>
						<div style="float: left;font-size: 15px;margin-left: 25px;margin-top: 3px">
							{{item.userInfo.uname}}<br>
							<span style="font-size: 10px">
								<span>评论时间：{{item.createtime}} </span>
								<span v-if="item.updatetime!=null">修改时间：{{item.updatetime}}</span>
							</span>
						</div>
						<div style="font-family: 幼圆;font-size: 10px">
							<el-rate style="float: right" :value="item.level" disabled show-score text-color="#ff9900">
							</el-rate>
						</div>
						<div style="height: 40px"></div>
						<div style="width: 790px;margin-top: 15px;margin-left: 69px;">
							<div style="font-family: 幼圆;font-size: 15px;margin-bottom: 25px">
								{{item.info}}
							</div>
							<span style="margin-right: 20px" v-for="items in item.imgs" v-if="items!=''">
								<img :src="items" width="100px" height="100px" border="1px solid green">
							</span>
						</div>
						<el-divider></el-divider>
					</div>

					<div class="block" style="float: left;margin-top: -14px">
						<el-pagination style="float: right" @size-change="handleSizeChange"
							@current-change="handleCurrentChange" :current-page="pageCurrent" :page-sizes="pageSizes"
							:page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
						</el-pagination>
					</div>
				</div>
				<!--我的评论-->
				<div class="content_info" v-if="tab === 'big'">
					<div v-for="item in mycomments">
						<el-avatar style="float: left" :size="47" :src="item.userInfo.uicon"></el-avatar>
						<div style="float: left;font-size: 15px;margin-left: 25px;margin-top: 3px">
							{{item.userInfo.uname}}<br>
							<span style="font-size: 10px">
								<span>评论时间：{{item.createtime}} </span>
								<span v-if="item.updatetime!=null">修改时间：{{item.updatetime}}</span>
							</span>
						</div>
						<div style="font-family: 幼圆;font-size: 10px">
							<el-rate style="float: right" :value="item.level" disabled show-score text-color="#ff9900">
							</el-rate>
						</div>
						<div style="height: 40px"></div>
						<div style="width: 790px;margin-top: 15px;margin-left: 69px;">
							<div style="font-family: 幼圆;font-size: 15px;margin-bottom: 25px">
								{{item.info}}
								<div
									style="font-size: 20px;font-family: 幼圆;float: right;margin-top: -20px;width: 124px">
									<div style="float: left">
										<el-link style="font-size: 20px" type="primary" @click="editComment(item)">
											编辑
										</el-link>
									</div>
									<div style="float: right">
										<el-link style="font-size: 20px" type="danger" @click="deleteComment(item.id)">
											删除
										</el-link>
									</div>

								</div>
							</div>
							<span style="margin-right: 20px" v-for="items in item.imgs" v-if="items!=''">
								<img :src="items" width="100px" height="100px" border="1px solid green">
							</span>
						</div>
						<el-divider></el-divider>
					</div>

					<div class="block" style="float: left;margin-top: -14px">
						<el-pagination style="float: right" @size-change="handleSizeChange"
							@current-change="handleCurrentChange" :current-page="pageCurrent" :page-sizes="pageSizes"
							:page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
						</el-pagination>
					</div>
				</div>
			</div>
			<div class="layout_right">
				<div class="teacher_info clearfix" style="width: 300px;">
					<span class="head">
						所属店铺:{{classData.sname}}
						<a href="javascript:" class="atten_btn" style="margin-right: 10px" @click="gunazhudianpu"
							v-if="guanZhuStatus===1">
							<span class="iconfont"></span>&nbsp;未关注
						</a>
						<a href="javascript:" class="atten_btn" style="margin-right: 10px" @click="gunazhudianpu"
							v-if="guanZhuStatus===0">
							<span class="iconfont" style="color: red"></span>&nbsp;已关注
						</a>
					</span>
					<div class="teacher_msg">
						<div class="teacher_msg_right">

							<div>
								<!--                            <img class="teacher_phone" v-if="classData.avatar" :src="classData.avatar" alt="">-->
								<img class="teacher_phone" v-if="classData.avatar" :src="classData.avatar" alt="">
							</div>
							<!--                            <img class="teacher_phone" v-else src="./src/assets/image/da1.png" alt="">-->
							<div class="teacher_name">
								<router-link :to="{name: 'BusinessXinX', params: {id:classData.bid}}">
									<img :src="classData.bicon" style="width: 300px;margin-left: -17px">
								</router-link>
							</div>
							<div v-html="classData.intro" class="info_box"></div>
						</div>
					</div>
				</div>
			</div>


			<el-dialog title="编辑评论" :visible.sync="commentDialogVisible" width="50%">
				<span>
					<span style="float: left"> 我的评分:</span>
					<el-rate style="float: left;margin-left: 50px" v-model="editData.level"></el-rate>
					<el-input style="margin-top: 20px" type="textarea" :rows="2" placeholder="从多个角度评价宝贝,可以帮助更多想买的人"
						v-model="editData.info">
					</el-input>
					<div style="margin: 20px 0">最多上传三张图片!</div>
					<el-upload style="float: left;margin-right: 40px" class="avatar-uploader"
						action="http://localhost:7500/syssystem/file/upload" :show-file-list="false"
						:on-success="handleAvatarSuccess0" :before-upload="beforeAvatarUpload">
						<img v-if="editData.imgs[0]" v-model="editData.imgs[0]" :src="editData.imgs[0]" class="avatar"
							alt="">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-upload style="float: left;margin-right: 40px" class="avatar-uploader"
						action="http://localhost:7500/syssystem/file/upload" :show-file-list="false"
						:on-success="handleAvatarSuccess1" :before-upload="beforeAvatarUpload">
						<img v-if="editData.imgs[1]" v-model="editData.imgs[1]" :src="editData.imgs[1]" class="avatar"
							alt="">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-upload style="float: left;margin-right: 40px" class="avatar-uploader"
						action="http://localhost:7500/syssystem/file/upload" :show-file-list="false"
						:on-success="handleAvatarSuccess2" :before-upload="beforeAvatarUpload">
						<img v-if="editData.imgs[2]" v-model="editData.imgs[2]" :src="editData.imgs[2]" class="avatar"
							alt="">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<div style="height: 200px"></div>
					<el-link @click="deleteImg(0)" icon="el-icon-delete"
						style="float: left;margin-right: 173px;margin-left: 64px">删除</el-link>
					<el-link @click="deleteImg(1)" icon="el-icon-delete" style="float: left;margin-right: 173px;">删除
					</el-link>
					<el-link @click="deleteImg(2)" icon="el-icon-delete" style="float: left">删除</el-link>
				</span>
				<span slot="footer" class="dialog-footer">
					<el-button @click="commentDialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="updateComment">确 定</el-button>
				</span>
			</el-dialog>

			<!--添加评论-->
			<el-dialog title="编辑评论" :visible.sync="addcommentDialogVisible" width="50%">
				<span>
					<span style="float: left"> 我的评分:</span>
					<el-rate style="float: left;margin-left: 50px" v-model="addCommentData.level"></el-rate>
					<el-input style="margin-top: 20px" type="textarea" :rows="2" placeholder="从多个角度评价宝贝,可以帮助更多想买的人"
						v-model="addCommentData.info">
					</el-input>
					<div style="margin: 20px 0">最多上传三张图片!</div>
					<el-upload style="float: left;margin-right: 40px" class="avatar-uploader"
						action="http://localhost:7500/syssystem/file/upload" :show-file-list="false"
						:on-success="addhandleAvatarSuccess0" :before-upload="beforeAvatarUpload">
						<img v-if="addCommentData.imgs[0]" v-model="addCommentData.imgs[0]"
							:src="addCommentData.imgs[0]" class="avatar" alt="">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-upload style="float: left;margin-right: 40px" class="avatar-uploader"
						action="http://localhost:7500/syssystem/file/upload" :show-file-list="false"
						:on-success="addhandleAvatarSuccess1" :before-upload="beforeAvatarUpload">
						<img v-if="addCommentData.imgs[1]" v-model="addCommentData.imgs[1]"
							:src="addCommentData.imgs[1]" class="avatar" alt="">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-upload style="float: left;margin-right: 40px" class="avatar-uploader"
						action="http://localhost:7500/syssystem/file/upload" :show-file-list="false"
						:on-success="addhandleAvatarSuccess2" :before-upload="beforeAvatarUpload">
						<img v-if="addCommentData.imgs[2]" v-model="addCommentData.imgs[2]"
							:src="addCommentData.imgs[2]" class="avatar" alt="">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<div style="height: 200px"></div>
					<el-link @click="deleteImg1(0)" icon="el-icon-delete"
						style="float: left;margin-right: 173px;margin-left: 64px">删除</el-link>
					<el-link @click="deleteImg1(1)" icon="el-icon-delete" style="float: left;margin-right: 173px;">删除
					</el-link>
					<el-link @click="deleteImg1(2)" icon="el-icon-delete" style="float: left">删除</el-link>
				</span>
				<span slot="footer" class="dialog-footer">
					<el-button @click="addcommentDialogVisible = false">取 消</el-button>
					<el-button type="primary" @click="addCommentConfirm">确 定</el-button>
				</span>
			</el-dialog>


		</div>
	</div>

</template>

<script>
	import YDisplay from "@/components/course/Display"

	export default {
		name: "DetailId",
		components: {
			YDisplay,
		},
		data() {
			return {
				addCommentData: {
					level: null,
					info: "",
					imgs: ["", "", ""],
				},
				addcommentDialogVisible: false,
				editData: {
					level: null,
					info: "",
					imgs: ["", "", ""],
				},
				commentDialogVisible: false,
				//我的评论
				mycomments: {},
				//全部评论
				comments: {},
				//课程和教师数据
				tab: 'info',
				//当前播放章节
				nowPeriodNo: '',
				classData: {},
				// 传过来的goodsid
				id: this.$route.params.id,
				ChapterData: {},
				//孩子传过来的id
				ChildId: '',
				videoId: '',

				isFree: false,
				isTitileUpdate: false,
				TeacherStatus: false,
				//关注的实体类
				guanZhuData: {
					teacherid: '',
					memberid: window.sessionStorage.getItem("memberId"),
				},
				guanZhuStatus: 1,
				//新增入库的总条数
				total: 0,
				//新增入库的接受一个整型数组，数组元素为显示的选择每页显示的个数的选项
				pageSizes: [3, 5, 8, 10],
				//新增入库的默认显示几条
				pageSize: 3,
				//新增入库的当前显示的页面变量
				pageCurrent: 1,
				dialogImageUrl: '',
				dialogVisible: false,
				hasBuy: false,

				collection: {}

			}
		},
		created() {
			console.log(this.getCurrentTime())

			this.queryClassAndTeacher();
			//页面加载获取评论
			this.getComments();
			//页面加载获取我的评论
			this.getMyComments();
			this.hasByThisGoods();
			this.isCollectionBusiness();

		},

		methods: {
			//图片上传成功后的回调
			addhandleAvatarSuccess0(res, file) {
				this.addCommentData.imgs[0] = res.data;
				this.$forceUpdate()
			},
			//图片上传成功后的回调
			addhandleAvatarSuccess1(res, file) {
				this.addCommentData.imgs[1] = res.data;
				this.$forceUpdate()
			},
			//图片上传成功后的回调
			addhandleAvatarSuccess2(res, file) {
				this.addCommentData.imgs[2] = res.data;
				this.$forceUpdate()
			},
			//确认添加评论
			addCommentConfirm() {
				this.addCommentData.goodsid = this.id;
				this.addCommentData.bid = this.classData.busid;
				this.addCommentData.userid = sessionStorage.getItem("userId")
				this.$http.post("syssystem/u-comment/addComment", this.addCommentData).then(res => {
					if (res.data.code === 2000) {
						this.$message.success(res.data.msg);
						this.addcommentDialogVisible = false;
						this.getMyComments()
						this.getComments()
					}
				})
			},
			//页面加载判断该用户是否买过该商品
			hasByThisGoods() {
				this.$http.get("sys-order/o-order/hasBuyThisGoods/" + this.id + "/" + sessionStorage.getItem("userId"))
					.then(res => {
						if (res.data.data) {
							this.hasBuy = true;
						}
					})
			},
			//添加评论
			addComment() {
				this.addcommentDialogVisible = true
			},
			//删除评论
			deleteComment(id) {
				this.$http.get("syssystem/u-comment/deleteById?id=" + id).then(res => {
					if (res.data.data) {
						this.$message.success(res.data.msg)
						this.getMyComments();
						this.getComments()
					} else {
						this.$message.success("操作失败，请重试！")
					}
				})
			},
			//提交编辑评论
			updateComment() {
				if (this.editData.info === "" || this.editData.info === '') {
					this.$message.error("请编辑评论后再提交评价!")
					return
				}
				this.$http.post("syssystem/u-comment/addComment", this.editData).then(res => {
					if (res.data.data) {
						this.$message.success(res.data.msg)
						this.commentDialogVisible = false;
						this.getMyComments()
						this.getComments()
					} else {
						this.$message.success("失败，请重试！")
					}
				})
			},
			deleteImg(num) {
				this.editData.imgs[num] = ''
				this.$forceUpdate()
				this.$message.success("已删除")

			},
			deleteImg1(num) {
				this.addCommentData.imgs[num] = ''
				this.$forceUpdate()
				this.$message.success("已删除")

			},
			beforeAvatarUpload(file) {
				const isJPG = file.type === 'image/jpeg';
				const isPNG = file.type === 'image/png';
				const isLt2M = file.size / 1024 / 1024 < 5;

				if (!isJPG && !isPNG) {
					this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
					return
				}
				if (!isLt2M) {
					this.$message.error('上传头像图片大小不能超过 5MB!');
					return
				}
				return (isJPG || isPNG) && isLt2M;
			},
			//图片上传成功后的回调
			handleAvatarSuccess0(res, file) {
				this.editData.imgs[0] = res.data;
				this.$forceUpdate()
			},
			//图片上传成功后的回调
			handleAvatarSuccess1(res, file) {
				this.editData.imgs[1] = res.data;
				this.$forceUpdate()
			},
			//图片上传成功后的回调
			handleAvatarSuccess2(res, file) {
				this.editData.imgs[2] = res.data;
				this.$forceUpdate()
			},

			//编辑评论渲染
			editComment(item) {
				this.commentDialogVisible = true;
				this.editData = item
			},

			changeComments(obj) {
				this.tab = obj;
				this.pageCurrent = 1;
				this.pageSize = 3;
			},
			//新增入库的每页显示的条数大小改变时触发的方法
			handleSizeChange(val) {
				this.pageSize = val;
				if (this.tab === 'info') {
					this.getComments();
				} else {
					this.getMyComments();
				}
			},
			//新增入库的页码改变时触发的方法
			handleCurrentChange(val) {
				this.pageCurrent = val;
				if (this.tab === 'info') {
					this.getComments();
				} else {
					this.getMyComments();
				}

			},
			//我的评论
			getMyComments() {
				this.$http.get("syssystem/u-comment/findComment?id=" + this.$route.params.id +
					"&pageCurrent=" + this.pageCurrent + "&pageSize=" + this.pageSize +
					"&userId=" + sessionStorage.getItem("userId")).then(res => {
					if (res.data.code === 2000) {
						this.mycomments = res.data.data.records;
						this.total = res.data.data.total
					}
				})
			},
			//评论
			getComments() {
				this.$http.get("syssystem/u-comment/findComment?id=" + this.$route.params.id +
					"&pageCurrent=" + this.pageCurrent + "&pageSize=" + this.pageSize).then(res => {
					if (res.data.code === 2000) {
						this.comments = res.data.data.records;
						// console.log(res.data.data.records.imgs)
						this.total = res.data.data.total
					}
				})
			},
			//关注/取关店铺
			gunazhudianpu() {
				var status = 0;
				if (this.guanZhuStatus === 0) {
					status = 1;
				}

				this.collection.bid = this.classData.bid
				this.collection.status = status
				this.collection.uid = sessionStorage.getItem("userId")
				this.$http.post("syssystem/u-collection/changeCollectionStatus", this.collection).then(res => {
					if (res.data.data) {
						this.guanZhuStatus = res.data.data;
						this.$message.success(res.data.msg);
						this.isCollectionBusiness();
					}
				})

			},
			//是否关注店铺
			isCollectionBusiness() {
				this.$http.get("syssystem/u-collection/isGoodsCollection?bid=" + this.classData.bid + "&userId=" +
					sessionStorage.getItem("userId")).then(res => {
					if (res.data.code === 2000) {

						this.guanZhuStatus = res.data.data[0].status;

					}
				})
			},

			//父传子
			UpdateStatus(isTitileUpdate) {
				this.isTitileUpdate = isTitileUpdate;
			},
			FuChuanZi: function(videoSourceId) {
				// childValue就是子组件传过来的值
				this.ChildId = videoSourceId

			},
			TeacherVideoStatus(TeacherVideoStatus) {
				this.TeacherStatus = TeacherVideoStatus;

			},
			//是否免费
			updateIsFree(isFree) {
				this.isFree = isFree;
				// alert(this.isFree)
			},
			//VideoId
			updateVideoId(val) {
				this.videoId = val;
				// alert(this.videoId)
			},
			//根据gooodsid查询所有商品信息
			queryClassAndTeacher() {

				var that = this;
				this.$http.get("syssystem/g-goods/findGoodsByGoodsid/" + this.id).then(function(resp) {
					if (resp.data.code === 2000) {
						that.classData = resp.data.data;
						console.log(that.classData)
						that.isCollectionBusiness();
					}

					// console.log(that.guanZhuData.teacherid)
				})
			},

			getCurrentTime() {
				//获取当前时间并打印
				var _this = this;
				let yy = new Date().getFullYear();
				let mm = new Date().getMonth() + 1;
				let dd = new Date().getDate();
				let hh = new Date().getHours();
				let mf = new Date().getMinutes() < 10 ? '0' + new Date().getMinutes() : new Date().getMinutes();
				let ss = new Date().getSeconds() < 10 ? '0' + new Date().getSeconds() : new Date().getSeconds();
				_this.gettime = yy + '-' + mm + '-' + dd + ' ' + hh + ':' + mf + ':' + ss;
				return _this.gettime;
			}

		}
	}
</script>

<style lang="scss" rel="stylesheet/scss">
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}

	.course_detail {
		.detail_info {
			margin: 20px auto 60px;
			width: 1200px;
		}

		.layout_left {
			width: 920px;
			float: left;

			.info_body {
				margin-bottom: 30px;
			}
		}

		.course_tab {
			padding-left: 30px;
			background: #fff;
			border-radius: 8px;
			margin-bottom: 20px;

			li {
				float: left;
				height: 66px;
				line-height: 66px;
				margin-right: 80px;

				&.on {
					border-bottom: 2px solid #D51423;

					a {
						color: #D51423;
					}
				}

				a {
					color: #000;
					font-size: 14px;
					font-weight: 700;

					&:hover {
						text-decoration: none;
						color: #D51423;
					}
				}
			}
		}

		.content_info {
			padding: 30px;
			background: #fff;
			border-radius: 8px;
			min-height: 400px;

			.title {
				border-left: 3px solid #000;
				padding-left: 12px;
				font-size: 16px;
				color: #000;
				font-weight: 700;
				margin-bottom: 25px;
			}

			.introduce {
				font-size: 14px;
				line-height: 30px;
				color: #333;
				padding-left: 8px;
			}
		}

		.layout_right {
			width: 260px;
			float: right;
		}

		.teacher_info {
			background: #fff;
			border-radius: 8px;
			position: relative;
			min-height: 180px;

			.head {
				display: block;
				line-height: 60px;
				height: 60px;
				padding-left: 20px;
				font-size: 14px;
				color: #333;
				border-bottom: 1px solid rgb(228, 228, 228);
			}

			.teacher_phone {
				width: 46px;
				height: 46px;
				border-radius: 50%;
				background: rgb(228, 228, 228);
				text-align: center;
				line-height: 46px;
				font-size: 13px;
				color: #999;
				float: left;
				margin: 0px 10px 0 10px;

				img {
					width: 46px;
					height: 46px;
					border-radius: 50%;
				}
			}

			.teacher_msg_right {
				width: 235px;
				float: right;
				margin: 12px 15px 12px 12px;
				line-height: 20px;
			}

			.teacher_name {
				font-size: 14px;
				font-weight: 700;
				color: #333;
				margin-bottom: 10px;
			}
		}
	}
</style>
