<template>
  <view class="u-wrap">
    <view class="main-head">
      <view class="main-head-content">
        <view class="u-demo-title u-flex" @click="goBack" style="width: 50%;">
          <u-icon name="arrow-left" :size="36" ></u-icon>
          <view class="u-text-left u-flex-1 u-margin-left-20">
            箱码详情
          </view>
        </view>
<!--        -->
      </view>
    </view>
    <view class="content" v-if=" parentInfo.type != 2">
      <view class="tab-content u-border-bottom" >
        <view class="tab-body">
          <liuyuno-tabs :tabData="tablist" :current="current" @tabClick='tabChange'/>
        </view>
      </view>

      <u-gap height="10" bg-color="#f0f3f8"v-if="status==1"></u-gap>
      <view class="content-body" v-if="status==1">
        <view class="question-body-summary" v-for="(item,itemIndex) in orderDetail" :key="item.boxNo+itemIndex">
          <view class="u-item-title">
            <view class="question-body-name u-flex  u-margin-left-20 u-line-1 u-font-weight "
                  :class="isshow?'u-border-bottom':''">
              <view class="u-fle-1" v-if="!item.groupNo">箱码：{{item.boxNo}}</view>
              <view class="u-fle-1" v-if="item.groupNo">配送企业：{{item.distributeName}}</view>
            </view>
          </view>

          <view class="">
            <view class="question-body-data-summary u-line-1  u-font-weight u-margin-left-24"
                  v-if="!item.groupNo">配送企业：{{item.distributeName}}</view>

            <view class="u-margin-top-10 u-flex u-line-1 u-margin-left-20">
              <view style="width:120upx;height: 40upx;">发票号：</view>
              <view style="flex-wrap: wrap;" class="u-flex">
                <text class="question-info_invoiceNo u-margin-right-30" v-for="text in item.invoiceNo.split(',')" :key="text">{{text}}</text>
              </view>
            </view>
<!--            <view class="u-line-1  data_tags u-flex" v-if="item.invoiceNo" >-->
<!--              &lt;!&ndash;											<text class="question-info u-margin-left-30">发票号：{{item.invoiceNo && item.invoiceNo.map(item =>{return item.medicineBoxCode}).join(',') }}</text>&ndash;&gt;-->
<!--              <view style="width:120upx;height: 60upx;">发票号：</view>-->
<!--              <view style="flex-wrap: wrap;" class="u-flex">-->
<!--                <text class="question-info_invoiceNo u-margin-right-30" v-for="text in item.invoiceNo.split(',')">{{text}}</text>-->
<!--              </view>-->
<!--            </view>-->
            <view class="u-margin-top-10 u-margin-left-20 u-line-1 u-margin-top-10" v-if="item.groupNo">
              配送组号：
              <text class="question-info" v-if="item.groupNo">{{ item.groupNo }}</text>
            </view>

            <view class="question-body-type  u-flex u-font-24 u-padding-bottom-20 u-margin-top-30">
              <view class="u-flex-1 u-flex">
                <u-icon name="clock" :size="34"
                        class="main-body-arrow u-margin-right-5 u-margin-left-24"></u-icon>
                {{item.distributeTime}}
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
    <view style="height: 20upx;" class="suggest-top"></view>
    <view class="u-menu-wrap" id="element" v-if="status==1">
      <scroll-view scroll-y scroll-with-animation class="right-box" @scrolltolower="reachBottom">
        <view class="page-view"  v-for="(detail,dIndex) in orderDetail" :key="detail.boxNo+dIndex">
          <u-checkbox-group @change="radioGroupChange" v-if="detail.deliveryOrderDetailsVOS.length>1">
            <u-table align="center" borderColor="#fff">
              <view style="width: 100%;">
                <view style="width:28upx;"></view>
                <u-tr class="u-tr" v-if="parentInfo.isMerge == 0">
                  <!--              商品名称      数量    箱数       规格    批号-->
