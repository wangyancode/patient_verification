<template>
  <div class="sidebar-logo-container" :class="{ collapse: collapse }">
    <transition name="sidebarLogoFade">
      <!-- <router-link v-if="collapse" key="collapse" class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo">
      </router-link> -->
      <router-link class="sidebar-logo-link" to="/">
        <img v-if="logo" :src="logo" class="sidebar-logo-full" />
        <h1 class="sidebar-title">{{ hospitalName }}</h1>
        <el-divider direction="vertical" content-position="center"></el-divider>
        <h1 class="sidebar-title">{{ title }}</h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
import { getAuthConfig } from "@/api/login";
import { getPersonalInfo } from "@/api/system/user";

export default {
  name: "SidebarLogo",
  props: {
    collapse: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      logoImage: require("@/assets/images/menu_logo.png"),
      logo: require("@/assets/images/logo.png"),
      title: "医技检查身份核对系统",
      hospitalName: "",
    };
  },
  created() {
    // this.getConfigInfo()
  },
  methods: {
    getConfigInfo() {
      // getAuthConfig().then(res => {
      //   if (res.data.configServerInfoVO.hospitalName) {
      //     this.hospitalName = res.data.configServerInfoVO.hospitalName
      //   }
      //
      //   if (res.data.configServerInfoVO.orgLogoUrl) {
      //     this.logo = res.data.configServerInfoVO.orgLogoUrl
      //   }
      // })
      // getAuthConfig().then(res => {
      //   if (res.data.orgName) {
      //     this.hospitalName = res.data.orgName
      //   }
      //   if (res.data.orgLogo) {
      //     this.logo = res.data.orgLogo
      //   }
      // })
    },
  },
};
</script>

<style lang="scss" scoped>
.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: 64px;
  line-height: 64px;
  text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;
    display: flex;
    align-items: center;

    & .sidebar-logo-full {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-left: 25px;
    }

    & .sidebar-logo {
      width: 32px;
      height: 32px;
      vertical-align: middle;
      margin-right: 12px;
    }

    & .sidebar-title {
      display: inline-block;
      margin: 0 0 0 16px;
      color: #fff;
      // vertical-align: middle;
      // font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      // -webkit-box-reflect: below 0 -webkit-linear-gradient(transparent, transparent
      //       20%, rgba(255, 255, 255, 0.3));
      // font: 600 18px Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      // text-transform: uppercase;
      // letter-spacing: 3px;
      font-family: Alibaba PuHuiTi 3, Alibaba PuHuiTi 30;
      font-size: 24px;
      color: #ffffff;
      font-weight: normal;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}

::v-deep .el-divider--vertical {
  height: 30px;
  margin: 0;
  top: 2px;
  background: rgba(255, 255, 255, 0.12);
}
</style>
