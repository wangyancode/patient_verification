<template>
	<view class="main">
		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<u-icon name="arrow-left" :size="36" @click="$util.goback()"></u-icon>
					<view class="u-text-left u-flex-1 u-margin-left-20">
					到货验收
					</view>
					<view class="u-margin-left-20 main-scan" @click.stop="sidebar">
						<image src="@/static/images/btn.png" mode="" style="width: 30upx;height: 30upx;">
						</image>
					</view>
				</view>
				<view class="main-head-search u-padding-bottom-40 u-flex">
					<u-search v-model="listQuery.param.dispensingOrderNo" @change="change" :placeholder="'请扫描配送箱二维码或输入发票号'"
										:placeholderColor="'#fff'" :bgColor="'rgba(0,0,0,0.2)'"
										:inputStyle="{'background':'transparent'}" :color="'#fff'" :shape="'square'" :clearabled="true"
										:show-action="false" :input-align="'left'" @clear="clear"></u-search>
					<view class="u-margin-left-20 main-scan"  @click="getScancode">
						<image src="@/static/images/scan.png" mode="" style="width: 30upx;height: 30upx;">
						</image>
					</view>
				</view>
			</view>
		</view>
		<view class="content">
<!--			<u-tabs-swiper ref="uTabs" :list="tabsList" :is-scroll="false" @change="tabsChange"></u-tabs-swiper>-->
								 <liuyuno-tabs :tabData="tabsList"  :current="current" @tabClick='tabsChange' />

			<swiper class="swiper">
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<u-gap bg-color="#f6f8fb" :height="20" :margin-top="0" :margin-bottom="0"></u-gap>
						<view class="content-body" v-if="list.length>0">
							<view class="question-body ">
								<view class="question-body-summary u-margin-bottom-20" v-for="(item,index) in list"
											:key="index" @click="handleDetail(item)">
									<view class="u-padding-20">
										<view class="u-item-title">
											<view
													class="question-body-name u-margin-left-20 u-line-1 u-font-weight u-border-bottom">
												箱码：{{item.boxNo}}
											</view>

										</view>
										<view class="question-body-data-summary u-line-1   u-flex">
											<view class="data-title u-font-weight">配送企业：{{item.distributeName}}</view>
										</view>
										<view class="u-line-1 data_tags" v-if="item.invoiceNo">
<!--											<text class="question-info u-margin-left-30">发票号：{{item.invoiceNo && item.invoiceNo.map(item =>{return item.medicineBoxCode}).join(',') }}</text>-->
											<text class="question-info u-margin-left-30">发票号：{{item.invoiceNo}}</text>
										</view>
										<view class="u-line-1 data_tags" v-if="item.groupNo">
											<text class="question-info u-margin-left-30"
														v-if="item.groupNo">配送组号：{{item.groupNo}}</text>
										</view>
										<view class="question-body-type  u-flex u-font-24 " :class="item.distributeTime?'u-margin-top-30':'u-margin-top-10'">
											<view class="u-flex-1 u-flex margin-left-24">
												<u-icon name="clock" :size="34"
																class="main-body-arrow u-margin-right-5"></u-icon>
												{{item.distributeTime}}
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
		<stepsPopup v-if="showPopup" @close="showPopup = false" :data="stepPopupList" :info="stepPopupData"></stepsPopup>
		<u-modal ref="uModal" v-model="showMedicineBoxCode" title="验收确认" :show-title="true" :show-cancel-button="true"

				 :async-close="false" @confirm="toDetail(showMedicineBoxData,0)" @cancel="toDetail(showMedicineBoxData,1)">
			<view class="slot-content">
				<view style="width: 100%;line-height: 30upx;font-size: 24upx;">
					检测到有相同组号的配送箱，是否确认合并验收
				</view>
			</view>
		</u-modal>

		<u-popup v-model="sidebarShow" mode="bottom" width="560rpx" :closeable='true' ref="uPopupOrder"
						 height="60%"
						 @close='closePup'>
			<scroll-view scroll-y="true" >
				<view style="height: 55vh;">
					<view class="topName">
						<view style="margin-left:30upx">
							数据筛选
						</view>
					</view>
					<view class='middleName'>
						<view class="topName1">
							<view style="margin-left:30upx">
								配送企业
							</view>
						</view>
						<view class="classifyBigBox">
							<view class="oneClassify flex" :class="{selectActive:selected[item.dictValue]==true}"
										v-for="(item,i) in orderCategoryArr" @click="onSelectClassify(item.dictValue)">
								{{item.dictLabel}}
							</view>
						</view>
					</view>
					<view class='middleName' >
						<view class="topName1">
							<view style="margin-left:30upx">
								配送时间
							</view>
						</view>
						<view class="classifyBigBox">
							<view class="oneClassify flex" :class="{selectActive:selected1[item.dictValue]==true}"
										v-for="(item,i) in orderCategoryArr" @click="onSelectClassify1(item.dictValue)">
								{{item.dictLabel}}
							</view>
						</view>
					</view>
					<view class="bottomName">
						<view class="bot1">
							<u-button type="primary" plain @click='reSubmit'>重 置</u-button>
						</view>
						<view class="bot2">
							<u-button type="primary" @click='submit'>确 定</u-button>
						</view>
					</view>
				</view>
			</scroll-view>
		</u-popup>


	</view>
