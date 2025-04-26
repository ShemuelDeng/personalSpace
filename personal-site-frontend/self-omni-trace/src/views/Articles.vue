<template>
  <div class="articles-container">
    <!-- 多平台同步弹框 -->
    <el-dialog
      title="同步到第三方平台"
      :visible.sync="syncDialogVisible"
      width="500px"
      @close="handleSyncDialogClose">
      <div class="platform-list">
        <div
          v-for="platform in platforms"
          :key="platform.id"
          class="platform-item"
          :class="{ 'selected': selectedPlatforms.includes(platform.id) }"
          @click="togglePlatform(platform.id)">
          <img :src="platform.icon" :alt="platform.name" class="platform-icon">
          <span class="platform-name">{{ platform.name }}</span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="syncDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleConfirmSync" :loading="syncing">确 定</el-button>
      </span>
    </el-dialog>
    <div class="article-cards">
      <div 
        v-for="article in articles" 
        :key="article.id" 
        class="article-card"
        @click="viewArticleDetail(article.id)">
        <div class="article-cover">
          <el-image
            :src="article.coverImage || getRandomCover()"
            fit="cover">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </div>
        <div class="article-info">
          <h3 class="article-title">{{ article.title }}</h3>
          <p class="article-summary">{{ article.summary }}</p>
          <div class="article-meta">
            <div class="article-stats">
              <span><i class="el-icon-view"></i> {{ article.viewCount }}</span>
              <span><i class="el-icon-star-off"></i> {{ article.likeCount }}</span>
              <span><i class="el-icon-collection"></i> {{ article.saveCount }}</span>
            </div>
            <div class="article-date">{{ formatDate(article.createdAt) }}</div>
          </div>
          <div class="article-actions">
            <el-button
              size="mini"
              type="primary"
              @click.stop="handleSyncToThirdPlatform(article)">同步到第三方平台</el-button>
            <el-button
              size="mini"
              type="info"
              @click.stop="viewSyncRecords(article.id)">查看同步结果</el-button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 加载更多 -->
    <div class="load-more" v-if="hasMore">
      <p v-if="loading">加载中...</p>
      <el-button v-else @click="loadMore" type="text">加载更多</el-button>
    </div>
    <div class="no-more" v-else>
      <p>没有更多文章了</p>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import { API_ENDPOINTS } from '@/api/config'
import juejinIcon from '@/assets/platform-icons/juejin.svg'
import toutiaoIcon from '@/assets/platform-icons/toutiao.svg'
import zhihuIcon from '@/assets/platform-icons/zhihu.svg'
import csdnIcon from '@/assets/platform-icons/csdn.svg'

