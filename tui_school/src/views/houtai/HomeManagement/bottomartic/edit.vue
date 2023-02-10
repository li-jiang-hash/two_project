<template>
  <!--弹窗-->
  <el-dialog
    width="35%"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
  >
    <el-form :model="editformData" :rules="rules" ref="editformData" label-width="100px">
      <el-form-item label="导航名称：" prop="name">
        <el-input v-model="editformData.name" placeholder="请输入导航名称"></el-input>
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
        name: [
          {
            required: true,
            message: "请输入导航名称",
            trigger: "blur",
            autocomplete: "on",
          },
        ],
      },
    };
  },
  props: {
    // route object
    editformData: {
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
      this.editformData.sort = value;
    },
    handleClose(done) {
      this.$refs["editformData"].resetFields();
      this.$emit("close-callback");
    },
    submitForm() {
      var that = this;
      this.$refs["editformData"].validate((valid) => {
        if (!this.editformData.name) {
          this.$message({
            type: "error",
            message: "请输入导航名称",
          });
          return false;
        }
        if (valid) {
          this.$http
            .post(
              "/syssystem/tb-bottom-article/updBottom",
              qs.stringify(this.editformData)
            )
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
        }
      });
    },
  },
};
</script>
