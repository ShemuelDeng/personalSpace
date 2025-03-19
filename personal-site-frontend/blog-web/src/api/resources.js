import request from '@/utils/request'

/**
 * 获取资源列表
 */
export function getResourcesApi(params) {
  return request({
    url: '/api/resource/list',
    method: 'get',
    params
  })
}

/**
 * 添加资源
 */
export function addResourceApi(data) {
  return request({
    url: '/api/resource/add',
    method: 'post',
    data
  })
} 

/**
 * 验证码验证
 */
export function verifyCodeApi(data) {
  return request({
    url: '/api/resource/verify',
    method: 'get',
    params: data
  })
}

