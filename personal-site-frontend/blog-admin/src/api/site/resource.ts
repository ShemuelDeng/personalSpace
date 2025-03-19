import request from '@/utils/request'

/**
 * 获取资源表列表
 */
export function listSysResourceApi(params?: any) {
    return request({
        url: '/sys/resource/list',
        method: 'get',
        params
    })
}

/**
 * 获取资源表详情
 */
export function detailSysResourceApi(id: any) {
    return request({
        url: '/sys/resource/' + id,
        method: 'get'
    })
}

/**
 * 添加资源表
 */
export function addSysResourceApi(data: any) {
    return request({
        url: '/sys/resource/add',
        method: 'post',
        data
    })
}

/**
 * 修改资源表
 */
export function updateSysResourceApi(data: any) {
    return request({
        url: `/sys/resource/update`,
        method: 'put',
        data
    })
}


/**
 * 删除资源表
 */
export function deleteSysResourceApi(ids: number[] | number) {
    return request({
        url: `/sys/resource/delete/` + ids,
        method: 'delete'
    })
}


