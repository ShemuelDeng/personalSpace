<template>
  <div class="article-sync-records">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>文章同步记录</span>
          <el-button type="primary" @click="openSettingDialog">同步工具设置</el-button>
        </div>
      </template>
      
      <el-table :data="records" v-loading="loading" border style="width: 100%">
        <el-table-column prop="articleTitle" label="文章标题" min-width="200">
          <template #default="{ row }">
            <el-tooltip :content="row.articleTitle" placement="top" :hide-after="2000">
              <span class="ellipsis">{{ row.articleTitle }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        
        <el-table-column prop="platformType" label="同步平台" width="120">
          <template #default="{ row }">
            <el-tag :type="getPlatformTagType(2)">
              {{ row.platformName }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="syncResult" label="同步结果" width="100">
          <template #default="{ row }">
            <el-tag :type="row.syncResult === 1 ? 'success' : 'danger'">
              {{ row.syncResult === 1 ? '成功' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="syncTime" label="同步时间" width="180" />
        
        <el-table-column prop="syncFailReason" label="失败原因" min-width="200">
          <template #default="{ row }">
            <span v-if="row.syncResult === 0" class="error-reason">
              {{ row.syncFailReason || '未知错误' }}
            </span>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination-container">
        <el-pagination
          v-model="currentPage"
          :page-size="pageSize"
          @update:page-size="val => pageSize = val"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 同步工具设置对话框 -->
    <el-dialog
            :visible.sync="dialogVisible"
      title="同步工具设置"
      width="500px"
    >
      <el-form :model="settingForm" :rules="rules" ref="settingFormRef" label-width="80px">
        <el-form-item label="平台" prop="platformId">
          <el-select v-model="settingForm.platformId" placeholder="请选择平台">
            <el-option
              v-for="(name, id) in platforms"
              :key="id"
              :label="name"
              :value="Number(id)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="Cookie" prop="cookie">
          <el-input
            v-model="settingForm.cookie"
            type="textarea"
            :rows="4"
            placeholder="请输入Cookie信息"
          />
        </el-form-item>
        <el-form-item label="Header" prop="header">
          <el-input
            v-model="settingForm.header"
            type="textarea"
            :rows="4"
            placeholder="请输入Header信息"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit" :loading="submitting">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
import { BASE_URL, API_ENDPOINTS } from '@/api/config'

export default {
  name: 'ArticleSyncRecords',
  data() {
    return {
      loading: false,
      records: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      submitting: false,
      settingFormRef: null,
      settingForm: {
        platformId: undefined,
        cookie: '',
        header: ''
      },
      platforms: {
        1: '稀土掘金',
        2: '今日头条',
        3: '知乎',
        4: 'CSDN'
      },
      rules: {
        platformId: [{ required: true, message: '请选择平台', trigger: 'change' }],
        cookie: [{ required: false, message: '请输入Cookie信息', trigger: 'blur' }],
        header: [{ required: false, message: '请输入Header信息', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.fetchRecords()
  },
  methods: {
    async fetchRecords() {
      this.loading = true
      try {
        // 从路由参数中获取文章ID
        const articleId = this.$route.query.articleId
        const response = await axios.get(`${BASE_URL}${API_ENDPOINTS.ARTICLE_SYNC.LIST}`, {
          params: {
            current: this.currentPage,
            size: this.pageSize,
            articleId: articleId
          }
        })
        const { records, total } = response.data.data
        this.records = records
        this.total = total
      } catch (error) {
        console.error('获取同步记录失败:', error)
        this.$message.error('获取同步记录失败')
      } finally {
        this.loading = false
      }
    },
    getPlatformName(type) {
      const platforms = {
        1: '稀土掘金',
        2: '今日头条',
        3: '知乎',
        4: 'CSDN'
      }
      return platforms[type] || '未知平台'
    },
    getPlatformTagType(type) {
      const types = {
        1: '',
        2: 'warning',
        3: 'info',
        4: 'success'
      }
      return types[type] || 'info'
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.fetchRecords()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchRecords()
    },
    
    // 新增的方法
    openSettingDialog() {
      this.dialogVisible = true
      this.settingForm = {
        platformId: undefined,
        cookie: '',
        header: ''
      }
    },
    async handleSubmit() {

      
      try {
        //await this.settingFormRef.validate()
        this.submitting = true
        
        await axios.put(`${BASE_URL}/api/third-party-platform-auth-info/update`, this.settingForm)
        this.$message.success('配置更新成功')
        this.dialogVisible = false
      } catch (error) {
        if (error.response) {
          this.$message.error(error.response.data.message || '配置更新失败')
        } else {
          this.$message.error('配置更新失败')
        }
      } finally {
        this.submitting = false
      }
    }
  }
}
</script>

<style scoped>
.article-sync-records {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.ellipsis {
  display: inline-block;
  width: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.error-reason {
  color: #f56c6c;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>