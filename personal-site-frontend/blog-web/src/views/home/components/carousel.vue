<template>
  <el-carousel 
    v-if="slides && slides.length" 
    :interval="5000"
    class="custom-carousel"
  >
    <el-carousel-item v-for="(slide, index) in slides" :key="index">
      <img v-lazy="slide.cover" :key="slide.cover" :alt="slide.title">
      <div class="slide-content">
        <h3>{{ slide.title }}</h3>
        <p>{{ slide.description }}</p>
        <button class="read-more" @click="$emit('click', slide.id)">
          阅读更多
          <i class="fas fa-arrow-right"></i>
        </button>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>

<script>
export default {
  name: 'Carousel',
  props: {
    slides: {
      type: Array,
      required: true
    }
  }
}
</script>

<style lang="scss" scoped>
.custom-carousel {
  width: 100%;
  border-radius: $border-radius-lg;
  overflow: hidden;
  box-shadow: $shadow-lg;
  margin-bottom: $spacing-lg;
  :deep(.el-carousel__container) {
    height: 400px;
    @include responsive(sm) {
      height: 280px;
    }
  }
}

.el-carousel__item {
  width: 100%;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.slide-content {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: $spacing-xl;
  background: linear-gradient(
    transparent,
    rgba(0, 0, 0, 0.2) 20%,
    rgba(0, 0, 0, 0.8)
  );
  color: white;

  h3 {
    font-size: 2.2em;
    margin-bottom: $spacing-md;
    font-weight: 600;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  }

  p {
    margin-bottom: $spacing-lg;
    opacity: 0.9;
    font-size: 1.2em;
    max-width: 800px;
  }

  .read-more {
    display: inline-flex;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-sm $spacing-lg;
    background: $primary;
    color: white;
    border: none;
    border-radius: 20px;
    cursor: pointer;
    transition: all 0.3s ease;

    &:hover {
      background: darken($primary, 10%);
      transform: translateX(5px);
    }
  }
}
</style> 