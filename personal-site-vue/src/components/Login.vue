<template>
  <form @submit.prevent="handleSubmit">
    <input v-model="form.email" placeholder="邮箱">
    <input v-model="form.password" type="password" placeholder="密码">
    <button type="submit">登录</button>
  </form>
</template>

<script>
import CryptoJS from 'crypto-js';

export default {
  data() {
    return {
      form: {
        email: '',
        password: ''
      }
    }
  },
  methods: {
    async handleSubmit() {
      // 客户端第一次哈希
      const hashedPwd = CryptoJS.SHA256(this.form.password).toString();
      
      // 发送登录请求（后续需要对接后端）
      console.log('加密后的密码：', hashedPwd);
      this.form.password = ''; // 清空明文密码
    }
  }
}
</script>

<style scoped>
form {
  max-width: 400px;
  margin: 2rem auto;
  padding: 20px;
  border: 1px solid #ddd;
}
input {
  display: block;
  width: 100%;
  margin-bottom: 1rem;
  padding: 8px;
}
</style>