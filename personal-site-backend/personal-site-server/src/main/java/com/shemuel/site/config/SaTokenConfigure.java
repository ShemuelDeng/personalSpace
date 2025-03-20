package com.shemuel.site.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.context.model.SaResponse;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenContextException;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.servlet.model.SaResponseForServlet;
import cn.dev33.satoken.spring.SaTokenContextForSpring;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        registry.addInterceptor(new MyLoginCheckInterceptor(handle ->{
                    //StpUtil.checkLogin();
                }))
                .addPathPatterns("/api/**")
                .excludePathPatterns("/user/login", "/user/register","/user/password/recall","/ok");
    }
}