</template>
<script>
	import liuyunoTabs from "@/components/liuyuno-tabs/liuyuno-tabs.vue";
	import stepsPopup from './components/index'
	// #ifdef APP-PLUS
	var testModule = uni.requireNativePlugin("zll-pda-PdaModule")
	// #endif
	const eda50ScanModule = uni.requireNativePlugin('wm-Eda50QrcodeScan')

	export default {
		components:{stepsPopup,liuyunoTabs},
		data() {
			return {
				showPopup:false,
				sidebarShow:false,
				stepPopupData:{},
				stepPopupList:[],
				orderTypeArr:[],
				customStyle:{
					marginTop: '0px', // 注意驼峰命名，并且值必须用引号包括，因为这是对象
					color: '#2c9ef7',
					backgroundColor:'',
					position:'absolute',
					right:'10upx',
				},
				list: [],
				tabsList: [{
					name: '验收中',
					count:0,
				}, {
					name: '已验收',
					count:0,
				}, ],
				tabs:[],
				page: 1,
				psize: 10,
				total: 0,
				hasMoreData: false,
				keyword: undefined,
				// 因为内部的滑动机制限制，请将tabs组件和swiper组件的current用不同变量赋值
				current: 0, // tabs组件的current值，表示当前活动的tab选项
				tabCurrent: 0, // swiper组件的current值，表示当前那个swiper-item是活动的

				title: '素胚勾勒出青花，笔锋浓转淡',
				subTitle: '2020-05-15',
				thumb: 'http://pic2.sc.chinaz.com/Files/pic/pic9/202002/hpic2119_s.jpg',
				listQuery: {
					pageSize:10,
					pageNum:1,
					param:{
						distributeCode:undefined,
						distributeTime:undefined,
						checkStatus:1, //验收状态1待验收2验收中3已验收
						boxNo:undefined,
					}
				},
				memberinfo:{},
				showModal: true,
				showMedicineBoxCode: false,
				showMedicineBoxData: {},
				medicineBoxCodeTips: '',

				selected: {},
				selected1: {},
				isSubmit: true,
				isList:true,
				selectSave: {},
				selectSave1: {},
				orderListSave: [],
				categoryListSave: [],
				categoryList: [],
				orderCategoryArr: [],
				orderList: [],

			};
		},
		onLoad() {
			// #ifdef APP-PLUS
			this.initScan()
			this.initScan2()
			// #endif
		},
		onShow() {
			this.memberinfo=uni.getStorageSync('memberInfo')

			this.getList(0);
			// this.getList(1);

		},
		onUnload(){
			eda50ScanModule.releaseDevice()
		},
		methods: {
			getList(tabCurrent=this.tabCurrent){
				this.listQuery.param.checkStatus = tabCurrent+2;
				this.$request.post("/deliveryOrder/allStatusQueryPage",{
					...this.listQuery,
				}).then(res =>{
						// console.log(res,'开，往城市边缘开，车窗都摇下来。',tabCurrent,this.tabCurrent);
					if(this.tabCurrent = tabCurrent ){
						this.list = res.rows;
					}
					if(!tabCurrent){
						this.list = res.rows;
					}
					if(tabCurrent!=1){
						this.tabsList[tabCurrent].count = res.total;
					}
				}).catch(err=>{

				})
			},




			onPullDownRefresh() {
				this.page = 1;
				this.listQuery.pageNum = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.getList();
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
				this.getList();
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
				this.listQuery.pageNum = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.listQuery.param.dispensingOrderNo = value
				this.getList()
			},
			clear() {
				this.page = 1;
				this.listQuery.pageNum = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.listQuery.param.dispensingOrderNo = undefined
				this.getList()
			},
			// tabs通知swiper切换
			tabsChange(index) {
				console.log(index,"???");
				this.tabCurrent = index;
				this.current = index;
				this.listQuery.pageNum = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.getList()
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
				this.tabCurrent = current;
				this.current = current;
			},
			handleDetail(data){
				let {boxNo,groupNo} = data;
				this.showMedicineBoxData= data;
				if(groupNo){
					this.showMedicineBoxCode = true;
				}else{
					this.toDetail(data,1)
				}
			},
			toDetail(data,type) {
				let {boxNo,groupNo} = data;
				uni.navigateTo({
					url: `/pages/workPages/checkAcceptance/detail?boxNo=${boxNo}&type=${this.tabCurrent}&groupNo=${groupNo||''}&isMerge=${type}`
				});
			},
			showPopupFun(obj){
				// console.log(obj,'>>>>>>>>>>>>>>>>>>>>>>>>>>');
				let that = this;
				that.$request.get(`/nurseManagement/orderStageInfo/${obj.dispensingOrderNo}`,{}).then(res =>{
					// console.log(res,'/nurseManagement/orderStageInfo/{dispensingOrderNo}')
					that.stepPopupData = obj;
					that.stepPopupList = res.data;
					that.showPopup = true;

				})
			},
			timeFormat(faultDate,completeTime){
				// 返回格式为xx天xx小时xx分钟
				// function(faultDate, completeTime) {
					let stime = Date.parse(new Date(faultDate));
					let etime = Date.parse(new Date(completeTime));
					// 两个时间戳相差的毫秒数
					let usedTime = etime - stime;
					// 计算相差的天数
					let days = Math.floor(usedTime / (24 * 3600 * 1000));
					// 计算天数后剩余的毫秒数
					let leave1 = usedTime % (24 * 3600 * 1000);
					// 计算出小时数
					let hours = Math.floor(leave1 / (3600 * 1000));
					// 计算小时数后剩余的毫秒数
					let leave2 = leave1 % (3600 * 1000);
					// 计算相差分钟数
					let minutes = Math.floor(leave2 / (60 * 1000));
					// 计算小时数后剩余的毫秒数
					let leave3 = leave2 % (3600 * 1000);
					// 计算相差分钟数
					let seconds = Math.floor(leave2 / (1000));
					let time = '';
					if(days == 0 && hours == 0 && minutes == 0){
						time =  seconds + "秒"
					}else if(days == 0 && hours == 0){
						time =  minutes + "分"
					}else if(days == 0){
						time = hours + "时" + minutes + "分"
					}else{
						time = days + "天" + hours + "时" + minutes + "分"
					}
					return time;
				// }
			},
			getScancode() {
				testModule.startScan()
				this.initScan()
				eda50ScanModule.startScan()
				this.initScan2()
				setTimeout(()=>{
					eda50ScanModule.releaseDevice()
				},5000)
			},
			initScan() {
				testModule.initScan((res) => {
					// 有些PDA会自带换行符，trim函数处理下
					let code = res.trim()
					//code就是扫描获取的值
					if (code) {
						this.scanConfirm(code)
					}
				})
			},
			initScan2() {
				eda50ScanModule.initDevice(res=>{
					// 有些PDA会自带换行符，trim函数处理下
					let code = res.trim()
					//code就是扫描获取的值
					if (code) {
						this.scanConfirm(code)
					}
				})
			},
			scanConfirm(code) {
				var that = this;
				this.$request
						.post('/nurseManagement/detailsScanBoxQuery', {medicineBoxCode: code,})
						.then(res => {
							console.log(res);
							if (res.code == 200) {
								that.initScan();
								that.initScan2();
								let {dispensingOrderId,dispensingOrderNo,} = res.data;
								this.showMedicineBoxData = res.data;
								if(res.data.dispensingOrderBoxVOList && res.data.dispensingOrderBoxVOList.length>1){
									if(that.showModal){
										that.showModal = false;
										that.medicineBoxCodeTips =  res.data.dispensingOrderBoxVOList&&  res.data.dispensingOrderBoxVOList.map(x=>{return x.medicineBoxCode}).join(',')
										that.showMedicineBoxCode = true;
									}
								}
							} else {
								that.initScan()
								that.initScan2()
								uni.showToast({
									title: res.msg,
									icon: 'none'
								});
							}

						})
						.catch(err => {
							console.log(err);
							that.initScan()
							that.initScan2()
						});
			},
			sidebar() {
				var that = this
				this.isSubmit = false
				// that.$request.get(`/system/dict/data/type/order_type`).then(res => {
				// 	that.orderTypeArr = res.data
				// })
				this.getSelect()

			},

			getSelect() {
				var that = this
				that.$request.get(`/system/dict/data/type/order_category`).then(res => {
					that.orderCategoryArr = res.data
					this.sidebarShow = true
				})
			},
			closePup() {
				if (this.isSubmit) {
					this.selectSave = JSON.parse(JSON.stringify(this.selected))
					this.selectSave1 = JSON.parse(JSON.stringify(this.selected1))
					this.orderListSave = JSON.parse(JSON.stringify(this.orderList))
					this.categoryListSave = JSON.parse(JSON.stringify(this.categoryList))

					this.sidebarShow = false
					this.keyword = ''
					this.listQuery.pageNum = 1;
					this.total = 0;
					this.list = [];
					this.dispensingOrderIds = []
					this.hasMoreData = true;
					this.listQuery.param.dispensingOrderNo = ''
					if (this.listQuery.param.dispensingOrderNo = '') {
						this.flags = false
					}
					this.getList()
				} else {
					this.selected = JSON.parse(JSON.stringify(this.selectSave))
					this.selected1 = JSON.parse(JSON.stringify(this.selectSave1))
					this.orderList = JSON.parse(JSON.stringify(this.orderListSave))
					this.categoryList = JSON.parse(JSON.stringify(this.categoryListSave))
				}
			},
			//选取清单性质
			onSelectClassify(id) {
				var that = this
				that.orderList = []
				this.$set(this.selected, id, !this.selected[id]) //动态更新视图数据
				for (var i in this.selected) {
					if (this.selected[i]) {
						that.orderList.push(i)
					}
				}
				console.log(this.orderList);
			},

			//选取清单类型
			onSelectClassify1(id) {
				var that = this
				that.categoryList = []
				this.$set(this.selected1, id, !this.selected1[id]) //动态更新视图数据
				console.log(this.selected1);
				for (var i in this.selected1) {
					if (this.selected1[i]) {
						that.categoryList.push(i)
					}
				}
			},
			// 重置
			reSubmit() {
				this.selected = {}
				this.selected1 = {}
				this.orderList = []
				this.categoryList = []
			},
			// 确定
			submit() {
				this.isSubmit = true
				this.sidebarShow =false
				// this.sidebarShow =false
				// this.keyword = ''
				// this.listQuery.pageNum = 1;
				// this.total = 0;
				// this.list = [];
				// this.dispensingOrderIds = []
				// this.hasMoreData = true;
				// this.listQuery.param.dispensingOrderNo = ''
				// if (this.listQuery.param.dispensingOrderNo = '') {
				// 	this.flags = false
				// }
				// this.getRealInfo()
			},
		}
	};
