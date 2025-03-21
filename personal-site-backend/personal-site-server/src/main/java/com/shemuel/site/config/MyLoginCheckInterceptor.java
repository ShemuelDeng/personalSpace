package com.shemuel.site.config;

import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.StopMatchException;
import cn.dev33.satoken.fun.SaParamFunction;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.strategy.SaAnnotationStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-17-20:21
 * @Description:
 */
public class MyLoginCheckInterceptor extends SaInterceptor {
    public MyLoginCheckInterceptor() {
        super();
    }
    public MyLoginCheckInterceptor(SaParamFunction<Object> auth) {
        super(auth);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (this.isAnnotation && handler instanceof HandlerMethod) {
                Method method = ((HandlerMethod)handler).getMethod();
                SaAnnotationStrategy.instance.checkMethodAnnotation.accept(method);
            }

            this.auth.run(handler);
        }catch (NotLoginException ex){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write("{\"code\": 401, \"message\": \"未登录或者登录信息已过期\"}");
            return false; // 中断后续流程
        }
        catch (StopMatchException var5) {

        } catch (BackResultException var6) {
            if (response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }

            response.getWriter().print(var6.getMessage());
            return false;
        }

        return true;
    }
}
