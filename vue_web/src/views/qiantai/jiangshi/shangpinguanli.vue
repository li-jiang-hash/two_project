<template>
  <div class="pad20">
    <el-card class="box-card">
      <!--头部搜索-->
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" size="mini">
        <el-form-item label="商品名称">
          <el-input v-model="searchForm.gname" placeholder="请输入商品名称"></el-input>
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="searchForm.state" placeholder="请选择审核状态">
            <el-option  label="审核通过" :value="0"/>
            <el-option  label="审核驳回" :value="1"/>
            <el-option  label="等待审核" :value="2"/>
          </el-select>
        </el-form-item>
        <el-form-item label="商品状态">
          <el-select v-model="searchForm.status" placeholder="请选择审核状态">
            <el-option label="下架" :value="1"></el-option>
            <el-option label="上架" :value="0"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button icon='el-icon-search'size="mini" type="primary" @click="handleCheck">查询</el-button>
          <el-button icon='el-icon-refresh' size="mini" @click="handleReset" >重置</el-button>
          <el-button type="primary" icon="el-icon-refresh" round @click="addDialogFormVisible=true">新增</el-button>
        </el-form-item>
      </el-form>

      <!--表格数据-->
      <div>
        <el-table v-loading="ctrl.loading" size="medium" :data="tableData" stripe border style="width: 100%">
          <el-table-column prop="cardfront" label="商品图片" width="122" fixed header-align="center" align="center">
            <template slot-scope="scope">
              <img :src="scope.row.img"  width="100" height="60">
            </template>
          </el-table-column>
          <el-table-column
              prop="gname"
              label="商品名称"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="totalnum"
              label="库存量"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="stocknum"
              label="剩余量"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="sort.sortname"
              label="商品分类"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="remark"
              label="商品描述"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="unit.unitname"
              label="商品单位"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="brand.brandname"
              label="商品品牌"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="deposit.depositname"
              label="储藏方式"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="expirationtime"
              label="保质期"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="checkContent"
              label="驳回理由"
              align="center"
              width="120px">
          </el-table-column>
          <el-table-column
              prop="price"
              label="商品价格"
              align="center"
              width="100px">
            <template slot-scope="scope">
              {{scope.row.price}}元
            </template>
          </el-table-column>
          <el-table-column
              prop="state"
              label="审核状态"
              align="center"
              fixed="right"
              width="120px">
            <template slot-scope="scope">
              <el-button v-if="scope.row.state===0" type="success" plain>审核通过</el-button>
              <el-button v-if="scope.row.state===1" type="danger" plain>审核驳回</el-button>
              <el-button v-if="scope.row.state===2" type="warning" plain>等待审核</el-button>
            </template>
          </el-table-column>
          <el-table-column
              prop="status"
              label="商品状态"
              align="center"
              fixed="right"
              width="120px">
            <template slot-scope="scope">
              <el-button v-if="scope.row.status===0" type="success" plain>上架</el-button>
              <el-button v-if="scope.row.status===1" type="danger" plain>下架</el-button>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
              align="center"
              width="250">
            <template slot-scope="scope">
              <ul class="list-item-actions">
                <li>
                  <el-button type="primary" @click="updateGoods(scope.row)" size="mini">修改</el-button>
                  <el-button type="danger" @click="deleteGoods(scope.row.id)" size="mini">删除</el-button>
                  <el-button v-if="scope.row.status===1 && scope.row.state===0" type="success" @click="changeStatus(scope.row)" size="mini">上架</el-button>
                  <el-button v-if="scope.row.status===1 && scope.row.state!==0" disabled type="success" size="mini">上架</el-button>
                  <el-button v-if="scope.row.status===0"  type="warning" @click="changeStatus(scope.row)" size="mini">下架</el-button>
                </li>
              </ul>
            </template>
          </el-table-column>
        </el-table>

        <el-divider><i class="el-icon-moon-night"></i></el-divider>
        <div class="mgt20">
          <el-pagination
              background
              style="float: right;margin-top: 20px; margin-bottom: 22px"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="[1, 5, 10, 15]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>

      </div>

      <!--添加的弹出层-->
      <el-dialog title="商品信息" :visible.sync="addDialogFormVisible">
        <el-form :model="addForm" style="width: 400px;">
          <el-form-item label="商品名称" label-width="80px">
            <el-input v-model="addForm.gname" ></el-input>
          </el-form-item>
          <el-form-item label="商品图片" label-width="80px">
            <el-upload
                    style="float: left;margin-right: 40px"
                    class="avatar-uploader"
                    action="http://localhost:7500/syssystem/file/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
              <img v-if="imgUrl" v-model="addForm.img" :src="imgUrl"
                   class="avatar" alt="">
              <i v-else class="el-icon-plus avatar-uploader avatar-uploader-icon" style="border-radius: 6px;border: 1px dashed #d9d9d9;"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="商品描述" label-width="80px">
            <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="addForm.remark">
            </el-input>
          </el-form-item>
          <el-form-item label="商品单位" label-width="80px">
            <el-select v-model="addForm.unitid" placeholder="请选择">
              <el-option
                      v-for="item in unitForm"
                      :label="item.unitname"
                      :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品品牌" label-width="80px">
            <el-select v-model="addForm.brandid" placeholder="请选择">
              <el-option
                      v-for="item in brandForm"
                      :label="item.brandname"
                      :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品类别" label-width="80px">
            <el-select v-model="addForm.sortid" placeholder="请选择">
              <el-option
                      :label="sortname.sortname"
                      :value="addForm.sortid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="储藏方式" label-width="80px">
            <el-select v-model="addForm.depositid" placeholder="请选择">
              <el-option
                      v-for="item in depositForm"
                      :label="item.depositname"
                      :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="保质期" label-width="80px">
            <el-date-picker
                    value-format="yyyy-MM-dd hh:mm:ss"
                    v-model="addForm.expirationtime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="商品价格" label-width="80px">
            <el-input-number v-model="addForm.price" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
            <span style="padding-left: 20px">(单位:元)</span>
          </el-form-item>
          <el-form-item label="商品库存" label-width="80px">
            <el-input-number v-model="addForm.totalnum" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addDialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addGoods">确 定</el-button>
        </div>
      </el-dialog>



      <!--修改的弹出层-->
      <el-dialog title="商品信息" :visible.sync="updateDialogFormVisible">
        <el-form :model="updateForm" style="width: 400px;">
          <el-form-item label="商品名称" label-width="80px">
            <el-input v-model="updateForm.gname" ></el-input>
          </el-form-item>
          <el-form-item label="商品图片" label-width="80px">
            <el-upload
                    style="float: left;margin-right: 40px"
                    class="avatar-uploader"
                    action="http://localhost:7500/syssystem/file/upload"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
              <img v-if="imgUrl" v-model="updateForm.img" :src="imgUrl"
                   class="avatar" alt="">
              <i v-else class="el-icon-plus avatar-uploader avatar-uploader-icon" style="border-radius: 6px;border: 1px dashed #d9d9d9;"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="商品描述" label-width="80px">
            <el-input
                    type="textarea"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="updateForm.remark">
            </el-input>
          </el-form-item>
          <el-form-item label="商品单位" label-width="80px">
            <el-select v-model="updateForm.unitid" placeholder="请选择">
              <el-option
                      v-for="item in unitForm"
                      :label="item.unitname"
                      :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品品牌" label-width="80px">
            <el-select v-model="updateForm.brandid" placeholder="请选择">
              <el-option
                      v-for="item in brandForm"
                      :label="item.brandname"
                      :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="商品类别" label-width="80px">
            <el-select v-model="updateForm.sortid" placeholder="请选择">
              <el-option
                      :label="updateForm.sort.sortname"
                      :value="updateForm.sortid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="储藏方式" label-width="80px">
            <el-select v-model="updateForm.depositid" placeholder="请选择">
              <el-option
                      v-for="item in depositForm"
                      :label="item.depositname"
                      :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="保质期" label-width="80px">
            <el-date-picker
                    value-format="yyyy-MM-dd hh:mm:ss"
                    v-model="updateForm.expirationtime"
                    type="datetime"
                    placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="商品价格" label-width="80px">
            <el-input-number v-model="updateForm.price" @change="handleChange" :min="1" :max="10" label="描述文字"></el-input-number>
            <span style="padding-left: 20px">(单位:元)</span>
          </el-form-item>
          <el-form-item label="商品库存" label-width="80px">
            <el-input-number v-model="updateForm.totalnum" @change="handleChange" :min="1"  label="描述文字"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updateDialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateConfirm">确 定</el-button>
        </div>
      </el-dialog>


    </el-card>

  </div>

