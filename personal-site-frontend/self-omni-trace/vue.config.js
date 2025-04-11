const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
  outputDir: '../../personal-site-backend/personal-site-server/src/main/resources/static/' // 直接输出到Spring Boot静态目录
})

