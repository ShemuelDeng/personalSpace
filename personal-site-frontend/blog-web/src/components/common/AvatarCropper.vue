<template>
  <div>
    <el-dialog :title="title" :visible.sync="visible" width="800px" append-to-body @opened="modalOpened"  @close="closeDialog">
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            :outputType="options.outputType"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :sm="3" :xs="3">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :sm="2" :xs="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :sm="2" :xs="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { VueCropper } from "vue-cropper";
import { uploadFileApi } from '@/api/file'
import { updateProfileApi } from '@/api/user'

export default {
  components: { VueCropper },
  props: {
    user: {
      type: Object
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      title: "修改头像",
      options: {
        img: null,
        autoCrop: true,
        autoCropWidth: 200,
        autoCropHeight: 200,
        fixedBox: true,
        outputType:"png"
      },
      previews: {},
      resizeHandler: null
    };
  },
  watch: {
    visible: {
      handler(newVal) {
        this.options.img = this.user.avatar;
      },
    },
  },
  methods: {
    /**
     * 编辑裁剪器
     */
    editCropper() {
      this.$emit('update:visible', true);
    },
    /**
     * 弹出层
     */
    modalOpened() {
      if (!this.resizeHandler) {
        this.resizeHandler = () => {
          this.refresh();
        };
      }
      window.addEventListener("resize", this.resizeHandler);
    },
    /**
     * 刷新
     */
    refresh() {
      this.$refs.cropper.refresh();
    },
    /**
     * 上传头像
     */
    requestUpload() {
    },
    /**
     * 旋转左
     */
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    /**
     * 旋转右
     */
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    /**
     * 缩放
     */
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    /**
     * 上传头像
     */
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    /**
     * 上传头像
     */
    async uploadImg() {
      this.$refs.cropper.getCropBlob(async data => {
        const file = new File([data], 'avatar.jpg', { type: 'image/jpg' });
        let formData = new FormData();
        formData.append("file", file);
        // 调用上传接口
        const response = await uploadFileApi(formData, 'avatar')
        if (response.data) {
          // 更新头像
          await updateProfileApi({ 
            id: this.user.id, 
            avatar: response.data 
          })
          this.open = false;
          this.$emit('update:visible', false);
          this.$message.success('头像更新成功')
          this.$store.state.userInfo.avatar = response.data
          // 触发更新事件，通知父组件更新用户信息
          this.$emit('update-avatar', response.data);
        }
      });
    },
    /**
     * 实时预览
     */
    realTime(data) {
      this.previews = data;
    },
    /**
     * 关闭弹出层
     */
    closeDialog() {
      this.options.img = null
      this.$emit('update:visible', false);
      window.removeEventListener("resize", this.resizeHandler);
    }
  }
};
</script>
<style scoped lang="scss">
.avatar-upload-preview {
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 200px;
	height: 200px;
	border-radius: 50%;
	box-shadow: 0 0 4px #ccc;
	overflow: hidden;
}
</style>
