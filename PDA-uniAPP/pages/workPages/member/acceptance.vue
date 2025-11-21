<template>
	<view class="main">
		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<u-icon name="arrow-left" :size="36" @click="$util.goback()"></u-icon>
					<view class="u-text-left u-flex-1 u-margin-left-20">
					药品验收
					</view>
				</view>
				<view class="main-head-search u-padding-bottom-40">
					<u-search v-model="keyword" @change="change" placeholder="请输入单号或病区" :bgColor="'rgba(0,0,0,0.2)'" :color="'#fff'" :shape="'square'"
						:clearabled="true" :show-action="false" :input-align="'left'" @clear="clear"></u-search>
				</view>
			</view>
		</view>
		<view class="content">
			<u-tabs-swiper ref="uTabs" :list="tabsList" :is-scroll="false" @change="tabsChange"></u-tabs-swiper>
			<swiper :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish" class="swiper">
				<swiper-item class="swiper-item" v-for="(item, index) in tabsList" :key="index">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<u-gap bg-color="#f6f8fb" :height="20" :margin-top="0" :margin-bottom="0"></u-gap>


						<view class="content-body" v-if="list.length>0">
							<view class="question-body ">
								<view class="question-body-summary u-margin-bottom-20" v-for="(item,index) in list"
											:key="index" @click="toDetail(item.id)">
									<view class="u-padding-20">
										<view class="u-item-title">
											<view
													class="question-body-name u-margin-left-20 u-line-1 u-font-weight u-border-bottom">
												单号：{{item.sn}}
											</view>
										</view>
										<view class="question-body-data-summary u-line-1  u-font-weight u-flex">
											<view class="data-title">药房：{{item.ks}}</view>

											<u-button shape="circle"  size="mini" :custom-style="customStyle" @click="showPopup = true">乌啼</u-button>
										</view>
										<view class="u-line-1 data_tags">
											<u-tag text="调配发药：黎明" type="info" mode="plain"  size="mini" />
											<u-tag text="退药人：王菲" type="info" mode="plain"  size="mini" class="u-margin-left-10" />
										</view>
										<view class="u-line-1 data_tags">
											<u-tag text="药箱编号：DYX001,DYX002" type="info" mode="plain"  size="mini" />
										</view>
										<view class="u-line-1 data_tags">
											<u-tag text="配送：刘德华" type="info" mode="plain"  size="mini" />
										</view>
										<view class="question-body-type u-margin-top-10 u-flex u-font-24">
											<view class="u-flex-1 u-flex">
												<u-icon name="clock" :size="34"
																class="main-body-arrow u-margin-right-5"></u-icon>
												{{item.time}}
											</view>
											<view class="u-flex-1 u-flex">
												<image src="../../../static/images/hospital.png" mode="aspectFill"
															 style="height: 30upx;width: 30upx;margin-right: 10upx;"></image>
												{{item.ks}}
											</view>
										</view>
									</view>
								</view>

							</view>

						</view>

					</scroll-view>


				</swiper-item>
			</swiper>

		</view>
		<u-popup v-model="showPopup" mode="bottom" border-radius="14" height="60%">
			<view>
				人生若只如初见，何事秋风悲画扇
			</view>
		</u-popup>
	</view>
