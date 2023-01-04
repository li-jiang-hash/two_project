<template>
    <div id="hourGuanLi">
            <el-tabs v-model="activeName" style="padding-left: 10px">
                <el-tab-pane :label="sort" name="qbkc"></el-tab-pane>
            </el-tabs>

            <el-table
                    :data="tableData"
                    style="width: 100%">
                <el-table-column
                        type="index"
                        align="center"
                        label="序号">
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="title"
                        label="题目">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status==1" v-text="scope.row.title"></span>
                        <input v-if="scope.row.status==2" v-model="scope.row.title" style="height: 30px;padding-left: 10px"></input>
                    </template>
                </el-table-column>
                <el-table-column
                        align="center"
                        prop="videoSourceId"
                        label="视频">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status==1" class="icon-warp">
                            <template>
                                <i class="el-icon-video-play" id="bofang" @click="dianjiBoFang(scope.row)"></i>
                            </template>
                        </span>
                        <el-upload
                                v-if="scope.row.status==2"
                                class="upload-demo"
                                action="http://localhost:9000/student/member/uploadMsg"
                                :headers="homeToken"
                                :limit="3"
                                :on-success="handleAvatarSuccess"
                                :file-list="fileList">
                            <el-button size="mini" type="primary">视频上传</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="videoOriginalName"
                        align="center"
                        label="课件">
                    <template slot-scope="scope">
                        <span v-if="scope.row.status==1" >-</span>
                        <el-upload
                                v-if="scope.row.status==2"
                                class="upload-demo"
                                action="http://192.168.31.243:9000/student/member/uploadMsg"
                                :limit="3"
                                :on-success="handleAvatarSuccessFile"
                                :headers="homeToken"
                                :file-list="fileList">
                            <el-button size="mini" type="primary">附件上传</el-button>
                        </el-upload>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="isFree"
                        label="是否免费"
                    align="center"
                >
                    <template slot-scope="scope">
                        <span v-if="scope.row.isFree===1" class="c_green">免费</span>
                        <span v-else-if="scope.row.isFree===0" class="c_red">收费</span>
                        <el-checkbox v-else v-model="scope.row.isFree"></el-checkbox>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        align="center"
                >
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" v-if="scope.row.status===2" @click="insertCourse(scope.row,scope.$index+1)">保存</el-button>
                        <el-button type="text" v-if="scope.row.status===1" size="small" @click="updateHour(scope.row)">修改</el-button><br>
                        <el-button type="text" v-if="scope.row.status===1" size="small" @click="removeCourse(scope.row.id,scope.row.sort)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <center>
                <el-button type="danger" plain style="width: 300px;margin-top: 50px" @click="returnCommit()">完成返回</el-button>
            </center>

        <!--视频文件的播放-->
        <el-dialog width="35%"
                   id="pp"
                   :title="MoveTitle"
                   v-if="addFormVisible"
                   :destroy-on-close=true
                   :visible.sync="addFormVisible">
            <d-player ref="player" id="player" :options="options"></d-player>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "hourGuanLi",
        data(){
            return{
                MoveTitle:'',
                homeToken:{homeToken:sessionStorage.getItem("homeToken")},
                fileList: [],
                activeName: 'qbkc',
                tableData: [
                    {
                        id:"",
                        chapterId:this.chapterId,
                        courseId:this.courseId,
                        title:'',
                        videoSourceId:'',
                        videoOriginalName:'',
                        status:2,
                        sort:'',
                        isFree:'',
                    }
                ],

                //章节id,
                chapterId:'',
                courseId:'',
                sort:'',

                //上传的视频
                videoUrl:"",
                //上传的文件
                videoOriginalName:"",

                addFormVisible:false,
                db:'',
                parentMsg:'',
                options: {
                    container: document.getElementById("dplayer"), //播放器容器
                    mutex: false, //  防止同时播放多个用户，在该用户开始播放时暂停其他用户
                    theme: "#b7daff", // 风格颜色，例如播放条，音量条的颜色
                    loop: false, // 是否自动循环
                    lang: "zh-cn", // 语言，'en', 'zh-cn', 'zh-tw'
                    screenshot: true, // 是否允许截图（按钮），点击可以自动将截图下载到本地
                    hotkey: true, // 是否支持热键，调节音量，播放，暂停等
                    preload: "auto", // 自动预加载
                    volume: 0.7, // 初始化音量
                    playbackSpeed: [0.5, 0.75, 1, 1.25, 1.5, 2, 3], //可选的播放速度，可自定义
                    video: {
                        url: '', // 播放视频的路径
                        pic: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=24336452,906724543&fm=26&gp=0.jpg', // 视频封面图片
                        thumbnails:
                            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=24336452,906724543&fm=26&gp=0.jpg" // 进度条上的缩略图,需要通过dplayer-thumbnails来生成
                    },
                },
                contextmenu: [
                    //  自定义上下文菜单
                    // 右键菜单
                    {
                        text: "b站",
                        link: "https://www.bilibili.com"
                    },
                    {
                        text: "返回首页",
                        click: player => {
                            console.log(player);
                        }
                    }
                ],
                highlight: [
                    //进度条上的自定义时间标记
                    // 进度条时间点高亮
                    {
                        text: "10M",
                        time: 6
                    },
                    {
                        text: "20M",
                        time: 12
                    }
                ]
            }
        },
        created(){
            this.chapterId = this.$route.query.chapterId;//章节id
            this.courseId = this.$route.query.courseId;//课程id
            this.sort ="第"+ this.$route.query.sort+"章";//第几章的内容
            this.initTable();
        },
        methods:{

            //点击视频播放
            dianjiBoFang(row){
                this.addFormVisible=true;
                this.options.video.url=row.videoSourceId;
                this.MoveTitle=row.title;
                //dialog默认采用的懒加载模式，，即没打开之前不会将dialog中元素渲染到DOM上，，所以想要使用需要设置超时时间为0，解法非唯一
                setTimeout(() => {
                    this.$refs.player.dp.play();
                }, 0)
            },

            updateHour(row){
                row.status=2;
                if (row.isFree===1){
                    row.isFree=true;
                }else {
                    row.isFree=false;
                }
            },


            //初始化数据
            initTable(){
                this.$http.get(`/core/video/findAllVideo/${this.chapterId}`).then((resp) => {
                    if (resp.data.code===2000){
                        this.tableData=resp.data.result;
                        const object = {
                            id:"",
                            chapterId:this.chapterId,
                            courseId:this.courseId,
                            title:'',
                            videoSourceId:'',
                            videoOriginalName:'',
                            status:2,
                            sort:'',
                        }
                        this.tableData.push(object);
                    }
                })
            },
            //上传文件成功之后的回调
            handleAvatarSuccess(res, file) {
                this.videoUrl=res;
            },
            handleAvatarSuccessFile(res,file){
                this.videoOriginalName=res;
            },



            //添加
            insertCourse(row,index){

                if (row.title===""){
                    this.$message.error("题目不可以为空")
                    return;
                }
                if (row.isFree!=null && row.isFree===true){
                    row.isFree=1;
                }else {
                    row.isFree=0;
                }
                if (row.id===""){
                    if (this.videoUrl===""){
                        this.$message.error("视频不可以为空")
                        return;
                    }

                    this.$http.put(`core/video/insertVideo`,{chapterId:this.chapterId,isFree:row.isFree,title:row.title,videoSourceId:this.videoUrl,videoOriginalName:this.videoOriginalName,chapterId:this.chapterId,courseId:this.courseId,sort:index}).then((resp) => {
                        if (resp.data.code==2000){
                            row.status=1;
                            this.$message.success(resp.data.msg);
                            this.initTable();
                            this.videoUrl=null;
                            this.videoOriginalName=null;
                        }else {
                            this.$message.error(resp.data.msg);
                        }
                    })
                }else {
                    this.$http.post(`core/video/updateVideo`,{id:row.id,isFree:row.isFree,chapterId:this.chapterId,title:row.title,videoSourceId:this.videoUrl,videoOriginalName:this.videoOriginalName}).then((resp) => {
                        if (resp.data.code==2000){
                            row.status=1;
                            this.$message.success(resp.data.msg);
                            this.initTable();
                            this.videoUrl=null;
                            this.videoOriginalName=null;
                        }else {
                            this.$message.error(resp.data.msg);
                        }
                    })
                }
            },
            //删除
            removeCourse(id,sort){
                this.$confirm(`确定要《《彻底删除》》该课程吗?`, {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {//确定删除
                    this.$http.delete(`core/video/deleteById/${id}/${sort}/${this.chapterId}`).then((resp) => {
                        if (resp.data.code===2000){
                            this.$message.success(resp.data.msg);
                            this.initTable();
                        }else {
                            this.$message.error(resp.data.msg);

                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '删除已取消'
                    });
                })

            },
            returnCommit(){
                this.$router.push('/updateCourse?courseId='+this.courseId);
            }
        }
    }
</script>

<style scoped>
    #bofang:hover{
        color: red;
        cursor:pointer
    }
    .icon-warp{
        font-size: 20px;
    }
</style>