<template>
  <div class="resources-container">
    <!-- 页面标题和搜索栏 -->
    <div class="page-header">
      <div class="header-left">
        <h1 class="page-title">资源中心</h1>
      </div>
      <div class="header-right">
        <div class="search-bar">
          <el-input
            v-model="params.name"
            placeholder="搜索你感兴趣的资源,按回车键搜索..."
            prefix-icon="el-icon-search"
            clearable
            @keyup.enter.native="handleSearch"
          />
        </div>
        <el-button type="primary" icon="el-icon-upload" @click="showUploadDialog">
          上传资源
        </el-button>
      </div>
    </div>

    <!-- 分类内容 -->
    <div v-for="category in categories" :key="category.id" class="category-section" v-if="category.resources?.length > 0">
      <div class="category-header">
        <div class="category-title">
          <svg-icon :icon-class="category.remark"></svg-icon>
          <h2>{{ category.label }}</h2>
        </div>
      </div>
      
      <div class="resources-grid">
        <div
          v-for="resource in category.resources"
          :key="resource.id"
          class="resource-card"
          @click="handleResourceClick(resource)"
        >
          <div class="resource-content">
            <div class="resource-icon">
              <svg-icon :icon-class="category.remark"></svg-icon>
            </div>
            <div class="resource-info">
              <div class="resource-name text-ellipsis" :title="resource.name">{{ resource.name }}</div>
              <div class="resource-meta">
                <el-tag size="mini" :type="!resource.isFree ? 'success' : 'warning'" effect="plain">
                  {{ !resource.isFree ? '免费' : '付费' }}
                </el-tag>
                
              </div>
            </div>
          </div>
          <div class="resource-footer">
            <div class="resource-uploader text-ellipsis">
              <img :src="resource.avatar" class="uploader-avatar">
              <span class="uploader-name">{{ resource.nickname }}</span>
            </div>
            <div class="resource-stats">
              <span class="upload-time">
               <i class="el-icon-time"></i> {{ formatTime(resource.createTime) }}
             </span>
              <span class="download-count" title="下载次数">
                <i class="el-icon-download"></i> {{ resource.downloads || 0 }}
              </span>
              <span class="view-count" title="浏览次数">
                <i class="el-icon-view"></i> {{ resource.views || 0 }}
              </span>
            </div>
          </div>
        </div>
      </div>

      <!-- 每个分类的分页 -->
      <div class="pagination-box" v-if="category.total > params.pageSize">
        <el-pagination
          :current-page.sync="categoryPages[category.id]"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          :total="category.total"
          @current-change="(page) => handlePageChange(page, category)"
        />
      </div>
    </div>

    <Add :visible.sync="uploadDialogVisible" :categories="categories" />

    <!-- 资源详情对话框 -->
    <el-dialog
      title="资源详情"
      :visible.sync="detailDialogVisible"
      width="600px"
      class="resource-detail-dialog"
      top="5vh"
      :close-on-click-modal="false"
    >
      <div class="resource-detail" v-if="currentResource">
        <div class="detail-header">
          <div class="resource-icon">
            <svg-icon :icon-class="currentResource.categoryIcon"></svg-icon>
          </div>
          <h3 class="resource-title">{{ currentResource.name }}</h3>
          <el-tag size="small" :type="!currentResource.isFree ? 'success' : 'warning'" effect="plain">
            {{ !currentResource.isFree ? '免费' : '付费' }}
          </el-tag>
        </div>

        <el-descriptions  border>
          <el-descriptions-item label="上传者">
            <div class="uploader-info text-ellipsis">
              <img :src="currentResource.avatar" class="uploader-avatar">
              <span>{{ currentResource.nickname }}</span>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="上传时间">{{ formatTime(currentResource.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="下载次数">{{ currentResource.downloads || 0 }}</el-descriptions-item>
          <el-descriptions-item label="资源描述">{{ currentResource.description || '暂无描述' }}</el-descriptions-item>
      </el-descriptions>
        
        <!-- 添加获取链接部分 -->
        <div class="get-link-section" v-if="!showVerifyCode">
          <el-button type="primary" @click="handleGetLink">
            获取下载链接
          </el-button>
          <p class="tip">点击获取链接后需要扫码验证</p>
        </div>

        <!-- 验证码部分 -->
        <div class="verify-section" v-if="showVerifyCode">
          <div class="qr-code">
            <img v-lazy="'https://img.shiyit.com/qrcode.jpg'" :key="'https://img.shiyit.com/qrcode.jpg'"  alt="扫码获取验证码">
            <p class="scan-tip">请使用微信扫码关注公众号发送 <span class="code-tip">验证码</span> 获取验证码</p>
          </div>
          <el-form :model="verifyForm" class="verify-form">
            <el-form-item>
              <el-input
                v-model="verifyForm.code"
                placeholder="请输入验证码"
                maxlength="6"
              >
                <template slot="append">
                  <el-button type="primary" @click="handleVerify" :loading="verifying">
                    验证
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-form>
        </div>

        <!-- 下载链接部分 -->
        <div class="download-link-section" v-if="currentResource.panPath">
          <div class="link-item">
            <span class="label">网盘链接:</span>
            <el-input v-model="currentResource.panPath" readonly>
              <el-button slot="append" @click="copyText(currentResource.panPath)">
                复制
              </el-button>
            </el-input>
          </div>
          <div class="link-item" v-if="currentResource.panCode">
            <span class="label">提取码:</span>
            <el-input v-model="currentResource.panCode" readonly>
              <el-button slot="append" @click="copyText(currentResource.panCode)">
                复制
              </el-button>
            </el-input>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCloseDetail">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Add from './components/add.vue';
import { getDictDataApi } from '@/api/dict';
import { getResourcesApi } from '@/api/resources';
import { formatTime } from '@/utils/time';
import { verifyCodeApi } from '@/api/resources';
export default {
  name: 'ResourcesView',
  components: {
    Add
  },
  data() {
    return {
      categoryPages: {},
      categories: [],
      uploadDialogVisible: false,
      params: {
        pageNum: 1,
        pageSize: 8,
        category: '',
        name: ''
      },
      detailDialogVisible: false,
      currentResource: null,
      showVerifyCode: false,
      verifying: false,
      verifyForm: {
        code: ''
      }
    }
  },
  created() {
    this.getCategory()

    // 初始化每个分类的页码
    this.categories.forEach(category => {
      this.$set(this.categoryPages, category.id, 1)
    })
  },
  methods: {
    /**
     * 获取资源
     */
     getCategory() {
      //1. 首先获取资源分类
      getDictDataApi('sys_resource_category').then(res => {
        this.categories = res.data
        this.getResources()
      })
    
    },
    /**
     * 获取资源
     */
     getResources() {
      this.categories.forEach(category => {
          this.params.category = category.value
          getResourcesApi(this.params).then(res => {
            this.$set(category, 'resources', res.data.records)
            this.$set(category, 'total', res.data.total)
          })
        })
    },
    /**
     * 格式化时间
     */
    formatTime(time) {
      return formatTime(time)
    },
    /**
     * 搜索
     */
    handleSearch() {
      this.params.pageNum = 1
      this.getResources()
    },
    /**
     * 分页
     */
    handlePageChange(page, category) {
       this.$set(this.categoryPages, category.id, page)
      //获取资源
      this.params.category = category.value
      this.params.pageNum = page
      getResourcesApi(this.params).then(res => {
        this.$set(category, 'resources', res.data.records)
        this.$set(category, 'total', res.data.total)
      })
    },
    /**
     * 处理资源点击事件
     */
    handleResourceClick(resource) {
      this.currentResource = {
        ...resource,
        categoryIcon: this.categories.find(c => c.value === resource.category)?.remark
      }
      this.detailDialogVisible = true
    },

    /**
     * 显示上传对话框
     */
    showUploadDialog() {
      //需要登录
      if(!this.$store.state.userInfo) {
        this.$message.warning('请先登录')
        return
      }
      this.uploadDialogVisible = true
    },

    /**
     * 获取下载链接
     */
    handleGetLink() {
      if(!this.$store.state.userInfo) {
        this.$message.warning('请先登录')
        return
      }
      this.showVerifyCode = true
    },

    /**
     * 验证码验证
     */
    handleVerify() {
      if(!this.verifyForm.code) {
        this.$message.warning('请输入验证码')
        return
      }
      this.verifying = true
      this.verifyForm.id = this.currentResource.id
      verifyCodeApi(this.verifyForm).then(res => {
        this.currentResource.panPath = res.data.panPath
        this.currentResource.panCode = res.data.panCode
        this.verifying = false
      }).catch(err => {
        this.$message.error(err.message)
      }).finally(() => {
        this.verifying = false
      })
    },

    /**
     * 复制文本
     */
    copyText(text) {
      navigator.clipboard.writeText(text).then(() => {
        this.$message.success('复制成功')
      }).catch(() => {
        this.$message.error('复制失败')
      })
    },

    /**
     * 关闭详情对话框
     */
    handleCloseDetail() {
      this.detailDialogVisible = false
      this.showVerifyCode = false
      this.qrCodeUrl = ''
      this.verifyForm.code = ''
      this.downloadInfo = {
        link: '',
        code: ''
      }
    }
  }
}
</script>

<style scoped lang="scss">
$text-color: #2c3e50;

.resources-container {
  padding: 30px;
  max-width: 1200px;
  margin: 0 auto;
  min-height: calc(100vh - 60px);
}

.page-header {
  margin-bottom: 40px;
  text-align: left;
  background: var(--resources-bg);
  padding: 60px;
  border-radius: 24px;
  position: relative;
  overflow: hidden;
  display: grid;
  grid-template-columns: 0.8fr 1.2fr;
  gap: 40px;
  align-items: center;
  box-shadow: 
    inset 0 0 60px rgba(64, 158, 255, 0.06),
    0 10px 30px -10px rgba(64, 158, 255, 0.05);

  &::before {
    content: '';
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: 
      radial-gradient(circle at 20% 30%, rgba(64, 158, 255, 0.08) 0%, transparent 50%),
      radial-gradient(circle at 80% 70%, rgba(83, 82, 237, 0.08) 0%, transparent 50%);
    animation: rotate 60s linear infinite;
  }

  &::after {
    content: '';
    position: absolute;
    inset: 0;
    background: 
      linear-gradient(120deg, rgba(255, 255, 255, 0.6) 0%, transparent 50%),
      linear-gradient(-120deg, rgba(255, 255, 255, 0.4) 0%, transparent 50%);
    pointer-events: none;
  }

  .header-left {
    position: relative;
    z-index: 1;
  }

  .header-right {
    display: flex;
    gap: 20px;
    align-items: center;
    position: relative;
    z-index: 1;
    width: 100%;

    .search-bar {
      flex: 1;
    }

    .el-button {
      flex-shrink: 0;
      height: 56px;
      padding: 0 30px;
      font-size: 16px;
      border-radius: 16px;
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-2px);
      }
    }
  }

  .search-bar {
    max-width: none;
    margin: 0;
    position: relative;
    z-index: 1;
    filter: drop-shadow(0 8px 16px rgba(0, 0, 0, 0.04));

    :deep(.el-input__inner) {
      height: 56px;
      border-radius: 16px;
    }

    :deep(.el-input-group__append) {
      background: rgba(255, 255, 255, 0.9);
      backdrop-filter: blur(10px);
      border-color: rgba(64, 158, 255, 0.1);
      padding: 0 20px;
      font-size: 15px;
    }

    :deep(.el-select .el-input__inner) {
      border: none;
      background: transparent;
      height: auto;
      padding: 0;
    }
  }
}

.page-title {
  font-size: 42px;
  line-height: 1.2;
  background: linear-gradient(120deg, #2b5dff 0%, #5352ed 50%, #2b5dff 100%);
  background-size: 200% auto;
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  margin-bottom: 20px;
  font-weight: 800;
  position: relative;
  z-index: 1;
  letter-spacing: -0.5px;
  text-shadow: 0 2px 15px rgba(83, 82, 237, 0.15);
  animation: shine 8s linear infinite;

  &::after {
    content: '发现优质资源';
    display: block;
    font-size: 16px;
    font-weight: 400;
    color: #666;
    margin-top: 12px;
    background: none;
    -webkit-text-fill-color: initial;
    text-shadow: none;
  }
}

.category-section {
  border-radius: 12px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  background: var(--card-bg);

  .category-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;

    .category-title {
      display: flex;
      align-items: center;
      gap: 10px;
      background: linear-gradient(135deg, rgba(64, 158, 255, 0.12), rgba(83, 82, 237, 0.12));
      padding: 12px 20px;
      border-radius: 8px;
      position: relative;
      overflow: hidden;
      border: 1px solid rgba(64, 158, 255, 0.1);

      &::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: linear-gradient(45deg, rgba(255, 255, 255, 0.1), transparent);
        pointer-events: none;
      }

      i {
        font-size: 20px;
        color: $primary;
      }

      h2 {
        font-size: 18px;
        font-weight: 600;
        color: var(--text-primary);
        margin: 0;
      }
    }

  }

  .resources-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
    gap: 20px;

    .resource-card {
      background: var(--card-bg);
      border-radius: 8px;
      transition: all 0.3s ease;
      cursor: pointer;
      border: 1px solid var(--border-color);
      display: flex;
      flex-direction: column;
      color: var(--text-primary);
      &:hover {
        transform: translateY(-3px);
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        border-color: $primary;
      }

      .resource-content {
        padding: 16px;
        flex: 1;
      }

      .resource-icon {
        text-align: center;
        margin-bottom: 12px;
        svg{
          width: 50px;
          height: 50px;
        }
      }

      .resource-info {
        text-align: center;

        .resource-name {
          font-size: 14px;
          margin-bottom: 8px;
          font-weight: 500;
        }

        .resource-meta {
          display: flex;
          justify-content: center;
          align-items: center;
          gap: 8px;
        }
      }

      .resource-footer {
        padding: 12px 16px;
        border-top: 1px solid var(--border-color);
        display: flex;
        justify-content: space-between;
        align-items: center;

        .resource-uploader {
          display: flex;
          align-items: center;
          gap: 6px;
          max-width: 90px;

          .uploader-name {
            font-size: 12px;
          }
        }

        .resource-stats {
          display: flex;
          gap: 12px;
          font-size: 12px;

          .download-count,
          .view-count,.upload-time {
            display: flex;
            align-items: center;
            gap: 4px;
          }
        }
      }
    }
  }
}

