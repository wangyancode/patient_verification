<template>
  <div :id="id" :class="className" :style="{ height: height, width: width }" :xData="xData" :yData="yData" />
</template>

<script>
import * as echarts from 'echarts';

export default {
  props: {
    className: {
      type: String,
      default: 'chartLineMarker'
    },
    id: {
      type: String,
      default: 'chartLineMarker'
    },
    width: {
      type: String,
      default: '200px'
    },
    height: {
      type: String,
      default: '200px'
    },
    xData: {
      type: Array,
      default: function () { return [] }
    },
    yData: {
      type: Array,
      default: function () { return [] }
    },
  },
  watch: {
    xData: {
      deep: true,
      handler() {
        if (this.chart) {

          this.initChart()
        }
      }
    },
    yData: {
      deep: true,
      handler() {
        if (this.chart) {
          this.initChart()
        }
      }
    },
  },
  data() {
    return {
      chart: null
    }
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(document.getElementById(this.id))
      this.chart.setOption({
        backgroundColor: '#fff',
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            lineStyle: {
              color: '#57617B'
            }
          }
        },
        legend: {
          x: 'center',
          icon: 'rect',
          itemWidth: 14,
          itemHeight: 16,
          itemGap: 60,
          data: ['接入总数', '新增主索引量', '处理数据量'],
          y: 'bottom',
          icon: 'diamond',
          textStyle: {
            fontSize: 12,
            color: '#000'
          }
        },
        grid: {
          top: 30,
          left: '3%',
          right: '3%',
          bottom: '10%',
          containLabel: true
        },
        xAxis: [{
          type: 'category',
          boundaryGap: false,
          axisLine: {
            lineStyle: {
              color: '#aeb5c1'
            }
          },
          data: this.xData
        }],
        yAxis: [{
          type: 'value',
          axisTick: {
            show: false
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#aeb5c1'
            }
          },
          axisLabel: {
            margin: 10,
            textStyle: {
              fontSize: 14
            }
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(0,0,0,.1)'
            }
          }
        }],
        dataZoom: [{
          type: 'slider',
          show: false,//控制滚动条显示隐藏
          realtime: true,
          end: 100,
          zoomLock: true, //控制面板是否进行缩放
        },
        {
          type: 'inside',
          // start: 0,//初始化时，滑动条宽度开始标度
          // end: 100,  //初始化时，滑动条宽度结束标度
          zoomOnMouseWheel: true,  //滚轮是否触发缩放
          moveOnMouseMove: true,  //鼠标滚轮触发滚动
          moveOnMouseWheel: false
        }],
        series: [{
          name: '接入总数',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 3,
          showSymbol: true,
          lineStyle: {
            normal: {
              width: 1
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(4,172,4, 0.7)'
              }, {
                offset: 0.9,
                color: 'rgba(255, 255, 255, 0.3)'
              }], false),
              shadowColor: 'rgba(0, 0, 0, 0.1)',
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: 'rgb(4,172,4)',
              borderColor: 'rgba(4,172,4,0.1)',
              borderWidth: 6
            }
          },
          data: this.yData[0],
        }, {
          name: '新增主索引量',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 3,
          showSymbol: true,
          lineStyle: {
            normal: {
              width: 1
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(126,145,255,0.7)'
              }, {
                offset: 0.9,
                color: 'rgba(255, 255, 255, 0.3)'
              }], false),
              shadowColor: 'rgba(0, 0, 0, 0.1)',
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: 'rgb(126,145,255)',
              borderColor: 'rgba(126,145,255,0.1)',
              borderWidth: 6

            }
          },
          data: this.yData[1],
        }, {
          name: '处理数据量',
          type: 'line',
          smooth: true,
          symbol: 'circle',
          symbolSize: 3,
          showSymbol: true,
          lineStyle: {
            normal: {
              width: 1
            }
          },
          areaStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                offset: 0,
                color: 'rgba(243,108,35,0.7)'
              }, {
                offset: 0.9,
                color: 'rgba(255, 255, 255,0.3)'
              }], false),
              shadowColor: 'rgba(0, 0, 0, 0.1)',
              shadowBlur: 10
            }
          },
          itemStyle: {
            normal: {
              color: 'rgb(243,108,35)',
              borderColor: 'rgba(243,108,35,0.1)',
              borderWidth: 6
            }
          },
          data: this.yData[2],
        }]
      })
    }
  }
}
</script>
