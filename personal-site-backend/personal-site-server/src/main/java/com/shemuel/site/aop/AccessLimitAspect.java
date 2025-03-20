package com.shemuel.site.aop;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import com.shemuel.site.annotation.AccessLimit;
import com.shemuel.site.common.RestResult;
import com.shemuel.site.service.IRateLimiter;
import com.shemuel.site.utils.RateLimitFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class AccessLimitAspect {

    public static final Map<String, IRateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    @Value("${rateLimiter.type: local}")
    private String rateLimiterType;

    // SpEL表达式解析器
    private static final ExpressionParser parser = new SpelExpressionParser();
    private static final ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();


    // 环绕通知
    @Around("within(@com.shemuel.site.annotation.AccessLimit *) && within(com.shemuel.site.controller..*) || " +
            "@annotation(com.shemuel.site.annotation.AccessLimit) && within(com.shemuel.site.controller..*)")
    public Object beforeClassAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object target = joinPoint.getTarget();

        // 获取方法上的注解
        AccessLimit methodAnnotation = AnnotationUtils.findAnnotation(method, AccessLimit.class);
        // 获取类上的注解
        AccessLimit classAnnotation = AnnotationUtils.findAnnotation(target.getClass(), AccessLimit.class);

        AccessLimit activeAnnotation = null;
        if (methodAnnotation != null) {
            activeAnnotation = methodAnnotation;
        } else if (classAnnotation != null) {
            // 检查是否被排除
            String methodName = method.getName();
            if (!Arrays.asList(classAnnotation.excludeMethods()).contains(methodName)) {
                activeAnnotation = classAnnotation;
            }
        }

        if (activeAnnotation == null) {
            return joinPoint.proceed(); // 无有效注解，直接放行
        }

        // 1. 解析SpEL表达式，获取限流Key对应的参数值
        String keyValue = parseKey(activeAnnotation.key(), joinPoint);
        log.info("拦截类注解 - 方法名: {}" , method.getName());
        log.info("拦截类注解 - 类名: {} " , joinPoint.getTarget().getClass().getName());


        // todo 项目启动时，初始化限流器
        String accessGroup = getAccessGroup(activeAnnotation.group(), joinPoint);
        IRateLimiter iRateLimiter = rateLimiterMap.get(accessGroup);
        if (iRateLimiter == null) {
            iRateLimiter = RateLimitFactory.getRateLimiter(rateLimiterType, activeAnnotation);
            rateLimiterMap.put(accessGroup, iRateLimiter);
        }

        log.info("限流的key: " + keyValue);
        log.info("限流的group: " + accessGroup);
        boolean granted = iRateLimiter.isGranted(keyValue);
        if (!granted) {
            return RestResult.error(activeAnnotation.msg());
        }
        return joinPoint.proceed();
    }

    /**
     * 指定了限流key, 按照key进行限流
     * key 是spel 则解析
     * 未指定key, 按照用户维度进行限流
     * 已登录的接口，都需要按照用户维度限流
     */
    public String parseKey(String keyRegex, ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 优先使用指定的spel表达式的key
        if (StringUtils.isNotEmpty(keyRegex)) {
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            StandardEvaluationContext context = new StandardEvaluationContext();
            // 将方法参数名和值绑定到上下文
            Object[] args = joinPoint.getArgs();
            for (int i = 0; i < args.length; i++) {
                context.setVariable(parameterNames[i], args[i]);
            }

            // 解析表达式
            Expression expression = parser.parseExpression(keyRegex);
            String value = expression.getValue(context, String.class);
            // 解析成功，返回解析结果
            if (StringUtils.isNotEmpty(value)) {
                return  value;
            }
            // 解析失败，就按照原始的key进行限流
            return keyRegex;
        }
        // 如果key为空，使用用户id
        if (StpUtil.isLogin()) {
            // 已登录
            // ，按照用户 + 方法级别进行限流
            return  StpUtil.getLoginIdAsString();
        }

        // 没有登录，没有key，按照方法级别进行限流
        return  className + method.getName();
    }

    public String getAccessGroup(String group, ProceedingJoinPoint joinPoint) {
        if (StringUtils.isNotEmpty(group)) {
            return group;
        }
        String className = joinPoint.getTarget().getClass().getName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return className + method.getName();
    }
}