export default {
  name: 'ArticlesList',
  data() {
    return {
      articles: [],
      currentPage: 1,
      pageSize: 5, // 每次加载5篇文章
      total: 0,
      loading: false,
      hasMore: true,
      // 封面图片数组
      coverImages: [
        require('@/assets/cover1.png'),
        require('@/assets/cover2.png'),
        require('@/assets/cover3.png'),
        require('@/assets/cover4.png')
      ],
      // 是否已添加滚动监听
      scrollListenerAdded: false,
      // 同步相关数据
      syncDialogVisible: false,
      currentArticle: null,
      syncing: false,
      selectedPlatforms: [],
      platforms: [
        { id: 1, name: '稀土掘金', icon: juejinIcon },
        { id: 2, name: '今日头条', icon: toutiaoIcon },
        { id: 3, name: '知乎', icon: zhihuIcon },
        { id: 4, name: 'CSDN', icon: csdnIcon }
      ]
    }
  },
  created() {
    this.fetchArticles()
  },
  mounted() {
    // 添加滚动监听
    this.addScrollListener()
  },
  beforeDestroy() {
    // 移除滚动监听
    this.removeScrollListener()
  },
  methods: {
    // 添加滚动监听
    addScrollListener() {
      if (!this.scrollListenerAdded) {
        window.addEventListener('scroll', this.handleScroll)
        this.scrollListenerAdded = true
      }
    },
    // 移除滚动监听
    removeScrollListener() {
      window.removeEventListener('scroll', this.handleScroll)
      this.scrollListenerAdded = false
    },
    // 处理滚动事件
    handleScroll() {
      // 如果正在加载或没有更多数据，则不处理
      if (this.loading || !this.hasMore) return
      
      // 检查是否滚动到底部
      const scrollHeight = document.documentElement.scrollHeight
      const scrollTop = document.documentElement.scrollTop || document.body.scrollTop
      const clientHeight = document.documentElement.clientHeight
      
      // 当距离底部100px时加载更多
      if (scrollHeight - scrollTop - clientHeight < 100) {
        this.loadMore()
      }
    },
    // 获取随机封面图片
    getRandomCover() {
      const index = Math.floor(Math.random() * this.coverImages.length)
      return this.coverImages[index]
    },
    // 加载更多文章
    loadMore() {
      this.currentPage++
      this.fetchArticles(true)
    },
    // 获取文章列表
    async fetchArticles(append = false) {
      if (this.loading) return
      
      this.loading = true
      try {
        const response = await request.get(API_ENDPOINTS.ARTICLE.LIST, {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        })
        console.log(response)
        const newArticles = response.data.records || []
        this.total = response.data.total || 0
        
        // 如果是追加模式，则将新文章添加到现有列表中
        if (append) {
          this.articles = [...this.articles, ...newArticles]
        } else {
          this.articles = newArticles
        }
        
        // 判断是否还有更多文章
        this.hasMore = this.articles.length < this.total
      } catch (error) {
        console.error('获取文章列表失败:', error)
        // this.$message.error('获取文章列表失败')
      } finally {
        this.loading = false
      }
    },
    // 查看文章详情（在新标签页打开）
    viewArticleDetail(articleId) {
      this.$router.push(`/article/${articleId}`)
    },
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    },
    
    // 打开同步弹框
    handleSyncToThirdPlatform(article) {
      event.stopPropagation()
      this.currentArticle = article
      this.syncDialogVisible = true
    },

    // 切换平台选择状态
    togglePlatform(platformId) {
      const index = this.selectedPlatforms.indexOf(platformId)
      if (index === -1) {
        this.selectedPlatforms.push(platformId)
      } else {
        this.selectedPlatforms.splice(index, 1)
      }
    },

    // 关闭弹框时重置状态
    handleSyncDialogClose() {
      this.selectedPlatforms = []
      this.currentArticle = null
      this.syncing = false
    },

    // 查看同步记录
    viewSyncRecords(articleId) {
      this.$router.push(`/article-sync-records?articleId=${articleId}`)
    },

    // 确认同步到选中的平台
    async handleConfirmSync() {
      if (this.selectedPlatforms.length === 0) {
        this.$message.warning('请至少选择一个平台')
        return
      }

      this.syncing = true
      try {
        const response = await request.post(API_ENDPOINTS.ARTICLE.SYNC_THIRD_PLATFORM, {
          platformIds: this.selectedPlatforms,
          articleId: this.currentArticle.id
        })

 
        if (response.data.code === 200) {
          this.$message.success('文章已成功同步到选中平台')
          this.syncDialogVisible = false
        } else {
          this.$message.error(`同步失败: ${response.data.message || '未知错误'}`)
        }
      } catch (error) {
        console.error('同步到第三方平台失败:', error)
        this.$message.error('同步失败: ' + (error.response?.data?.message || error.message || '未知错误'))
      } finally {
        this.syncing = false
      }
    }
  }
}
</script>

<style scoped>
.articles-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.article-cards {
  display: grid;
  grid-template-columns: 1fr;
  gap: 20px;
  margin-bottom: 30px;
  max-width: 1000px;
  margin-left: auto;
  margin-right: auto;
}

.article-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  overflow: hidden;
  transition: transform 0.3s, box-shadow 0.3s;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  height: 220px;
}

.article-cover {
  width: 300px;
  height: 100%;
  flex-shrink: 0;
  order: 2;
}
.article-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
}

.article-cover {
  height: 180px;
  overflow: hidden;
}

.article-cover .el-image {
  width: 100%;
  height: 100%;
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  font-size: 30px;
}

.article-info {
  padding: 20px;
  flex: 1;
  display: flex;
  flex-direction: column;
  order: 1;
  text-align: left;
}

.article-title {
  margin: 0 0 10px 0;
  font-size: 18px;
  color: #303133;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.article-summary {
  color: #606266;
  font-size: 14px;
  line-height: 1.6;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
  flex: 1;
}

.article-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: auto;
  padding-top: 10px;
  border-top: 1px solid #ebeef5;
}

.article-date {
  color: #909399;
  font-size: 12px;
}

.article-stats {
  display: flex;
  gap: 10px;
}

.article-stats span {
  color: #909399;
  font-size: 12px;
}

.article-stats i {
  margin-right: 3px;
}

.article-actions {
  margin-top: 10px;
  text-align: right;
}

.load-more, .no-more {
  text-align: center;
  margin: 20px 0;
  color: #909399;
}

/* 平台选择弹框样式 */
.platform-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  padding: 10px;
}

.platform-item {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.platform-item:hover {
  border-color: #409eff;
  background-color: #f5f7fa;
}

.platform-item.selected {
  border-color: #409eff;
  background-color: #ecf5ff;
}

.platform-icon {
  width: 32px;
  height: 32px;
  margin-right: 10px;
}

.platform-name {
  font-size: 14px;
  color: #606266;
}

/* 响应式调整 */
@media screen and (max-width: 768px) {
  .article-cards {
    grid-template-columns: 1fr;
  }
  
  .article-card {
    flex-direction: column;
    height: auto;
  }

  .article-cover {
    width: 100%;
    height: 200px;
    order: 1;
  }

  .article-info {
    order: 2;
  }

  .platform-list {
    grid-template-columns: 1fr;
  }
}
</style>