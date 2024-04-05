package cn.xc.starter.ratelimeter.annotation;

import cn.xc.starter.ratelimeter.LimiteType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 限流使用注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoRateLimiter {

    double permitsPerSecond() default 0D;   // 每秒令牌数

    String returnJson() default "";         // 失败结果 JSON

    LimiteType limiteType() default LimiteType.RATE_LIMITER;

}
