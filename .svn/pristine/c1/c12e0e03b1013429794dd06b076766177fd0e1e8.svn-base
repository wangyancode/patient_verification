<template>
  <div id="container"></div>
</template>

<script>
import { mapGetters } from 'vuex'
import { Graph, Node, Path, Cell } from '@antv/x6';
import insertCss from 'insert-css'

export default {
  props: {
    processInfo: {
      type: Array,
      default: function () { return [] }
    },
    isBrowse: {
      type: Boolean,
      default: true
    },
  },
  watch: {
    'processInfo': {
      deep: true,
      immediate: true,
      handler(val, oldVal) {
        if (this.graph) {
          this.graph.dispose()
        }
        this.init()
      },
    },
    'type': {
      deep: true,
      immediate: true,
      handler(val, oldVal) {
        this.graph = null
      },
    }
  },
  computed: {
    processData: {
      get() {
        let nodes = []
        let edges = []
        let wrapHtml = `<div class="my-btn"></div>`
        let sortArr = [] //节点顺序与当前x值（去除相同）
        let allSortInfo = []//节点顺序与对应id
        let nodeExcuState = 1//默认节点执行状态未执行
        let sameTimes = 0
        //处理数据
        if (this.processInfo.length > 0) {

          //循环设置节点
          this.processInfo.forEach((val, key) => {
            if (val.nodeExcuState || this.isBrowse == true) { //管理里的浏览状态（非列表查询）或执行状态为已执行
              wrapHtml = `<div class="my-btn-active"></div>`
              nodeExcuState = 2 //节点执行状态已执行
            }
            let nodesDefault = {
              id: 'node1', // String，可选，节点的唯一标识
              x: 80,       // Number，必选，节点位置的 x 值
              y: 240,       // Number，必选，节点位置的 y 值
              width: 16,   // Number，可选，节点大小的 width 值
              height: 16,  // Number，可选，节点大小的 height 值
              shape: 'html',
              html() {
                const wrap = document.createElement('div')
                wrap.innerHTML = `<div class="my-btn-active"></div>`
                return wrap
              },
              attrs: {
                // 指定 text 元素的样式
                label: {
                  text: '开立时间1', // 文字
                  fill: '#3c3e41', // 文字颜色
                  fontSize: 14,
                  refY: -16, // x 轴偏移，类似 css 中的 margin-left
                },
              },
            }
            nodesDefault.id = val.id
            nodesDefault.x = 80 + (key - sameTimes) * 120
            nodesDefault.y = 240
            nodesDefault.html = function () {
              const wrap = document.createElement('div')
              wrap.innerHTML = wrapHtml
              return wrap
            };
            let sameSortIndex = sortArr.findIndex((item) => {
              return item.sort == val.sort
            })
            if (sameSortIndex !== -1) {
              nodesDefault.x = sortArr[sameSortIndex].xdata
              sameTimes++
              nodesDefault.y = 120
            } else {
              sortArr.push({ 'sort': val.sort, 'xdata': nodesDefault.x })
            }
            allSortInfo.push({ 'sort': val.sort, 'id': val.id, 'xdata': nodesDefault.x, 'ydata': nodesDefault.y, 'nodeExcuState': nodeExcuState })
            // nodesDefault.attrs.label.text = val.nodeInfo.name
            nodes.push(nodesDefault)
          });

          // console.log('nodes', nodes);
          // console.log('allSortInfo', allSortInfo);

          //循环设置连线(只考虑并行最多只存在两个时)
          if (allSortInfo.length > 1) {
            allSortInfo.forEach((val, key) => {
              //默认节点与节点间连线信息
              let edgesDefault = {
                source: 'node1',
                target: 'node2',
                attrs: {
                  line: {
                    stroke: '#9fa1a4',
                    strokeDasharray: 3,
                    targetMarker: 'classic',
                  },
                },
              }


              //判断与上一个值是否相同，如果相同，则添加与上上个不同的值关系连线
              if (allSortInfo[key - 1] && allSortInfo[key - 2] && allSortInfo[key - 1].sort == allSortInfo[key].sort) {
                let edgesDefault = {
                  source: 'node1',
                  target: 'node2',
                  attrs: {
                    line: {
                      stroke: '#9fa1a4',
                      strokeDasharray: 3,
                      targetMarker: 'classic',
                    },
                  },
                }
                edgesDefault.source = allSortInfo[key - 2].id;
                edgesDefault.target = allSortInfo[key].id;
                edgesDefault.vertices = [{ x: allSortInfo[key - 2].xdata + 8, y: allSortInfo[key].ydata + 8 }];
                if (allSortInfo[key].nodeExcuState == 2) {
                  edgesDefault.attrs.line.stroke = '#1890ff' //线颜色
                  edgesDefault.attrs.line.style = { animation: 'ant-line 30s infinite linear' } //线流动
                }
                edges.push(edgesDefault)
              }
              if (allSortInfo[key + 1]) {
                //与下一个值不同时，添加连线，并判断与上一个值是否相同
                if (allSortInfo[key + 1].sort != allSortInfo[key].sort) {
                  edgesDefault.source = allSortInfo[key].id;
                  edgesDefault.target = allSortInfo[key + 1].id;
                  if (allSortInfo[key - 1] && allSortInfo[key - 1].sort == allSortInfo[key].sort) {
                    edgesDefault.vertices = [{ x: allSortInfo[key + 1].xdata + 8, y: allSortInfo[key].ydata + 8 }];
                  }
                  if (allSortInfo[key + 1].nodeExcuState == 2) {
                    edgesDefault.attrs.line.stroke = '#1890ff' //线颜色
                    edgesDefault.attrs.line.style = { animation: 'ant-line 30s infinite linear' }//线流动
                  }
                  edges.push(edgesDefault)
                } else {
                  //与下一个值相同时，则添加与下下个不同的值关系连线
                  if (allSortInfo[key + 2]) {
                    edgesDefault.source = allSortInfo[key].id;
                    edgesDefault.target = allSortInfo[key + 2].id;
                    if (allSortInfo[key + 2].nodeExcuState == 2) {
                      edgesDefault.attrs.line.stroke = '#1890ff' //线颜色
                      edgesDefault.attrs.line.style = { animation: 'ant-line 30s infinite linear' } //线流动
                    }
                    edges.push(edgesDefault)
                  }
                }



              }
            });
          }

        }

        //返回流程图数据
        return { 'nodes': nodes, 'edges': edges }
      },
    },

  },
  data() {
    return {
      dataInfo: {
        // 节点
        nodes: [
          {
            shape: 'html',
            id: 'node1', // String，可选，节点的唯一标识
            x: 80,       // Number，必选，节点位置的 x 值
            y: 240,       // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            markup: [
              {
                tagName: 'foreignObject',
                attrs: {
                  class: 'my-btn-active',
                },
                shape: 'html',
                html() {
                  const wrap = document.createElement('div')
                  wrap.innerHTML = `<div class="my-btn-active"></div>`
                  return wrap
                },
              },
              {
                tagName: 'text',
                attrs: {
                  class: 'title',
                },
              },

              {
                tagName: 'text',
                attrs: {
                  class: 'name',
                },
              },
              {
                tagName: 'text',
                attrs: {
                  class: 'department',
                },
              },
              {
                tagName: 'text',
                attrs: {
                  class: 'time',
                },
              },

            ],
            attrs: {
              '.my-btn-active': {
                stroke: '#2c9ef7',
                fill: '#2c9ef7',
              },

              '.title': {
                text: '开立时间1',
                refY: -16,
                fill: '#3c3e41',
                fontSize: 14,
              },
              '.name': {
                text: '张三',
                refY: 24,
                fill: '#3c3e41',
                fontSize: 14,
              },
              '.department': {
                text: '检查科室',
                refY: 40,
                fill: '#3c3e41',
                fontSize: 14,
              },
              '.time': {
                text: '05-06 16:13:12',
                refY: 58,
                fill: '#3c3e41',
                fontSize: 14,
              },
            },
          },
          {
            id: 'node2', // String，节点的唯一标识
            x: 200,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn-active"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间2', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node3', // String，节点的唯一标识
            x: 320,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn-active"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间3', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node4', // String，节点的唯一标识
            x: 440,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn-active"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间4', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node5', // String，节点的唯一标识
            x: 440,      // Number，必选，节点位置的 x 值
            y: 120,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn-active"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间5', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node6', // String，节点的唯一标识
            x: 560,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn-active"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间6', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node7', // String，节点的唯一标识
            x: 680,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn-active"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间7', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node8', // String，节点的唯一标识
            x: 800,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间8', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node9', // String，节点的唯一标识
            x: 920,      // Number，必选，节点位置的 x 值
            y: 240,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `<div class="my-btn"></div>`
              return wrap
            },
            attrs: {
              // 指定 text 元素的样式
              label: {
                text: '开立时间9', // 文字
                fill: '#3c3e41', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
        ],
        // 边
        edges: [
          {
            source: 'node1',
            target: 'node2',
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node2',
            target: 'node3',
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node3',
            target: 'node4',
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node3',
            target: 'node5',
            vertices: [{ x: 327, y: 127 }],
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node5',
            target: 'node6',
            vertices: [{ x: 567, y: 127 }],
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node4',
            target: 'node6',
            connector: { name: 'smooth' },
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node6',
            target: 'node7',
            attrs: {
              line: {
                stroke: '#1890ff',
                strokeDasharray: 5,
                targetMarker: 'classic',
                style: {
                  animation: 'ant-line 30s infinite linear',
                },
              },
            },
          },
          {
            source: 'node7',
            target: 'node8',
            attrs: {
              line: {
                stroke: '#9fa1a4',
                strokeDasharray: 5,
                targetMarker: 'classic',
              },
            },
          },
          {
            source: 'node8',
            target: 'node9',
            attrs: {
              line: {
                stroke: '#9fa1a4',
                strokeDasharray: 5,
                targetMarker: 'classic',
              },
            },
          },
        ],
      },
      graph: null

    }
  },
  mounted() {
    // this.init()
  },
  methods: {
    initGraph() {
      // 确保 id 为 container 的 div 存在
      if (document.getElementById('container') === null) {
        const judgeGraphDiv = setInterval(() => {
          if (document.getElementById('container') !== null) {
            window.clearInterval(judgeGraphDiv);
            this.init();
          }
        }, 1000)
      } else {
        this.init();
      }
    },
    init() {
      if (this.processInfo.length == 0) {
        return
      }
      this.graph = new Graph({
        container: document.getElementById('container'),
        history: true,
        height: 360,
        // async: true,
        scroller: true,
        autoResize: true,
        background: {
          color: '#f2f5f8', // 设置画布背景颜色
        },
        panning: {
          enabled: false,
        },
      });

      insertCss(`
                .x6-node foreignObject{
                  background-color: #2c9ef7 !important
                }
                #container {
                  width:auto;
                  height: 360px;
                }
                .my-btn-active {
                    width: 14px;
                    height: 14px;
                    background: #2c9ef7;
                    border-radius: 50%;
                    box-shadow: 0 0 8px #2c9ef7;
                    -moz-box-shadow: 0 0 8px #2c9ef7;
                    -webkit-box-shadow: 0 0 8px #2c9ef7;
                }
                 .my-btn {
                    width: 14px;
                    height: 14px;
                    background: #9fa1a4;
                    border-radius: 50%;
                    box-shadow: 0 0 8px #9fa1a4;
                    -moz-box-shadow: 0 0 8px #9fa1a4;
                    -webkit-box-shadow: 0 0 8px #9fa1a4;
                }
                 @keyframes ant-line {
                    to {
                        stroke-dashoffset: -1000
                    }
                  }
              `)

      this.graph.fromJSON(this.dataInfo)
      this.graph.centerContent()
    },
  }
}
</script>

<style lang="scss" scoped>
.readmore {
  float: right;
}
</style>
