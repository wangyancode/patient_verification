<template>
    <div class="back">
        <div class="scoll-table">
            <div class="scroll-table-top">
                <div class="rank" v-if="rankFlag">序号</div>
                <div v-for="(item, index) in columnList" :key="index" :style="{ width: item.width + '%',textAlign:'center'}">
                    {{ item.label }}
                </div>
            </div>
            <div
                class="seamless-warp"
                v-if="listData.length < scollLength"
                :style="{ height: scollLength * singleHeight + 'px' }"
            >
                <ul class="item">
                    <li
                        v-for="(item, index) in listData"
                        :key="index"
                        style="height: 240px;"
                    >
                        <div class="index" :style="{ color: index < 3 ? '#ffffff' : 'white' }" v-if="rankFlag">
                            {{ index + 1 }}
                        </div>
                        <div v-for="(el, indexs) in columnList" :key="indexs" :style="{ width: el.width + '%' }">
                          <el-tag
                            style="margin-right: 3px;width: 220px;"
                            :type="serverStatus[el.prop]" v-if="el.type=='tag'">
                            {{item[el.prop]}}
                          </el-tag>
                          <span v-else>{{item[el.prop]}}</span>
<!--                          <el-tooltip-->
<!--                            class="item"-->
<!--                            effect="light"-->
<!--                            :content="`CPU ${item.cpuUseRate || 0}%  硬盘 ${item.diskUseRate || 0}%  内存 ${item.memUseRate || 0}%`"-->
<!--                            placement="bottom-start"-->
<!--                            v-if="el.type=='status'"-->
<!--                          >-->
<!--                            <div  style="display: flex;">-->
<!--                              <el-tag-->
<!--                                style="margin-right: 3px;width: 220px;"-->
<!--                                :type="serverStatus[item.cpuUseRateStatus]">-->
<!--                                {{`CPU ${item.cpuUseRate || 0}%`}}-->
<!--                              </el-tag>-->
<!--                              <el-tag-->
<!--                                style="margin-right: 3px;"-->
<!--                                :type="serverStatus[item.diskUseRateStatus]">-->
<!--                                {{`硬盘 ${item.diskUseRate || 0}%`}}-->
<!--                              </el-tag>-->
<!--                              <el-tag-->
<!--                                :type="serverStatus[item.memUseRateStatus]">-->
<!--                                {{`内存 ${item.memUseRate || 0}%`}}-->
<!--                              </el-tag>-->
<!--                            </div>-->
<!--                          </el-tooltip>-->
<!--                          <el-tooltip-->
<!--                            class="item"-->
<!--                            effect="dark"-->
<!--                            :disabled="item[el.prop]?(item[el.prop].length > 4 ? false : true):true"-->
<!--                            :content="item[el.prop]"-->
<!--                            placement="bottom-start"-->
<!--                            v-else-->
<!--                          >-->
<!--                            <span >{{ item[el.prop] }}</span>-->
<!--                          </el-tooltip>-->
                        </div>
                    </li>
                </ul>
            </div>
            <div v-else>

                <vue-seamless-scroll
                    :data="listData"
                    class="seamless-warp"
                    :class-option="optionSingleHeight"
                    style="height: 240px;"
                >
                    <ul class="item">
                        <li
                            v-for="(item, index) in listData"
                            :key="index"
                            :style="{ height: singleHeight + 'px', lineHeight: singleHeight + 'px' }"
                        >
                            <div class="index" :style="{ color: index < 3 ? '#ffffff' : '#FAFBFC' }" v-if="rankFlag">
                                {{ index + 1 }}
                            </div>
                            <div v-for="(el, indexs) in columnList" :key="indexs" :style="{ width: el.width + '%' }">
                              <el-tag
                                size="mini"
                                :type="serverStatus[item[el.prop+'Status']]" v-if="el.type=='tag'">
                                {{`${item[el.prop] || 0}%`}}
                              </el-tag>
                              <span v-else>{{item[el.prop]}}</span>
