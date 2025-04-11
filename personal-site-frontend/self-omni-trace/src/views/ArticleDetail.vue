<template>
  <div class="article-detail-container">
    <div class="article-content" v-if="article">
      <div class="article-header">
        <h1>{{ article.title }}</h1>
        <div class="article-meta">
          <span>发布时间：{{ formatDate(article.createdAt) }}</span>
          <span>更新时间：{{ formatDate(article.updatedAt) }}</span>
          <div class="article-stats">
            <span><i class="el-icon-view"></i> {{ article.viewCount }}</span>
            <span><i class="el-icon-star-off"></i> {{ article.likeCount }}</span>
            <span><i class="el-icon-collection"></i> {{ article.saveCount }}</span>
          </div>
        </div>
      </div>
      <mavon-editor
        v-model="article.content"
        :subfield="false"
        :defaultOpen="'preview'"
        :toolbarsFlag="false"
        :boxShadow="false"
        class="md-editor"
      />
    </div>
    <div class="article-content empty" v-else>
      <el-empty description="文章加载中或不存在"></el-empty>
    </div>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import request from '@/utils/request'
import { API_ENDPOINTS } from '@/api/config'

export default {
  name: 'ArticleDetail',
  components: {
    mavonEditor
  },
  data() {
    return {
      article: null,
      loading: false
    }
  },
  created() {
    // 从路由参数获取文章ID
    const articleId = this.$route.params.id
    if (articleId) {
      this.fetchArticleDetail(articleId)
    }
  },
  methods: {
    async fetchArticleDetail(id) {
      this.loading = true
      try {
        const response = await request.get(`${API_ENDPOINTS.ARTICLE.DETAIL}${id}`)
        this.article = response.data
      } catch (error) {
        console.error('获取文章详情失败:', error)
        this.$message.error('获取文章详情失败')
      } finally {
        this.loading = false
      }
    },
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    }
  }
}
</script>

<style scoped>
.article-detail-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.article-content {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.article-content.empty {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 400px;
}

.article-header {
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #eee;
}

.article-header h1 {
  margin: 0 0 10px 0;
  font-size: 24px;
  color: #303133;
}

.article-meta {
  color: #909399;
  font-size: 14px;
}

.article-meta span {
  margin-right: 20px;
  display: inline-block;
}

.article-stats {
  margin-top: 10px;
  display: flex;
  gap: 15px;
}

.article-stats span {
  color: #909399;
}

.article-stats i {
  margin-right: 5px;
}

.md-editor {
  min-height: 500px;
}
</style>