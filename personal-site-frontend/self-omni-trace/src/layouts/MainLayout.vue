<template>
  <div class="main-layout">
    <el-container>
      <el-aside :width="isCollapse ? '64px' : '250px'" class="sidebar">
        <!-- 个人信息部分 -->
        <div class="sidebar-profile" v-show="!isCollapse">
          <el-avatar :size="80" src="https://avatars.githubusercontent.com/u/objectx" class="profile-avatar"></el-avatar>
          <h3 class="sidebar-name">ObjectX-不知名程序员</h3>
          <p class="sidebar-desc">前端工程师 & 图形编辑 & AI</p>
        </div>
        
        <el-menu
          :default-active="$route.path"
          router
          :collapse="isCollapse"
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
        <div class="sidebar-footer" v-show="!isCollapse">
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
        <div class="toggle-sidebar" @click="toggleSidebar">
          <i :class="isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"></i>
        </div>
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
      isCollapse: false,
      menuItems: [
        { path: '/', icon: 'el-icon-s-home', title: '首页&简介' },
        { path: '/tech-stack', icon: 'el-icon-s-management', title: '技术栈' },
        { path: '/notes', icon: 'el-icon-notebook-1', title: '灵感笔记' },
        { path: '/articles', icon: 'el-icon-document', title: '技术文章' },
        { path: '/life-photos', icon: 'el-icon-picture', title: '生活相册' },
        { path: '/workspace', icon: 'el-icon-office-building', title: '工作空间' },
        { path: '/navigation', icon: 'el-icon-guide', title: '导航站' },
        { path: '/timeline', icon: 'el-icon-time', title: '时间轴' },
        { path: '/projects', icon: 'el-icon-s-cooperation', title: '项目' },
        { path: '/editor', icon: 'el-icon-edit', title: '编辑器' },
        { path: '/demo', icon: 'el-icon-s-platform', title: 'demo' },
        { path: '/friends', icon: 'el-icon-user', title: '友链' }
      ]
    }
  },
  methods: {
    toggleSidebar() {
      this.isCollapse = !this.isCollapse;
    }
  },
  mounted() {
    // 监听窄屏幕自动收起侧边栏
    const handleResize = () => {
      this.isCollapse = window.innerWidth <= 768;
    };
    window.addEventListener('resize', handleResize);
    handleResize(); // 初始化时执行一次
    this.$once('hook:beforeDestroy', () => {
      window.removeEventListener('resize', handleResize);
    });
  }
}
</script>

<style scoped>
.main-layout {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.el-container {
  width: 100%;
  height: 100%;
  display: flex;
}

.el-main {
  flex: 1;
  position: relative;
  background-color: #f5f7fa;
  padding: 20px;
  overflow-y: auto;
  height: 100%;
}

.sidebar {
  background-color: #f5f7fa;
  border-right: 1px solid #e6e6e6;
  display: flex;
  flex-direction: column;
  position: relative;
  transition: width 0.3s;
  overflow: hidden;
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
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sidebar-desc {
  font-size: 12px;
  color: #606266;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.site-menu {
  border-right: none;
  flex: 1;
}

.el-main {
  flex: 1;
  position: relative;
  background-color: #f5f7fa;
  padding: 20px;
  overflow-y: auto;
  height: 100%;
}

.toggle-sidebar {
  position: fixed;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  background-color: #fff;
  padding: 8px;
  border-radius: 0 4px 4px 0;
  box-shadow: 2px 0 8px rgba(0,0,0,0.15);
  cursor: pointer;
  z-index: 100;
  transition: left 0.3s;
}

.toggle-sidebar:hover {
  background-color: #f5f7fa;
}

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

/* 响应式布局 */
@media screen and (max-width: 768px) {
  .toggle-sidebar {
    left: 64px;
  }
  
  .el-main {
    padding: 10px;
  }
}
</style>