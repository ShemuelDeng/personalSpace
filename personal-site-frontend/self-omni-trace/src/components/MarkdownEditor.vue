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