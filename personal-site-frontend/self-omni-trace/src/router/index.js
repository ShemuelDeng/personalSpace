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
      {        path: '/articles',
        component: () => import('@/views/ArticleList.vue'),
        meta: { title: '技术文章' }
      }
    ]
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
