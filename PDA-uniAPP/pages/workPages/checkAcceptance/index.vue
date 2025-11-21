<template>
	<view class="main">
		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<view class="u-text-left  u-margin-left-20 u-flex" style="width: 50%;" >
						<u-icon name="arrow-left" :size="36" @click="goIndex"></u-icon>
						<view class="u-text-left u-flex-1 u-margin-left-20" @click="goIndex">
							到货验收
						</view>
					</view>
					<view class="u-text-left u-flex-1 u-margin-left-20"></view>
<!--					<view class="u-margin-left-20 main-scan" @click.stop="onRefresh">-->
<!--						<image src="../../../static/images/reflash.png" mode="" style="width: 30upx;height: 30upx;">-->
<!--						</image>-->
<!--					</view>-->
					<view class="u-margin-left-20 main-scan" @click.stop="sidebar">
						<image src="../../../static/images/btn.png" mode="" style="width: 30upx;height: 30upx;">
						</image>
					</view>
				</view>
				<view class="main-head-search u-padding-bottom-40 u-flex">
					<u-search v-model="listQuery.param.queryCriteria" @change="change" :placeholder="'请扫描配送箱二维码或输入发票号'"
										:placeholderColor="'#fff'" :bgColor="'rgba(0,0,0,0.2)'"
										:inputStyle="{'background':'transparent'}" :color="'#fff'" :shape="'square'" :clearabled="true"
										:show-action="false" :input-align="'left'" @clear="clear"></u-search>
					<view class="u-margin-left-20 main-scan"  @click="getScancode">
						<image src="../../../static/images/scan.png" mode="" style="width: 30upx;height: 30upx;">
						</image>
					</view>
				</view>
			</view>
		</view>
		<view class="content">
<!--			<u-tabs-swiper ref="uTabs" :list="tabsList" :is-scroll="false" @change="tabsChange"></u-tabs-swiper>-->
								 <liuyuno-tabs :tabData="tabsList"  :current="current" @tabClick='tabsChange' ref="index_check" />

			<swiper class="swiper">
				<swiper-item class="swiper-item">
					<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="reachBottom">
						<u-gap bg-color="#f6f8fb" :height="20" :margin-top="0" :margin-bottom="0"></u-gap>
						<view class="content-body" v-if="list.length>0">
							<view class="question-body ">
								<view class="question-body-summary u-margin-bottom-20" v-for="(item,index) in list"
											:key="index" @click="handleDetail(item)">
									<view class="" style="padding: 0 20upx;">
										<view class="u-item-title">
											<view
													class="question-body-name u-margin-left-20 u-line-1 u-font-weight u-border-bottom">
												箱码：{{item.boxNo}}
											</view>

										</view>
										<view class="question-body-data-summary u-line-1   u-flex">
<!--											<view class="data-title u-font-weight">配送企业：{{item.distributeName}}</view>-->
											<view class="data-title u-font-weight" >药品名称：
												<view :style="{color:item.drugName == '拼'?'#FF7373':''}">{{item.drugName}}</view>
											</view>
										</view>
										<view class="u-line-1  data_tags u-flex u-margin-left-20" v-if="item.invoiceNo" >
<!--											<text class="question-info u-margin-left-30">发票号：{{item.invoiceNo && item.invoiceNo.map(item =>{return item.medicineBoxCode}).join(',') }}</text>-->
											<view style="width:150upx;height: 40upx;font-size: 34upx" class="">配送企业：</view>
											<view style="flex-wrap: wrap;font-size: 34upx;" class="u-flex">
												<text class="question-info_invoiceNo u-margin-right-30">{{item.distributeName}}</text>
											</view>
										</view>
										<view class="u-line-1  data_tags u-flex u-margin-left-20" v-if="item.invoiceNo" >
<!--											<text class="question-info u-margin-left-30">发票号：{{item.invoiceNo && item.invoiceNo.map(item =>{return item.medicineBoxCode}).join(',') }}</text>-->
											<view style="width:120upx;height: 40upx;font-size: 34upx" class="">发票号：</view>
											<view style="flex-wrap: wrap;font-size: 34upx;" class="u-flex">
												<text class="question-info_invoiceNo u-margin-right-30" v-for="text in item.invoiceNo.split(',')">{{text}}</text>
											</view>
										</view>
										<view class="u-line-1 data_tags u-margin-left-20 u-flex" v-if="item.groupNo">
											<view  style="width:140upx;height: 40upx;font-size: 34upx;">配送组号：</view>
											<text class="question-info u-margin-left-30"
														v-if="item.groupNo">{{item.groupNo}}</text>
										</view>
										<view class="question-body-type u-font-24 " :class="item.distributeTime?'':'u-margin-top-10'">
											<view class="u-flex-1 u-flex margin-left-24" style="font-size: 28upx;">