<!--                  <view style="width: 28upx;"></view>-->
                  <u-th class="u-th" style="border-right:1px solid #f5f6f8;" width="25%">商品名称</u-th>
                  <u-th class="u-th" style="border-right:1px solid #f5f6f8" width="15%">数量</u-th>
                  <u-th class="u-th" style="border-right:1px solid #f5f6f8" width="15%">箱数</u-th>
                  <u-th class="u-th" style="border-right:1px solid #f5f6f8" width="22.5%">规格</u-th>
                  <u-th class="u-th" style="border-right:1px solid #f5f6f8" width="22.5%">批号</u-th>
                </u-tr>
                <view class="u-tr" style="display: flex;text-align: center;background: #f5f6f8;" v-if="parentInfo.isMerge == 1">
                  <!--              商品名称      数量    箱数       规格    批号-->
                  <view class="u-th" style="border-right:1px solid #f5f6f8;line-height: 60upx;width:25%;" >商品名称</view>
                  <view class="u-th" style="border-right:1px solid #f5f6f8;line-height: 60upx;width:15%;" >数量</view>
                  <!--                <u-th class="u-th" style="border-right:1px solid #f5f6f8" width="15%">单位</u-th>-->
                  <view class="u-th" style="border-right:1px solid #f5f6f8;line-height: 60upx;width:30%;" >规格</view>
                  <view class="u-th" style="border-right:1px solid #f5f6f8;line-height: 60upx;width:30%;" >批号</view>
                </view>
              </view>
                <u-checkbox
                    v-for="(item,index) in detail.deliveryOrderDetailsVOS"
                    @change="radioChange"
                    v-model="item.checked"
                    :key="index"
                    :name="index"
                >
                  <u-tr class="u-tr">
                    <u-td class="u-td" width="25%" :style="{color:item.isInValidity==0?'#FF7373':''}">{{item.drugName}}</u-td>
                    <u-td class="u-td" width="15%" @click="selectItem(item)">{{item.distributeCount}}</u-td>
                    <u-td class="u-td" width="15%" v-if="parentInfo.isMerge == 0" @click="selectItem(item)">{{item.boxCount}}件</u-td>
<!--                    <u-td class="u-td" width="15%" v-if="parentInfo.isMerge == 1" @click="selectItem(item)">{{item.unit}}</u-td>-->
                    <u-td class="u-td" :width="parentInfo.isMerge == 1?'30%':'22.5%'" @click="selectItem(item)">
                      <div style="width: 100%;word-break: break-word;">{{item.drugSpecs}}</div>
                    </u-td>
                    <u-td class="u-td" :width="parentInfo.isMerge == 1?'30%':'22.5%'" @click="selectItem(item)"><text>{{item.approvalNo}}</text></u-td>
                  </u-tr>
                </u-checkbox>
            </u-table>
          </u-checkbox-group>
          <view v-if="detail.deliveryOrderDetailsVOS.length == 1" style="background: #fff;">
            <view v-for="item in detail.deliveryOrderDetailsVOS" :key="item.allDistributeCount+item.approvalNo">
              <view class="single_drug">
                <view class="title">
                  <view style="font-size: 36upx;" :style="{color:item.isInValidity==0?'#FF7373 !important':''}">{{item.drugName}}</view>
                  <!--          <view>x {{drugList[0].distributeCount}} {{drugList[0].unit}}</view>-->
