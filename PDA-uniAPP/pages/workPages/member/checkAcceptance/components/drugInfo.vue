<template>
	<view class="popup-main">
		<u-popup v-model="showPopup" mode="bottom" border-radius="14" height="80%"
						 @close="$emit('close')">
			<scroll-view scroll-y style="height: calc(100% - 140upx);width: 100%; " >
				<view class="durg-list border-bottom-2" v-for="(item,index) in drugList">
					<view class="drug-name u-font-weight">{{item.drugName}}</view>
					<view class="drug-list-content ">
						<view class="baseInfo ">
							<view class="drug-use u-padding-bottom-20 u-font-26 u-flex" v-if="item.bedNo">
								<view class="drug-use-item">床号：{{item.bedNo}}</view>
								<view class="drug-use-item u-margin-left-20">姓名：{{item.patientName}}</view>
							</view>
							<view class="drug-specifications u-padding-bottom-20 u-font-26">{{item.drugSpecification}}</view>
							<view class="drug-specifications u-padding-bottom-20 u-font-26">{{item.drugOrigin}}</view>
							<view class="drug-use u-padding-bottom-20 u-font-26 u-flex">
								<view class="drug-use-item">一次用量：{{item.drugSingleDose}}</view>
								<view class="drug-use-item u-margin-left-20">频次：{{item.drugFrequency}}</view>
							</view>
						</view>
						<view class="drug-num ">
							<view class="drug-num-title ">{{actionSheetList[type]}}数量</view>
							<u-number-box v-model="item.num" @change="valChange" size="40" bg-color="#ffffff"></u-number-box>
						</view>
					</view>

					<view class="drug-img " v-if="type == 3">
						<view class="drug-img-title u-font-weight">上传图片</view>
						<view class="wrap">
							<scroll-view scroll-x style="height: 100%;width: 100%; " >
								<view class="pre-box" v-if="!showUploadList">
									<view class="pre-item" v-for="(img, imgIndex) in item.lists" :key="index">
										<image class="pre-item-image" :src="img.url" mode="aspectFit"></image>
										<view class="u-delete-icon" @tap.stop="deleteItem(index)">
											<u-icon name="close" size="20" color="#ffffff"></u-icon>
										</view>
										<u-line-progress v-if="img.progress > 0 && !img.error" :show-percent="false" height="16" class="u-progress"
																		 :percent="img.progress"></u-line-progress>
									</view>
								</view>
							</scroll-view>
							<u-upload @on-choose-fail="onChooseFail" :before-remove="beforeRemove" :ref="`uUpload${index}`"
												@on-success="onSuccessUpload"
												@on-change="onChangeUpload"
												@on-choose-complete="onChooseComplete($event,item)"
												:custom-btn="customBtn" :show-upload-list="showUploadList" :action="action"
												:auto-upload="autoUpload"
												:index="index"
												:source-type="['album', 'camera']"
												:show-progress="showProgress" :deletable="deletable"
												:max-count="maxCount" @on-list-change="onListChange">
								<view v-if="customBtn" slot="addBtn" class="slot-btn" hover-class="slot-btn__hover" hover-stay-time="150">
									<u-icon name="photo" size="60" :color="$u.color['lightColor']"></u-icon>
								</view>
							</u-upload>
						</view>
					</view>

				</view>
			</scroll-view>


			<u-gap height="40" bg-color="#bbb"></u-gap>
			<view class="btn-group">
				<view class="btn-item" @click="$emit('close')">取消</view>
				<view class="btn-item  btn-submit" @click="handleSubmit">{{['','退药','补药','确定'][type]}}</view>
			</view>
		</u-popup>
	</view>
