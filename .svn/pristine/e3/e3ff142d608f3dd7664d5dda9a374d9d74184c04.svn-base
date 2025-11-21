<!-- 拖拽 -->
<template>
  <div class="sidebar-container-child">
    <div class="sidebar-container-search">
      <el-row type="flex">
        <el-col :span="20.5" style="margin-right:5px">
          <el-input :placeholder="placeholder" v-model.trim="keyword" clearable>
            <i class="el-icon-search el-input__icon" slot="suffix" @click="handleIconClick"></i>
          </el-input>
        </el-col>
        <el-col :span="3.5">
          <el-button type="primary" plain icon="el-icon-plus" v-hasPermi="[hasPermiArr[0]]"
            style="width:36px;display: flex;justify-content: center;padding: 10px 10px;" @click="handelAdd"
            title="新增"></el-button>
        </el-col>
      </el-row>
    </div>

    <el-scrollbar style="height: 77vh;min-height: 600px;width: 100%;" v-show="typeList.length > 0">
      <el-menu :default-active="defaultTypeIndex" @select="handleSelect">
        <el-menu-item :index="index + ''" v-for="(item, index) in typeList" :key="index">
          <template slot="title">
            <el-tooltip :content="item.name" placement="right" v-if="item.name.length > 11">
              <div class="menu-info">
                <div class="enumText">{{ item.name }}</div>
                <div class="enumButton" style="display:none">
                  <el-button type="text" size="mini" @click.stop="handelEdit(item)">
                    <svg-icon icon-class="edit" v-hasPermi="[hasPermiArr[1]]"></svg-icon>
                  </el-button>
                  <el-button type="text" class="danger" size="mini" @click.stop="handelDelete(item)">
                    <svg-icon icon-class="delete" v-hasPermi="[hasPermiArr[2]]"></svg-icon>
                  </el-button>
                </div>
              </div>
            </el-tooltip>
            <div class="menu-info" v-else>
              <div class="enumText" >{{ item.name }}</div>
              <div class="enumButton" style="display:none">
                <el-button type="text" size="mini" @click.stop="handelEdit(item)" v-hasPermi="[hasPermiArr[1]]">
                  <svg-icon icon-class="edit"></svg-icon>
                </el-button>
                <el-button type="text" class="danger" size="mini" @click.stop="handelDelete(item)" v-hasPermi="[hasPermiArr[2]]">
                  <svg-icon icon-class="delete"></svg-icon>
                </el-button>
              </div>
            </div>
          </template>
        </el-menu-item>

      </el-menu>
    </el-scrollbar>
    <el-scrollbar style="height: 80vh;min-height: 600px;width: 100%;" v-show="!typeList || typeList.length == 0">
      <div style="width:100%;text-align: center;">
        <p class="table-empty" style="margin-top: 80px">
          <img src="@/assets/images/empty.png" class="table-empty-image" style="width: 140px; height: 140px" />
        </p>
        <p class="table-empty-text"><span>暂无数据</span></p>
      </div>
    </el-scrollbar>


  </div>
</template>
<script>

import { checkPermi } from "@/utils/permission";
export default {

  data() {
    return {
      defaultActive: '0',
      keyword: '',
    }
  },
  props: {
    // 树数据
    typeList: {
      type: Array,
      required: true
    },
    hasPermiArr: {
      type: Array,
    },
    // 默认index
    defaultTypeIndex: {
      type: String,
      default: '0'
    },
    // 标题
    typeTitle: {
      type: String,
      default: '新增'
    },
    // 搜索框提示语
    placeholder: {
      type: String,
      default: '请输入类别名称'
    },
  },
  watch: {
    keyword(val) {
      this.handleIconClick()
      // this.$refs.dataTree.filter(val);
    },
  },
  mounted() {
  },
  methods: {
    checkPermi,

    //切换分类
    handleSelect(index, indexPath) {
      let typeId = this.typeList[index].id
      this.$emit('changeType', typeId, index)
    },

    //点击搜索
    handleIconClick() {
      this.$emit('getTypeList', this.keyword)
    },

    /** 新增分类 */
    handelAdd() {
      this.$emit('handleCreateType', {}, 1)
    },

    /** 编辑分类 */
    handelEdit(data) {
      this.$emit('handleCreateType', data, 2)
    },

    /** 删除分类 */
    handelDelete(data) {
      this.$emit('handleTypeDelete', data)
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

}

.marginRight {
  margin-right: 16px;
}

// ::v-deep .el-input--medium .el-input__inner {
//   height: 32px !important;
//   line-height: 32px !important;
// }

::v-deep .el-menu-item.is-active {
  background: #eaf5fe;
  // border-left:5px solid #2c9ef7;
}

::v-deep .el-scrollbar__wrap {
  overflow-x: hidden;
}

.menu-info {
  display: flex;
  justify-content: space-between;
  align-items: center;


}

.enumText {
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 14px;
  max-width: 84%;
  margin-left: 2px;
}

.menu-info:hover .enumButton {
  display: inline-flex !important;
}

// .input-icon {
//    font-size: 18px;
// }

::v-deep .el-menu-item {
  height: 45px;
  line-height: 45px;
}

// ::v-deep .menu-info .el-button--text {
//   color: #909090;
// }


.danger {
  color: #ff4949;
}

::v-deep .el-table td {
  border-bottom: 0 solid #dfe6ec;
}

.el-input__icon {
  color: #a3a4b2;
  vertical-align: middle;
}

::v-deep .el-menu-item.is-active .el-input__icon {
  color: #2c9ef7;
}
</style>
