<template>
  <div class="login-container">
    <!-- 登录表单 -->
    <el-form @submit.native.prevent="handleLogin">
      <el-input v-model="username" placeholder="用户名"></el-input>
      <el-input v-model="password" type="password" placeholder="密码"></el-input>
      <el-button type="primary" native-type="submit">登录</el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    handleLogin() {
      // 执行登录逻辑
      localStorage.setItem('token', 'your_auth_token')
      this.$router.push('/home') // 跳转到主页面
    }
  }
}
</script>