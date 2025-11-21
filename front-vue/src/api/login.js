import request from '@/utils/request'

// 登录方法
export function login(userAccount, password, code, uuid) {
  const data = {
    userAccount,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}

// 注册方法
export function register(data) {
  return request({
    url: '/register',
    headers: {
      isToken: false
    },
    method: 'post',
    data: data
  })
}


// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  })
}

// 获取MAC地址
export function getMac() {
  return request({
    url: '/getMac',
    headers: {
      isToken: false
    },
    method: 'post',
    timeout: 20000
  })
}


// 获取配置
export function getAuthCodeFlag(configKey) {
  return request({
    url: 'system/config/configKey/' + configKey,
    method: 'get',
  })
}



// 获取配置
export function getAuthConfig() {
  return request({
    url: 'system/config/detail',
    method: 'get',
  })
}

