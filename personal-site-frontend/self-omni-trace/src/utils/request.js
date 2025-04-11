import axios from 'axios'
import { Message } from 'element-ui'

// 创建axios实例
const service = axios.create({
  // 从环境变量中获取基础URL
  baseURL: process.env.VUE_APP_BASE_API,
  // 请求超时时间
  timeout: 10000
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 在请求发送前可以进行一些处理，比如添加token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    console.error('请求错误：', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    // 这里可以根据后端的响应结构进行统一处理
    if (res.code !== 200) {
      Message.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  error => {
    console.error('响应错误：', error)
    Message.error(error.message || '请求失败')
    return Promise.reject(error)
  }
)

export default service