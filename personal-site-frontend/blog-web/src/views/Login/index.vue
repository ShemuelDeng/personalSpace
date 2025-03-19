<template>
  <div>
    <div class="login-container">
      <!-- 登录表单 -->
      <div class="login-body">
        <el-tooltip class="item" effect="dark" content="回到首页" placement="top">
          <button class="back-btn" @click="backToHome">
            <i class="el-icon-back"></i>
          </button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" :content="currentForm === 'login' ? '账号密码登录' : '扫码登录'" placement="top">
          <button class="switch-form-btn" @click="handleSwitchForm">
            <i :class="currentForm === 'login' ? 'el-icon-user' : 'fas fa-qrcode'"></i>
          </button>
        </el-tooltip>
        <!-- 微信扫码登录 -->
        <div v-show="currentForm === 'login'" class="form-container">
          <div class="qrcode-content">
            <div class="qrcode-box">
              <!-- 这里放二维码图片 -->
              <img v-lazy="'https://img.shiyit.com/qrcode.jpg'" :key="'https://img.shiyit.com/qrcode.jpg'" alt="微信二维码">
            </div>
            <p class="qrcode-tip">登录验证码：
              <span class="code-text">{{ wechatForm.code }}</span>
              <span class="code-text" v-if="wechatForm.code === '验证码已失效'">
                <i class="fas fa-sync-alt" @click="getWechatLoginCode"></i>
              </span>
            </p>
            <p class="qrcode-tip">微信扫码关注公众号，并发送验证码</p>
          </div>

          <div class="divider">
            <el-divider>其他登录方式</el-divider>
          </div>

          <div class="third-party-login">
            <div v-for="(item, type) in loginTypes" :key="type" class="login-icon-wrapper"
              @click="handleThirdPartyLogin(type)" v-if="type !== 'wechat'">
              <el-tooltip :content="item.title" placement="top">
                <div :class="['login-icon', type]">
                  <i :class="item.icon"></i>
                </div>
              </el-tooltip>
            </div>
          </div>
        </div>

        <!-- 账号密码登录表单 -->
        <div v-show="currentForm === 'account'" class="form-container">
          <div class="form-header">
            <h2 class="form-title">账号密码登录</h2>
            <p class="form-subtitle">欢迎回来,请输入您的账号</p>
          </div>

          <el-form :model="loginForm" :rules="rules" ref="ruleFrom">
            <el-form-item class="form-item" prop="username">
              <el-input 
                prefix-icon="el-icon-user-solid" 
                v-model="loginForm.username" 
                placeholder="请输入用户名"
                @keyup.enter.native="handleLogin"
                size="large"
              />
            </el-form-item>

            <el-form-item class="form-item" prop="password">
              <el-input 
                prefix-icon="el-icon-lock" 
                v-model="loginForm.password" 
                placeholder="请输入密码"
                @keyup.enter.native="handleLogin" 
                show-password
                size="large"
              />
            </el-form-item>

            <div class="form-options">
              <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            </div>

            <el-form-item class="form-item">
              <el-button class="submit-btn ripple" :loading="loading" @click="handleLogin" type="primary">
                登 录
              </el-button>
            </el-form-item>
          </el-form>
          
          <div class="form-switch">
            <a @click="switchForm('register')">立即注册</a>
            <span class="divider-line">|</span>
            <a @click="switchForm('forgot')">忘记密码?</a>
          </div>
        </div>

        <!-- 注册表单 -->
        <div v-show="currentForm === 'register'" class="form-container">
          <div class="form-header">
                <h2 class="form-title">注册账号</h2>
                <p class="form-subtitle">欢迎注册,请输入您的账号</p>
          </div>
          <el-form :model="registerForm" :rules="rules" ref="registerForm">

            <el-form-item lable="昵称" prop="nickname">
              <el-input prefix-icon="el-icon-user-solid" v-model="registerForm.nickname" placeholder="请输入昵称" />
            </el-form-item>

            <el-form-item class="form-item" prop="email">
              <el-input prefix-icon="el-icon-message" v-model="registerForm.email" placeholder="请输入邮箱" />
            </el-form-item>

            <el-form-item class="form-item" prop="code">
              <el-input prefix-icon="el-icon-key" v-model="registerForm.code" placeholder="请输入验证码">
                <template slot="append">
                  <el-button @click="sendRegisterCode" :disabled="codeSending">
                    {{ codeButtonText }}
                  </el-button>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item class="form-item" prop="password">
              <el-input prefix-icon="el-icon-lock" v-model="registerForm.password" placeholder="请输入密码" show-password />
            </el-form-item>

            <el-form-item class="form-item">
              <el-button class="submit-btn" :loading="loading" @click="handleRegister">
                注 册
              </el-button>
            </el-form-item>

            <div class="form-switch">
              已有账号？<a @click="switchForm('account')">立即登录</a>
            </div>
          </el-form>

        </div>

        <!-- 忘记密码表单 -->
        <div v-show="currentForm === 'forgot'" class="form-container">
             <div class="form-header">
                <h2 class="form-title">找回账号</h2>
                <p class="form-subtitle">重置密码,请输入您的邮箱</p>
          </div>
          <el-form :model="forgotForm" :rules="rules" ref="forgotForm">
            <el-form-item class="form-item" prop="email">
              <el-input prefix-icon="el-icon-message" v-model="forgotForm.email" placeholder="请输入注册邮箱" />
            </el-form-item>

            <el-form-item class="form-item" prop="code">
              <el-input prefix-icon="el-icon-key" v-model="forgotForm.code" placeholder="请输入验证码">
                <template slot="append">
                  <el-button @click="sendVerificationCode" :disabled="codeSending">
                    {{ codeButtonText }}
                  </el-button>
                </template>
              </el-input>
            </el-form-item>

            <el-form-item class="form-item" prop="password">
              <el-input prefix-icon="el-icon-lock" v-model="forgotForm.password" placeholder="请输入新密码" show-password />
            </el-form-item>

            <el-form-item class="form-item">
              <el-button class="submit-btn" :loading="loading" @click="handleResetPassword">
                重置密码
              </el-button>
            </el-form-item>

            <div class="form-switch">
              <a @click="switchForm('account')">返回登录</a>
            </div>
          </el-form>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { disableScroll, enableScroll } from '@/utils/scroll'
