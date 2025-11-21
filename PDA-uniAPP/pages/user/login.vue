<template>
	<view class="login">
		<view class="top_box">
			<image src="/static/bg/login_bg.png" class="bg"></image>
			<image src="/static/login_text.png" class="login_text" mode="widthFix"></image>
		</view>
		<view class="u-demo-area">
			<view class="container">
				<uv-form :model="loginForm" :rules="loginRules" ref="uForm" errorType="toast">
					<uv-form-item prop="userAccount" label-width="0" :borderBottom="false"
						:customStyle="{borderRadius:'16rpx',background:'#eee',padding:'20rpx'}">
						<uv-input border="none" class="u-input" :placeholderStyle="'fontSize:30rpx'"
							placeholder="请输入工号" v-model="loginForm.userAccount" type="text" prefixIcon="account"
							prefixIconStyle="font-size: 34rpx;color: rgba(0,0,0,0.85)" @confirm="formSubmit">
						</uv-input>
					</uv-form-item>
					<uv-form-item prop="password" label-width="0" :borderBottom="false"
						:customStyle="{borderRadius:'16rpx',background:'#eee',padding:'20rpx',marginTop:'30rpx'}">
						<uv-input :password-icon="true" :placeholderStyle="'fontSize:30rpx'" border="none"
							type="password" v-model="loginForm.password" placeholder="请输入密码" prefixIcon="lock"
							prefixIconStyle="font-size: 34rpx;color: rgba(0,0,0,0.85)" @confirm="formSubmit"></uv-input>
					</uv-form-item>
					<uv-form-item prop="department" label-width="0" :borderBottom="false" class="uv-flex"
						:customStyle="{borderRadius:'16rpx',background:'#eee',padding:'24rpx 20rpx',marginTop:'30rpx'}">
						<uv-icon name="order" :size="34" color="rgba(0,0,0,0.85)" style="margin-right:10rpx"></uv-icon>
						<picker class="uv-flex-1" :range="deptList" range-key="dictLabel" @change="setDept">
							<view class="uv-flex w-100">
								<text class="uv-flex-1" :class="{defaultColor:!loginForm.department}">{{loginForm.department||'请选择科室'}}</text>
								<uv-icon name="arrow-down" :size="34" color="rgba(0,0,0,0.85)"></uv-icon>
							</view>
						</picker>
					</uv-form-item>
				</uv-form>
				<uv-button @click="formSubmit" data-name="reg" type="primary" class="login-b" :shape="'circle'">登录</uv-button>
			</view>
		</view>
		<uv-modal @cancel='cancelDalog' :show-cancel-button='true' ref="modal" content="当前版本过低,请及时更新" @confirm="upConfirm"></uv-modal>
		
	</view>
