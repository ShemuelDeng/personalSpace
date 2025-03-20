package com.shemuel.site.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口访问限流注解
 * @Author: dengshaoxiang
 * @Date: 2025-03-20-16:46
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface AccessLimit {

    /**
     * 限流key, 支持 SpEL表达式
     * 优先使用key
     * 如果key为空，使用用户id
     * 如果用户id为空，使用类名加方法名
     * @return
     */
    String key() default "";

    /**
     * 时间窗口内允许访问的次数
     * @return
     */
    int limit() default 1;

    /**
     * 时间窗口
     * @return
     */
    int interval() default 1;

    /**
     * 限流器的分组
     * 同一个分组里，使用同一个限流器
     * 如果不指定，使用类名 + 方法名
     * @return
     */
    String group() default "";

    /**
     * 提示信息
     * @return
     */
    String msg() default "接口访问次数超出限制";

    /**
     * 如果作用在类上，需要排除的方法
     * @return
     */
    String[] excludeMethods() default {};
}
