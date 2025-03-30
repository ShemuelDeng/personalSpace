<template>
  <div class="articles-container">
    <!-- 左侧文章列表 -->
    <div class="article-list">
      <el-table
        :data="articles"
        style="width: 100%"
        @row-click="handleArticleClick">
        <el-table-column
          prop="coverImage"
          label="封面"
          width="180">
          <template slot-scope="scope">
            <el-image
              style="width: 160px; height: 90px"
              :src="scope.row.coverImage"
              fit="cover">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          width="180">
        </el-table-column>
        <el-table-column
          prop="summary"
          label="摘要">
        </el-table-column>
        <el-table-column
          label="统计信息"
          width="200">
          <template slot-scope="scope">
            <div class="article-stats">
              <span><i class="el-icon-view"></i> {{ scope.row.viewCount }}</span>
              <span><i class="el-icon-star-off"></i> {{ scope.row.likeCount }}</span>
              <span><i class="el-icon-collection"></i> {{ scope.row.saveCount }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          label="操作"
          width="120">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click.stop="handleSyncToCSDN(scope.row)">同步到CSDN</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
    
    <!-- 右侧文章内容 -->
    <div class="article-content" v-if="selectedArticle">
      <div class="article-header">
        <h1>{{ selectedArticle.title }}</h1>
        <div class="article-meta">
          <span>发布时间：{{ formatDate(selectedArticle.createdAt) }}</span>
          <span>更新时间：{{ formatDate(selectedArticle.updatedAt) }}</span>
        </div>
      </div>
      <mavon-editor
        v-model="selectedArticle.content"
        :subfield="false"
        :defaultOpen="'preview'"
        :toolbarsFlag="false"
        :boxShadow="false"
        class="md-editor"
      />
    </div>
    <div class="article-content empty" v-else>
      <el-empty description="请选择一篇文章查看"></el-empty>
    </div>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import axios from 'axios'
import { BASE_URL, API_ENDPOINTS } from '@/api/config'

export default {
  name: 'ArticlesList',
  components: {
    mavonEditor
  },
  data() {
    return {
      articles: [],
      selectedArticle: null,
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    this.fetchArticles()
  },
  methods: {
    async fetchArticles() {
      try {
        const response = await axios.get(`${BASE_URL}${API_ENDPOINTS.ARTICLE.LIST}`, {
          params: {
            page: this.currentPage,
            size: this.pageSize
          }
        })
        console.log(response)
        this.articles = response.data.data.records
        this.total = response.data.total
        console.log(this.articles)
      } catch (error) {
        console.error('获取文章列表失败:', error)
        this.$message.error('获取文章列表失败')
      }
    },
    handleArticleClick(row) {
      this.selectedArticle = row
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchArticles()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchArticles()
    },
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
    },
    
    async handleSyncToCSDN(article) {
      try {
        // 阻止事件冒泡，避免触发行点击事件
        event.stopPropagation()
        
        // 显示加载中提示
        const loading = this.$loading({
          lock: true,
          text: '正在同步到CSDN...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        
        // 调用同步接口
        const response = await axios.post(`${BASE_URL}${API_ENDPOINTS.ARTICLE.SYNC_CSDN}${article.id}`)
        
        // 关闭加载提示
        loading.close()
        
        // 根据返回结果显示不同提示
        if (response.data.code === 200) {
          this.$message.success('文章已成功同步到CSDN')
        } else {
          this.$message.error(`同步失败: ${response.data.message || '未知错误'}`)
        }
      } catch (error) {
        // 关闭可能存在的加载提示
        this.$loading().close()
        
        console.error('同步到CSDN失败:', error)
        this.$message.error('同步到CSDN失败: ' + (error.response?.data?.message || error.message || '未知错误'))
      }
    }
  }
}
</script>

<style scoped>
.articles-container {
  display: flex;
  height: 100%;
  gap: 20px;
  padding: 20px;
}

.article-list {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 600px;
  max-width: 800px;
}

.article-content {
  flex: 1;
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  overflow-y: auto;
}

.article-content.empty {
  display: flex;
  align-items: center;
  justify-content: center;
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
}

.article-stats {
  display: flex;
  gap: 15px;
}

.article-stats span {
  color: #909399;
}

.article-stats i {
  margin-right: 5px;
}

.pagination-container {
  margin-top: 20px;
  text-align: right;
}

.md-editor {
  height: calc(100% - 100px);
}
</style>