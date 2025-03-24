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
        component: () => import('@/views/HomeView.vue'),
        meta: { title: '首页' }
      },
      // {
      //   path: '/articles',
      //   component: () => import('@/views/Articles.vue'),
      //   meta: { title: '技术文章' }
      // },
      // {
      //   path: '/workspace',
      //   component: () => import('@/views/Workspace.vue'),
      //   meta: { title: '工作空间' }
      // },
      // 其他子路由...
    ]
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
