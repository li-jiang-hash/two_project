<!--头部导航-->
<template>
  <div class="pad20">
    <el-card class="box-card">
      <el-form :inline="true" :model="formData" class="demo-form-inline" size="mini">
        <el-form-item label="商家id">
          <el-input v-model="formData.businessId" placeholder="商家id"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button icon='el-icon-search'size="mini" type="primary" @click="handleCheck">查询</el-button>
          <el-button icon='el-icon-refresh' size="mini" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!--表格数据-->
      <el-table
          :data="tableData"
          stripe
          size="medium"
          style="width: 100%"
          :border="true"
      >
        <el-table-column
            prop="id"
            label="id"

        >
        </el-table-column>
        <el-table-column
            prop="sname"
            label="商家店名"
            align="center"
        >
        </el-table-column>
          <el-table-column
              prop="appealTime"
              label="申请时间"
              :formatter="dateFormat"
              align="center"
          >
        </el-table-column>
        <el-table-column
            prop="info"
            label="申诉内容"
            align="center"
        >
        </el-table-column>
        <el-table-column
        header-align="center"
        align="center"
        prop="status"
        label="状态"
        width="">
        <template slot-scope="scope">
          <el-tag type="primary" v-if="scope.row.status==0" class="el-icon-circle-check"> 待审核</el-tag>
          <el-tag type="success" v-if="scope.row.status==1" class="el-icon-circle-close">申请通过</el-tag>
          <el-tag type="danger" v-if="scope.row.status==2" class="el-icon-refresh-left">申请驳回</el-tag>
        </template>
        </el-table-column>

        <!--操作按钮-->
        <el-table-column
        header-align="center"
        align="center"
        label="操作"
        width="200">
        <template slot-scope="scope">
          <el-button type="success" icon="el-icon-edit" v-if="scope.row.status==0"  size="small" @click="pass(scope.row)">通过</el-button>
          <el-button type="danger" icon="el-icon-edit" v-if="scope.row.status==0" size="small" @click="bohui(scope.row)">驳回</el-button>
        </template>
        </el-table-column>
      </el-table>


      <el-dialog
          title="驳回"
          :visible.sync="rejectDialogVisible"
          width="50%">
        <span>
            确定要驳回吗？
        </span>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="rejectConfirm">确 定</el-button>
        </span>
      </el-dialog>

      <el-divider><i class="el-icon-moon-night"></i></el-divider>
      <!--分页-->
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
    </el-card>





  </div>
</template>

<script>
export default {
  name: "HeadNavigation",
  data(){
    return {
      businessid:"",
      //审核驳回弹出层显示隐藏
      rejectDialogVisible:false,
      //用来接受后台传来的表中的数据
      tableData: [],
      currentPage:1,
      pageSize:5,
      //总条数
      total:0,
      //表单数据对象
      formData:{
        businessId: ""
      },
      ruleForm: {

      },
      editruleForm: {
        id:"",
        businessId:"",
        appealTime:"",
        info:"",
        status:""
      },
      rules: {



      },
      //不启用的导航 用于添加导航回显
      addTableData:[],
      //修改会话框标题
      title:""

    }
  },
  created() {
    //页面加载时调用查询的方法
    this.init();
  },
  methods:{
    //时间转换
    dateFormat(row, column, cellValue, index) {
      const daterc = row[column.property]
      //console.log(row, column)
      if (daterc) {
        if (daterc.indexOf('T') === -1) return daterc
        const arr = daterc.split('T')
        const d = arr[0]
        const darr = d.split('-')
        const t = arr[1]
        const tarr = t.split('.000')
        const marr = tarr[0].split(':')
        const dd = darr[0] + '-' + darr[1] + '-' + darr[2] + ' ' + marr[0] + ':' + marr[1] + ':' + marr[2].substring(0, 2)
        return dd
      }
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
    //条件查询
    handleCheck(){
      this.init()
    },
    // 重置查询条件
    handleReset() {
      this.reload()
    },
    //页面重载
    reload(){
      this.formData={}
      this.init()
    },

    //重置表单数据
    reset(){
      this.$refs.ruleForms.resetFields();
    },
    editreset(){
      this.$refs.editruleForms.resetFields();
    },
    close(){
      this.$refs.ruleForms.resetFields();
    },
    editclose(){
      this.$refs.editruleForms.resetFields();
    },
    //分页查询当前的所有数据
    init(){
      this.$http.post("/business/appeal/shensu?currentPage="+this.currentPage+"&pageSize="+this.pageSize,this.formData).then(resp => {
        if (resp.data.code===2000){
          console.log(resp.data.data);
          this.tableData=resp.data.data.records;
          this.total=resp.data.data.total;

        }
        if (resp.data.code===5000){
          this.$message({
            message:resp.data.msg,
            type:"error",
          })
          this.tableData=[];
        }
      })
    },


    //确认驳回
    rejectConfirm(){
      var that = this
      this.$http.post("/business/appeal/bohui?id="+this.businessid+"&status=2").then(function (result){
        if (result.data.code===2000){
          that.$message.success(result.data.msg)
          that.rejectDialogVisible = false;
          that.init();
        }
      })
    },
    //点击驳回按钮
    bohui(row){
      this.businessid = row.id;
      this.rejectDialogVisible = true;
    },
    //审核通过
    pass(row){
      var that = this
      var id = row.id;
      this.$http.post("/business/appeal/tongguo?id="+id+"&status=1").then(function (result){
        if (result.data.code===2000){
          that.$message.success(result.data.msg)
          that.init()
        }
      })
    },


  }
}
</script>

<style scoped>
.elpage ,.eldialog{
  display: flex;
  justify-content: right;
}
</style>