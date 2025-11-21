<template>
  <div class="parentContent" ref="parentContent" style="display: flex">
    <div id="container" ref="container" style="flex: 1"></div>

  </div>


</template>

<script>
  import {mapGetters} from 'vuex'
  import {Graph, Node, Path, Cell} from '@antv/x6';
  import G6 from '@antv/g6';
  import insertCss from 'insert-css'
  // import { getNodeExcuteInfo } from '@/api/closedLoopInspection/inspection'

  export default {
    props: {
      processInfo: {
        type: Object,
        default: function () {
          return []
        }
      },
      isBrowse: {
        type: Boolean,
        default: true
      },
      orderNo: {
        type: String,
        default: ''
      },
    },
    watch: {
      'processInfo': {
        deep: true,
        immediate: true,
        handler(val, oldVal) {
          // console.log(val, '        console.log(\'节点顺序与当前x值（去除相同）\')\n');
          let obj = JSON.parse(JSON.stringify(this.processInfo));
          let index = 0;
          let changeFn = (a, parentId = null) =>{
            a.forEach((item)=>{
              //ES6:解构运算符...为剩余运算符，是扩展运算符的一种用法。
              let { children, ...i} = item;
              item.id = 'level_'+index
              index++;
              if(children && children.length){
                // 递归。          传入父id
                changeFn(children, item.id);
              }
            })
          }
          changeFn([obj])

          if (this.graph) {
            this.graph.dispose()
          }
          this.initGraph()
        },
      },
      'type': {
        deep: true,
        immediate: true,
        handler(val, oldVal) {
          this.graph = null
        },
      },
      screenWidth(val) {
        this.graph.dispose()
        this.init()
      }
    },
    computed: {
      processData: {
        get() {
          let nodes = []
          let edges = []
          let sortArr = [] //节点顺序与当前x值（去除相同）
          let allSortInfo = []//节点顺序与对应id
          let sameTimes = 0

        },
      },

    },
    data() {
      return {
        graph: null,
        getExcuteInfo: [],
        nodeName: '节点名称',
        dialogNodeDetailVisible: false,
        screenWidth: 0,
        canvasWidth: 0, // 画布宽度
        canvasHeight: 0 // 画布高度
      }
    },
    mounted() {
      // this.init()
      // console.log('节点顺序与当前x值（去除相同）')
      this.initSize()
    },
    methods: {

      initSize() {
        const self = this // 因为箭头函数会改变this指向，指向windows。所以先把this保存
        setTimeout(() => {
          // todo 浏览器窗口发生变化时
          window.onresize = function () {
            // todo 获取div parentContent 的宽度和高度
            this.canvasWidth = self.$refs.parentContent.clientWidth
            // todo 修改画布的大小
            // self.graph.changeSize(this.canvasWidth, 360)
            // todo 将图移动到画布中心位置
            self.graph.centerContent()
          }
        }, 20)
      },
      initGraph() {
        // 确保 id 为 container 的 div 存在
        if (document.getElementById('container') === null) {
          const judgeGraphDiv = setInterval(() => {
            if (document.getElementById('container') !== null) {
              window.clearInterval(judgeGraphDiv);
              this.init();
            }
          }, 300)
        } else {
          this.init();
        }
      },
      init() {
        if (this.processInfo.length == 0) {
          return
        }
        G6.registerNode('card-node', {
          draw: function drawShape(cfg, group) {
            const r = 2;
            const color = '#5B8FF9';
            const w = cfg.size[0];
            const h = cfg.size[1];
            const shape = group.addShape('rect', {
              attrs: {
                x: -w / 2,
                y: -h / 2,
                width: w, //200,
                height: h, // 60
                // stroke: color,
                radius: r,
                fill: '#fff',
              },
              // must be assigned in G6 3.3 and later versions. it can be any string you want, but should be unique in a custom item type
              name: 'main-box',
              draggable: true,
            });
            group.addShape('text', {
              attrs: {
                textBaseline: 'top',
                textAlign: 'center',
                x: 0,
                y: -h / 2 + 2,
                lineHeight: 20,
                text: cfg.nodeName,
                fill: '#000',
              },
              // must be assigned in G6 3.3 and later versions. it can be any string you want, but should be unique in a custom item type
              name: 'title',
            });
            // cfg.children &&
            group.addShape('circle', {
              attrs: {
                x: 0,
                y: 0,
                r: 6,
                cursor: 'pointer',
                // symbol: cfg.collapsed ? G6.Marker.expand : G6.Marker.collapse,
                stroke: '#666',
                lineWidth: 1,
                fill:cfg.excuStatus ==0?'#2c9ef7':'#fff',
              },
              // must be assigned in G6 3.3 and later versions. it can be any string you want, but should be unique in a custom item type
              name: 'collapse-icon',
            });
            group.addShape('text', {
              attrs: {
                textBaseline: 'top',
                textAlign: 'center',
                x: 0,
                y: -h / 2 + 30,
                lineHeight: 20,
                text: cfg.operatorName || '',
                fill: 'rgba(0,0,0, 1)',
              },
              // must be assigned in G6 3.3 and later versions. it can be any string you want, but should be unique in a custom item type
              name: `description`,
            });
            group.addShape('text', {
              attrs: {
                textBaseline: 'top',
                textAlign: 'center',
                x: 0,
                y: -h / 2 + 50,
                lineHeight: 20,
                text: cfg.operateTime || '',
                fill: 'rgba(0,0,0, 1)',
              },
              // must be assigned in G6 3.3 and later versions. it can be any string you want, but should be unique in a custom item type
              name: `description`,
            });
            return shape;
          },
        });

        let data = {};
        // console.log([data].flat(Infinity),'.flat(Infinity)')
        let index = 0;
        let changeFn = (a, parentId = null) =>{
          a.forEach((item)=>{
            //ES6:解构运算符...为剩余运算符，是扩展运算符的一种用法。
            let { children, ...i} = item;
            item.id = 'level_'+index;
            index++;
            if(children && children.length){
              // 递归。          传入父id
              changeFn(children, item.id);
            }
          })
          return a;
        }
        data = changeFn([this.processInfo])[0]
        let obj = JSON.parse(JSON.stringify(data))
        let arr = this.objFn(obj)
        const container = document.getElementById('container');
        const width = container.scrollWidth;
        const height = container.scrollHeight || 500;

        const graph = new G6.TreeGraph({
          container: 'container',
          width,
          height,
          // fitCenter:true,
          modes: {
            default: ['drag-canvas', 'zoom-canvas'],
          },
          defaultNode: {
            type: 'card-node',
            size: [100, 40],
          },
          defaultEdge: {
            type: 'cubic-horizontal',
            style: {
              endArrow: true,
            },
          },
          layout: {
            type: 'indented',
            direction: 'LR',
            dropCap: false,
            indent: 200,
            getHeight: () => {
              return 60;
            },
          },
        });
        graph.edge((edge) => {
          console.log(arr.filter(x=>x.id == edge.source)[0].nodeName == '封箱');
          return {
            id: edge.id,
            type: 'cubic-horizontal',
            style: {
              startArrow:arr.filter(x=>x.id == edge.source)[0].nodeName == '封箱' && arr.filter(x=>x.id == edge.source)[0].excuStatus== 0,
              stroke: arr.filter(x=>x.id == edge.source)[0].excuStatus== 0?'#2c9ef7':'#ccc',
            },
          };
        });
        graph.data(data);
        // graph.fromJSON(this.processData);
        graph.render();
        graph.fitView();
        // graph.on('node:click', (e) => {
        //   if (e.target.get('name') === 'collapse-icon') {
        //     e.item.getModel().collapsed = !e.item.getModel().collapsed;
        //     graph.setItemState(e.item, 'collapsed', e.item.getModel().collapsed);
        //     graph.layout();
        //   }
        // });
        if (typeof window !== 'undefined')
          window.onresize = () => {
            if (!graph || graph.get('destroyed')) return;
            if (!container || !container.scrollWidth || !container.scrollHeight) return;
            graph.changeSize(container.scrollWidth, container.scrollHeight);
          };
      },
      closed() {
        this.dialogNodeDetailVisible = false
      },
      handleCancel() {
        this.dialogNodeDetailVisible = false
      },
      objFn : (params) =>{
        let arr = [];
        arr.push(params);
        let result = [];
        let changeFn = (a, parentId = null) =>{
          a.forEach((item)=>{
            //ES6:解构运算符...为剩余运算符，是扩展运算符的一种用法。
            let { children, ...i} = item;
            result.push({
              ...i,
              parentId,
            });
            if(children && children.length){
              // 递归。          传入父id
              changeFn(children, item.id);
            }
          })
        }
        changeFn(arr);
        return result;
      },
    }
  }
</script>

<style lang="scss" scoped>
  .readmore {
    float: right;
  }

  ::v-deep .el-dialog__body {
    padding-top: 0px !important;
  }

  // 1.css
  .parentContent {
    width: 100%;
    height: 100%;
  }
</style>
