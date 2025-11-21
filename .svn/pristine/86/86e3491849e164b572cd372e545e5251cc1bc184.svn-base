<template>
  <view class="popup-main">
    <u-popup v-model="showPopup" mode="bottom" border-radius="14" :mask-close-able="false"
             height="80%"
             @close="$emit('close')">
      <scroll-view scroll-y style="height: calc(100% - 140upx);width: 100%; " :show-scrollbar="true"
                   v-if="drugList.length>1">
        <view class="durg-list border-bottom-2" v-for="(item,index) in drugList">
          <view class="drug-name u-font-weight">
            <view style="word-wrap: break-word;" :style="{color:item.isInValidity==0?'#FF7373 !important':''}">{{item.drugName}}</view>
            <view style="width:200upx;text-align: right;">{{type == 1? `${item.pieces==null?'':'X'+item.pieces}${item.pieces==null?'':'件'}`:`X ${item.boxCount}件`}}</view>
          </view>
          <view class="drug-list-content">
            <view class="baseInfo">
              <view class="drug-use u-padding-bottom-20 u-font-26 u-flex">
                <view class="drug-use-item">规格：{{item.drugSpecs}}</view>
              </view>
              <view class="drug-use u-padding-bottom-20 u-font-26 u-flex">
                <view class="drug-use-item">批号：{{item.approvalNo}}</view>

              </view>
              <view class="drug-use u-padding-bottom-20 u-font-26 u-flex">
                <view class="drug-use-item" :style="{color:item.isInValidity==0?'#FF7373 !important':''}">效期：{{item.validDate}}</view>
              </view>
            </view>
            <view class="drug-num ">
              <!--							<view class="drug-num-title ">{{actionSheetList[type]}}数量</view>-->
              <u-number-box v-model="item.checkCount"
                            :key="`${item.boxNo}_${item.licenseNo}`"
                            :min="0"
                            @change="valChange($event,item)"
                            size="40" bg-color="#ffffff"></u-number-box>
            </view>
          </view>
          <view class="drug-specifications u-padding-bottom-20 u-font-26 ">生产企业：{{item.productionName}}</view>
        </view>
      </scroll-view>

      <view class="single_drug" v-if="drugList.length==1">
        <view class="title">
          <view :style="{color:drugList[0].isInValidity==0?'#FF7373 !important':''}">{{drugList[0].drugName}}</view>
