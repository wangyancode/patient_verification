import request from '@/utils/request'

export function getData(data) {
  return request({
    url: '/databaseMonitor/page',
    method: 'post',
    data: data
  })
}
// 获取分类
export function getDict(dictType) {
  return request({
    url: `/dictData/list/${dictType}`,
    method: 'get',
  })
}