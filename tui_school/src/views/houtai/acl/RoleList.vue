<template>
  <div class="roleList">
    <!--卡片-->
    <el-card class="box-card">
      <el-form :inline="true" :model="formData" size="mini" @submit.native.prevent>
        <el-form-item label="角色名：">
          <el-input
            v-model="formData.roleName"
            placeholder="请输入角色名称"
            @keyup.enter.native="findUserBySouSuo"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态:">
          <el-select
            v-model="formData.status"
            class="auto-width"
            clearable
            filterable
            placeholder="状态"
            style="width: 85px"
          >
            <el-option
              v-for="item in statusIdList"
              :key="item.code"
              :label="item.desc"
              :value="item.code"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" type="primary" @click="findUserBySouSuo"
            >查询</el-button
          >
          <el-button icon="el-icon-refresh" class="filter-item" @click="reload"
            >重置</el-button
          >
          <el-button
            type="primary"
            icon="el-icon-circle-plus-outline"
            size="mini"
            @click="addFormVisible = true"
            >添加</el-button
          >
        </el-form-item>
      </el-form>

      <el-table :data="tableData" stripe border style="width: 100%">
        <el-table-column prop="roleName" label="角色名"> </el-table-column>
        <el-table-column prop="remark" label="备注"> </el-table-column>
        <el-table-column width="180" prop="status" align="center" label="状态">
          <template slot-scope="scope">
            <el-switch
              active-value="1"
              inactive-value="0"
              v-model="scope.row.status"
              @change="changeStatus(scope.row.id, scope.row.status)"
              inactive-color="#13ce66"
              active-color="#ff4949"
              active-text="禁用"
              inactive-text="正常"
            >
            </el-switch>
          </template>
        </el-table-column>

        <el-table-column>
          <template slot-scope="scope">
            <div v-if="scope.row.id === '1'">
              <el-button
                size="mini"
                style="width: 95%"
                type="success"
                @click="fenpeiPermissionDialog(scope.row.id)"
                disabled
                >分配权限</el-button
              >
            </div>
            <div v-else>
              <el-button type="success" size="mini" @click="updateRole(scope.row)"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="success"
                @click="fenpeiPermissionDialog(scope.row.id)"
                >分配权限</el-button
              >
              <el-button
                size="mini"
                disabled
                v-if="scope.row.status == 0"
                type="danger"
                @click="handleDelete(scope.$index, scope.row.id)"
                >删除</el-button
              >
              <el-button
                size="mini"
                v-else
                type="danger"
                @click="handleDelete(scope.$index, scope.row.id)"
                >删除</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
      <el-divider><i class="el-icon-moon-night"></i></el-divider>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="float: right; margin-top: 20px; margin-bottom: 22px"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="allTotal"
      >
      </el-pagination>

      <!--添加角色的弹出框表单-->
      <el-dialog
        title="添加角色"
        @closed="flushAll"
        width="30%"
        :visible.sync="addFormVisible"
      >
        <el-form
          :model="addForm"
          :rules="addFormRules"
          ref="addFormLabel"
          label-width="70px"
          @submit.native.prevent
          class="demo-ruleForm"
        >
          <el-form-item label="名称:" prop="roleName">
            <el-input
              @keyup.enter.native="addRole"
              type="text"
              v-model="addForm.roleName"
              autocomplete="off"
              placeholder="请输入用户名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="备注:" prop="remark">
            <el-input
              type="textarea"
              v-model="addForm.remark"
              autocomplete="off"
              placeholder="请输入角色备注"
            ></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="addForm.status" placeholder="请选择在职状态">
              <el-option label="正常" :value="0"></el-option>
              <el-option label="禁用" :value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="addRole">确 定</el-button>
        </div>
      </el-dialog>

      <!--修改角色弹窗-->
      <el-dialog
        width="30%"
        title="修改角色"
        @closed="flushUpdateAll"
        :visible.sync="updateFormVisible"
      >
        <el-form
          ref="updateFormLabel"
          :model="updateForm"
          :rules="updateFormRules"
          label-width="100px"
        >
          <el-form-item label="名称:" prop="roleName">
            <el-input v-model="updateForm.roleName"></el-input>
          </el-form-item>
          <el-form-item label="备注:">
            <el-input type="textarea" v-model="updateForm.remark"></el-input>
          </el-form-item>
        </el-form>
        <el-row style="margin-top: 17px">
          <el-button
            style="float: right; margin-left: 6px"
            size="mini"
            type="danger"
            plain
            @click="updateFormVisible = false"
            >取 消</el-button
          >
          <el-button
            style="float: right"
            size="mini"
            type="primary"
            @click="updateRoleById"
            >确 定</el-button
          >
        </el-row>
      </el-dialog>

      <!--分配权限的弹出框-->
      <el-dialog title="分配权限" :visible.sync="fenDialogVisible" width="25%">
        <!--树形菜单
                      data 树形的数据
                      default-expanded-keys 默认展开项
                      default-checked-keys 默认选中项-->
        <el-tree
          :data="data"
          show-checkbox
          ref="tree"
          node-key="id"
          highlight-current
          :default-expanded-keys="checkItems"
          :props="defaultProps"
        >
        </el-tree>

        <el-row style="margin-top: 17px">
          <el-button
            style="float: right; margin-left: 6px"
            size="mini"
            type="danger"
            plain
            @click="fenDialogVisible = false"
            >取 消</el-button
          >
          <el-button
            style="float: right"
            size="mini"
            type="primary"
            @click="confirmFenPermission"
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
  name: "RoleList",
  data() {
    return {
      //控制添加弹出对话框是否显示
      addFormVisible: false,
      //添加按钮弹出层表单数据
      addForm: {
        remark: "",
        roleName: "",
      },
      //新增用户的表单元素判断
      addFormRules: {
        roleName: [
          { required: true, message: "角色名不能为空" },
          { min: 2, max: 10, message: "账号的长度[2~10]", trigger: "blur" },
        ],
        remark: [
          { required: true, message: "备注不能为空" },
          { min: 2, max: 30, message: "备注的长度[2~30]", trigger: "blur" },
        ],
        status: [{ required: true, message: "至少选择一项" }],
      },

      //控制修改弹出对话框是否显示
      updateFormVisible: false,
      //修改按钮弹出层表单数据
      updateForm: {
        id: "",
        remark: "",
        roleName: "",
        roleCode: "",
      },
      //修改用户的表单元素判断
      updateFormRules: {
        roleName: [
          { required: true, message: "角色名不能为空" },
          { min: 2, max: 10, message: "账号的长度[2~10]", trigger: "blur" },
        ],
      },

      //搜索框搜索的内容
      formData: {
        roleName: "",
        isDeleted: "",
      },
      statusIdList: [
        {
          code: "1",
          desc: "禁用",
        },
        {
          code: "0",
          desc: "正常",
        },
      ],

      //当前页面--》page
      currentPage: 1,
      //每页几条--》limit
      pageSize: 5,
      //总条数
      allTotal: 0,
      //表格显示数据
      tableData: [],
      fenDialogVisible: false,
      //当前角色具有的所有权限的id集合
      checkItems: [],
      roleId: "",
      //所有的权限
      data: [],
      defaultProps: {
        children: "children",
        label: "pname",
      },
    };
  },
  created() {
    this.initRoleData();
  },
  methods: {
    //点击修改按钮时的事件
    updateRole(row) {
      this.updateForm = row;
      this.updateFormVisible = true;
    },

    //排序按钮的点击事件
    handleChange(value) {
      this.updateForm.roleCode = value;
    },

    //添加角色页面确定
    addRole() {
      var that = this;
      this.$refs.addFormLabel.validate((valid) => {
        //判断表单内是否有标红的地方
        if (valid) {
          //如果没有
          this.$http
            .post("syssystem/e-role/addRole", qs.stringify(this.addForm))
            .then(function (resp) {
              if (resp.data.code === 2000) {
                that.$message.success(resp.data.msg);
                that.initRoleData();
                that.addFormVisible = false; //设置成功后关闭弹出框
                that.flushAll();
              } else {
                that.$message.error(resp.data.msg);
                that.flushAll();
              }
            });
        } else {
          that.$message.error("添加有误");
        }
      });
    },

    //修改页面确认修改事件
    updateRoleById() {
      var that = this;
      this.$refs.updateFormLabel.validate((valid) => {
        //判断表单内是否有标红的地方
        if (valid) {
          this.$http
            .post("syssystem/e-role/addRole", qs.stringify(this.updateForm))
            .then(function (resp) {
              if (resp.data.code === 2000) {
                that.$message.success(resp.data.msg);
                that.initRoleData();
              } else {
                that.$message.error(resp.data.msg);
                that.initRoleData();
              }
              that.updateFormVisible = false;
            });
        } else {
          that.$message.error("修改有误");
        }
      });
    },

    //添加页面关闭时清空表单
    flushAll() {
      this.$refs.addFormLabel.resetFields();
    },

    //修改页面关闭时清空表单
    flushUpdateAll() {
      this.updateForm = {};
      this.$refs.updateFormLabel.resetFields();
      this.initRoleData();
    },

    //分页
    handleSizeChange(val) {
      this.pageSize = val;
      this.initRoleData();
    },
    //分页按钮被点击，发生改变时
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initRoleData();
    },

    //搜索按钮
    findUserBySouSuo() {
      this.initRoleData();
    },

    //删除角色的方法
    handleDelete(index, id) {
      this.$confirm(`确定要《《彻底删除》》该用户吗?`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var that = this;
          this.$http.delete(`/syssystem/e-role/${id}`).then(function (resp) {
            if (resp.data.code === 2000) {
              that.$message.success(resp.data.msg);
              that.initRoleData();
            } else {
              that.$message.error(resp.data.msg);
              that.initRoleData();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "删除已取消",
          });
        });
    },
    //确认分配权限
    confirmFenPermission() {
      //获取所有被选中的节点
      // var checkedNodes = this.$refs.tree.getCheckedNodes();
      // console.log(checkedNodes);

      var checkedNodesval = this.$refs.tree.getCheckedKeys();

      //获取半选的节点
      // var halfCheckedNodes = this.$refs.tree.getHalfCheckedNodes();
      var halfCheckedNodes = this.$refs.tree.getHalfCheckedKeys();
      console.log(halfCheckedNodes);
      //声明一个数组存放被选中的节点id
      // var menuids = [];
      //扩展运算符 ...
      var menuids = [...halfCheckedNodes, ...checkedNodesval];

      // checkedNodes.forEach((item, index) => {
      //   menuids.push(item.id);
      // });
      // halfCheckedNodes.forEach((item, index) => {
      //   menuids.push(item.id);
      // });

      this.$http
        .post(
          "/syssystem/r-role-permission/" + this.roleId,
          qs.stringify({ menuids: menuids.join(",") })
        )
        .then((result) => {
          this.fenDialogVisible = false;
          if (result.data.code === 2000) {
            this.$message.success(result.data.msg);
          }
          this.initRoleData();
        });
    },

    //初始化表格
    initRoleData() {
      var that = this;
      this.$http
        .post(
          "/syssystem/e-role?currentPage=" +
            this.currentPage +
            "&pageSize=" +
            this.pageSize,
          qs.stringify(this.formData)
        )
        .then(function (result) {
          that.tableData = result.data.data.records;
          console.log(result.data);
          that.allTotal = result.data.data.total;
          //不让搜索栏清空，点击重置才会刷新清空
          //   that.formData = {};
        });
    },

    //角色权限分配
    fenpeiPermissionDialog(roleId) {
      this.roleId = roleId;
      this.fenDialogVisible = true;
      //查询后台接口  所有的权限 以及当前用户具有的权限id
      this.$http.post("/syssystem/e-role/selectPermission/" + roleId).then((result) => {
        console.log(result);
        this.data = result.data.data.firstMenus;
        //默认选中的树的数据
        // setTimeout(() =>{
        //     result.data.result..forEach(item => {
        //         this.$refs.tree.setChecked(item, true, false);
        //     })}, 100)
        setTimeout(() => {
          result.data.data.permission.forEach((item) => {
            this.$refs.tree.setChecked(item, true, false);
          });
        }, 100);

        // this.checkItems=result.data.permission;
      });
    },

    //根据传入的id与转态修改用户状态
    changeStatus(id, status) {
      const title = { 0: "启用", 1: "禁用" };
      this.$confirm(`确定要《《${title[status]}》》该用户吗?`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          var that = this;
          this.$http
            .post(`/syssystem/e-role/upStatus/${id}/${status}`)
            .then(function (resp) {
              if (resp.data.code === 2000) {
                that.$message.success(resp.data.msg);
                that.initRoleData();
              } else {
                that.$message.error(resp.data.msg);
                that.initRoleData();
              }
            });
        })
        .catch(() => {
          this.initRoleData();
        });
    },

    // 刷新当前页面
    reload() {
      this.formData = {};
      this.initRoleData();
    },
  },
};
</script>

<style scoped></style>
