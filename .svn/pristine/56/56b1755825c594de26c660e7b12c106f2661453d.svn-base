<template>
  <div class="root">
    <el-select class="main-select-tree" ref="selectTree" filterable :filter-method="dataFilter"
               v-model="value.parentId" :placeholder="placeholder" style="width:100%">
      <el-option v-for="item in formatData(dataTree)" :key="item.value" :label="item.groupName" :value="item.value"
                 style="display: none;" />
      <el-tree class="main-select-el-tree" ref="selecteltree" :data="dataTree" node-key="uniqueFlag" highlight-current
               :props="defaultProps" @node-click="handleNodeClick" :current-node-key="value.parentId"
               :default-expand-all="expandAll" :filter-node-method="filterNode" >
        <div :class="{ 'custom-tree-node': true }" slot-scope="{node, data}" class="show-hide">
          <div class="enumIconText">
            <el-image style="height: 20px;width: 20px;" fit="cover"
                      :src="require(node.isCurrent ? '@/assets/images/actived.png' : '@/assets/images/notActived.png')"
                      v-if="data.nodeType == 2"></el-image>
            <el-image style="height: 20px;width: 20px;" fit="cover"
                      :src="require(node.expanded ? '@/assets/images/expanded.png' : '@/assets/images/notExpand.png')"
                      v-if="data.nodeType == 1"></el-image>
            <span class="enumText">
                {{ data.label }}
              </span>
          </div>
        </div>
      </el-tree>
    </el-select>
  </div>
</template>
<script>
  export default {
    name: 'selectTree',
    watch: {
      value: {
        handler(val, oldVal) {
          this.$nextTick(() => {
            this.$refs['selecteltree'].setCurrentKey(val.parentId)
          })
        },
        deep: true
      }
    },
    props: {
      // 选中节点的值名称
      value: {
        type: Object,
      },
      // 树数据
      dataTree: {
        type: Array,
        required: true
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
      // 是否展开所有节点
      expandAll: {
        type: Boolean,
        default: true
      },
      placeholder: {
        type: String,
        default: '请选择'
      }
    },
    methods: {

      //处理树形数据
      formatData(data, options = []) {
        data.forEach((item, key) => {
          options.push({ groupName: item.label, value: item.id });
          if (item.children && item.children.length > 0) {
            this.formatData(item.children, options)
          }
        });
        return options;
      },

      //选择节点
      handleNodeClick(node) {
        this.value.parentId = node.id;
        this.value.parentName = node.label;
        this.$refs.selectTree.blur();
      },

      //自定义下拉搜索
      dataFilter(val) {
        this.$refs.selecteltree.filter(val);
      },

      //树形搜索
      filterNode(value, data) {
        if (!value) return true;
        return data.groupName.indexOf(value) !== -1;
      },

      // 设置选中节点
      async setCheckedNodes(val) {
        // 外层"this.$nextTick"处理第一次回显dom可能未加载导致setCheckedKeys报错
        this.$nextTick(() => {
          this.$refs.tree.setCheckedKeys(selectedArray);
        })
      },
    },
  }
</script>
<style lang="scss" scoped>
  .custom-tree-node {

    display: flex;
    justify-content: space-between;
    width: 100%;

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
</style>
