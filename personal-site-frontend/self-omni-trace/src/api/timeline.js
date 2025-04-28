// 时间轴大事件相关API
import request from '@/utils/request'

/**
 * 获取时间轴大事件列表
 */
export function getTimelineEventsApi(params) {
  return request({
    url: '/api/timeline-event/list',
    method: 'get',
    params
  })
}

/**
 * 获取时间轴大事件详情
 */
export function getTimelineEventDetailApi(id) {
  return request({
    url: `/api/timeline-event/${id}`,
    method: 'get'
  })
}

/**
 * 添加时间轴大事件
 */
export function addTimelineEventApi(data) {
  return request({
    url: '/api/timeline-event/add',
    method: 'post',
    data
  })
}

/**
 * 更新时间轴大事件
 */
export function updateTimelineEventApi(data) {
  return request({
    url: '/api/timeline-event/update',
    method: 'put',
    data
  })
}

/**
 * 删除时间轴大事件
 */
export function deleteTimelineEventApi(ids) {
  return request({
    url: `/api/timeline-event/delete/${ids}`,
    method: 'delete'
  })
}