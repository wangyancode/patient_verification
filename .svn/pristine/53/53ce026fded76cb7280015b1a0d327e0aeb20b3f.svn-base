<template>
  <view class="flex_container padding-safe-area">
	<view class="bg_box" :style="{paddingTop:(statusBarHeight+44)+'px'}">
		<image src="/static/bg/index_bg.png" mode=""></image>
	</view>
    <wxx-nav title="首页" backgroundColor="transparent" color="#fff" :IsBacks="false"></wxx-nav>
	<image src="/static/imgs/img_banner.png" class="banner"></image>
	<view class="title_box flex">
		<view class="title flex-1">身份核对</view>
		<view>
			<picker :range="deptList" range-key="regionName" @change="tabDept">
				<view class="flex part_box">
					<text class="val">{{partName}}</text>
					<uv-icon name="arrow-down-fill" color="rgba(0, 0, 0, 0.45)" :size="20"></uv-icon>
				</view>
			</picker>
		</view>
	</view>
	<view class="flex-1 menu_box flex flex-wrap">
		<navigator url="./check" class="menu_item flex-y flex-xy-center">
			<image src="/static/imgs/img_work.png" mode=""></image>
			<view>患者身份核对</view>
		</navigator>
		<navigator url="/pages/user/center" class="menu_item flex-y flex-xy-center">
			<image src="/static/imgs/img_user.png" mode=""></image>
			<view>个人中心</view>
		</navigator>
	</view>
  </view>
</template>
<script>
  export default {
    data() {
      return {
		statusBarHeight:'',
		partName:'',
		deptList:[]
      }
    },
	computed:{
		userInfo(){
			return this.$store.state.userInfo;
		},
		deptCode() {
			return this.$store.state.deptCode;
		}
	},
	onLoad() {
		const sys = uni.$uv.sys();
		this.statusBarHeight = sys.statusBarHeight;
		this.deptList = this.userInfo.regionList;
		const tempList=this.userInfo.regionList.filter(el=>{return el.regionValue===this.deptCode})
		this.partName = tempList[0].regionName;
		this.$store.commit('SET_DEPTCODE',tempList[0].regionValue);
	},
    methods: {
		tabDept(e){
			const index = e.detail.value;
			this.partName = this.deptList[index].regionName;
			this.$store.commit('SET_DEPTCODE',this.deptList[index].regionValue);
		}
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
		height: 232rpx;
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
	.banner{
		width: 100%;
		height: 364rpx;
		flex-shrink: 0;
	}
	.title_box{
		padding:16rpx;
		.title{
			padding-left: 24rpx;
			border-left: 8rpx solid #1B86FF;
			color: rgba(0, 0, 0, 0.85);
			font-size: 32rpx;
			font-weight: bold;
		}
	}
	.part_box{
		background: #FFFFFF;
		border-radius: 16rpx;
		border: 2rpx solid rgba(0,0,0,0.06);
		padding: 10rpx 16rpx;
		.val{
			margin-right: 10rpx;
			color: rgba(0, 0, 0, 0.85);
			font-size: 28rpx;
		}
	}
	.menu_box{
		padding: 24rpx 16rpx 0;
		align-items: flex-start;
		justify-content: space-between;
		align-content: flex-start;
		overflow: auto;
		.menu_item{
			width: calc(50% - 16rpx);
			background: #fff;
			border: 2rpx solid rgba(0,0,0,0.06);
			border-radius: 16rpx;
			padding:40rpx 0;
			color: rgba(0, 0, 0, 0.65);
			font-size: 28rpx;
			line-height: 2;
			margin-bottom: 16rpx;
			image{
				width: 172rpx;
				height: 172rpx;
			}
		}
	}
</style>