import {
  sendEmailCodeApi, registerApi, forgotPasswordApi,
  getWechatLoginCodeApi, getWechatIsLoginApi, getAuthRenderApi
} from '@/api/auth'
import { setCookie } from '@/utils/cookie'

export default {
  name: 'Login',
  data() {
    return {
      currentForm: 'login',
      loading: false,
      wechatForm: {
        code: '',
        showQrcode: false,
      },
      countdown: 0,
      loginForm: {
        username: '',
        password: '',
        source: 'PC'
      },
      registerForm: {
        nickname: '',
        email: '',
        password: '',
        code: ''
      },
      forgotForm: {
        email: '',
        code: '',
        password: ''
      },
      loginTypes: {
        github: {
          title: 'GitHub账号登录',
          icon: 'fab fa-github'
        },
        qq: {
          title: 'QQ账号登录',
          icon: 'fab fa-qq'
        },
        wechat: {
          title: '微信扫码登录',
          icon: 'fab fa-weixin'
        },
        gitee: {
          title: '码云账号登录',
          icon: 'fab fa-git-alt'
        },
        weibo: {
          title: '微博账号登录',
          icon: 'fab fa-weibo'
        }
      },
      codeSending: false,
      codeButtonText: '发送验证码',
      codeTimer: null,
      pollingTimer: null,
      rules: {
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' }
        ]
      },
      rememberMe: false,
    }
  },

  created() {
    Object.keys(this.loginTypes).forEach(key => {
      if (!this.$store.state.webSiteInfo?.loginTypeList?.includes(key)) {
        delete this.loginTypes[key]
      }
    })
    this.getWechatLoginCode()
    this.$nextTick(() => {
      disableScroll()
    })
  },
  methods: {
    /**
     * 切换表单
     * @param form 
     */
    switchForm(form) {
      this.currentForm = form
      this.loading = false
      this.clearTimer()
      if (form === 'login') {
        this.getWechatLoginCode()
      }
    },
    /**
     * 登录
     */
    async handleLogin() {
      this.$refs['ruleFrom'].validate(async valid => {
        if (valid) {
          this.loading = true
          try {
            await this.$store.dispatch('loginAction', this.loginForm)
            this.$message.success('登录成功')
            this.handleClose()
          } catch (error) {
            this.$message.error(error.message || '登录失败，请重试')
          } finally {
            this.loading = false
          }
        } else {
          return false;
        }
      })
    },
    /**
     * 注册
     */
    async handleRegister() {
      this.$refs['registerForm'].validate(async valid => {
        if (valid) {
          this.loading = true
          try {
            await registerApi(this.registerForm)
            this.$message.success('注册成功')
            this.switchForm('login')
          } catch (error) {
            this.$message.error(error.message || '注册失败，请重试')
          } finally {
            this.loading = false
          }
        } else {
          console.log('error submit!!')
          return false;
        }
      })
    },
    /**
     * 忘记密码
     */
    async handleResetPassword() {
      this.$refs['forgotForm'].validate(async valid => {
        if (valid) {
          this.loading = true
          try {
            // 调用重置密码接口
            await forgotPasswordApi(this.forgotForm)
            this.$message.success('密码重置成功')
            this.switchForm('login')
          } catch (error) {
            this.$message.error(error.message || '重置失败，请重试')
          } finally {
            this.loading = false
          }
        } else {
          console.log('error submit!!')
          return false;
        }
      })

    },
    /**
     * 发送忘记密码邮箱验证码
     */
    async sendVerificationCode() {
      if (this.codeSending) return

      if (!this.forgotForm.email) {
        this.$message.error('请先输入邮箱')
        return
      }

      this.codeSending = true
      this.sendEmailCode(this.forgotForm.email)

    },

    /**
     * 第三方登录
     */
    handleThirdPartyLogin(type) {
      if (type === 'wechat') {
        this.wechatForm.showQrcode = true
        this.getWechatLoginCode()
        return
      }
      getAuthRenderApi(type).then(res => {
        //将当前地址存到cookie中
        if (!window.location.href.includes('login')) {
          setCookie('redirectUrl', window.location.href)
        }
        window.open(res.data, "_self")
      })
    },
    /**
     * 获取微信登录验证码
     */
    getWechatLoginCode() {
      getWechatLoginCodeApi().then(res => {
        this.wechatForm.code = res.data
        this.pollingWechatIsLogin()
        // 开始倒计时
        let countdown = 60
        this.codeTimer = setInterval(() => {
          countdown--
          if (countdown <= 0) {
            clearInterval(this.codeTimer)
            clearInterval(this.pollingTimer)
            this.wechatForm.code = '验证码已失效'
          }
        }, 1000)
      })
    },
    /**
     * 定时轮询获取微信登录状态
     */
    pollingWechatIsLogin() {
      this.pollingTimer = setInterval(() => {
        getWechatIsLoginApi(this.wechatForm.code).then(res => {
          if (res.code === 200) {
            this.$store.commit('SET_TOKEN', res.data.token)
            this.$store.commit('SET_USER_INFO', res.data)
            clearInterval(this.pollingTimer)
            this.$message.success('登录成功')
            this.handleClose()
          }
        })
      }, 1000)
    },

    /**
     * 关闭登录弹窗
     */
    handleClose() {
      this.$router.go(-1)
    },


    /**
     * 发送邮箱验证码
     */
    sendRegisterCode() {
      if (this.codeSending) return

      if (!this.registerForm.email) {
        this.$message.error('请先输入邮箱')
        return
      }
      this.codeSending = true
      this.sendEmailCode(this.registerForm.email)
    },

    /**
     * 发送邮箱验证码
     */
    sendEmailCode(email) {
      sendEmailCodeApi(email).then(res => {
        this.$message.success("发送成功，请前往邮箱查看验证码")
        // 开始倒计时
        let countdown = 60
        this.codeButtonText = `${countdown}秒后重试`

        this.codeTimer = setInterval(() => {
          countdown--
          if (countdown <= 0) {
            clearInterval(this.codeTimer)
            this.codeSending = false
            this.codeButtonText = '发送验证码'
          } else {
            this.codeButtonText = `${countdown}秒后重试`
          }
        }, 1000)
      }).catch(err => {
        this.$message.error(err.message || "发送失败")
        this.codeSending = false
      })
    },

    /**
     * 清理定时器
     */
    clearTimer() {
      if (this.codeTimer) {
        clearInterval(this.codeTimer)
      }
      if (this.pollingTimer) {
        clearInterval(this.pollingTimer)
      }
    },

    handleSwitchForm() {
      if (this.currentForm === 'login') {
        this.switchForm('account')
      } else if (this.currentForm === 'account') {
        this.switchForm('login')
      } else {
        this.switchForm('login')
      }
    },

    /**
     * 回到首页
     */
    backToHome() {
      this.$router.push('/')
    },
  },
  beforeDestroy() {
    enableScroll()
    this.clearTimer()
  }
}
</script>
<style scoped lang="scss">
.login-container {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url('@/assets/login-bg.svg') no-repeat;
  background-size: cover;
  min-height: 100vh;
  z-index: 2000;
}