</template>
<script>
	export default {
		computed: {
			// i18n() {
			// 	return this.$t('drugRepercussion');
			// }
		},
		data() {
			return {
				showPopup:false,
				customStyle:{
					marginTop: '0px', // 注意驼峰命名，并且值必须用引号包括，因为这是对象
					color: '#2c9ef7',
					backgroundColor:'',
					position:'absolute',
					right:'10upx',
				},
				list: [
					{
						id: 1,
						sn: 'PY202309283374',
						ks: '配药中心1',
						bq: '十三病区',
						keyword: '您的配药单已生成，请开始配药',
						time: '2023-09-04 12:00:00'
					},
					{
						id: 2,
						sn: 'PY202309283374',
						ks: '配药中心1',
						bq: '十三病区',
						keyword: '您的配药单已生成，请开始配药',
						time: '2023-09-04 12:00:00'
					},
				],
				tabsList: [{
					name: '验收中'
				}, {
					name: '验收合格'
				}, {
					name: '验收不合格',
					count: 9
				}],
				tabs:[],
				page: 1,
				psize: 10,
				total: 0,
				hasMoreData: false,
				keyword: undefined,
				// 因为内部的滑动机制限制，请将tabs组件和swiper组件的current用不同变量赋值
				current: 0, // tabs组件的current值，表示当前活动的tab选项
				swiperCurrent: 0, // swiper组件的current值，表示当前那个swiper-item是活动的

				title: '素胚勾勒出青花，笔锋浓转淡',
				subTitle: '2020-05-15',
				thumb: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/hpic2119_s.jpg',
			};
		},
		onLoad() {
			this.Getlist();
		},
		onShow() {},
		methods: {
			Getlist() {
				var that = this;
				this.$request
					.get('notice/index', {
						params: {
							page: that.page,
							keyword: that.keyword
						}
					})
					.then(res => {
						if (res.data.errno == 1) {
							for (var i = 0; i < res.data.data.notice.length; i++) {
								that.list.push(res.data.data.notice[i]);
							}
							that.total = res.data.data.total;
							that.psize = res.data.data.psize;
							if (that.page * that.psize < that.total) {
								that.page = that.page + 1;
								that.hasMoreData = true;
							} else {
								that.hasMoreData = false;
							}
						} else {
							uni.showToast({
								title: res.data.message,
								icon: 'none'
							});
						}
					})
					.catch(err => {});
			},

			onPullDownRefresh() {
				this.page = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.Getlist();
				setTimeout(function() {
					uni.stopPullDownRefresh();
				}, 1000);
			},

			reachBottom() {
				if (!this.hasMoreData) {
					uni.showToast({
						title: '已是最后一页',
						duration: 1000
					});
					return false;
				}
				uni.showLoading({
					title: '加载中'
				});
				this.Getlist();
				setTimeout(function() {
					uni.hideLoading();
				}, 1000);
			},
			// todetail(event) {
			// 	var id = event;
			// 	uni.navigateTo({
			// 		url: '../member/notice_detail?id=' + id
			// 	});
			// },
			change(value) {
				// 搜索框内容变化时，会触发此事件，value值为输入框的内容
				//console.log(value);
				this.page = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.keyword = value
				this.Getlist()
			},
			clear() {
				this.page = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.keyword = undefined
				this.Getlist()
			},
			// tabs通知swiper切换
			tabsChange(index) {
				this.swiperCurrent = index;
			},
			// swiper-item左右移动，通知tabs的滑块跟随移动
			transition(e) {
				let dx = e.detail.dx;
				this.$refs.uTabs.setDx(dx);
			},
			// 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
			// swiper滑动结束，分别设置tabs和swiper的状态
			animationfinish(e) {
				let current = e.detail.current;
				this.$refs.uTabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},
			toDetail(event) {
				var id = event;
				uni.navigateTo({
					url: 'pages/nurse/drugAcceptance/detail?id=' + id
				});
			},
		}
	};
</script>

<style lang="scss" scoped>
	.main{
		height: 100vh;
	}
	.main-head {
		background-color: #009ced;

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

	.content {
		width: 100%;
		/*height: 100%;*/
		height: calc(100% - 354upx);
		/*margin: 0 auto;*/
		.swiper{
			height: 100%;
		}
		.swiper-item{
			height: 100%;
		}

		.question-body-summary {
			padding: 10upx;
			background-color: #fff;
			border-radius: 10upx;

			.question-body-name {
				height: 80upx;
				line-height: 80upx;
				font-size: 34upx;

			}

			.question-body-data-summary {
				font-size: 34upx;
				height: 100upx;
				line-height: 100upx;
				.data-title{
					width: 60%;
				}
			}
			.data_tags{
				height: 80upx;
				/*line-height: 100upx;*/
				font-size: 24upx;

			}
			.question-body-type {
				font-size: 26upx;
				color: #7b8293;
			}
		}

	}
	.overview-header-title {
		border-left: 6upx solid #2c9ef7;
		padding-left: 12upx;
		line-height: 24upx;
		font-size: 20upx;
	}
	.u-card-wrap {
		background-color: $u-bg-color;
		padding: 1px;
	}

	.u-body-item {
		font-size: 32rpx;
		color: #333;
		padding: 20rpx 10rpx;
	}
	.u-item-title {
		position: relative;
	}

	.u-item-title:after {
		position: absolute;
		width: 4px;
		top: 26upx;
		height: 16px;
		content: '';
		left: 0;
		border-radius: 10px;
		background-color: #2c9ef7;
	}
</style>
