<template>
	<view class="u-wrap">
		<view class="main-head">
			<view class="main-head-content">
				<view class="u-demo-title u-flex">
					<u-icon name="arrow-left" :size="36" @click="$util.goback()"></u-icon>
					<view class="u-text-left u-flex-1 u-margin-left-20">
						箱码详情
					</view>
				</view>
				<view class="main-head-search u-padding-bottom-40 u-flex">
					<u-search v-model="drugNameAndOrigin" @change="change" :placeholder="'请输入药品名称或产地'"
										:placeholderColor="'#fff'" :bgColor="'rgba(0,0,0,0.2)'"
										:inputStyle="{'background':'transparent'}" :color="'#fff'" :shape="'square'" :clearabled="true"
										:show-action="false" :input-align="'left'" @clear="clear"></u-search>
<!--					<view class="u-margin-left-20 main-scan" v-if="status==1">-->
<!--						<image src="../../../static/images/scan.png" mode="" style="width: 30upx;height: 30upx;">-->
<!--						</image>-->
<!--					</view>-->
				</view>
			</view>
		</view>
<!--		<view style="height: 255upx;"></view>-->
		<view class="content">
			<view class="tab-content u-border-bottom" v-if="status !=2">
				<view class="tab-body">
					<liuyuno-tabs :tabData="tablist"  :current="current" @tabClick='tabChange' />
				</view>
			</view>
			<view class="content-body">
				<view class="question-body-summary">

					<view class="u-item-title">
						<view class="question-body-name u-flex  u-margin-left-20 u-line-1 u-font-weight "
							:class="isshow?'u-border-bottom':''">
							<view class="u-fle-1">
								单号：{{drugDetail.initialDispensingOrderNo}}
							</view>
						</view>
					</view>
					<view class="" >
						<view class="question-body-data-summary u-line-1  u-font-weight u-margin-left-24">病区：{{drugDetail.wardName}}</view>
						<view class="">
							<text class="question-info">调配发药：{{drugDetail.deliveryPersonName}}</text>
							<text class="question-info u-margin-left-30"
								  v-if="drugDetail.recheckUserName">复核：{{drugDetail.recheckUserName}}</text>
						</view>
						<view class="u-margin-top-30 ">
							<text class="question-info">药箱：{{drugDetail.dispensingOrderBoxVOList&& drugDetail.dispensingOrderBoxVOList.map(item => {return item.medicineBoxCode}).join(',')}}</text>
						</view>
						<view class="u-margin-top-30 ">
							<text class="question-info">配送：{{drugDetail.deliveryPersonName}}</text>
						</view>
						<view class="question-body-type  u-flex u-font-24 u-padding-bottom-20"
									:class="drugDetail.generateTime?'u-margin-top-30':'u-margin-top-10'"
						>
							<view class="u-flex-1 u-flex">
								<u-icon name="clock" :size="34"
									class="main-body-arrow u-margin-right-5 u-margin-left-24"></u-icon>
                {{drugDetail.generateTime}}
							</view>
							<view class="u-flex-1 u-flex ">
								<image src="../../../static/images/hospital.png" mode="aspectFill"
									style="height: 30upx;width: 30upx;margin-right: 10upx;margin-left: 34px;"></image>
                {{drugDetail.deptName}}
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view style="height: 20upx;" class="suggest-top"></view>
		<view class="u-menu-wrap" id="element">
			<scroll-view scroll-y scroll-with-animation class="u-tab-view menu-scroll-view" :scroll-top="scrollTop"
				:scroll-into-view="itemId">
				<view v-for="(item,index) in drugDetail.drugListTypeVOList" :key="index" class="u-tab-item"
					:class="[current == index ? 'u-tab-item-active' : '']" @tap.stop="swichMenu(index)">
					<text class="u-line-1">{{item.shortName}}</text>
					<text v-if="item.drugVOList && item.drugVOList.length>0">({{item.drugVOList.length}})</text>
					<text v-if="item.bedNumberGroupingDTOList && item.bedNumberGroupingDTOList.length>0">({{item.bedNumberGroupingDTOList.length}})</text>
				</view>
			</scroll-view>
			<scroll-view :scroll-top="scrollRightTop" :scroll-y="showScroll" scroll-with-animation class="right-box"
				@scroll="rightScroll">
				<view class="page-view">
					<u-checkbox-group :size="30" :width="'auto'" :wrap="false" :activeColor="'#2c9ef7'" style="width: 100%;">
						<view class="class-item" :id="'item' + index"
									v-for="(item , index) in drugDetail.drugListTypeVOList" :key="index">
							<view class="item-title u-flex">
								<text>{{item.shortName}}</text>
								<u-checkbox v-model="item.checked" :name="item.drugListTypeCode" :disabled="false"
														v-if="status==1" @change="checkboxChange">
									<text style="font-size: 26upx;">全选</text> </u-checkbox>
							</view>
							<view class="item-container">
								<u-checkbox-group :size="30" :width="'auto'" :wrap="false" :activeColor="'#2c9ef7'" v-if="item.showType==1"  style="width: 100%;">
									<view class="thumb-box" v-for="(item1, index1) in item.bedNumberGroupingDTOList" :key="index1">
										<view class="thumb-body u-border-bottom" >
											<view
													class="thumb-parent-info u-padding-left-20  u-font-weight u-margin-bottom-20">
												<text> 床号：{{item1.bedNo}}</text>
												<text class="u-margin-left-40"> 姓名：{{item1.patientName}}</text>
											</view>

											<view class="" v-for="(val,key) in item1.drugVOList" :key="key">
												<view class="drug-title  u-padding-20">
													<text style="color: #333;"  class="u-font-weight">
														<text style="margin-right: 10upx;">{{val.sortNo}}.</text>
														{{val.drugName}}</text>

													<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[val.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,'4','5',].includes(val.drugProcessNode) && val.exceptionDrugNum!=0">
														{{`${{4:'多药', 5:'少药', 6:'破损',}[val.drugProcessNode]}X${val.exceptionDrugNum}${val.drugUnit}`}}
													</text>
													<!--	<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[val.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,6,'4','5','6',].includes(val.drugProcessNode)">-->
													<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[val.drugProcessNode]}`}"  class="u-font-24"
																v-if="[6,'6',].includes(val.drugProcessNode) && val.exceptionDrugNum!=0 && val.listDocumentVO"  @click.stop="showDrugPictrue(val.listDocumentVO)">
														<u-icon name="photo" color="#2979ff" size="28"></u-icon>
														{{`${{4:'多药', 5:'少药', 6:'破损',}[val.drugProcessNode]}X${val.listDocumentVO && val.listDocumentVO.length}${val.drugUnit}`}}
													</text>
													<u-checkbox v-model="val.checked" :name="val.dispensingOrderDrugId"
																			@change="checkboxChildrenChange"
																			:disabled="item.packageFlag==1?false:true"
																			style="margin-right: 30upx;" v-if="status==1"></u-checkbox>
												</view>
												<view class="u-flex ">
													<view class="u-flex-4">
														<view class="drug-summary u-padding-20 u-font-26">
															<view class="u-padding-bottom-20">
																<text class="u-margin-right-20">{{val.drugSpecification}}</text>
																<text>{{val.drugOrigin.substr(0,4)}}</text>
															</view>
															<view class="u-padding-bottom-20">
																<text>{{val.drugSingleDose}}</text>
																<text class="u-margin-left-20">{{val.drugFrequency}}</text>
															</view>
														</view>

													</view>
													<view class="u-flex-1 u-font-weight u-font-36" style="color: #333;">
														X <text>{{val.normalDrugNum}}{{val.drugUnit}}</text>
													</view>
												</view>


											</view>

										</view>
										<view class="thumb-body u-border-bottom" v-if="item.showType==2">
											<view class="drug-title  u-padding-20">
												<text style="color: #333;"  class="u-font-weight">
													<text style="margin-right: 10upx;">{{item1.sortNo}}.</text>

													{{item1.drugName}}</text>
<!--												<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[val.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,6,'4','5','6',].includes(item1.drugProcessNode)">-->
<!--													{{`${{4:'多药', 5:'少药', 6:'破损',}[item1.drugProcessNode]}X${item1.exceptionDrugNum}${item1.drugUnit}`}}-->
<!--												</text>-->
												<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[item1.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,'4','5',].includes(item1.drugProcessNode) && item1.exceptionDrugNum!=0">
													{{`${{4:'多药', 5:'少药', 6:'破损',}[item1.drugProcessNode]}X${item1.exceptionDrugNum}${item1.drugUnit}`}}
												</text>
												<!--	<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[val.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,6,'4','5','6',].includes(val.drugProcessNode)">-->
												<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[item1.drugProcessNode]}`}"  class="u-font-24"
															v-if="[6,'6',].includes(item1.drugProcessNode) && item1.exceptionDrugNum!=0 && item1.listDocumentVO"  @click.stop="showDrugPictrue(item1.listDocumentVO)">
													<u-icon name="photo" color="#2979ff" size="28"></u-icon>
													{{`${{4:'多药', 5:'少药', 6:'破损',}[item1.drugProcessNode]}X${item1.listDocumentVO && item1.listDocumentVO.length}${item1.drugUnit}`}}
												</text>
												<u-checkbox v-model="item1.checked" :name="item1.dispensingOrderDrugId"
																		:disabled="item.packageFlag==1?false:true"
																		@change="checkboxChildrenChange" style="margin-right: 30upx;"
																		v-if="status==1"></u-checkbox>
											</view>
											<view class="u-flex ">
												<view class="u-flex-4">
													<view class="drug-summary u-padding-20 u-font-26 ">
														<view class="u-padding-bottom-20">
															<text class="u-margin-right-20">{{item1.drugSpecification}}</text>
															<text>{{item1.drugOrigin.substr(0,4)}}</text>
														</view>
													</view>
												</view>
												<view class="u-flex-1 u-font-weight u-font-36" style="color: #333;">
													X <text>{{item1.normalDrugNum}}{{item1.drugUnit}}</text>
												</view>
											</view>
										</view>
									</view>
								</u-checkbox-group>
								<u-checkbox-group :size="30" :width="'auto'" :wrap="false" :activeColor="'#2c9ef7'" v-if="item.showType==2"  style="width: 100%;">
									<view class="thumb-box" v-for="(item1, index1) in item.drugVOList" :key="index1">
										<view class="thumb-body u-border-bottom" >
											<view class="drug-title  u-padding-20">
												<text style="color: #333;"  class="u-font-weight">
													<text style="margin-right: 10upx;">{{item1.sortNo}}.</text>
													{{item1.drugName}}</text>
												<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[item1.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,'4','5',].includes(item1.drugProcessNode) && item1.exceptionDrugNum!=0">
													{{`${{4:'多药', 5:'少药', 6:'破损',}[item1.drugProcessNode]}X${item1.exceptionDrugNum}${item1.drugUnit}`}}
												</text>
												<!--	<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[val.drugProcessNode]}`}"  class="u-font-24" v-if="[4,5,6,'4','5','6',].includes(val.drugProcessNode)">-->
												<text :style="{color:`#${{4:'ff5d5d', 5:'ff9021', 6:'748fbc',}[item1.drugProcessNode]}`}"  class="u-font-24"
															v-if="[6,'6',].includes(item1.drugProcessNode) && item1.exceptionDrugNum!=0 && item1.listDocumentVO"  @click.stop="showDrugPictrue(item1.listDocumentVO)">
													<u-icon name="photo" color="#2979ff" size="28"></u-icon>
													{{`${{4:'多药', 5:'少药', 6:'破损',}[item1.drugProcessNode]}X${item1.listDocumentVO && item1.listDocumentVO.length}${item1.drugUnit}`}}
												</text>
												<u-checkbox v-model="item1.checked" :name="item1.dispensingOrderDrugId"
																		:disabled="item.packageFlag==1?false:true"
																		@change="checkboxChildrenChange" style="margin-right: 30upx;"
																		v-if="status==1"></u-checkbox>
											</view>
											<view class="u-flex ">
												<view class="u-flex-4">
													<view class="drug-summary u-padding-20 u-font-26 ">
														<view class="u-padding-bottom-20">
															<text class="u-margin-right-20">{{item1.drugSpecification}}</text>
															<text>{{item1.drugOrigin.substr(0,4)}}</text>
														</view>
													</view>
												</view>
												<view class="u-flex-1 u-font-weight u-font-36" style="color: #333;">
													X <text>{{item1.normalDrugNum}}{{item1.drugUnit}}</text>
												</view>
											</view>
										</view>
									</view>
								</u-checkbox-group>
							</view>
						</view>
					</u-checkbox-group>
				</view>
			</scroll-view>
		</view>

		<view class="bottom-bar" v-if="status==1">
			<u-checkbox v-model="allcChecked" :disabled="false" @change="selectAllChange"
				class="u-margin-left-20">全部</u-checkbox>
			<u-button class="bar-button" @click="btnClickNot" data-name="3333" :loading="loading" :plain="false"
				:shape="'square'" :ripple="true" :hairLine="false" :type="'error'">验收不合格</u-button>
			<u-button class="bar-button" @click="btnClick" data-name="33322" :loading="loading" :plain="false"
				:shape="'square'" :ripple="true" :hairLine="false" :type="'primary'">验收合格</u-button>
		</view>

		<u-modal ref="uModal" v-model="allChecksShow" title="验收确认" :show-cancel-button="true" :show-title="true"
						 :async-close="false" @confirm="checkConfirm" content="确认此单据所有药品验收完毕，一经确认，无法更改结果">
		</u-modal>
		<u-modal ref="uModal" v-model="allChecksShow_not" title="验收确认" :show-cancel-button="true" :show-title="true"
						 :async-close="false" @confirm="handleConfirm" content="确认此单据所有药品验收完毕，一经确认，无法更改结果">
		</u-modal>

		<u-modal ref="uModal" v-model="finishShow" title="验收完毕" :show-cancel-button="true" :show-title="true"
						 :async-close="false" @confirm="finishConfirm" content="此单据所有药品验收完毕">
		</u-modal>


		<u-action-sheet :list="actionSheetList" v-model="showActionSheet" @click="actionSheetSelect"></u-action-sheet>

