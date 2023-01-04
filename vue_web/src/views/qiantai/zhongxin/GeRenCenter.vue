<template>
    <div id="geRenCenter">
    <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="个人信息" name="first">
                <div class="main_box">
                    <!--            v-if="isLogin"-->
                    <div class="main_cont form" v-if="isLogin">
                        <form action="" >
                            <div class="form_group">
                                <div class="label">手机号:</div>
                                <div class="form_ctl">
                                    <div class="text">{{obj.telephone}}</div>
                                </div>
                            </div>
                            <div class="form_group">
                                <div class="label">昵称:</div>
                                <div class="form_ctl">
                                    <input type="text" v-model="obj.uname" class="form_input" placeholder="请输入昵称">
                                </div>
                            </div>
                            <div class="form_group">
                                <div class="label">地址:</div>
                                <div class="form_ctl">
                                    <input type="text" v-model="obj.location" class="form_input" placeholder="请输入地址">
                                </div>
                            </div>
                            <div class="form_group">
                                <div class="label">年龄:</div>
                                <div class="form_ctl">
                                    <input type="text" v-model="obj.age" maxlength="2" oninput = "value=value.replace(/[^\d]/g,'')" class="form_input" placeholder="请输入年龄（100以内）">
                                </div>
                            </div>
                            <div class="form_group">
                                <div class="label">性别:</div>
                                <div class="form_ctl form_ctl_radio">
                                    <input type="radio" v-model="obj.usex" class="radiobox" id="sex1" value="0" name="sex">
                                    <label for="sex1">男</label>
                                    <input type="radio" v-model="obj.usex" class="radiobox" id="sex2" value="1" name="sex">
                                    <label for="sex2">女</label>
                                </div>
                            </div>
                            <div class="form_group">
                                <div class="label">个人头像:</div>
                                <div class="form_ctl upload_ctl">
                                    <input type="hidden" v-model="obj.uicon">
                                    <el-upload
                                            class="avatar-uploader"
                                            action="http://192.168.1.23:8000/user/upload01"
                                            :show-file-list="false"
                                            :on-success="handleAvatarSuccess"
                                            :before-upload="beforeAvatarUpload"
                                    >
                                        <img style="height: 100px;width: 100px" v-if="obj.uicon" v-model="obj.uicon" :src="obj.uicon" class="avatar" alt="">
                                        <i v-else  class="el-icon-plus avatar-uploader-icon"></i>
                                    </el-upload>
                                    <p class="tip">* 图片尺寸为800x800，图片大小&lt;500KB，建议使用真人照片，便于品牌宣传效果</p>
                                </div>
                            </div>
                            <div class="form_group">
                                <div class="label">&nbsp;</div>
                                <div class="form_ctl">
                                    <!--                            <button @click="baoCunYongHu" class="submit_btn xiugai">保存</button>-->
                                    <el-button @click="baoCunYongHu" class="submit_btn xiugai">保存</el-button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="main_cont form" v-else>
                        <div class="form_group">
                            <div class="label">手机号:</div>
                            <div class="form_ctl">
                                <div class="text">{{obj.telephone}}</div>
                            </div>
                        </div>
                        <div class="form_group">
                            <div class="label">昵称:</div>
                            <div class="form_ctl">
                                <div class="text">{{ obj.uname }}</div>
                            </div>
                        </div>
                        <div class="form_group">
                            <div class="label">地址:</div>
                            <div class="form_ctl">
                                <div class="text">{{ obj.location }}</div>
                            </div>
                        </div>
                        <div class="form_group">
                            <div class="label">年龄:</div>
                            <div class="form_ctl">
                                <div class="text">{{ obj.age }}</div>
                            </div>
                        </div>
                        <div class="form_group">
                            <div class="label">性别:</div>
                            <div class="form_ctl">
                                <div class="text" v-if="obj.usex === 0">男</div>
                                <div class="text" v-else-if="obj.usex === 1">女</div>
                            </div>
                        </div>
                        <div class="form_group">
                            <div class="label">个人头像:</div>
                            <div class="form_ctl upload_ctl">
                                <input type="hidden" v-model="obj.uicon">
                                <div class="preview">
                                    <img :src="obj.uicon" alt="" v-if="obj.uicon">
                                    <i class="iconfont" v-else>&#xe6b2;</i>
                                </div>
                            </div>
                        </div>
                        <div class="form_group">
                            <div class="label">&nbsp;</div>
                            <div class="form_ctl">
                                <button @click="isLogin=true" class="submit_btn xiugai" >修改</button>
                            </div>
                        </div>
                    </div>
                </div>
        </el-tab-pane>
        <el-button @click="insertA" size="small" style="background-color: #c9e5dc;margin-left: 90%">添加地址</el-button>
        <el-tab-pane label="收货地址" name="second">
                <el-table
                        :data="tableData"
                        style="width: 100%">
                    <el-table-column
                            label="我的地址"
                            prop="addr"
                            width="400px">
                    </el-table-column>
                    <el-table-column
                            label="操作">
                        <template slot-scope="scope">
                            <el-button @click="deleteAddr(scope.row.id)" size="small" >删除</el-button>
                            <el-button size="small" @click="update(scope.row)">编辑</el-button>
                        </template>
                    </el-table-column>
                </el-table>

            <!--            <%&#45;&#45;    添加地址&#45;&#45;%>-->
            <el-dialog
                    title="添加我的地址"
                    :visible.sync="insertAddr"
                    width="30%">
                <!--model 绑定表单数据-->
                <el-form :model="insertform" :rules="stuRules" ref="insertForm">
                    <el-form-item label="地址内容" label-width="100px" prop="addr">
                        <el-input v-model="insertform.addr" autocomplete="off"></el-input>
                    </el-form-item>
                </el-form>
                <span slot="footer" class="dialog-footer">
                <el-button @click="insertAddr=false">取消</el-button>
                <el-button type="primary" @click="insertATrue"> 确认</el-button>
            </span>
            </el-dialog>
        </el-tab-pane>
