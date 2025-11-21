//工作台接口
import request from '@/utils/request'

export function saveData(data) {
  return request({
    url: '/dictData/page',
    method: 'post',
    data: data
  })
}