<!--		<drugInfo v-if="showPopup" @close="showPopup = false" :type="actionSheetType" :info="drugInfo"></drugInfo>-->
		<drugInfo v-if="showPopup" @close="popupClose" :type="actionSheetType" :drug-info="drugDetail" :drug-list="checkDrugList" :allChecked="allcChecked"></drugInfo>
		<u-mask :show="showImg" @click="showImg = false">
			<view class="img-warp" @tap.stop>
				<u-swiper :list="drugImgList" style="width:100%;" name="documentUrl" imgMode="aspectFit" height="100vh"></u-swiper>
			</view>
		</u-mask>

		<view class="btn" @tap="toTop" :style="{'display':(flag===false? 'none':'block')}">
			<u-icon name="arrow-upward" :size="40" class="cuIcon-top"></u-icon>
		</view>


		<u-modal ref="uModal" v-model="showMedicineBoxCode" title="温馨提示" :show-cancel-button="false" :show-title="true"

				 :async-close="false" @confirm="showMedicineBoxCode = false" >
			<view class="slot-content">
				<view style="width: 100%;line-height: 30upx;font-size: 24upx;">
					该单据有多个药箱（{{drugDetail.dispensingOrderBoxVOList&& drugDetail.dispensingOrderBoxVOList.map(x=>{return x.medicineBoxCode}).join(',')}}），请勿遗漏
				</view>
			</view>
		</u-modal>
	</view>