<!--												<u-icon name="clock" :size="34" class="main-body-arrow u-margin-right-5"></u-icon>-->
												<view  style="width:150upx;height: 40upx;font-size: 28upx;">配送时间：</view>

												{{item.distributeTime}}
											</view>
											<view class="u-flex-1 u-flex margin-left-24" style="font-size: 28upx;" v-if="listQuery.param.checkStatus != 1">
<!--												<u-icon name="clock" :size="34" class="main-body-arrow u-margin-right-5"></u-icon>-->
												<view  style="width:150upx;font-size: 28upx;line-height: 28upx">验收时间：</view>

												{{item.checkDatetime}}
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
		<u-modal ref="uModal" v-model="showMedicineBoxCode" title="" :show-title="true" :show-cancel-button="true"

				 :async-close="false" @confirm="toDetail(showMedicineBoxData,0)" @cancel="toDetail(showMedicineBoxData,1)">
			<view class="slot-content">
				<view style="width: 80%;line-height: 42upx;font-size: 42upx;margin: 30upx 10%;text-align: center;">验收确认</view>
				<view style="width: 90%;line-height: 50upx;font-size: 36upx;margin: 30upx 5%;">
					检测到有相同组号的配送箱，是否确认合并验收
				</view>
			</view>
		</u-modal>

		<u-modal ref="uModal" v-model="showDiffRegionWarning" title="" :show-title="true" :show-cancel-button="false"

				 :async-close="false" @confirm="diffRegionConfirm">
			<view class="slot-content">
				<view style="width: 80%;line-height: 42upx;font-size: 42upx;margin: 30upx 10%;text-align: center;">验收确认</view>
				<view style="width: 90%;line-height: 50upx;font-size: 36upx;margin: 30upx 5%;">
					{{diffRegionMsg}}
				</view>
			</view>
		</u-modal>
		<u-modal ref="uModal" v-model="showSameBoxWarning" title="" :show-title="true" :show-cancel-button="false"
						 @confirm="sameBoxConfirm"
				 :async-close="false">
			<view class="slot-content">
				<view style="width: 80%;line-height: 42upx;font-size: 42upx;margin: 30upx 10%;text-align: center;">验收确认</view>
				<view style="width: 90%;line-height: 50upx;font-size: 36upx;margin: 30upx 5%;">
					{{diffRegionMsg}}
				</view>
			</view>
		</u-modal>

		<u-popup v-model="sidebarShow" mode="bottom" width="560rpx" :closeable='true' ref="uPopupOrder"
						 height="60%" duration="0" :mask-close-able="false" close-icon-color="#000"
						 @open="openPup"
						 @close='closePup'>
			<scroll-view scroll-y="true" style="height: calc(100% - 120upx);width: 100%;">
				<view style="width: 100%;height: 200upx;align-items: center;position: absolute;top:calc(50% - 100upx);" v-if="sidebarShowLoading">
					<view style="width: 100%;display: flex;justify-content: center;">
						<u-loading show size="80" mode="flower" ></u-loading>
					</view>
					<view style="width: 100%;text-align: center;">加载中...</view>
				</view>
				<view v-if="!sidebarShowLoading">
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
							<view class="oneClassify companyItem" :class="{selectActive:item.selected==true}"
										v-for="(item,i) in orderCategoryArr" :key="item.distributeCode" @click="onSelectClassify(item)">
								{{item.distributeName}}
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
							<view class="oneClassify flex" :class="{selectActive:item.selected==true}"
										v-for="(item,i) in timeTypeList" :key="item.dictLabel+'key'" @click="onSelectClassify1(item)">
								{{item.dictLabel}}
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
<!--			<view class="bottomName btn-group">-->
<!--				<view class="bot1">-->
<!--					<u-button type="primary" plain @click.stop='submit(1)'>重 置</u-button>-->
<!--				</view>-->
<!--				<view class="bot2">-->
<!--					<u-button type="primary" @click.stop='submit(0)'>确 定</u-button>-->
<!--				</view>-->
<!--			</view>-->
			<view class="btn-group">
				<view class="btn-item" @click="submit(1)">重置</view>
				<view class="btn-item  btn-submit" @click="submit(0)">确定</view>
			</view>
		</u-popup>

		<u-calendar v-model="showCalendar" mode="range"  @change="calendarChange" ref="calendar"></u-calendar>

		<view class="u-margin-left-20 main-refresh" @click.stop="onRefresh">
			<u-icon name="reload" size="50" color="#fff"></u-icon>
		</view>

	</view>
