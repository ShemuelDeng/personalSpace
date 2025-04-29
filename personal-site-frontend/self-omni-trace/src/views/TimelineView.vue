<template>
  <div class="timeline-container">
    <div class="timeline-header">
      <h2>时间轴</h2>
      <el-button type="primary" size="small" @click="showAddDialog">添加大事件</el-button>
    </div>

    <div class="timeline-content">
      <el-tabs v-model="activeYear" type="card" @tab-click="handleYearChange" tab-position="left">
        <el-tab-pane v-for="year in years" :key="year" :label="year + '年'" :name="year">
          <!-- 时间轴组件 -->
          <el-timeline>
            <el-timeline-item
              v-for="event in filteredEvents"
              :key="event.id"
              :timestamp="formatDate(event.eventDate)"
              :color="event.color || '#409EFF'"
              placement="top"
            >
              <el-card>
                <h4 style="text-align: left;">{{ event.title }}</h4>
                <p style="text-align: left;">{{ event.content }}</p>
                <!-- 显示图片 -->
                <div class="event-images" v-if="event.images">
                  <el-image
                    v-for="(img, index) in event.images.split(',')"
                    :key="index"
                    :src="img"
                    :preview-src-list="event.images.split(',')"
                    fit="cover"
                    class="event-image"
                  />
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-tab-pane>
      </el-tabs>
    </div>

    <!-- 添加大事件对话框 -->
    <el-dialog title="添加大事件" :visible.sync="dialogVisible" width="50%">
      <el-form :model="eventForm" :rules="rules" ref="eventForm" label-width="100px">
        <el-form-item label="事件标题" prop="title">
          <el-input v-model="eventForm.title" placeholder="请输入事件标题"></el-input>
        </el-form-item>
        <el-form-item label="事件内容" prop="content">
          <el-input type="textarea" v-model="eventForm.content" :rows="4" placeholder="请输入事件内容"></el-input>
        </el-form-item>
        <el-form-item label="事件日期" prop="eventDate">
          <el-date-picker
            v-model="eventForm.eventDate"
            type="datetime"
            placeholder="选择日期时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="标记颜色">
          <el-color-picker v-model="eventForm.color"></el-color-picker>
        </el-form-item>
        <el-form-item label="上传图片">
          <el-upload
            list-type="picture-card"
            :http-request="handleUpload"
            :headers="uploadHeaders"
            :data="uploadData"
            multiple
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEvent">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getTimelineEventsApi, addTimelineEventApi } from '@/api/timeline'
import { uploadFileApi } from '@/api/file'

export default {
  name: 'TimelineView',

  data() {
    return {
      events: [], // 所有事件数据
      years: [], // 年份列表
      activeYear: '', // 当前选中的年份
      dialogVisible: false, // 对话框显示状态
      eventForm: {
        title: '',
        content: '',
        eventDate: new Date().toISOString().slice(0, 19).replace('T', ' '), // 默认为当前日期时间
        color: '#409EFF',
        images: ''
      },
      uploadedImages: [], // 用于存储上传的图片URL
      fileList: [], // 用于存储上传的文件列表
      rules: {
        title: [{ required: true, message: '请输入事件标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入事件内容', trigger: 'blur' }],
        eventDate: [{ required: true, message: '请选择事件日期', trigger: 'change' }]
      }
    }
  },
  computed: {
    baseApi() {
      return process.env.VUE_APP_BASE_API
    },
    // 根据当前选中的年份过滤事件
    filteredEvents() {
      if (!this.activeYear) return []
      return this.events.filter(event => {
        const eventYear = new Date(event.eventDate).getFullYear().toString()
        return eventYear === this.activeYear
      }).sort((a, b) => new Date(b.eventDate) - new Date(a.eventDate)) // 按日期降序排序
    }
  },
  created() {
    this.fetchTimelineEvents()
  },
  methods: {
    handleUpload(file) {
      const formData = new FormData()
      formData.append('file', file.file)
      return uploadFileApi(formData)
      .then(res => {
        this.handleUploadSuccess(res)
      })
      .catch(err => {
        console.log('上传图片出错11:', err)
        this.handleUploadError(err)
      })
    },
    // 获取时间轴事件数据
    async fetchTimelineEvents() {
      try {
        const res = await getTimelineEventsApi()
        if (res.code === 200 && res.data) {
          this.events = res.data.records || []
          this.processYears()
        } else {
          this.$message.error(res.message || '获取时间轴数据失败')
        }
      } catch (error) {
        console.error('获取时间轴数据出错:', error)
        this.$message.error('获取时间轴数据出错')
      }
    },
    
    // 处理年份数据
    processYears() {
      // 从事件中提取所有不同的年份
      const yearSet = new Set()
      this.events.forEach(event => {
        if (event.eventDate) {
          const year = new Date(event.eventDate).getFullYear().toString()
          yearSet.add(year)
        }
      })
      
      // 转换为数组并排序
      this.years = Array.from(yearSet).sort((a, b) => b - a) // 降序排列，最新年份在前
      
      // 如果有年份数据，默认选中第一个（最新的）年份
      if (this.years.length > 0) {
        this.activeYear = this.years[0]
      }
    },
    
    // 年份标签切换处理
    handleYearChange(tab) {
      this.activeYear = tab.name
    },
    
    // 格式化日期显示
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}:${date.getSeconds().toString().padStart(2, '0')}`
    },
    
    // 显示添加对话框
    showAddDialog() {
      this.dialogVisible = true
      const now = new Date()
      const formattedDate = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')} ${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}:${String(now.getSeconds()).padStart(2, '0')}`
      this.eventForm = {
        title: '',
        content: '',
        eventDate: formattedDate,
        color: '#409EFF',
        images: ''
      }
      this.uploadedImages = []
    },

    // 处理图片上传成功
    handleUploadSuccess(response) {
      console.log('图片上传成功', response)
      if (response.code === 200) {
        const fileUrl = response.data.url;
        // file.url = fileUrl // 设置文件的URL
        this.uploadedImages.push(fileUrl)
        this.eventForm.images = this.uploadedImages.join(',')
        this.$message.success('图片上传成功')
      } else {
        this.$message.error(response.message || '图片上传失败')
      }
    },

    // 处理图片上传失败
    handleUploadError() {
      this.$message.error('图片上传失败')
    },

    // 处理图片移除
    handleRemove(file) {
      const index = this.uploadedImages.indexOf(file.url)
      if (index !== -1) {
        this.uploadedImages.splice(index, 1)
        this.eventForm.images = this.uploadedImages.join(',')
      }
    },
    
    // 提交新事件
    submitEvent() {
      this.$refs.eventForm.validate(async valid => {
        if (valid) {
          try {
            const res = await addTimelineEventApi(this.eventForm)
            if (res.code === 200) {
              this.$message.success('添加成功')
              this.dialogVisible = false
              this.fetchTimelineEvents() // 重新获取数据
            } else {
              this.$message.error(res.message || '添加失败')
            }
          } catch (error) {
            console.error('添加事件出错:', error)
            this.$message.error('添加事件出错')
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
.timeline-container {
  padding: 20px;
}

.timeline-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.el-timeline-item {
  margin-bottom: 20px;
}

.el-card {
  border-radius: 4px;
}

.event-images {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.event-image {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 4px;
  cursor: pointer;
}

/* 覆盖Element Plus时间轴时间戳样式，使其左对齐 */
:deep(.el-timeline-item__timestamp) {
  text-align: left !important;
  display: block;
  margin-bottom: 8px;
  color: #909399;
  font-size: 13px;
}
</style>