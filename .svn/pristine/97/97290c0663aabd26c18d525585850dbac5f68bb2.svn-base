<template>
	<view class="flex_container padding-safe-area">
		<view class="bg_box" :style="{height:(statusBarHeight+44)+'px'}">
			<image src="/static/bg/title_bg.png" mode=""></image>
		</view>
		<wxx-nav title="修改密码" backgroundColor="transparent" color="#fff"></wxx-nav>
		<view class="container-input flex-1">
			<view class="flex item_box" style="margin-bottom: 16rpx;">
				<view class="input-label">当前密码</view>
				<uv-input placeholder="请输入" border="none" class="flex-1" v-model="pwd.oldPassword" type="password" />
			</view>
			<view class="flex item_box uv-border-bottom">
				<view class="input-label">新密码</view>
				<uv-input placeholder="请输入" border="none" class="flex-1" v-model="pwd.newPassword" type="password" />
			</view>
			<view class="flex item_box">
				<view class="input-label">确认新密码</view>
				<uv-input placeholder="请输入" border="none" class="flex-1" v-model="pwd.rePassword" type="password" />
			</view>
			<view class="tips">
				<text>提示:\n密码需混合大小写字母、数字和特殊字符中的两类以上，且不少于8个字符。</text>
			</view>
		</view>
		<view class="buttonStyle">
			<uv-button type="primary" shape="circle" @click="formSubmit">确认修改</uv-button>
		</view>
	</view>

</template>
<script>
	export default {
		data() {
			return {
				statusBarHeight: '',
				pwd: {
					oldPassword: '',
					newPassword: '',
					rePassword: ''
				}
			}
		},
		onLoad() {
			const sys = uni.$uv.sys();
			this.statusBarHeight = sys.statusBarHeight;
		},
		methods: {
			formSubmit() {
				if (!this.pwd.oldPassword) {
					uni.$uv.toast('请输入原密码');
					return
				}
				if (!this.pwd.newPassword) {
					uni.$uv.toast('请输入新密码');
					return
				}
				if(this.pwd.newPassword.length<8){
					uni.$uv.toast('新密码不得少于8个字符');
					return
				}
				if (this.pwd.newPassword != this.pwd.rePassword) {
					uni.$uv.toast('两次密码输入不一致');
					return
				}
				this.$api.updatePwd({oldPassword:this.pwd.oldPassword,newPassword:this.pwd.newPassword}).then(res=>{
					uni.removeStorageSync('token');
					this.$store.commit('SET_TOKEN','');
					this.$store.commit('SET_USERINFO',{});
					uni.showModal({
						title:'提示',
						content:'密码修改成功，请重新登录',
						showCancel:false,
						confirmText:'去登录',
						success: (res) => {
							uni.reLaunch({
								url:'/pages/user/login'
							})
						}
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.flex_container {
		background: #F7FAFC;
	}

	.bg_box {
		width: 100%;
		box-sizing: initial;
		position: absolute;
		top: 0;
		left: 0;

		image {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
		}
	}

	.item_box {
		padding: 24rpx 16rpx;
		color: rgba(0, 0, 0, 0.85);
		font-size: 28rpx;
		background: #fff;

		.input-label {
			width: 200rpx;
			margin-right: 10rpx;
		}
	}

	.tips {
		padding: 26rpx;
		color: rgba(0, 0, 0, 0.65);
		font-size: 24rpx;
		line-height: 1.6;
	}

	.buttonStyle {
		padding: 24rpx 16rpx;
	}
</style>