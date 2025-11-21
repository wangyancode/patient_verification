<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";

export default {
  name: "App",
  components: { ThemePicker },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },

};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
body {
  padding-right: 0!important;
}
::v-deep .el-table th.el-table__cell.is-leaf {
    border-bottom: 0px solid #dfe6ec!important;
}
::v-deep .el-table::before {
    height: 0px;
}
</style>
