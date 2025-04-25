import axios from 'axios'
import { Message } from 'element-ui'
import router from '@/router'

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
      config.headers['Authorization'] = `${token}`
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
    return res
  },
  error => {
    if (error.response) {
      const { status } = error.response
      if (status === 401) {
        localStorage.setItem('token', null)
        console.log('token过期1111')

          // 2. 避免重复跳转
          if (router.currentRoute.path !== '/login') {
              const currentPath = router.currentRoute.fullPath
              router.replace({
                  path: '/login',
                  query: { redirect: currentPath }
              })
          }
      }

      // 可选：根据其他状态码处理不同错误
      Message.error(error.response.data?.message || '请求失败')
    } else {
      // 处理网络错误或无响应的情况
      // Message.error('网络错误或服务器无响应')
        console.log('网络错误或服务器无响应', error)
    }
    return Promise.reject(error)
  }
)

export default service