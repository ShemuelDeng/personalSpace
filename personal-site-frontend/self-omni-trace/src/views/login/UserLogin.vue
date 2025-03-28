<template>
  <div class="login-container">
    <div class="login-box">
      <!-- 左侧品牌区域 -->
      <div class="login-left">
        <img src="@/assets/logo.png" alt="Logo" class="brand-logo">
        <h2 class="brand-title">欢迎使用</h2>
        <p class="brand-desc">您的个人空间管理系统</p>
      </div>
      
      <!-- 右侧登录区域 -->
      <div class="login-right">
        <h3 class="login-title">{{ isLogin ? '用户登录' : '用户注册' }}</h3>
        
        <!-- 登录方式切换 -->
        <div class="login-type-switch" v-if="isLogin">
          <el-radio-group v-model="loginType" size="small">
            <el-radio-button label="account">账号密码</el-radio-button>
            <el-radio-button label="phone">手机号登录</el-radio-button>
          </el-radio-group>
        </div>

        <!-- 账号密码登录 -->
        <el-form v-if="isLogin && loginType === 'account'" ref="accountForm" :model="accountForm" :rules="accountRules" @submit.native.prevent="handleAccountLogin">
          <el-form-item prop="username">
            <el-input v-model="accountForm.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="accountForm.password" prefix-icon="el-icon-lock" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="captcha">
            <div class="captcha-container">
              <el-input v-model="accountForm.captcha" prefix-icon="el-icon-picture" placeholder="请输入验证码"></el-input>
              <img src="#" alt="验证码" class="captcha-img" @click="refreshCaptcha">
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit" class="submit-btn">登录</el-button>
          </el-form-item>
        </el-form>

        <!-- 手机号登录 -->
        <el-form v-if="isLogin && loginType === 'phone'" ref="phoneForm" :model="phoneForm" :rules="phoneRules" @submit.native.prevent="handlePhoneLogin">
          <el-form-item prop="phone">
            <el-input v-model="phoneForm.phone" prefix-icon="el-icon-mobile-phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item prop="smsCode">
            <div class="sms-container">
              <el-input v-model="phoneForm.smsCode" prefix-icon="el-icon-message" placeholder="请输入验证码"></el-input>
              <el-button :disabled="smsTimer > 0" @click="sendSmsCode" class="sms-btn">{{ smsTimer > 0 ? `${smsTimer}s后重新获取` : '获取验证码' }}</el-button>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit" class="submit-btn">登录</el-button>
          </el-form-item>
        </el-form>

        <!-- 注册表单 -->
        <el-form v-if="!isLogin" ref="registerForm" :model="registerForm" :rules="registerRules" @submit.native.prevent="handleRegister">
          <el-form-item prop="username">
            <el-input v-model="registerForm.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="registerForm.password" prefix-icon="el-icon-lock" type="password" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input v-model="registerForm.confirmPassword" prefix-icon="el-icon-lock" type="password" placeholder="请确认密码"></el-input>
          </el-form-item>
          <el-form-item prop="phone">
            <el-input v-model="registerForm.phone" prefix-icon="el-icon-mobile-phone" placeholder="请输入手机号"></el-input>
          </el-form-item>
          <el-form-item prop="smsCode">
            <div class="sms-container">
              <el-input v-model="registerForm.smsCode" prefix-icon="el-icon-message" placeholder="请输入验证码"></el-input>
              <el-button :disabled="registerSmsTimer > 0" @click="sendRegisterSmsCode" class="sms-btn">{{ registerSmsTimer > 0 ? `${registerSmsTimer}s后重新获取` : '获取验证码' }}</el-button>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit" class="submit-btn">注册</el-button>
          </el-form-item>
        </el-form>

        <!-- 第三方登录 -->
        <div class="third-party-login" v-if="isLogin">
          <div class="divider">
            <span>第三方账号登录</span>
          </div>
          <div class="third-party-icons">
            <i class="el-icon-chat-dot-square" title="微信登录"></i>
            <i class="el-icon-chat-line-square" title="QQ登录"></i>
          </div>
        </div>

        <!-- 切换登录/注册 -->
        <div class="switch-form">
          <span @click="switchForm">{{ isLogin ? '没有账号？立即注册' : '已有账号？立即登录' }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    // 确认密码验证
    const validateConfirmPassword = (rule, value, callback) => {
      if (value !== this.registerForm.password) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    }

    return {
      isLogin: true,
      loginType: 'account',
      smsTimer: 0,
      registerSmsTimer: 0,

      // 账号密码登录表单
      accountForm: {
        username: '',
        password: '',
        captcha: ''
      },
      // 手机号登录表单
      phoneForm: {
        phone: '',
        smsCode: ''
      },
      // 注册表单
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        phone: '',
        smsCode: ''
      },

      // 表单验证规则
      accountRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      phoneRules: {
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        smsCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      },
      registerRules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPassword: [
          { required: true, message: '请确认密码', trigger: 'blur' },
          { validator: validateConfirmPassword, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ],
        smsCode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 账号密码登录
    handleAccountLogin() {
      this.$refs.accountForm.validate(valid => {
        if (valid) {
          // TODO: 实现登录逻辑
          localStorage.setItem('token', 'your_auth_token')
          this.$router.push('/home')
        }
      })
    },

    // 手机号登录
    handlePhoneLogin() {
      this.$refs.phoneForm.validate(valid => {
        if (valid) {
          // TODO: 实现手机号登录逻辑
          localStorage.setItem('token', 'your_auth_token')
          this.$router.push('/home')
        }
      })
    },

    // 注册
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          // TODO: 实现注册逻辑
          this.isLogin = true
          this.$message.success('注册成功，请登录')
        }
      })
    },

    // 刷新图形验证码
    refreshCaptcha() {
      // TODO: 实现刷新验证码逻辑
    },

    // 发送短信验证码
    sendSmsCode() {
      if (!this.phoneForm.phone) {
        this.$message.warning('请先输入手机号')
        return
      }
      // TODO: 实现发送验证码逻辑
      this.smsTimer = 60
      const timer = setInterval(() => {
        this.smsTimer--
        if (this.smsTimer <= 0) {
          clearInterval(timer)
        }
      }, 1000)
    },

    // 发送注册短信验证码
    sendRegisterSmsCode() {
      if (!this.registerForm.phone) {
        this.$message.warning('请先输入手机号')
        return
      }
      // TODO: 实现发送验证码逻辑
      this.registerSmsTimer = 60
      const timer = setInterval(() => {
        this.registerSmsTimer--
        if (this.registerSmsTimer <= 0) {
          clearInterval(timer)
        }
      }, 1000)
    },

    // 切换登录/注册表单
    switchForm() {
      this.isLogin = !this.isLogin
      this.$nextTick(() => {
        // 重置表单
        if (this.isLogin) {
          this.$refs.accountForm?.resetFields()
          this.$refs.phoneForm?.resetFields()
        } else {
          this.$refs.registerForm?.resetFields()
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7fa;
}

.login-box {
  display: flex;
  width: 900px;
  height: 560px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.login-left {
  width: 380px;
  background: linear-gradient(135deg, #409eff, #67c23a);
  padding: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.brand-logo {
  width: 120px;
  margin-bottom: 20px;
}

.brand-title {
  font-size: 28px;
  margin-bottom: 10px;
}

.brand-desc {
  font-size: 16px;
  opacity: 0.8;
}

.login-right {
  flex: 1;
  padding: 40px;
  position: relative;
}

.login-title {
  font-size: 24px;
  color: #303133;
  margin-bottom: 30px;
  text-align: center;
}

.login-type-switch {
  text-align: center;
  margin-bottom: 20px;
}

.submit-btn {
  width: 100%;
}

.captcha-container,
.sms-container {
  display: flex;
  gap: 10px;
}

.captcha-img {
  width: 100px;
  height: 40px;
  cursor: pointer;
}

.sms-btn {
  width: 120px;
}

.third-party-login {
  margin-top: 30px;
}

.divider {
  display: flex;
  align-items: center;
  color: #909399;
  margin: 20px 0;
}

.divider::before,
.divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: #dcdfe6;
}

.divider span {
  padding: 0 12px;
  font-size: 14px;
}

.third-party-icons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.third-party-icons i {
  font-size: 24px;
  color: #909399;
  cursor: pointer;
  transition: color 0.3s;
}

.third-party-icons i:hover {
  color: #409eff;
}

.switch-form {
  text-align: center;
  margin-top: 20px;
}

.switch-form span {
  color: #409eff;
  cursor: pointer;
}

.switch-form span:hover {
  text-decoration: underline;
}
</style>