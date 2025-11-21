<template>
	<view class="popup-main">
		<u-popup v-model="showPopup" mode="bottom" border-radius="14" height="60%"
						 @close="$emit('close')">
			<view class="popupTitle">
				<view class="popup-title-text">配送进度</view>
				<u-icon name="close" color="#2979ff" size="28" @click="$emit('close')" class="popup-close"></u-icon>
			</view>
			<view class="popup-info">
				<view class="order-code">单号：{{info.dispensingOrderNo}}</view>
				<view class="worker-name">配送员：{{info.deliveryPersonName}}</view>
			</view>
			<view class="popup-steps">
				<scroll-view scroll-y style="height: 100%;width: 100%; " >
					<view class="popup-steps-content">
						<u-time-line>
							<u-time-line-item nodeTop="2" v-for=" item in data">
								<!-- 此处自定义了左边内容，用一个图标替代 -->
<!--								<template v-slot:node>-->
<!--									<view class="u-node" style="background: #19be6b;">-->
<!--										&lt;!&ndash; 此处为uView的icon组件 &ndash;&gt;-->
<!--									</view>-->
<!--								</template>-->
								<template v-slot:content>
									<view>
<!--										<view class="u-order-title">待取件</view>-->
										<view class="u-order-time">{{item.operateTime || ''}}</view>
										<view class="u-order-desc">{{item.operateTypeDesc || ''}}</view>
									</view>
								</template>
							</u-time-line-item>
<!--							<u-time-line-item>-->
<!--								&lt;!&ndash; 此处没有自定义左边的内容，会默认显示一个点 &ndash;&gt;-->
<!--								<template v-slot:content>-->
<!--									<view>-->
<!--										<view class="u-order-desc">【深圳市】日照香炉生紫烟</view>-->
<!--										<view class="u-order-time">2019-12-06 22:30</view>-->
<!--									</view>-->
<!--								</template>-->
<!--							</u-time-line-item>-->
						</u-time-line>
					</view>

				</scroll-view>

			</view>
		</u-popup>
	</view>
</template>
<script>
	export default {
		props:{
			data:{
				type:Array,
			},
			info:{
				type:Object,
			}
		},
		computed: {
			// i18n() {
			// 	return this.$t('drugRepercussion');
			// }
		},
		data() {
			return {
				showPopup:true,
			};
		},
		onLoad() {
			this.Getlist();
		},
		onShow() {},
		methods: {

		}
	};
</script>

<style lang="scss" scoped>
	.popup-main{
		height: 100vh;
		position: absolute;

	}
	.popupTitle{
		height: 80upx;
		width: 100%;
		line-height: 80upx;
		text-align: center;
		display: flex;
		background: #e6e6e6;
		color: #000000;
		/*position: absolute;*/
		/*top: 0;*/
		/*left: 0;*/
		.popup-title-text{
			width: 100%;
		}
		.popup-close{
			/*float: right;*/
			margin: 0 20upx;
			position: absolute;
			top: 30upx;
			right: 0;
		}
	}
	.popup-info{
		/*position: absolute;*/
		/*top: 80upx;*/
		/*left: 0;*/
		width: 100%;
		height: 80upx;
		line-height: 80upx;
		display: flex;
		justify-content: space-between;
		color: #000000;
		border-bottom: 1upx solid #e5e5e5;
		padding: 0 20upx;
	}
	.popup-steps{
		width: 100%;
		height: calc(100% - 165upx);
		/*position: relative;*/
		.popup-steps-content{
			padding: 24rpx 24rpx 24rpx 40rpx;
		}
	}



	.u-node {
		width: 44rpx;
		height: 44rpx;
		border-radius: 100rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		background: #d0d0d0;
	}

	.u-order-title {
		color: #333333;
		font-weight: bold;
		font-size: 32rpx;
	}

	.u-order-title.unacive {
		color: rgb(150, 150, 150);
	}

	.u-order-desc {
		color: #999999;
		font-size: 28rpx;
		height: 40upx;
		margin-bottom: 6rpx;
	}

	.u-order-time {
		color: #999999;
		height: 40upx;
		font-size: 26rpx;
	}

	.tel {
		color: $u-type-primary;
	}
	::v-deep{
		.u-time-axis::before{
			height: calc(100% - 40px);
		}
	}
</style>
