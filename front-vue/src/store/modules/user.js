import { login, logout, getInfo } from "@/api/login";
import { getToken, setToken, removeToken } from "@/utils/auth";
import { getPersonalInfo } from "@/api/system/user";

const user = {
  state: {
    token: getToken(),
    name: "",
    avatar: "",
    roles: [],
    permissions: [],
    userInfo: {},
    islogin: 0,
    messageNoteadLists: [],
    messageNoteadNum: 0,
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_NAME: (state, name) => {
      state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles;
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions;
    },
    SET_USERINFO: (state, user) => {
      state.userInfo = user;
    },
    SET_IS_LOGIN: (state, islogin) => {
      state.islogin = islogin;
    },
    SET_MESSAGE_NO_READ_LIST: (state, messageNoteadLists) => {
      state.messageNoteadLists = messageNoteadLists;
    },
    SET_MESSAGE_NO_READ_NUM: (state, number) => {
      state.messageNoteadNum = number;
    },
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      const username = userInfo.username.trim();
      const password = userInfo.password;
      const code = userInfo.code;
      const uuid = userInfo.uuid;
      return new Promise((resolve, reject) => {
        login(username, password, code, uuid)
          .then((res) => {
            setToken(res.data.token);
            commit("SET_TOKEN", res.data.token);
            commit("SET_IS_LOGIN", 1);
            resolve();
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 判断当前是否是刚登录状态 修改刚登录状态为0  0--非刚登陆 1--刚登录
    IsFirstLogin({ commit }) {
      commit("SET_IS_LOGIN", 0);
    },

    MessageNoteadLists({ commit }, messageLists) {
      let messageNoteadLists = JSON.parse(messageLists);
      // console.log('state111', messageNoteadLists);
      commit("SET_MESSAGE_NO_READ_LIST", messageNoteadLists);
      commit("SET_MESSAGE_NO_READ_NUM", messageNoteadLists.length);
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getPersonalInfo().then((response) => {
          // 修复个人中心修改头像后，工作台首页头像因为接口getInfo的avatar没变不会变动问题
          let user = response.data;
          let defaultAvatar =
            user.userSex == "女"
              ? require("@/assets/images/avatar-nv.png")
              : require("@/assets/images/avatar-nan.png");
          let avatar =
            user.avatar == "" || user.avatar == null
              ? defaultAvatar
              : user.avatar;
          commit("SET_AVATAR", avatar);
        });

        getInfo()
          .then((res) => {
            const user = res.data.user;
            // process.env.VUE_APP_BASE_API
            // const avatar = (user.avatar == "" || user.avatar == null) ? require("@/assets/images/profile.jpg") : user.avatar;
            if (
              res.data.rolePermissions &&
              res.data.rolePermissions.length > 0
            ) {
              // 验证返回的roles是否是一个非空数组
              commit("SET_ROLES", res.data.rolePermissions);
              commit("SET_PERMISSIONS", res.data.menuPermissions);
            } else {
              commit("SET_ROLES", ["ROLE_DEFAULT"]);
            }
            commit("SET_NAME", user.userAccount);
            // commit('SET_AVATAR', avatar)
            commit("SET_USERINFO", user);
            resolve(res);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token)
          .then(() => {
            commit("SET_TOKEN", "");
            commit("SET_ROLES", []);
            commit("SET_PERMISSIONS", []);
            removeToken();
            resolve();
          })
          .catch((error) => {
            reject(error);
          });
      });
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise((resolve) => {
        commit("SET_TOKEN", "");
        removeToken();
        resolve();
      });
    },
  },
};

export default user;
