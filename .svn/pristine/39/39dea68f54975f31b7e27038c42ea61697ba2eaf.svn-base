<template>
  <div id="secondEchart" :style="{ height: height, width: width }"></div>
</template>

      <script>
import * as echarts from "echarts";
import $ from "jquery";
export default {
  props: {
    width: {
      type: String,
      default: "200px"
    },
    height: {
      type: String,
      default: "400px"
    },
    xData: {
      type: Array,
      default: function() {
        return [];
      }
    },
    yData: {
      type: Array,
      default: function() {
        return [];
      }
    },
    typeNum: {
      type: String,
      default: "2"
    }
  },
  watch: {
    xData: {
      deep: true,
      handler() {
        if (this.myChart) {
          this.getDataSecond();
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
          this.getDataSecond();
        }
      }
    },
    screenWidth() {
      this.myChart.resize(); //当监听到变量screenWidth更新后调用myChart的resize()方法
    },
    width: {
      deep: true,
      handler() {
        if (this.myChart) {
          this.getDataSecond();
          this.$nextTick(() => {
            echarts.init(document.getElementById("secondEchart")).resize();
          });
        }
      }
    }
  },
  beforeDestroy() {
    if (!this.myChart) {
      return;
    }
    this.myChart.dispose();
    this.myChart = null;
  },
  data() {
    return {
      myChart: null,
      screenWidth: document.body.clientWidth //获取当前宽度并存储
    };
  },
  mounted() {
    this.getDataSecond();
  },
  methods: {
    getDataSecond() {
      this.myChart = echarts.init(document.getElementById("secondEchart"));
      var option;

      option = {
        color: ["#2c9ef7", "#f4253e"],
        legend: {
          right: 0,
          icon: "rect",
          itemHeight: 15,
          itemWidth: 15
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow"
          },
          formatter: function(params) {
            let html = params[0].name;
            params.forEach((item, index) => {
              html += `<br/>${item.marker + item.seriesName}: ${
                item.value === 1.1 ? 1 : item.value  + '%'
              }`;
            });
            return html;
          }
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          top: "15%",
          containLabel: true
        },
        xAxis: [
          {
            type: "category",
            triggerEvent: true,
            data: this.yData.map(item => {
              if (item.dateRange.indexOf(":00") != -1) {
                return item.dateRange.split(":")[0] + "点";
              } else {
                return item.dateRange;
              }
            }),
            axisLine: {
              lineStyle: {
                color: "#b1b1b1" //x轴线条颜色
              }
            },
            axisLabel: {
              formatter: function(value) {
                if (value.length > 11) {
                  value = value.substring(0, 11) + "..";
                }
                return value;
              },
              rotate: this.xData.length < 11 ? 0 : 35,
              textStyle: {
                color: "black"
              }
            },
            axisTick: {
              show: false,
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            // name: '数量(份)',
            nameTextStyle: {
              color: "black"
            },
            // type: 'log',
            axisLine: {
              lineStyle: {
                color: "#b1b1b1" //x轴线条颜色
              }
            },
            axisLabel: {
              textStyle: {
                color: "black"
              },
              formatter: function (value) {
                return value + '%';//第2步，将y轴最小值1变成从0开始；
              }
            },
            axisTick: { show: false }
          }
        ],
        series: [
          {
            name: "上报成功数据量",
            type: "line",
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgb(44, 158, 247)"
                },
                {
                  offset: 1,
                  color: "rgb(255, 255, 255)"
                }
              ])
            },
            data: this.yData.map(item => {
              return {
                name: item.dateRange,
                value: item.reportSuccRate
              };
            })
          },
          {
            name: "上报失败数据量",
            type: "line",
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgb(244, 37, 62)"
                },
                {
                  offset: 1,
                  color: "rgb(255, 255, 255)"
                }
              ])
            },
            data: this.yData.map(item => {
              return {
                name: item.dateRange,
                value: item.reportFailRate
              };
            })
          },
        ]
      };
      let that = this;
      window.addEventListener("resize", () => {
        //使用resize监听方法
        that.screenWidth = document.body.clientWidth; //给存储的变量screenWidth赋当前窗口的宽度
        window.screenWidth = that.screenWidth;
      });
      option && this.myChart.setOption(option);

      // extension(this.myChart);
      // function extension(mychart) {
      //   //判断是否创建过div框,如果创建过就不再创建了
      //   var id = document.getElementById("extension");
      //   if (!id) {
      //     var div = "<div id = 'extension' sytle=\"display:line\"></div>";
      //     $("html").append(div);
      //   }

      //   mychart.on("mouseover", function(params) {
      //     if (params.componentType == "xAxis") {
      //       $("#extension")
      //         .css({
      //           position: "absolute",
      //           color: "black",
      //           //"border":"solid 2px white",
      //           "font-family": "Arial",
      //           "font-size": "20px",
      //           padding: "5px",
      //           display: "inline"
      //         })
      //         .text(params.value);

      //       $("html").mousemove(function(event) {
      //         var xx = event.pageX - 30;
      //         var yy = event.pageY + 20;
      //         $("#extension")
      //           .css("top", yy)
      //           .css("left", xx);
      //       });
      //     }
      //   });

      //   mychart.on("mouseout", function(params) {
      //     if (params.componentType == "xAxis") {
      //       $("#extension").css("display", "none");
      //     }
      //   });
      // }
    }
  }
};
</script>
