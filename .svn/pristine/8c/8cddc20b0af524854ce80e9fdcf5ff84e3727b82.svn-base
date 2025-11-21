<template>
	<view class="container">
		<view class="bg_box" :style="{paddingTop:(statusBarHeight+44)+'px'}">
			<image src="/static/bg/user_bg.png" mode=""></image>
		</view>
		<wxx-nav title="个人中心" backgroundColor="transparent" color="#fff"></wxx-nav>
		<view class="center_box flex">
			<!-- <uv-avatar src="https://via.placeholder.com/200x200.png/2878ff" :size="120"></uv-avatar> -->
			<view class="flex-1" style="margin-left: 32rpx;">
				<view class="name">{{userInfo.userName}}</view>
				<view class="num">工号：{{userInfo.userAccount}}</view>
			</view>
		</view>
		<navigator url="./info" class="link_item uv-border-bottom flex">
			<image src="/static/icon/icon_user.png" mode=""></image>
			<text class="flex-1">我的信息</text>
			<uv-icon name="arrow-right" :size="32" color="rgba(0, 0, 0, 0.45)"></uv-icon>
		</navigator>
		<navigator url="./resetPwd" class="link_item uv-border-bottom flex">
			<image src="/static/icon/icon_lock.png" mode=""></image>
			<text class="flex-1">修改密码</text>
			<uv-icon name="arrow-right" :size="32" color="rgba(0, 0, 0, 0.45)"></uv-icon>
		</navigator>
		<view class="link_item flex">
			<image src="/static/icon/icon_ver.png" mode=""></image>
			<text class="flex-1">版本号</text>
			<text class="version">V{{appVersion}}</text>
		</view>
		<view class="btn" @click="logout">退出登录</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				statusBarHeight:0,
				appVersion:'1.0.0'
			};
		},
		computed:{
			userInfo(){
				return this.$store.state.userInfo;
			}
		},
		onLoad() {
			const sys = uni.$uv.sys();
			this.statusBarHeight = sys.statusBarHeight;
			this.appVersion = sys.appVersion;
		},
		methods:{
			logout(){
				uni.removeStorageSync('token');
				this.$store.commit('SET_TOKEN','');
				this.$store.commit('SET_USERINFO',{});
				uni.reLaunch({
					url:'/pages/user/login'
				})
			}
		},
	}
</script>

<style lang="scss">
	.container{
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #F7FAFC;
		.bg_box{
			width: 100%;
			height:234rpx;
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
		.center_box{
			height: 234rpx;
			position: relative;
			z-index: 3;
			padding: 0 16rpx;
			.name{
				color: #FFFFFF;
				font-size: 38rpx;
				font-weight: bold;
			}
			.num{
				color: rgba(255, 255, 255, 0.85);
				font-size: 30rpx;
			}
		}
		.link_item{
			padding: 26rpx 16rpx;
			color: rgba(0, 0, 0, 0.85);
			font-size: 28rpx;
			background: #fff;
			image{
				width: 42rpx;
				height: 42rpx;
				margin-right: 16rpx;
			}
			.version{
				color: rgba(0, 0, 0, 0.45);
			}
		}
		.btn{
			margin-top: 16rpx;
			background: #fff;
			height: 100rpx;
			line-height: 100rpx;
			text-align: center;
			color: #F56C6C;
			font-size: 28rpx;
		}
	}
</style>
