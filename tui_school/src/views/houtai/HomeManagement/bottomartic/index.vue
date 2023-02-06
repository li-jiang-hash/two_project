<template>
  <div class="pad20">
    <el-card class="box-card">
      <el-form :inline="true" :model="formData" class="demo-form-inline" size="mini">
        <el-form-item label="分类名称">
          <el-input v-model="formData.name" placeholder="请输入分类标题"></el-input>
        </el-form-item>
        <el-form-item label="状态：">
          <el-select
            v-model="formData.isDisable"
            class="auto-width"
            clearable
            filterable
            placeholder="状态"
            style="width: 85px"
          >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="handleCheck"
            >查询</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="handleReset"
            >重置</el-button
          >
          <el-button
            type="primary"
            icon="el-icon-circle-plus-outline"
            size="mini"
            @click="handleAddSubclass(0)"
            >添加</el-button
          >
        </el-form-item>
      </el-form>
      <div>
        <el-table
          :data="tableData"
          style="width: 100%"
          v-loading="ctrl.loading"
          border
          row-key="id"
          :tree-props="{ children: 'children' }"
          :default-expand-all="false"
        >
          <el-table-column align="center" type="index" label="序号" width="100">
          </el-table-column>
          <el-table-column prop="id" v-if="false" label="id号" width="50">
          </el-table-column>
          <el-table-column prop="name" label="导航名称"> </el-table-column>
          <el-table-column width="170" prop="isDisable" label="状态" align="center">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.isDisable"
                @change="changeStatus(scope.row.id, scope.row.isDisable)"
                active-value="1"
                inactive-value="0"
                active-color="#ff4949"
                inactive-color="#13ce66"
                active-text="禁用"
                inactive-text="启用"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="340" align="center">
            <template slot-scope="scope">
              <ul class="list-item-actions">
                <li>
                  <el-button
                    type="danger"
                    @click="handleDelRow(scope.row.id)"
                    :disabled="scope.row.isDisable == 0"
                    size="mini"
                    >删除</el-button
                  >
                  <el-button
                    type="success"
                    @click="handleUpdateRow(scope.row)"
                    size="mini"
                    >修改</el-button
                  >
                  <el-button
                    type="primary"
                    icon="el-icon-circle-plus-outline"
                    size="mini"
                    v-if="scope.row.parentId == '0'"
                    @click="handleAddSubclass(scope.row.id)"
                    >添加</el-button
                  >
                  <el-button
                    type="primary"
                    size="mini"
                    v-if="scope.row.parentId !== '0'"
                    @click="handleArticala(scope.row.id)"
                    >文章管理</el-button
                  >
                </li>
              </ul>
            </template>
          </el-table-column>
        </el-table>
        <el-divider><i class="el-icon-moon-night"></i></el-divider>
        <div class="mgt20">
          <el-pagination
            background
            style="float: right; margin-top: 20px; margin-bottom: 22px"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[1, 5, 10, 15]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </div>
      </div>
      <edit
        :visible="ctrl.dialogVisible"
        :editformData="editformData"
        :title="ctrl.dialogTitle"
        @close-callback="closeCallback"
      ></edit>
      <add
        :visible="ctrl.addDialogVisible"
        :addformData="addformData"
        :title="ctrl.dialogTitle"
        @close-callback="closeCallback"
      ></add>
    </el-card>
  </div>
</template>
<script>
import Add from "./add";
import Edit from "./edit";
import qs from "qs";
export default {
  components: { Edit, Add },
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      //总条数
      total: 0,
      opts: {
        statusIdList: [],
      },
      // 页面控制数据，例如形式弹窗，显示加载中等
      ctrl: {
        loading: false,
        remoteAuthorLoading: false,
        dialogVisible: false,
        addDialogVisible: false,
      },
      //下拉选项框的数据
      options: [
        {
          value: 0,
          label: "正常",
        },
        {
          value: 1,
          label: "禁用",
        },
      ],
      // 表单数据, 例如新增编辑子项，页面表单
      // 条件筛选参数
      map: {},
      formData: {},
      tableData: [],
      addformData: {},
      editformData: {},
      list: [],
    };
  },
  created() {
    this.init();
  },
  methods: {
    handleCheck() {
      var that = this;
      this.$http
        .post(
          // `/home/bottomArticle/getAllfurryBottomArticle/${this.currentPage}/${this.pageSize}`,
          "/syssystem/tb-bottom-article?currentPage=" +
            this.currentPage +
            "&pageSize=" +
            this.pageSize,
          qs.stringify(this.formData)
        )
        .then(function (resp) {
          if (resp.data.code === 2000) {
            that.tableData = resp.data.data.records;
            that.total = resp.data.data.total;
          }
          if (resp.data.code === 5000) {
            that.$message({
              message: resp.data.msg,
              type: "error",
            });
            that.tableData = [];
          }
        });
    },
    handleArticala(id) {
      this.$router.push({ path: "/home/foottext/detail", query: { id: id } });
    },

    //改变当前页数
    handleSizeChange(val) {
      this.pageSize = val;
      this.init();
    },
    //改变当前每页的条数
    handleCurrentChange(val) {
      this.currentPage = val;
      this.init();
    },
    //初始化表格
    init() {
      var that = this;
      this.$http
        .post(
          //   `/home/bottomArticle/getAllBottomArticle/${this.currentPage}/${this.pageSize}`
          "/syssystem/tb-bottom-article?currentPage=" +
            this.currentPage +
            "&pageSize=" +
            this.pageSize,
          qs.stringify(this.formData)
        )
        .then(function (resp) {
          if (resp.data.code === 2000) {
            that.tableData = resp.data.data.records;
            that.total = resp.data.data.total;
          }
          if (resp.data.code === 5000) {
            that.$message({
              message: resp.data.msg,
              type: "error",
            });
            that.tableData = [];
          }
        });
    },
    //新增
    handleAddSubclass(id) {
      this.addformData.parentId = id;
      this.ctrl.dialogTitle = "添加";
      this.ctrl.addDialogVisible = true;
    },
    //编辑
    handleUpdateRow(data) {
      this.editformData = data;
      this.ctrl.dialogTitle = data.name + " —— 编辑";
      this.ctrl.dialogVisible = true;
    },
    // 关闭编辑弹窗回调
    closeCallback() {
      this.ctrl.dialogVisible = false;
      this.ctrl.addDialogVisible = false;
      this.init();
    },
    //删除
    handleDelRow(id) {
      var that = this;
      this.$confirm(`确定删除这条数据?`, "我要删除", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.ctrl.loading = true;
          this.$http.delete(`/syssystem/tb-bottom-article/${id}`).then(function (resp) {
            that.ctrl.loading = false;
            if (resp.data.code === 2000) {
              that.$message({
                message: resp.data.msg,
                type: "success",
              });
            }
            if (resp.data.code === 5000) {
              that.$message({
                message: resp.data.msg,
                type: "error",
              });
            }
            that.init();
          });
        })
        .catch(() => {
          this.ctrl.loading = false;
        });
    },
    //改变状态
    changeStatus(id, isDisable) {
      var that = this;
      this.$http
        .post(`/syssystem/tb-bottom-article/updisable/${id}/${isDisable}`)
        .then(function (resp) {
          if (resp.data.code === 2000) {
            that.$message({
              message: resp.data.msg,
              type: "success",
            });
          } else {
            that.$message({
              message: resp.data.msg,
              type: "error",
            });
          }
          that.init();
        });
    },
    // 重置查询条件
    handleReset() {
      this.reload();
    },
    // 刷新当前页面
    reload() {
      this.formData = {};
      this.init();
    },
  },
};
</script>
