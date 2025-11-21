<template>
	<view class="main">
		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<u-icon name="arrow-left" :size="36" @click="$util.goback()"></u-icon>
					<view class="u-text-left u-flex-1 u-margin-left-20">
						消息中心
					</view>
				</view>
<!--				<view class="main-head-search u-padding-bottom-40">-->
<!--					<u-search v-model="keyword" @change="change" :placeholder="'请输入消息内容'" :placeholderColor="'#fff'" :bgColor="'rgba(0,0,0,0.2)'"-->
<!--						:inputStyle="{'background':'transparent'}" :color="'#fff'" :shape="'square'" :clearabled="true"-->
<!--						:show-action="false" :input-align="'left'" @clear="clear"></u-search>-->
<!--				</view>-->
			</view>
		</view>
		<view style="height: 155upx;"></view>
		<view class="content">
			<view class="question-body u-margin-top-30 " v-if="list.length>0">
				<view class="question-body-summary u-margin-top-20 " v-for="(item,index) in list" :key="index"
					@click="todetail(item.id)">
					<view class="u-padding-20">
						<view class="question-body-name u-font-30 u-margin-bottom-10"> {{item.messageContent}}</view>
						<view class="u-padding-20">
							<view class="u-item-title">
								<view
										class="question-body-name  u-line-1 u-font-weight u-border-bottom " stle="font-size:28upx;">
									箱码：{{item.boxNo}}
								</view>

							</view>
							<view class="question-body-data-summary u-line-1   u-flex">
								<view class="data-title u-font-weight">配送企业：{{item.distributeName}}</view>
							</view>
							<view class="u-line-1  data_tags u-flex" v-if="item.invoiceNo" >
								<!--											<text class="question-info u-margin-left-30">发票号：{{item.invoiceNo && item.invoiceNo.map(item =>{return item.medicineBoxCode}).join(',') }}</text>-->
								<view style="width:120upx;">发票号：</view>
								<view style="flex-wrap: wrap;" class="u-flex">
									<text class="question-info_invoiceNo u-margin-right-30" v-for="text in item.invoiceNo.split(',')">{{text}}</text>
								</view>
							</view>
							<view class="u-line-1 data_tags" v-if="item.groupNo">
								配送组号：
								<text class="question-info u-margin-left-30"
											v-if="item.groupNo">{{item.groupNo}}</text>
							</view>
<!--							<view class="question-body-type  u-flex u-font-24 " :class="item.sendTime?'u-margin-top-30':'u-margin-top-10'">-->
<!--								<view class="u-flex-1 u-flex margin-left-24">-->
<!--									<u-icon name="clock" :size="34"-->
<!--													class="main-body-arrow u-margin-right-5"></u-icon>-->
<!--									{{item.sendTime}}-->
<!--								</view>-->
<!--							</view>-->
						</view>
						<view class="question-body-type u-margin-top-20 ">{{item.sendTime}}</view>
					</view>
				</view>
				<view class="" style="height: 40upx;"></view>
			</view>
			<view class="question-body u-margin-top-30 u-text-center " style="padding-top: 200upx;"   v-else>
				<image src="../../../static/images/empty.png" mode="aspectFill"
					style="width: 200upx;height: 200upx;"></image>
			</view>
		</view>

	</view>
</template>
<script>
	export default {
		data() {
			return {
				list: [],
				page: 1,
				psize: 10,
				total: 0,
				hasMoreData: true,
				keyword: undefined,
			};
		},
		onShow() {
			this.page = 1;
			this.hasMoreData = true;
			this.total = 0;
			this.list = [];
			this.Getlist();
		},
		methods: {
			Getlist() {
				var that = this;
				this.$request
					.post('/message/page', {
						"pageNum": that.page,
						"pageSize": that.psize,
						"param": {
							"messageContent": that.keyword,
						}
					})
					.then(res => {
						for (var i = 0; i < res.rows.length; i++) {
							that.list.push(res.rows[i]);
						}
						that.total = res.total;
						if (that.page * that.psize < that.total) {
							that.page = that.page + 1;
							that.hasMoreData = true;
						} else {
							that.hasMoreData = false;
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

			onReachBottom() {
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
			}
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

	.content {
		width: 94%;
		margin: 0 auto;

		.question-body-summary {
			padding: 10upx;
			background-color: #fff;
			border-radius: 10upx;

			.question-body-name {
				line-height: 50upx;
				font-size: 32upx;
			}

			.question-body-data-summary {
				font-size: 28upx;
			}

			.question-body-type {
				font-size: 26upx;
				color: #7b8293;
			}
		}

	}
</style>