.text-ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}


@keyframes shine {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}

@media (max-width: 1200px) {
  .page-header {
    grid-template-columns: 1fr;
    gap: 30px;
    padding: 40px 30px;
  }
}

@media (max-width: 768px) {
  .page-header {
    padding: 30px 20px;
    
    .header-right {
      flex-direction: column;
      gap: 15px;
      
      .el-button {
        width: 100%;
      }
    }
  }
  
  .page-title {
    font-size: 32px;
  }
}

.resource-detail-dialog {
  :deep(.el-dialog__body) {
    padding: 30px;
  }
}

.resource-detail {
  .detail-header {
    text-align: center;
    margin-bottom: 30px;
    
    .resource-icon {
      margin-bottom: 15px;
      
      svg {
        width: 60px;
        height: 60px;
      }
    }
    
    .resource-title {
      font-size: 20px;
      margin: 0 0 10px;
      color: var(--text-primary);
    }
  }
  .uploader-info {
    display: flex !important;
    align-items: center !important;
    gap: $spacing-base !important;
    max-width: 140px;
  }
}

.get-link-section {
  text-align: center;
  margin-top: 20px;
  padding: 20px;
  border-top: 1px solid var(--border-color);
  
  .tip {
    margin-top: 10px;
    color: #909399;
    font-size: 12px;
  }
}

.verify-section {
  text-align: center;
  margin-top: 20px;
  padding: 20px;
  border-top: 1px solid var(--border-color);
  
  .qr-code {
    margin-bottom: 20px;
    
    img {
      width: 200px;
      height: 200px;
      margin-bottom: 10px;
    }
    
    .scan-tip {
      color: #909399;
      font-size: 14px;
      .code-tip {
        color: $primary;
      }
    }
  }
  
  .verify-form {
    max-width: 300px;
    margin: 0 auto;
  }
}

.download-link-section {
  margin-top: 20px;
  padding: 20px;
  border-top: 1px solid var(--border-color);
  
  .link-item {
    margin-bottom: 15px;
    
    .label {
      display: block;
      margin-bottom: 5px;
      color: #909399;
    }
    
    :deep(.el-input-group__append) {
      cursor: pointer;
    }
  }
}
.uploader-avatar {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  object-fit: cover;
}
</style>