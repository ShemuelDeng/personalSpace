<template>
        <div class="profile-header">
          <div class="profile-banner"></div>
          <div class="profile-info">
            <el-avatar :size="100" src="https://avatars.githubusercontent.com/u/objectx" class="profile-avatar"></el-avatar>
            <h2 class="profile-name">ObjectX-不知名程序员1111</h2>
            <p class="profile-description">你好👋, 我是 ObjectX-不知名程序员，一个热爱生活和分享技术的前端工程师 & 图形编辑器开发者 & AI前瞻。我希望能够通过技术的博客，与大家分享我的生活态度，给你技术的学习，希望能给大家一些启发和帮助！你也可以在这些的社交链接里面了解更多。</p>
            
            <div class="social-links">
              <h3>社交账号</h3>
              <div class="links-container">
                <el-link href="https://github.com/objectx" target="_blank" type="primary">
                  <i class="el-icon-link"></i> Github
                </el-link>
                <el-link href="https://juejin.cn/user/objectx" target="_blank" type="success">
                  <i class="el-icon-link"></i> 掘金
                </el-link>
                <el-link href="https://zhihu.com/people/objectx" target="_blank" type="warning">
                  <i class="el-icon-link"></i> 知识库
                </el-link>
                <el-link href="https://space.bilibili.com/objectx" target="_blank" type="danger">
                  <i class="el-icon-video-camera"></i> bilibili
                </el-link>
                <el-link href="https://weibo.com/objectx" target="_blank" type="info">
                  <i class="el-icon-chat-dot-round"></i> 推特
                </el-link>
              </div>
            </div>

            <div class="site-stats">
              <h3>网站信息</h3>
              <div class="stats-container">
                <el-card shadow="hover">
                  <div class="stat-item">
                    <i class="el-icon-view"></i>
                    <span>喜欢本站</span>
                    <div class="stat-value">26</div>
                  </div>
                </el-card>
                <el-card shadow="hover">
                  <div class="stat-item">
                    <i class="el-icon-chat-line-round"></i>
                    <span>访问量</span>
                    <div class="stat-value">244</div>
                  </div>
                </el-card>
                <el-card shadow="hover">
                  <div class="stat-item">
                    <i class="el-icon-time"></i>
                    <span>运行时间</span>
                    <div class="stat-value">67天22时13分</div>
                  </div>
                </el-card>
                <el-card shadow="hover">
                  <div class="stat-item">
                    <i class="el-icon-star-on"></i>
                    <span>关注我</span>
                    <div class="stat-value">3</div>
                  </div>
                </el-card>
              </div>
            </div>

            <div class="education">
              <h3>教育经历</h3>
              <el-card class="education-card" shadow="hover">
                <div class="education-info">
                  <span class="school">某211大学</span>
                  <span class="major">计算机科学与技术</span>
                  <span class="degree">本科</span>
                  <span class="period">CET6</span>
                  <span class="year">2018 - 2022</span>
                </div>
              </el-card>
            </div>
          </div>
        </div>
</template>

<script>
export default {
  name: 'IndexRight',
  data() {
    return {
      
    }
  },
  methods: {
    
  },
  mounted() {
    
  }
}
</script>

<style scoped>
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