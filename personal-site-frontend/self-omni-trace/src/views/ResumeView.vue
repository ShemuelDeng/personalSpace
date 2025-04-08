<template>
  <div class="resume-container">
    <el-row :gutter="20" class="full-height">
      <!-- 左侧编辑区域 -->
      <el-col :span="12" class="edit-section">
        <el-card class="edit-card">
          <template #header>
            <div class="card-header">
              <span>个人简历信息编辑</span>
            </div>
          </template>
          
          <el-form :model="resumeForm" label-position="top">
            <!-- 基本信息 -->
            <el-divider content-position="left">基本信息</el-divider>
            <el-form-item label="姓名">
              <el-input v-model="resumeForm.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="年龄">
                  <el-input-number v-model="resumeForm.age" :min="1" controls-position="right"></el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="工作年限">
                  <el-input-number v-model="resumeForm.experience" :min="0" controls-position="right"></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="联系方式">
              <el-input v-model="resumeForm.contact" placeholder="请输入联系方式">
                <i slot="prefix" class="el-icon-phone"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="所在地">
              <el-input v-model="resumeForm.location" placeholder="请输入所在地">
                <i slot="prefix" class="el-icon-location"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="出生年月">
              <el-date-picker
                v-model="resumeForm.birthday"
                type="month"
                placeholder="请选择出生年月"
                format="YYYY-MM"
                value-format="YYYY-MM"
              >
                <i slot="prefix" class="el-icon-date"></i>
              </el-date-picker>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="resumeForm.email" placeholder="请输入邮箱地址">
                <i slot="prefix" class="el-icon-message"></i>
              </el-input>
            </el-form-item>
            <el-form-item label="个人网站">
              <div v-for="(website, index) in resumeForm.websites" :key="index" class="website-item">
                <el-input v-model="website.name" placeholder="请输入网站名称" style="margin-bottom: 10px">
                  <i slot="prefix" class="el-icon-document"></i>
                </el-input>
                <el-input v-model="website.url" placeholder="请输入个人网站地址">
                  <i slot="prefix" class="el-icon-link"></i>
                  <template #append>
                    <el-select v-model="website.icon" placeholder="选择图标" style="width: 100px">
                      <el-option label="链接" value="Link">
                        <i class="el-icon-link"></i>
                      </el-option>
                      <el-option label="文档" value="Document">
                        <i class="el-icon-document"></i>
                      </el-option>
                      <el-option label="GitHub" value="Platform">
                        <i class="el-icon-platform-eleme"></i>
                      </el-option>
                    </el-select>
                    <el-button type="danger" @click="removeWebsite(index)">
                      <i class="el-icon-delete"></i>
                    </el-button>
                  </template>
                </el-input>
              </div>
              <el-button type="primary" plain @click="addWebsite" style="margin-top: 10px;">
                <i class="el-icon-plus"></i>
                添加网站
              </el-button>
            </el-form-item>
            
            <!-- 工作经历 -->
            <el-divider content-position="left">工作经历</el-divider>
            <div v-for="(work, index) in resumeForm.workExperience" :key="index" class="experience-item">
              <div class="item-header">
                <h4>工作经历 #{{ index + 1 }}</h4>
                <el-button type="danger" size="small" icon="el-icon-delete" circle @click="removeWork(index)"></el-button>
              </div>
              <el-form-item label="公司名称">
                <el-input v-model="work.company" placeholder="请输入公司名称"></el-input>
              </el-form-item>
              <el-form-item label="职位">
                <el-input v-model="work.position" placeholder="请输入职位"></el-input>
              </el-form-item>
              <el-form-item label="工作时间">
                <el-date-picker
                  v-model="work.period"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="工作描述">
                <el-input type="textarea" v-model="work.description" rows="3" placeholder="请输入工作描述"></el-input>
              </el-form-item>
            </div>
            <el-button type="primary" plain icon="el-icon-plus" @click="addWork">添加工作经历</el-button>
            
            <!-- 教育经历 -->
            <el-divider content-position="left">教育经历</el-divider>
            <div v-for="(edu, index) in resumeForm.education" :key="index" class="experience-item">
              <div class="item-header">
                <h4>教育经历 #{{ index + 1 }}</h4>
                <el-button type="danger" size="small" icon="el-icon-delete" circle @click="removeEducation(index)"></el-button>
              </div>
              <el-form-item label="学校名称">
                <el-input v-model="edu.school" placeholder="请输入学校名称"></el-input>
              </el-form-item>
              <el-form-item label="专业">
                <el-input v-model="edu.major" placeholder="请输入专业"></el-input>
              </el-form-item>
              <el-form-item label="学习时间">
                <el-date-picker
                  v-model="edu.period"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
                </el-date-picker>
              </el-form-item>
            </div>
            <el-button type="primary" plain icon="el-icon-plus" @click="addEducation">添加教育经历</el-button>
          </el-form>
        </el-card>
      </el-col>
      
      <!-- 右侧预览区域 -->
      <el-col :span="12" class="preview-section">
        <el-card class="preview-card">
          <template #header>
            <div class="card-header">
              <span>简历预览</span>
            </div>
          </template>
          
          <div class="resume-preview">
            <h1 class="preview-name">{{ resumeForm.name || '姓名' }}</h1>
            
            <div class="preview-basic-info">
              <span v-if="resumeForm.age"><i class="el-icon-user"></i> {{ resumeForm.age }}岁</span>
              <span v-if="resumeForm.experience"><i class="el-icon-time"></i> {{ resumeForm.experience }}年工作经验</span>
              <span v-if="resumeForm.location"><i class="el-icon-location"></i> {{ resumeForm.location }}</span>
              <span v-if="resumeForm.birthday"><i class="el-icon-date"></i> {{ formatBirthday(resumeForm.birthday) }}</span>
              <span v-if="resumeForm.contact"><i class="el-icon-phone"></i> {{ resumeForm.contact }}</span>
              <span v-if="resumeForm.email"><i class="el-icon-message"></i> {{ resumeForm.email }}</span>
              <template v-if="resumeForm.websites.length">
                <span v-for="(website, index) in resumeForm.websites" :key="index">
                  <a :href="website.url" target="_blank">
                    <i :class="'el-icon-' + website.icon.toLowerCase()"></i>
                    {{ website.name || '个人网站' + (resumeForm.websites.length > 1 ? (index + 1) : '') }}
                  </a>
                </span>
              </template>
            </div>
            
            <div class="preview-section" v-if="resumeForm.workExperience.length">
              <h2>工作经历</h2>
              <div v-for="(work, index) in resumeForm.workExperience" :key="index" class="preview-item">
                <div class="preview-item-header">
                  <h3>{{ work.company }}</h3>
                  <span>{{ work.position }}</span>
                  <span v-if="work.period && work.period[0] && work.period[1]">
                    {{ formatDate(work.period[0]) }} - {{ formatDate(work.period[1]) }}
                  </span>
                </div>
                <p class="preview-item-desc">{{ work.description }}</p>
              </div>
            </div>
            
            <div class="preview-section" v-if="resumeForm.education.length">
              <h2>教育经历</h2>
              <div v-for="(edu, index) in resumeForm.education" :key="index" class="preview-item">
                <div class="preview-item-header">
                  <h3>{{ edu.school }}</h3>
                  <span>{{ edu.major }}</span>
                  <span v-if="edu.period && edu.period[0] && edu.period[1]">
                    {{ formatDate(edu.period[0]) }} - {{ formatDate(edu.period[1]) }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'ResumeView',

  data() {
    return {
      resumeForm: {
        name: '',
        age: null,
        experience: null,
        contact: '',
        location: '',
        birthday: '',
        email: '',
        websites: [], // [{name: '', url: '', icon: ''}]
        workExperience: [],
        education: []
      }
    }
  },
  methods: {
    addWebsite() {
      this.resumeForm.websites.push({ name: '', url: '', icon: 'Link' })
    },
    removeWebsite(index) {
      this.resumeForm.websites.splice(index, 1)
    },
    addWork() {
      this.resumeForm.workExperience.push({
        company: '',
        position: '',
        period: [],
        description: ''
      })
    },
    removeWork(index) {
      this.resumeForm.workExperience.splice(index, 1)
    },
    addEducation() {
      this.resumeForm.education.push({
        school: '',
        major: '',
        period: []
      })
    },
    removeEducation(index) {
      this.resumeForm.education.splice(index, 1)
    },
    formatDate(date) {
      if (!date) return ''
      const d = new Date(date)
      return `${d.getFullYear()}.${String(d.getMonth() + 1).padStart(2, '0')}`
    },
    formatBirthday(dateStr) {
      if (!dateStr) return ''
      // 处理YYYY-MM格式的字符串
      const parts = dateStr.split('-')
      if (parts.length === 2) {
        return `${parts[0]}.${parts[1]}`
      }
      return dateStr
    }
  }
}
</script>

<style scoped>
.resume-container {
  padding: 20px;
}

.full-height {
  height: 100%;
}

.edit-section, .preview-section {
  height: 100%;
}

.edit-card, .preview-card {
  height: 100%;
  overflow-y: auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.experience-item {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.item-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.item-header h4 {
  margin: 0;
}

/* 预览样式 */
.resume-preview {
  padding: 20px;
}

.preview-name {
  text-align: center;
  margin-bottom: 20px;
  color: #303133;
}

.preview-basic-info {
  text-align: center;
  margin-bottom: 30px;
}

.preview-basic-info span {
  margin: 0 10px;
  color: #606266;
}

.preview-section {
  margin-bottom: 30px;
}

.preview-section h2 {
  color: #303133;
  border-bottom: 2px solid #409EFF;
  padding-bottom: 10px;
  margin-bottom: 20px;
}

.preview-item {
  margin-bottom: 20px;
}

.preview-item-header {
  margin-bottom: 10px;
}

.preview-item-header h3 {
  margin: 0;
  color: #303133;
}

.preview-item-header span {
  color: #606266;
  margin-right: 15px;
}

.preview-item-desc {
  color: #606266;
  line-height: 1.6;
  margin: 10px 0;
}
</style>