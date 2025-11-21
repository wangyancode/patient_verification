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
      default: '400px'
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
        if (this.myChart) {
          this.initChart()
        }
      }
    },
    yData: {
      deep: true,
      handler() {
        if (this.myChart) {
          const index = this.yData.indexOf(1); // ?️  0
          if (index !== -1) {
            this.yData[index] = 1.1;
          }
          this.initChart()
        }
      }
    },
    screenWidth(){
      this.myChart.resize() //当监听到变量screenWidth更新后调用myChart的resize()方法
    },
    width: {
      deep: true,
      handler() {
        if (this.myChart) {
          this.initChart()
          console.log(this.width,'234');
          this.$nextTick(()=> {
            echarts.init(document.getElementById(this.id)).resize()
          })
        }
      }
    },
  },
  data() {
    return {
      screenWidth:document.body.clientWidth,  //获取当前宽度并存储
      myChart: null
    }
  },
  mounted() {
    this.initChart()
  },
  beforeDestroy() {
    if (!this.myChart) {
      return
    }
    this.myChart.dispose()
    this.myChart = null
  },
  methods: {
    initChart() {

      this.myChart = echarts.init(document.getElementById(this.id))
      var option;

      option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          formatter:function(params){
            let html = params[0].name
            params.forEach((item,index)=>{
                html+=(`<br/>${item.marker+item.seriesName}: ${item.value===1.1 ? 1 : item.value}`)
            })
            return html;
            }

          },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        color: ['#2c9ef7'],
        dataZoom: [{
          show: true, // 为true 滚动条出现
          type: 'slider', // 有type这个属性，滚动条在最下面，也可以不行，写y：36，这表示距离顶端36px，一般就是在图上面。
          height: 10, // 表示滚动条的高度，也就是粗细
          bottom:0,
          startValue:0,
          endValue: 5
        },{
          type: "inside",  // 支持内部鼠标滚动平移
          zoomOnMouseWheel: true,  //滚轮是否触发缩放
          moveOnMouseMove: true,  //鼠标滚轮触发滚动
          moveOnMouseWheel: false,
          start: 1,//初始化时，滑动条宽度开始标度
          end: 100  //初始化时，滑动条宽度结束标度
        }],
        xAxis: [
          {
            type: 'category',
            data: this.xData,
            axisLine: {
              lineStyle: {
                color: '#b1b1b1'//x轴线条颜色
              }
            },
            axisLabel: {
              textStyle: {
                  color: 'black'
              }
            },
            axisTick: {
              show:false,
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            name: '数量(份)',
            nameTextStyle:{
              color:"black"
            },
            type: 'log',
            axisLine: {
              lineStyle: {
                color: '#b1b1b1'//x轴线条颜色
              }
            },
            axisLabel: {
              textStyle: {
                color: 'black'
              },
              formatter: function (value) {
                return value === 1 ? 0 : value;//第2步，将y轴最小值1变成从0开始；
              }
            },
            axisTick:{ show:false }
          }
        ],
        series: [
          {
            name: '文档数',
            type: 'bar',
            barWidth: 60,
            data: this.yData
          }
        ]
      };
      let that = this
      window.addEventListener("resize",()=>{  //使用resize监听方法
        that.screenWidth = document.body.clientWidth  //给存储的变量screenWidth赋当前窗口的宽度
        window.screenWidth = that.screenWidth
      })
      option && this.myChart.setOption(option);
    }
  }
}
</script>
