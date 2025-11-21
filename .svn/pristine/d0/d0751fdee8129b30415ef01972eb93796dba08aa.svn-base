import request from '@/utils/request'
// 获取用户信息
export function getUserData(data) {
  return request({
    url: '/user/page',
    method: 'post',
    data
  })
}
// 新增
export function addUserData(data) {
  return request({
    url: '/user/save',
    method: 'post',
    data
  })
}
// 获取角色信息
export function getRoleData(data) {
  return request({
    url: '/system/role/optionselect',
    method: 'get',
  })
}
// 编辑
export function editUserData(data) {
  return request({
    url: '/user/update',
    method: 'put',
    data: data
  })
}
// 删除
export function deleteUserData(data) {
  return request({
    url: '/user/delete',
    method: 'delete',
    data: data
  })
}

// 重置密码
export function resetPssword(data) {
  return request({
    url: '/user/resetPwd',
    method: 'put',
    data: data
  })
}
// 修改用户状态
export function changeStatus(data) {
  return request({
    url: '/user/changeStatus',
    method: 'post',
    data
  })
}

// 获取初始密码
export function getInitialPassword() {
  return request({
    url: '/system/config/configKey/initPassword',
    method: 'get',
  })
}

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return request({
    // url: '/dictData/list/' + dictType,
    url: "/system/dict/data/type/" + dictType,
    method: "get",
  });
}