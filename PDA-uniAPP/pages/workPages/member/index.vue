<template>
	<view class="main">

		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<u-icon name="arrow-left" :size="36" @click="$util.goback()"></u-icon>
					<view class="u-text-left u-flex-1 u-margin-left-20">
						个人中心
					</view>
					<view class="u-text-right u-flex-1 ">
						<view class="question-add" @click="$util.navigateTo('/pages/user/settings')">
							<u-icon name="setting" :size="32" class="u-margin-right-10"></u-icon>
							设置
						</view>
					</view>
				</view>
				<view class="member-info">
					<view class="member-info-headimg">
						<image :src="memberinfo.avatar ? memberinfo.avatar:'../../../static/images/avatar-nan.png'"
							mode="aspectFill" style="width: 130upx;height: 130upx;"></image>
					</view>
					<view class="member-info-name">
						<view class="member-info-account">
							{{ memberinfo.userName }}
						</view>
					</view>
				</view>
			</view>
		</view>
		<u-gap bg-color="#f7f9fa" :height="20" :margin-top="160" :margin-bottom="0"></u-gap>
		<view class="main-body">
			<view class="u-margin-top-30">
				<view class="u-flex main-body-summary u-border-bottom"
					@click="$util.navigateTo('/pages/workPages/member/info')">
					<image src="../../../static/images/myinfo.png" mode="aspectFill" style="width: 40upx;height: 40upx;">
					</image>
					<view class="main-body-type u-flex-3">我的信息</view>
					<u-icon name="arrow-right" :size="32" class="main-body-arrow"></u-icon>
				</view>
				<view class="u-flex main-body-summary u-border-bottom"
							@click="$util.navigateTo('/pages/workPages/checkAcceptance/index?from=member')">
					<image src="../../../static/images/myAcceptance.png" mode="aspectFill" style="width: 40upx;height: 40upx;">
					</image>
					<view class="main-body-type u-flex-3" >我的验收</view>
					<u-icon name="arrow-right" :size="32" class="main-body-arrow"></u-icon>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				memberinfo: []
			};
		},
		onShow() {
			this.memberinfo = uni.getStorageSync('memberInfo')
		},
		methods: {

		}
	};
</script>

<style lang="scss" scoped>
	.main {
		background-color: #fff;
	}

	.main-head {

		width: 100%;
		background-repeat: no-repeat;
		background: url(@/static/images/member-center.png) no-repeat center center;
		background-size: 100% 100%;

		.main-head-content {
			width: 92%;
			margin: 0 auto;
			height: 320upx;

			.u-demo-title {
				padding-top: 30upx;
				color: #ffffff;
				height: 170upx;
				line-height: 140upx;
				font-size: 34upx;
			}

			.member-info {
				padding: 72upx 0 10upx;
				align-items: center;
				border-radius: 50%;
				text-align: center;

				.member-info-name {
					font-size: 32upx;
					// color: #ffffff;
				}
			}

			.main-cate-image {
				width: 90upx;
				height: 90upx;
			}
		}
	}


	.main-body {
		width: 92%;
		margin: 0 auto;
		margin-bottom: 200upx;
		background-color: #fff;

		.main-body-summary {
			height: 120upx;
			line-height: 100upx;

			.main-body-type {
				margin-left: 40upx;
				font-size: 30upx;
			}

			.main-body-arrow {
				color: #a6acba;
			}
		}
	}

	.loginout {
		position: fixed;
		bottom: 100upx;
		color: #0a84ce;
		text-align: center;
		width: 100%;
		font-size: 30upx;
	}
</style>
