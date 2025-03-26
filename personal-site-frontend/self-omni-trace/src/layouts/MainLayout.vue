<template>
  <div class="main-layout">
    <el-container>
      <el-aside width="250px">
        <!-- 个人信息部分 -->
        <div class="sidebar-profile">
          <el-avatar :size="80" src="https://avatars.githubusercontent.com/u/objectx" class="profile-avatar"></el-avatar>
          <h3 class="sidebar-name">ObjectX-不知名程序员</h3>
          <p class="sidebar-desc">前端工程师 & 图形编辑 & AI</p>
        </div>
        
        <el-menu
          :default-active="$route.path"
          router
          class="site-menu"
          background-color="#f5f7fa"
          text-color="#303133"
          active-text-color="#409EFF">
          <el-menu-item v-for="item in menuItems" :key="item.path" :index="item.path">
            <i :class="item.icon"></i>
            <span>{{ item.title }}</span>
          </el-menu-item>
        </el-menu>
        
        <!-- 在线状态和社交链接 -->
        <div class="sidebar-footer">
          <div class="online-status">
            <span class="status-dot"></span>
            <span>Online</span>
          </div>
          <div class="sidebar-social-links">
            <a href="https://github.com/objectx" target="_blank" class="social-icon">
              <i class="el-icon-link"></i>
            </a>
            <a href="https://juejin.cn/user/objectx" target="_blank" class="social-icon">
              <i class="el-icon-medal-1"></i>
            </a>
            <a href="https://zhihu.com/people/objectx" target="_blank" class="social-icon">
              <i class="el-icon-notebook-2"></i>
            </a>
          </div>
        </div>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'MainLayout',
  data() {
    return {
      menuItems: [
        { path: '/', icon: 'el-icon-s-home', title: '首页&简介' },
        { path: '/tech-stack', icon: 'el-icon-s-management', title: '技术栈' },
        { path: '/notes', icon: 'el-icon-notebook-1', title: '灵感笔记' },
        { path: '/tech-articles', icon: 'el-icon-document', title: '技术文章' },
        { path: '/life-photos', icon: 'el-icon-picture', title: '生活相册' },
        { path: '/workspace', icon: 'el-icon-office-building', title: '工作空间' },
        { path: '/navigation', icon: 'el-icon-guide', title: '导航站' },
        { path: '/timeline', icon: 'el-icon-time', title: '时间轴' },
        { path: '/projects', icon: 'el-icon-s-cooperation', title: '项目' },
        { path: '/demo', icon: 'el-icon-s-platform', title: 'demo' },
        { path: '/friends', icon: 'el-icon-user', title: '友链' }
      ]
    }
  }
}
</script>

<style scoped>
.main-layout {
  display: flex;
}

.el-container {
  min-height: 100vh;
  width: 100%;
}

.el-aside {
  background-color: #f5f7fa;
  border-right: 1px solid #e6e6e6;
  display: flex;
  flex-direction: column;
  position: relative;
}

.sidebar-profile {
  padding: 20px 15px;
  text-align: center;
  border-bottom: 1px solid #e6e6e6;
}

.sidebar-name {
  margin: 10px 0 5px;
  font-size: 16px;
  font-weight: bold;
}

.sidebar-desc {
  font-size: 12px;
  color: #606266;
  margin: 0;
}

.site-menu {
  border-right: none;
  flex: 1;
}

.el-main {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.profile-header {
  position: relative;
}

.profile-banner {
  height: 200px;
  background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%);
}

.profile-info {
  max-width: 1200px;
  margin: -50px auto 0;
  padding: 20px;
  position: relative;
  z-index: 1;
}

.profile-avatar {
  border: 4px solid #fff;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.profile-name {
  margin: 15px 0;
  font-size: 24px;
  color: #303133;
}

.profile-description {
  color: #606266;
  line-height: 1.6;
  margin-bottom: 30px;
}

.social-links, .site-stats, .education {
  margin-bottom: 40px;
}

.links-container {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
  margin-top: 15px;
}

.stats-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 15px;
}

.stat-item {
  text-align: center;
}

.stat-item i {
  font-size: 24px;
  color: #409EFF;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 20px;
  font-weight: bold;
  color: #303133;
  margin-top: 8px;
}

.education-card {
  margin-top: 15px;
}

.education-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.school {
  font-weight: bold;
  color: #303133;
}

.major, .degree, .period {
  color: #606266;
}

.year {
  color: #909399;
}

/* 侧边栏底部样式 */
.sidebar-footer {
  padding: 15px;
  border-top: 1px solid #e6e6e6;
  margin-top: auto;
}

.online-status {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  font-size: 14px;
}

.status-dot {
  width: 8px;
  height: 8px;
  background-color: #67C23A;
  border-radius: 50%;
  margin-right: 6px;
}

.sidebar-social-links {
  display: flex;
  justify-content: space-around;
  margin-top: 10px;
}

.social-icon {
  color: #606266;
  font-size: 18px;
  transition: color 0.3s;
}

.social-icon:hover {
  color: #409EFF;
}
</style>