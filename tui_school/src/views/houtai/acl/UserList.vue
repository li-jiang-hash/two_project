<template>
  <div class="userList">
    <!--卡片-->
    <el-card class="box-card">
      <!--定义搜索-->
      <el-form
        :inline="true"
        :model="formData"
        class="demo-form-inline"
        @submit.native.prevent
      >
        <el-form-item size="mini">
          <el-input
            v-model="formData.ename"
            placeholder="请输入用户名称"
            @keyup.enter.native="findUserBySouSuo"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            round
            type="primary"
            @click="findUserBySouSuo"
            size="mini"
            icon="el-icon-search"
            style="margin-left: 10px; margin-right: 10px"
            >搜索</el-button
          >
          <el-button
            type="primary"
            icon="el-icon-refresh"
            size="mini"
            circle
            @click="handleReset"
          ></el-button>
          <el-button
            round
            type="primary"
            icon="el-icon-circle-plus-outline"
            size="mini"
            @click="xinzeng"
            >添加</el-button
          >
        </el-form-item>
      </el-form>

      <!--数据表格-->
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="ename" label="用户名" align="center"> </el-table-column>
        <el-table-column
          :formatter="dateFormat"
          prop="gmt_create"
          label="入职时间"
          align="center"
        >
        </el-table-column>
        <el-table-column :formatter="dateFormat" prop="gmt_modified" label="修改时间">
        </el-table-column>
        <el-table-column prop="telephone" label="电话号码" align="center">
        </el-table-column>
        <el-table-column prop="age" label="年龄" align="center"> </el-table-column>
        <el-table-column prop="role_name" label="角色" align="center">
        </el-table-column>
        <el-table-column header-align="center" align="center" prop="state" label="状态">
          <template slot-scope="scope">
            <el-tag
              type="primary"
              v-if="scope.row.state == 2"
              class="el-icon-circle-check"
            >
              冻结</el-tag
            >
            <el-tag type="danger" v-if="scope.row.state == 1" class="el-icon-circle-close"
              >离职</el-tag
            >
            <el-tag
              type="success"
              v-if="scope.row.state == 0"
              class="el-icon-refresh-left"
              >在职</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)"
              >编辑</el-button
            >
            <el-button
              size="mini"
              disabled
              v-if="scope.row.state == 0 || scope.row.state == 2"
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
            <!--                        <el-button size="mini" type="success" @click="fenRoleDialog(scope.row.id)">设置角色</el-button>-->
          </template>
        </el-table-column>
      </el-table>
      <el-divider><i class="el-icon-moon-night"></i></el-divider>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        style="float: right; margin-top: 20px; margin-bottom: 22px"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="allTotal"
      >
      </el-pagination>
    </el-card>

    <!--添加的弹出框表单-->
    <el-dialog
      :title="tableFormName"
      @closed="flushAll"
      width="30%"
      :visible.sync="dialogFormVisible"
    >
      <el-form
        :model="form"
        :rules="addFormRules"
        ref="formLabel"
        label-width="70px"
        @submit.native.prevent
        class="demo-ruleForm"
      >
        <!--                <span v-for="item in rolenames">-->
        <!--                    {{item}}-->
        <!--                </span>-->
        <el-form-item label="名称:" prop="ename">
          <el-input
            @keyup.enter.native="addUser"
            type="text"
            v-model="form.ename"
            autocomplete="off"
            placeholder="请输入用户名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="电话:" prop="telephone">
          <el-input
            @keyup.enter.native="addUser"
            type="text"
            v-model="form.telephone"
            autocomplete="off"
            placeholder="请输入用户电话"
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄:" prop="age">
          <el-input
            @keyup.enter.native="addUser"
            type="text"
            v-model="form.age"
            autocomplete="off"
            placeholder="请输入用户年龄"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择在职状态">
            <el-option label="在职" :value="0"></el-option>

            <el-option label="冻结" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="form.rid">
            <el-option
              v-for="item in rolenames"
              :label="item.roleName"
              :value="item.id"
            ></el-option>
            <!-- v-if="item.id != 1" -->
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </div>
    </el-dialog>

    <!--修改用户的对话框-->
    <el-dialog
      title="修改用户"
      :visible.sync="updateUserDialog"
      width="30%"
      @closed="closeUpdateUser"
    >
      <!--表单的数据绑定-->
      <el-form
        :model="updateUserFormData"
        @submit.native.prevent
        :rules="updateUserFormRule"
        ref="editerRef"
        label-width="70px"
        class="demo-ruleForm"
      >
      <el-table-column prop="updateUserFormData.id" v-if="false" label="编号"></el-table-column>
        <el-form-item label="用户名:" prop="ename">
          <el-input
            type="text"
            v-model="updateUserFormData.ename"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="电话:" prop="telephone">
          <el-input
            type="text"
            v-model="updateUserFormData.telephone"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="年龄:" prop="age">
          <el-input
            type="text"
            v-model="updateUserFormData.age"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="updateUserFormData.state" placeholder="请选择在职状态">
            <el-option label="在职" :value="0"></el-option>
            <el-option label="离职" :value="1"></el-option>
            <el-option label="冻结" :value="2"></el-option>
           
          </el-select>
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="updateUserFormData.rid" > 
            <el-option
              v-for="item in rolenames"
              :label="item.roleName"
              :value="item.id+''"
              
            ></el-option>
            <!-- v-if="item.id != 1" -->
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="quxiao">取 消</el-button>
        <el-button type="primary" @click="updateUser">确 定</el-button>
      </span>
    </el-dialog>

    <!--修改权限的窗口-->
    <!--        <el-dialog-->
    <!--                width="60%"-->
    <!--                title="修改权限"-->
    <!--                :visible.sync="visible"-->
    <!--                @closed="closeRoleSou"-->
    <!--                >-->
    <!--            <div class="pad20">-->
    <!--                <div>-->
    <!--                    <el-form :inline="true" size="mini" @submit.native.prevent>-->
    <!--                        <el-form-item label="角色：">-->
    <!--                            <el-input v-model="roleFormData.roleName" @keyup.enter.native="findRole"></el-input>-->
    <!--                        </el-form-item>-->
    <!--                        <el-form-item>-->
    <!--&lt;!&ndash;                            <el-button type="primary" :loading="ctrl.load">查询</el-button>&ndash;&gt;-->
    <!--                            <el-button type="primary" @click="findRole">查询</el-button>-->
    <!--                            <el-button class="filter-item" @click="restRole">重置</el-button>-->
    <!--                            <el-button type="success"  size="mini" @click="confirmFenRole">确定角色</el-button>-->
    <!--                        </el-form-item>-->
    <!--                    </el-form>-->
    <!--                </div>-->
    <!--                &lt;!&ndash;权限表格&ndash;&gt;-->
    <!--                <div>-->
    <!--                    <el-table-->
    <!--                            :data="roles"-->
    <!--                            border-->
    <!--                            ref="multipleTable"-->
    <!--                            :row-key="getRowKeys"-->
    <!--                            :reserve-selection="true"-->
    <!--                            @selection-change="handleSelectionChange"-->
    <!--                            style="width: 100%">-->
    <!--                        <el-table-column-->
    <!--                                prop="roleName"-->
    <!--                                label="角色名称"-->
    <!--                                align="center"-->
    <!--                                >-->
    <!--                        </el-table-column>-->
    <!--                        <el-table-column-->
    <!--                                prop="remark"-->
    <!--                                label="备注"-->
    <!--                                align="center"-->
    <!--                                >-->
    <!--                        </el-table-column>-->
    <!--                        <el-table-column-->
    <!--                                         prop="isDeleted"-->
    <!--                                         align="center"-->
    <!--                                         label="状态">-->
    <!--                            <template slot-scope="scope">-->
    <!--                                    <span v-html="scope.row.isDeleted===0?'启用':'禁用'"></span>-->
    <!--                            </template>-->
    <!--                        </el-table-column>-->
    <!--                        <el-table-column  align="center" width="60" :reserve-selection="true" type="selection" prop="id" :selectable='selectInit'></el-table-column>-->
    <!--                    </el-table>-->
    <!--                    <el-divider><i class="el-icon-loading"></i></el-divider>-->

    <!--                    <el-pagination-->
    <!--                            background-->
    <!--                            style="float: right;"-->
    <!--                            @size-change="handleSizeChange2"-->
    <!--                            @current-change="handleCurrentChange2"-->
    <!--                            :current-page="roleCurrentPage"-->
    <!--                            :page-size="rolePageSize"-->
    <!--                            :page-sizes="[5, 10, 15]"-->
    <!--                            layout="total, sizes, prev, pager, next, jumper"-->
    <!--                            :total="roleAllTotal">-->
    <!--                    </el-pagination>-->
    <!--                </div>-->
    <!--                <br/>-->
    <!--                <br/>-->
    <!--            </div>-->
    <!--        </el-dialog>-->
  </div>