</script>

<style lang="scss" scoped>
	.main{
		height: 100vh;
	}
	.main-head {
		/*background-color: #009ced;*/
		background: url(@/static/images/headTop.png) no-repeat center center;

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
				justify-content: space-between;
				.data-title{
					width: 60%;
					margin-left: 24upx;

				}
			}
			.data_tags{
				height: 60upx;
				/*line-height: 100upx;*/
				font-size: 24upx;

			}
			.question-body-type {
				font-size: 26upx;
				color: #7b8293;
				.margin-left-24{
					margin-left: 24upx;

				}
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
		display: flex;
		justify-content: space-between;
		align-items: center;
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
	.question-info {
		padding: 6upx 20upx;
		background-color: #f4f5f6;
		border-radius: 5px;
		font-size: 28upx;
		margin-left: 24upx;
	}
	.main-scan {
		width: 64upx;
		height: 64upx;
		margin: 0 auto;
		background-color: rgba(0, 0, 0, 0.2);
		border-radius: 5px;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.notArrived{
		line-height: 36upx;
		min-width: 160upx;
		border-radius: 30upx;
		font-size: 24upx;
		text-align: center;
		/*border: 2upx solid #2c9ef7;*/
		background-color: rgba(44,158,247,.1);
		color: #2c9ef7;
		padding: 6upx 10upx;
	}
	.hadArrived{
		min-width: 160upx;
		line-height: 36upx;
		border-radius: 30upx;
		font-size: 24upx;
		text-align: center;
		/*border: 2upx solid #00c777;*/
		background-color: rgba(0,199,119,.1);
		color: #00c777;
		padding: 6upx 10upx;
	}
	.slot-content {
		font-size: 28rpx;
		color: $u-content-color;
		padding-left: 30rpx;
		word-break: break-all;
	}

	.topName {
		position: fixed;
		width: 100%;
		height: 120rpx;
		line-height: 120rpx;
		font-size: 30upx;
		font-weight: bold;
		color: #494949;
		z-index: 22;
		background-color: #fff;
		border-bottom: 1px solid #f7f7f7;
	}

	.topName1 {
		width: 100%;
		height: 120rpx;
		line-height: 120rpx;
		font-size: 30upx;
		font-weight: bold;
		color: #a9a9a9;
		z-index: 22;
		background-color: #fff;
	}
	.middleName {
		position: relative;
		top: 140upx;
	}

	.bottomName {
		width: 100%;
		position: fixed;
		bottom: 20upx;
		display: flex;
		margin-left: 20upx;

		.bot1 {
			width: 45%;
			margin-right: 20upx;
		}

		.bot2 {
			width: 45%;
		}
	}
	::v-deep .u-close--top-right {
		z-index: 23;
	}

	.classifyBigBox {
		display: flex;
		color: #a9a9a9;
	}

	.bottomName {}

	.oneClassify {
		font-size: 26upx;
		height: 60upx;
		padding: 12upx 15upx 15upx 12upx;
		border-radius: 60upx;
		border: 1upx solid #eaeaea;
		margin: 0upx 20upx;
	}

	.selectActive {
		background: #54a9ff;
		color: #FFFFFF;
	}

	.tl-btn-120 {}
</style>
