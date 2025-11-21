<template>
  <div id="container" class="container">
    <ul class="el-step">
      <li class="active">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
          <div class="el-step__info">
            <p>张三</p>
            <p>检查科室</p>
            <p>05-06 16:13:00</p>
          </div>
        </div>
      </li>
      <li class="parallel" style="left:60px">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
        </div>
      </li>
      <li class="active">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
          <div class="el-step__info">
            <p>张三</p>
            <p>检查科室</p>
            <p>05-06 16:13:00</p>
          </div>
        </div>
      </li>
      <li class="parallel" style="left:200px">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
        </div>
      </li>

      <li class="active">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
          <div class="el-step__info">
            <p>张三</p>
            <p>检查科室</p>
            <p>05-06 16:13:00</p>
          </div>
        </div>
      </li>
       <li class="parallel" style="left:340px">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
        </div>
      </li>
      <li class="active">
        <div class="circle">
          <div class="el-step__title">开立时间</div>
          <div class="el-step__info">
            <p>张三</p>
            <p>检查科室</p>
            <p>05-06 16:13:00</p>
          </div>
        </div>
      </li>
      <li class="active">
        <div class="circle"></div>
      </li>
      <li>
        <div class="circle"></div>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'



export default {
  data() {
    return {
      dataInfo: {
        // 节点
        nodes: [
          {
            id: 'node1', // String，可选，节点的唯一标识
            x: 40,       // Number，必选，节点位置的 x 值
            y: 160,       // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'circle',
            attrs: {
              // 指定 rect 元素的样式
              body: {
                stroke: '#2c9ef7', // 边框颜色
                fill: '#2c9ef7',
              },
              // 指定 text 元素的样式
              label: {
                text: '开立时间1', // 文字
                fill: '#333', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node2', // String，节点的唯一标识
            x: 140,      // Number，必选，节点位置的 x 值
            y: 160,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'html',
            html() {
              const wrap = document.createElement('div')
              wrap.innerHTML = `
              <a href="#" class="my-btn"></a>`
              return wrap
            },
            attrs: {
              // 指定 rect 元素的样式
              body: {
                stroke: '#2c9ef7', // 边框颜色
                fill: '#2c9ef7',
              },
              // 指定 text 元素的样式
              label: {
                text: '开立时间', // 文字
                fill: '#333', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },

          },
          {
            id: 'node3', // String，节点的唯一标识
            x: 240,      // Number，必选，节点位置的 x 值
            y: 160,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'circle',
            attrs: {
              // 指定 rect 元素的样式
              body: {
                stroke: '#2c9ef7', // 边框颜色
                fill: '#2c9ef7',
              },
              // 指定 text 元素的样式
              label: {
                text: '开立时间', // 文字
                fill: '#333', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node4', // String，节点的唯一标识
            x: 340,      // Number，必选，节点位置的 x 值
            y: 160,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'circle',
            attrs: {
              // 指定 rect 元素的样式
              body: {
                stroke: '#2c9ef7', // 边框颜色
                fill: '#2c9ef7',
              },
              // 指定 text 元素的样式
              label: {
                text: '开立时间', // 文字
                fill: '#333', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node5', // String，节点的唯一标识
            x: 240,      // Number，必选，节点位置的 x 值
            y: 80,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'circle',
            attrs: {
              // 指定 rect 元素的样式
              body: {
                stroke: '#2c9ef7', // 边框颜色
                fill: '#2c9ef7',
              },
              // 指定 text 元素的样式
              label: {
                text: '开立时间', // 文字
                fill: '#333', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
          {
            id: 'node6', // String，节点的唯一标识
            x: 440,      // Number，必选，节点位置的 x 值
            y: 160,      // Number，必选，节点位置的 y 值
            width: 16,   // Number，可选，节点大小的 width 值
            height: 16,  // Number，可选，节点大小的 height 值
            shape: 'circle',
            attrs: {
              // 指定 rect 元素的样式
              body: {
                stroke: '#2c9ef7', // 边框颜色
                fill: '#2c9ef7',
              },
              // 指定 text 元素的样式
              label: {
                text: '开立时间', // 文字
                fill: '#333', // 文字颜色
                fontSize: 14,
                refY: -16, // x 轴偏移，类似 css 中的 margin-left
              },
            },
          },
        ],
        // 边
        edges: [
          {
            source: 'node1', // String，必须，起始节点 id
            target: 'node2', // String，必须，目标节点 id
          },
          {
            source: 'node2', // String，必须，起始节点 id
            target: 'node3', // String，必须，目标节点 id
          },
          {
            source: 'node3', // String，必须，起始节点 id
            target: 'node4', // String，必须，目标节点 id
          },
          {
            source: 'node3', // String，必须，起始节点 id
            target: 'node5', // String，必须，目标节点 id
          },
        ],
      },
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.init()
    })
  },
  methods: {
    init() { },
  }
}
</script>

<style lang="scss" scoped>
.readmore {
  float: right;
}

ul {
  list-style: none;
  display: -webkit-box;
}

ul li {
  display: flex;
  justify-content: center;
  position: relative;
  align-items: center;
  width: 140px;
}


ul li:nth-child(n+2):after {
  content: '';
  height: 2px;
  width: 100%;
  position: absolute;
  border-bottom: #bcbcbc 2px dashed;
  left: -50%;
}

ul li div {
  color: black;
}

ul li div:before {
  color: inherit;
  position: absolute;
  bottom: -2rem;
  left: 50%;
  transform: translateX(-50%);
}

ul li.active:nth-child(n+2)::after {
  border-bottom: #2c9ef7 2px dashed;
}

ul li.active::before {
  background: #2c9ef7;
}

.container {
  width: auto;
  height: 300px;

  .el-step {
    position: relative;
    top: 160px;
  }
}

ul li .circle {
  width: 14px;
  height: 14px;
  background: #bcbcbc;
  border-radius: 50%;
  box-shadow: 0 0 8px #bcbcbc;
  -moz-box-shadow: 0 0 8px #bcbcbc;
  -webkit-box-shadow: 0 0 8px #bcbcbc;
  z-index: 1;
}
ul li.parallel{
  transform: rotate(270deg);
  position: absolute;
  left: 200px;
  bottom: 100px;
  width: 100px;
  .circle{
     transform: rotate(-270deg);
  }

}


ul li.active .circle {
  width: 14px;
  height: 14px;
  background: #2c9ef7;
  border-radius: 50%;
  box-shadow: 0 0 8px #2c9ef7;
  -moz-box-shadow: 0 0 8px #2c9ef7;
  -webkit-box-shadow: 0 0 8px #2c9ef7;
  z-index: 1;
}

.el-step__title {
  position: relative;
  top: -40px;
  left: -17px;
  width: 60px;
  text-align: center;
}

.el-step__info {
  position: relative;
  top: -10px;
  left: -53px;
  width: 120px;
  text-align: center;

  p {
    margin: 10px;
  }
}
</style>