</template>
<script>
	import classifyData from '@/common/classify.data.js';
	import drugInfo from "./components/drugInfo";
	import liuyunoTabs from "@/components/liuyuno-tabs/liuyuno-tabs.vue";
	import {
		forEach
	} from "../../../common/libs/underscore";
	import {
		nextTick
	} from "vue";
	export default {
		components:{drugInfo,liuyunoTabs},
		data() {
			return {
				scrollTop: 0, //tab标题的滚动条位置
				oldScrollTop: 0,
				current: 0, // 预设当前项的值
				menuHeight: 0, // 左边菜单的高度
				menuItemHeight: 0, // 左边菜单item的高度
				itemId: '', // 栏目右边scroll-view用于滚动的id
				tabbar: classifyData,
				menuItemPos: [],
				arr: [],
				scrollRightTop: 0, // 右边栏目scroll-view的滚动条高度
				timer: null, // 定时器
				keyword: undefined,
				tabcurrent: 0,
				tablist: [{
					name: '待验收',
					count:0,
				},
					{
						name: '验收合格',
						count:0,
					},
					{
						name: '验收不合格',
						count:0,
					},
				],
				tablistNew: [{
					name: '待验收',
					count:0,
				},
					{
						name: '验收合格',
						count:0,
					},
					{
						name: '验收不合格',
						count:0,
					},
					{
						name: '原始数据',
						count: 0
					},
				],

				isshow: false,
				loading: false,
				allcChecked: false,
				allChecksShow_not:false,
				allChecksShow:false,
				finishShow:false,
				showActionSheet:false,
				showPopup:false,
				actionSheetType:'0',
				actionSheetList: [
					{text: '验收多药', id:1,},
					{text: '验收少药', id:2,},
					{text: '验收破损',id:3,}],
				status:1,
				drugInfo:{},
				drugDetail:{},
				dispensingOrderId:'',
				parentQueryType:'',
				drugNameAndOrigin: undefined,
				checkDrugIdArr:[],
				drugCheckedList:[],
				dispensingOrderNo:undefined,
				allDrugData:[],
				checkDrugList:[],
				showImg:false,
				drugImgList:[],
				flag: false,
				showScroll: false,
				showModal: true,
				showMedicineBoxCode: false,
				dispensingOrderCategory:undefined,
			}
		},
		onLoad(options) {
			// this.dispensingOrderNo = options.dispensingOrderNo;
			this.dispensingOrderId = options.dispensingOrderId;
			this.parentQueryType=options.type;
			this.dispensingOrderCategory = options.dispensingOrderCategory ? options.dispensingOrderCategory : 1;

			if(this.parentQueryType==0){
				// this.queryStage=3
				this.status=1
			}else if(this.parentQueryType==1){
				// this.queryStage=3
				this.status=1
			}else{
				this.status = 2
			}
			this.Getlist();
			this.Getlist(1);
			if (this.dispensingOrderCategory == 2) {
				this.tablist = this.tablistNew
				this.Getlist3();
			}
		},
		onReady() {
			this.getMenuItemTop()
		},
		methods: {
			// 返回顶部
			toTop() {
				var that = this
				this.showScroll = true
				this.$nextTick(function() {
					this.scrollRightTop = this.scrollRightTop === 0 ? 1 : 0
				})
				uni.pageScrollTo({
					scrollTop: 0,
					duration: 100,
					success: () => {
						setTimeout(() => {
							that.showScroll = false
						}, 200)
					}
				});
			},
			onPageScroll(e) { //根据距离顶部距离是否显示回到顶部按钮
				console.log(e);
				if (e.scrollTop > 10) { //当距离大于10时显示回到顶部按钮
					this.flag = true
				} else {
					this.flag = false
				}

				let number = 277
				this.status == 2 ? number = 168 : number = 277
				if (e.scrollTop >= number) {
					this.showScroll = true
				}
				if (e.scrollTop == 0) {
					this.showScroll = false
				}
			},
			Getlist(type) {
				var that = this;
				let postUrl = '';
				let orderProcessNode = '';
				switch (that.tabcurrent) {
					case 0: //
						orderProcessNode = 6;
						break;
					case 1: //
						orderProcessNode = 8;
						break;
					case 2: //
						orderProcessNode = 9;
						break;
				}
				if(that.parentQueryType==0 || that.parentQueryType==1){
					if([0,1,'0','1'].includes(that.tabcurrent)){
						postUrl = '/nurseManagement/detailsAcceptanceDocuments'
					}
					if(that.tabcurrent == 1 || type==1){
						postUrl = '/nurseManagement/detailsAcceptanceDocumentsQualified'
					}
					if(that.tabcurrent == 2){
						postUrl = '/nurseManagement/detailsAcceptanceDocumentsUnqualified'
					}
				}
				if(that.parentQueryType==2 ){
					postUrl = '/nurseManagement/detailsQualifiedAcceptanceDocumentsResults'
				}
				if(that.parentQueryType==3 ){
					postUrl = '/nurseManagement/detailsUnQualifiedAcceptanceDocumentsResults'
				}

				this.$request.post(postUrl, {
					dispensingOrderId: that.dispensingOrderId,
					acceptanceUserId:uni.getStorageSync('memberInfo').userId,
					drugName: that.drugNameAndOrigin,
					dispensingOrderCategory:that.dispensingOrderCategory,
					orderProcessNode: orderProcessNode,
						})
						.then(res => {
							// console.log(res);

							if (res.data.drugListTypeVOList && res.data.drugListTypeVOList.length > 0) {
								let num = 0;
								res.data.drugListTypeVOList.forEach((item, key) => {
									item.checkAllIds = []
									if (item.showType == 1) { //汇总
										if (item.bedNumberGroupingDTOList && item.bedNumberGroupingDTOList.length > 0) {
											item.bedNumberGroupingDTOList.forEach((element, index) => {
												if (element.drugVOList && element.drugVOList.length > 0) {
													element.drugVOList.forEach((v, k) => {
														num++
														v.sortNo = num
													});
												}
											});
										}
									} else {
										if (item.drugVOList && item.drugVOList.length > 0) {
											item.drugVOList.forEach((element, index) => {
												num++
												element.sortNo = num
											});
										}
									}
								})
							}
							if(type !=1){
								that.drugDetail = res.data
							}
							if (that.tabcurrent == 0 && that.parentQueryType==0 ) {

								// if((!res.data.drugListTypeVOList || res.data.drugListTypeVOList.length==0) && that.drugNameAndOrigin){
								// 	that.finishShow=true
								// }
								that.tablist[0].count = that.drugDetail.quantityToBeAccepted
								// console.log(that.tablist[0].count);
							}
							if (that.tabcurrent == 1 || type==1) {
								that.tablist[1].count = that.drugDetail.quantityAcceptanceQualified
							}
							if(res.data.dispensingOrderBoxVOList && res.data.dispensingOrderBoxVOList.length>1 && (that.parentQueryType == 1 || that.parentQueryType==0) ){
								if(that.showModal){
									that.showModal = false;
									that.showMedicineBoxCode = true;
								}
							}
							that.processData();
						})
						.catch(err => {
							console.log(err,"error");
							uni.reLaunch({
								url: '/pages/nurse/drugAcceptance/index'
							});
						});
			},
			//获取原始数据详情
			Getlist3() {
				var that = this;
				this.$request
						.post('/nurseManagement/queryDetail', {
							dispensingOrderNo: that.dispensingOrderNo,
							drugNameAndOrigin: that.drugNameAndOrigin,
							queryInitialFlag: 0,
						})
						.then(res => {
							if (that.tabcurrent == 2) {
								if (res.data.drugListTypeVOList && res.data.drugListTypeVOList.length > 0) {
									let num = 0;
									res.data.drugListTypeVOList.forEach((item, key) => {
										item.checkAllIds = []
										if (item.showType == 1) { //汇总
											if (item.bedNumberGroupingDTOList && item.bedNumberGroupingDTOList.length > 0) {
												item.bedNumberGroupingDTOList.forEach((element, index) => {
													if (element.drugVOList && element.drugVOList.length > 0) {
														element.drugVOList.forEach((v, k) => {
															num++
															v.sortNo = num
														});
													}
												});
											}
										} else {
											if (item.drugVOList && item.drugVOList.length > 0) {
												item.drugVOList.forEach((element, index) => {
													num++
													element.sortNo = num
												});
											}
										}
									})
								}
								that.drugDetail = res.data
							}
						})
						.catch(err => {
							uni.showToast({
								title: '数据错误，请联系管理员',
								icon:'none',
								success: function(res2) {
									setTimeout(function() {
										uni.navigateBack()
									}, 1500);
								}
							});
						});
			},

			//搜索数据变更
			change(value) {
				// 搜索框内容变化时，会触发此事件，value值为输入框的内容
				console.log(value);
				//根据药品名称获取药品单号 锚点定位
				this.drugNameAndOrigin = value
				this.Getlist()
			},
			clear() {
				this.Getlist()
			},
			tabChange(index) {
				console.log(index);
				this.drugNameAndOrigin = undefined;
				this.tabcurrent = index;
				if(index != 0){
					this.status = 3;
				}else{
					this.status = 1;
				}
				this.Getlist()
				this.scrollRightTop = this.oldScrollTop;
				this.$nextTick(function() {
					this.scrollRightTop=0;
				})
			},

			checkboxChange(e) {
				var that = this
				// console.log(that.drugDetail,'>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>',e);
				that.drugDetail.drugListTypeVOList.forEach((item, index) => {
					if (e.name == item.drugListTypeCode) {
						if (e.value) {
							if (item.showType == 1) {
								// console.log(item,'drugListTypeVOList');
								item.bedNumberGroupingDTOList.forEach(element => {
									console.log(element,'drugListTypeVOList>>>>>>>>>>bedNumberGroupingDTOList');
									if (element.drugVOList && element.drugVOList.length > 0) {
										element.drugVOList.forEach(val => {
											// console.log(val,'bedNumberGroupingDTOList>>>>drugVOList');
											that.checkDrugIdArr.push(val.dispensingOrderDrugId)
											// let {drugName,drugCode,drugFrequency,dispensingOrderDrugId,drugOrigin,drugSingleDose,drugSpecification,drugUnit,drugUnitPrice} = val
										});
									}
								});
							} else {
								item.drugVOList.forEach(element => {
									that.checkDrugIdArr.push(element.dispensingOrderDrugId)
									// let {drugName,drugCode,drugFrequency,dispensingOrderDrugId,drugOrigin,drugSingleDose,drugSpecification,drugUnit,drugUnitPrice} = element
								});
							}
						} else {
							if (item.showType == 1) {
								item.bedNumberGroupingDTOList.forEach(element => {
									if (element.drugVOList && element.drugVOList.length > 0) {
										element.drugVOList.forEach(val => {
											that.checkDrugIdArr.splice(that.checkDrugIdArr.indexOf(val.dispensingOrderDrugId), 1)
										});
									}
								});
							} else {
								item.drugVOList.forEach(element => {
									that.checkDrugIdArr.splice(that.checkDrugIdArr.indexOf(element.dispensingOrderDrugId), 1)

								});
							}
						}

					}
				});
				that.dealDrugCheck()
			},
			//暂没用到
			checkboxGroupChange(e) {
				console.log(e, 'e8');
				var that = this
				that.checkDrugIdArr = []
				that.drugDetail.drugListTypeVOList.forEach((item, index) => {
					if (e.findIndex((v) => (v === item.drugListTypeCode)) != -1) {
						if (item.showType == 1) {
							item.itemList.forEach(element => {
								if (element.itemList && element.itemList.length > 0) {
									element.itemList.forEach(val => {
										that.checkDrugIdArr.push(val.dispensingOrderDrugId)
									});
								}
							});
						} else {
							item.itemList.forEach(element => {
								that.checkDrugIdArr.push(element.dispensingOrderDrugId)
							});
						}
					}
				});
				that.dealDrugCheck()
			},
			checkboxChildrenChange(e) {
				var that = this
				console.log(e, 'e');
				if (that.checkDrugIdArr.findIndex((v) => (v == e.name)) != -1) {
					that.checkDrugIdArr.splice(that.checkDrugIdArr.indexOf(e.name), 1)
				} else {
					that.checkDrugIdArr.push(e.name)
				}
				console.log('checkDrugIdArr', that.checkDrugIdArr);
				that.dealDrugCheck()
			},

			dealDrugCheck() {
				var that = this
				that.drugDetail.drugListTypeVOList.forEach((item, index) => {
					let childDrugIdArr = []
					if (item.showType == 1) {
						item.bedNumberGroupingDTOList.forEach(element => {
							if (element.drugVOList && element.drugVOList.length > 0) {
								element.drugVOList.forEach(val => {
									childDrugIdArr.push(val.dispensingOrderDrugId)
									if (that.checkDrugIdArr.findIndex((v) => (v === val
											.dispensingOrderDrugId)) != -1) {
										console.log(val,'<<<<<<<<<<<<<<<<<<<<<<<<<<<<');
										val.checked = true
									} else {
										val.checked = false
									}
								});
							}
						});
					} else {
						item.drugVOList.forEach(element => {
							console.log(element,'??????????????????');
							childDrugIdArr.push(element.dispensingOrderDrugId)
							if (that.checkDrugIdArr.findIndex((v) => (v === element
									.dispensingOrderDrugId)) != -1) {
								element.checked = true
							} else {
								element.checked = false
							}
						});
					}
					let set = new Set(that.checkDrugIdArr);
					that.checkDrugIdArr = Array.from(set);
					let hasAllId = childDrugIdArr.every((value) => that.checkDrugIdArr
							.includes(value))
					// console.log('childDrugIdArr', childDrugIdArr);
					// console.log('checkDrugIdArr', that.checkDrugIdArr);
					console.log('hasAllId', hasAllId);
					item.checked = hasAllId
					that.checkDrugIdArr.length > 0 && that.checkDrugIdArr.length == that.drugDetail.quantityToBeAccepted ? this
							.allcChecked = true : this.allcChecked = false
					console.log(that.checkDrugIdArr.length > 0 , that.checkDrugIdArr.length , that.drugDetail,'>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>');
				});
				this.$forceUpdate()
			},
			//底部全选
			selectAllChange(e) {
				this.allcChecked = !this.allcChecked
				var that = this
				that.drugDetail.drugListTypeVOList.forEach((item, index) => {
					if (e.value) {
						if (item.showType == 1) {
							item.bedNumberGroupingDTOList.forEach(element => {
								if (element.drugVOList && element.drugVOList.length > 0) {
									element.drugVOList.forEach(val => {
										that.checkDrugIdArr.push(val.dispensingOrderDrugId)
									});
								}
							});
						} else {
							item.drugVOList.forEach(element => {
								that.checkDrugIdArr.push(element.dispensingOrderDrugId)
							});
						}
					} else {
						that.checkDrugIdArr = []
					}
				});
				that.dealDrugCheck()

			},
			btnClick() {
				console.log(this.allcChecked,'11111111111111');
				if (this.allcChecked) {
					this.allChecksShow=true
					return
				}
				this.checkConfirm();
			},
			btnClickNot() {
				if(this.checkDrugIdArr.length>0){
					this.showActionSheet=true
				}else{
					uni.showToast({
						title: '请先选择药品',
						duration: 500
					});
				}
			},
			actionSheetSelect(index){
				// console.log('index',this.actionSheetList[index].text,this.checkDrugIdArr);
				let arr = [];
				this.allDrugData.map(drug =>{
					if(drug.checked){
						drug.num = 0;
						arr.push(drug)
					}
				})
				switch (index) {
					case 1: //多药
						break;
					case 2: //少药
						break;
					case 3: //破损
						break;
				}
				// this.drugInfo =
				this.checkDrugList = arr;
				this.actionSheetType = this.actionSheetList[index].id;
				// console.log(this.actionSheetType,111111111111);
				if(arr.length>0){
					this.showPopup = true;
				}else{
					uni.showToast({
						title: '请选择至少一个药品进行操作',
						icon:'none',
					});

				}
			},
			handleConfirm(){
				this.showPopup = true;
			},

			finishConfirm(){
				this.finishShow=false
				uni.redirectTo({
					url:'/pages/nurse/drugAcceptance/index'
				})
			},
			checkConfirm(){
				let that = this;
				let arr = []
				that.allDrugData.map(drug =>{
					console.log(drug.checked);
					if(drug.checked ){
							arr.push(drug)
						}
					})
				that.checkDrugList = arr;
				if(arr.length>0){
					this.$request.post('/nurseManagement/qualifiedAcceptance', {
						drugVOList:that.checkDrugList,
						dispensingOrderId: that.drugDetail.dispensingOrderId,
					})
							.then(res => {
								console.log(res);
								uni.showToast({
									title: '验收成功',
									success: function (res2) {
										setTimeout(function () {
											that.checkDrugIdArr = []
											// that.Getlist()
											if(res.data.acceptanceCompletedFlag){
												that.finishConfirm();
											}else{
												that.Getlist()
												that.Getlist(1)
											}
										}, 500);
									}
								});
							})
							.catch(err => {
							});
				}else{
					uni.showToast({
						title: '请选择至少一个药品进行操作',
						icon:'none',
					});
				}

			},
			// 点击左边的栏目切换
			async swichMenu(index) {
				if (this.arr.length == 0) {
					await this.getMenuItemTop();
				}
				if (index == this.current) return;
				this.scrollRightTop = this.oldScrollTop;
				this.$nextTick(function() {
					this.scrollRightTop = this.arr[index];
					this.current = index;
					this.leftMenuStatus(index);
				})
			},
			// 获取一个目标元素的高度
			getElRect(elClass, dataVal) {
				new Promise((resolve, reject) => {
					const query = uni.createSelectorQuery().in(this);
					query.select('.' + elClass).fields({
						size: true
					}, res => {
						// 如果节点尚未生成，res值为null，循环调用执行
						if (!res) {
							setTimeout(() => {
								this.getElRect(elClass);
							}, 10);
							return;
						}
						this[dataVal] = res.height;
						resolve();
					}).exec();
				})
			},
			// 观测元素相交状态
			async observer() {
				this.tabbar.map((val, index) => {
					let observer = uni.createIntersectionObserver(this);
					// 检测右边scroll-view的id为itemxx的元素与right-box的相交状态
					// 如果跟.right-box底部相交，就动态设置左边栏目的活动状态
					observer.relativeTo('.right-box', {
						top: 0
					}).observe('#item' + index, res => {
						if (res.intersectionRatio > 0) {
							let id = res.id.substring(4);
							this.leftMenuStatus(id);
						}
					})
				})
			},
			// 设置左边菜单的滚动状态
			async leftMenuStatus(index) {
				this.current = index;
				// 如果为0，意味着尚未初始化
				if (this.menuHeight == 0 || this.menuItemHeight == 0) {
					await this.getElRect('menu-scroll-view', 'menuHeight');
					await this.getElRect('u-tab-item', 'menuItemHeight');
				}
				// 将菜单活动item垂直居中
				this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;
			},
			// 获取右边菜单每个item到顶部的距离
			getMenuItemTop() {
				new Promise(resolve => {
					let selectorQuery = uni.createSelectorQuery();
					selectorQuery.selectAll('.class-item').boundingClientRect((rects) => {
						// 如果节点尚未生成，rects值为[](因为用selectAll，所以返回的是数组)，循环调用执行
						if (!rects.length) {
							setTimeout(() => {
								this.getMenuItemTop();
							}, 10);
							return;
						}
						rects.forEach((rect) => {
							// 这里减去rects[0].top，是因为第一项顶部可能不是贴到导航栏(比如有个搜索框的情况)
							this.arr.push(rect.top - rects[0].top);
							resolve();
						})
					}).exec()
				})
			},
			// 右边菜单滚动
			async rightScroll(e) {
				this.oldScrollTop = e.detail.scrollTop;
				if (this.arr.length == 0) {
					await this.getMenuItemTop();
				}
				if (this.timer) return;
				if (!this.menuHeight) {
					await this.getElRect('menu-scroll-view', 'menuHeight');
				}
				setTimeout(() => { // 节流
					this.timer = null;
					// scrollHeight为右边菜单垂直中点位置
					let scrollHeight = e.detail.scrollTop + this.menuHeight / 2;
					for (let i = 0; i < this.arr.length; i++) {
						let height1 = this.arr[i];
						let height2 = this.arr[i + 1];
						// 如果不存在height2，意味着数据循环已经到了最后一个，设置左边菜单为最后一项即可
						if (!height2 || scrollHeight >= height1 && scrollHeight < height2) {
							this.leftMenuStatus(i);
							return;
						}
					}
				}, 10)
			},
			processData(){
				let that = this;
				this.allDrugData = [];
				// console.log(that.drugDetail,'????????????????');
				that.drugDetail.drugListTypeVOList?.forEach((item, index) => {
					if (item.showType == 1) {
						item.bedNumberGroupingDTOList.forEach(element => {
							if (element.drugVOList && element.drugVOList.length > 0) {
								element.drugVOList.forEach(val => {
									// console.log(element,'>>>>>>>>>>>>>>>>');
									this.$set(val,'showType',item.showType)
									this.allDrugData.push(val);
								});
							}
						});
					} else {
						item.drugVOList.forEach(element => {
							this.$set(element,'showType',item.showType)
							this.allDrugData.push(element);
						});
					}
				});
				// console.log(that.allDrugData,'allDrugData');
			},
			popupClose(){
				this.showPopup = false;
				this.Getlist();
				this.Getlist(1);
				this.checkDrugIdArr = [];
				this.allcChecked = false;
			},

			showDrugPictrue(img){
				let that = this;
				that.drugImgList= img;
				const urls = that.drugImgList.map((item) => {
					return this.getSrc(item)
				})
				uni.previewImage({
					// current: url,
					urls
				})
			},
			// 获取图片的路径
			getSrc(item) {
				return uni.$u.test.object(item)
						? (item['documentUrl']) || item.src
						: item
			},
		}
	}
