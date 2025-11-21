import request from '@/utils/request'

// 查询
export function detailData(query) {
    return request({
      url: '/system/config/detail',
      method: 'get',
      params: query
    })
  }

export function editDetailData(data) {
    return request({
      url: '/system/config/update',
      method: 'put',
      data:data
    })
  }
