import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex);

const store = new Vuex.Store({
	state:{
		token:uni.getStorageSync('token')||'',
		userInfo:null,
		deptCode:'',
	},
	mutations:{
		SET_TOKEN(state,data){
			state.token = data;
		},
		SET_USERINFO(state,data){
			state.userInfo = data;
		},
		SET_DEPTCODE(state,data){
			state.deptCode = data;
		},
		
	},
})
export default store
