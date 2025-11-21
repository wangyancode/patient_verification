<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="12" :xs="24">
        <el-card class="box-card" style="height: 652px">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <el-row>
              <el-col :span="12">
                <div
                  class="text-center"
                  style="height: 200px; width: 200px; margin: 68px auto"
                >
                  <!--                  <userAvatar :user="user" />-->
                  <userAvatar
                    @saveImgUrl="saveImgUrl"
                    :logoUrl="logoUrl"
                    :key="timer"
                  />
                </div>
              </el-col>
              <el-col :span="12">
                <div class="name">{{ user.name }}</div>
                <!-- <div class="account">
                  <span class="label">职工ID:</span>
                  <span class="text">{{ user.account }}</span>
                </div> -->
                <div class="info" style="margin-top: 30px">
                  <svg-icon icon-class="jobNoByUser" class="icon" />
                  <div class="info_lable">
                    <span class="info_label_text">账号</span>:
                  </div>
                  <div class="pull-right">{{ user.userAccount }}</div>
                </div>
                <div class="info">
                  <svg-icon icon-class="sexByUser" class="icon" />
                  <div class="info_lable">
                    <span class="info_label_text">性别</span>:
                  </div>
                  <div class="pull-right">{{ user.userSexName }}</div>
                </div>
                <div class="info">
                  <svg-icon icon-class="rolesByUser" class="icon" />
                  <div class="info_lable">
                    <span class="info_label_text">所属角色</span>:
                  </div>
                  <div class="pull-right">
                    {{ user.roleNames || "超级管理员" }}
                  </div>
                </div>
                <div class="info">
                  <svg-icon icon-class="timeByUser" class="icon" />
                  <div class="info_lable">
                    <span class="info_label_text">创建时间</span>:
                  </div>
                  <div class="pull-right">{{ user.createDatetime }}</div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12" :xs="24">
        <el-card style="height: 652px">
          <div slot="header" class="clearfix">
            <span>密码修改</span>

            <div style="float: right">
              <div style="display: flex; align-items: center">
                <button
                  type="button"
                  aria-label="Close"
                  @click="close"
                  class="el-dialog__headerbtn"
                >
                  <i class="el-icon el-icon-close"></i>
                </button>
              </div>
            </div>
          </div>
          <!--          <el-tabs v-model="activeTab">-->
          <!--            <el-tab-pane label="基本资料" name="userinfo">-->
          <!--              <userInfo :user="user" />-->
          <!--            </el-tab-pane>-->
          <!--            <el-tab-pane label="修改密码" name="resetPwd">-->
          <resetPwd />
          <!--            </el-tab-pane>-->
          <!--          </el-tabs>-->
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userAvatar from "../../component/userAvatar.vue";
import userInfo from "./userInfo";
import resetPwd from "./resetPwd";
import { getPersonalInfo, updateAvatar } from "@/api/system/user";
import { mapGetters } from "vuex";

export default {
  name: "Profile",
  components: { userAvatar, userInfo, resetPwd },
  computed: {
    ...mapGetters(["avatar", "name", "userInfo"]),
  },
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      activeTab: "userinfo",
      timer: "",
      logoUrl: "",
    };
  },
  created() {
    this.getUser();
    // this.user = this.userInfo;
    // console.log(this.avatar)
    // console.log(this.name)
    // console.log(this.userInfo)
  },
  methods: {
    getUser() {
      getPersonalInfo().then((response) => {
        this.user = response.data;
        this.logoUrl = response.data.avatar;
        if (!response.data.avatar) {
          let defaultAvatar =
            this.user.userSex == "女"
              ? require("@/assets/images/avatar-nv.png")
              : require("@/assets/images/avatar-nan.png");
          this.logoUrl = defaultAvatar;
        }
        // this.roleGroup = response.roleGroup;
        // this.postGroup = response.postGroup;
        let string = [];
        if (this.user.roles) {
          this.user.roles.map((item) => {
            string.push(item.name);
          });
        }
        this.$set(this.user, "rolesStr", string.join(","));
      });
    },
    saveImgUrl(val) {
      this.timer = new Date().getTime();
      // this.addAppFormData.documentId = val.id;
      updateAvatar(val.documentId).then((res) => {});
      this.logoUrl = val.documentUrl;
    },
    close() {
      this.$tab.closePage();
    },
  },
};
</script>
<style lang="scss" scoped>
.name {
  height: 28px;
  font-size: 28px;
  font-family: Microsoft YaHei;
  font-weight: 400;
  color: #333333;
  line-height: 28px;
  margin-top: 68px;
}

.account {
  margin-top: 20px;
  margin-bottom: 35px;
  display: flex;

  .label {
    width: 80px;
    height: 14px;
    font-size: 16px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #808080;
    line-height: 26px;
    text-align-last: justify;
  }

  .text {
    height: 12px;
    margin-left: 10px;
    font-size: 16px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #333333;
    line-height: 26px;
  }
}

.info {
  width: 100%;
  height: 46px;
  display: flex;
  line-height: 26px;

  .icon {
    margin: 5px;
  }

  .info_lable {
    width: 80px;
    font-size: 16px;
    font-family: Microsoft YaHei;
    font-weight: 400;
    color: #808080;
    line-height: 26px;
    display: flex;

    .info_label_text {
      width: 80px;
      text-align-last: justify;
    }
  }

  .pull-right {
    margin-left: 50px;
  }
}

::v-deep .el-dialog__headerbtn {
  position: absolute;
  top: 15px;
  right: 20px;
  padding: 0;
  background: transparent;
  border: none;
  outline: none;
  cursor: pointer;
  font-size: 16px;
}
</style>
