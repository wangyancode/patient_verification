<template>
  <div class="tooltip-container">
    <el-tooltip class="my-tooltip" :class="!showTooltip?'tooltip-cursor':''" :disabled="showTooltip" :content="text" placement="top">
      <div ref="tooltipBox" class="text-box">
        <span ref="tooltipItem" class="">{{text}}</span>
      </div>
    </el-tooltip>
  </div>
</template>

<script>
export default {
  name: "myTooltip",
  props: {
    text: {
      type: String,
      default: () => ""
    }
  },
  data(){
    return {
      showTooltip: true
    }
  },
  watch:{
    text:{
      handler(){
        this.$nextTick(()=>this.checkWidth());
      },
      immediate: true
    }
  },
  methods:{
    checkWidth(){
      const boxWidth = this.$refs['tooltipBox'].offsetWidth;
      const itemWidth = this.$refs['tooltipItem'].offsetWidth;
      this.showTooltip = boxWidth > itemWidth
    }
  }
};
</script>
<style lang="scss" scoped>
  .tooltip-container{
    width:100%;
    .text-box{
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
    .tooltip-cursor{
      cursor: pointer;
    }
  }
</style>