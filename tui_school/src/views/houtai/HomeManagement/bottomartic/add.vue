<template>
  <!--弹窗-->
  <el-dialog
    width="30%"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
  >
    <el-form :model="addformData" :rules="rules" ref="addformData" label-width="100px">
      <el-form-item label="导航名称：" prop="navName">
        <el-input v-model="addformData.name" placeholder="请输入导航名称"></el-input>
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
      <el-button style="float: right" size="mini" type="primary" @click="submitForm()"
        >确定</el-button
      >
    </el-row>
  </el-dialog>
</template>
<script>
import qs from "qs";
export default {
  name: "Add",
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
    addformData: {
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
    handleClose(done) {
      this.$refs["addformData"].resetFields();
      this.$emit("close-callback");
    },
    submitForm() {
      var that = this;
      this.$refs["addformData"].validate((valid) => {
        if (valid) {
          this.$http
            .post(
              "/syssystem/tb-bottom-article/addBottom",
              qs.stringify(this.addformData)
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
