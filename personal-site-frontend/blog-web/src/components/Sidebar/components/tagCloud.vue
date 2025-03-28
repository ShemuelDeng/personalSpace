<template>
  <div class="articleTag-wall">
    <div 
      class="articleTag-cloud"
      ref="wrapper"
      @mouseenter="stopRotate"
      @mouseleave="startRotate"
    >
      <p
        v-for="(item, index) in data"
        :key="index"
        ref="articleTag"
        @click="clickTag(item)"
        @mouseenter="handleTagHover(index)"
        @mouseleave="handleTagLeave"
        :class="{ 'articleTag-dimmed': hoveredIndex !== null && hoveredIndex !== index }"
      >
        {{ item.name }}
      </p>
    </div>
  </div>
</template>

<script>
import { getTagsApi } from '@/api/tags'
export default {
  data() {
    return {
      data: [],
      option: {
        radius: 140, // 滚动半径，单位px
        maxFont: 24, // 最大字体大小
        color: null, // 字体颜色。为空时随机
        rotateAngleXbase: 600, // 默认旋转速度基数，数越小速度越快
        rotateAngleYbase: 600,
      },
      tagList: [],
      isRotating: true,
      hoveredIndex: null,
    };
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
      this.timer = null;
    }
  },
  watch: {
    data() {
      this.$nextTick(() => {
        this._initTags();
      });
    },
  },
  mounted() {
    this._initTags();
    getTagsApi().then(res => {
      this.data = res.data
    })
  },
  methods: {
    /**
     * 初始化标签
     */
    _initTags() {
      this.rotateAngleX = Math.PI / this.option.rotateAngleXbase;
      this.rotateAngleY = Math.PI / this.option.rotateAngleYbase;

      for (var i = 0, length = this.data.length; i < length; i++) {
        // 获取球面上均匀的点的经纬度 θ = arccos( ((2*num)-1)/all - 1); Φ = θ*sqrt(all * π);
        let angleX = Math.acos((2 * (i + 1) - 1) / length - 1);
        let angleY = angleX * Math.sqrt(length * Math.PI);
        // 根据经纬度获取点的坐标，球中心的点坐标是 (0,0,0) x=r*sinθ*cosΦ   y=r*sinθ*sinΦ   z=r*cosθ;
        const x = this.option.radius * Math.sin(angleX) * Math.cos(angleY);
        const y = this.option.radius * Math.sin(angleX) * Math.sin(angleY);
        const z = this.option.radius * Math.cos(angleX);
        if (this.option.color) {
          this.$refs.articleTag[i].style.color = this.option.color;
        } else {
          // 随机颜色
          this.$refs.articleTag[i].style.color =
            "rgb(" +
            Math.round(255 * Math.random()) +
            "," +
            Math.round(255 * Math.random()) +
            "," +
            Math.round(255 * Math.random()) +
            ")";
        }
        // 每个标签对象都有四对值
        var articleTag = {
          x: x,
          y: y,
          z: z,
          ele: this.$refs.articleTag[i],
        };
        this.tagList.push(articleTag);
      }
      this.startRotate();
    },
    /**
     * 设置每个标签的坐标位置和字体大小以及透明度
     */
    setPosition(articleTag, r, maxFont) {
      // 设置每个标签的坐标位置和字体大小以及透明度
      if (this.$refs.wrapper) {
        articleTag.ele.style.transform =
          "translate(" +
          (articleTag.x +
            this.$refs.wrapper.offsetWidth / 2 -
            articleTag.ele.offsetWidth / 2) +
          "px," +
          (articleTag.y +
            this.$refs.wrapper.offsetHeight / 2 -
            articleTag.ele.offsetHeight / 2) +
          "px)";
        articleTag.ele.style.opacity = articleTag.z / r / 2 + 0.7;
        articleTag.ele.style.fontSize = (articleTag.z / r / 2 + 0.5) * maxFont + "px";
      }
    },
    /**
     * 旋转X轴
     */
    rotateX(articleTag) {
      var cos = Math.cos(this.rotateAngleX);
      var sin = Math.sin(this.rotateAngleX);
      var y1 = articleTag.y * cos - articleTag.z * sin;
      var z1 = articleTag.y * sin + articleTag.z * cos;
      articleTag.y = y1;
      articleTag.z = z1;
    },
    /**
     * 旋转Y轴
     */
    rotateY(articleTag) {
      var cos = Math.cos(this.rotateAngleY);
      var sin = Math.sin(this.rotateAngleY);
      var x1 = articleTag.z * sin + articleTag.x * cos;
      var z1 = articleTag.z * cos - articleTag.x * sin;
      articleTag.x = x1;
      articleTag.z = z1;
    },
    /**
     * 点击标签
     */
    clickTag(item) {
      this.$router.push({
        path: '/tags',
        query: {
          tagId: item.id,
          tagName: item.name
        }
      })
    },
    /**
     * 停止旋转
     */
    stopRotate() {
      this.isRotating = false;
      if (this.timer) {
        clearInterval(this.timer);
        this.timer = null;
      }
    },
    /**
     * 开始旋转
     */
    startRotate() {
      if (this.timer) {
        return;  // 如果定时器已存在，直接返回
      }
      
      this.isRotating = true;
      const _self = this;
      this.timer = setInterval(function () {
        if (!_self.isRotating) {
          clearInterval(_self.timer);
          _self.timer = null;
          return;
        }
        for (var i = 0; i < _self.tagList.length; i++) {
          _self.rotateX(_self.tagList[i]);
          _self.rotateY(_self.tagList[i]);
          _self.setPosition(
            _self.tagList[i],
            _self.option.radius,
            _self.option.maxFont
          );
        }
      }, 20);
    },
    handleTagHover(index) {
      this.hoveredIndex = index;
    },
    handleTagLeave() {
      this.hoveredIndex = null;
    },
  },
};
</script>

<style scoped>
.articleTag-cloud {
  width: 300px;
  height: 300px;
  position: relative;
  color: #333;
  margin: 0 auto;
  text-align: center;
  cursor: default;
}

.articleTag-cloud p {
  position: absolute;
  top: 0px;
  left: 0px;
  color: #333;
  font-family: Arial;
  text-decoration: none;
  margin: 0 10px 15px 0;
  line-height: 18px;
  text-align: center;
  font-size: 16px;
  padding: 4px 9px;
  display: inline-block;
  border-radius: 3px;
  transition: opacity 0.3s ease;
}

.articleTag-cloud p:hover {
  cursor: pointer;
}

.articleTag-dimmed {
  opacity: 0.05 !important;
}
</style>