</template>
<script>
	import $config from '@/common/config'
	export default {
		props:{
			type:{
				type:[String,Number],
			},
			drugInfo:{
				type:Object
			},
			drugList:{
				type:Array
			}
		},
		computed: {
			// i18n() {
			// 	return this.$t('drugRepercussion');
			// }
		},
		data() {
			return {
				fileList: [],
				actionSheetList:{
					1:'多药',
					2:'少药',
					3:'破损',
				},
						// [
						// 	{text: '多药', id:1,},
						// 	{text: '少药', id:2,},
						// 	{text: '破损',id:3,}],
				// fileList: [{
				// 	url: 'http://pics.sc.chinaz.com/files/pic/pic9/201912/hpic1886.jpg',
				// 	error: false,
				// 	progress: 100
				// }],
				showUploadList: true,
				customBtn: false,
				autoUpload: true,
				showProgress: true,
				deletable: true,
				customStyle: false,
				maxCount: 9,
				lists: [], // 组件内部的文件列表
				showPopup:true,
				drugNum:0,
				action: `${$config.siteroot}/system/document/fastDFSUpload`, // 演示地址
				showUploadList: false,
				// 如果将某个ref的组件实例赋值给data中的变量，在小程序中会因为循环引用而报错
				// 这里直接获取内部的lists变量即可
			};
		},
		onReady() {
			// 得到整个组件对象，内部图片列表变量为"lists"
			this.drugList.map((item,index)=>{
				console.log(item);
				this.$set(item,'lists',this.$refs['uUpload'+index][0].lists)
			})
		},
		onLoad() {
			console.log(this.drugList,'onLoad',this.$refs['uUpload'+0]);
		},
		onShow() {
			console.log(this.drugList,'?????????',this.$refs['uUpload'+0]);

		},
		methods: {
			valChange(e){

			},
			deleteItem(index) {
				this.$refs.uUpload.remove(index);
			},

			onListChange(lists) {
				console.log('onListChange', lists);
			},
			beforeRemove(index, lists) {
				return true;
			},
			onChooseFail(e) {
				// console.log(e);
			},
			onChooseComplete(lists,item) {
				console.log(lists,111111111111111111111);
				this.$set(item,'lists',lists)

			},
			onChangeUpload(res, index, lists, name){
				// console.log(res, index, lists, name,'res, index, lists, name');

			},
			onSuccessUpload(data,index, lists, name){
				console.log(data,index, lists, name,'>>>>>>>>>>>>>>>>>>>>>>>>>',);
				// documentList.push()

				// this.$set(item,'documentIdList',lists)
				// console.log(this.drugList[index].listDocumentVO,'<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<');
			},
			handleSubmit(){
				var that = this;
				let postUrl = '';
				if(that.type==1){
					postUrl = '/nurseManagement/acceptanceOfMultipleDrugs'
				}
				if(that.type==2){
					postUrl = '/nurseManagement/acceptanceOfInsufficientDrugs'
				}
				if(that.type==3){
					// let listDocumentVO= [];
					// 	listDocumentVO.push({
					// 		documentName:lists[index].file.name
					// 		,documentSize:lists[index].file.size
					// 		,documentType:lists[index].file.type
					// 		,documentUrl:data.msg})
						// [name].listDocumentVO = listDocumentVO;
					console.log(that.drugList);
					// that.drugList = [that.drugList[0]];
					that.drugList.map(item=>{
						item.listDocumentVO = [];
							if(item.lists){
								let lists = item.lists;
								lists.map(list =>{
									item.listDocumentVO.push({
										documentName:list.file.name
										,documentSize:list.file.size
										,documentType:list.file.type
										,documentUrl:list.response.data.documentUrl})
								})
							}
						})

					postUrl = '/nurseManagement/acceptanceDamage'
				}
				this.$request.post(postUrl, {
					drugVOList:that.drugList,
					dispensingOrderId: that.drugInfo.dispensingOrderId,
							// drugProcessNode:{'多药':4, '少药':5, '破损':6, }[that.actionSheetList[that.type]],
							//
						})
						.then(res => {
							console.log(res);
							that.$emit('close')
							uni.showToast({
								title: `${['','退药','补药','确定'][that.type]}成功`,
								success: function(res2) {
									setTimeout(function() {

									}, 1500);
								}
							});
						})
						.catch(err => {});
			},
			checkConfirm(){
				console.log(222);
			},
		}
	};
</script>

<style lang="scss" scoped>
	.popup-main{
		height: 100%;
		position: absolute;
	}
	.border-bottom-2 {
		border-bottom: 8upx solid #f0f3f8;
	}
	.durg-list{
		/*padding:0 20upx;*/
		/*display: flex;*/
		.drug-list-content{
			width: 100%;
			/*margin: 0 20upx;*/
			padding: 0 20upx;
			display: flex;
			justify-content: space-between;
			border-bottom: 2upx dashed #f0f3f8;

		}
	}
	.baseInfo{
	}
	.drug-name{

		line-height: 80upx;
		height: 80upx;
		width: 100%;
		color: #000000;
		margin: 20upx 20upx 0;
	}
	.drug-img{
		/*padding:0 20upx;*/
		margin: 0 20upx;
		.drug-img-title{
			margin: 16upx 0 0;
		}
	}
	.drug-num{
		margin: 0 10upx 20upx 0;
		.drug-num-title{
			margin-bottom: 20upx;
		}
	}
	.btn-group{
		height: 100upx;
		width: 100%;
		display: flex;
		line-height: 100upx;
		.btn-item{
			flex: 1;
			text-align: center;
			line-height: 100upx;
		}
		.btn-submit{
			border-left: 4upx solid #f0f3f8;
			color: #2c9ef7;
		}

	}

	.wrap {
		width: 100%;
		padding: 24rpx 0;
		/*display: flex;*/
	}

	.slot-btn {
		width: 341rpx;
		height: 140rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		background: rgb(244, 245, 246);
		border-radius: 10rpx;
	}

	.slot-btn__hover {
		background-color: rgb(235, 236, 238);
	}

	.pre-box {
		display: flex;
		align-items: center;
		/*justify-content: space-between;*/
		/*flex-wrap: wrap;*/
	}

	.pre-item {
		/*flex: 0 0 48.5%;*/
		border-radius: 10rpx;
		height: 140rpx;
		width: 140rpx;
		/*overflow: hidden;*/
		position: relative;
		margin-bottom: 20rpx;
	}

	.u-progress {
		position: absolute;
		bottom: 10rpx;
		left: 8rpx;
		right: 8rpx;
		z-index: 9;
		width: auto;
	}

	.pre-item-image {
		width: 140rpx;
		height: 140rpx;
	}

	.u-delete-icon {
		position: absolute;
		top: 10rpx;
		right: 10rpx;
		z-index: 10;
		background-color: $u-type-error;
		border-radius: 100rpx;
		width: 44rpx;
		height: 44rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}
::v-deep{
	.u-numberbox{
		border: 1px solid #e6e6e6;
		border-radius: 10rpx ;

	}
	.u-icon-minus, .u-icon-plus{
		width: 60rpx;
	}
	.u-number-input{
		border-left: 1px solid #e6e6e6;
		border-right: 1px solid #e6e6e6;
	}

}
</style>