</template>
<script>
	import configs from '../../common/config.js'
	export default {
		data() {
			return {
				loginForm: {
					userAccount: "",
					password: "",
					platform:'app',
					department:'',
				},
				loginRules: {
					userAccount: [{
						required: true,
						trigger: "blur",
						message: "请输入工号"
					}],
					password: [{
						required: true,
						trigger: "blur",
						message: "请输入密码"
					}],
					department:[{
						required: true,
						trigger: "change",
						message: "请选择科室"
					}]
				},
				isLoading:false,
				deptName:'',
				errorType: ['toast'],
				deptList:[],
				memberInfo: {},
				roles: [],
				showPopup: false,
				isUpShow: false,
				isUp: true,
				versionData:{} ,
				userData:{}
			};
		},
		computed:{
			token(){
				return this.$store.state.token;
			}
		},
		onLoad() {
			if(this.token){
				this.$api.getInfo().then(res=>{
					this.$store.commit('SET_USERINFO',res.user);
					this.$store.commit('SET_DEPTCODE',res.user.regionList[0].regionValue);
					uni.reLaunch({
						url:'/pages/workPages/index'
					})
				})
			}else{
				// uni.showModal({
				// 	title: '提示',
				// 	content: configs.baseURL||'未知错误',
				// 	showCancel: false
				// })
				this.$api.getDict('user_dept').then(res=>{
					this.deptList = res;
					// console.log(777777777777,res)
				}).catch(err=>{
					uni.showModal({
						title: '提示',
						content: err||'未知错误',
						showCancel: false
					})
				})
				// this.$api.getVersion()
			}
		},
		methods: {
			setDept(e){
				const index = e.detail.value;
				this.loginForm.department = this.deptList[index].dictLabel;
				this.$store.commit('SET_DEPTCODE',this.deptList[index].dictValue)
			},
			formSubmit() {
				this.$refs.uForm.validate().then(() => {
					if(this.isLoading) return;
					this.isLoading = true;
					this.$api.login(this.loginForm).then(res=>{
						this.$store.commit('SET_TOKEN',res.token)
						// this.$api.getInfo().then(res=>{
						// 	this.$store.commit('SET_USERINFO',res.user);
						// 	uni.redirectTo({
						// 		url:'/pages/workPages/index'
						// 	})
						// })
						// that.$request.setConfig(config => {
						// 	/* 设置全局配置 */
						// 	config.header = {
						// 		...config.header,
						// 		Authorization: 'Bearer ' + res.data.token,
						// 	};
						// 	return config;
						// });
						// console.log(3333333333)
						uni.setStorageSync('token',res.token)
						this.updates()
					}).finally(()=>{
						this.isLoading = false;
					})
				});
			},
			updates() {
				var that = this
				let system = uni.getSystemInfoSync();
                console.log(666666666666,system.appVersion)
				this.$api.checkVersion(system.appVersion).then(res => {
					// console.log(7777777,res)
					if (res == null) {
						that.isUp = true
						// that.versionsNo = '最新版本' + system.appVersion
					} else {
						that.isUp = false
						that.versionData = res
						// console.log(222222222,that.versionData)
						// that.versionsNo = '当前版本过低（'+system.appVersion + ')'+'，请及时更新'
					}
					if (!this.isUp) {
						setTimeout(() => {
							// this.isUpShow = true
							this.$refs.modal.open()
							// console.log(333333333)
						}, 500)
					} else {
						this.getMemberInfo()
					}
				}).catch(err=>{
					console.log("error:",err)
				})
			},
			getMemberInfo() {
				var that = this;
				this.$api.getInfo().then(res => {
						console.log('memberInfo', res);
						this.$store.commit('SET_USERINFO', res.user);
						that.memberInfo = res.user
						that.roles = res.rolePermissions
						uni.setStorageSync('memberInfo', res.user);
						uni.setStorageSync('roles', res.rolePermissions);
						// if (res.data.rolePermissions && (res.data.rolePermissions[0] == 'sys_nurse' || res.data
						// 		.rolePermissions[0] == 'sys_delivery' || res.data.rolePermissions[0] == 'sys_pharmacy'
						// 	)) {
							uni.showToast({
								title: '登录成功',
								success: function(res2) {
									// that.islogin = true;
									setTimeout(function() {
										//判断用户角色跳转对应页面
										// if (res.data.rolePermissions[0] == 'sys_nurse') { //护士端
											uni.reLaunch({
												url: '/pages/workPages/index'
											})
										// }
										// if (res.data.rolePermissions[0] == 'sys_delivery') { //配送端
										// 	uni.reLaunch({
										// 		url: '/pages/delivery/index'
										// 	})
										// }
										// if (res.data.rolePermissions[0] == 'sys_pharmacy') { //药房端
										// 	uni.reLaunch({
										// 		url: '/pages/dispensing/index'
										// 	})
										// }
			
									}, 1500);
								}
							});
						// } else {
						// 	uni.showToast({
						// 		title: '该用户无此权限，无法登陆',
						// 		icon: 'none',
						// 	});
						// 	that.clearToken()
						// }
			
					})
					.catch(err => {});
			},

			clearToken() {
				var that = this
				uni.clearStorageSync()
				// that.$request.setConfig(config => {
				// 	/* 设置全局配置 */
				// 	config.header = {
				// 		...config.header,
				// 		Authorization: '',
				// 	};
				// 	return config;
				// });
			},
			cancelDalog() {
				uni.showToast({
					title: '已取消更新',
					duration: 2000,
					icon: 'none',
				});
				this.getMemberInfo()
			},
			upConfirm() {
				this.downloadFile(this.versionData.documentUrl)
			},
			async downloadFile(url) {
				uni.showLoading({
					title: "下载中"
				});
				uni.downloadFile({
					url: url, //仅为示例，并非真实的资源
					success: (res) => {
						console.log(res, "下载成功");
						if (res.statusCode === 200) {
							console.log("下载成功");
							uni.hideLoading();
							uni.showToast({
								title: "下载成功",
								icon: "success"
							});
							uni.saveFile({
								tempFilePath: res.tempFilePath,
								success: function(res) {
									uni.openDocument({
										filePath: res.savedFilePath,
										success: function(
												res) {
											console.log(
													res,
													"打开安装包"
											);
										},
									});
								},
								fail: (err) => {
									console.log(err, "打开安装包-失败");
								},
							});
						}
					},
					fail: (err) => {
						console.log(err, "下载失败");
						uni.hideLoading();
						uni.showToast({
							title: "下载失败,请检查网络",
							icon: "none",
							duration: 1500,
						});
					},
				});
			},
		}
	};
</script>

<style lang="scss" scoped>
	.login {
		width: 100vw;
		height: 100vh;
		background: #fff;
		overflow: hidden;

		.top_box {
			width: 100%;
			height: 462rpx;
			position: relative;
			z-index: 2;
			display: flex;
			align-items: center;
			justify-content: center;

			.bg {
				width: 100%;
				height: 100%;
				position: absolute;
				top: 0;
				left: 0;
				z-index: -1;
			}

			.login_text {
				width: 480rpx;
				height: auto;
			}
		}
	}

	.u-demo-area {
		margin-top: 66rpx;
		padding: 0 16rpx;
	}

	.container {
		text-align: left;
		font-size: 32rpx;
		color: #fff;
		.defaultColor{
			font-size: 30rpx;
			color: #808080;
		}
	}

	.login-b {
		margin-top: 100upx;
		height: 100upx;
		width: 100%;
	}


</style>