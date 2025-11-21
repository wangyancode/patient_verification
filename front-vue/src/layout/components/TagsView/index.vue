<template>
  <div id="tags-view-container" class="tags-view-container">

    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container"
      @toggleClick="toggleSideBar" />
    <scroll-pane ref="scrollPane" class="tags-view-wrapper" @scroll="handleScroll">
      <router-link v-for="tag in visitedViews" ref="tag" :key="tag.path" :class="isActive(tag) ? 'active' : ''"
        :to="{ path: tag.path, query: tag.query, fullPath: tag.fullPath }" tag="span" class="tags-view-item"
        :style="activeStyle(tag)" @click.middle.native="!isAffix(tag) ? closeSelectedTag(tag) : ''"
        @contextmenu.prevent.native="openMenu(tag, $event)">
        <svg-icon :icon-class="tag.meta.icon" v-if="(tag.meta && tag.meta.icon && tag.meta.icon != '#')" />
        <svg-icon icon-class="menu_default" v-else />
        {{ tag.title }}
        <span v-if="!isAffix(tag)" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)" />
      </router-link>
    </scroll-pane>
    <ul v-show="visible" :style="{ left: left + 'px', top: top + 'px' }" class="contextmenu">
      <li @click="refreshSelectedTag(selectedTag)"><i class="el-icon-refresh-right"></i> 刷新页面</li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)"><i class="el-icon-close"></i> 关闭当前</li>
      <li @click="closeOthersTags"><i class="el-icon-circle-close"></i> 关闭其他</li>
      <li v-if="!isFirstView()" @click="closeLeftTags"><i class="el-icon-back"></i> 关闭左侧</li>
      <li v-if="!isLastView()" @click="closeRightTags"><i class="el-icon-right"></i> 关闭右侧</li>
      <li @click="closeAllTags(selectedTag)"><i class="el-icon-circle-close"></i> 全部关闭</li>
    </ul>
  </div>
</template>

<script>
import ScrollPane from './ScrollPane'
import path from 'path'
import Hamburger from '@/components/Hamburger'
import { mapGetters } from "vuex";

export default {
  components: { ScrollPane, Hamburger },
  data() {
    return {
      visible: false,
      top: 0,
      left: 0,
      selectedTag: {},
      affixTags: []
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device',
      'selValue'
    ]),
    visitedViews() {
      return this.$store.state.tagsView.visitedViews
    },
    routes() {
      return this.$store.state.permission.routes
    },
    theme() {
      return this.$store.state.settings.theme;
    }
  },
  watch: {
    $route() {
      this.addTags()
      this.moveToCurrentTag()
    },
    visible(value) {
      if (value) {
        document.body.addEventListener('click', this.closeMenu)
      } else {
        document.body.removeEventListener('click', this.closeMenu)
      }
    }
  },
  mounted() {
    this.initTags()
    this.addTags()
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    isActive(route) {
      return route.path === this.$route.path
    },
    activeStyle(tag) {
      if (!this.isActive(tag)) return {};
      return {
        "background-color": this.theme,
        "border-color": this.theme
      };
    },
    isAffix(tag) {
      return tag.meta && tag.meta.affix
    },
    isFirstView() {
      try {
        return this.selectedTag.fullPath === this.visitedViews[1].fullPath || this.selectedTag.fullPath === '/index'
      } catch (err) {
        return false
      }
    },
    isLastView() {
      try {
        return this.selectedTag.fullPath === this.visitedViews[this.visitedViews.length - 1].fullPath
      } catch (err) {
        return false
      }
    },
    filterAffixTags(routes, basePath = '/') {
      let tags = []
      routes.forEach(route => {
        if (route.meta && route.meta.affix) {
          const tagPath = path.resolve(basePath, route.path)
          tags.push({
            fullPath: tagPath,
            path: tagPath,
            name: route.name,
            meta: { ...route.meta }
          })
        }
        if (route.children) {
          const tempTags = this.filterAffixTags(route.children, route.path)
          if (tempTags.length >= 1) {
            tags = [...tags, ...tempTags]
          }
        }
      })
      return tags
    },
    initTags() {
      const affixTags = this.affixTags = this.filterAffixTags(this.routes)
      for (const tag of affixTags) {
        // Must have tag name
        if (tag.name) {
          this.$store.dispatch('tagsView/addVisitedView', tag)
        }
      }
    },
    addTags() {
      const { name } = this.$route
      if (name) {
        this.$store.dispatch('tagsView/addView', this.$route)
        if (this.$route.meta.link) {
          this.$store.dispatch('tagsView/addIframeView', this.$route)
        }
      }
      return false
    },
    moveToCurrentTag() {
      const tags = this.$refs.tag
      this.$nextTick(() => {
        for (const tag of tags) {
          if (tag.to.path === this.$route.path) {
            this.$refs.scrollPane.moveToTarget(tag)
            // when query is different then update
            if (tag.to.fullPath !== this.$route.fullPath) {
              this.$store.dispatch('tagsView/updateVisitedView', this.$route)
            }
            break
          }
        }
      })
    },
    refreshSelectedTag(view) {
      this.$tab.refreshPage(view);
      if (this.$route.meta.link) {
        this.$store.dispatch('tagsView/delIframeView', this.$route)
      }
    },
    closeSelectedTag(view) {
      this.$tab.closePage(view).then(({ visitedViews }) => {
        if (this.isActive(view)) {
          this.toLastView(visitedViews, view)
        }
      })
    },
    closeRightTags() {
      this.$tab.closeRightPage(this.selectedTag).then(visitedViews => {
        if (!visitedViews.find(i => i.fullPath === this.$route.fullPath)) {
          this.toLastView(visitedViews)
        }
      })
    },
    closeLeftTags() {
      this.$tab.closeLeftPage(this.selectedTag).then(visitedViews => {
        if (!visitedViews.find(i => i.fullPath === this.$route.fullPath)) {
          this.toLastView(visitedViews)
        }
      })
    },
    closeOthersTags() {
      this.$router.push(this.selectedTag).catch(() => { });
      this.$tab.closeOtherPage(this.selectedTag).then(() => {
        this.moveToCurrentTag()
      })
    },
    closeAllTags(view) {
      this.$tab.closeAllPage().then(({ visitedViews }) => {
        if (this.affixTags.some(tag => tag.path === this.$route.path)) {
          return
        }
        this.toLastView(visitedViews, view)
      })
    },
    toLastView(visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        this.$router.push(latestView.fullPath)
      } else {
        // now the default is to redirect to the home page if there is no tags-view,
        // you can adjust it according to your needs.
        if (view.name === 'Dashboard') {
          // to reload home page
          this.$router.replace({ path: '/redirect' + view.fullPath })
        } else {
          this.$router.push('/')
        }
      }
    },
    openMenu(tag, e) {
      const menuMinWidth = 105
      const offsetLeft = this.$el.getBoundingClientRect().left // container margin left
      // console.log(this.$el);
      console.log(offsetLeft);
      console.log(this.sidebar.opened);
      const offsetWidth = this.$el.offsetWidth // container width
      const maxLeft = offsetWidth - menuMinWidth // left boundary
      let left = 0
      if (this.sidebar.opened) {
        left = e.clientX - offsetLeft + 250 // 15: margin right
      } else {
        left = e.clientX - offsetLeft + 50 // 15: margin right
      }

      if (left > maxLeft) {
        this.left = maxLeft
      } else {
        this.left = left
      }

      this.top = e.clientY
      this.visible = true
      this.selectedTag = tag
    },
    closeMenu() {
      this.visible = false
    },
    handleScroll() {
      this.closeMenu()
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/variables.scss";


.back-home {
  padding: 0px 15px;
  line-height: 46px;
  height: 100%;
  float: right;
  cursor: pointer;
  transition: 0.3s;
  -webkit-tap-highlight-color: transparent;
  color: #606266;

  &:hover {
    background: rgba(0, 0, 0, 0.025);
  }
}

.hideSidebar .tags-view-container {
  margin-left: 54px;
  width: calc(100% - 54px);
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  transition: margin-left .28s;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);
  transition: .28s;
}



