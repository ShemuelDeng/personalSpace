//package com.shemuel.site.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.catalina.core.ApplicationContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@Component
//@Slf4j
//public class RateLimiterInitializer implements ApplicationRunner {
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Value("${rateLimiter.type: local}")
//    private String rateLimiterType;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        // 扫描所有Controller层的Bean
//        Map<String, Object> controllers = applicationContext.(RestController.class);
//
//        controllers.values().forEach(controller -> {
//            Class<?> clazz = AopUtils.getTargetClass(controller);
//
//            // 处理类级别注解
//            processClassLevelAnnotation(clazz, controller);
//
//            // 处理方法级别注解
//            processMethodLevelAnnotations(clazz);
//        });
//    }
//
//    private void processClassLevelAnnotation(Class<?> clazz, Object controller) {
//        AccessLimit classAnnotation = AnnotationUtils.findAnnotation(clazz, AccessLimit.class);
//        if (classAnnotation != null) {
//            Arrays.stream(clazz.getDeclaredMethods())
//                .filter(method -> !Arrays.asList(classAnnotation.excludeMethods()).contains(method.getName()))
//                .forEach(method -> initializeRateLimiter(classAnnotation, clazz, method));
//        }
//    }
//
//    private void processMethodLevelAnnotations(Class<?> clazz) {
//        Arrays.stream(clazz.getDeclaredMethods())
//            .forEach(method -> {
//                AccessLimit methodAnnotation = AnnotationUtils.findAnnotation(method, AccessLimit.class);
//                if (methodAnnotation != null) {
//                    initializeRateLimiter(methodAnnotation, clazz, method);
//                }
//            });
//    }
//
//    private void initializeRateLimiter(AccessLimit annotation, Class<?> clazz, Method method) {
//        try {
//            // 构造模拟的ProceedingJoinPoint
//            ProceedingJoinPoint joinPoint = new MethodProceedingJoinPoint() {
//                @Override
//                public Object getTarget() {
//                    return clazz;
//                }
//
//                @Override
//                public MethodSignature getSignature() {
//                    return new MethodSignature() {
//                        @Override
//                        public Method getMethod() {
//                            return method;
//                        }
//                        // 其他方法实现...
//                    };
//                }
//            };
//
//            String group = getAccessGroup(annotation.group(), joinPoint);
//            if (!AccessLimitAspect.rateLimiterMap.containsKey(group)) {
//                IRateLimiter limiter = RateLimitFactory.getRateLimiter(rateLimiterType, annotation);
//                AccessLimitAspect.rateLimiterMap.put(group, limiter);
//                log.info("Initialized rate limiter for group: {}", group);
//            }
//        } catch (Exception e) {
//            log.error("Initialize rate limiter failed", e);
//        }
//    }
//
//    // 复制原切面中的getAccessGroup逻辑
//    private String getAccessGroup(String group, ProceedingJoinPoint joinPoint) {
//        // ... 与AccessLimitAspect中相同的实现逻辑 ...
//    }
//}
