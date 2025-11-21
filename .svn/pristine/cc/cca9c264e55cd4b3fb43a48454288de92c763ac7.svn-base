import request from '@/utils/request'

// 文档总数
export function fetchOverview() {
  return request({
    url: '/sharefileDataCount/countOne',
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
export function listData(data) {
  return request({
    url: '/sharefileDataCount/listView',
    method: 'post',
    data: data
  })
}
export function chartData(data) {
  return request({
    url: '/sharefileDataCount/statisticView',
    method: 'post',
    data: data
  })
}