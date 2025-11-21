<template>
    <div :class="{ 'has-logo': showLogo }"
        :style="{ backgroundColor: settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground }">
        <logo v-if="showLogo" :collapse="isCollapse" />
        <el-scrollbar :class="settings.sideTheme" wrap-class="scrollbar-wrapper">
            <el-menu :default-active="activeMenu" :collapse="isCollapse"
                :background-color="settings.sideTheme === 'theme-dark' ? variables.menuBackground : variables.menuLightBackground"
                :text-color="settings.sideTheme === 'theme-dark' ? variables.menuColor : variables.menuLightColor"
                :unique-opened="false" :active-text-color="settings.theme" :collapse-transition="false" mode="vertical"
                @select="handleSelect">
                <sidebar-item v-for="(route, index) in sidebarRouters" :key="route.path + index" :item="route"
                    :base-path="route.path" />
            </el-menu>
        </el-scrollbar>
        <!-- <div class="footer" v-if="!isCollapse">
            <img :src="logoUrl" alt="" class="footer-img" v-if="logoUrl" />
            <div class="company-name">
                Copyright©2022-{{ year }}
            </div>
        </div> -->
    </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/assets/styles/variables.scss";
import { getAuthConfig } from "@/api/login";

export default {
    components: { SidebarItem, Logo },
    computed: {
        ...mapState(["settings"]),
        ...mapGetters(["sidebarRouters", "sidebar"]),
        activeMenu() {
            const route = this.$route;
            const { meta, path } = route;
            // if set path, the sidebar will highlight the path you set
            if (meta.activeMenu) {
                return meta.activeMenu;
            }
            return path;
        },
        showLogo() {
            return this.$store.state.settings.sidebarLogo;
        },
        variables() {
            return variables;
        },
        isCollapse() {
            return !this.sidebar.opened;
        }
    },
    data() {
        return {
            year: '',
            logoUrl: ''
        }
    },
    created() {
        // this.getAuthConfig()
        this.nowtime()
    },
    methods: {
        handleSelect(url, indexPath) {
            let visitedViews = this.$store.state.tagsView.visitedViews
            if (visitedViews.length > 0) {
                visitedViews.forEach(element => {
                    if (element.fullPath === url) {
                        this.$store.dispatch('tagsView/delCachedView', element).then(() => { })
                    }
                });
            }
            if (this.$route.fullPath === url) {
                this.$nextTick(() => {
                    this.$router.replace({
                        path: '/redirect' + url
                    })
                })
            }
        },
        getAuthConfig() {
            // getAuthConfig().then(res => {
            //     if (res.data.configServerInfoVO.logoUrl) {
            //         this.logoUrl = res.data.configServerInfoVO.logoUrl
            //     }
            // })
        },

        nowtime() {
            let nowDate = new Date();
            this.year = nowDate.getFullYear();
        }
    },
};
</script>


<style lang="scss" scoped>
.footer {
    width: 100%;
    height: 90px;
    text-align: center;
    margin-top: 20px;
    font-size: 16px;
    overflow: hidden;

    .footer-img {
        margin-top: 14px;
        width: 120px;
        height: 120px;
        position: relative;
        top: -60px;
    }

    .company-name {
        margin-top: 4px;
        font-size: 14px;
        color: #29303b;
        position: relative;
        top: -100px;
    }
}
</style>
