<!-- 拖拽 -->
<template>
  <div class="sidebar-container-child">
    <div class="sidebar-container-search">
      <el-row type="flex">
        <el-col :span="20.5">
          <el-input placeholder="请输入转换类别名称" v-model.trim="keyword">
            <i class="el-icon-search el-input__icon" slot="suffix" @click="handleIconClick"></i>
          </el-input>
        </el-col>
        <el-col :span="3.5">
          <div class="sidebar-container-operate" @click="handleCreateGroup" title="新增" v-has-permi="['mdata:group:add']">
            <svg-icon icon-class="plus" />
          </div>
        </el-col>
      </el-row>
    </div>

    <el-scrollbar style="height: 80vh;min-height: 600px;">
      <div class="sidebar-container-tree">
        <el-tree :data="dataTree" ref="dataTree" node-key="uniqueFlag" :highlight-current="true" @node-drop="handleDrop"
          draggable :allow-drag="allowDrag" :allow-drop="allowDrop" :default-expanded-keys="defaultExpandKeys"
          :filter-node-method="filterNode" :default-expand-all="defaultExpandAll" v-if="refreshTable"
          @node-expand="nodeExpand" @node-collapse="nodeCollapse">
          <div :class="{ 'custom-tree-node': true }" slot-scope="{node, data}" class="show-hide"
            @click.stop="tabPage(data, node)">
            <el-tooltip :content="data.label" placement="right" v-if="data.len > 12">
              <div style="width:100%;display: flex;justify-content: space-between;">
                <div class="enumIconText">
                  <div class="enumIconImage">
                    <el-image style="height: 20px;width: 20px;"
                      :src="require(node.isCurrent ? '@/assets/images/actived.png' : '@/assets/images/notActived.png')"
                      v-if="data.nodeType == 2"></el-image>
                    <el-image style="height: 20px;width: 20px;"
                      :src="require(node.expanded ? '@/assets/images/expanded.png' : '@/assets/images/notExpand.png')"
                      v-if="data.nodeType == 1"></el-image>
                  </div>
                  <span class="enumText">
                    {{ data.label }}
                  </span>
                </div>
                <span class="enumButton" v-if="!data.showOmit" :ref="'treeMenu' + data.id" style="display:none">
                  <el-button type="text" size="mini" @click.stop="appendTreeNode(data, node)"
                    v-if="data.nodeType == 1 && data.parentId != null">
                    <svg-icon icon-class="plus"></svg-icon>
                  </el-button>
                  <el-button type="text" size="mini" @click.stop="editCateName(data, node)" v-if="data.parentId != null">
                    <svg-icon icon-class="edit-2"></svg-icon>
                  </el-button>
                  <el-button type="text" class="danger" size="mini" @click.stop="removeTreeNode(data, node)"
                    v-has-permi="['mdata:table:delete']" v-if="data.parentId != null">
                    <svg-icon icon-class="delete"></svg-icon>
                  </el-button>
                </span>
              </div>
            </el-tooltip>
            <div style="width:100%;display: flex;justify-content: space-between;" v-else>
              <div class="enumIconText">
                <div class="enumIconImage">
                  <el-image style="height: 20px;width: 20px;"
                    :src="require(node.isCurrent ? '@/assets/images/actived.png' : '@/assets/images/notActived.png')"
                    v-if="data.nodeType == 2"></el-image>
                  <el-image style="height: 20px;width: 20px;"
                    :src="require(node.expanded ? '@/assets/images/expanded.png' : '@/assets/images/notExpand.png')"
                    v-if="data.nodeType == 1"></el-image>
                </div>
                <span class="enumText">
                  {{ data.label }}
                </span>
              </div>
              <span class="enumButton" v-if="!data.showOmit" :ref="'treeMenu' + data.id" style="display:none">
                <el-button type="text" size="mini" @click.stop="appendTreeNode(data, node)"
                  v-if="data.nodeType == 1 && data.parentId != null" title="新增" v-has-permi="['mdata:table:add']">
                  <svg-icon icon-class="plus"></svg-icon>
                </el-button>
                <el-button type="text" size="mini" @click.stop="editCateName(data, node)"
                  v-if="data.nodeType == 2 && data.parentId != null" title="编辑" v-has-permi="['mdata:table:update']">
                  <svg-icon icon-class="edit-2"></svg-icon>
                </el-button>

                <!--                nodeType != 2   分组修改-->
                <el-button type="text" size="mini" @click.stop="editCateName(data, node)"
                  v-if="data.nodeType != 2 && data.parentId != null" title="编辑" v-has-permi="['mdata:group:update']">
                  <svg-icon icon-class="edit-2"></svg-icon>
                </el-button>
                <el-button type="text" class="danger" size="mini" @click.stop="removeTreeNode(data, node)"
                  v-has-permi="['mdata:table:delete']" v-if="data.nodeType == 2 && data.parentId != null" title="删除">
                  <svg-icon icon-class="delete"></svg-icon>
                </el-button>
                <!--                nodeType != 2   分组删除-->
                <el-button type="text" class="danger" size="mini" @click.stop="removeTreeNode(data, node)"
                  v-has-permi="['mdata:group:delete']" v-if="data.nodeType != 2 && data.parentId != null" title="删除">
                  <svg-icon icon-class="delete"></svg-icon>
                </el-button>
              </span>
            </div>
          <!-- <span class="enumButton" v-if="data.showOmit" style="display:none">
            <el-button type="text" size="mini" @click.stop="appendTreeNode(data, node)">
              <svg-icon icon-class="omit"></svg-icon>
            </el-button>
            </span> -->
          </div>
        </el-tree>

      </div>

    </el-scrollbar>
  </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
