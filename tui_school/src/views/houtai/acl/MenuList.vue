<template>
  <div class="menuList">
    <!--背景卡片-->
    <el-card class="box-card">
      <el-form
        size="mini"
        ref="souSuoForm"
        :inline="true"
        @submit.native.prevent
        :model="souSuoFormInline"
        class="demo-form-inline"
        style="margin-bottom: 0px"
      >
        <el-form-item label="" prop="startTime">
          <div class="block">
            <span>时间范围</span>
            <el-date-picker
              :clearable="false"
              v-model="valueTime"
              type="datetimerange"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search()" icon="el-icon-search"
            >查询</el-button
          >
          <el-button @click="resetForm('souSuoForm')" icon="el-icon-refresh"
            >重置</el-button
          >
          <el-button
            type="primary"
            @click="displayInsertForm = true"
            size="mini"
            icon="el-icon-plus"
            >新增</el-button
          >
        </el-form-item>
      </el-form>

      <!--数据表格-->
      <el-table
        :data="tableData"
        border
        style="width: 100%; margin-bottom: 20px"
        row-key="id"
        :default-expand-all="false"
      >
        <!--                <el-table-column-->
        <!--                        prop="id"-->
        <!--                        label="编号"-->
        <!--                        >-->
        <!--                </el-table-column>-->
        <el-table-column align="center" prop="gmt_date" label="发送日期">
        </el-table-column>
        <el-table-column align="center" prop="role_name" label="发送人"> </el-table-column>
        <el-table-column align="center" prop="stype" label="发送类型"> </el-table-column>
        <el-table-column align="center" fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="success" size="medium " @click="update(scope.row)"
              >修改</el-button
            >
            <el-button type="danger" size="medium" @click="del(scope.row, scope.row.id)"
              >删除</el-button
            >
            <el-button plain @click="open(scope.row.roleName, scope.row.scontend)">
              查看公告
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-divider><i class="el-icon-moon-night"></i></el-divider>
      <!--分页栏-->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="float: right; margin-top: 20px; margin-bottom: 22px"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>

      <!-- 发布通知-->
      <el-dialog
        title="发布公告"
        :visible.sync="displayInsertForm"
        width="28%"
        :before-close="handleInsertClose"
      >
        <el-form
          ref="insertForm"
          :model="insertFormDate"
          :rules="formRules"
          label-width="80px"
        >
          <el-form-item label="公告类型" prop="stype">
            <el-input v-model="insertFormDate.stype"></el-input>
          </el-form-item>
          <el-form-item label="公告内容" prop="scontend">
            <el-input v-model="insertFormDate.scontend"></el-input>
          </el-form-item>
        </el-form>
        <el-row style="margin-top: 17px">
          <el-button
            style="float: right; margin-left: 6px"
            size="mini"
            type="danger"
            plain
            @click="handleInsertClose()"
            >取 消</el-button
          >
          <el-button
            style="float: right"
            size="mini"
            type="primary"
            @click="insertFormSubmit()"
            >确 定</el-button
          >
        </el-row>
      </el-dialog>

      <!--修改通知-->
      <el-dialog
        title="修改"
        :visible.sync="displayUpdateForm"
        width="28%"
        :closed="handleUpdateClose"
      >
        <el-form
          ref="updateForm"
          :model="updateFormDate"
          :rules="formRules"
          label-width="80px"
          @submit.native.prevent
        >
          <el-form-item label="公告类型" prop="stype">
            <el-input v-model="updateFormDate.stype"></el-input>
          </el-form-item>
          <el-form-item label="公告内容" prop="scontend">
            <el-input type="textarea" v-model="updateFormDate.scontend"></el-input>
          </el-form-item>
        </el-form>
        <el-row style="margin-top: 17px">
          <el-button
            style="float: right; margin-left: 6px"
            size="mini"
            type="danger"
            plain
            @click="handleUpdateClose()"
            >取 消</el-button
          >
          <el-button
            style="float: right"
            size="mini"
            type="primary"
            @click="updateFormSubmit()"
            >确 定</el-button
          >
        </el-row>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import qs from "qs";
