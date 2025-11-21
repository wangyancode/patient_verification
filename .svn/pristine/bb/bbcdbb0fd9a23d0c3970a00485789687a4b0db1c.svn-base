import request from '@/utils/request'

// 查询角色列表
export function listRole(data) {
  return request({
    url: '/system/role/list',
    method: 'post',
    data: data
  })
}


// 分配用户分页查询列表
export function roleUserList(data) {
  return request({
    url: '/system/role/distribution/list',
    method: 'post',
    data: data
  })
}


// 添加用户分页查询列表
export function roleAddUserList(data) {
  return request({
    url: '/system/role/addUser/list',
    method: 'post',
    data: data
  })
}


// 机构科室树形结构
export function departmentTree(data) {
  return request({
    url: '/department/tree/list',
    method: 'POST',
    data: data
  })
}



// 选择用户添加所选
export function roleSelectAddUser(data) {
  return request({
    url: '/system/role/addUser',
    method: 'POST',
    data: data
  })
}


// 选择用户添加所有用户
export function roleAllAddUser(data) {
  return request({
    url: '/system/role/addAllUser',
    method: 'POST',
    data: data
  })
}


// 选择用户取消授权
export function roleCancelSingle(data) {
  return request({
    url: '/system/role/cancel/single',
    method: 'DELETE',
    data: data
  })
}


// 所有用户取消授权
export function roleCancelAllSingle(data) {
  return request({
    url: '/system/role/cancel/all',
    method: 'DELETE',
    data: data
  })
}

// 查询角色详细
export function getRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/system/role/add',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/system/role/edit',
    method: 'POST',
    data: data
  })
}


// 角色状态修改
export function changeRoleStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/system/role/updateStatus?id=' + data.id + '&status=' + data.status,
    method: 'put',
    data: data
  })
}

// 删除角色
export function delRole(id) {
  return request({
    url: '/system/role/delete?id=' + id,
    method: 'delete',
  })
}


// 查询应用权限(集成门户)
export function getAppAuthlist(data) {
  return request({
    url: '/system/role/auth/app?id=' + data.id,
    method: 'POST',
    data
  })
}

// 查询数据权限(集成门户)
export function getDataAuthlist(data) {
  return request({
    url: '/system/role/auth/data?id=' + data.id,
    method: 'POST',
    data
  })
}

// 查询脱敏规则列表（360系统）
export function getHideAuthlist(data) {
  return request({
    url: '/system/role/auth/hide?id=' + data.id,
    method: 'POST',
    data
  })
}

// 查询菜单操作权限列表
export function getMenuAuthlist(data) {
  return request({
    url: '/system/role/auth/menu?id=' + data.id + '&type=' + data.type,
    method: 'POST',
    data: data
  })
}



// 集成门户-保存
export function saveGetwayData(data) {
  return request({
    url: '/system/role/door/save',
    method: 'POST',
    data: data
  })
}



// 主索引/主数据/闭环[复用]-保存
export function saveMenuData(data) {
  return request({
    url: '/system/role/menu/save',
    method: 'POST',
    data: data
  })
}


// 360全息视图-保存
export function saveViewData(data) {
  return request({
    url: '/system/role/view/save',
    method: 'POST',
    data: data
  })
}


// 360全息视图-脱敏字段选择列表
export function getHideFiledList() {
  return request({
    url: '/config/hideFiledList',
    method: 'get',
  })
}





// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}


// 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// 授权用户选择
export function authUserSelectAll(data) {
  return request({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    params: data
  })
}

// 根据角色ID查询部门树结构
export function deptTreeSelect(roleId) {
  return request({
    url: '/system/role/deptTree/' + roleId,
    method: 'get'
  })
}
