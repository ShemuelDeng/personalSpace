<template>
  <img
    class="logo-img"
    :src="logoUrl"
    :width="size"
    :height="size"
    alt="Logo"
  />
</template>

<script setup lang="ts">
defineProps({
  size: {
    type: [Number, String],
    default: 32
  },
})

const logoUrl = import.meta.env.VITE_APP_LOGO
</script>

<style scoped>
.logo-img {
  transition: transform 0.3s ease;
}

.logo-img:hover {
  transform: scale(1.1);
}
</style> 