// import { nodeDrag, tableDrag } from '@/api/mainDataConfiguration'
export default {
  props: {
    // 树数据
    dataTree: {
      type: Array,
      required: true
    },
    defaultExpandAll: {
      type: Boolean,
      default: false
    },
    defaultExpandKeys: {
      type: Array,
      default: []
    },
    // 设置指定的label,value,children
    defaultProps: {
      type: Object,
      default: () => {
        return {
          label: 'label',
          id: 'id',
          children: 'children'
        }
      }
    },
  },
  watch: {
    keyword(val) {
      this.handleIconClick()
      // this.$refs.dataTree.filter(val);
    },
    dataTree: {
      handler(val, oldVal) {
        this.dbupdateWatch = false; // 初始化
        if (val === oldVal) {
          this.dbupdateWatch = true;
        }
      },
      deep: true
    },
    defaultExpandAll: {
      handler(val, oldVal) {
        this.refreshTable = false; // 初始化
        this.$nextTick(() => {
          if (val !== oldVal) {
            this.refreshTable = true;
          }
        })

      },
      deep: true,
      immediate: true
    },
  },
  data() {
    return {
      keyword: '',
      dbupdateWatch: true,
      isShow: false,
      refreshTable: true,

    };
  },
  mounted() { },
  methods: {
    init(data) {
      data.forEach(value => {
        value.showOmit = this.dealOmitStatus(value.id)
        if (value.children && value.children.length > 0) {
          this.init(value.children)
        }
      })
      this.data = data
    },
    dealOmitStatus(id) {
      let treeMenuBox = this.$refs['treeMenu' + id];
      if (treeMenuBox.scrollWidth > treeMenuBox.clientWidth) {
        return true
      } else {
        return false
      }
    },

    //点击搜索
    handleIconClick() {
      this.$emit('parentGetTree', this.keyword)
    },

    //新增分组
    handleCreateGroup() {
      this.$emit('parentCreateGroup', 1)
    },


    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //拖动允许
    allowDrag(node) {
      // return node.data.nodeType == 1
      return true
    },
    //拖动位置
    allowDrop(draggingNode, dropNode, dropType,) {

      if (draggingNode.data.nodeType == 1) {//拖动节点时
        if (dropNode.data.nodeType !== 1 && dropType == 'inner') {
          return false
        } else {
          return true
        }
      } else {//拖动字典表时
        if (dropNode.data.nodeType == 1 && dropType == 'inner') {
          return true
        }
        return false
      }
    },
    //拖拽
    handleDrop(draggingNode, dropNode, dropType, ev) {
      // //drapType:  0-拖拽到参考节点的下面,1-拖拽到参考节点的前面，2-拖拽到参考节点的后面
      const typeObj = { 'inner': 0, 'before': 1, 'after': 2 }

    },
    //展开节点触发
    nodeExpand(data, node) {
      this.defaultExpandKeys.push(node.data.uniqueFlag)
    },
    //关闭节点触发
    nodeCollapse(data, node) {
      let index = this.defaultExpandKeys.indexOf(node.data.uniqueFlag);
      if (index != -1) {
        this.defaultExpandKeys.splice(index, 1)
      }
    },
    //切换字典表页面
    tabPage(data, node) {
      this.$nextTick(() => {
        this.$refs['dataTree'].setCurrentKey(data.uniqueFlag)
      })
      this.$emit('parentTabPage', data)
    },
    //编辑
    editCateName(data, node) {
      if (data.nodeType == 2) {
        this.$emit('parentCreateWordBook', data, 2)
      } else {
        this.$emit('parentCreateGroup', data, 2)
      }
    },
    //新增
    appendTreeNode(data, node) {
      let index = this.defaultExpandKeys.indexOf(node.data.uniqueFlag);
      if (index == -1) {
        this.defaultExpandKeys.push(node.data.uniqueFlag)
      }
      this.$emit('parentCreateWordBook', data, 1)
    },
    //删除
    removeTreeNode(data, node) {
      this.$emit('parentDelete', data)
    },
  }
}

