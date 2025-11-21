<script>
import { mapGetters } from 'vuex'
import { getAuthCodeFlag } from "@/api/login";
export default {
  computed: {
    ...mapGetters(['userInfo', 'islogin', ]),
    visitedViews() {
      return this.$store.state.tagsView.visitedViews
    },
  },
  created() {

    let name = '/404'
    if (this.$store.state.permission.addRoutes && this.$store.state.permission.addRoutes.length > 0) {
      if (this.$store.state.permission.addRoutes[0].children && this.$store.state.permission.addRoutes[0].children.length > 0) {
        name = this.$store.state.permission.addRoutes[0].children[0].name
      }
    }

    console.log('addRoutes', this.$store.state.permission);
    console.log('name', name);

    this.$tab.closeAllPage().then(() => { })
    // this.$router.replace({ path: '/president' })
    this.$router.replace({ name: name }).catch(err => { console.log(err) })
  },
  mounted() {
    this.checkChangePassword()
  },
  render: function (h) {
    return h() // avoid warning message
  },
  methods: {
    checkChangePassword() {
      let changePwd = 0
      getAuthCodeFlag('initUpdatePasswordFlag').then(res => {
        changePwd = res.msg
        if (changePwd == 1) { //开启修改密码开关设置（0-- 关闭 1--开启）
          // console.log('islogin',this.islogin);
          // console.log('resetPasswordFlag',this.userInfo.resetPasswordFlag);
          if (this.islogin == 1) {  //判断是否首次登录进入页面
            this.$store.dispatch("IsFirstLogin").then(() => { }).catch(() => { }); //修改刚登录状态为0  0--非刚登陆 1--刚登录
            if (this.userInfo.resetPasswordFlag == '1') {  //判断用户是否已修改过密码
              this.$confirm('为保证您的账户安全，请及时修改您的登录密码', '提示', {
                confirmButtonText: '确定',
                showCancelButton: false,
                type: 'warning'
              }).then(() => {
                this.$router.push("/user/profile")
              }).catch(() => { });
            }
          }
        }
      })
    },
  }
}
</script>