</template>
<script>
	import liuyunoTabs from "@/components/liuyuno-tabs/liuyuno-tabs.vue";
	import stepsPopup from './components/index'
	// #ifdef APP-PLUS
	var testModule = uni.requireNativePlugin("zll-pda-PdaModule")
	// #endif
	// const eda50ScanModule = uni.requireNativePlugin('wm-Eda50QrcodeScan')
	const eda50ScanModule = uni.requireNativePlugin('wm-Eda50QrcodeScan')

	export default {
		components:{stepsPopup,liuyunoTabs},
		data() {
			return {
				showPopup:false,
				sidebarShow:false,
				sidebarShowLoading:false,
				showCalendar:false,
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
					name: '待验收',
					count:0,
				},{
					name: '验收中',
					count:0,
				}, {
					name: '已验收',
					count:0,
				}, ],
				tabs:[],
				pageNum: 1,
				pageSize: 10,
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
						distributeCodes:undefined,
						distributeTime:undefined,
						checkStatus:1, //验收状态1待验收2验收中3已验收
						boxNo:undefined,
						queryCriteria:undefined,
						yfsb:undefined,
						isScanned:undefined,
					}
				},
				memberinfo:{},
				showModal: true,
				showMedicineBoxCode: false,
				showDiffRegionWarning: false,
				showSameBoxWarning: false,
				diffRegionMsg: '',
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
				selectedCompanyArr:[],
				orderList: [],
				timeTypeList:[
					{dictLabel:'1天内',dictValue:'1',selected:false,},
					{dictLabel:'3天内',dictValue:'3',selected:false,},
					{dictLabel:'7天内',dictValue:'7',selected:false,},
					{dictLabel:'14天内',dictValue:'14',selected:false,},
					{dictLabel:'自定义',dictValue:'diy',selected:false,},
				],
				fromType:''
			};
		},
		onLoad(options) {
			// console.log(options,'飞蛾扑火');
			this.fromType = options.from;
			if(options.from=='member'){
				this.tabsList=[{
					name: '验收中',
					count:0,
				}, {
					name: '已验收',
					count:0,
				}, ]
			}
			// #ifdef APP-PLUS
			this.initScan()
			this.initScan2()
			// #endif
		},
		onShow() {
			this.memberinfo = uni.getStorageSync('memberInfo');
			// this.current = 0;
			// console.log(uni.getStorageSync('memberInfo'));
			// console.log(this.current,'11111111111111111111111',this.tabCurrent);
			this.tabCurrent = this.current;
			let param = uni.getStorageSync('listQuery');
			this.listQuery.param = {...this.listQuery.param,...param};
			this.listQuery.param.yfsb = this.memberinfo.defaultRegionValue;
			this.listQuery.param.queryCriteria = '';

			this.getList();
			// this.getList(1);

		},
		onUnload(){
			eda50ScanModule.releaseDevice()
		},
		methods: {
			// 下拉刷新
			onRefresh() {
				this.page = 1;
				this.listQuery.pageNum = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				this.listQuery.param.queryCriteria = undefined;
				this.getList();
				this.trigger = true
				setTimeout(() => {
					this.trigger = false
				}, 500);
			},
			getList(){
				if(this.fromType == 'member'){
					this.listQuery.param.checkStatus = this.tabCurrent+2;
				}else{
					this.listQuery.param.checkStatus = this.tabCurrent+1;
				}
				this.listQuery.param.isScanned = 1;
				this.$request.post("/deliveryOrder/allStatusQueryPage",{
					...this.listQuery,
				}).then(res =>{
						// console.log(res,'开，往城市边缘开，车窗都摇下来。',tabCurrent,this.tabCurrent);
					// if(this.tabCurrent == tabCurrent ){
					// 	console.log('镇魔？你不服气？',tabCurrent,this.tabCurrent);
					// 	for (var i = 0; i < res.rows.length; i++) {
					// 		this.list.push(res.rows[i]);
					// 	}
					// }

					// if(!tabCurrent){
						//  console.log('是什么意思？',this.tabCurrent);
					if(this.listQuery.pageNum == 1){
						this.list = [];
					}
						for (var i = 0; i < res.rows.length; i++) {
							this.list.push(res.rows[i]);
						}
					console.log('this.list',this.list);

					this.total = res.total;
					if (this.listQuery.pageNum * this.listQuery.pageSize < this.total) {
						this.listQuery.pageNum = this.listQuery.pageNum + 1;
						this.hasMoreData = true;
					} else {
						this.hasMoreData = false;
					}
				}).catch(err=>{

				})
				let {distributeTimeStart,distributeTimeEnd,distributeCodes,queryCriteria,yfsb,checkStatus } = this.listQuery.param
				this.$request.post('/deliveryOrder/allStatusQueryCount',{distributeTimeStart,
					queryCriteria,
					yfsb,
					// checkStatus,
					distributeTimeEnd,
					distributeCodes,}).then(res=>{
					// console.log(res,this.fromType,'fromType');
					if(this.fromType != 'member'){
						this.$set(this.tabsList[0],'count',res.data.toBeAcceptedCount);
						this.$set(this.tabsList[1],'count',res.data.duringAcceptanceCount);
						this.$set(this.tabsList[2],'count',res.data.acceptedCount);

					}else{
						this.$set(this.tabsList[0],'count',res.data.duringAcceptanceCount);
						this.$set(this.tabsList[1],'count',res.data.acceptedCount);
					}
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
				// this.listQuery.pageNum = this.pageNum;
				//  console.log(this.listQuery.pageNum,'llllllllllll');
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
				this.listQuery.param.queryCriteria = value
				this.getList()
			},
			clear(e) {
				console.log(e,'quni ma l elab');
				this.page = 1;
				this.listQuery.pageNum = 1;
				this.hasMoreData = true;
				this.total = 0;
				this.list = [];
				console.log('你是个什么啊？');
				this.listQuery.param.queryCriteria = undefined;
				this.$set(this.listQuery.param,'queryCriteria',undefined);
				this.listQuery.param.queryCriteria = '';
				this.$forceUpdate();
				// this.listQuery.param.dispensingOrderNo = undefined
				this.getList()
			},
			// tabs通知swiper切换
			tabsChange(index) {
				//  console.log(index,"???——tabsChange");
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
			handleDetail(data){
				let {boxNo,groupNo} = data;
				// console.log(groupNo,'?????????????????????????????????' ,groupNo && (this.fromType=='member'?this.current == 0:this.current != 2));
				this.showMedicineBoxData= data;
				if(groupNo && (this.fromType=='member'?this.current == 0:this.current != 2)){
					this.showMedicineBoxCode = true;
				} else{
					this.toDetail(data,1)
				}
			},
			toDetail(data,type) {
				console.log(data);
				let {boxNo,groupNo,distributeCode} = data;
				uni.navigateTo({
					url: `/pages/workPages/checkAcceptance/detail?boxNo=${boxNo}&type=${this.fromType=='member'?this.current+1:this.current}&groupNo=${groupNo||''}&isMerge=${type}&fromType=${this.fromType}&distributeCode=${distributeCode}`
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
				//  console.log(testModule,'1111111111111');
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
				eda50ScanModule.initDevice(false,res=>{
					// 有些PDA会自带换行符，trim函数处理下
					let code = res.trim()
					console.log(code,'dididididdididi');
					//code就是扫描获取的值
					if (code) {
						this.scanConfirm(code)
					}
				})
			},
			scanConfirm(code) {
				var that = this;
				let param = that.listQuery.param;
				this.listQuery.param.queryCriteria = code;
				console.log(code,'申报时间，水水水水');
				this.$request
						.post('/deliveryOrder/allStatusQueryPage', {pageSize: 10,pageNum:1,param:{...param,checkStatus:1,queryCriteria:code,isScanned:0}})
						.then(res => {
							//  //  console.log(res);
							if (res.code == 200) {
								that.initScan();
								that.initScan2();
								//  console.log(res.rows,'企鹅冻感冒啦');
								// let {dispensingOrderId,dispensingOrderNo,} = res.data;
								// this.showMedicineBoxData = res.rows[0];
								this.handleDetail(res.rows[0]);
							} else {
								that.initScan()
								that.initScan2()
								if(res.code == '519'){
									that.showDiffRegionWarning = true;
									that.diffRegionMsg = res.msg
								}else if(res.code == '522'){
									that.showSameBoxWarning = true;
									that.diffRegionMsg = res.msg
								}else{
									uni.showToast({
										title: res.msg,
										icon: 'none'
									});
								}
							}
						})
						.catch(err => {
							//  console.log(err);
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

			getSelect(type) {
				var that = this
				let date = new Date().getTime();
				that.sidebarShow = true;
				that.sidebarShowLoading = true;
				// uni.showLoading({
				// 	title: '加载中'
				// });
				that.$request.post(`/deliveryCompany/list`, {status: 0}).then(res => {
					res.data.map(item => {
						that.$set(item, 'selected', false)
						if (that.orderList.includes(item.distributeCode) || that.listQuery.param.distributeCodes?.includes(item.distributeCode)) {
							that.$set(item, 'selected', true)
						}
					})
					that.$nextTick(() => {
						that.orderCategoryArr = res.data
					})
				});
				that.sidebarShowLoading = false;
				// uni.hideLoading();

			},
			openPup(){
				let onSelectClassify = JSON.parse(JSON.stringify(uni.getStorageSync('onSelectClassify')))
				let onSelectClassify1 = JSON.parse(JSON.stringify(uni.getStorageSync('onSelectClassify1')))
				// console.log('onSelectClassify',onSelectClassify,this.orderCategoryArr);
				// console.log('onSelectClassify1',onSelectClassify1,this.timeTypeList);
				// onSelectClassify.selected = true
				// onSelectClassify1.selected = false
				this.timeTypeList.map(item =>{
					if(onSelectClassify1.dictValue == item.dictValue){
						this.$set(item, 'selected', true) //动态更新视图数据
					}
				})
			},
			closePup() {
				this.sidebarShow = false;
			},
			//选取清单性质
			onSelectClassify(data) {
				var that = this
				that.orderList = []
				that.$set(data,'selected', !data.selected) //动态更新视图数据
				that.orderCategoryArr.map(item =>{
					if(item.selected){
						that.orderList.push(item.distributeCode)
						// that.selectedCompanyArr.push(ite)
					}
				})
				that.listQuery.param.distributeCodes = that.orderList;
				uni.setStorageSync('onSelectClassify', data);
				//  console.log(that.orderList);
			},

			//选取清单类型
			onSelectClassify1(data) {
				var that = this
				console.log(data,'time');

				that.categoryList = []
				that.selected1 = {};
				that.timeTypeList.map(item =>{
					if(data.dictValue == item.dictValue){
						that.$set(item, 'selected', !item.selected) //动态更新视图数据

					}else{
						item.selected = false;
					}
					if(item.selected){
						that.categoryList.push(item.dictValue)
					}
				})

				// console.log(this.selected1);
				if(data.dictValue == 'diy'){
					that.$set(data, 'selected', true) //动态更新视图数据
					that.showCalendar= true;
				}else{
					that.listQuery.param.distributeTimeStart = that.getNextDate(Number('-'+data.dictValue)) +' 00:00:00';
					that.listQuery.param.distributeTimeEnd = that.getNextDate(0) +' 23:59:59'
				}
				if(!data.selected){
					that.listQuery.param.distributeTimeStart ='';
					that.listQuery.param.distributeTimeStart ='';
				}
					uni.setStorageSync('onSelectClassify1', data);
					// uni.removeStorageSync('onSelectClassify1');
				// for (var i in that.selected1) {
				// 	if (that.selected1[i]) {
				// 		that.categoryList.push(i)
				// 	}
				// }
			},
			// 重置
			reSubmit() {
				var that = this;
				that.timeTypeList.map(item =>{
						item.selected = false;
				})
				that.orderCategoryArr.map(item =>{
					item.selected = false;
				})
				this.orderList = [];
				that.listQuery.param.distributeTimeStart ='';
				that.listQuery.param.distributeTimeStart ='';
				that.listQuery.param.distributeCodes =[];
				uni.removeStorageSync('listQuery');
				uni.removeStorageSync('onSelectClassify');
				uni.removeStorageSync('onSelectClassify1');
				// this.getList();
				// this.sidebarShow = false;
			},
			// 确定
			submit(type) {
				var that = this;
				if(type == 0){
					this.sidebarShow =false;
					this.listQuery.pageNum = 1;
					this.total = 0;
					this.list = [];
					// console.log("这是为什么呢？")
					this.hasMoreData = true;
					this.getList()
					uni.setStorageSync('listQuery', this.listQuery.param);

				}else{
					this.reSubmit()
				}

			},
			calendarChange(e){
				// console.log(e,'calendarChange');
				if(e.startDate && e.endDate){
					this.listQuery.param.distributeTimeStart = e.startDate +' 00:00:00';
					this.listQuery.param.distributeTimeEnd = e.endDate +' 23:59:59'
					this.getList()
				}
			},
			getNextDate(day,date = new Date,) {
				//  console.log(date,'????????????');
				let dd = new Date(date);
				dd.setDate(dd.getDate() + day);
				let y = dd.getFullYear();
				let m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
				let d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
				// let h = dd.getHours() < 10 ? "0" + dd.getHours() : dd.getHours();
				// let t = dd.getMinutes() < 10 ? "0" + dd.getMinutes() : dd.getMinutes();
				// let s = dd.getSeconds() < 10 ? "0" + dd.getSeconds() : dd.getSeconds();
				let time = y + "-" + m + "-" + d
				// let time = y + "-" + m + "-" + d + ' ' + h + ':'+ t + ':' + s
				return time;
			},
			goIndex(){
				// uni.navigateTo({url: `/pages/workPages/index`})
				uni.navigateBack({
					delta: 1
				});
			},
			diffRegionConfirm(){
				this.listQuery.param.queryCriteria = '';
				this.listQuery.param.isScanned = '1';
				this.getList();
			},
			sameBoxConfirm(){
				// this.listQuery.param.queryCriteria = '';
				this.listQuery.param.isScanned = '1';
				this.getList();
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
				height: 60upx;
				line-height: 60upx;
				font-size: 34upx;

			}

			.question-body-data-summary {
				font-size: 40upx;
				height: 70upx;
				line-height: 70upx;
				justify-content: space-between;
				.data-title{
					width: 100%;
					margin-left: 24upx;
					display: flex;
					justify-content: flex-start;

				}
			}
			.data_tags{
				/*height: 60upx;*/
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
		top: 18upx;
		height: 16px;
		content: '';
		left: 0;
		border-radius: 10px;
		background-color: #2c9ef7;
	}
	.question-info_invoiceNo {
		padding: 6upx 20upx 0upx;
		background-color: #f4f5f6;
		border-radius: 5px;
		font-size: 34upx;
		margin: 0upx 10upx 10upx 0 ;
	}
	.question-info {
		padding: 6upx 20upx;
		background-color: #f4f5f6;
		border-radius: 5px;
		font-size: 34upx;
		/*margin-left: 24upx;*/
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
		z-index: 1;
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
		z-index: 0;
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
		flex-wrap: wrap;
	}

	.bottomName {}
	.companyItem{
		word-break: keep-all;
		/*margin: 10upx;*/
	}
	.oneClassify {
		font-size: 26upx;
		width: fit-content;
		height: 60upx;
		padding: 12upx 15upx 15upx 12upx;
		border-radius: 60upx;
		border: 1upx solid #eaeaea;
		margin: 0upx 20upx 10upx;
	}

	.selectActive {
		background: #54a9ff;
		color: #FFFFFF;
	}
	.btn-group {
		height: 100upx;
		width: 100%;
		display: flex;
		line-height: 100upx;

		.btn-item {
			flex: 1;
			text-align: center;
			line-height: 100upx;
		}

		.btn-submit {
			border-left: 4upx solid #f0f3f8;
			color: #2c9ef7;
		}

	}
	.tl-btn-120 {}

	.main-refresh{
		width: 100upx;
		height: 100upx;
		border-radius: 50upx;
		background: #2c9ef7;
		position: fixed;
		bottom: 20%;
		right: 0;
		align-items: center;
		display: flex;
		justify-content: center;
	}
</style>
