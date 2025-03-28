<template>
  <div class="editor-container" :class="{ 'fullscreen': isFullScreen }">
    <div class="editor-header">
      <div class="editor-actions">
        <el-button 
          type="primary" 
          size="small" 
          icon="el-icon-document-copy" 
          @click="copyAsHtml"
          :loading="copying">
          复制为HTML
        </el-button>
        <el-button
          type="text"
          size="small"
          icon="el-icon-full-screen"
          @click="toggleFullScreen">
          {{ isFullScreen ? '退出全屏' : '全屏编辑' }}
        </el-button>
        <el-button
          type="primary"
          size="small"
          icon="el-icon-upload"
          @click="showPublishDialog">
          发布文章
        </el-button>
      </div>
    </div>
    
    <div class="editor-content">
      <!-- Markdown编辑器 -->
      <div v-show="editorType === 'markdown'" class="markdown-editor">
        <mavon-editor
          v-model="markdownContent"
          @change="updatePreview"
          :toolbars="markdownToolbars"
          :boxShadow="false"
          :subfield="true"
          :defaultOpen="'preview'"
          :style="{ height: '100%' }"
        />
      </div>
      
      <!-- 富文本编辑器 -->
      <div v-show="editorType === 'richtext'" class="richtext-editor" ref="richtextEditor"></div>
    </div>
    
    <!-- 复制成功提示 -->
    <el-dialog
      title="复制成功"
      :visible.sync="copySuccessVisible"
      width="30%"
      :show-close="false"
      :close-on-click-modal="true"
      :close-on-press-escape="true"
    >
      <span>HTML内容已成功复制到剪贴板！</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="copySuccessVisible = false">确定</el-button>
      </span>
    </el-dialog>

    <!-- 发布文章弹窗 -->
    <el-dialog
      title="发布文章"
      :visible.sync="publishDialogVisible"
      width="50%"
      :before-close="handlePublishDialogClose"
    >
      <el-form :model="publishForm" :rules="publishRules" ref="publishForm" label-width="80px">
        <el-form-item label="分类" prop="category">
          <el-radio-group v-model="publishForm.category">
            <el-radio label="后端">后端</el-radio>
            <el-radio label="前端">前端</el-radio>
            <el-radio label="Android">Android</el-radio>
            <el-radio label="iOS">iOS</el-radio>
            <el-radio label="人工智能">人工智能</el-radio>
            <el-radio label="开发工具">开发工具</el-radio>
            <el-radio label="代码人生">代码人生</el-radio>
            <el-radio label="阅读">阅读</el-radio>
          </el-radio-group>
        </el-form-item>
    
        <el-form-item label="标签" prop="tags">
          <el-select
            v-model="publishForm.tags"
            multiple
            filterable
            allow-create
            default-first-option
            placeholder="请输入标签"
          >
            <el-option
              v-for="tag in commonTags"
              :key="tag"
              :label="tag"
              :value="tag">
            </el-option>
          </el-select>
        </el-form-item>
    
        <el-form-item label="封面图" prop="coverImage">
          <el-upload
            class="cover-uploader"
            action="/api/upload"
            :show-file-list="false"
            :on-success="handleCoverSuccess"
            :before-upload="beforeCoverUpload">
            <img v-if="publishForm.coverImage" :src="publishForm.coverImage" class="cover-image">
            <i v-else class="el-icon-plus cover-uploader-icon"></i>
            <div class="cover-tip">建议尺寸：192*128px</div>
          </el-upload>
        </el-form-item>
    
        <el-form-item label="专栏" prop="column">
          <el-select v-model="publishForm.column" placeholder="请选择专栏" clearable>
            <el-option
              v-for="column in columns"
              :key="column.value"
              :label="column.label"
              :value="column.value">
            </el-option>
          </el-select>
        </el-form-item>
    
        <el-form-item label="编辑摘要" prop="summary">
          <el-input
            type="textarea"
            v-model="publishForm.summary"
            :rows="4"
            placeholder="请输入文章摘要"
            maxlength="100"
            show-word-limit>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handlePublishDialogClose">取消</el-button>
        <el-button type="primary" @click="handlePublish">确定发布</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ClipboardJS from 'clipboard';
import '@toast-ui/editor/dist/toastui-editor.css';
import { mavonEditor } from 'mavon-editor';
import 'mavon-editor/dist/css/index.css';

