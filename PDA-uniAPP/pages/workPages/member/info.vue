<template>
	<view class="main">
		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<u-icon name="arrow-left" :size="36" @click="$util.goback()"></u-icon>
					<view class="u-text-left u-flex-1 u-margin-left-20">
						我的信息
					</view>
				</view>
			</view>
		</view>

		<view style="height: 160upx;"></view>
		<view class="main-body">
			<view class="u-margin-top-30">
				<view class="u-flex main-body-summary u-border-bottom" >
					<view class="main-body-type u-flex-3">头像</view>
					<image :src="memberinfo.avatar ? memberinfo.avatar:'../../../static/images/avatar-nan.png'"
						mode="aspectFill" style="width: 80upx;height: 80upx;"></image>
				</view>
				<view class="u-flex main-body-summary u-border-bottom" >
					<view class="main-body-type u-flex-3">姓名</view>
					<view class="">{{ memberinfo.userName }}</view>
				</view>
				<view class="u-flex main-body-summary u-border-bottom">
					<view class="main-body-type u-flex-3">性别</view>
					<view class="">{{ memberinfo.userSex }}</view>
				</view>
<!--				<view class="u-flex main-body-summary u-border-bottom">-->
<!--					<view class="main-body-type u-flex-3">职称</view>-->
<!--					<view class="">{{ memberinfo.positionalTitleName }}</view>-->
<!--				</view>-->
				<view class="u-flex   u-border-bottom" style="min-height:120upx;padding: 10upx 0;">
					<view class="main-body-type u-flex-3 u-margin-left-40" v-if="memberinfo.regionType=='user_dept'">科室</view>
					<view class="main-body-type u-flex-3 u-margin-left-40" v-else>病区</view>
					<view class="u-flex-6 u-text-right">{{ memberinfo.regionName }}</view>
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
			this.memberinfo=uni.getStorageSync('memberInfo')
			console.log(this.memberinfo);
		},
		methods: {

		}
	};
</script>

<style lang="scss" scoped>
	.main-head {
		width: 100%;
		background-repeat: no-repeat;
		background: url(@/static/images/headTop.png) no-repeat center center;
		background-size: 100% 100%;
		position: fixed;
		z-index: 20;
		top: 0px;

		.main-head-content {
			width: 92%;
			margin: 0 auto;

			.u-demo-title {
				padding-top: 30upx;
				color: #ffffff;
				height: 170upx;
				line-height: 140upx;
				font-size: 34upx;
			}

			.member-info {
				padding: 90upx 0 10upx;
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
		margin: 0 auto;
		margin-bottom: 200upx;
		background-color: #fff;
		padding: 0 40upx;

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
