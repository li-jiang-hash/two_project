<template>
  <!--弹窗-->
  <el-dialog
    width="35%"
    :title="title"
    :visible.sync="visible"
    :before-close="handleClose"
  >
    <el-form :model="formData" :rules="rules" ref="formData" label-width="100px">
      <el-form-item label="广告图片:" prop="imageUrl">
        <div>
          <el-upload
            class="avatar-uploader"
            action="http://localhost:7500/syssystem/file/upload"
            :show-file-list="false"
            :headers="token"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img
              style="height: 50px; width: 200px"
              v-if="formData.imageUrl"
              v-model="formData.imageUrl"
              :src="formData.imageUrl"
              class="avatar"
              alt=""
            />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
      </el-form-item>
      <el-form-item label="广告标题:" prop="title">
        <el-input v-model="formData.title"></el-input>
      </el-form-item>
      <el-form-item label="广告链接:" prop="linkUrl">
        <el-input v-model="formData.linkUrl"></el-input>
      </el-form-item>
      <el-form-item label="广告排序:" prop="sort">
        <el-input-number
          style="width: 300px"
          v-model="formData.sort"
          @change="handleChange"
          :min="1"
          :max="1000"
          label="描述文字"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="开始时间:" prop="gmtCreate">
        <el-date-picker
          value-format="yyyy-MM-ddTHH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
          v-model="formData.gmtCreate"
          type="datetime"
          placeholder="选择日期时间"
          :picker-options="gmtCreate"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间:" prop="gmtModified">
        <el-date-picker
          value-format="yyyy-MM-ddTHH:mm:ss"
          format="yyyy-MM-dd HH:mm:ss"
          v-model="formData.gmtModified"
          type="datetime"
          placeholder="选择日期时间"
          :picker-options="gmtModified"
        >
        </el-date-picker>
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
      token: {
        token: sessionStorage.getItem("token"),
      },
      opts: {
        advTargetList: [],
      },
      imageUrl: "",
      formData: {
        imageUrl: "",
        title: "",
        linkUrl: "",
        sort: "",
        // gmtCreate: "",
        // gmtModified: "",
      },
      gmtCreate: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      },

      gmtModified: {
        disabledDate: (time) => {
          if (this.formData.gmtCreate != "") {
            return (
              time.getTime() < new Date(this.formData.gmtCreate).getTime() ||
              time.getTime() < Date.now()
            ); //禁用开始日期之前的日期
          }
        },
      },
      rules: {
        imageUrl: [
          {
            required: true,
            message: "请输入广告图片",
            trigger: "blur",
            autocomplete: "on",
          },
        ],
        title: [{ required: true, message: "请输入广告标题", trigger: "blur" }],
        linkUrl: [{ required: true, message: "请输入广告链接", trigger: "blur" }],
        sort: [{ required: true, message: "请输入广告排序", trigger: "blur" }],
        gmtCreate: [{ required: true, message: "请输入开始时间", trigger: "blur" }],
        gmtModified: [{ required: true, message: "请输入结束时间", trigger: "blur" }],
      },
    };
  },
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "",
    },
  },
  mounted() {},
  methods: {
    handleChange(value) {
      this.formData.sort = value;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    handleClose(done) {
      this.$refs["formData"].resetFields();
      this.$emit("close-callback");
    },
    handleAvatarSuccess(res, file) {
      //console.log(res)
      this.formData.imageUrl = res.data;
    },
    submitForm() {
      var that = this;
      console.log(this.formData);
      this.$refs.formData.validate((valid) => {
        if (valid) {
          this.$http
            .post("/syssystem/tb-banner/addBanner", qs.stringify(this.formData))
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
            message: "输入框不能为空",
            type: "error",
          });
        }
      });
    },
  },
};
</script>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 150px;
  height: 150px;
  line-height: 150px;
  text-align: center;
}
.avatar {
  width: 150px;
  height: 150px;
  display: block;
}
</style>
