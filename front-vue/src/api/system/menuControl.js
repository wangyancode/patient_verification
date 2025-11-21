import request from '@/utils/request'

// 分页查询
export function listData(data) {
  return request({
    url: '/system/menu/list',
    method: 'post',
    data: data
  })
}

export function getPerms() {
  return request({
    url: '/system/menu/generatePermissionID',
    method: 'get'
  })
}

// 新增菜单
export function addMenuNew(data) {
  return request({
    url: '/system/menu/save',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateRoleNew(data) {
  return request({
    url: '/system/menu/update',
    method: 'put',
    data: data
  })
}


// 删除厂商
export function delMenuNew(data) {
  return request({
    url: '/system/menu/delete/' + data,
    method: 'delete',
    data: data
  })
}