</template>

<script>


export default {


  data(){

    return{
      updateForm:{
        sort:{}
      },
      updateDialogFormVisible:false,
      sortname:"",
      depositForm:{},
      brandForm:{},
      unitForm:{},
      imgUrl:"",
      //添加的表单信息
      addForm:{},
      addDialogFormVisible:false,
      currentPage:1,
      pageSize:5,
      //总条数
      total:0,
      searchForm:{},
      formData:{},
      tableData:[],
      insertDialogVisible: false,
      statusForm:{
        id:"",
        status:0
      },


      // 页面控制数据，例如形式弹窗，显示加载中等
      ctrl: {
        loading: false,
        addDialogVisible: false,
        dialogVisible: false
      },
    }
  },
  created() {
    this.init()
    this.initUnit();
    this.initBrand();
    this.initDeposit();
    this.getSort();
    this.gitbusinessId()
  },
  methods:{
    getSort(){
      this.$http.get("syssystem/g-sort/getSort").then(res=>{
        console.log(res.data.data);
        if (res.data.code===2000){
          this.sort = res.data.data;
          // this.addForm.sortname=res.data.data;
          console.log(this.sort);
        }
      })
    }
    ,
    gitbusinessId(){
      sessionStorage.getItem("bisid")
      
    }
    ,
    updateConfirm(){
      this.$http.post("commodity/goods/updateGoods",this.updateForm).then(res=>{
        if (res.data.code===2000){
          this.$message.success(res.data.msg);
          this.updateDialogFormVisible = false;
          this.imgUrl="";
          this.updateForm = {
            sort:{}
          }
          this.init()

        }
      })
    },
    updateGoods(row){
      this.imgUrl = row.img
      this.updateDialogFormVisible = true;
      this.updateForm = row;
    },
    addGoods(){
       
      console.log( this.addForm)
      this.addForm.bid = sessionStorage.getItem("bisid")
      this.$http.post("syssystem/g-goods/addGoods",this.addForm).then(res=>{
        if (res.data.code===2000){
          this.$message.success(res.data.msg);
          this.addDialogFormVisible = false;
          this.addForm={}
          this.imgUrl=""
          this.init();
        }
      })
    },
    handleChange(value) {
      console.log(value);
    },
    initDeposit(){
      this.$http.post("syssystem/g-deposit/findAllDeposit").then(res=>{
        if (res.data.code===2000){
          this.depositForm = res.data.data;
        }
      })
    },
    initBrand(){
      this.$http.post("syssystem/g-brand/brand").then(res=>{
        if (res.data.code===2000){
          this.brandForm = res.data.data;
        }
      })
    },
    //页面加载查询所有单位
    initUnit(){
      this.$http.get("syssystem/g-unit/selectAllUnit").then(res=>{
        if (res.data.code===2000){
          this.unitForm = res.data.data;
        }
      })
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
    handleAvatarSuccess(res, file) {
      this.imgUrl = res.data;
      this.addForm.img = this.imgUrl
      this.$forceUpdate()
    },
    //修改上下架状态
    changeStatus(row){
      this.statusForm.id = row.id;
      if (row.status===0){
        this.statusForm.status = 1;
      }
      if (row.status===1){
        this.statusForm.status=0;
      }
      this.$http.post("commodity/goods/updateStatus",this.statusForm).then(res=>{
        if (res.data.code===2000){
          this.$message.success(res.data.msg);
          this.init();
          this.statusForm = {}
        }
      })
    },
    deleteGoods(id){
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.post("commodity/goods/deleteGoodsById?id="+id).then(res=>{
          if (res.data.code===2000){
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.init();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    // 关闭编辑弹窗回调
    closeCallback() {
      this.ctrl.dialogVisible = false;
      this.ctrl.addDialogVisible = false
      this.reload()
    },
    //改变当前页数
    handleSizeChange(val) {
      this.pageSize=val;
      this.init();
    },
    //改变当前每页的条数
    handleCurrentChange(val) {
      this.currentPage=val;
      this.init();
    },
    //条件查询方法
    handleCheck(){
      this.init()
    },
    //重置方法
    handleReset(){
      this.searchForm ={}
      this.reload()
    },
    //清空查询的表单数据
    reload(){
      this.searchformData={}
      this.init()
    },

    /*页面加载时获取所有的信息*/
    init(){
      var that=this;
      console.log(this.searchForm);
      this.$http.post(`/syssystem/g-goods/selectAllGoodsInfoByBusinessId/${this.currentPage}/${this.pageSize}/${sessionStorage.getItem("bisid")}`,this.searchForm).then(function (resp) {
        if (resp.data.code===2000){
          that.tableData=resp.data.data.records;
          that.total=resp.data.data.total;
          //that.addForm.sortid = that.tableData[0].sortid
          //that.sortname= that.tableData[0].sort.sortname
        }
      })
    },

    //删除
    handleDelete(linkId) {
      var that=this;
      this.$http.post(`/home/link/deleteLink/${linkId}`).then(function (resp) {
        if (resp.data.code===2000){
          that.$message({
            message:resp.data.msg,
            type:"success"
          })
        }else{
          that.$message({
            message:resp.data.msg,
            type:"error"
          })
        }
        that.init();
      })
    },
    //添加按钮
    insertConfirm(){
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          axios.post("emp/insert",this.insertForm).then(function (result){
            if (result.data.code===2000){
              app.$message.success(result.data.msg);
            }
            app.insertForm = {}
            app.insertDialogVisible = false
            this.currentPage = 1
            app.initTable();
          })
        }
      });
    },
    removeInformation(){
      this.$refs['ruleForm'].resetFields();
      this.insertForm = {}
    },
  }
}
</script>

<style scoped>
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
</style>