<!--          <view>x {{drugList[0].distributeCount}} {{drugList[0].unit}}</view>-->
          <view> {{type == 1? `${drugList[0].pieces==null?'':'X'+drugList[0].pieces}${drugList[0].pieces==null?'':'件'}`:`X${drugList[0].boxCount}件`}}</view>
        </view>
        <view class="sub_title">
          配送企业：{{drugInfo.distributeName}}
        </view>
        <view class="info_item">
          发票号:{{drugInfo.invoiceNo}}
        </view>
        <view class="info_item">
          配送组号：{{drugInfo.groupNo}}
        </view>
      </view>
      <scroll-view scroll-y style="height: calc(100% - 540upx);width: 100%; " :show-scrollbar="true"
                   v-if="drugList.length==1">
        <view class="single_drug_item">
          <view class="label">药品分类</view>
          <view class="content">{{drugList[0].drugType}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label">规格</view>
          <view class="content">{{drugList[0].drugSpecs}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label">剂型</view>
          <view class="content">{{drugList[0].formName}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label">生产企业</view>
          <view class="content">{{drugList[0].productionName}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label">批号</view>
          <view class="content">{{drugList[0].approvalNo}}</view>
        </view>
        <view class="single_drug_item" :style="{color:drugList[0].isInValidity==0?'#FF7373 !important':''}">
          <view class="label">效期</view>
          <view class="content">{{drugList[0].validDate}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label">进价</view>
          <view class="content">{{drugList[0].price}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label">是否贵重</view>
          <view class="content">{{drugList[0].isExpensive}}</view>
        </view>
        <view class="single_drug_item">
          <view class="label" >批准文号</view>
          <view class="content">{{drugList[0].licenseNo}}</view>
        </view>
      </scroll-view>
      <view class="single_drug_num" v-if="drugList.length==1">
        <view class="num_box">
          <view class="num_title" >实收数量</view>
          <u-number-box v-model="drugList[0].checkCount"
                        :key="`${1}_${1}`"
                        :min="0"
                        @change="valChange($event,drugList[0])"
                        size="40" bg-color="#ffffff"></u-number-box>
<!--          <view class="num_unit" >{{drugList[0].unit}}</view>-->
        </view>
      </view>

      <!--			<u-gap height="20" bg-color="#f0f3f8"></u-gap>-->
      <view class="btn-group">
        <view class="btn-item" @click="$emit('close')">取消</view>
        <view class="btn-item  btn-submit" @click="handleConfirm">确定</view>
      </view>
    </u-popup>


    <u-modal ref="uModal" v-model="finishShow" title="验收提示" :show-cancel-button="true" :show-title="true"
             :async-close="false" @confirm="finishConfirm" content="检测到验收数量与实际数量不符，无法追溯数量异常的箱号，不允许合并验收">
    </u-modal>
    <u-modal ref="uModal" v-model="allChecksShow_not" title="验收确认" :show-cancel-button="true" :show-title="true"
             :async-close="false" @confirm="handleSubmit" content="确认此单据所有药品验收完毕，一经确认，无法更改结果">
    </u-modal>
  </view>
</template>
<script>
  import $config from '@/common/config'

  export default {
    props: {
      type: {
        type: [String, Number],
      },
      drugInfo: {
        type: Object
      },
      drugList: {
        type: Array
      },
      allChecked: {
        type: Boolean
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
        actionSheetList: {
          1: '多药',
          2: '少药',
          3: '破损',
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
        finishShow: false,
        allChecksShow_not: false,
        customBtn: false,
        autoUpload: true,
        showProgress: true,
        deletable: true,
        customStyle: false,
        maxCount: 9,
        lists: [], // 组件内部的文件列表
        showPopup: true,
        drugNum: 0,
        action: `${$config.siteroot}/system/document/fastDFSUpload`, // 演示地址
        showUploadList: false,
        // 如果将某个ref的组件实例赋值给data中的变量，在小程序中会因为循环引用而报错
        // 这里直接获取内部的lists变量即可
      };
    },
    onReady() {
    },
    onLoad() {
    },
    onShow() {
    },
    methods: {
      valChange(e, item) {
        //  console.log(this.type,'然我卡看你对不对');
        //  console.log(e, item, '>>>>>>>>>>>>>>>>>>>>>>>>>>',this.drugInfo);
      },
      deleteItem(index) {
        this.$refs.uUpload.remove(index);
      },

      onListChange(lists) {
        //  console.log('onListChange', lists);
      },
      beforeRemove(index, lists) {
        return true;
      },
      onChooseFail(e) {
        // console.log(e);
      },
      onChooseComplete(lists, item) {
        // console.log(lists, 111111111111111111111);
        this.$set(item, 'lists', lists)

      },
      onChangeUpload(res, index, lists, name) {
        // console.log(res, index, lists, name,'res, index, lists, name');

      },
      onSuccessUpload(data, index, lists, name) {
        // console.log(data, index, lists, name, '>>>>>>>>>>>>>>>>>>>>>>>>>',);
        // documentList.push()

        // this.$set(item,'documentIdList',lists)
        // console.log(this.drugList[index].listDocumentVO,'<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<');
      },
      finishConfirm() {
        this.finishShow = false
        this.$emit('close',1)
      },
      handleConfirm() {
        // if (this.allChecked) {
        //   this.allChecksShow_not = true;
        //   return
        // }
        this.handleSubmit();
      },
      handleSubmit() {
        var that = this;
        console.log(that.drugList,that.drugInfo);
        if (that.drugList.some(x => x.checkCount != x.allDistributeCount) && this.type== 0) {
          this.finishShow = true;
          return
        }
        let postUrl = '';
        //  console.log(that.type,'1111111111111111');
        if(that.type == 0){
          postUrl = '/deliveryOrder/mergeCheckDrugs'
        }
        if(that.type == 1){
          postUrl = '/deliveryOrder/checkDrugs'
        }
        this.$request.post(postUrl, {
          deliveryOrderDetailsVOs: that.drugList,
          boxNo: that.type == 1?that.drugInfo.boxNo :undefined,
          groupNo: that.type == 0?that.drugInfo.groupNo :undefined,
          distributeCode:that.drugInfo.distributeCode,
          // drugProcessNode:{'多药':4, '少药':5, '破损':6, }[that.actionSheetList[that.type]],
          //
        }).then(res => {
            uni.showToast({
              title: `验收成功`,
            });
          console.log(res.data.isChecked == 0,'尊都家督');
          if (res.data.isChecked == 0) {
              uni.redirectTo({
                url: '/pages/workPages/checkAcceptance/index'
              })
            } else {
              that.$emit('close');
            }
          })
          .catch(err => {
          });
      },
      checkConfirm() {
        //  console.log(222);
      },
    }
  };
</script>

<style lang="scss" scoped>
  .popup-main {
    height: 100%;
    position: absolute;
  }

  .border-bottom-2 {
    border-bottom: 4upx solid #f0f3f8;
  }

  .durg-list {
    /*padding:0 20upx;*/
    /*display: flex;*/
    .drug-list-content {
      width: 100%;
      /*margin: 0 20upx;*/
      padding: 0 20upx;
      display: flex;
      justify-content: space-between;
      border-bottom: 2upx dashed #f0f3f8;

    }
    .drug-specifications{
      padding: 0 20upx;
    }
  }

  .baseInfo {
    width: 60%;

    .drug-use {
      display: flex;

      .drug-use-item {
        flex: 1;
      }
    }
  }


  .drug-name {

    line-height: 40upx;
    /*height: 80upx;*/
    width:100%;
    color: #000000;
    padding: 0 20upx;
    margin: 20upx 0;
    display: flex;
    justify-content: space-between;

  }

  .drug-img {
    /*padding:0 20upx;*/
    margin: 0 20upx;

    .drug-img-title {
      margin: 16upx 0 0;
    }
  }

  .drug-num {
    margin: 0 10upx 20upx 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
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

  .wrap {
    width: 100%;
    padding: 24 rpx 0;
    /*display: flex;*/
  }

  .slot-btn {
    width: 341 rpx;
    height: 140 rpx;
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgb(244, 245, 246);
    border-radius: 10 rpx;
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
    border-radius: 10 rpx;
    height: 140 rpx;
    width: 140 rpx;
    /*overflow: hidden;*/
    position: relative;
    margin-bottom: 20 rpx;
  }

  .u-progress {
    position: absolute;
    bottom: 10 rpx;
    left: 8 rpx;
    right: 8 rpx;
    z-index: 9;
    width: auto;
  }

  .pre-item-image {
    width: 140 rpx;
    height: 140 rpx;
  }

  .u-delete-icon {
    position: absolute;
    top: 10 rpx;
    right: 10 rpx;
    z-index: 10;
    background-color: $u-type-error;
    border-radius: 100 rpx;
    width: 44 rpx;
    height: 44 rpx;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .single_drug {
    height: 300upx;
    width: 100%;
    border-bottom: 1upx solid #e5e5e5;

    .title {
      background: #e5e5e5;
      height: 100upx;
      width: 100%;
      display: flex;
      justify-content: space-between;
      line-height: 100upx;
      font-size: 30upx;
      font-weight: 650;
      padding: 0 20upx;
    }

    .sub_title {
      height: 80upx;
      width: 100%;
      font-size: 30upx;
      font-weight: 600;
      padding: 0 20upx;
      line-height: 80upx;
    }

    .info_item {
      height: 60upx;
      width: 100%;
      font-size: 30upx;
      padding: 0 20upx;
      line-height: 60upx;
    }
  }

  .single_drug_item {
    /*height: 60upx;*/
    min-height: 60upx;
    line-height: 60upx;
    width: 100%;
    padding: 0 20upx;
    display: flex;
    width: 100%;
    .label{
      color: #878787;
      width: 30%;
    }
    .content{
      width: 70%;
      height: fit-content;
    }
  }

  .single_drug_num {
    height: 140upx;
    width: 100%;
    padding: 0 20upx;
    border-bottom: 1upx solid #e5e5e5;
    border-top: 1upx solid #e5e5e5;
    display: flex;
    flex-direction: column;
    justify-content: center;

    .num_box {
      display: flex;
      width: 100%;
      height: 60upx;

      .num_title {
        line-height: 60upx;
        width: fit-content;
        height: 60upx;
        margin-right: 30upx;
        font-size: 28upx;
      }
      .num_unit{
        margin-left: 20upx;
        line-height: 60upx;
        width: fit-content;
        height: 60upx;
        margin-right: 30upx;
        font-size: 28upx;
      }
    }
  }


  ::v-deep {
    .u-numberbox {
      border: 1px solid #e6e6e6;
      border-radius: 10rpx;

    }

    .u-icon-minus, .u-icon-plus {
      width: 60rpx;
    }

    .u-number-input {
      border-left: 1px solid #e6e6e6;
      border-right: 1px solid #e6e6e6;
      width: 100upx !important;
    }

  }
</style>