<!--                                <el-tooltip-->
<!--                                    class="item"-->
<!--                                    effect="light"-->
<!--                                    :content="`CPU ${item.cpuUseRate || 0}%  硬盘 ${item.diskUseRate || 0}%  内存 ${item.memUseRate || 0}%`"-->
<!--                                    placement="bottom-start"-->
<!--                                    v-if="el.type=='status'"-->
<!--                                >-->
<!--                                  <div  style="display: flex;margin: 5px 0;">-->
<!--                                    <el-tag-->
<!--                                      style="margin-right: 3px;width: 220px;"-->
<!--                                      :type="serverStatus[item.cpuUseRateStatus]">-->
<!--                                      {{`CPU ${item.cpuUseRate || 0}%`}}-->
<!--                                    </el-tag>-->
<!--                                    <el-tag-->
<!--                                      style="margin-right: 3px;"-->
<!--                                      :type="serverStatus[item.diskUseRateStatus]">-->
<!--                                      {{`硬盘 ${item.diskUseRate || 0}%`}}-->
<!--                                    </el-tag>-->
<!--                                    <el-tag-->
<!--                                      :type="serverStatus[item.memUseRateStatus]">-->
<!--                                      {{`内存 ${item.memUseRate || 0}%`}}-->
<!--                                    </el-tag>-->
<!--                                  </div>-->
<!--                                </el-tooltip>-->
<!--                                  <el-tooltip-->
<!--                                    class="item"-->
<!--                                    effect="dark"-->
<!--                                    :disabled="item[el.prop]?(item[el.prop].length > 4 ? false : true):true"-->
<!--                                    :content="item[el.prop]"-->
<!--                                    placement="bottom-start"-->
<!--                                    v-else-->
<!--                                  >-->
<!--                                  <span >{{ item[el.prop] }}</span>-->
<!--                                </el-tooltip>-->
                            </div>
                        </li>
                    </ul>
                </vue-seamless-scroll>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "HealthTable",
    data() {
        return {
          serverStatus: ['success', 'danger', 'warning'],
          btnName: {
                1: "一级预警",
                2: "二级级预警",
                3: "三级预警",
                4: "四级预警",
                9: "其他预警",
            },
        };
    },
    computed: {
        optionSingleHeight() {
            return {
                singleHeight: 40,
            };
        },
    },
    props: {
        listData: {
            type: Array,
            default: () => [],
        },
        columnList: {
            type: Array,
            default: () => [],
        },
        scollLength: {
            type: Number,
            default: () => 0,
        },
        singleHeight: {
            type: Number,
            default: () => 40,
        },
        //是否需要排名列
        rankFlag: {
            type: Boolean,
            default: true,
        },
    },
    methods: {
        handleClick(val) {
            this.$emit("handleClick", val);
        },
    },
};
</script>
<style lang="scss" scoped>
.scoll-table {
    .scroll-table-top {
        display: flex;
        color: #FFFFFF;
        font-size: 12px;
        padding: 7px 0;
        border-bottom: 1px solid #163762;
        .rank {
          width: 59px;
          text-align: center;
        }
    }
    .seamless-warp {
        // height: 216px;
      text-align: center;
        overflow: hidden;
        color: #fafbfc;
        ul {
          padding-inline-start: 0px;
          margin: 0;
            li {
                font-size: 12px;
                // height: 22px;
                // line-height: 22px;
                display: flex;
                .cell {
                    display: inline-block;
                    padding-left: 20px;
                    height: 22px;
                    overflow: hidden;
                }
                .index {
                    //   color: #30C2FF;
                    /*padding-left: 15px;*/
                    width: 59px;
                    font-size: 12px;
                }
                span {
                    display: block;
                    width: 85%;
                    white-space: nowrap; /*设置不换行*/
                    overflow: hidden; /*设置隐藏*/
                    text-overflow: ellipsis; /*设置隐藏部分为省略号*/
                }
            }
            li:nth-child(even) {
                background: rgba(230, 233, 236, 0.1);
            }
            // .odd-li {
            //   background: #1d3455 100%;
            // }
            // .even-li {
            //   background: #ccc;
            // }
        }
    .btn {
        cursor: pointer;
        padding: 1px 12px 1px 12px;
        color: #313d47;
        display: inline;
    }
    .yellow-btn {
        background: #FFF099;
        border: 1px solid #FFE064;
    }
    .red-btn {
        background:#FF8585;
        border: 1px solid #FF2738;
    }
    .orange-btn{
       background: rgb(206, 129, 92);
       border: 1px solid rgb(212, 69, 0);
    }
    .blue-btn{
      background: #61b2f7;
      border: 1px solid #259bff;
    }
    .other-btn{
          background: #63ecc4;
          border: 1px solid #1defb0;
    }
    }

    a {
        color: #259bff;
    }
}
</style>
<style>
/*.el-tag.el-tag--info {*/
/*    background-color: transparent;*/
/*    border-color: transparent;*/
/*    color: #fefefe;*/
/*    display: block;*/
/*    white-space: nowrap;*/
/*    width: 45%;*/
/*    overflow: hidden;*/
/*    !* border: 1px solid; *!*/
/*    text-overflow: ellipsis;*/
/*}*/
</style>