<!--        修改地址-->
        <el-dialog
                title="修改地址"
                :visible.sync="updateAddr"
                width="30%">
            <!--model 绑定表单数据-->
            <el-form :model="updateform" :rules="updateRules" ref="updateForm">
                <el-form-item label="地址内容" label-width="100px" prop="addr">
                    <el-input v-model="updateform.addr" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="updateAddr=false">取消</el-button>
                <el-button type="primary" @click="updateTrue"> 确认</el-button>
            </span>
        </el-dialog>


    </el-tabs>
</div>
</template>

<script>

    export default {
        name: "GeRenCenter",

        data () {
            return {
                isLogin: false,
                obj: {},
                activeName: 'first',
                tableData:[],
                insertAddr:false,
                insertform:{},
                updateAddr:false,
                updateform:{},
                //表单效验
                stuRules:{
                    addr:[{required:true,message:"请输入地址",trigger:'blur'}
                    ]
                },
                updateRules:{
                    addr:[{required:true,message:"请输入地址",trigger:'blur'}
                    ]
                },
            }
        },
        created () {
            //初始时加载数据，获取用户信息
            this.initMember();
            this.addressTable();
        },
        methods: {
            updateTrue(){
                this.$refs['updateForm'].validate((valid) =>{
                    var that = this;
                    if (valid) {//表示通过
                        this.$http.post("user/addr/updateById", this.updateform).then(function (result) {
                            if (result.data.code==2000){
                                that.$message.success(result.data.msg);//弹出成功
                                that.updateAddr=false;//隐藏弹出框
                                that.addressTable();//重新加载数据
                            }
                        })
                    }
                });
            },
            //修改点击事件
            update(row){
                this.updateAddr=true;
                this.addr=row.addr;
                //数据回显 quexiaobug
                this.updateform = JSON.parse(JSON.stringify(row));

                //清空效验
                this.$nextTick(() =>{
                    this.$refs['updateForm'].clearValidate()
                })
            },
            //确认添加
            insertATrue(){
                this.$refs['insertForm'].validate((valid) =>{
                    var that = this;
                    if (valid) {//表示通过
                        this.$http.post("user/addr/insertAddr", this.insertform).then(function (result) {
                            that.$message.success(result.data.msg);//弹出成功
                            that.insertAddr=false;//弹出框隐藏
                            that.addressTable();//重新加载数据
                        })
                    }
                });
            },
            insertA(){
                this.insertAddr=true;
                //清空表单效验
                this.$nextTick(() =>{
                    this.$refs['insertForm'].clearValidate()
                })
                //历史添加数据为空
                this.insertform={}
            },
            //删除地址
            deleteAddr(id){
                var that=this;
                this.$http.get('user/addr/deleteById?id='+id).then(function (result) {
                    that.$message.success(result.data.msg);
                    that.addressTable();
                })
            },
            //查询所有地址
            addressTable(){
                var that=this;
                this.$http.get(`/user/addr/findAll`).then(function (result) {
                    that.tableData=result.data.data;
                })
            },
            //标签页
            handleClick(tab, event) {
                //console.log(tab, event);
            },
            //修改用户信息的保存方法
            baoCunYongHu(){
                // console.log(this.obj)
                var that=this;
                this.$http.post('/user/updateMessage',this.obj).then(function (resp) {
                    that.$message.success(resp.data.msg);
                    that.isLogin=false
                    //重新加载页面
                    that.initMember();
                })

            },

            //图片上传成功后的回调
            handleAvatarSuccess(res, file) {
                this.obj.uicon = res.data;
            },
            //图片选择后的判断，是否为图片及图片大小限制
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },

            //初始化用户信息
            initMember(){
                var that=this;
                this.$http.get(`/user/findUserById`).then(function (result) {
                    that.obj=result.data.data;
                    //console.log(result)
                })
            }
        },


    }
</script>

<style lang="scss" scoped>

    .upload_ctl{
        .preview{
            width: 100px;
            background-color: #f5f5f5;
            text-align: center;
            margin-bottom: 10px;
            i{

                font-size: 80px;
                color: #ddd;
            }
        }
    }
    .xiugai{
        margin-left: 110px;
    }
    .form .submit_btn {
        text-align: center;
        line-height: 0px;
        color: #fff;
    }
</style>