</script>


<style lang="scss" scoped>
#app .sidebar-container-child {
  -webkit-transition: width 0.28s;
  transition: width 0.28s;
  background-color: #ffffff;
  height: 100%;
  width: auto;

  .sidebar-container-search {
    padding: 15px 10px;

    .el-input__icon {
      cursor: pointer;
    }

    .sidebar-container-operate {
      width: 30px;
      height: 30px;
      border: 1px solid #2c9ef7;
      color: #2c9ef7;
      border-radius: 5px;
      margin-left: 4px;
      text-align: center;
      line-height: 30px;
      font-size: 18px;
      margin-top: 1px;
      cursor: pointer;
    }
  }

  .sidebar-container-tree {
    width: 100%;
    overflow: hidden;

    .custom-tree-node {

      display: flex;
      justify-content: space-between;
      width: 100%;

      .enumIconImage {
        display: flex;
        justify-content: center;
        align-items: center;
      }

      .enumIconText {
        display: flex;
        align-items: center;
        justify-content: center;
        overflow: hidden;
        text-overflow: ellipsis;
        font-size: 14px;
        max-width: 80%;

        .enumText {
          overflow: hidden;
          text-overflow: ellipsis;
          font-size: 14px;
          max-width: 100%;
          margin-left: 2px;
        }
      }



      .enumButton {
        margin-right: 10px;
        width: 50px;
        text-align: center;
        font-weight: bold;
        display: flex;
        justify-content: flex-end;

        button {
          margin-left: 4px !important;
        }

        .danger {
          color: #ff4949;
        }
      }

    }

  }

  .show-hide:hover .enumButton {
    display: inline-flex !important;
  }


  ::v-deep {

    .el-tree {
      color: #606266;

      .el-tree-node .el-tree-node__content {
        height: 36px;
        line-height: 36px;

      }

      .custom-tree-node {
        overflow: hidden !important;
        text-overflow: ellipsis !important;
      }


      .el-tree-node__expand-icon {
        color: #565961;
      }

      .el-tree-node__expand-icon.is-leaf {
        color: transparent !important;
        cursor: default !important;
      }

    }

    .el-input--medium .el-input__inner {
      height: 32px !important;
      line-height: 32px !important;
    }

    .el-scrollbar__wrap {
      overflow-x: hidden;
    }
  }

}
</style>
