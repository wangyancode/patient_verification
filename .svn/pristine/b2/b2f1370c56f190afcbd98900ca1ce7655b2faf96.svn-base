import request from '@/utils/request'
// 获取当前版本号
export function getRersion() {
  return request({
    url: 'appVersions/getLastVersions',
    method: 'get',
  })
}

// 文件图片上传
export function upLoadData(query) {
  return request({
    url: '/system/document/fastDFSUpload',     
    method: 'post',
    data: query
  })
}

//保存版本号
export function saveRersion(query) {
  return request({
    url: '/appVersions/save',     
    method: 'post',
    data: query
  })
}