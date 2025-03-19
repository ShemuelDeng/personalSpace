<template>
  <div>
    <!-- 上传资源对话框 -->
    <el-dialog
      title="上传资源"
      :visible.sync="dialogVisible"
      width="500px"
      custom-class="upload-dialog"
      @close="handleClose"
    >
      <el-form
        ref="uploadForm"
        :model="uploadForm"
        :rules="uploadRules"
        label-width="80px"
      >
        <el-form-item label="资源名称" prop="name">
          <el-input v-model="uploadForm.name" placeholder="请输入资源名称" />
        </el-form-item>

        <el-form-item label="资源分类" prop="category">
          <el-select v-model="uploadForm.category" placeholder="请选择分类">
            <el-option
              v-for="category in categories"
              :key="category.id"
              :label="category.label"
              :value="category.value"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="资源类型" prop="isFree">
          <el-radio-group v-model="uploadForm.isFree">
            <el-radio :label="1">免费</el-radio>
            <el-radio :label="0">付费</el-radio>
          </el-radio-group>
        </el-form-item>

        
        <el-form-item label="网盘地址" prop="panPath">
          <el-input v-model="uploadForm.panPath" placeholder="请输入网盘地址" />
        </el-form-item>

        <el-form-item label="提取码" prop="panCode">
          <el-input v-model="uploadForm.panCode" placeholder="请输入提取码" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="submitUpload" :loading="uploading">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addResourceApi } from '@/api/resources';
export default {
  name: "AddResource",
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    categories: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      uploading: false,
      uploadForm: {
        name: '',
        category: '',
        isFree: 1,
        panPath: '',
        panCode: ''
      },
      uploadRules: {
        name: [
          { required: true, message: '请输入资源名称', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '请选择资源分类', trigger: 'change' }
        ],
        type: [
          { required: true, message: '请选择资源类型', trigger: 'change' }
        ],
        panPath: [
          { required: true, message: '请输入网盘地址', trigger: 'blur' }
        ],
        panCode: [
          { required: true, message: '请输入提取码', trigger: 'blur' }
        ]
      }
    };
  },
  computed: {
    dialogVisible: {
      get() {
        return this.visible;
      },
      set(value) {
        this.$emit('update:visible', value);
      }
    }
  },
  methods: {
 
    /**
     * 关闭对话框
     */
    handleClose() {
      this.dialogVisible = false;
      this.$refs.uploadForm.resetFields();
    },
    /**
     * 提交上传
     */
    submitUpload() {
      this.$refs.uploadForm.validate((valid) => {
        if (valid) {
          addResourceApi(this.uploadForm).then(res => {
            this.$message.success("资源上传成功，等待博主审核！");
            // 重置表单
            this.$refs.uploadForm.resetFields();
            this.handleClose();
          })
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">

</style>