<!--                  <view>x {{// parentInfo.isMerge == 1? `${item.distributeCount}${item.unit}`:`${item.distributeCount}${item.unit}/${item.boxCount}件`}}</view>-->
                  <view> {{parentInfo.isMerge == 1? `${item.pieces==null?'':'x'+item.pieces}${item.pieces==null?'':'件'}`:`x${item.boxCount}件`}}</view>
                </view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">药品分类</view>
                <view class="item_content">{{item.drugType}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">规格</view>
                <view class="item_content">{{item.drugSpecs}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">批号</view>
                <view class="item_content">{{item.approvalNo}}</view>
              </view>
              <view class="single_drug_item" :style="{color:item.isInValidity==0?'#FF7373 !important':''}">
                <view class="item_label">效期</view>
                <view class="item_content">{{item.validDate}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">进价</view>
                <view class="item_content">{{item.price}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">是否贵重</view>
                <view class="item_content">{{item.isExpensive}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">生产企业</view>
                <view class="item_content">{{item.productionName}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label" >批准文号</view>
                <view class="item_content">{{item.licenseNo}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">剂型</view>
                <view class="item_content">{{item.formName}}</view>
              </view>
              <view class="single_drug_num">
                <view class="num_box">
                  <view class="num_title" >实收数量</view>
                  <u-number-box v-model="item.checkCount"
                                :key="`${1}_${1}`"
                                :min="0"
                                @change="valChange($event,item)"
                                size="40" bg-color="#ffffff"></u-number-box>
<!--                  <view class="num_unit" >{{item.unit}}</view>-->
                </view>
              </view>
            </view>
          </view>
        </view>
        <view style="margin-top: 50upx;width: 100%;text-align: center;height:60upx;line-height: 60upx;display: flex;justify-content: center;" @click="getMoreDetails" v-if="parentInfo.isMerge == 0">

          <view style="background: linear-gradient(to right, #fff, #2c9ef7);height: 4upx;width: 40%;margin: 30upx 10upx 0;"></view>
          <view style="color:#2c9ef7;">
            <text style="margin-right: 4upx">详情</text>
          <u-icon name="arrow-up" size="16" color="#2c9ef7" v-if="showMoreDetail"></u-icon>
          <u-icon name="arrow-down" size="16" color="#2c9ef7" v-if="!showMoreDetail"></u-icon>
          </view>
          <view style="background: linear-gradient(to right, #2c9ef7, #fff);height: 4upx;width: 40%;margin: 30upx 10upx 0;"></view>
        </view>
        <view v-if="showMoreDetail">
          <view class="box_card" v-for="item in moreDetails">
            <view class="u-item-title">
              <view class="question-body-name u-flex  u-margin-left-20 u-line-1 u-font-weight "
                    :class="isshow?'u-border-bottom':''">
                <view class="u-fle-1">箱码：{{item.boxNo}}</view>
                <!--                <view class="u-fle-1">配送企业：{{orderDetail.initialDispensingOrderNo || '上海控股徐州股份有限公司'}}</view>-->
              </view>
            </view>
            <view v-for="drug in item.deliveryOrderDetailsVOS">
              <view class="question-body-data-summary u-line-1  u-font-weight u-margin-left-24" :style="{color:drug.isInValidity==0?'#FF7373 !important':''}">{{drug.drugName}}</view>
              <view style="width: 100%" class="u-flex">
                <view class="question-info " style="color:#8f8f8f; width:50%;">数量：{{ drug.distributeCount}}</view>
                <view class="question-info u-margin-left-30" style="color:#8F8F8F; width:50%;">规格：{{drug.drugSpecs}}</view>
              </view>
              <view style="width: 100%" class="u-flex">
                <view class="question-info" style="color:#8F8F8F; width:50%;">批号：{{ drug.approvalNo }}</view>
                <view class="question-info u-margin-left-30" style="color:#8F8F8F; width:50%;" :style="{color:drug.isInValidity==0?'#FF7373 !important':''}">效期：{{drug.validDate}}</view>
              </view>
            </view>

          </view>
        </view>
        <view style="height: 200upx;"></view>
      </scroll-view>
    </view>
    <view class="u-menu-wrap_2" id="element" v-if="status==2" :style="{marginTop: parentInfo.type == 2?'155upx':''}">
      <scroll-view scroll-y scroll-with-animation class="right-box" @scrolltolower="reachBottom">
        <view class="page-view_2"  v-for="detail in orderDetail" :key="detail.boxNo">
          <view class="question-body-summary">
            <view class="u-item-title">
              <view class="question-body-name u-flex  u-margin-left-20 u-line-1 u-font-weight "
                    :class="isshow?'u-border-bottom':''">
                <view class="u-fle-1">箱码：{{detail.boxNo}}</view>
<!--                <view class="u-fle-1" v-if="detail.groupNo">配送企业：{{detail.distributeName}}</view>-->
              </view>
            </view>
            <view class="">
              <view class="question-body-data-summary u-line-1  u-font-weight u-margin-left-24"
                    >配送企业：{{detail.distributeName}}</view>
              <view class="u-margin-top-30 u-flex u-line-1 u-margin-left-20">
                <view style="width:120upx;height: 60upx;">发票号：</view>
                <view style="flex-wrap: wrap;" class="u-flex">
                  <text class="question-info_invoiceNo u-margin-right-30" v-for="text in detail.invoiceNo.split(',')" :key="text">{{text}}</text>
                </view>
              </view>
              <view class="u-margin-top-30 u-margin-left-20" v-if="detail.groupNo">
                配送组号：
                <text class="question-info " v-if="detail.groupNo">{{detail.groupNo}}</text>
              </view>

              <view class="question-body-type  u-flex u-font-24 u-padding-bottom-20 u-margin-top-30">
                <view class="u-flex-1 u-flex">
                  <u-icon name="clock" :size="34"
                          class="main-body-arrow u-margin-right-5 u-margin-left-24"></u-icon>
                  {{detail.distributeTime}}
                </view>
              </view>
            </view>
          </view>
          <view v-if=" detail.deliveryOrderDetailsVOS.length>1">
            <view class="durg-list border-bottom-2" v-for="(item,index) in detail.deliveryOrderDetailsVOS" :key="item.licenseNo">
              <view class="drug-name u-font-weight">
                <view style="font-size: 36upx;" :style="{color:item.isInValidity==0?'#FF7373 !important':''}">{{item.drugName}}</view>
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
                    <view class="drug-use-item">批准文号：{{item.licenseNo}}</view>
                  </view>
                </view>
                <view class="drug-num ">
                  <view :style="{color:item.checkCount == item.distributeCount?'':'#FF7373'}" class="u-font-weight">实收 {{item.checkCount}}</view>
                  <view>X {{item.allDistributeCount}}</view>
                </view>
              </view>
              <view class=" drug-specifications u-padding-bottom-20 u-font-26" style="padding: 0 16upx;">生产企业：{{item.productionName}}</view>
            </view>
          </view>

          <view v-if="detail.deliveryOrderDetailsVOS.length == 1">
            <view v-for="item in detail.deliveryOrderDetailsVOS" :key="item.drugName">
              <view class="single_drug">
                <view class="title">
                  <view style="font-size: 36upx;" :style="{color:item.isInValidity==0?'#FF7373':''}">{{item.drugName}}</view>
                  <!--          <view>x {{drugList[0].distributeCount}} {{drugList[0].unit}}</view>-->
<!--                  <view>x {{// parentInfo.isMerge == 1? `${item.distributeCount}${item.unit}`:`${item.distributeCount}${item.unit}/${item.boxCount}件`}}</view>-->
                  <view> {{parentInfo.isMerge == 1? `${item.pieces==null?'':'x'+item.pieces}${item.pieces==null?'':'件'}`:`x${item.boxCount}件`}}</view>
                </view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">药品分类</view>
                <view class="item_content">{{item.drugType}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">规格</view>
                <view class="item_content">{{item.drugSpecs}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">批号</view>
                <view class="item_content">{{item.approvalNo}}</view>
              </view>
              <view class="single_drug_item" :style="{color:item.isInValidity==0?'#FF7373 !important':''}">
                <view class="item_label">效期</view>
                <view class="item_content">{{item.validDate}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">进价</view>
                <view class="item_content">{{item.price}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">是否贵重</view>
                <view class="item_content">{{item.isExpensive}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">生产企业</view>
                <view class="item_content">{{item.productionName}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label" >批准文号</view>
                <view class="item_content">{{item.licenseNo}}</view>
              </view>
              <view class="single_drug_item">
                <view class="item_label">剂型</view>
                <view class="item_content">{{item.formName}}</view>
              </view>
              <view class="single_drug_num">
                <view class="num_box">
                  <view class="num_title" >实收数量</view>
                  <view style=" " class="num_title aaa">{{item.checkCount}}</view>
<!--                  <u-number-box v-model="item.checkCount"-->
<!--                                :key="`${1}_${1}`"-->
<!--                                :min="0"-->
<!--                                @change="valChange($event,item)"-->
<!--                                size="40" bg-color="#ffffff"></u-number-box>-->
<!--                  <view class="num_unit" >{{item.unit}}</view>-->
                </view>
              </view>
            </view>

          </view>

        </view>
        <view style="height: 200upx;" v-if="status==1"></view>
      </scroll-view>
    </view>

    <view class="bottom-bar" v-if="status==1">
      <view v-if="allData.length>1" style="height: 100%;width:30%;align-items: center;display:flex;justify-content: center;">

<!--        <u-checkbox-group  >-->
<!--          <u-checkbox v-model="trueBool" :disabled="false"  v-if="allcChecked" @change="trueBool = true"-->
<!--                      class=" all">-->
<!--&lt;!&ndash;            <view style="height: 80upx;width: 120upx;background: #2c9ef7;border-radius: 10upx;line-height: 80upx;text-align: center;color: #fff;">全部</view>&ndash;&gt;-->
<!--          </u-checkbox>-->
<!--        <u-checkbox v-model="falseBool" :disabled="false" v-if="!allcChecked" @change="falseBool = false"-->
<!--                      class="all">-->
<!--          </u-checkbox>-->
        <view style="border: 1upx solid #2c9ef7;width:32upx;height: 32upx; margin: 0 20upx 0 0;align-items: center;display: flex;flex-direction: column;justify-content:center;" :style="{background:allcChecked?'#2c9ef7':'#fff'}" >
<!--          <u-icon class="" name="checkbox-mark" size="30" color="#fff"/>-->
          <u-icon class="" name="checkbox-mark" size="30" color="#fff"/>

<!--          <u-image width="100%" height="32upx" src="../../../static/images/checkbox-checked.png"></u-image>-->
        </view>
<!--        <view style="border: 1upx solid #2c9ef7;width:32upx;height: 32upx;background: #fff; margin: 0 20upx 0 0;" v-if="!allcChecked">-->
<!--          <u-icon class="" name="checkbox-mark" size="30" color="#fff"/>-->
<!--        </view>-->
            <view style="height: 80upx;width: 120upx;background: #2c9ef7;border-radius: 10upx;line-height: 80upx;text-align: center;color: #fff;" @click="selectAllChange">

              全部</view>
<!--        </u-checkbox-group>-->
<!--        <u-button @click="selectAllChange" class="u-margin-left-10">全部-->
<!--        </u-button>-->
      </view>
      <u-button class="bar-button" @click="btnClick" data-name="33322" :loading="loading" :plain="false" style=""
                :shape="'square'" :ripple="true" :hairLine="false" :type="'primary'">验收
      </u-button>

    </view>

    <u-modal ref="uModal" v-model="allChecksShow" title="" :show-cancel-button="true" :show-title="true"
             :async-close="false" @confirm="checkConfirm">
      <view style="width: 80%;line-height: 42upx;font-size: 42upx;margin: 30upx 10%;text-align: center;">验收确认</view>
      <view style="width: 100%;line-height: 50upx;font-size: 36upx;margin: 30upx 0;">
        检测到验收数量与实际数量不符，无法追溯数量异常的箱号，不允许合并验收
      </view>
    </u-modal>


    <drugInfo v-if="showPopup" @close="popupClose" :type="parentInfo.isMerge" :drug-info="orderDetail[0]"
              :drug-list="checkDrugList" :allChecked="allcChecked"></drugInfo>

    <view class="btn" @tap="toTop" :style="{'display':(flag===false? 'none':'block')}">
      <u-icon name="arrow-upward" :size="40" class="cuIcon-top"></u-icon>
    </view>
    <u-modal ref="uModal" v-model="finishShow" title="" :show-cancel-button="true" :show-title="true"
             :async-close="false" @confirm="finishConfirm" >
      <view style="width: 80%;line-height: 42upx;font-size: 42upx;margin: 30upx 10%;text-align: center;">验收提示</view>
      <view style="width: 90%;line-height: 50upx;font-size: 36upx;margin: 30upx 5%;">
        检测到验收数量与实际数量不符，无法追溯数量异常的箱号，不允许合并验收
      </view>
    </u-modal>
  </view>
</template>
<script>
  import classifyData from '@/common/classify.data.js';
  import drugInfo from "./components/drugInfo";
  import liuyunoTabs from "@/components/liuyuno-tabs/liuyuno-tabs.vue";


  export default {
    components: {drugInfo, liuyunoTabs},
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
        tabCurrent: 0,
        tablist: [{
          name: '待验收',
          count:0,
        },
          {
            name: '已验收',
            count:0,
          },
        ],
        isshow: false,
        loading: false,
        allcChecked: false,
        trueBool: true,
        falseBool: false,
        allChecksShow: false,
        allChecksShow_not: false,
        finishShow: false,
        showActionSheet: false,
        showPopup: false,
        showImg: false,
        status: 1,
        actionSheetType: '0',
        actionSheetList: [
          {text: '验收多药', id: 1,},
          {text: '验收少药', id: 2,},
          {text: '验收破损', id: 3,}],
        drugInfo: {},
        drugDetail: {},
        checkDrugIdArr: [],
        dispensingOrderNo: undefined,
        allDrugData: [],
        checkDrugList: [],
        drugImgList: [],
        flag: false,
        showScroll: true,
        showModal: true,
        showMedicineBoxCode: false,
        showMoreDetail: false,
        orderDetail: {},
        drugListTypeName: '',
        drugListTypeCode: '',
        showType: '',
        queryInitialFlag: 1,
        typeChecked: false,
        pageNum: 1,
        pageSize: 5,
        total: 0,
        hasMoreData: true,
        dispenseType: 1, //1-扫码药品调配 2-选择药品调配 3-选择清单类型分配 4--全选调配
        selectType: 'down',
        sort: 1,
        queryStage:6,
        value:6,
        parentInfo:{
          boxNo:'',
          type:'0',
          groupNo:'',
          isMerge:"1",
          fromType:""
        },
        moreDetails:[],
        allData:[],
      }
    },
    onLoad(options) {
      this.parentInfo.boxNo = options.boxNo;
      this.parentInfo.type = options.type;
      this.parentInfo.isMerge = options.isMerge;
      this.parentInfo.groupNo = options.groupNo;
      this.parentInfo.fromType = options.fromType;
      this.parentInfo = {...options};

      if(this.parentInfo.type == 2){
        this.status=2;
        this.getOrderInfo(1)
      }else{
        this.getOrderInfo(0)
      }
       // if(options.groupNo != ''){
      //   this.parentInfo.isMerge = 0;
      // }
  //  console.log(this.tabCurrent,'?????????????');
    },
    onReady() {
      // this.getMenuItemTop()
    },
    methods: {
      //获取单据基本信息及左侧分类
      getOrderInfo(drugCheckStatus=this.tabCurrent) {
        var that = this;
        let { boxNo,groupNo ,isMerge,distributeCode} = that.parentInfo;

        that.allcChecked = false;
        that.showMoreDetail = false;
        that.allData = [];
        // console.log(boxNo,'越过山丘，才发现无人等候');
        that.$request
          .post('/deliveryOrder/queryDetails', {
            boxNo:boxNo,
            drugCheckStatus:drugCheckStatus+1, //验收状态1待验收2已验收
            groupNo:groupNo,
            isMerge:isMerge,//是否合并 0-是，1-否
            distributeCode:distributeCode
          })
          .then(res => {
            res.data.map(item =>{
              item.deliveryOrderDetailsVOS.map(delivery =>{
                that.$set(delivery,'checked',false)
                that.allData.push(delivery)
                if(delivery.checkCount == null){
                  delivery.checkCount = delivery.allDistributeCount;
                  that.$set(delivery,'checkCount',delivery.allDistributeCount)
                }
                console.log(delivery.checkCount,'你个杀手包')
              })
            })
            // console.log(that.tabCurrent,drugCheckStatus,'this.tabCurrent,deliveryOrderDetailsVOS');
            if(that.tabCurrent = drugCheckStatus ){
                console.log(res.data,'阿斯利康的规划')
              that.orderDetail = res.data;
            }
            if(!drugCheckStatus){
              console.log(res.data,'埃里克给')
              that.orderDetail = res.data;
            }
            that.$forceUpdate();
            that.tabsList[drugCheckStatus].count = res.data.length;
          })
          .catch(err => {
            // uni.showToast({
            // 	title: '数据错误，请联系管理员',
            // 	icon: 'none',
            // 	success: function(res2) {
            // 		setTimeout(function() {
            // 			uni.navigateBack()
            // 		}, 1500);
            // 	}
            // });
          });
      },
      getMoreDetails(){
        if(this.showMoreDetail){
          this.showMoreDetail = !this.showMoreDetail;
        }else{
          let { boxNo,groupNo ,isMerge,distributeCode} = this.parentInfo;
          this.$request.post("/deliveryOrder/queryDetailsAllBox",{
            boxNo:boxNo,
            drugCheckStatus:1, //验收状态1待验收2已验收
            groupNo:groupNo,
            isMerge:0,//是否合并 0-是，1-否
            distributeCode:distributeCode,
          }).then(res =>{
        //  console.log(res);
            this.moreDetails = res.data;
            this.showMoreDetail = true;
          }).catch(err=>{

          })
        }

      },
      //药品升序降序
      downUpRank(){
        if(this.sort==1) {
          this.sort=2
        }else{
          this.sort=1
        }
        this.getOrderInfo()
      },
      // 返回顶部
      toTop() {
        var that = this
        this.showScroll = true
        this.$nextTick(function () {
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
        if (e.scrollTop > 10) { //当距离大于10时显示回到顶部按钮
          this.flag = true
        } else {
          this.flag = false
        }

        let number = 188
        this.status == 2 ? number = 168 : number = 188
        if (e.scrollTop >= number) {
          this.showScroll = true
        }
        if (e.scrollTop == 0) {
          this.showScroll = false
        }
      },



      //触底加载下一页
      reachBottom() {
        if (!this.hasMoreData) {
          uni.showToast({
            title: '已是最后一页',
            duration: 500
          });
          return false;
        }
        // uni.showLoading({
        // 	title: '加载中'
        // });
        // setTimeout(function() {
        // 	uni.hideLoading();
        // }, 200);
      },
      clear() {
        this.getOrderInfo()
      },
      tabChange(index) {
        this.tabCurrent = index;
        this.status = index+1;
        this.getOrderInfo();
      },
      //底部全选
      async selectAllChange(e) {
        // console.log('tiktok,安抚看来文法爱马仕打开', e);
        // var that = this
        this.allcChecked = !this.allcChecked
        this.checkDrugList= [];
        await this.orderDetail.map(item =>{
          item.deliveryOrderDetailsVOS.map(delivery =>{
            this.$set(delivery,'checked',this.allcChecked)
            if(delivery.checked){
              this.checkDrugList.push(delivery)
            }
          })
        })
        await this.$forceUpdate();
      },
      btnClick() {
        // console.log(this.checkDrugList,'checkDrugList');
        if(this.allData.length>1){
          if(this.checkDrugList.length>0){
            this.checkDrugList.map(item =>{
              item.checkCount = item.allDistributeCount;
            })
            this.showPopup= true;
          }
        }else{
          this.handleSubmit();
        }

        // this.checkConfirm();
      },
      handleSubmit() {
        var that = this;
        if (that.allData.some(x => x.checkCount != x.allDistributeCount) && that.parentInfo.isMerge == 0) {
          this.finishShow = true;
          return
        }

        let postUrl = '';
         // console.log(that.type,'1111111111111111');
        if(that.parentInfo.isMerge == 0){
          postUrl = '/deliveryOrder/mergeCheckDrugs'
        }
        if(that.parentInfo.isMerge == 1){
          postUrl = '/deliveryOrder/checkDrugs'
        }
        this.$request.post(postUrl, {
          deliveryOrderDetailsVOs: that.allData,
          boxNo: that.parentInfo.isMerge == 1?that.parentInfo.boxNo :undefined,
          groupNo: that.parentInfo.isMerge == 0?that.parentInfo.groupNo :undefined,
          distributeCode:this.parentInfo.distributeCode,
          // drugProcessNode:{'多药':4, '少药':5, '破损':6, }[that.actionSheetList[that.type]],
          //
        }).then(res => {
          // uni.showToast({
          //   title: `验收成功`,
          // });
          console.log(res.data.isChecked == 0,'尊都家督');
          if (res.data.isChecked == 0) {
            this.$request.post('/deliveryOrder/checkDrugsVerifyNotAcceptedBoxs',{
              deliveryOrderDetailsVOs: that.allData,
              boxNo: that.parentInfo.isMerge == 1?that.parentInfo.boxNo :undefined,
              groupNo: that.parentInfo.isMerge == 0?that.parentInfo.groupNo :undefined,
              isMerge:that.parentInfo.isMerge,
              distributeCode:this.parentInfo.distributeCode,
            }).then(res2=>{
              uni.showToast({
                title:'验收成功'
              })
              setTimeout(()=>{
                // uni.navigateTo({
                //   url: '/pages/workPages/checkAcceptance/index'
                // })
                uni.navigateBack({
                  delta: 1
                });
              },1500)
            })


          } else {
            that.$emit('close');
          }
        })
          .catch(err => {
          });
      },
      finishConfirm() {
        this.finishShow = false
        // uni.redirectTo({
        //   url:'/pages/nurse/drugAcceptance/index'
        // })
        uni.navigateBack({
          delta: 1
        });
      },
      handleConfirm() {
        this.showPopup = true;
      },
      async checkConfirm() {
        var that = this;
        let drugListInfo = this.orderDetail.drugDetailVOList[this.current];
        // console.log('index', this.actionSheetList[index].text, drugListInfo);

        if (that.checkDrugIdArr.length > 0) {
          this.$request.post('/nurseManagement/qualifiedAcceptance', {
            "allFlag": this.allcChecked?0:1,//0 是，1否
            "dispensingOrderNo": this.dispensingOrderNo,
            "drugListTypeCode":!that.allcChecked && drugListInfo.checked? drugListInfo.drugListTypeCode:undefined,
            drugVOList:!that.allcChecked && !drugListInfo.checked ?this.drugDetail.filter(x => x.checked):undefined,
            distributeCode:this.parentInfo.distributeCode,
          })
            .then(res => {
          //  console.log(res);
              uni.showToast({
                title: '验收成功',
                success: function (res2) {
                  setTimeout(function () {
                    that.checkDrugIdArr = []
                    // that.Getlist()
                    if(res.data.acceptanceCompletedFlag){
                      that.finishConfirm();
                    }else{
                    }
                  }, 500);
                }
              });
            })
            .catch(err => {
            });
        } else {
          uni.showToast({
            title: '请选择至少一个药品进行操作',
            icon: 'none',
          });
        }

      },
      processData(){
        let that = this;
        this.allDrugData = [];
        // console.log(that.drugDetail,'????????????????');
        that.orderDetail.drugDetailVOList[that.current]?.forEach((item, index) => {
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

      popupClose(data) {
        // this.$set(this,'allcChecked',false);
        this.allcChecked = false;
        this.getOrderInfo()
        this.checkDrugList = [];
        // this.selectAllChange();
        // this.$forceUpdate();
        this.showPopup = false;
        console.log(this.allcChecked,'allcChecked',this);
      },
      showDrugPictrue(img) {
        let that = this;
        that.drugImgList = img;
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
      radioChange(e){
        // console.log(e,'我在海的那一边');
      },
      selectItem(item){
        console.log(item,'我在天的这一边');
        this.$set(item,'checked',!item.checked)
      },
      radioGroupChange(e){
      //  console.log(e,'你在爱的那一边');
        this.checkDrugList = [];

        this.orderDetail.map(item =>{
          item.deliveryOrderDetailsVOS.map(delivery =>{
            if(delivery.checked){
              this.checkDrugList.push(delivery)
            }
          })
        })
      //  console.log(this.allData,'我倒要看看你是什么牛马',this.checkDrugList,this.checkDrugList.length == this.allData.length);
        if(this.checkDrugList.length < this.allData.length){
          this.allcChecked = false
        }
        if(this.checkDrugList.length == this.allData.length){
          this.allcChecked = true
        }
        // console.log(this.checkDrugList,'彼得潘');
      },
        valChange(e, item) {
        //  console.log(this.type,'然我卡看你对不对');
        //  console.log(e, item, '>>>>>>>>>>>>>>>>>>>>>>>>>>',this.drugInfo);
      },
      goBack(){
        // uni.reLaunch({url: `/pages/workPages/checkAcceptance/index`})
        uni.navigateBack({
          delta: 1
        });
      }
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
    background: #F2F2F2;

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
        position: relative;

        .u-demo-clock {
          position: absolute;
          top: 30upx;
          left: 50%;
          /* 将文本的左侧距离容器宽度的一半对齐 */
          transform: translateX(-50%);
        }
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
    margin-top: 155upx;
    width: 100%;
    /*height: 100%;*/
    /*height: calc(100% - 454upx);*/
    .swiper{
      height: 100%;
    }
    .swiper-item{
      height: 100%;
    }
    .tab-content {
      background: #fff;
      width: 100%;

      .tab-body {
        width: 70%;
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
    /*background-color: #f7fbff;*/
    margin-top: 20upx;
    margin-bottom: 20upx;
    border-bottom: 2upx solid #e5e5e5;

    .question-body-name {
      height: 80upx;
      line-height: 80upx;
      display: flex;
      justify-content: space-between;
      font-size: 34upx;
    }
    .question-info {
      padding: 6upx 20upx;
      background-color: #f4f5f6;
      border-radius: 5px;
      font-size: 28upx;
      /*margin-left: 24upx;*/
      margin: 10upx 0 0 24upx ;
    }

    .question-body-type {
      font-size: 26upx;
      color: #7b8293;
    }
  }

  .u-menu-wrap {
    flex: 1;
    display: flex;
    height: calc(100vh - var(--window-bottom) - var(--window-top) - 228px);
  }
  .u-menu-wrap_2 {
    flex: 1;
    display: flex;
    height: calc(100vh - var(--window-bottom) - var(--window-top) - 128px);
  }



  .right-box {
    background-color: rgb(250, 250, 250);
  }

  .page-view {
    padding: 0 16rpx 16rpx;
    /*background: #ffffff;*/
  }
  .page-view_2 {
    padding: 0 16rpx 16rpx;
    background: #ffffff;
    margin: 20upx;
    border-radius: 30upx;
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
    height: 120upx;
    display: flex;
    align-items: center;
    justify-content: center;
    /*line-height: 120upx;*/
    padding: 10upx 20upx;
    background-color: #fff;
    position: fixed;
    bottom: 0;
    width: 100%;
    z-index: 1000;

    .bar-button {
      width: 76%
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
    /*.u-th{*/
    /*  flex: none;*/
    /*  width: 80upx;*/
    /*}*/
    .u-th:first-child{
      margin-left: 28upx;
    }
    /*.u-th:nth-child(4){*/
    /*  width: 160upx;*/
    /*}*/
    /*.u-th:nth-child(5){*/
    /*  width: 200upx;*/
    /*}*/
    /*.u-tr .u-td{*/
    /*  width: 80upx;*/
    /*}*/
    /*.u-tr .u-td:first-child{*/
    /*  width: 200upx;*/
    /*  word-break: break-all;*/
    /*}*/
    .u-tr .u-td:nth-child(4){
      word-break: break-all;
      /*word-wrap: break-word;*/
    }
    .u-tr .u-td:nth-child(5){
      word-break: break-all;
    }

    .u-checkbox__label{
      width: calc(100% - 28upx) !important;
    }
    .u-checkbox{
      width: 100% !important;
    }
    .all{
      /*width: 100% !important;*/
      width:30% !important;
    }
    .u-checkbox-group{
      display: block;
      width: 100% !important;
    }
  }
  .question-info_invoiceNo {
    padding: 6upx 20upx 0upx;
    background-color: #f4f5f6;
    border-radius: 5px;
    font-size: 28upx;
    /*margin: 0upx 20upx 20upx 0 ;*/
  }
  .box_card{
    margin: 20upx 20upx 0 20upx;
    border-radius: 10upx;
    background-color: #ffffff;
    padding: 20upx;


    .question-body-name {
      height: 80upx;
      line-height: 80upx;
      display: flex;
      justify-content: space-between;
      font-size: 34upx;
    }
    .question-info {
      padding: 6upx 20upx;
      /*background-color: #f4f5f6;*/
      border-radius: 5px;
      font-size: 28upx;
      /*margin-left: 24upx;*/
    }

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
  }
  .baseInfo {
    width: 70%;

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
    width: 100%;
    color: #000000;
    padding: 0 20upx;
    margin: 20upx 0;
    display: flex;
    justify-content: space-between;
  }
  .single_drug {
    width: 100%;
    border-bottom: 1upx solid #e5e5e5;

    .title {
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
    padding: 0 40upx;
    display: flex;
    width: 100%;
    .item_label{
      color: #878787;
      width: 30%;
    }
    .item_content{
      width: 70%;
      height: fit-content;
    }
  }

  .single_drug_num {
    height: 140upx;
    width: 100%;
    padding: 0 40upx;
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
    .u-checkbox__label{
      margin: 0;
    }
  }
</style>

