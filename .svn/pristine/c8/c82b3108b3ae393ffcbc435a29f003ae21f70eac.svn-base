import request from '@/utils/request'

export function listRole(data) {
  return request({
    url: '/dictData/page',
    method: 'post',
    data: data
  })
}
export function listLeftDict(data) {
  return request({
    url: '/dictType/page',
    method: 'post',
    data: data
  })
}
//左侧字典类型新增
export function addTypeNew(data) {
  return request({
    url: '/dictType/save',
    method: 'post',
    data: data
  })
}
//左侧字典类型修改
export function updateTypeNew(data) {
  return request({
    url: '/dictType/update',
    method: 'put',
    data: data
  })
}
//左侧字典类型删除
export function deleteTypeNew(data) {
  return request({
    url: '/dictType/delete',
    method: 'delete',
    data: data
  })
}
//字典列表新增
export function DictAdd(data) {
  return request({
    url: '/dictData/save',
    method: 'post',
    data: data
  })
}
//字典列表修改
export function DictEdit(data) {
  return request({
    url: '/dictData/update',
    method: 'put',
    data: data
  })
}
export function DictDelete(data) {
  return request({
    url: '/dictData/delete',
    method: 'delete',
    data: data
  })
}
export function getLogin() {
  return request({
    url: '/loginLog/' + userAccount,
    method: 'get'
  })
}