.tags-view-container {
  height: 46px;
  width: calc(100% - #{$sideBarWidth});
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  transition: margin-left .28s;
  margin-left: $sideBarWidth;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);
  transition: .28s;
  display: flex;

  .scroll-container .el-scrollbar__wrap {
    height: 57px !important;
  }

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: .3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .tags-view-wrapper {

    .tags-view-item {
      height: 36px;
      padding: 0px 30px;
      margin-top: 9px;
      margin-right: -14px;
      font-size: 14px;
      line-height: 36px;
      text-align: center;
      border: 0;
      outline: none;
      background: #fff;
      cursor: pointer;
      display: inline-block;
      position: relative;

      &.active {
        padding: 0 30px;
        color: #2c9ef7 !important;
        background: #e8f4ff !important;
        mask: url('~@/assets/images/vab-tab.png');
        mask-size: 100% 100%;
        z-index: 1;

        // &::before {
        //   content: '';
        //   background: #2c9ef7;
        //   display: inline-block;
        //   width: 6px;
        //   height: 6px;
        //   border-radius: 50%;
        //   position: relative;
        //   margin-right: 4px;
        //   margin-bottom: 1px;
        // }

        &:hover {
          padding: 0 30px;
          color: #2c9ef7 !important;
          background: #e8f4ff !important;
          mask: url('~@/assets/images/vab-tab.png');
          mask-size: 100% 100%;
        }

        &:after {
          display: none;
        }
      }

      &:hover {
        padding: 0 30px;
        background: #eee;
        mask: url('~@/assets/images/vab-tab.png');
        mask-size: 100% 100%;
      }

      &:after {
        content: '';
        position: absolute; //子元素用绝对路径
        right: 6px;
        top: 9px;
        width: 1px;
        height: 18px;
        background-color: #b4bccc;
        z-index: 1;
      }

      &:last-of-type:after {
        display: none;
      }


    }
  }

  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);

    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;

      &:hover {
        background: #eee;
      }
    }
  }
}
</style>

<style lang="scss">
//reset element css of el-icon-close
.tags-view-wrapper {
  .tags-view-item {
    .el-icon-close {
      width: 10px;
      height: 10px;
      vertical-align: 2px;
      border-radius: 50%;
      text-align: center;
      transition: all .3s cubic-bezier(.645, .045, .355, 1);
      transform-origin: 100% 50%;


      &:before {
        transform: scale(.6);
        display: inline-block;
        vertical-align: -5px;
        margin-left: 8px;
        font-size: 23px;
      }

      // &:hover {
      //   background-color: #fff;
      //   color: #fff;
      // }
    }
  }
}
</style>
