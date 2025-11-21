<template>
  <el-form
    ref="form"
    :model="user"
    :rules="rules"
    label-width="80px"
    style="margin-top: 50px; margin-left: 30px"
  >
    <el-form-item label="旧密码" prop="oldPassword" style="margin-bottom: 36px">
      <el-input
        v-model="user.oldPassword"
        placeholder="请输入旧密码"
        type="password"
        show-password
        style="width: 500px; height: 36px"
      />
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword" style="margin-bottom: 36px">
      <el-input
        v-model="user.newPassword"
        placeholder="请输入新密码"
        type="password"
        show-password
        style="width: 500px; height: 36px"
      />
    </el-form-item>
    <el-form-item
      label="确认密码"
      prop="confirmPassword"
      style="margin-bottom: 36px"
    >
      <el-input
        v-model="user.confirmPassword"
        placeholder="请确认新密码"
        type="password"
        show-password
        style="width: 500px; height: 36px"
      />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="medium" @click="submit">保存</el-button>
      <el-button type="danger" size="medium" @click="close">关闭</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updatePwd } from "@/api/system/user";
import store from "@/store";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined,
      },
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: "旧密码不能为空", trigger: "blur" },
          // 移除旧密码格式校验规则
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          // 修改新密码校验规则
          {
            pattern:
              /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/,
            message: "密码需包含大小写字母、数字、特殊符号，长度8-16位！",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          // 修改确认密码校验规则
          {
            pattern:
              /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/,
            message: "密码需包含大小写字母、数字、特殊符号，长度8-16位！",
            trigger: "blur",
          },
          { required: true, validator: equalToPassword, trigger: "blur" },
        ],
      },
    };
  },
  created() {
    // console.log('这么离谱的吗？我确实存在吗？')
  },
  methods: {
    submit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          updatePwd(this.user.oldPassword, this.user.newPassword).then(
            (response) => {
              if (response.code == 200) {
                this.$modal.msgSuccess("修改成功");
                setTimeout(() => {
                  this.$store.dispatch("LogOut").then(() => {
                    this.$router.push({ path: "/login" }).catch(() => {});
                  });
                }, 2500);
              } else {
                this.$modal.msgError(response.msg);
              }
            }
          );
        }
      });
    },
    close() {
      this.$tab.closePage();
    },
  },
};
</script>
