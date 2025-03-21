package com.shemuel.site.config;

import com.alibaba.fastjson.JSON;
import com.shemuel.site.annotation.AccessLimit;
import com.shemuel.site.service.IRateLimiter;
import com.shemuel.site.utils.RateLimiterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class RateLimiterInitializer implements ApplicationRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private RateLimiterFactory rateLimiterFactory;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(RestController.class);

        for (Object controller : beansWithAnnotation.values()) {
            Class<?> clazz = AopUtils.getTargetClass(controller);
            processClassAnnotations(clazz);
        }
        log.info("RateLimiterInitializer initialized {}", JSON.toJSONString(rateLimiterFactory.getAllRateLimiterGroups()));
    }

    private void processClassAnnotations(Class<?> clazz) {
        AccessLimit classAnnotation = clazz.getAnnotation(AccessLimit.class);
        Set<String> excludes = new HashSet<>();
        // 先处理类上的注解
        if (classAnnotation != null) {
            // 记录排除的方法
            excludes.addAll(new HashSet<>(Arrays.asList(classAnnotation.excludeMethods())));
        }
        // 遍历所有方法
        for (Method method : clazz.getDeclaredMethods()) {

            // 优先处理类注解
            if (classAnnotation != null) {
                doCreateLimiter(clazz, classAnnotation, method);
            }

            AccessLimit methodAnnotation = method.getAnnotation(AccessLimit.class);
            // 再处理方法注解
            if (methodAnnotation != null) {
                doCreateLimiter(clazz, methodAnnotation, method);
            }
        }

        // 处理排除的方法
        if (!CollectionUtils.isEmpty(excludes)) {
            for (String exclude : excludes) {
                String group = classAnnotation.group().isEmpty()
                        ? clazz.getName() + "#" + exclude
                        : classAnnotation.group();
                rateLimiterFactory.removeRateLimiter(group);
            }
        }

    }

    private void doCreateLimiter(Class<?> clazz, AccessLimit classAnnotation, Method method) {
        String group = classAnnotation.group().isEmpty()
                ? clazz.getName() + "#" + method.getName()
                : classAnnotation.group();
        IRateLimiter rateLimiter = rateLimiterFactory.createRateLimiter(classAnnotation);
        rateLimiterFactory.addRateLimiter(group, rateLimiter);
    }
}