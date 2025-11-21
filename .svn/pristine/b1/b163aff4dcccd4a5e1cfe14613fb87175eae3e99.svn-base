import request from '@/utils/request'

// 初始化查询
export function getHealData(data) {
    return request({
        url: '/applyCompany/page',
        method: 'post',
        data: data
    })
}

// 新增

export function addHealData(data) {
    return request({
        url: '/applyCompany/save',
        method: 'post',
        data: data
    })
}

// 删除
export function deleteHealData(data) {
    return request({
        url: `/applyCompany/delete`,
        method: 'delete',
        data: data
    })
}

// 编辑
export function updateHealData(data) {
    return request({
        url: `/applyCompany/update`,
        method: 'put',
        data: data
    })
}

// 修改用户状态
export function resetStatus(data) {
    return request({
        url: '/user/changeStatus',
        method: 'post',
        data: data
    })
}

// 重置
export function resetting(data) {
    return request({
        url: `/user/resetPwd`,
        method: 'put',
        data: data
    })
}

// 单位名称下拉查询
export function unitNameData(data) {
    return request({
        url: '/company/list',
        method: 'post',
        data:data
    })
}
// 角色下拉查询
export function rolesSelectData(data) {
    return request({
        url: '/system/role/optionselect',
        method: 'get',
    })
}

// 职称下拉查询
export function positionalSelectData(data) {
    return request({
        url: '/positionalTitle/list',
        method: 'post',
        data: data
    })
}










