const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
  outputDir: '../../personal-site-backend/personal-site-server/src/main/resources/static/', // 直接输出到Spring Boot静态目录
  parallel: false, // 禁用thread-loader，解决Node.js高版本兼容性问题
  chainWebpack: config => {
    // 禁用所有线程加载器
    config.module.rules.delete('js')
    config.module.rule('js')
      .test(/\.jsx?$/)
      .use('babel-loader')
      .loader('babel-loader')
      .end()
  }
})