</template>

<script>
import qs from "qs";
export default {
  name: "UserList",
  created() {
    this.initTable();
    this.getRole();
  },
  data() {
    return {
      rolenames: [],
      //控制权限的弹框显示
      visible: false,

      checkAll: false, //全选
      checkedRoles: [], //用户具有的权限
      roles: [], //查询具有的所有权限
      allRoles: [],
      userId: "", //用户id
      isIndeterminate: true,

      roleFormData: {
        //权限搜索框的内容
        roleName: "",
      },

      //当前页面--》page
      roleCurrentPage: 1,
      //每页几条--》limit
      rolePageSize: 5,
      //总条数
      roleAllTotal: 0,

      idlist: [],

      //新增用户的表单元素判断
      addFormRules: {
        ename: [
          { required: true, message: "用户名不能为空" },
          { min: 2, max: 10, message: "名称的长度[2~10]", trigger: "blur" },
        ],
        telephone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          {
            pattern: /(^((\+86)|(86))?(1[3-9])\d{9}$)|(^(0\d{2,3})-?(\d{7,8})$)/,
            message: "输入的手机号码格式不正确，请重新输入",
            trigger: "blur",
          },
        ],
        age: [
          { required: true, message: "年龄不能为空" },
          { min: 2, max: 2, message: "年龄的长度[2]位", trigger: "blur" },
        ],
        state: [{ required: true, message: "状态不能为空" }],
      },
      //控制修改框的是否显示
      updateUserDialog: false,
      //修改用户的表单数据
      updateUserFormData: {},
      //修改的表单校验规则
      updateUserFormRule: {
        ename: [
          { required: true, message: "用户名不能为空" },
          { min: 2, max: 10, message: "名称的长度[2~10]", trigger: "blur" },
        ],
        telephone: [
          { required: true, message: "请输入手机号码", trigger: "blur" },
          {
            pattern: /(^((\+86)|(86))?(1[3-9])\d{9}$)|(^(0\d{2,3})-?(\d{7,8})$)/,
            message: "输入的手机号码格式不正确，请重新输入",
            trigger: "blur",
          },
        ],
        age: [{ required: true, message: "年龄不能为空" }],
        state: [{ required: true, message: "状态不能为空" }],
      },

      //当前页面--》page
      currentPage: 1,
      //每页几条--》limit
      pageSize: 5,
      //总条数
      allTotal: 0,
      tableFormName: "",
      tableData: [
        {
          rid: "",
        },
      ],
      formData: {
        ename: "",
      },

      //控制添加弹出对话框是否显示
      dialogFormVisible: false,
      //添加按钮弹出层表单数据
      form: {
        ename: "",
      },
      checkedrow:[],
      startDate: {
          disabledDate(time) {
              return time.getTime() > Date.now();
          },
      },
      
      
      endDate: {
          disabledDate :(time)=> {
              if (this.formData.startDate!='')
                  return time.getTime() < new Date(this.formData.startDate).getTime() || time.getTime() > Date.now();
          },
      },
    };
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
    quxiao() {
      this.updateUserDialog = false;
      this.updateUserFormData = {};
      this.initTable();
    },
    // getRowKeys(row) {
    //     return row.id;
    // },

    // created() {
		// 	this.initTable();
		// 	// 查询 班级的信息   /tab-class
		//   ax.get("/syssystem/e-role/selectRoleAll").then(r=>{
		// 	console.log(r.data.t);
		// 	// 将班级的信息  赋值给classList
		// 	this.rolenames=r.data.data;
		// })
		// },
    //获取角色
    getRole() {
      this.$http.post("/syssystem/e-role/selectRoleAll").then((result) => {
        this.rolenames = result.data.data;
        console.log(this.rolenames);
      });
    },
    // //确认分配角色
    // confirmFenRole(){
    //     const idlist = [];
    //     this.checkedRoles.map(item => { // 遍历获取已选中的角色id
    //         idlist.push(item.id)
    //     })
    //     var that=this;
    //     this.$http.post("/system/role/fenUserRole",{"userId":this.userId,"roleIds":idlist}).then(function(resp){
    //         if (resp.data.code===2000){
    //             that.$message.success(resp.data.msg);
    //         }else {
    //             that.$message.error(resp.data.msg);
    //         }
    //         that.visible=false;
    //     });
    // },

    // // 选择添加的角色
    // handleSelectionChange(val) {
    //     this.checkedRoles=val;
    //
    // },

    //             selectInit(row,index){
    // //在这里一定要记得类型匹配的上。我就是入了一个小坑，就是类型判断不一定，导致浪费了好久。eg:'false'==false是不相等的，我就栽在了这个上面。可能因为我是小菜鸟吧。在此写一下，希望看到我的文章的朋友们可以避免这个问题。
    //                 if(row.isDeleted===1){
    //                     return false  //不可勾选
    //                 }else{
    //                     return true  //可勾选
    //                 }
    //             },

    //点击设置角色按钮,加载所有角色及用户具有的角色
    // fenRoleDialog(userId){
    //     this.visible=true;
    //     this.userId=userId;
    //     var that=this;
    //         this.$http.get( `/system/role/findRoleByUserId/${userId}`).then(function(resp){
    //             if (resp.data.code===2000){
    //                 that.allRoles=resp.data.result.allRole;
    //                 that.checkedRoles=resp.data.result.userRoleIds;
    //                 that.findRole();//分页查询
    //                 // that.setSelect();//数据回显
    //             }
    //         });
    //
    // },

    //用于权限的会回显设置
    // setSelect() {
    //         this.allRoles.map(item=>{
    //             if (this.checkedRoles.indexOf(item.id)!==-1){
    //                 this.$nextTick(() => {
    //                     this.$refs.multipleTable.toggleRowSelection(item) // 回显
    //                 })
    //
    //             }
    //         })
    // },

    //关闭设置角色页面时，清空数据
    // closeRoleSou(){
    //   this.roleFormData.roleName="";
    //   this.checkedRoles=[];
    //   this.rolePageSize=5;//每页几条--》limit
    //   this.roleCurrentPage=1;//当前页面--》page
    //   this.$refs.multipleTable.clearSelection();
    // },

    //点击重置按钮时，
    // restRole(){
    //     this.rolePageSize=5;//每页几条--》limit
    //     this.roleCurrentPage=1;//当前页面--》page
    //     this.findRole();
    // },

    /*根据id删除用户
     * index:表格中的序号。。0开始
     * id:用户id
     * */
    handleDelete(index, id) {
      this.$confirm(`确定要《《彻底删除》》该用户吗?`, {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(() => {
          var that = this;
          this.$http.delete(`/syssystem/e-emp-info/delEmp/${id}`).then(function (resp) {
            if (resp.data.code === 2000) {
              that.$message.success(resp.data.msg);
              that.initTable();
            } else {
              that.$message.error(resp.data.msg);
            }
          });
          this.reload();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "删除已取消",
          });
        });
    },

    //修改用户信息
    handleEdit(index, row) {
      this.updateUserFormData = row; //将当前行的数据放入

      this.updateUserDialog = true; //开启弹出框
    },
    //新增
    xinzeng() {
      this.tableFormName = "新增用户";
      this.dialogFormVisible = true;
      this.form = {};
    },

    //添加用户信息
    addUser() {
      var that = this;
      this.$refs.formLabel.validate((valid) => {
        //判断表单内是否有标红的地方
        if (valid) {
          this.$http
            .post("/syssystem/e-emp-info/insertEmp", qs.stringify(this.form))
            .then(function (resp) {
              console.log(resp.data);
              if (resp.data.code === 2000) {
                that.$message.success(resp.data.msg);
                that.initTable();
                that.dialogFormVisible = false; //设置成功后关闭弹出框
                that.flushAll();
              } else {
                that.$message.error(resp.data.msg);
                that.flushAll();
                that.form = {};
              }
            });
        } else {
          that.$message.error("添加有误");
        }
      });
    },

    //修改用户信息
    updateUser() {
      var that = this;
      // console.log(this.updateUserFormData);
      this.$refs.editerRef.validate((valid) => {
        //判断表单内是否有标红的地方
        if (valid) {
          this.$http
            .post(
              '/syssystem/e-emp-info/updEmp',
              qs.stringify(this.updateUserFormData)
            )
            .then(function (resp) {
              if (resp.data.code === 2000) {
                that.$message.success(resp.data.msg);
                that.initTable();
              } else {
                that.$message.error(resp.data.msg);
              }
              that.updateUserDialog = false;
            });
        } else {
          that.$message.error("修改有误");
        }
      });
    },

    //加载表格数据
    initTable() {
      var that = this;
      this.$http
        .post(
          "/syssystem/e-emp-info?currentPage=" +
            this.currentPage +
            "&pageSize=" +
            this.pageSize,
          qs.stringify(this.formData)
        )
        .then(function (resp) {
          that.tableData = resp.data.data.records;
          console.log(that.tableData);
          // for (var i=0;i<resp.data.data.records.length;i++){
          //     that.rolenames[i] = resp.data.data.records[i].role
          // }console.log(that.rolenames)
          that.allTotal = resp.data.data.total;
          // that.formData = {};
        });
    },

    //关闭时清空表单
    flushAll() {
      this.$refs.formLabel.resetFields();
    },
    // 重置查询条件
    handleReset() {
      this.reload();
    },
    // 刷新当前页面
    reload() {
      this.formData = {};
      this.initTable();
    },
    //关闭时清空表单
    closeUpdateUser() {
      // this.updateUserFormData = {}
      // this.$refs.editerRef.resetFields();
      // this.initTable();
    },

    //搜索按钮
    findUserBySouSuo() {
      this.initTable();
    },

    // //权限弹出框中搜索按钮
    // findRole(){
    //     var that=this;
    //     this.$http.post( `/system/role/findRoleByRoleName/${this.roleCurrentPage}/${this.rolePageSize}`,this.roleFormData).then(function(resp){
    //         that.roles=resp.data.result.records;
    //         that.roleAllTotal=resp.data.result.total;
    //         that.setSelect();
    //         that.roleFormData.roleName="";
    //
    //     });
    // },

    //分页
    handleSizeChange(val) {
      this.pageSize = val;
      this.initTable();
    },
    //分页按钮被点击，发生改变时
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initTable();
    },

    // //角色弹框的分页
    // handleSizeChange2(val) {
    //     this.rolePageSize=val;
    //     this.findRole();
    // },
    // //角色弹框分页按钮被点击，发生改变时
    // handleCurrentChange2(val) {
    //     this.roleCurrentPage=val;
    //     this.findRole();
    // },

    //根据传入的id与转态修改用户状态
    // changeStatus(id,deleted){
    //     const title = { 0: '启用', 1: '禁用' }
    //     this.$confirm(`确定要《《${title[deleted]}》》该用户吗?`, {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning'
    //     }).then(() => {
    //         var that=this;
    //         this.$http.post(`/system/user/updateUserStatusByid/${id}/${deleted}`).then(function (resp) {
    //             if (resp.data.code===2000){
    //                 that.$message.success(resp.data.msg);
    //                 that.initTable();
    //             }else {
    //                 that.$message.error(resp.data.msg);
    //             }
    //         })
    //         this.reload()
    //     }).catch(() => {
    //         this.reload()
    //     })
    // }
  },
};
</script>

<style>
#maxdd {
  background-color: rgba(255, 255, 255, 0.5);
}
/*解决elementUI中表格格子不对齐的问题*/
.el-table th.gutter {
  display: table-cell !important;
}

.el-form-item__content {
  line-height: 0px;
  position: relative;
  font-size: 14px;
}
</style>
