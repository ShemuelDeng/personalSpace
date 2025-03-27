<template>
  <div class="editor-view">
    <h1>内容编辑器</h1>
    <p class="editor-description">支持Markdown和富文本编辑，可一键复制为HTML格式</p>
    
    <markdown-editor 
      v-model="content" 
      @html-change="htmlContent = $event"
      :initial-value="initialContent"
    />
    
    <div class="editor-actions">
      <el-button type="success" @click="saveContent">保存内容</el-button>
      <el-button @click="resetContent">重置</el-button>
    </div>
  </div>
</template>

<script>
import MarkdownEditor from '@/components/MarkdownEditor.vue';

export default {
  name: 'EditorView',
  components: {
    MarkdownEditor
  },
  data() {
    return {
      content: '',
      htmlContent: '',
      initialContent: `# 欢迎使用Markdown编辑器

## 基本语法

### 标题
使用 # 号可表示 1-6 级标题，例如：
# 一级标题
## 二级标题
### 三级标题

### 强调
*斜体*或_斜体_
**粗体**或__粗体__
***粗斜体***或___粗斜体___

### 列表
无序列表使用星号、加号或是减号作为列表标记：
* 第一项
* 第二项
* 第三项

有序列表使用数字并加上 . 号：
1. 第一项
2. 第二项
3. 第三项

### 链接和图片
[链接名称](链接地址)
![图片alt](图片地址)

### 代码
\`单行代码\`

\`\`\`javascript
// 代码块
function hello() {
  console.log('Hello, world!');
}
\`\`\`

### 表格
| 表头   | 表头   |
| ------ | ------ |
| 单元格 | 单元格 |
| 单元格 | 单元格 |

### 引用
> 引用文本

### 分割线
---

尝试编辑一下内容，看看右侧的预览效果吧！`
    };
  },
  methods: {
    saveContent() {
      // 这里可以实现保存内容的逻辑
      this.$message.success('内容已保存');
      // 例如，可以将内容保存到localStorage或发送到服务器
      localStorage.setItem('savedEditorContent', this.content);
    },
    resetContent() {
      this.content = this.initialContent;
      this.$message.info('内容已重置');
    }
  },
  mounted() {
    // 尝试从localStorage加载之前保存的内容
    const savedContent = localStorage.getItem('savedEditorContent');
    if (savedContent) {
      this.content = savedContent;
    } else {
      this.content = this.initialContent;
    }
  }
};
</script>

<style scoped>
.editor-view {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h1 {
  text-align: left;
  margin-bottom: 10px;
}

.editor-description {
  text-align: left;
  color: #606266;
  margin-bottom: 20px;
}

.editor-actions {
  margin-top: 20px;
  text-align: right;
}
</style>