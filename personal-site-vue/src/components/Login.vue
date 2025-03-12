<template>
  <div class="auth-container">
    <div class="auth-card">
      <!-- 登录注册切换 -->
      <div class="tabs">
        <button
            :class="{ active: isLogin }"
            @click="switchTab(true)"
        >登录</button>
        <button
            :class="{ active: !isLogin }"
            @click="switchTab(false)"
        >注册</button>
      </div>

      <!-- 登录表单 -->
      <form v-if="isLogin" @submit.prevent="handleLogin">
        <el-input
            v-model="loginForm.email"
            placeholder="邮箱"
            prefix-icon="el-icon-message"
        ></el-input>

        <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            prefix-icon="el-icon-lock"
        ></el-input>

        <div class="captcha-box">
          <el-input
              v-model="loginForm.captcha"
              placeholder="验证码"
              style="width: 60%"
          ></el-input>
          <img
              :src="captchaImg"
              class="captcha-img"
              @click="refreshCaptcha"
          >
        </div>

        <el-button
            type="primary"
            native-type="submit"
            class="submit-btn"
        >
          <i class="el-icon-s-promotion"></i> 立即登录
        </el-button>
      </form>

      <!-- 注册表单 -->
      <form v-else @submit.prevent="handleRegister">
        <el-input
            v-model="registerForm.email"
            placeholder="邮箱"
            prefix-icon="el-icon-message"
        ></el-input>

        <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="密码（8-20位）"
            prefix-icon="el-icon-lock"
        ></el-input>

        <el-input
            v-model="registerForm.confirmPwd"
            type="password"
            placeholder="确认密码"
            prefix-icon="el-icon-lock"
        ></el-input>

        <div class="captcha-box">
          <el-input
              v-model="registerForm.captcha"
              placeholder="验证码"
              style="width: 60%"
          ></el-input>
          <img
              :src="captchaImg"
              class="captcha-img"
              @click="refreshCaptcha"
          >
        </div>

        <el-button
            type="success"
            native-type="submit"
            class="submit-btn"
        >
          <i class="el-icon-circle-plus"></i> 立即注册
        </el-button>
      </form>
    </div>
  </div>
</template>

<script>
import CryptoJS from 'crypto-js'

export default {
  data() {
    return {
      isLogin: true,
      captchaImg: '',
      captchaText: '',
      loginForm: {
        email: '',
        password: '',
        captcha: ''
      },
      registerForm: {
        email: '',
        password: '',
        confirmPwd: '',
        captcha: ''
      }
    }
  },
  mounted() {
    this.refreshCaptcha()
  },
  methods: {
    // 生成随机验证码（实际应调接口）
    generateCaptcha() {
      const chars = 'ABCDEFGHJKMNPQRSTWXYZ23456789'
      let captcha = ''
      for(let i = 0; i < 4; i++) {
        captcha += chars.charAt(Math.floor(Math.random() * chars.length))
      }
      this.captchaText = captcha
      this.createCaptchaImage(captcha)
    },

    // 创建验证码图片（前端模拟）
    createCaptchaImage(text) {
      const canvas = document.createElement('canvas')
      canvas.width = 120
      canvas.height = 40
      const ctx = canvas.getContext('2d')

      // 绘制背景
      ctx.fillStyle = '#f8f8f8'
      ctx.fillRect(0, 0, canvas.width, canvas.height)

      // 绘制文字
      ctx.font = '24px Arial'
      ctx.fillStyle = '#409EFF'
      ctx.fillText(text, 30, 28)

      // 生成干扰线
      for(let i = 0; i < 5; i++) {
        ctx.strokeStyle = `rgba(0,0,0,${Math.random()*0.3})`
        ctx.beginPath()
        ctx.moveTo(Math.random()*120, Math.random()*40)
        ctx.lineTo(Math.random()*120, Math.random()*40)
        ctx.stroke()
      }

      this.captchaImg = canvas.toDataURL()
    },

    refreshCaptcha() {
      this.generateCaptcha()
    },

    switchTab(isLogin) {
      this.isLogin = isLogin
      this.refreshCaptcha()
    },

    validateCaptcha(input) {
      return input.toUpperCase() === this.captchaText
    },

    // 在Login.vue的methods中更新handleLogin方法
    async handleLogin() {
      try {
        // 客户端哈希
        const clientHash = CryptoJS.SHA256(this.loginForm.password).toString()

        // 调用登录接口
        const response = await this.$http.post('/login', {
          email: this.loginForm.email,
          password: clientHash // 注意这里传递的是客户端哈希
        })

        // 处理登录成功
        this.$message.success('登录成功')
        this.$router.push('/dashboard') // 跳转到主页

      } catch (error) {
        this.refreshCaptcha()
        this.loginForm.password = ''
      }
    },

    async handleRegister() {
      if (this.registerForm.password !== this.registerForm.confirmPwd) {
        this.$message.error('两次密码不一致')
        return
      }

      if (!this.validateCaptcha(this.registerForm.captcha)) {
        this.$message.error('验证码错误')
        return
      }

      const hashedPwd = CryptoJS.SHA256(this.registerForm.password).toString()
      // TODO: 调用注册接口
      console.log('注册请求:', {
        email: this.registerForm.email,
        password: hashedPwd
      })
    }
  }
}
</script>

<style scoped>
.auth-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

.auth-card {
  background: rgba(255, 255, 255, 0.95);
  padding: 40px;
  border-radius: 15px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  width: 100%;
  max-width: 400px;
  transition: transform 0.3s ease;
}

.auth-card:hover {
  transform: translateY(-5px);
}

.tabs {
  display: flex;
  margin-bottom: 30px;
  border-bottom: 2px solid #eee;
}

.tabs button {
  flex: 1;
  padding: 12px;
  border: none;
  background: none;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s;
}

.tabs button.active {
  color: #409EFF;
  border-bottom: 2px solid #409EFF;
}

.el-input {
  margin-bottom: 20px;
}

.captcha-box {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.captcha-img {
  width: 35%;
  height: 40px;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: opacity 0.3s;
}

.captcha-img:hover {
  opacity: 0.8;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  transition: transform 0.2s;
}

.submit-btn:hover {
  transform: scale(0.98);
}
</style>
