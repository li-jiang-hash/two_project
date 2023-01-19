<template>
  <!--弹窗-->
  <el-dialog
    width="35%"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
  >
    <el-form :model="formData" :rules="rules" ref="formData" label-width="100px">
      <el-form-item label="链接名称：" prop="linkName">
        <el-input v-model="formData.linkName"></el-input>
      </el-form-item>
      <el-form-item label="链接地址：" prop="linkUrl">
        <el-input v-model="formData.linkUrl"></el-input>
      </el-form-item>
    </el-form>
    <el-row style="margin-top: 17px">
      <el-button
        style="float: right; margin-left: 6px"
        size="mini"
        type="danger"
        plain
        @click="handleClose"
        >取 消</el-button
      >
      <el-button
        style="float: right"
        size="mini"
        type="primary"
        @click="submitForm('formData')"
        >确定</el-button
      >
    </el-row>
  </el-dialog>
</template>

<script>
import qs from "qs";
export default {
  name: "Edit",
  data() {
    return {
      ctrl: {
        dialogVisible: true,
      },
      form: {},
      rules: {
        linkUrl: [
          {
            required: true,
            message: "请输入链接地址",
            trigger: "blur",
            autocomplete: "on",
          },
        ],
        linkName: [{ required: true, message: "请输入链接名称", trigger: "blur" }],
      },
    };
  },
  props: {
    formData: {
      type: Object,
      default: () => {},
    },
    visible: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "",
    },
  },
  methods: {
    handleChange(value) {
      this.formData.sort = value;
    },
    handleClose(done) {
      this.$refs["formData"].resetFields();
      this.$emit("close-callback");
    },
    submitForm(formName) {
      var that = this;
      this.$refs[formName].validate((valid) => {
        if (!this.formData.linkUrl) {
          this.$message({
            type: "error",
            message: "请输入链接地址",
          });
          return false;
        }
        if (!this.formData.linkName) {
          this.$message({
            type: "error",
            message: "请输入链接名称",
          });
          return false;
        }
        if (valid) {
          this.$http
            .post("/syssystem/tb-link/addLink", qs.stringify(this.formData))
            .then(function (resp) {
              if (resp.data.code === 2000) {
                that.$message({
                  message: resp.data.msg,
                  type: "success",
                });
                that.handleClose("close-callback");
              }
              if (resp.data.code === 5000) {
                that.$message({
                  message: resp.data.msg,
                  type: "error",
                });
              }
            });
        } else {
          this.$message({
            type: "error",
            message: "提交失败",
          });
        }
      });
    },
  },
};
</script>
