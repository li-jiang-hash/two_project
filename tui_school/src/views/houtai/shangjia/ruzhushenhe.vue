<template>
  <div class="pad20">
    <div>
      <el-form :inline="true" :model="map" size="mini">
        <el-form-item label="审核状态：">
          <el-select
            v-model="map.status"
            class="auto-width"
            clearable
            filterable
            placeholder="请选择审核状态"
            style="width: 130px"
          >
            <el-option label="待审核" :value="2"> </el-option>
            <el-option label="审核通过" :value="0"> </el-option>
            <el-option label="审核驳回" :value="1"> </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" type="primary" @click="handleCheck"
            >查询</el-button
          >
          <el-button icon="el-icon-refresh" class="filter-item" @click="handleReset"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table
        :data="statusIdList"
        style="width: 100%"
        border
        :default-expand-all="false"
      >
        <el-table-column
          header-align="center"
          align="center"
          prop="sname"
          label="商铺店名"
        >
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          prop="sortname"
          label="商品类别"
        >
        </el-table-column>
        <el-table-column header-align="center" align="center" prop="bname" label="申请人">
        </el-table-column>
        <el-table-column
          prop="license"
          label="营业执照"
          width="122"
          fixed
          header-align="center"
          align="center"
        >
          <template slot-scope="scope">
            <img :src="scope.row.license" width="100" height="60" />
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          prop="telephone"
          label="电话号码"
        >
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          prop="reason"
          label="审核备注"
        >
        </el-table-column>

        <el-table-column
          prop="cardfront"
          label="身份证正面"
          width="122"
          fixed
          header-align="center"
          align="center"
        >
          <template slot-scope="scope">
            <img :src="scope.row.cardfront" width="100" height="60" />
          </template>
        </el-table-column>
        <el-table-column
          prop="cardreverse"
          label="身份证反面"
          width="122"
          fixed
          header-align="center"
          align="center"
        >
          <template slot-scope="scope">
            <img :src="scope.row.cardreverse" width="100" height="60" />
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          align="center"
          prop="status"
          label="审核状态"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.status === 0">审核成功</span>
            <span v-if="scope.row.status === 1">审核驳回</span>
            <span v-if="scope.row.status === 2">待审核</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" header-align="center" align="center">
          <template slot-scope="scope">
            <el-button
              v-if="scope.row.status === 2"
              type="primary"
              @click="shenhe(scope.row)"
              size="mini"
              >去审核</el-button
            >
            <el-button v-if="scope.row.status !== 2" type="info" size="mini"
              >已审核</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!--审核的弹出层-->
      <el-dialog
        title="用户审核"
        :visible.sync="checkvisible"
        :before-close="closeCllback1"
        width="30%"
      >
        <el-form ref="checkformDataRef" :model="checkformData" label-width="100px">
          <el-form-item label="审核状态：">
            <el-radio-group v-model="checkformData.status">
              <el-radio :label="0">通过</el-radio>
              <el-radio :label="1">不通过</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注：" style="margin-left: -40px">
            <el-input
              type="textarea"
              :rows="3"
              placeholder="请输入备注内容"
              v-model="checkformData.reason"
            ></el-input>
          </el-form-item>
        </el-form>
        <el-row style="margin-top: 17px">
          <el-button
            style="float: right; margin-left: 6px"
            size="mini"
            type="danger"
            plain
            @click="resetCheck"
            >取 消</el-button
          >
          <el-button
            style="float: right"
            size="mini"
            type="primary"
            @click="submitCheckForm"
            >确定</el-button
          >
        </el-row>
      </el-dialog>

      <el-pagination
        background
        style="float: right; margin-top: 20px; margin-bottom: 22px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.currentPage"
        :page-size="page.pageSize"
        :page-sizes="[2, 4, 8, 15, 20, 30]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.totalCount"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      sorts: {},
      checkformData: {},
      //审核的弹出层
      checkvisible: false,
      map: {},
      formData: {},
      tableData: [],
      expands: [],
      addSubjectFormData: {
        remark: "",
      },
      editSubjectFormData: {},
      addDialogVisible: false,
      editDialogVisible: false,
      page: {
        beginPageIndex: 1,
        currentPage: 1,
        endPageIndex: 8,
        pageSize: 5,
        totalCount: 0,
        totalPage: 0,
      },
      statusIdList: [],
      //添加框的表单验证
      addSubjectFormRule: {
        title: [{ required: true, message: "分类名称不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    this.findAllBrands();
  },
  methods: {
    //时间转换
    dateFormat(row, column, cellValue, index) {
      const daterc = row[column.property];
      //console.log(row, column)
      if (daterc) {
        if (daterc.indexOf("T") === -1) return daterc;
        const arr = daterc.split("T");
        const d = arr[0];
        const darr = d.split("-");
        const t = arr[1];
        const tarr = t.split(".000");
        const marr = tarr[0].split(":");
        const dd =
          darr[0] +
          "-" +
          darr[1] +
          "-" +
          darr[2] +
          " " +
          marr[0] +
          ":" +
          marr[1] +
          ":" +
          marr[2].substring(0, 2);
        return dd;
      }
    },
    //提交审核的表单
    submitCheckForm() {
      var that = this;
      this.$http
        .post(`/syssystem/b-business-info/shenhe`, this.checkformData)
        .then(function (resp) {
          if (resp.data.code === 2000) {
            that.$message.success(resp.data.msg);
            that.checkvisible = false;
            that.findAllBrands();
          } else {
            that.$message.error(resp.data.msg);
            that.checkvisible = false;
            that.findAllBrands();
          }
        });
    },
    //审核取消后清空
    resetCheck() {
      this.checkvisible = false;
      this.checkformData.checkContent = "";
      this.reload();
    },
    //点击审核的弹出层
    shenhe(row) {
      this.checkformData = row;
      this.checkvisible = true;
    },
    //页面加载查询所有品牌
    findAllBrands() {
      this.$http
        .post(
          "/syssystem/b-business-info/ruzhu?currentPage=" +
            this.page.currentPage +
            "&pageSize=" +
            this.page.pageSize,
          this.map
        )
        .then((res) => {
          if (res.data.code === 2000) {
            this.statusIdList = res.data.data.records;
            this.page.totalCount = res.data.data.total;
          }
        });
    },
    //编辑分类框
    editSubject(row) {
      this.editDialogVisible = true;
      this.editSubjectFormData = row;
    },
    //添加分类框
    addSubject(parentId, id) {
      this.addDialogVisible = true;
      this.addSubjectFormData.parentId = parentId;
      this.addSubjectFormData.id = id;
    },
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.findAllBrands();
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.findAllBrands();
    },
    // 查询条件
    handleCheck() {
      this.findAllBrands();
    },
    // 重置查询条件
    handleReset() {
      this.reload();
    },

    // 关闭弹窗回调
    closeCllback() {
      this.addDialogVisible = false;
      // this.$refs.addSubjectFormRef.resetFields();
      this.reload();
    },
    closeCllback1() {
      this.checkvisible = false;
      // this.$refs.editSubjectFormRef.resetFields();
      this.reload();
    },

    // 刷新当前页面
    reload() {
      this.map = {};
      this.editSubjectFormData = {};
      this.findAllBrands();
    },
    textClass(userType) {
      return {
        c_red: userType === 0,
        c_blue: userType === 2,
      };
    },
  },
};
</script>
