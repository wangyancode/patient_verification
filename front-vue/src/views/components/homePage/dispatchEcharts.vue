<template>
  <div id="dispatchEcharts" :style="{ height: height, width: width }"></div>
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
      type: Object,
      default: function() {
        return {};
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
          // const index = this.yData.indexOf(1); // ?️  0
          // if (index !== -1) {
          //   this.yData[index] = 1.1;
          // }
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
            echarts.init(document.getElementById("dispatchEcharts")).resize();
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
      this.myChart = echarts.init(document.getElementById("dispatchEcharts"));
      let xDatas = this.xData;
      var dataZoomEnd = xDatas.length>20?30:100
      var option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff"
            },
          },
          formatter: function(params) {
            let html = params[0].name;
            params.forEach((item, index) => {
              html += `<br/>${item.marker + item.seriesName}: ${
                item.value === 1.1 ? 1 : item.value + "s"
              }`;
            });
            return html;
          }
        },
        dataZoom:[
          {
            type:'slider',
            show:dataZoomEnd==100?false:true,
            realtime:true,
            start:0,
            end:dataZoomEnd
          },
          {
            type:'inside',
            realtime:true,
            start:0,
            end:dataZoomEnd
          }
        ],
        grid: {
          left: "3%",
          right: "4%",
          bottom: "18%",
          top: "12%",
          textStyle: {
            color: "#fff"
          }
        },
        legend: {
          right: 0,
          icon: "rect",
          itemHeight: 15,
          itemWidth: 15,
          itemGap: 35
        },

        calculable: true,
        xAxis: [
          {
            type: "category",
            triggerEvent: true,
            axisLine: {
              lineStyle: {
                color: "#90979c"
              }
            },
            splitLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            splitArea: {
              show: false
            },
            axisLabel: {
              interval: 0,
              formatter: function(value) {
                // console.log(xDatas);
                if (xDatas.length > 4) {
                  value = value.substring(0, 7) + "..";
                }
                return value;
              },
              rotate: this.xData.length > 10 ? 35 : 0
            },
            data: this.xData.map(item => {
              // console.log(item);
              if (item.dateRange.indexOf(":00") != -1) {
                return item.dateRange;
                // return item.dateRange.split(":")[0] + "点";
              } else {
                return item.dateRange;
              }
            })
          }
        ],
        yAxis: [
          {
            type: "value",
            splitLine: {
              show: false
            },
            axisLine: {
              lineStyle: {
                color: "#90979c"
              }
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: "black"
              }
              // formatter: function(value) {
              //   console.log(value,'valuessssssss');
              //   // return value + "%"; //第2步，将y轴最小值1变成从0开始；
              // }
            },
            splitArea: {
              show: false
            }
          }
        ],
        series: [
          {
            name: "采集",
            type: "bar",
            stack: "总量",
            barMaxWidth: 35,
            barGap: "10%",
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
            itemStyle: {
              normal: {
                color: "rgba(175, 140, 249,1)",
                label: {
                  show: false,
                  textStyle: {
                    color: "#fff"
                  },
                  position: "insideTop",
                  formatter: function(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.yData.gather
          },
          {
            name: "转换",
            type: "bar",
            stack: "总量",
            barMaxWidth: 35,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "rgba(243, 127, 111,1)",
                label: {
                  show: false,
                  textStyle: {
                    color: "#fff"
                  },
                  position: "insideTop",
                  formatter: function(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.yData.convert
          },
          {
            name: "校验",
            type: "bar",
            stack: "总量",
            barMaxWidth: 35,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "rgba(19, 212, 14,1)",
                label: {
                  show: false,
                  textStyle: {
                    color: "#fff"
                  },
                  position: "insideTop",
                  formatter: function(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.yData.check
          },
          {
            name: "上报",
            type: "bar",
            stack: "总量",
            barMaxWidth: 35,
            barGap: "10%",
            itemStyle: {
              normal: {
                color: "rgba(250, 224, 41,1)",
                label: {
                  show: false,
                  textStyle: {
                    color: "#fff"
                  },
                  position: "insideTop",
                  formatter: function(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.yData.escalation
          },
          {
            name: "自定义",
            type: "bar",
            stack: "总量",
            itemStyle: {
              normal: {
                color: "rgba(44, 158, 247,1)",
                barBorderRadius: 0,
                label: {
                  show: false,
                  position: "top",
                  formatter: function(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.yData.custom
          },
          {
            name: "平均耗时",
            type: "line",
            // "stack": "总量",
            smooth: true,
            symbol: "none",
            itemStyle: {
              normal: {
                color: "rgba(245, 91, 110,1)",
                barBorderRadius: 0,
                label: {
                  show: false,
                  position: "top",
                  formatter: function(p) {
                    return p.value > 0 ? p.value : "";
                  }
                }
              }
            },
            data: this.yData.everyTime
          }
        ]
      };
      let that = this;
      window.addEventListener("resize", () => {
        //使用resize监听方法
        that.screenWidth = document.body.clientWidth; //给存储的变量screenWidth赋当前窗口的宽度
        window.screenWidth = that.screenWidth;
      });
      option && this.myChart.setOption(option);

      extension(this.myChart);

      function extension(mychart) {
        //判断是否创建过div框,如果创建过就不再创建了
        var id = document.getElementById("extension");
        if (!id) {
          var div = "<div id = 'extension' sytle=\"display:line\"></div>";
          $("html").append(div);
        }

        mychart.on("mouseover", function(params) {
          if (params.componentType == "xAxis") {
            $("#extension")
              .css({
                position: "absolute",
                color: "black",
                //"border":"solid 2px white",
                "font-family": "Arial",
                "font-size": "20px",
                padding: "5px",
                display: "inline"
              })
              .text(params.value);

            $("html").mousemove(function(event) {
              var xx = event.pageX - 30;
              var yy = event.pageY + 20;
              $("#extension")
                .css("top", yy)
                .css("left", xx);
            });
          }
        });

        mychart.on("mouseout", function(params) {
          if (params.componentType == "xAxis") {
            $("#extension").css("display", "none");
          }
        });
      }
    }
  }
};
</script>