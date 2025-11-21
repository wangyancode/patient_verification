import configs from '../config.js'
// 此vm参数为页面的实例，可以通过它引用vuex中的变量
export const Request = (vm)=>{
    // 初始化请求配置
    uni.$uv.http.setConfig((config) => {
        config.baseURL = configs.baseURL;
        return config
    })
	
	// 请求拦截
	uni.$uv.http.interceptors.request.use((config) => { // 可使用async await 做异步操作
	    config.data = config.data || {}
		if(vm.$store.state.token){
			config.header.Authorization = `Bearer ${vm.$store.state.token}`
		}
	    return config 
	}, config => { // 可使用async await 做异步操作
	    return Promise.reject(config)
	})
	
	// 响应拦截
	uni.$uv.http.interceptors.response.use((response) => {
		const data = response.data
		// 自定义参数
		const custom = response.config?.custom
		if (data.code !== 200) { 
			// 如果没有显式定义custom的toast参数为false的话，默认对报错进行toast弹出提示
			if (custom.toast !== false) {
				if(data.code==401){
					uni.$uv.toast('信息已失效，请重新登录');
					uni.removeStorageSync('token');
					vm.$store.commit('SET_TOKEN','');
					vm.$store.commit('SET_USERINFO',{});
					setTimeout(()=>{
						uni.reLaunch({
							url:'/pages/user/login'
						})
					},1000)
				}else{
					// uni.$uv.toast(data.msg)
					uni.showModal({
						title: '提示',
						content: data.msg||'未知错误',
						showCancel: false
					})
				}
			}
			return Promise.reject(data)
		}
		return data.data === undefined ? {} : data.data
	}, (response) => { 
		// 对响应错误做点什么 （statusCode !== 200）
		uni.$uv.toast(response.errMsg||'请求失败')
		// uni.showModal({
		// 	title: '提示',
		// 	content: response.errMsg||'请求失败',
		// 	showCancel: false
		// })
		return Promise.reject(response)
	})
}