import request from '@/utils/request'

// 认证相关的API接口
export const AUTH_API = {
  // 账号密码登录
  login: (identifier, password) => {
    return request({
      url: '/auth/login',
      method: 'post',
      data: {
        identifier,
        password
      }
    })
  },

  // 注册新用户
  register: (registerData) => {
    return request({
      url: '/auth/register',
      method: 'post',
      data: registerData
    })
  },

  // 获取图形验证码
  getCaptcha: () => {
    return request({
      url: '/auth/captcha',
      method: 'get',
      responseType: 'blob'
    })
  },

  // 发送短信验证码
  sendSmsCode: (phone) => {
    return request({
      url: '/auth/sms/code',
      method: 'post',
      data: { phone }
    })
  },

  // 重置密码
  resetPassword: (identifier, captcha, newPassword) => {
    return request({
      url: '/auth/password/recall',
      method: 'post',
      data: {
        identifier,
        captcha,
        newPassword
      }
    })
  }
}