// src/utils/request.js
import axios from 'axios'
import { Message } from 'element-ui'

const service = axios.create({
    baseURL: 'http://localhost:8089', // 根据实际后端地址修改
    timeout: 10000
})

service.interceptors.response.use(
    response => {
        const res = response.data
        if (res.code !== 200) {
            Message.error(res.msg || '请求失败')
            return Promise.reject(new Error(res.msg || 'Error'))
        }
        return res
    },
    error => {
        Message.error(error.message)
        return Promise.reject(error)
    }
)

export default service
