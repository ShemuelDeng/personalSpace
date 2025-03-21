package com.shemuel.site.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口访问限流注解,可以用在类上，也可以用在方法上
 * 如果用在类上，且指定了group， 那么类里的所有方法都用该group的限流器
 *    没有指定group， 则类里的每个方法都受到限流， 但是每个方法的group不同（类名+方法名）
 * 如果用在方法上， 且指定了group， 那么该方法用该group的限流器
 *    没有指定group， 则该方法的group为类名+方法名
 * 如果类上和方法同时使用了该注解， 则方法上的注解会覆盖类上的注解
 * @Author: 公众号： 加瓦点灯
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