</script>

<style lang="scss" scoped>
	.u-wrap {
		height: auto;
		/* #ifdef H5 */
		height: calc(100vh - var(--window-top));
		/* #endif */
		display: flex;
		flex-direction: column;
		.suggest-top {
			position: sticky !important;
			z-index: 999;
			top: var(--window-top);
			width: 100%;
		}
	}

	.main-head {
		width: 100%;
		background-repeat: no-repeat;
		background: url(@/static/images/headTop.png) no-repeat center center;
		background-size: 100% 100%;
		position: fixed;
		z-index: 20;
		top: 0px;

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

		.main-head-content {
			width: 92%;
			margin: 0 auto;

			.u-demo-title {
				padding-top: 30upx;
				color: #ffffff;
				height: 150upx;
				line-height: 120upx;
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
		background: #fff;
		margin-top: 255upx;
		.tab-content {
			background: #fff;
			width: 100%;

			.tab-body {
				width: 100%;
			}
		}

		.content-body {
			width: 94%;
			margin: 0 auto;
		}

	}

	.question-body-summary {
		padding: 20upx 20upx 0 20upx;
		border-radius: 10upx;
		background-color: #f7fbff;
		margin-top: 20upx;
		margin-bottom: 20upx;

		.question-body-name {
			height: 80upx;
			line-height: 80upx;
			display: flex;
			justify-content: space-between;
			font-size: 34upx;
		}



		.question-body-data-summary {
			font-size: 34upx;
			height: 100upx;
			line-height: 100upx;
			margin-left: 24upx;

		}

		.question-info {
			padding: 6upx 20upx;
			background-color: #f4f5f6;
			border-radius: 5px;
			font-size: 28upx;
			margin-left: 24upx;
		}

		.question-body-type {
			font-size: 26upx;
			color: #7b8293;
		}
	}

	.u-menu-wrap {
		flex: 1;
		display: flex;
		/*overflow: hidden;*/
		height: calc(100vh - var(--window-bottom) - var(--window-top) - 128px);
	}

	.u-search-inner {
		background-color: rgb(234, 234, 234);
		border-radius: 100rpx;
		display: flex;
		align-items: center;
		padding: 10rpx 16rpx;
	}

	.u-search-text {
		font-size: 26rpx;
		color: $u-tips-color;
		margin-left: 10rpx;
	}

	.u-tab-view {
		width: 200rpx;
		height: 100%;
	}

	.u-tab-item {
		height: 110rpx;
		background: #f6f6f6;
		box-sizing: border-box;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 26rpx;
		color: #444;
		font-weight: 400;
		line-height: 1;
	}

	.u-tab-item-active {
		position: relative;
		color: #000;
		font-size: 30rpx;
		font-weight: 600;
		background: #fff;
	}

	.u-tab-item-active::before {
		content: "";
		position: absolute;
		border-left: 4px solid $u-type-primary;
		height: 32rpx;
		left: 0;
		top: 39rpx;
	}

	.u-tab-view {
		height: 100%;
	}

	.right-box {
		background-color: rgb(250, 250, 250);
	}

	.page-view {
		padding: 0 16rpx 16rpx;
	}

	.class-item {
		width: 100%;
		margin-bottom: 30rpx;
		background-color: #fff;
		padding: 0 16rpx 16rpx;
		border-radius: 8rpx;
	}

	.class-item:last-child {
		/*min-height: 100vh;*/
		margin-bottom: 120upx;
	}

	.item-title {
		margin-top: 10upx;
		font-size: 26rpx;
		color: $u-main-color;
		font-weight: bold;
		display: flex;
		justify-content: space-between;
		height: 54upx;

	}

	.drug-title {
		display: flex;
		justify-content: space-between;
	}

	.item-menu-name {
		font-weight: normal;
		font-size: 24rpx;
		color: $u-main-color;
	}

	.item-container {
		display: flex;
		flex-wrap: wrap;
	}

	.thumb-box {
		width: 100%;
		margin-top: 20rpx;

		.thumb-parent-info {
			color: #2c9ef7;
			display: flex;
			position: relative;
		}

		.thumb-parent-info::after {
			content: "";
			position: absolute;
			border-left: 4px solid $u-type-primary;
			height: 28rpx;
			left: 0;
			top: 8rpx;
		}


	}

	.item-menu-image {
		width: 120rpx;
		height: 120rpx;
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

	.bottom-bar {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 120upx;
		line-height: 120upx;
		padding: 10upx 20upx;
		background-color: #fff;
		position: fixed;
		bottom: 0;
		width: 100%;
		z-index: 1000;

		.bar-button {
			width: 36%
		}

	}

	.btn {
		position: fixed;
		z-index: 9999;
		left: 16px;
		bottom: 100px;
		background-color: #2c9ef7;
		padding: 5px;
		display: none;
		border-radius: 50%;
		width: 100upx;
		height: 100upx;
		text-align: center;
		padding-top: 30upx;

	}

	.btn .cuIcon-top {
		color: #FFFFFF;
	}
	.slot-content {
		font-size: 28rpx;
		color: $u-content-color;
		padding-left: 30rpx;
		word-break: break-all;
	}
	::v-deep{

		.u-mask{
			display: flex;
			flex-direction: column;
			justify-content: center;
		}
	}
	.img-warp{
		/*display: flex;*/
		/*flex-direction: column;*/
		/*justify-content: center;*/
	}
</style>
