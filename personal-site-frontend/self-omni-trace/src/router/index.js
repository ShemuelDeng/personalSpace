import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // 外层路由
  {
    path: '/login',
    component: () => import('@/views/login/UserLogin.vue')
  },
  // 主页面（需要登录）的嵌套路由
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'), // 主页面布局组件
    meta: { requiresAuth: true }, // 需要登录的元标记
    children: [
      // 默认重定向
      {
        path: '',
        redirect: '/home'
      },
      // 子路由
      {
        path: '/home',
        component: () => import('@/layouts/IndexRight.vue'),
        meta: { title: '首页' }
      },
      // 添加编辑器页面
      {
        path: '/editor',
        component: () => import('@/views/EditorView.vue'),
        meta: { title: '编辑器' }
      },
      {
        path: '/articles',
        component: () => import('@/views/Articles.vue'),
        meta: { title: '技术文章' }
      },
      {
        path: '/article/:id',
        component: () => import('@/views/ArticleDetail.vue'),
        meta: { title: '文章详情' }
      },
      {
        path: '/article-sync-records',
        component: () => import('@/views/ArticleSyncRecords.vue'),
        meta: { title: '同步记录' }
      },
      {
        path: '/resume',
        component: () => import('@/views/ResumeView.vue'),
        meta: { title: '个人简历' }
      }
    ]
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 全局导航守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    // 如果已登录且要去登录页，重定向到首页
    if (token) {
      next('/')
    } else {
      next()
    }
  } else {
    // 检查页面是否需要登录权限
    if (to.matched.some(record => record.meta.requiresAuth)) {
      // 需要登录权限但未登录，重定向到登录页
      if (!token) {
        next({
          path: '/login',
          query: { redirect: to.fullPath }
        })
      } else {
        next()
      }
    } else {
      next()
    }
  }
})

export default router
