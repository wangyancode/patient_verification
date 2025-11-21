<template>
  <view class="flex_container padding-safe-area">
	<view class="bg_box" :style="{height:(statusBarHeight+44)+'px'}">
		<image src="/static/bg/title_bg.png" mode=""></image>
	</view>
    <wxx-nav title="我的信息" backgroundColor="transparent" color="#fff"></wxx-nav>
	<view class="container-input flex-1">
		<!-- <view class="flex item_box uv-border-bottom">
		  <view class="input-label">头像</view>
		  <view class="flex-1 flex flex-between">
			  <uv-avatar src="https://via.placeholder.com/200x200.png/2878ff" :size="66"></uv-avatar>
			  <uv-icon name="arrow-right" :size="28" color="rgba(0, 0, 0, 0.45)"></uv-icon>
		  </view>
		</view> -->
		<view class="flex item_box uv-border-bottom">
		  <view class="input-label">姓名</view>
		  <text>{{userInfo.userName}}</text>
		</view>
		<view class="flex item_box uv-border-bottom">
		  <view class="input-label">性别</view>
		  <text>{{userInfo.userSex}}</text>
		</view>
		<view class="flex item_box uv-border-bottom">
		  <view class="input-label">科室</view>
		  <text>{{userInfo.regionName}}</text>
		</view>
		<view class="flex item_box">
		  <view class="input-label">工号</view>
		  <text>{{userInfo.userAccount}}</text>
		</view>
	</view>
  </view>
</template>
<script>
  export default {
    data() {
      return {
		
      }
    },
	computed:{
		userInfo(){
			return this.$store.state.userInfo||{};
		}
	},
	onLoad() {
		const sys = uni.$uv.sys();
		this.statusBarHeight = sys.statusBarHeight;
	},
    methods: {
      
    }
  }

</script>

<style lang="scss" scoped>
	.flex_container{
		background: #F7FAFC;
	}
	.bg_box{
		width: 100%;
		box-sizing: initial;
		position: absolute;
		top: 0;
		left: 0;
		image{
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
		}
	}
	.item_box{
		padding: 24rpx 16rpx;
		color: rgba(0, 0, 0, 0.65);
		font-size: 28rpx;
		background: #fff;
		.input-label{
			width: 150rpx;
			font-weight: bold;
			color: rgba(0, 0, 0, 0.85);
			margin-right: 10rpx;
			flex-shrink:0;
		}
	}
</style>