.login-body {
  width: 420px;
  padding: 32px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
  backdrop-filter: blur(8px);
  background: rgba(255, 255, 255, 0.95);
  position: relative;
}

.form-container {
  animation: fadeIn 0.3s ease;
}

.form-item {
  margin-bottom: 20px;

  :deep(.el-input__inner) {
    height: 44px;
    font-size: 14px;
    
    &::placeholder {
      color: #9ca3af;
    }
  }
  
  :deep(.el-input__prefix) {
    left: 12px;
    color: #6b7280;
  }
}

.submit-btn {
  width: 100%;
  height: 42px;
  border: none;
  border-radius: 8px;
  background: #6366f1;
  color: #fff;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;

  &:hover {
    background: #4f46e5;
    transform: translateY(-1px);
  }

  &:active {
    transform: translateY(0);
  }
}

.divider {
  margin: 24px 0;
  color: #9ca3af;
  
  :deep(.el-divider__text) {
    background-color: #fff;
    padding: 0 12px;
    font-size: 14px;
  }
}

.third-party-login {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-bottom: 24px;
}

.login-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.2s;
  background: #f3f4f6;

  &:hover {
    transform: translateY(-2px);
  }

  &.github { color: #24292e; }
  &.qq { color: #12B7F5; }
  &.wechat { color: #07C160; }
  &.gitee { color: #C71D23; }
  &.weibo { color: #E6162D; }
}

.form-switch {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 24px;
  color: #6b7280;
  font-size: 14px;

  a {
    color: $primary;
    text-decoration: none;
    font-weight: 500;
    cursor: pointer;
    
    &:hover {
      color: darken($primary, 10%);
    }
  }
}

.divider-line {
  color: #e5e7eb;
  margin: 0 12px;
}

.qrcode-content {
  padding: 24px;
  text-align: center;
  animation: fadeIn 0.3s ease;
}

.qrcode-box {
  width: 200px;
  height: 200px;
  margin: 0 auto 16px;
  padding: 8px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.qrcode-tip {
  margin: 8px 0;
  color: #6b7280;
  font-size: 14px;
}

.code-text {
  color: #6366f1;
  font-weight: 500;
  i {
    cursor: pointer;
    margin-left: $spacing-sm;
  }
}

.form-header {
  text-align: center;
  margin-bottom: 32px;
}

.form-title {
  font-size: 24px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0 0 8px;
}

.form-subtitle {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.forgot-link {
  color: $primary;
  font-size: 14px;
  cursor: pointer;
  
  &:hover {
    color: darken($primary, 10%);
  }
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.switch-form-btn {
  position: absolute;
  top: 16px;
  right: 16px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  background: #f3f4f6;
  border: none;
  color: #6b7280;
  
  &:hover {
    background: #e5e7eb;
    transform: rotate(180deg);
  }
  
  i {
    font-size: 20px;
  }
}

.back-btn {
  position: absolute;
  top: 16px;
  right: 60px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  background: #f3f4f6;
  border: none;
  color: #6b7280;
  z-index: 1;
  
  &:hover {
    background: #e5e7eb;
    transform: translateX(-4px);
    color: #6366f1;
  }
  
  i {
    font-size: 20px;
  }
}
</style>