export default {
  name: "MenuList",
  data() {
    return {
      //时间范围
      valueTime: [],
      startTime: "",
      endTime: "",
      visible: false,
      id: "",
      //搜索表单
      souSuoFormInline: {
        name: "",
      },
      //表格数据
      tableData: [],
      //分页信息
      currentPage: 1,
      //当前页码数
      pageSize: 5,
      //总条数
      total: 0,

      //新增表单数据
      displayInsertForm: false,
      insertFormDate: {
        stype: "",
        scontend: "",
      },

      //主目录或菜单修改表单的层显示
      displayUpdateForm: false,
      updateFormDate: {
        stype: "",
        scontend: "",
      },

      //修改权限
      displayUpdatePermission: false,
      ids: [],

      //表单的规则
      formRules: {
        stype: [{ required: true, message: "请输入公告类型", trigger: "blur" }],
        scontend: [{ required: true, message: "请输入公告内容", trigger: "blur" }],
      },
    };
  },
  //页面加载时初始化表格数据
  created() {
    this.initTable();
  },
  methods: {
    open(roleName, msg) {
      const h = this.$createElement;
      this.$message({
        center: true,
        showClose: true,
        type: "success",
        message: h("Strong", null, [
          h("div", null, roleName + ":"),
          h("div", { style: "color: teal" }, msg),
        ]),
      });

      // this.$message({
      //     message: msg,
      //     center: true,
      //     type:"info",
      //     offset:10,
      //
      // });
    },

    //模糊查询
    search() {
      this.currentPage = 1;
      if (this.valueTime.length === 0) {
        this.startTime = "";
        this.endTime = "";
        this.initTable();
        return;
      }
      this.startTime = this.valueTime[0];
      this.endTime = this.valueTime[1];
      console.log(this.startTime + "......" + this.endTime);
      this.initTable();
    },
    //页头的点击条状
    goBack() {
      this.$router.push("/houHome");
    },

    //获取表格数据
    initTable() {
      const that = this;
      that.$http
        .post(
          "/syssystem/e-message?currentPage=" +
            this.currentPage +
            "&pageSize=" +
            this.pageSize +
            "&startTime=" +
            this.startTime +
            "&endTime=" +
            this.endTime
        )
        .then(function (response) {
          console.log(response);
          if (response.data.code === 2000) {
            that.tableData = response.data.data.records;
            that.total = response.data.data.total;
            that.$refs.souSuoForm.resetFields(); //将搜索框清空
          }
        });
    },
    //页数改变时
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initTable();
    },
    //每页数量发生改变时
    handleSizeChange(val) {
      this.pageSize = val;
      this.initTable();
    },
    //重置搜索表单
    resetForm(ref) {
      this.valueTime = [];
      this.startTime = "";
      this.endTime = "";
      this.initTable();
    },

    //删除按钮事件，执行删除
    del(row, id) {
      this.$confirm(`确定删除该${row.stype}吗？, 是否继续?`, "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        type: "warning",
      })
        .then(() => {
          this.$http.delete(`/syssystem/e-message/${id}`).then((resp) => {
            if (resp.data.code === 2000) {
              this.$message.success(resp.data.msg);
              this.initTable();
            } else this.$message.error(resp.data.msg);
            this.initTable();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "删除已取消",
          });
        });
    },
    //新增表单关闭前回调
    handleInsertClose() {
      this.displayInsertForm = false;
      this.insertFormDate = {};
      this.$refs.insertForm.resetFields();
    },
    //新增表单提交
    insertFormSubmit() {
      const that = this;
      this.insertFormSubmit.rid=sessionStorage.getItem("rid")
      console.log(sessionStorage.getItem("rid"));
      this.$refs.insertForm.validate((valid) => {
        if (valid) {
          that.$http
            .post(`/syssystem/e-message/sendMessage`, qs.stringify(this.insertFormDate))
            .then(function (resp) {
              if (resp.data.code === 2000) {
                that.initTable();
                that.$message.success(resp.data.msg);
                that.handleInsertClose();
              } else {
                that.$message.error(resp.data.msg);
              }
            });
        }
      });
    },
    //修改按钮
    update(row) {
      this.updateFormDate = row;
      this.displayUpdateForm = true;
    },
    //主目录或菜单修改表单关闭回调
    handleUpdateClose() {
      this.displayUpdateForm = false;
      this.$refs.updateForm.resetFields();
    },
    //修改表单提交事件
    updateFormSubmit() {
      this.$refs.updateForm.validate((valid) => {
        if (valid) {
          this.$http
            .post(`/syssystem/e-message/sendMessage`, qs.stringify(this.updateFormDate))
            .then((resp) => {
              if (resp.data.code === 2000) {
                this.$message.success(resp.data.msg);
                this.initTable();
                this.handleUpdateClose();
              } else {
                this.$message.error(resp.data.msg);
              }
            });
        }
      });
    },
  },
};
</script>
<style>
/*修改图标*/
.el-message .el-icon-success {
  font-size: 25px;
}
/*修改字体*/
.el-message {
  font-size: 15px;
  font-weight: 700;
}
</style>
