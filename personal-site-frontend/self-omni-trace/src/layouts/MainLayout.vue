<template>
  <el-container style="height: 100vh">
    <!-- 左侧边栏 -->
    <el-aside
        :width="isCollapse ? '64px' : '12.5%'"
        style="background: #304156; transition: all 0.3s"
    >
      <!-- 个人信息区域 -->
      <div class="user-info">
        <el-avatar
            :size="60"
            src="https://example.com/avatar.png"
            style="margin-bottom: 10px"
        ></el-avatar>
        <div class="user-name">用户名</div>
        <div class="user-role">前端开发者</div>
      </div>

      <!-- 导航菜单 -->
      <el-menu
          :default-active="activeMenu"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
          style="border-right: none"
          :collapse="isCollapse"
      >
        <el-menu-item
            v-for="item in menuItems"
            :key="item.path"
            :index="item.path"
            @click="handleMenuClick(item)"
        >
          <i :class="item.icon"></i>
          <span slot="title">{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- 右侧主内容区 -->
    <el-container>
      <!-- 顶部图片区域 -->
      <el-header style="height: 270px; padding: 0">
        <div
            class="banner"
            style="height: 100%; background: url('https://example.com/banner.jpg') center/cover"
        ></div>
      </el-header>

      <!-- 路由视图容器 -->
      <el-main style="padding: 0; height: calc(100vh - 270px)">
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      isCollapse: false,
      activeMenu: '/',
      menuItems: [
        { path: '/', title: '首页', icon: 'el-icon-s-home' },
        { path: '/articles', title: '技术文章', icon: 'el-icon-document' },
        { path: '/workspace', title: '工作空间', icon: 'el-icon-s-operation' },
        { path: '/album', title: '个人相册', icon: 'el-icon-picture' },
        { path: '/notes', title: '灵感笔记', icon: 'el-icon-edit' },
        { path: '/milestones', title: '大事记', icon: 'el-icon-date' },
        { path: '/relax', title: '咕一下', icon: 'el-icon-coffee' }
      ]
    }
  },
  methods: {
    handleMenuClick(item) {
      this.$router.push(item.path)
      this.activeMenu = item.path
    }
  }
}
</script>

<style scoped>
.user-info {
  padding: 20px;
  text-align: center;
  color: #fff;
  border-bottom: 1px solid rgba(255,255,255,0.1);
}

.user-name {
  font-size: 16px;
  margin: 5px 0;
}

.user-role {
  font-size: 12px;
  color: #909399;
}

.el-menu {
  height: calc(100% - 160px);
  overflow-y: auto;
}

.el-header {
  background-color: #B3C0D1;
}

.el-main {
  background-color: #E9EEF3;
}
</style>