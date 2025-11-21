import request from '@/utils/request'

// 查询菜单列表
export function listMenu() {
  return request({
    url: '/sharefileRule/selectLatestOne',
    method: 'get'
  })
}
// 查询菜单列表
export function getRule(data) {
  return request({
    url: '/dictData/page',
    method: 'post',
    data: data
  })
}
// 新增菜单
export function addRule(data) {
  return request({
    url: '/sharefileRule/save',
    method: 'post',
    data: data
  })
}