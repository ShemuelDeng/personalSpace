// API配置文件

// 基础URL配置
export const BASE_URL = 'http://localhost:8088'  // 开发环境API地址

// API端点配置
export const API_ENDPOINTS = {
  // 文章相关接口
  ARTICLE: {
    LIST: '/api/article/list',  // 获取文章列表
    DETAIL: '/api/article/',  // 获取文章详情，需要拼接文章ID
    CREATE: '/api/article/add',  // 创建文章
    UPDATE: '/api/article/update/',  // 更新文章，需要拼接文章ID
    DELETE: '/api/article/delete/',  // 删除文章，需要拼接文章ID
    SYNC_THIRD_PLATFORM: '/api/article/sync/third-platform',  // 同步文章到CSDN，需要拼接文章ID
  },
  ARTICLE_SYNC: {
    LIST: '/api/article-sync-record/list'
  },
  // 标签相关接口
  TAG: {
    LIST: '/api/user-tag/list',  // 获取标签列表
    CREATE: '/api/tags',  // 创建标签
  },
  
  // 专栏相关接口
  CATEGORY: {
    LIST: '/api/user-category/list',  // 获取分类列表
  },
  
  // 文件上传接口
  UPLOAD: {
    IMAGE: '/api/upload/image',  // 图片上传
  },
  
  // 用户相关接口
  USER: {
    LOGIN: '/api/auth/login',  // 用户登录
    LOGOUT: '/api/auth/logout',  // 用户登出
    PROFILE: '/api/users/profile',  // 获取用户信息
  }
}