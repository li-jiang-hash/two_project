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
        <el-table-column align="center" prop="ename" label="审查人"> </el-table-column>
        <el-table-column align="center" prop="sname" label="审查店铺"> </el-table-column>
        <el-table-column align="center" prop="gmtCreate" label="审查时间">
        </el-table-column>

        <el-table-column header-align="center" align="center" prop="state" label="状态">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.state == 1" class="el-icon-circle-close"
              >审查不合格</el-tag
            >
            <el-tag
              type="success"
              v-if="scope.row.state == 0"
              class="el-icon-refresh-left"
              >审查通过</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column align="center" fixed="right" label="操作" width="350">
          <template slot-scope="scope">
            <!--                        <el-button type="success"  size="medium " @click="update(scope.row)">修改</el-button>-->
            <el-button type="danger" size="medium" @click="del(scope.row, scope.row.id)"
              >删除</el-button
            >
            <el-button
              plain
              @click="open(scope.row.sname, scope.row.msg)"
              v-if="scope.row.state == 0"
              disabled
            >
              停业原因
            </el-button>
            <el-button
              plain
              @click="open(scope.row.sname, scope.row.msg)"
              v-if="scope.row.state == 1"
            >
              停业原因
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

      <!-- 审查新增-->
      <el-dialog
        title="审查店铺"
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
          <el-form-item label="审查店铺">
            <el-select v-model="insertFormDate.bid">
              <el-option
                v-for="item in business"
                :label="item.sname"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="state">
            <el-select v-model="insertFormDate.state" placeholder="请选择审查是否合格">
              <el-option label="审查合格" :value="0"></el-option>
              <el-option label="店铺整顿" :value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="整顿原因:" prop="remark" v-if="insertFormDate.state === 1">
            <el-input
              type="textarea"
              v-model="insertFormDate.msg"
              autocomplete="off"
              placeholder="请输入整顿原因"
            ></el-input>
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

      <!--            &lt;!&ndash;修改通知&ndash;&gt;-->
      <!--            <el-dialog-->
      <!--                    title="修改"-->
      <!--                    :visible.sync="displayUpdateForm"-->
      <!--                    width="28%"-->
      <!--                    :closed="handleUpdateClose">-->
      <!--                <el-form ref="updateForm" :model="updateFormDate" :rules="formRules" label-width="80px" @submit.native.prevent>-->
      <!--                    <el-form-item  label="公告类型" prop="insertFormDate.stype">-->
      <!--                        <el-input v-model="updateFormDate.stype" ></el-input>-->
      <!--                    </el-form-item>-->
      <!--                    <el-form-item  label="公告内容" prop="insertFormDate.scontend">-->
      <!--                        <el-input type="textarea" v-model="updateFormDate.scontend"></el-input>-->
      <!--                    </el-form-item>-->
      <!--                </el-form>-->
      <!--                <el-row style="margin-top:17px;">-->
      <!--                    <el-button style="float:right;margin-left:6px;" size="mini" type="danger" plain @click="handleUpdateClose()">取 消</el-button>-->
      <!--                    <el-button style="float:right" size="mini" type="primary"   @click="updateFormSubmit()">确 定</el-button>-->
      <!--                </el-row>-->
      <!--            </el-dialog>-->
    </el-card>
  </div>
</template>

<script>
export default {
  name: "MenuList",
  data() {
    return {
      //所有店铺
      business: [],
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
        name: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
        path: [{ required: true, message: "请输入路由地址", trigger: "blur" }],
        permissionValue: [{ required: true, message: "请输入接口地址", trigger: "blur" }],
        type: [{ required: true, message: "请选择类型", trigger: "blur" }],
      },
    };
  },
  //页面加载时初始化表格数据
  created() {
    this.initTable();
    this.getBusiness();
  },
  methods: {
    //获取所有店铺
    getBusiness() {
      const that = this;
      that.$http.get("/syssystem/e-examine").then(function (response) {
        if (response.data.code === 2000) {
          that.business = response.data.data;
        }
      });
    },
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
          "/syssystem/e-examine?currentPage=" +
            this.currentPage +
            "&pageSize=" +
            this.pageSize +
            "&startTime=" +
            this.startTime +
            "&endTime=" +
            this.endTime
        )
        .then(function (response) {
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
      this.$confirm(`确定删除该条记录吗？, 是否继续?`, "提示", {
        cancelButtonText: "取消",
        confirmButtonText: "确定",
        type: "warning",
      })
        .then(() => {
          this.$http.post(`/emp/examine/deleteExamine/${id}`).then((resp) => {
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
      this.$refs.insertForm.validate((valid) => {
        if (valid) {
          that.$http
            .post(`/emp/examine/insertExamine`, this.insertFormDate)
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
    // //修改按钮
    // update(row) {
    //     this.updateFormDate = row;
    //     this.displayUpdateForm = true;
    //
    // },
    //主目录或菜单修改表单关闭回调
    handleUpdateClose() {
      this.displayUpdateForm = false;
      this.$refs.updateForm.resetFields();
    },
    // //修改表单提交事件
    // updateFormSubmit() {
    //     this.$refs.updateForm.validate(valid => {
    //         if (valid) {
    //             this.$http.post(`emp/message/updateMessage/`, this.updateFormDate).then((resp) => {
    //                 if (resp.data.code === 2000) {
    //                     this.$message.success(resp.data.msg);
    //                     this.initTable();
    //                     this.handleUpdateClose();
    //                 } else {
    //                     this.$message.error(resp.data.msg);
    //                 }
    //             })
    //         }
    //     })
    // }
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