export default {
  name: 'MarkdownEditor',
  components: {
    mavonEditor
  },
  props: {
    initialValue: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      editorType: 'markdown',
      markdownContent: this.initialValue,
      htmlPreview: '',
      editor: null,
      clipboard: null,
      copying: false,
      copySuccessVisible: false,
      isFullScreen: false,
      publishDialogVisible: false,
      publishForm: {
        category: '',
        tags: [],
        coverImage: '',
        column: '',
        summary: ''
      },
      publishRules: {
        category: [{ required: true, message: '请选择文章分类', trigger: 'change' }],
        tags: [{ required: true, message: '请至少添加一个标签', trigger: 'change' }],
        summary: [{ required: true, message: '请输入文章摘要', trigger: 'blur' }]
      },
      commonTags: ['Vue', 'React', 'JavaScript', 'TypeScript', 'Node.js', 'Python', 'Java', 'Go'],
      columns: [
        { value: 'frontend', label: '前端进阶' },
        { value: 'backend', label: '后端架构' },
        { value: 'algorithm', label: '算法精选' },
        { value: 'career', label: '职业成长' }
      ],
      markdownToolbars: {
        bold: true,
        italic: true,
        header: true,
        underline: true,
        strikethrough: true,
        mark: true,
        superscript: true,
        subscript: true,
        quote: true,
        ol: true,
        ul: true,
        link: true,
        imagelink: true,
        code: true,
        table: true,
        fullscreen: false,
        readmodel: true,
        htmlcode: true,
        help: true,
        undo: true,
        redo: true,
        trash: true,
        save: true,
        navigation: true
      }
    };
  },
  mounted() {
    this.initRichtextEditor();
    this.initClipboard();
  },
  beforeDestroy() {
    if (this.editor) {
      this.editor.destroy();
    }
    if (this.clipboard) {
      this.clipboard.destroy();
    }
  },
  methods: {
    showPublishDialog() {
      this.publishDialogVisible = true;
    },

    handlePublishDialogClose() {
      this.$refs.publishForm.resetFields();
      this.publishDialogVisible = false;
    },

    handleCoverSuccess(res, file) {
      this.publishForm.coverImage = URL.createObjectURL(file.raw);
    },

    beforeCoverUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isImage) {
        this.$message.error('上传封面图片只能是图片格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传封面图片大小不能超过 2MB!');
      }
      return isImage && isLt2M;
    },

    handlePublish() {
      this.$refs.publishForm.validate((valid) => {
        if (valid) {
          // TODO: 实现发布逻辑
          console.log('发布表单数据:', {
            ...this.publishForm,
            content: this.markdownContent
          });
          this.handlePublishDialogClose();
          this.$message.success('发布成功！');
        }
      });
    },

    async initRichtextEditor() {
      try {
        const Editor = (await import('@toast-ui/editor')).default;
        const codeSyntaxHighlight = (await import('@toast-ui/editor-plugin-code-syntax-highlight')).default;
        const hljs = (await import('highlight.js')).default;
        
        this.editor = new Editor({
          el: this.$refs.richtextEditor,
          height: '400px',
          initialEditType: 'wysiwyg',
          previewStyle: 'vertical',
          events: {
            change: () => {
              if (this.editorType === 'richtext') {
                this.markdownContent = this.editor.getMarkdown();
                this.updatePreview();
              }
            }
          }
        });
        
        this.editor.pluginInfo = {
          plugins: [[codeSyntaxHighlight, { hljs }]]
        };
        
        this.editor.setMarkdown(this.markdownContent);
      } catch (error) {
        console.error('富文本编辑器初始化失败:', error);
        this.$message.error('富文本编辑器加载失败，请刷新页面重试');
      }
    },
    initClipboard() {
      const button = document.createElement('button');
      button.id = 'copy-html-btn';
      button.style.display = 'none';
      document.body.appendChild(button);
      
      this.clipboard = new ClipboardJS('#copy-html-btn', {
        text: () => {
          return this.$refs.mavonEditor.d_render;
        }
      });
      
      this.clipboard.on('success', () => {
        this.copying = false;
        this.copySuccessVisible = true;
        setTimeout(() => {
          this.copySuccessVisible = false;
        }, 2000);
      });
      
      this.clipboard.on('error', () => {
        this.copying = false;
        this.$message.error('复制失败，请手动复制');
      });
    },
    updatePreview() {
      this.$emit('input', this.markdownContent);
      this.$emit('html-change', this.$refs.mavonEditor.d_render);
    },
    copyAsHtml() {
      this.copying = true;
      document.getElementById('copy-html-btn').click();
    },
    toggleFullScreen() {
      this.isFullScreen = !this.isFullScreen;
    },
    syncContent() {
      if (this.editorType === 'richtext' && this.editor) {
        this.editor.setMarkdown(this.markdownContent);
      }
    }
  },
  watch: {
    editorType() {
      this.syncContent();
    }
  }
}
</script>

<style scoped>
.editor-container {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
  background-color: #fff;
  transition: all 0.3s ease;
}

/* 发布文章弹窗样式 */
.cover-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 192px;
  height: 128px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.cover-uploader:hover {
  border-color: #409EFF;
}

.cover-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}

.cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cover-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 8px;
}

.el-radio-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.el-select {
  width: 100%;
}

.el-form-item {
  margin-bottom: 22px;
}

.editor-container.fullscreen {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 9999;
  border-radius: 0;
}

.editor-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 15px;
  border-bottom: 1px solid #dcdfe6;
  background-color: #f5f7fa;
}

.editor-content {
  display: flex;
  height: calc(100vh - 200px);
  min-height: 400px;
}

.editor-container.fullscreen .editor-content {
  height: calc(100vh - 52px);
  min-height: unset;
}

.markdown-editor, .richtext-editor {
  flex: 1;
  overflow: auto;
  display: flex;
  flex-direction: column;
}

/* 富文本编辑器样式覆盖 */
:deep(.toastui-editor-defaultUI) {
  border: none !important;
}

:deep(.toastui-editor-main) {
  height: calc(100% - 40px) !important;
}

/* mavonEditor样式覆盖 */
:deep(.v-note-wrapper) {
  z-index: 1;
}

:deep(.v-note-wrapper.fullscreen) {
  z-index: 10000;
}
</style>