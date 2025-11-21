import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    data,
    headers: {
      isToken: false
    }
  })
}

export function getInfo() {
  return request({
    url: '/getInfo',
    method: 'get',
  })
}



//获取标准字典信息
export function getQueryByType() {
  return request({
    url: 'dictData/queryByType/empi_',
    method: 'get',
  })
}


//获取操作人信息
export function getOperators() {
  return request({
    url: '/user/queryList',
    method: 'post',
    data:{"userName":""}
  })
}


//获取字段设置
export function getFieldConfig() {
  return request({
    url: '/empiFieldsConfig/queryAllConfig',
    method: 'post',
  })
}


//新增字段设置
export function userFieldSave(data) {
  return request({
    url: '/empiFieldsConfig/save',
    method: 'post',
    data
  })
}

//编辑字段设置
export function userFieldUpdate(data) {
  return request({
    url: '/empiFieldsConfig/update',
    method: 'put',
    data
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post',
    headers: {
      isToken: false
    }
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    method: 'get',
    headers: {
      isToken: false
    }
  })
}


// 二次验证
export function verifyPassword(data) {
  return request({
    url: '/verifyPassword',
    method: 'post',
    data
  })
}
